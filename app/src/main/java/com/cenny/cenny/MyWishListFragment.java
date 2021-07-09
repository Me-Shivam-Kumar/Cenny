package com.cenny.cenny;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishListFragment extends Fragment {

    public MyWishListFragment() {
        // Required empty public constructor
    }
    private RecyclerView myWishListRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        myWishListRecyclerView=view.findViewById(R.id.mywishlist_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myWishListRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishListModel> wishListModelList=new ArrayList<>();

       /* wishListModelList.add(new WishListModel(R.drawable.common_google_signin_btn_icon_dark,"Google Course",2,"Rs.2999/-","Rs.500/-","Cash on Delivery ONLY"));
        wishListModelList.add(new WishListModel(R.drawable.common_google_signin_btn_icon_dark,"Google Course",1,"Rs.2999/-","Rs.500/-","Cash on Delivery ONLY"));
        wishListModelList.add(new WishListModel(R.drawable.common_google_signin_btn_icon_dark,"Google Course",0,"Rs.2999/-","Rs.500/-","Cash on Delivery ONLY"));

        WishListAdapter wishListAdapter =new WishListAdapter(wishListModelList,true);
        myWishListRecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();*/

        return view;
    }
}
