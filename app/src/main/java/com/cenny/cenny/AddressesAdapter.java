package com.cenny.cenny;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.cenny.cenny.DeliveryActivity.SELECT_ADDRESS;
import static com.cenny.cenny.MyAccountFragment.MANAGE_ADDRESS;
import static com.cenny.cenny.MyAddressesActivity.refreshItem;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {
    private List<AddressesModel> addressesModelList;
    private int MODE;
    private int preSelectedPosition=-1;


    public AddressesAdapter(List<AddressesModel> addressesModelList,int MODE) {
        this.addressesModelList = addressesModelList;
        this.MODE=MODE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String username=addressesModelList.get(position).getFullName();
        String useraddress=addressesModelList.get(position).getAddress();
        String userpincode=addressesModelList.get(position).getPincode();
        Boolean selected=addressesModelList.get(position).getSelected();

        holder.setData(username,useraddress,userpincode,selected,position);

    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fullname,address,pincode;
        private ImageView icon;
        private LinearLayout optionsContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            pincode=itemView.findViewById(R.id.pincode);
            icon=itemView.findViewById(R.id.check_icon_view);
            optionsContainer=itemView.findViewById(R.id.options_container);

        }
        private  void setData(String name_text, String address_text, String pincode_text, final Boolean selected, final int position){
            fullname.setText(name_text);
            address.setText(address_text);
            pincode.setText(pincode_text);
            if(MODE==SELECT_ADDRESS){
                icon.setImageResource(R.drawable.ic_check_black_24dp);
                if(selected){
                    preSelectedPosition=position;
                    icon.setVisibility(View.VISIBLE);
                }else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(preSelectedPosition!=position) {
                            addressesModelList.get(position).setSelected(true);
                            addressesModelList.get(preSelectedPosition).setSelected(false);
                            refreshItem(preSelectedPosition, position);
                            preSelectedPosition = position;
                        }
                    }
                });

            }else if(MODE==MANAGE_ADDRESS){
                optionsContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.ic_vertical_dots);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=-1;
                    }
                });


            }
        }
    }
}
