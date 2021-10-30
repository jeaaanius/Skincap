package com.example.skincap.ui.checkskin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skincap.R;
import com.example.skincap.classifier.AnalyzerClassifier;
import com.example.skincap.classifier.ImageClassifier;
import com.example.skincap.databinding.ActivityResultBinding;
import com.example.skincap.ui.MainActivity;
import com.example.skincap.util.GlideBinder;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 1002;

    private ActivityResultBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResultBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        int requestCode = getIntent().getIntExtra("REQUEST_CODE", 0);
        if(requestCode == 1001)
            startCameraInstant();
        else if(requestCode == 1002)
            startGalleryInstant();
    }

    @SuppressWarnings("deprecation")
    private void startCameraInstant() {
        OpenCVLoader.initDebug();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
   @SuppressWarnings("deprecation")
    private void startGalleryInstant() {
       OpenCVLoader.initDebug();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == RESULT_CANCELED) {
                finish();
            }else{
                Bitmap bitmapPhoto = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");
                resultBitmap(bitmapPhoto);
                showSkin(bitmapPhoto);
            }
        }else if (requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == RESULT_CANCELED){
                finish();
            }else{
                Uri selectedImage = data.getData();
                try {
                    Bitmap selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                    resultBitmap(selectedImageBitmap);
                    showSkin(selectedImageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initImageClassifier(final Bitmap bitmap) {
        try {

            ImageClassifier classifier = new ImageClassifier(this);

            List<ImageClassifier.Recognition> predictions = classifier.recognizeImage(bitmap, 0);

            final List<String> predictionsList = new ArrayList<>();

            for (ImageClassifier.Recognition recognition : predictions) {
                predictionsList.add(recognition.getName() + " : " + recognition.getConfidence());
                Log.i("Probabilities: ", recognition.getName() + " : " + recognition.getConfidence());
            }

            setRecognizedProbabilities(predictionsList);

        } catch (IOException e) {
            Log.e("Image Classifier Error", "ERROR: " + e);
        }

        // SECOND CLASSIFIER FOR SKIN ANALYZER
        try {

            AnalyzerClassifier classifier = new AnalyzerClassifier(this);

            List<AnalyzerClassifier.Recognition> predictions = classifier.recognizeImage(bitmap, 0);

            final List<String> predictionsList = new ArrayList<>();

            for (AnalyzerClassifier.Recognition recognition : predictions) {
                predictionsList.add(recognition.getName() + " : " + recognition.getConfidence());
                Log.i("Probabilities: ", recognition.getName() + " : " + recognition.getConfidence());
            }
            setRecognizedProbabilities2(predictionsList);

        } catch (IOException e) {
            Log.e("Image Classifier Error", "ERROR: " + e);
        }
    }

    private void setRecognizedProbabilities(final List<String> predictionsList) {
        binding.lvProbabilities.setAdapter(getProbabilityAdapter(predictionsList));
    }

    private ArrayAdapter<String> getProbabilityAdapter(final List<String> predictionsList) {
        return new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, predictionsList);
    }

    // SECOND CLASSIFIER METHODS
    private void setRecognizedProbabilities2(final List<String> predictionsList) {
        binding.lvProbabilities2.setAdapter(getProbabilityAdapter2(predictionsList));
    }

    private ArrayAdapter<String> getProbabilityAdapter2(final List<String> predictionsList) {
        return new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, predictionsList);
    }

    private void resultBitmap(Bitmap photo){

        photo = photo.copy(Bitmap.Config.ARGB_8888, true);
        binding.ivCapture.setImageBitmap(photo);

        GlideBinder.bindImage(binding.ivCapture, photo);
        initImageClassifier(photo);
        String result = binding.lvProbabilities.getItemAtPosition(0).toString() + " " +
                binding.lvProbabilities2.getItemAtPosition(0).toString();

        ArrayList<String> scripts = new ArrayList<String>();

        String[] result_array = result.split(" : ",0);
        for(int i = 0; i< result_array.length; i++){
            result_array[i].trim();
        }
        String temp_array[] = result_array[1].split(" ");
        scripts.add(result_array[0]);
        scripts.add(temp_array[0]);
        scripts.add(temp_array[1]);
        scripts.add(result_array[2]);

        float confidence1 = (Float.parseFloat(scripts.get(1)))*100;
        float confidence2 = (Float.parseFloat(scripts.get(3)))*100;

        binding.resultTv.setText(scripts.get(0));
        binding.conditionConfidenceTv.setText(String.format("%.2f", confidence1) + "%");
        binding.resultTv2.setText(scripts.get(2));
        binding.analyzerConfidenceTv.setText(String.format("%.2f", confidence2) + "%");

        ArrayList<String> issue = new ArrayList<String>();
        issue.add("Acne Papule"); // 0 - 3
        issue.add("A tiny red lump on the skin. It normally has a diameter of less than 5 millimeters");
        issue.add("The primary causes of papules, and acne in general, include:\n" +
                "bacteria, excess oil production, excess activity of androgens (male sex hormones)\n" +
                "Acne can also be triggered or aggravated by:\n" +
                "stress, diet (such as consuming too much sugar)");
        issue.add("► Benzoyl peroxide\n" +
                "is the best single ingredient (antimicrobial) to fight both acne bacteria and yeast.\n\n" +
                "► Salicylic acid\n" +
                "is a keratolytic and has anti-inflammatory, antibacterial, and antifungal properties.");

        issue.add("Sun Spots"); // 4 - 7
        issue.add("Flat brown spots appear on parts of your skin that have been exposed to the sun.");
        issue.add("Sunspots occur as a result of overexposure to UV light. Therefore, " +
                "spending too much time out in the sun or inside a tanning bed " +
                "can result in the blemishes forming. This exposure causes your " +
                "skin to increase its production of melanin, which is the pigment " +
                "that gives skin its color.");
        issue.add("► Licorice extract\n" +
                "Some of the active ingredients in licorice extract may help lighten " +
                "sunspots and other skin discoloration aggravated by sun exposure.\n\n" +
                "► Vitamin C\n" +
                "Topical L-ascorbic acid protects your skin from UVA and UVB rays, " +
                "promotes collagen production, and has been found to be effective in " +
                "lightening dark spots.\n\n" +
                "► Vitamin E\n" +
                "Applying vitamin E oil provides even more benefits for your skin against " +
                "sun damage and may help lighten sunspots.\n\n" +
                "► Azelaic acid\n" +
                "Azelaic acid treats sunspots and melasma because it blocks the production " +
                "of \"abnormal pigmentation\".\n\n" +
                "► Mulberry\n" +
                "Mulberry Extract is known for its natural skin brightening properties " +
                "and the ability to help fade away dark spots and pigmentation caused by " +
                "ageing and exposure to the sun.\n\n" +
                "► Niacinamide\n" +
                "can be used to treat a wide range of skin issues, including acne, rosacea, " +
                "hyperpigmentation and wrinkles. Additionally, it provides plenty of benefits " +
                "for the skin, helping to increase collagen production, reduce moisture loss, " +
                "improve fine lines and wrinkles, protect the skin barrier and minimize the " +
                "appearance of pores and dark spots caused by sun exposure.");

        issue.add("Whiteheads"); // 8 - 11
        issue.add("Little, raised lumps on the skin that are white in color.");
        issue.add("When dead skin cells, oil, and bacteria become stuck within one of your pores.");
        issue.add("► Tea tree\n" +
                "touted as a natural anti-inflammatory. It may also have antimicrobial benefits. " +
                "These effects mean tea tree oil could help clear up whiteheads. (NOT OIL)\n\n" +
                "► Green Tea\n" +
                "Some of the compounds in green tea have antimicrobial and anti-inflammatory " +
                "properties. Additionally, it appears to decrease sebum production. " +
                "Studies looking at its use in mild to moderate acne are promising.");

        issue.add("Blackheads"); // 12 - 15
        issue.add("Small lumps on the skin caused by clogged hair follicles.");
        issue.add("Form when a hair follicle in the skin becomes clogged or " +
                "plugged. Dead skin cells and excess oil collect in the follicle’s " +
                "opening, which produces a bump. If the skin over the bump opens, " +
                "the air exposure causes the plug to look black, thus forming a blackhead.");
        issue.add("► Azelaic acid\n" +
                "often used as an alternative treatment for acne if the side effects of " +
                "benzoyl peroxide or topical retinoids are particularly irritating or painful." +
                "Azelaic acid works by getting rid of dead skin and killing bacteria.");

        issue.add("Fungal Acne"); // 16 - 19
        issue.add("Infection in the hair follicles of your skin.");
        issue.add("Caused by an overgrowth of yeast, a type of fungus.");
        issue.add("► Itraconazole\n" +
                "Many investigators have studied the efficacy of itraconazole, as this " +
                "antifungal is excreted in high concentrations in sebum." +
                "Itraconazole is a broad-spectrum triazole, which is highly lipophilic " +
                "and keratophilic with good oral absorption and extensive tissue distribution.\n\n" +
                "► Azelaic Acid\n" +
                "This natural ingredient and commonly used to treat fungal acne as well " +
                "as rosacea also has fungal acne properties." +
                "It may help by regulating the fatty acid content in your skin, a common " +
                "mechanism used by antifungals. As the yeast or fungi survive on the " +
                "fatty acids and oils in the skin, reducing the free fatty acid content " +
                "may help to reduce the yeast population.\n\n" +
                "► Tea Tree\n" +
                "Derived from the Melaleuca alternifolia plant, it is a well-known antimicrobial." +
                "In vitro studies show that it may be effective in inhibiting several " +
                "species of fungi and we know there are many.");

        issue.add("Perioral Dermatitis"); // 20 - 23
        issue.add("A rash involving the skin around the mouth that is inflamed.");
        issue.add("The cause of perioral dermatitis is unknown. However, experts " +
                "suggest that it can occur after the use of strong topical steroids on the skin.");
        issue.add("► Vitamin B3 (INCI: Niacinamide)\n" +
                "In the case of acne vulgaris where similar germ colonization occurs " +
                "as in perioral dermatitis, vitamin B3 also has an anti-inflammatory " +
                "effect which is comparable to clindamycin.\n\n" +
                "► Provitamin B5 (INCI: D-panthenol)\n" +
                "the pre-stage of pantothenic acid improves the skin hydration." +
                "It improves the cell formation and epithelialization after skin lesions " +
                "and inhibits the itching.\n\n" +
                "► Green tea (camellia leaf extract)\n" +
                "Calms and helps regulate sebum production to promote a balanced, clear complexion.");

        issue.add("Milia"); // 24 - 27
        issue.add("Typically develops on the nose and cheeks as a little white lump.");
        issue.add("In older children and adults, milia are typically associated with some type of damage to the skin. This may include:\n" +
                "burns\n" +
                "long-term sun damage\n" +
                "long-term use of steroid creams\n");
        issue.add("► Curettage\n" +
                "*Note: not an ingredient, but a dermatological procedure*\n" +
                "With this procedure, the dermatologist scrapes off the milia then seals " +
                "the skin with a hot wire.");

        issue.add("Oily"); // 28 - 31
        issue.add("The result of the overproduction of sebum from sebaceous glands.");
        issue.add("Genetics, hormone changes, or even stress may increase sebum production.");
        issue.add("► Beta-Hydroxy Acids (BHAs)\n" +
                "BHAs are oil-soluble acids that plunge deep into pores to target oil " +
                "glands and decrease oil secretion.\n\n" +
                "► Niacinamide\n" +
                "Lauded its anti-inflammatory, soothing, brightening, and skin tone-evening benefits\n\n" +
                "► Glycolic Acid\n" +
                "An AHA that minimizes pores and speeds up cell turnover " +
                "for fresher, clearer, younger-looking skin.");

        issue.add("Dry"); // 32 - 35
        issue.add("An uncomfortable condition marked by scaling, itching, and cracking.");
        issue.add("Exposure to dry weather conditions, hot water, and certain chemicals " +
                "can cause your skin to dry out. Dry skin can also result from " +
                "underlying medical conditions.");
        issue.add("► Beta-Hydroxy Acids (BHAs)\n" +
                "BHAs are oil-soluble acids that plunge deep into pores to target oil " +
                "glands and decrease oil secretion.\n\n" +
                "► Niacinamide\n" +
                "Lauded its anti-inflammatory, soothing, brightening, and skin tone-evening benefits\n\n" +
                "► Glycolic Acid\n" +
                "An AHA that minimizes pores and speeds up cell turnover " +
                "for fresher, clearer, younger-looking skin.");

        issue.add("Balanced"); // 36 - 39
        issue.add("Skin is eudermic");
        issue.add("It could be the result of your healthy living, genetics, or proper regime.");
        issue.add("ALL IS WELL. Keep it up");

        if(scripts.get(0).equals("Acne Papule")){
            binding.definitionDesc.setText(issue.get(1));
            binding.causesDesc.setText(issue.get(2));
            binding.ingredDesc.setText(issue.get(3));
        }
        else if(scripts.get(0).equals("Sun Spots")){
            binding.definitionDesc.setText(issue.get(5));
            binding.causesDesc.setText(issue.get(6));
            binding.ingredDesc.setText(issue.get(7));
        }
        else if(scripts.get(0).equals("Whiteheads")){
            binding.definitionDesc.setText(issue.get(9));
            binding.causesDesc.setText(issue.get(10));
            binding.ingredDesc.setText(issue.get(11));
        }
        else if(scripts.get(0).equals("Blackheads")){
            binding.definitionDesc.setText(issue.get(13));
            binding.causesDesc.setText(issue.get(14));
            binding.ingredDesc.setText(issue.get(15));
        }
        else if(scripts.get(0).equals("Fungal Acne")){
            binding.definitionDesc.setText(issue.get(17));
            binding.causesDesc.setText(issue.get(18));
            binding.ingredDesc.setText(issue.get(19));
        }
        else if(scripts.get(0).equals("Perioral Dermatitis")){
            binding.definitionDesc.setText(issue.get(21));
            binding.causesDesc.setText(issue.get(22));
            binding.ingredDesc.setText(issue.get(23));
        }
        else if(scripts.get(0).equals("Milia")){
            binding.definitionDesc.setText(issue.get(25));
            binding.causesDesc.setText(issue.get(26));
            binding.ingredDesc.setText(issue.get(27));
        }
        if(scripts.get(0).equals("Normal")){
            if(scripts.get(2).equals("Oily")){
                binding.definitionDesc.setText(issue.get(29));
                binding.causesDesc.setText(issue.get(30));
                binding.ingredDesc.setText(issue.get(31));
            }else if(scripts.get(2).equals("Dry")){
                binding.definitionDesc.setText(issue.get(33));
                binding.causesDesc.setText(issue.get(34));
                binding.ingredDesc.setText(issue.get(35));
            }else if(scripts.get(2).equals("Balanced")) {
                binding.definitionDesc.setText(issue.get(37));
                binding.causesDesc.setText(issue.get(38));
                binding.ingredDesc.setText(issue.get(39));
            }

        }
//        if(confidence1 < 50) {
//            Toast.makeText(ResultActivity.this, "For Better accuracy\nTry Clearer Image", Toast.LENGTH_LONG).show();
//
//        }
    }

    private void showSkin(Bitmap image){
        Mat hsvImage;
        Mat thresholdImage[] = new Mat[9];
        for(int i=0; i<thresholdImage.length; i++)
        {
            thresholdImage[i] = new Mat(256, 256, CvType.CV_8U);
        }

        hsvImage = new Mat(256, 256, CvType.CV_8U);
        Utils.bitmapToMat(image, hsvImage);
        Imgproc.cvtColor(hsvImage, hsvImage, Imgproc.COLOR_RGB2HSV);
        Utils.matToBitmap(hsvImage,image);


        Scalar skin = new Scalar(0,24,151);
        Scalar skin2 = new Scalar(23,197,255);

        Scalar skin3 = new Scalar(3, 83, 172);
        Scalar skin4 = new Scalar(23, 103, 252);

        Scalar skin5 = new Scalar(0, 58, 170);
        Scalar skin6 = new Scalar(20, 78, 250);

        Scalar skin7 = new Scalar(0, 61, 101);
        Scalar skin8 = new Scalar(21, 81, 199);

        Scalar skin9 = new Scalar(6, 46, 132);
        Scalar skin10 = new Scalar(26, 66, 212);


        Core.inRange(hsvImage, skin, skin2, thresholdImage[0]);
        Core.inRange(hsvImage, skin3, skin4, thresholdImage[1]);
        Core.inRange(hsvImage, skin5, skin6, thresholdImage[2]);
        Core.inRange(hsvImage, skin7, skin8, thresholdImage[3]);
        Core.inRange(hsvImage, skin9, skin10, thresholdImage[4]);

        Bitmap image2 = Bitmap.createBitmap(thresholdImage[0].cols(), thresholdImage[0].rows(), Bitmap.Config.ARGB_8888);

        Core.bitwise_or(thresholdImage[0],thresholdImage[1], thresholdImage[5]);
        Core.bitwise_or(thresholdImage[2],thresholdImage[3], thresholdImage[6]);
        Core.bitwise_or(thresholdImage[4],thresholdImage[5], thresholdImage[7]);
        Core.bitwise_or(thresholdImage[6],thresholdImage[7], thresholdImage[8]);

        Utils.matToBitmap(thresholdImage[8],image2);

        int length = image.getWidth(); // row
        int width = image.getHeight(); // col

        float white = Core.countNonZero(thresholdImage[8]);
        white = (white/(length * width))*100;

        if(white < 50){
            for (int i = 0; i<2; i++){
                Toast.makeText(ResultActivity.this, "Skin Detected is too limited\nTry clearer and closer image, plus good lighting" ,Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(ResultActivity.this, "Skin Detection Successfull" ,Toast.LENGTH_LONG).show();
        }

        binding.ivCapture2.setImageBitmap(image2);
        binding.skinDetectTv.setText( String.format("%.2f", white) + "%");
    }
}
