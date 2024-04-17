package com.example.activiesandviews;
import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;

public class CartPreferences {
    private static final String PREFS_NAME = "cart_prefs";
    private static final String CART_KEY = "cart";

    public static void saveCart(Context context, ArrayList<Product> cart) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray();
        for (Product product : cart) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", product.getName());
                jsonObject.put("price", product.getPrice());
                jsonObject.put("quantity", product.getQuantity());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString(CART_KEY, jsonArray.toString());
        editor.apply();
    }

    public static ArrayList<Product> loadCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_KEY, null);
        ArrayList<Product> products = new ArrayList<>();
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    double price = jsonObject.getDouble("price");
                    int quantity = jsonObject.getInt("quantity");
                    Product product = new Product(name, price, quantity);
                    products.add(product);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
}
