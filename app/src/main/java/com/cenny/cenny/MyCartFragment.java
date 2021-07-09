package com.cenny.cenny;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {

    public MyCartFragment() {
        // Required empty public constructor
    }
    private RecyclerView cartItemsRecyclerView;
    private Button continueBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_cart, container, false);

        cartItemsRecyclerView=view.findViewById(R.id.cart_items_recycler_view);
        continueBtn=view.findViewById(R.id.cart_continue_btn);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);

        List<CartItemModel> cartItemModelList=new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.common_google_signin_btn_icon_dark,"Google Course",2,"Rs.2999/-","Rs.3500/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.common_google_signin_btn_icon_dark,"Google Course",1,"Rs.2999/-","Rs.3500/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.common_google_signin_btn_icon_dark,"Google Course",0,"Rs.2999/-","Rs.3500/-",1,0,0));
        cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Rs.6000/-","Free","Rs.6000/-","Rs200/-"));

        CartAdapter cartAdapter=new CartAdapter(cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntetn=new Intent(getContext(),AddAddressActivity.class);
                getContext().startActivity(deliveryIntetn);
            }
        });


        return view;
    }
}
