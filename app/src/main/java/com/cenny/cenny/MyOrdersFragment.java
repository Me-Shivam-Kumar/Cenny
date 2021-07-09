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
public class MyOrdersFragment extends Fragment {
    private RecyclerView myOrderRecyclerView;

    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_orders, container, false);
        myOrderRecyclerView=view.findViewById(R.id.my_orders_recyclerview);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerView.setLayoutManager(layoutManager);

        List<MyOrdersItemsModel> myOrdersItemsModelList=new ArrayList<>();
        myOrdersItemsModelList.add(new MyOrdersItemsModel(R.drawable.common_google_signin_btn_icon_dark,"Google Course","Cancelled"));
        myOrdersItemsModelList.add(new MyOrdersItemsModel(R.drawable.common_google_signin_btn_icon_dark,"Google Course","To be deliverd on Mon,12 FEB 2020"));

        MyOrderAdapter myOrderAdapter=new MyOrderAdapter(myOrdersItemsModelList);
        myOrderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();






        return view;
    }

}
