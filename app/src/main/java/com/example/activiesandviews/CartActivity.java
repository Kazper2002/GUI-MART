package com.example.activiesandviews;
import android.os.Bundle;
import android.widget.*;
import androidx.activity.ComponentActivity;
import java.io.*;
import java.util.*;

// CART ACTIVITY

public class CartActivity extends ComponentActivity {

    private ArrayList<Product> productList; // List of all products currently in cart
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        productList = readCartItems();  // Read cart items into productList
        setupButtons();
        ListView cartListView = findViewById(R.id.ListView_Cart);
        CartAdapter adapter = new CartAdapter(this, productList); // ADAPTER TO RECIEVE PRODUCT ARRAYLIST
        cartListView.setAdapter(adapter);   // SEND ARRAYLIST TO POPULATE LISTVIEW
    }

    // CHECKOUT BUTTON - PROCEED TO CHECKOUT
    private void setupButtons() {
        Button button = findViewById(R.id.Btn_ProceedCheckout);  // Button Init

        button.setOnClickListener(view -> {
            if (!productList.isEmpty()) {
                //Intent intent = new Intent(CartActivity.this, CheckoutActivity.class); // TODO CONNECT TO CHECKOUT VIEW WHEN IMPLEMENTED
                //startActivity(intent);
            } else {
                Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
            }
        });
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