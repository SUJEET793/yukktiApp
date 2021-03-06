package com.me.Orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.me.R;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order_list> {

    Context context;
    int resource;

    public OrderListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Order_list> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)  {
        String name = getItem(position).name;
        String price = getItem(position).price;
        String quantity = getItem(position).quantity;
        String total_price=getItem(position).total_price;

        Order_list order_list = new Order_list(name,price,quantity,total_price);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);

        TextView nameTextview = (TextView)convertView.findViewById(R.id.order_list_item_name);
        TextView priceTextView = (TextView)convertView.findViewById(R.id.order_list_item_price);
        TextView quantityTextView = (TextView)convertView.findViewById(R.id.order_list_item_quantity);
        TextView total_priceTextView=convertView.findViewById(R.id.total_per_price);
        try {
            nameTextview.setText(name);
            priceTextView.setText(price);
            quantityTextView.setText(quantity);
            total_priceTextView.setText(""+total_price);
        }catch (Exception e){}


        return convertView;
    }
}
