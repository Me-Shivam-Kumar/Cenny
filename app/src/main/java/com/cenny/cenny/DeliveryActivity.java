package com.cenny.cenny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddNewAddress;
    public static final int SELECT_ADDRESS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");


        deliveryRecyclerView=findViewById(R.id.delivery_recyclerview);
        changeOrAddNewAddress=findViewById(R.id.change_or_add_address_btn);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);

        List<CartItemModel> cartItemModelList=new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.common_google_signin_btn_icon_dark,"Google Course",2,"Rs.2999/-","Rs.3500/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.common_google_signin_btn_icon_dark,"Google Course",1,"Rs.2999/-","Rs.3500/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.common_google_signin_btn_icon_dark,"Google Course",0,"Rs.2999/-","Rs.3500/-",1,0,0));
        cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Rs.6000/-","Free","Rs.6000/-","Rs200/-"));

        CartAdapter cartAdapter=new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddress.setVisibility(View.VISIBLE);
        changeOrAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myaddressesIntent=new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                myaddressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myaddressesIntent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
