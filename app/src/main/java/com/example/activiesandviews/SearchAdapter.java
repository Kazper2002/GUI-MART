package com.example.activiesandviews;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
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
    private static final String TAG = "SearchAdapter";
    private List<Item> items;
    private List<Item> itemsFiltered;
    private Context context;

    private RequestOptions requestOptions;

    public SearchAdapter(Context context, String filename) {
        this.context = context;
        items = new ArrayList<>();
        itemsFiltered = new ArrayList<>();
        loadData(filename);

        // Initialize RequestOptions
        requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder);
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
                    String imageName = parts[2].trim(); // Image name is at index 2
                    try {
                        double price = Double.parseDouble(parts[4].trim()); // Price is at index 4
                        items.add(new Item(name, price, imageName));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Invalid price format in row: " + line);
                    }
                }
            }
            br.close();
            itemsFiltered.addAll(items); // Copy items to filtered list initially
            Log.d(TAG, "Data loaded successfully. Total items: " + items.size());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error loading data: " + e.getMessage());
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
        loadImageIntoImageView(item.getImageName(), holder.imageView);
    }



    private void loadImageIntoImageView(String imageName, ImageView imageView) {
        int imageResource = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (imageResource != 0) {
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(imageResource)
                    .into(imageView);
        } else {
            Log.e(TAG, "Image resource not found for item. Image name: " + imageName);
        }
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
                if (results.values instanceof List<?>) {
                    itemsFiltered.addAll((List<Item>) results.values); // Update filtered items
                    notifyDataSetChanged(); // Notify adapter about data changes
                    Log.d(TAG, "Filtered items: " + itemsFiltered.size());
                } else {
                    Log.e(TAG, "Filtering results are not of type List<Item>");
                }
            }

        };
    }
}
