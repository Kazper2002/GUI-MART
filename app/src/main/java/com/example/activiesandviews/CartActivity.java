package com.example.activiesandviews;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.ComponentActivity;

import java.io.*;
import java.util.*;

// CART ACTIVITY

public class CartActivity extends ComponentActivity {

    private Button button;
    private AssetManager assets;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        assets = getAssets();
        setupButtons();
        ListView cartListView = findViewById(R.id.ListView_Cart);
        ArrayList<Product> productList = readCartItems();   // ARRAYLIST OF PRODUCTS CURRENTLY IN CART
        CartAdapter adapter = new CartAdapter(this, productList); // ADAPTER TO RECIEVE PRODUCT ARRAYLIST
        cartListView.setAdapter(adapter);   // SEND ARRAYLIST TO POPULATE LISTVIEW
    }

    // CHECKOUT BUTTON - PROCEED TO CHECKOUT
    private void setupButtons() {
        //button = (Button) findViewById(R.id.Btn_ProceedCheckout);
        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                // TODO DO NOT ALLOW TO PROCEED TO CHECKOUT IF CART IS EMPTY
                if() {      // IF LIST VIEW NOT EMPTY
                    Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                }
                else {      // CART EMPTY
                    uText.setError("Cart is empty");
                }
            }
        });
         */
    }

    // TODO IMPLEMENT THE REAL PRODUCT DATA WHEN SEARCH IS FINISHED
    // QUERIES CART.TXT FOR DATA, GENERATES PRODUCTS, AND RETURNS AN ARRAYLIST OF PRODUCT OBJECTS
    private ArrayList<Product> readCartItems() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            InputStream is = getAssets().open("cart.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    products.add(new Product(name, price, quantity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}