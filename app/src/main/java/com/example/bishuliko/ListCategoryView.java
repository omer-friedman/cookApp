package com.example.bishuliko;

public class ListCategoryView {
    private String img_url;
    private String category;

    public ListCategoryView(String category, String img_url) {
        this.img_url = img_url;
        this.category = category;
    }

    public String getImgUrl() {
        return img_url;
    }
    public String getCategory() {
        return category;
    }
}
