package com.cenny.cenny;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {
    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch(cartItemModelList.get(position).getType()){
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case CartItemModel.CART_ITEM:
                View cartItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                return  new CartItemViewHolder(cartItemView);
                case CartItemModel.TOTAL_AMOUNT:
                    View cartTotalView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                    return new CartTotalAmountViewHolder(cartTotalView);


            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()){
            case CartItemModel.CART_ITEM:
                int resource=cartItemModelList.get(position).getProductImage();
                String title=cartItemModelList.get(position).getProductTitle();
                int freeCoupans=cartItemModelList.get(position).getFreeCoupens();
                String productPrice=cartItemModelList.get(position).getProductPrice();
                String cuttedPrice=cartItemModelList.get(position).getCuttedPrice();
                int offerApplied=cartItemModelList.get(position).getOfferApplied();

                ((CartItemViewHolder)holder).setItemDetails(resource,title,freeCoupans,productPrice,cuttedPrice,offerApplied);
                break;
                case CartItemModel.TOTAL_AMOUNT:
                    String totalItems=cartItemModelList.get(position).getTotalItems();
                    String totalItemPrice=cartItemModelList.get(position).getTotalItemsPrice();
                    String deliveryPrice=cartItemModelList.get(position).getDeliveryPrice();
                    String totalAmount=cartItemModelList.get(position).getTotalAmount();
                    String savedAmount=cartItemModelList.get(position).getSavedAmount();

                    ((CartTotalAmountViewHolder)holder).setTotalAmount(totalItems,totalItemPrice,deliveryPrice,totalAmount,savedAmount);
                    break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }
    public class CartItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private ImageView freeCoupanIcon;
        private TextView productTitle,productPrice,cuttedPrice,freeCoupen,offerApplied,coupensApplied,productQuantity;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_details_title);
            freeCoupanIcon=itemView.findViewById(R.id.free_coupan_icon);
            freeCoupen=itemView.findViewById(R.id.tv_free_coupan);
            productPrice=itemView.findViewById(R.id.product_price);
            cuttedPrice=itemView.findViewById(R.id.cutted_price);
            offerApplied=itemView.findViewById(R.id.offers_applied);
            coupensApplied=itemView.findViewById(R.id.coupans_applied);
            productQuantity=itemView.findViewById(R.id.product_details_price);
        }
        private void setItemDetails(int resource,String title,int freeCoupensNo,String productPriceText,String cuttedPriceText,int offersAppliedNumber){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if(freeCoupensNo>0){
                freeCoupanIcon.setVisibility(View.VISIBLE);
                freeCoupen.setVisibility(View.VISIBLE);
                if(freeCoupensNo==1){
                    freeCoupen.setText(freeCoupensNo+"free "+"Coupen");
                }else {
                    freeCoupen.setText(freeCoupensNo+"free "+"Coupens");
                }
            }
            else {
                freeCoupanIcon.setVisibility(View.INVISIBLE);
                freeCoupen.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            if(offersAppliedNumber>0){
                if(offersAppliedNumber==1){
                    offerApplied.setVisibility(View.VISIBLE);
                    offerApplied.setText(offersAppliedNumber+" offer applied");
                }else{
                    offerApplied.setVisibility(View.VISIBLE);
                    offerApplied.setText(offersAppliedNumber+" offers applied");
                }
            }else{
                offerApplied.setVisibility(View.INVISIBLE);
            }
            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog quantityDialog=new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    final EditText quantityNo=quantityDialog.findViewById(R.id.quantity_number);
                    Button cancelBtn=quantityDialog.findViewById(R.id.cancel_btn);
                    Button okBtn=quantityDialog.findViewById(R.id.ok_btn);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText("Qty "+ quantityNo.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();

                }
            });

        }
    }
    class CartTotalAmountViewHolder extends RecyclerView.ViewHolder{
        private TextView totalItems,totalItemPrice,deliveryPrice,totalAmount,savedAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems=itemView.findViewById(R.id.total_items);
            totalItemPrice=itemView.findViewById(R.id.total_items_price);
            deliveryPrice=itemView.findViewById(R.id.deivery_price);
            totalAmount=itemView.findViewById(R.id.total_price);
            savedAmount=itemView.findViewById(R.id.saved_amount);
        }
        private void setTotalAmount(String totalItemText,String totalItemPriceText,String deliveryPriceText,String totalAmountText,String savedAmountText){
            totalItems.setText(totalItemText);
            totalItemPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText(savedAmountText);
        }
    }
}
