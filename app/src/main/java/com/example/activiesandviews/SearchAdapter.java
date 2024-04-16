package com.example.activiesandviews;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
    private List<Item> items;
    private List<Item> itemsFiltered;
    private Context context;

    public SearchAdapter(Context context, String filename) {
        this.context = context;
        items = new ArrayList<>();
        itemsFiltered = new ArrayList<>();
        loadData(filename);
    }

    private void loadData(String filename) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Check if there are at least 3 parts
                    String name = parts[0].trim();
                    String imageUrl = parts[2].trim(); // Image URL is at index 2
                    double price = Double.parseDouble(parts[4].trim()); // Price is at index 4
                    items.add(new Item(name, price, imageUrl));
                }
            }
            br.close();
            itemsFiltered.addAll(items); // Copy items to filtered list initially
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Item item = itemsFiltered.get(position);
        holder.nameTextView.setText(item.getName());
        holder.priceTextView.setText(String.valueOf(item.getPrice()));
        loadImageIntoImageView(item.getImg(), holder.imageView);
    }

    private void loadImageIntoImageView(String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder) 
                .error(R.drawable.placeholder);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return itemsFiltered.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;
        ImageView imageView;

        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();
                List<Item> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(items); // If query is empty, return all items
                } else {
                    for (Item item : items) {
                        if (item.getName().toLowerCase().contains(query)) {
                            filteredList.add(item); // If item matches the query, add it to filtered list
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsFiltered.clear();
                itemsFiltered.addAll((List<Item>) results.values); // Update filtered items
                notifyDataSetChanged(); // Notify adapter about data changes
            }
        };
    }
}
