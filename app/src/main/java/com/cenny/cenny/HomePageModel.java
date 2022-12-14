package com.cenny.cenny;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER=0;
    public static final int HORIZONTAL_PRODUCT_VIEW=1;
    public static final int GRID_PRODUCT_LAYOUT=2;
    private  int type;
    private String backgroundColor;

    ///////Banner Slider
    private List<SliderModel> sliderModelList;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    ///////Banner Slider



    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    ////// Horizontal Product Layout
    private List<WishListModel> wishListModelList;
    public HomePageModel(int type, String title,String backgroundColor,List<HorizontalProductScrollModel> horizontalProductScrollModelList,List<WishListModel> wishListModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor=backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
        this.wishListModelList=wishListModelList;
    }


    public List<WishListModel> getWishListModelList() {
        return wishListModelList;
    }

    public void setWishListModelList(List<WishListModel> wishListModelList) {
        this.wishListModelList = wishListModelList;
    }

    ////// Horizontal Product Layout

    ////// Grid Product Layout
    public HomePageModel(int type, String title,String backgroundColor,List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor=backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    ////// Grid Product Layout

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }

    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }



}
