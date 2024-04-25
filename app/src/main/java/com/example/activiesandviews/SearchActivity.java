package com.example.activiesandviews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<Item> itemList;
    private UserModel userModel;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        adapter = new SearchAdapter(this, "productData.csv");
        recyclerView.setAdapter(adapter);

        setupSearchView();

        // Initialize cart button and set click listener
        Button cartButton = findViewById(R.id.cart_button);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CartActivity when the cart button is clicked
                Intent intent = new Intent(SearchActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        // Initialize Logout Button and set listener
        Button LogOutButton = findViewById(R.id.logout_button);
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CartActivity when the cart button is clicked
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //get access to user information
        userModel = new UserModel(this);

        /**
         * Set up welcome text
         */
        SearchView searchBar = findViewById(R.id.search_view);
        String usersName = userModel.getName();
        searchBar.setQueryHint("Hi " + usersName + ", start your search here!");

    }

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

    }
}
