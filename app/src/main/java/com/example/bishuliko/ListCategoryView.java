package com.example.bishuliko;

public class ListCategoryView {
    private int img;
    private String category;

    public ListCategoryView(int img,String category) {
        this.img = img;
        this.category = category;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImg() {
        return img;
    }

    public String getCategory() {
        return category;
    }
}
