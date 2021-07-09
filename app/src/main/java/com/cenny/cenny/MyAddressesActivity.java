package com.cenny.cenny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.cenny.cenny.DeliveryActivity.SELECT_ADDRESS;


public class MyAddressesActivity extends AppCompatActivity {
    private RecyclerView myAddressesRecyclerView;
    private static AddressesAdapter addressesAdapter;
    private Button deliverHereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deliverHereBtn=findViewById(R.id.deliver_here_btn);

        myAddressesRecyclerView=findViewById(R.id.addresses_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(MyAddressesActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);

        List<AddressesModel> addressesModelList=new ArrayList<>();
        addressesModelList.add(new AddressesModel("Shivam","Jandli","133001",true));
        addressesModelList.add(new AddressesModel("Shivam Kumar","Jandli,Kanwala","133001",false));
        addressesModelList.add(new AddressesModel("Shivam","Jandli,Ambala Cantt","133001",false));
        addressesModelList.add(new AddressesModel("Shivam","Jandli,Ambala Cantt","133001",false));
        addressesModelList.add(new AddressesModel("Shivam","Jandli,Ambala Cantt","133001",false));
        addressesModelList.add(new AddressesModel("Shivam","Jandli,Ambala Cantt","133001",false));
        addressesModelList.add(new AddressesModel("Shivam","Jandli,Ambala Cantt","133001",false));

        int mode=getIntent().getIntExtra("MODE",-1);
        if(mode==SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else {
            deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdapter=new AddressesAdapter(addressesModelList,mode);
        myAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();


    }
    public static void refreshItem(int deselect,int select){
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
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
