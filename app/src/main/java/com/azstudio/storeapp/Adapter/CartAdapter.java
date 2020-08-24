package com.azstudio.storeapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.azstudio.storeapp.Models.cartModel;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.dbHandler.DatabaseHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private List<cartModel> mDataset;
    private Context con;
    private Button place;
    public ImageView empty;
    private DatabaseHandler databaseHandler;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;
        public TextView name;
        public TextView price;
        public ImageView del;

        public MyViewHolder(View v) {
            super(v);

            price = (TextView) itemView.findViewById(R.id.price);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.imageshirt);
            del = (ImageView) itemView.findViewById(R.id.del);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CartAdapter(List<cartModel> myDataset, Context context, Button placeOrder, ImageView emptycart) {
        place = placeOrder;
        mDataset = myDataset;
        con = context;
        empty=emptycart;
        databaseHandler = new DatabaseHandler(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartlistitem, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.price.setText("₹" + mDataset.get(position).getPrice());
        holder.name.setText(mDataset.get(position).getName());
        Picasso.get().load(mDataset.get(position).getImage()).into(holder.image);
        place.setText("Place order (₹" + calculatesum() + ")");
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(con, ""+mDataset.get(position).getId(), Toast.LENGTH_SHORT).show();

                databaseHandler.delete(mDataset.get(position).getId());
                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
                place.setText("Place order (₹" + calculatesum() + ")");
                if(databaseHandler.getAllDetails().size()==0){
                    empty.setVisibility(View.VISIBLE);
                    place.setVisibility(View.GONE);
                }

            }
        });


    }

    private int calculatesum() {
        List<cartModel> listofitem = databaseHandler.getAllDetails();
        int sum = 0;
        for (int i = 0; i < listofitem.size(); i++) {
            sum = sum + Integer.parseInt(listofitem.get(i).getPrice());
        }
        return sum;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}