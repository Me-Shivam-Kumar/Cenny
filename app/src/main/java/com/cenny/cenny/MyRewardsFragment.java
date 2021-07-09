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
public class MyRewardsFragment extends Fragment {

    public MyRewardsFragment() {
        // Required empty public constructor
    }
    private RecyclerView myrewardsRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_rewards, container, false);

        myrewardsRecyclerView=view.findViewById(R.id.my_rewards_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myrewardsRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList =new ArrayList<>();
        rewardModelList.add(new RewardModel("Discount Coupen","till 13th August 2020","GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardModelList.add(new RewardModel("Discount Coupen","till 13th August 2020","GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardModelList.add(new RewardModel("Discount Coupen","till 13th August 2020","GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardModelList.add(new RewardModel("Discount Coupen","till 13th August 2020","GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));



        MyRewardAdapter myRewardAdapter=new MyRewardAdapter(rewardModelList,false);
        myrewardsRecyclerView.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();

        return view;
    }
}
