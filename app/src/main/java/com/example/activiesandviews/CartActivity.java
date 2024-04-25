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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        UserModel userModel = new UserModel(this);
        productList = new ArrayList<>();
        productList.addAll(userModel.getCart()); // Load from UserModel
        setupButtons();
        ListView cartListView = findViewById(R.id.ListView_Cart);
        CartAdapter adapter = new CartAdapter(this, productList);
        cartListView.setAdapter(adapter);
        Log.d("CartActivity", "Cart loaded with " + productList.size() + " items.");
    }

    private void setupButtons() {
        Button button = findViewById(R.id.Btn_ProceedCheckout);

        ImageButton backButton = findViewById(R.id.imageButton_Cart_BackButton);
        backButton.setOnClickListener(v -> onBackPressed());

        button.setOnClickListener(view -> {
            if (!productList.isEmpty()) {
                //Toast.makeText(CartActivity.this, "Proceed to checkout", Toast.LENGTH_SHORT).show();
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

