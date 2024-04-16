package com.example.activiesandviews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity; // Correct import for AppCompatActivity
import androidx.appcompat.widget.SearchView; // Correct import for SearchView
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.ComponentActivity; // Add this import for ComponentActivity

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity { // Extend AppCompatActivity

    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<Item> itemList;

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