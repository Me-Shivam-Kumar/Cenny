package com.cenny.cenny;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRewardAdapter extends RecyclerView.Adapter<MyRewardAdapter.ViewHolder> {
    private List<RewardModel> rewardModelList;
    private Boolean useMiniLayout=false;

    public MyRewardAdapter(List<RewardModel> rewardModelList,Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout=useMiniLayout;
    }

    @NonNull
    @Override
    public MyRewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(useMiniLayout){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layouts, parent, false);


        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout, parent, false);

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRewardAdapter.ViewHolder holder, int position) {
        String title=rewardModelList.get(position).getTitle();
        String expiryDateText =rewardModelList.get(position).getExpiryDate();
        String coupenBodyText=rewardModelList.get(position).getCoupenBody();

        holder.setData(title,expiryDateText,coupenBodyText);

    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rewardTitle,expiryDate,coupenBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardTitle=itemView.findViewById(R.id.coupen_title);
            expiryDate=itemView.findViewById(R.id.coupen_validity);
            coupenBody=itemView.findViewById(R.id.coupen_body);
        }
        private void setData(final String title, final String expiryDateText, final String coupenBodyText){
            rewardTitle.setText(title);
            expiryDate.setText(expiryDateText);
            coupenBody.setText(coupenBodyText);

            if(useMiniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailsActivity.coupenTitle.setText(title);
                        ProductDetailsActivity.coupenBody.setText(coupenBodyText);
                        ProductDetailsActivity.coupenExpiryDate.setText(expiryDateText);
                        ProductDetailsActivity.showDialogRecyclerView();

                    }
                });
            }
        }
    }
}
