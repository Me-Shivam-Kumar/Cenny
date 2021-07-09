
package com.cenny.cenny;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBQueries {
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<String>();
    public static List<String> wishList=new ArrayList<>();




    public static void loadCategories(final Context context, final CategoryAdapter categoryAdapter) {

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public static void loadFragmentData(final Context context, final HomePageAdapter adapter, final int index, String categoryName) {
        firebaseFirestore.collection("CATEGORIES").document(categoryName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if ((long) documentSnapshot.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<SliderModel>();
                                    long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                                    for (long i = 1; i < no_of_banners + 1; i++) {
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + i).toString(), documentSnapshot.get("banner_" + i + "_background").toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(0, sliderModelList));

                                } else if ((long) documentSnapshot.get("view_type") == 1) {

                                    List<WishListModel> viewAllProductList = new ArrayList<>();
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long i = 1; i < no_of_products + 1; i++) {
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + i).toString(),
                                                documentSnapshot.get("product_image_" + i).toString(), documentSnapshot.get("product_title_" + i).toString(),
                                                documentSnapshot.get("product_subtitle_" + i).toString(),
                                                documentSnapshot.get("product_price_" + i).toString()));

                                        viewAllProductList.add(new WishListModel((long) documentSnapshot.get("free_coupens_" + i), documentSnapshot.get("product_image_" + i).toString(), documentSnapshot.get("average_rating_" + i).toString(),
                                                (long) documentSnapshot.get("total_ratings_" + i), documentSnapshot.get("product_full_title_" + i).toString(), documentSnapshot.get("product_price_" + i).toString(),
                                                documentSnapshot.get("cutted_price_" + i).toString(), (boolean) documentSnapshot.get("cod_" + i)
                                        ));
                                    }

                                    lists.get(index).add(new HomePageModel(1, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), horizontalProductScrollModelList,viewAllProductList));

                                } else if ((long) documentSnapshot.get("view_type") == 2) {
                                    List<HorizontalProductScrollModel> gridProductModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long i = 1; i < no_of_products + 1; i++) {
                                        gridProductModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + i).toString(),
                                                documentSnapshot.get("product_image_" + i).toString(), documentSnapshot.get("product_title_" + i).toString(),
                                                documentSnapshot.get("product_subtitle_" + i).toString(),
                                                documentSnapshot.get("product_price_" + i).toString()));

                                    }

                                    lists.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), gridProductModelList));
                                    HomeFragment.swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public static void loadWishList(final Context context, final Dialog dialog){
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    for(long i=0;i< (long)task.getResult().get("list_size");i++){
                        wishList.add(task.getResult().get("product_ID_"+i).toString());
                    }
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }
}

