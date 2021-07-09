package com.cenny.cenny;

public class SliderModel {
    private String banner;
    private String backgroundColor;

    public SliderModel(String banner, String backgroundColor) {
        this.banner = banner;
        this.backgroundColor = backgroundColor;
    }


    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBackgrounfColor() {
        return backgroundColor;
    }

    public void setBackgrounfColor(String backgrounfColor) {
        this.backgroundColor = backgrounfColor;
    }
}
