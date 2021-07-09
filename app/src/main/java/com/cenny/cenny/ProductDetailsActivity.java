package com.cenny.cenny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.cenny.cenny.DBQueries.loadWishList;
import static com.cenny.cenny.DBQueries.wishList;
import static com.cenny.cenny.MainActivity.showCart;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;
    private static Boolean ALREADY_ADDED_TO_WISH_LIST = false;
    private FloatingActionButton addToWishListBtn;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private TextView productTitle;
    private TextView productPrice, productCuttedPrice;

    private Button buyNowBtn;
    private Button coupenRedeemBtn;
    private LinearLayout addToCartBtn;


    /////coupendialog
    public static TextView coupenTitle, coupenBody, coupenExpiryDate;
    private static RecyclerView coupenRecyclerView;
    private static LinearLayout selectedCoupen;
    //////coupendialog

    ///Product Description
    private ConstraintLayout productDetailsTabsContainer;
    private FirebaseFirestore firebaseFirestore;
    private String productDescription;
    private String productOtherDetails;
    private List<ProductSpecificationModel> productSpecificationModelList;
    private TextView averageRating;

    ///Rating Layout
    private TextView totalRatings;
    private LinearLayout ratingsNumberContainer;
    private LinearLayout ratingsProgressBarContainer;

    private boolean setSignUpFragment = false;
    private Dialog signIndialog;
    private FirebaseUser currentUser;

    private String productID;
    private Dialog loadingDilaog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productSpecificationModelList = new ArrayList<>();
        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewPagerIndicator = findViewById(R.id.view_pager_indcator);
        addToWishListBtn = findViewById(R.id.add_to_wish_list_btn);
        productDetailsViewPager = findViewById(R.id.product_deatils_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);
        productTitle = findViewById(R.id.product_details_title);
        productPrice = findViewById(R.id.product_details_price);
        productCuttedPrice = findViewById(R.id.product_cutted_price);
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNumberContainer = findViewById(R.id.ratings_number_container);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progreshbar_container);
        averageRating = findViewById(R.id.average_rating);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);


        productDetailsTabsContainer = findViewById(R.id.product_details_tabs_container);

        buyNowBtn = findViewById(R.id.buy_now_btn);
        coupenRedeemBtn = findViewById(R.id.coupan_redemption_btn);
        productID = getIntent().getStringExtra("PRODUCT_ID");

        ///Loading Dialog
        loadingDilaog = new Dialog(ProductDetailsActivity.this);
        loadingDilaog.setContentView(R.layout.loading_progres_dialog);
        loadingDilaog.setCancelable(false);
        loadingDilaog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDilaog.show();
        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        firebaseFirestore.collection("PRODUCTS").document(
                productID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (long x = 1; x < (long) documentSnapshot.get("no_of_product_images") + 1; x++) {
                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    productPrice.setText("Rs." + documentSnapshot.get("product_price").toString() + "/-");
                    productCuttedPrice.setText("Rs." + documentSnapshot.get("cutted_price").toString() + "/-");
                    productDescription = documentSnapshot.get("product_description").toString();
                    productOtherDetails = documentSnapshot.get("prouct_other_details").toString();
                    totalRatings.setText(String.valueOf((long) documentSnapshot.get("total_ratings")) + " ratings");
                    averageRating.setText(documentSnapshot.get("average_rating").toString());
                    for (int i = 0; i < 5; i++) {
                        TextView rating = (TextView) ratingsNumberContainer.getChildAt(i);
                        rating.setText(String.valueOf((long) documentSnapshot.get((5 - i) + "_star")));

                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(i);
                        int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - i) + "_star"))));
                    }

                    for (long i = 1; i < (long) documentSnapshot.get("total_spec_titles") + 1; i++) {
                        productSpecificationModelList.add(new ProductSpecificationModel(documentSnapshot.get("spec_title_" + i).toString(), documentSnapshot.get("spec_" + i + "_value").toString()));
                    }
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));
                    if (wishList.size() == 0) {
                        loadWishList(ProductDetailsActivity.this,loadingDilaog);
                    }else {
                        loadingDilaog.dismiss();
                    }
                    if (wishList.contains(productID)) {
                        ALREADY_ADDED_TO_WISH_LIST = true;
                        addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF44336")));
                    } else {
                        ALREADY_ADDED_TO_WISH_LIST = false;
                        addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                    }

                } else {
                    loadingDilaog.dismiss();
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }


            }
        });


        viewPagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signIndialog.show();
                } else {
                    if (ALREADY_ADDED_TO_WISH_LIST) {
                        ALREADY_ADDED_TO_WISH_LIST = false;
                        addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));

                    } else {
                        Map<String, Object> addproductId = new HashMap();
                        addproductId.put("product_ID_" + (String) String.valueOf(wishList.size()), productID);
                        addproductId.put("list_size", (long) wishList.size() + 1);
                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST").
                                set(addproductId, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    ALREADY_ADDED_TO_WISH_LIST = true;
                                    addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF44336")));
                                    wishList.add(productID);
                                    Toast.makeText(ProductDetailsActivity.this,"Added to wish list successfully",Toast.LENGTH_SHORT).show();
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(ProductDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }
                }
            }
        });

        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signIndialog.show();
                } else {
                    Intent deliveyIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                    startActivity(deliveyIntent);
                }

            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signIndialog.show();
                } else {

                }
            }
        });
        /////Coupen Dialog
        final Dialog checkCoupenPriceDialog = new Dialog(ProductDetailsActivity.this);
        checkCoupenPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
        checkCoupenPriceDialog.setCancelable(true);
        checkCoupenPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerView = checkCoupenPriceDialog.findViewById(R.id.toggle_recyclerview);
        coupenRecyclerView = checkCoupenPriceDialog.findViewById(R.id.coupens_recyclerview);
        selectedCoupen = checkCoupenPriceDialog.findViewById(R.id.selected_coupen_container);
        coupenTitle = checkCoupenPriceDialog.findViewById(R.id.coupen_title);
        coupenBody = checkCoupenPriceDialog.findViewById(R.id.coupen_body);
        coupenExpiryDate = checkCoupenPriceDialog.findViewById(R.id.coupen_validity);

        TextView originalPrice = checkCoupenPriceDialog.findViewById(R.id.original_price);
        TextView discountedPrice = checkCoupenPriceDialog.findViewById(R.id.discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coupenRecyclerView.setLayoutManager(layoutManager);
        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Discount Coupen", "till 13th August 2020", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardModelList.add(new RewardModel("Discount ", "till 13th August 2020", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardModelList.add(new RewardModel("Reward", "till 13th August 2020", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardModelList.add(new RewardModel("Discount and Reward", "till 13th August 2020", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));


        MyRewardAdapter myRewardAdapter = new MyRewardAdapter(rewardModelList, true);
        coupenRecyclerView.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });
        //////Coupen Dialog

        coupenRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCoupenPriceDialog.show();

            }
        });


        //signIn Dialog
        signIndialog = new Dialog(ProductDetailsActivity.this);
        signIndialog.setContentView(R.layout.sign_in_dialog);
        signIndialog.setCancelable(true);
        signIndialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signIndialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpButton = signIndialog.findViewById(R.id.sign_up_btn);


        final Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIndialog.dismiss();
                setSignUpFragment = false;
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                startActivity(registerIntent);
            }
        });

        dialogSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIndialog.dismiss();
                setSignUpFragment = true;
                SignUpFragment.disableCloseBtn = true;
                SignInFragment.disableCloseBtn = true;
                startActivity(registerIntent);
            }
        });


    }

    public static void showDialogRecyclerView() {
        if (coupenRecyclerView.getVisibility() == View.GONE) {
            coupenRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);
        } else {
            coupenRecyclerView.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_menu_icon, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            //todo:search
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_cart_icon) {
            if (currentUser == null) {
                signIndialog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
