package com.example.activiesandviews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class productActivity extends ComponentActivity {
    private Button subB, addB, backToSearchButton, goToCartButton, addToCartButton;
    public ImageView pImage;
    public TextView quantity, name, price, desc;
    public int count = 0;
    private List<FullProduct> products = new ArrayList<>();
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdesc);

        String itemName = getIntent().getStringExtra("itemName").trim();

        Log.d("ProductActivity", "Item sent over name: " + itemName);

        name = findViewById(R.id.product);
        pImage = findViewById(R.id.pImage);
        price = findViewById(R.id.price);
        subB = findViewById(R.id.subB);
        addB = findViewById(R.id.addB);
        quantity = findViewById(R.id.quantity);
        desc = findViewById(R.id.description);
        backToSearchButton = findViewById(R.id.backToSearchButton);
        goToCartButton = findViewById(R.id.goToCartButton);
        addToCartButton = findViewById(R.id.cartB);

        userModel = new UserModel(this);

        products = readProductsFromCSV();
        setupButtons();
        updateUI(itemName);
    }

    private void setupButtons() {
        subB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    count--;
                    updateQuantity();
                }
            }
        });
        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                updateQuantity();
            }
        });


        // Android Back Button to return to cart
        backToSearchButton.setOnClickListener(v -> onBackPressed());

        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(productActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityToAdd = Integer.parseInt(quantity.getText().toString());
                String itemName = name.getText().toString();
                double itemPrice = Double.parseDouble(price.getText().toString().replace("$", ""));
                userModel.addToCart(itemName, itemPrice, quantityToAdd);

                // Show a Toast message indicating the number of items added to the cart
                String toastMessage = "Quantity of " + quantityToAdd + " " + itemName + " has been added to cart";
                Toast.makeText(productActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateQuantity() {
        quantity.setText(String.valueOf(count));
    }

    private List<FullProduct> readProductsFromCSV() {
        List<FullProduct> productList = new ArrayList<>();
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("productData.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) { // Ensure all required fields are present
                    FullProduct product = new FullProduct(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
                    productList.add(product);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }


    @SuppressLint("SetTextI18n")
    private void updateUI(String itemName) {
        for (FullProduct product : products) {
            if (product.getName().equals(itemName)) {
                // Update the views with product details
                name.setText(itemName);
                price.setText("$" + String.valueOf(product.getPrice()));
                desc.setText(product.getDescription());

                // Load image from the drawable folder using Picasso
                int imageResource = getResources().getIdentifier(product.getImage(), "drawable", getPackageName());
                Picasso.get().load(imageResource).into(pImage);
                break; // Exit the loop once the product is found
            }
        }
    }

}