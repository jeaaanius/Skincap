package com.example.skincap.ui.library;

public class Library {

    private String skinIssue;
    private String def;
    private String causes;
    private String ingredient;
    private boolean expanded;

    public Library(String skinIssue, String def, String causes, String ingredient) {
        this.skinIssue = skinIssue;
        this.def = def;
        this.causes = causes;
        this.ingredient = ingredient;
        this.expanded = false;
    }

    public String getSkinIssue() {
        return skinIssue;
    }

    public void setSkinIssue(String skinIssue) {
        this.skinIssue = skinIssue;
    }

    public String getDefinition() {
        return def;
    }

    public void setDefinition(String def) {
        this.def = def;
    }

    public String getCauses() {
        return causes;
    }

    public void setCauses(String causes) {
        this.causes = causes;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return "Library{" +
                "skinIssue='" + skinIssue + '\'' +
                ", definition='" + def + '\'' +
                ", causes='" + causes + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", expanded=" + expanded +
                '}';
    }
}

