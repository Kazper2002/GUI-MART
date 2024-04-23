package com.example.activiesandviews;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserModel {
    private static final String PREFERENCES_FILE = "UserPreferences";
    private static final String NAME_KEY = "name";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String CART_KEY = "cart";

    private SharedPreferences sharedPreferences;

    public UserModel(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public boolean registerUser(String username, String password, String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(NAME_KEY, name);
        return editor.commit();
    }

    public boolean authenticate(String username, String password) {
        String storedUsername = sharedPreferences.getString(USERNAME_KEY, "");
        String storedPassword = sharedPreferences.getString(PASSWORD_KEY, "");
        return storedUsername.equals(username) && storedPassword.equals(password);
    }

    public String getUserName() {
        return sharedPreferences.getString(USERNAME_KEY, "");
    }

    public String getName() {
        return sharedPreferences.getString(NAME_KEY, "");
    }

    public List<Product> getCart() {
        List<Product> cartItems = new ArrayList<>();
        String cartJson = sharedPreferences.getString(CART_KEY, "");

        if (!cartJson.isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(cartJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject itemJson = jsonArray.getJSONObject(i);
                    String itemName = itemJson.getString("name");
                    double price = itemJson.getDouble("price");
                    int quantity = itemJson.getInt("quantity");
                    cartItems.add(new Product(itemName, price, quantity));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cartItems;
    }

    public void addToCart(String itemName, double price, int quantity) {
        List<Product> cart = getCart();
        boolean found = false;
        for (Product item : cart) {
            if (item.getName().equals(itemName)) {
                item.increaseQuantity(quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            cart.add(new Product(itemName, price, quantity));
        }
        saveCart(cart);
    }
    public void removeFromCart(String itemName) {
        List<Product> cart = getCart();
        for (Iterator<Product> iterator = cart.iterator(); iterator.hasNext();) {
            Product item = iterator.next();
            if (item.getName().equals(itemName)) {
                iterator.remove(); // Remove the item from the cart
                break; // Stop iterating since the item is found and removed
            }
        }
        saveCart(cart); // Save the updated cart to SharedPreferences
    }


    public void clearCart() {
        saveCart(new ArrayList<>());
    }

    private void saveCart(List<Product> cart) {
        JSONArray jsonArray = new JSONArray();
        for (Product item : cart) {
            JSONObject itemJson = new JSONObject();
            try {
                itemJson.put("name", item.getName());
                itemJson.put("price", item.getPrice());
                itemJson.put("quantity", item.getQuantity());
                jsonArray.put(itemJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CART_KEY, jsonArray.toString());
        editor.apply();
    }
}
