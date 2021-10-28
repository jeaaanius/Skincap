package com.example.skincap.classifier;

import android.app.Activity;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.image.ops.Rot90Op;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AnalyzerClassifier {

    private static final float PROBABILITY_MEAN = 0.0F;
    private static final float PROBABILITY_STD = 255.0F;
    private static final float IMAGE_STD = 1.0f;
    private static final float IMAGE_MEAN = 0.0f;
    private static final int MAX_SIZE = 0;

    private final Interpreter tensorClassifier;
    private final int imageResizeX;
    private final List<String> labels;
    private final int imageResizeY;
    private TensorImage inputImageBuffer;
    private final TensorBuffer probabiltyImageBuffer;
    private final TensorProcessor probabilityProcessor;

    public AnalyzerClassifier(Activity activity) throws IOException {
        // loading the model
        MappedByteBuffer classifierModel = FileUtil.loadMappedFile(activity,
                "analyzer_model.tflite");
        labels = FileUtil.loadLabels(activity, "analyzer_labels.txt");

        tensorClassifier = new Interpreter(classifierModel, null);

        int imageTensorIndex = 0;       // input
        int probabilityTensorIndex = 0; // output

        int[] inputImageShape = tensorClassifier.getInputTensor(imageTensorIndex).shape();
        DataType inputDataType = tensorClassifier.getInputTensor(imageTensorIndex).dataType();

        int[] outputImageShape = tensorClassifier.getOutputTensor(probabilityTensorIndex).shape();
        DataType outputDataType = tensorClassifier.getOutputTensor(probabilityTensorIndex).dataType();

        imageResizeX = inputImageShape[1];
        imageResizeY = inputImageShape[2];

        inputImageBuffer = new TensorImage(inputDataType);

        probabiltyImageBuffer = TensorBuffer.createFixedSize(outputImageShape, outputDataType);

        probabilityProcessor = new TensorProcessor
                .Builder()
                .add(new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD))
                .build();
    }

    // method to recognize images
    public List<Recognition> recognizeImage(final Bitmap bitmap, final int sensorOrientation){
        List<Recognition> recognitions = new ArrayList<>();
        inputImageBuffer = loadImage(bitmap, sensorOrientation);
        tensorClassifier.run(inputImageBuffer.getBuffer(), probabiltyImageBuffer.getBuffer().rewind());
        Map<String, Float> labelledProbabilty = new TensorLabel(labels,
                probabilityProcessor.process(probabiltyImageBuffer)).getMapWithFloatValue();
        for(Map.Entry<String, Float> entry : labelledProbabilty.entrySet()){
            recognitions.add(new Recognition(entry.getKey(), entry.getValue()));
        }
        // sorting predictions based on confidence
        Collections.sort(recognitions);
        // returning top 3 predictions
        recognitions.subList(0, Math.min(MAX_SIZE, recognitions.size())).clear();
        return recognitions;
    }

    private TensorImage loadImage(Bitmap bitmap, int sensorOrientation) {
        inputImageBuffer.load(bitmap);
        // Pre-processing the capture image
        int noOfRotations = sensorOrientation / 90;
        int cropSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        ImageProcessor imageProcessor = new ImageProcessor.Builder()
                .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
                .add(new ResizeOp(imageResizeX, imageResizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(new Rot90Op(sensorOrientation))
                .add(new NormalizeOp(IMAGE_MEAN, IMAGE_STD))
                .build();
        return imageProcessor.process(inputImageBuffer);
    }

    // recognition class to return probabilities
    public static class Recognition implements Comparable{
        private String name;
        private float confidence;

        public Recognition(String name, float confidence) {
            this.name = name;
            this.confidence = confidence;
        }

        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
        public float getConfidence(){
            return confidence;
        }
        public void setConfidence(float confidence) {
            this.confidence = confidence;
        }

        @NonNull
        @Override
        public String toString(){
            return "Recognition{" +
                    "name='" + name + "'" +
                    ", confidence =" + confidence +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            return Float.compare(((Recognition)o).confidence, this.confidence);
        }
    }
}