package com.cenny.cenny;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductViewLayoutAdapter extends BaseAdapter {
    List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public GridProductViewLayoutAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if(convertView==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            ImageView productImage=view.findViewById(R.id.horizontal_scroll_product_image);
            TextView productTitle=view.findViewById(R.id.horizontal_scroll_product_title);
             TextView productDescription=view.findViewById(R.id.horizontal_scroll_product_description);
            TextView productPrice=view.findViewById(R.id.horizontal_scroll_product_price);

            Glide.with(parent.getContext()).load(horizontalProductScrollModelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.bg_two)).into(productImage);
            productTitle.setText(horizontalProductScrollModelList.get(position).getProductTitle());
            productDescription.setText(horizontalProductScrollModelList.get(position).getProductDescription());
            productPrice.setText("Rs."+horizontalProductScrollModelList.get(position).getProductPrice()+"/-");

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDeatilsIntent=new Intent(parent.getContext(),ProductDetailsActivity.class);
                    productDeatilsIntent.putExtra("PRODUCT_ID",horizontalProductScrollModelList.get(position).getProductID());
                    parent.getContext().startActivity(productDeatilsIntent);
                }
            });


        }else {
            view=convertView;

        }
        return view;
    }
}
