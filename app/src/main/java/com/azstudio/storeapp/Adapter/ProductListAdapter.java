package com.azstudio.storeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.azstudio.storeapp.Activities.ProductPage;
import com.azstudio.storeapp.Activities.profile;
import com.azstudio.storeapp.Models.ProductListModel;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.Utils.constants;
import com.azstudio.storeapp.dbHandler.DatabaseHandler;
import com.squareup.picasso.Picasso;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private ProductListModel mDataset;
    private Context con;
    private RelativeLayout dot;
    private TextView dottext;
    SharedPreferences hasLoggedIn;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView price;
        public TextView originalprice;
        public TextView title;
        public TextView description;
        public ImageView imageview;
     public LinearLayout linearLayout;

        public MyViewHolder(View v) {
            super(v);
            price = (TextView) itemView.findViewById(R.id.price);
            originalprice = (TextView) itemView.findViewById(R.id.originalprice);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            imageview = (ImageView) itemView.findViewById(R.id.iw);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.ll);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductListAdapter(ProductListModel myDataset, Context context, RelativeLayout dot, TextView dottext, SharedPreferences hasLoggedIn) {
        mDataset = myDataset;
        con = context;
        this.dot=dot;
        this.dottext=dottext;
        this.hasLoggedIn=hasLoggedIn;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_recycler_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        System.out.println("dvsdv"+mDataset.getProducts().get(position).getProduct_id());
        holder.price.setText("₹" + String.valueOf(mDataset.getProducts().get(position).getRate()));
        holder.originalprice.setText("₹" + String.valueOf(mDataset.getProducts().get(position).getOriginal_rate()));
        holder.originalprice.setPaintFlags(holder.originalprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.title.setText(mDataset.getProducts().get(position).getProduct_name());
        holder.description.setText(mDataset.getProducts().get(position).getDetails());
        Picasso.get().load(mDataset.getProducts().get(position).getImagea()).into(holder.imageview);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean dvs = hasLoggedIn.getBoolean("hasLoggedIn", false);
if(dvs) {
    startdetailsPage(position);
}else {

    con.startActivity(new Intent(con, profile.class));
}

            }
        });



    }

    private void startdetailsPage(int position) {
        Intent i = new Intent(con, ProductPage.class);
        i.putExtra("name", mDataset.getProducts().get(position).getProduct_name());
        i.putExtra("description", mDataset.getProducts().get(position).getDetails());
        i.putExtra("originalprice", "" + mDataset.getProducts().get(position).getOriginal_rate());
        i.putExtra("price", "" + mDataset.getProducts().get(position).getRate());
        i.putExtra("neck", mDataset.getProducts().get(position).getNeck_type());
        i.putExtra("sleeve", mDataset.getProducts().get(position).getSleeve());
        i.putExtra("fabric", mDataset.getProducts().get(position).getFabric());
        i.putExtra("imagea", mDataset.getProducts().get(position).getImagea());
        i.putExtra("imageb", mDataset.getProducts().get(position).getImageb());
        i.putExtra("imagec", mDataset.getProducts().get(position).getImagec());
        i.putExtra("productid", mDataset.getProducts().get(position).getTitle());
        con.startActivity(i);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.getProducts().size();
    }
}

