package com.example.activiesandviews;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.ComponentActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class productActivity  extends ComponentActivity{
    private Button subB, addB, searchP, checkoutP;
    public ImageView pImage;
    public TextView quantity, product, price, desc;
    public int count =0;
    private List<Map<String, String>> products = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdesc);

        product = findViewById(R.id.product);
        pImage = findViewById(R.id.pImage);
        price = findViewById(R.id.price);
        subB = findViewById(R.id.subB);
        addB = findViewById(R.id.addB);
        quantity = findViewById(R.id.quantity);
        desc = findViewById(R.id.description);
        searchP = findViewById(R.id.searchP);
        checkoutP = findViewById(R.id.checkoutP);

        readFile();
        setupButtons();
        updateUI(0);
    }

    private void setupButtons(){
        subB.setOnClickListener(this::onSubButtonClick);
        addB.setOnClickListener(this::onAddButtonClick);
        checkoutP.setOnClickListener(this::onCheckoutButtonClick);
        searchP.setOnClickListener(this::onSearchButtonClick);
    }
    private void onSubButtonClick(View v) {
        if (count > 0) {
            count--;
            updateQuantity();
        }
    }
    private void onAddButtonClick(View v) {
        count++;
        updateQuantity();
    }
    private void onCheckoutButtonClick(View v) {
        Intent intent = new Intent(productActivity.this, Product.class);
        startActivity(intent);
    }

    private void onSearchButtonClick(View v) {
        Intent intent = new Intent(productActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void updateQuantity(){
        quantity.setText(String.valueOf(count));
    }
    public void readFile(){
        //needs to be tested

        try {
            InputStream is = getAssets().open("productData.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Map<String, String> productMap = new HashMap<>();
                productMap.put("Product", parts[0]);
                productMap.put("Description", parts[1]);
                productMap.put("Image", parts[2]);
                productMap.put("Category", parts[3]);
                productMap.put("Price", parts[4]);
                products.add(productMap);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUI(int productIndex){
        if (productIndex < products.size()) {
            Map<String, String> productDetails = products.get(productIndex);
            product.setText(productDetails.get("Product"));
            price.setText(productDetails.get("Price"));
            desc.setText(productDetails.get("Description"));
            String imageUrl = productDetails.get("Image");
            Picasso.get().load(imageUrl).into(pImage);
        }
    }
}


