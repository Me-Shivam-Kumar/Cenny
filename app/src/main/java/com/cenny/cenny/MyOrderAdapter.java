package com.cenny.cenny;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private List<MyOrdersItemsModel> myOrdersItemsModelList;

    public MyOrderAdapter(List<MyOrdersItemsModel> myOrdersItemsModelList) {
        this.myOrdersItemsModelList = myOrdersItemsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource=myOrdersItemsModelList.get(position).getProductImage();
        String productTitle=myOrdersItemsModelList.get(position).getProductTitle();
        String deliveryDate=myOrdersItemsModelList.get(position).getDeliveryStatus();

        holder.setData(resource,productTitle,deliveryDate);


    }

    @Override
    public int getItemCount() {
        return myOrdersItemsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage,orderIndicator;
        private TextView productTitle,deliveryStatus;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            orderIndicator=itemView.findViewById(R.id.order_indicator);
            productTitle=itemView.findViewById(R.id.product_details_title);
            deliveryStatus=itemView.findViewById(R.id.order_deliver_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent=new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });

        }
        private void setData(int resource,String title,String deliveryDate){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if(deliveryDate.equals("Cancelled")){
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.failRed)));
            }else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.successGreen)));
            }
            deliveryStatus.setText(deliveryDate);
        }
    }
}
