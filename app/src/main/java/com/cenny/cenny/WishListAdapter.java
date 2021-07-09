package com.cenny.cenny;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
    private List<WishListModel> wishListModelList;
    private Boolean wishlist;

    public WishListAdapter(List<WishListModel> wishListModelList,Boolean wishlist) {
        this.wishListModelList = wishListModelList;
        this.wishlist=wishlist;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
        String resource=wishListModelList.get(position).getProductImage();
        String title=wishListModelList.get(position).getProductTitle();
        long freeCoupens=wishListModelList.get(position).getFreeCoupens();
        String productPrice=wishListModelList.get(position).getProductPrice();
        String cuttedPrice=wishListModelList.get(position).getCuttedPrice();
        boolean payMethod=wishListModelList.get(position).isCod();

        holder.setData(resource,title,freeCoupens,productPrice,cuttedPrice,payMethod);

    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle,freeCoupens;
        private ImageView coupenIcon;
        private View cutted_price_divider;
        private ImageButton deleteBtn;
        private TextView productPrice,cuttedPrice,paymentMethod;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_title);
            freeCoupens=itemView.findViewById(R.id.free_coupen);
            coupenIcon=itemView.findViewById(R.id.product_details_title);
            cutted_price_divider=itemView.findViewById(R.id.cutted_price_divider);
            deleteBtn=itemView.findViewById(R.id.delete_btn);
            productPrice=itemView.findViewById(R.id.product_price);
            cuttedPrice=itemView.findViewById(R.id.cutted_price);
            paymentMethod=itemView.findViewById(R.id.payment_method);
        }
        public void setData(String resource, String title, long freeCoupensNo, String price, String cutted_price_text, boolean payMethod){
            productTitle.setText(title);
            Glide.with(itemView.getContext()).load(resource).into(productImage);
            if(freeCoupensNo!=0){
                coupenIcon.setVisibility(View.VISIBLE);
                if(freeCoupensNo==1) {
                    freeCoupens.setText(freeCoupensNo + " free Coupen ");
                }else {
                    freeCoupens.setText(freeCoupensNo + " free Coupens ");
                }
            }else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(price);
            cuttedPrice.setText(cutted_price_text);
            paymentMethod.setText("YES");

            if(wishlist) {
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Delete Button Pressed", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
    }
}
