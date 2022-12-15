package com.suatzengin.imagegeneratorwithopenai.data.network.model;

public class ImageRequest {
    private String prompt;
    private int n;
    private String size;

    public ImageRequest(String prompt, int n, String size){
        this.setPrompt(prompt);
        this.setN(n);
        this.setSize(size);
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
