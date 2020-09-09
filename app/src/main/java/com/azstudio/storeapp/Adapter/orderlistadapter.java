package com.azstudio.storeapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azstudio.storeapp.Activities.MyAccount;
import com.azstudio.storeapp.Models.Product;
import com.azstudio.storeapp.Models.cartModel;
import com.azstudio.storeapp.Models.orderDetails;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.Utils.ProgressDialogue;
import com.azstudio.storeapp.ViewModel.ProductViewModel;
import com.azstudio.storeapp.dbHandler.DatabaseHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

public class orderlistadapter extends RecyclerView.Adapter<orderlistadapter.MyViewHolder> {
    private List<orderDetails> mDataset;
    private Context con;

    private DatabaseHandler databaseHandler;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {


        // each data item is ju public ImageView image;
        public TextView address;
        public TextView status;
        public TextView productid;
        public TextView size, name, price;
        public ImageView receivedimage, shirtimage;

        public MyViewHolder(View v) {
            super(v);

            address = (TextView) itemView.findViewById(R.id.address);
            status = (TextView) itemView.findViewById(R.id.status);
            productid = (TextView) itemView.findViewById(R.id.productid);
            size = (TextView) itemView.findViewById(R.id.size);
            price = (TextView) itemView.findViewById(R.id.price);
            name = (TextView) itemView.findViewById(R.id.name);
            receivedimage = (ImageView) itemView.findViewById(R.id.receivedimage);
            shirtimage = (ImageView) itemView.findViewById(R.id.shirtimage);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public orderlistadapter(List<orderDetails> myDataset, Context context) {

        mDataset = myDataset;
        con = context;
        databaseHandler = new DatabaseHandler(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public orderlistadapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_recycler_item, parent, false);

        orderlistadapter.MyViewHolder vh = new orderlistadapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(orderlistadapter.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(mDataset.get(position).getName()+" ("+mDataset.get(position).getPin_number()+" )");
        holder.price.setText("₹"+mDataset.get(position).getRate());
        holder.address.setText(mDataset.get(position).getAddress());
        holder.productid.setText(mDataset.get(position).getProduct_id());
        holder.status.setText(mDataset.get(position).getOrder_status());
        holder.size.setText(mDataset.get(position).getPin_number());
        Picasso.get().load(mDataset.get(position).getImagea()).into(holder.shirtimage);
        if (mDataset.get(position).getOrder_status().equals("pending")) {
            holder.receivedimage.setImageResource(R.drawable.ic_baseline_check_circle_24);
        }


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}