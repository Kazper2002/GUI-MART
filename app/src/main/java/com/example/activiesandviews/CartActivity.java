package com.example.activiesandviews;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.activity.ComponentActivity;
import java.util.*;

// CART ACTIVITY

public class CartActivity extends ComponentActivity {

    private ArrayList<Product> productList;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        productList = CartPreferences.loadCart(this);
        setupButtons();
        ListView cartListView = findViewById(R.id.ListView_Cart);
        adapter = new CartAdapter(this, productList);
        cartListView.setAdapter(adapter);
        if (productList.isEmpty()) {                // SAMPLE CART ITEMS - FOR TESTING
            addSampleItem("Spinach", 2.29, 3);
            addSampleItem("Apple", 1.59, 9);
            addSampleItem("Eggs", 7.60, 1);
            addSampleItem("Bread", 3.20, 1);
            addSampleItem("Milk", 5.66, 1);
        }
        Log.d("CartActivity", "Cart loaded with " + productList.size() + " items.");
    }

    // FOR TESTING
    private void addSampleItem(String name, double price, int quantity) {
        Product sampleProduct = new Product(name, price, quantity);
        productList.add(sampleProduct);
        CartPreferences.saveCart(this, productList);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void setupButtons() {
        Button button = findViewById(R.id.Btn_ProceedCheckout);

        ImageButton backButton = findViewById(R.id.imageButton_Cart_BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                onBackPressed();  // Calls the default back button action
            }
        });

        button.setOnClickListener(view -> {
            if (!productList.isEmpty()) {
                Toast.makeText(CartActivity.this, "Proceed to checkout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(CartActivity.this, "Cannot checkout with an empty cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        CartPreferences.saveCart(this, productList);  // Save cart items to SharedPreferences when app is paused
    }
}

