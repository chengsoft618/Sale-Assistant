package com.shoniz.saledistributemobility.view.dialog.orderdescription;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.customer.CustomerAddressEntity;

import java.util.List;

public class AddressAdapter extends ArrayAdapter<CustomerAddressEntity> {


    private Context context;
    private List<CustomerAddressEntity> lst;

    public AddressAdapter(@NonNull Context context, int resource, @NonNull List<CustomerAddressEntity> lst) {
        super(context, resource, lst);
        this.context = context;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public CustomerAddressEntity getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_customer_address, parent, false);

        CustomerAddressEntity item = lst.get(position);

        TextView name = listItem.findViewById(R.id.txtAddress);
        name.setText(item.Address);

        TextView release = listItem.findViewById(R.id.txtAddressId);
        release.setText("" + item.AddressID);


        return listItem;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_customer_address, parent, false);

        CustomerAddressEntity item = lst.get(position);

        TextView name = listItem.findViewById(R.id.txtAddress);
        name.setText(item.Address);

        TextView release = listItem.findViewById(R.id.txtAddressId);
        release.setText("" + item.AddressID);


        return listItem;
    }

    public interface CustomerAddressListener {
        void onClick(CustomerAddressEntity entity);
    }

}
