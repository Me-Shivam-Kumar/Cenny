package com.cenny.cenny;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.CheckedOutputStream;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 2:
                return HomePageModel.GRID_PRODUCT_LAYOUT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case HomePageModel.BANNER_SLIDER:
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout,parent,false);
                return new BannerSliderViewHolder(view);
             case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                 View horizontalproductview=LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                 return new HorizontalProductViewHolder(horizontalproductview) ;
             case HomePageModel.GRID_PRODUCT_LAYOUT:
                 View gridProductView=LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout,parent,false);
                 return new GridProductViewHolder(gridProductView);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()){
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList=homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder) holder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontalProducttitle=homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelList();
                String color=homePageModelList.get(position).getBackgroundColor();
                List<WishListModel> viewAllProductList= homePageModelList.get(position).getWishListModelList();
                ((HorizontalProductViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollModelList,horizontalProducttitle,color,viewAllProductList);
                break;
           case HomePageModel.GRID_PRODUCT_LAYOUT:
               String gridProducttitle=homePageModelList.get(position).getTitle();
               String gridlayoutcolor=homePageModelList.get(position).getBackgroundColor();
               List<HorizontalProductScrollModel> gridProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelList();
               ((GridProductViewHolder) holder).setGridProductLayout(gridProductScrollModelList,gridProducttitle,gridlayoutcolor);


            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder{
        private ViewPager bannerViewPager;
        private int currentPage;
        private Timer timer;
        final private long DELAY_TIME=3000;
        final private long PERIOD_TIME=3000;
        private List<SliderModel> arrangedList;
        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerViewPager=itemView.findViewById(R.id.banner_slider_view_pager);

        }
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList){
            currentPage=2;
            if(timer!=null){
                timer.cancel();
            }
            arrangedList=new ArrayList<>();
            for(int i=0;i<sliderModelList.size();i++){
                arrangedList.add(i,sliderModelList.get(i));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));


            Slider_Adapter sliderAdapter=new Slider_Adapter(arrangedList);
            bannerViewPager.setAdapter(sliderAdapter);
            bannerViewPager.setClipToPadding(false);
            bannerViewPager.setPageMargin(20);

            bannerViewPager.setCurrentItem(currentPage);


            ViewPager.OnPageChangeListener onPageChangeListener =new ViewPager.OnPageChangeListener() {
                @Override
                //This method come in use when by any means our slide is been scrolled.
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage=position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state==ViewPager.SCROLL_STATE_IDLE){
                        pageLooper(arrangedList);
                    }
                }
            };
            bannerViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(arrangedList);

            bannerViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangedList);
                    stopBannerSlideShow();
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        startBannerSlideShow(arrangedList);
                    }
                    return false;
                }
            });

        }
        private void pageLooper(List<SliderModel> sliderModelList){
            //This if is for, if our slider reach last page then we will move it to the 2 indexed slide i.e actually our first one.
            if(currentPage==sliderModelList.size()-2){
                currentPage=2;
                bannerViewPager.setCurrentItem(currentPage,false);
            }
            //This if is for, if our user is in the first page and scrolled back;
            if(currentPage==1){
                currentPage=sliderModelList.size()-3;
                bannerViewPager.setCurrentItem(currentPage,false);
            }
        }
        private void startBannerSlideShow(final List<SliderModel> sliderModelList){
            final Handler handler=new Handler();
            final Runnable update=new Runnable() {
                @Override
                public void run() {
                    if(currentPage>= sliderModelList.size()){
                        currentPage=1;
                    }
                    bannerViewPager.setCurrentItem(currentPage++,true);
                }
            };
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            },DELAY_TIME,PERIOD_TIME);
        }
        private void stopBannerSlideShow(){
            timer.cancel();
        }
    }
    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder{
        private TextView horizontalLayoutTitle;
        private Button horizontalViewAllButton;
        private RecyclerView horizontalRecyclerView;
        private ConstraintLayout container;


        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalLayoutTitle=itemView.findViewById(R.id.horizontal_scroll_title);
            horizontalViewAllButton=itemView.findViewById(R.id.horizontal_scroll_button);
            horizontalRecyclerView=itemView.findViewById(R.id.horizontal_scroll_recycler_view);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
            container=itemView.findViewById(R.id.container);
        }
        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, final String title, String color, final List<WishListModel> viewAllProductModelList){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalLayoutTitle.setText(title);
            if(horizontalProductScrollModelList.size()>8){
                horizontalViewAllButton.setVisibility(View.VISIBLE);
                horizontalViewAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishListModelList=viewAllProductModelList;
                        Intent viewAllIntent=new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        viewAllIntent.putExtra("title",title);
                        itemView.getContext().startActivity(viewAllIntent);

                    }
                });
            }else {
                horizontalViewAllButton.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter =new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }
    public class GridProductViewHolder extends RecyclerView.ViewHolder{
        TextView gridLayoutTitle;
        Button gridLayoutViewAllButton;
        private GridLayout gridProductLayout;
        private ConstraintLayout container;
        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle=itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllButton =itemView.findViewById(R.id.grid_product_layout_view_all_btn);
            gridProductLayout=itemView.findViewById(R.id.grid_layout);
            container=itemView.findViewById(R.id.container);

        }
        private void setGridProductLayout(final List<HorizontalProductScrollModel> horizontalProductScrollModelList, final String title, String color){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);
            for(int x=0;x<4;x++){
                ImageView productImage=gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_scroll_product_image);
                TextView productTitle=gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_scroll_product_title);
                TextView productDescription=gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_scroll_product_description);
                TextView productPrice=gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_scroll_product_price);

                Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.bg_two)).into(productImage);
                productTitle.setText(horizontalProductScrollModelList.get(x).getProductTitle());
                productDescription.setText(horizontalProductScrollModelList.get(x).getProductDescription());
                productPrice.setText("Rs."+horizontalProductScrollModelList.get(x).getProductPrice()+"/-");

                gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));

                final int finalX = x;
                gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                        productDetailsIntent.putExtra("PRODUCT_ID",horizontalProductScrollModelList.get(finalX).getProductID());
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }

            gridLayoutViewAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.horizontalProductScrollModelList=horizontalProductScrollModelList;
                    Intent viewAllIntent=new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);


                }
            });

        }
    }
}
