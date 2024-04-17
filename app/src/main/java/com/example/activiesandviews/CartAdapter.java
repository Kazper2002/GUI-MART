package com.example.activiesandviews;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

// CARTADAPTER RECEIVES CART INFORMATION AND DISPLAYS IT IN LISTVIEW IN CART VIEW
public class CartAdapter extends ArrayAdapter<Product> {
    private Context context;
    private ArrayList<Product> products;

    public CartAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent, false);
        }

        // Lookup view for data population
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        TextView productQuantity = convertView.findViewById(R.id.productQuantity);
        ImageButton deleteButton = convertView.findViewById(R.id.imageButton_Trash);

        // PRODUCT DATA
        Product product = getItem(position);
        productName.setText(product.getName());
        productPrice.setText(String.format("$%.2f", product.getPrice()));
        productQuantity.setText("Qty: " + product.getQuantity());

        deleteButton.setOnClickListener(v -> {
            products.remove(position); // Remove the item from the list
            notifyDataSetChanged();    // Notify the adapter to refresh the ListView
            CartPreferences.saveCart(context, products);  // Update SharedPreferences immediately after removal
        });

        // Return the completed view to render on screen
        return convertView;
    }

}
