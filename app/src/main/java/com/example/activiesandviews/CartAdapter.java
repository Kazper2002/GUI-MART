package com.example.activiesandviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// CARTADAPTER RECEIVES CART INFORMATION AND DISPLAYS IT IN LISTVIEW IN CART VIEW
public class CartAdapter extends ArrayAdapter<Product> {
    public CartAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
    }

    // INFLATE CART_ITEM.XML VIEW ONTO LISTVIEW IN CART.XML
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent, false);
        }
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        TextView productQuantity = convertView.findViewById(R.id.productQuantity); // New TextView for quantity

        productName.setText(product.getName());
        productPrice.setText(String.format("$%.2f", product.getPrice()));
        productQuantity.setText("Qty: " + product.getQuantity()); // Set the quantity text
        return convertView;
    }

}