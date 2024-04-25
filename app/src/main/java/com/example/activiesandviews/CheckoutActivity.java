package com.example.activiesandviews;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.activity.ComponentActivity;
import java.util.*;

// CheckoutActivity ACTIVITY

public class CheckoutActivity extends ComponentActivity {

    private ArrayList<Product> productList;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        userModel = new UserModel(this);
        productList = new ArrayList<>();
        productList.addAll(userModel.getCart()); // Load from UserModel
        setupButtons();
        ListView cartListView = findViewById(R.id.ListView_Cart);
        CartAdapter adapter = new CartAdapter(this, productList);
        cartListView.setAdapter(adapter);
        updateTotals();


        // show total at the bottom
        double totalAmount = calculateTotalAmount(); // Calculate the total amount
        TextView totalTextView = findViewById(R.id.Text_Subtotal);
        totalTextView.setText(String.format("Total: $%.2f", totalAmount));

        // Calculate and display item count
        int itemCount = calculateItemCount();
        TextView itemCountTextView = findViewById(R.id.Text_ItemCount);
        itemCountTextView.setText(String.format("Item Count: %d", itemCount));

        Log.d("CheckoutActivity", "Cart loaded with " + productList.size() + " items.");
    }

    private void setupButtons() {
        Button button = findViewById(R.id.Btn_ProceedCheckout);

        ImageButton backButton = findViewById(R.id.imageButton_Cart_BackButton);
        backButton.setOnClickListener(v -> onBackPressed());

        button.setOnClickListener(view -> {
            if (!productList.isEmpty()) {
                Toast.makeText(CheckoutActivity.this, "Order has been placed", Toast.LENGTH_SHORT).show();
                userModel.clearCart();
                Intent intent = new Intent(CheckoutActivity.this, SearchActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(CheckoutActivity.this, "Cannot checkout with an empty cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        CartPreferences.saveCart(this, productList);  // Save cart items to SharedPreferences when app is paused
    }
    private double calculateTotalAmount() {
        double total = 0.0;
        for (Product product : productList) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }
    private int calculateItemCount() {
        int count = 0;
        for (Product product : productList) {
            count += product.getQuantity();
        }
        return count;
    }
    // Method to update total amount and item count
    private void updateTotals() {
        double totalAmount = calculateTotalAmount(); // Calculate the total amount
        TextView totalTextView = findViewById(R.id.Text_Subtotal);
        totalTextView.setText(String.format("Total: $%.2f", totalAmount));

        // Calculate and display item count
        int itemCount = calculateItemCount();
        TextView itemCountTextView = findViewById(R.id.Text_ItemCount);
        itemCountTextView.setText(String.format("Item Count: %d", itemCount));
    }
    // Method to recalculate and update the total and subtotal when an item is deleted
    public void updateTotalsAfterItemDeleted() {
        // Update the productList with the latest cart items
        productList.clear();
        productList.addAll(userModel.getCart());

        // Update the total and subtotal TextViews
        updateTotals();
    }
}
