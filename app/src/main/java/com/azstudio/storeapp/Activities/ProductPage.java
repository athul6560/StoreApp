package com.azstudio.storeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.azstudio.storeapp.Adapter.SliderAdapter;
import com.azstudio.storeapp.Models.SliderItem;
import com.azstudio.storeapp.R;
import com.azstudio.storeapp.dbHandler.DatabaseHandler;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends AppCompatActivity {
    SliderView sliderView;
    private SliderAdapter adapter;
    private Button l, xl, xxl, xxxl;
    private Button addToCart, buyNow;
    private String size = "";
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        databaseHandler = new DatabaseHandler(this);
        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        TextView price = findViewById(R.id.price);
        buyNow = findViewById(R.id.buynow);
        TextView originalprice = findViewById(R.id.originalprice);
        TextView neck = findViewById(R.id.neck);
        TextView sleeve = findViewById(R.id.sleeve);
        TextView fabric = findViewById(R.id.fabric);
        l = findViewById(R.id.large_btn);
        xl = findViewById(R.id.xlarge_btn);
        xxl = findViewById(R.id.xxlarge_btn);
        xxxl = findViewById(R.id.xxxlarge_btn);
        addToCart = findViewById(R.id.addtocart_btn);
        sizeClicks();
        name.setText(getIntent().getExtras().getString("name"));
        description.setText(getIntent().getExtras().getString("description"));
        price.setText("₹" + getIntent().getExtras().getString("price"));
        originalprice.setText("₹" + getIntent().getExtras().getString("originalprice"));
        originalprice.setPaintFlags(originalprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        neck.setText("Neck Type : " + getIntent().getExtras().getString("neck"));
        sleeve.setText("Sleeve : " + getIntent().getExtras().getString("sleeve"));
        fabric.setText("Fabric : " + getIntent().getExtras().getString("fabric"));


        sliderView = findViewById(R.id.imageSlider);
        adapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        renewItems();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!size.equals("")) {
                    databaseHandler.deleteAll();
                    databaseHandler.addcart(getIntent().getExtras().getString("name"),
                            getIntent().getExtras().getString("price"),
                            getIntent().getExtras().getString("imagea"),
                            getIntent().getExtras().getString("productid"),
                            size);

                   startActivity(new Intent(ProductPage.this,Cart.class));

                } else {
                    Toast.makeText(ProductPage.this, "PLease select your size", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!size.equals("")) {
                    databaseHandler.addcart(getIntent().getExtras().getString("name"),
                            getIntent().getExtras().getString("price"),
                            getIntent().getExtras().getString("imagea"),
                            getIntent().getExtras().getString("productid"),
                            size);
                    Toast.makeText(ProductPage.this, "Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductPage.this, "PLease select your size", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void sizeClicks() {
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size = "l";
                setfocus(view);
                l.setTextColor(getApplication().getResources().getColor(R.color.white));
                xl.setTextColor(getApplication().getResources().getColor(R.color.black));
                xxl.setTextColor(getApplication().getResources().getColor(R.color.black));
                xxxl.setTextColor(getApplication().getResources().getColor(R.color.black));

                xl.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
                xxl.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
                xxxl.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
            }
        });
        xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfocus(view);
                size = "xl";
                xl.setTextColor(getApplication().getResources().getColor(R.color.white));
                l.setTextColor(getApplication().getResources().getColor(R.color.black));
                xxl.setTextColor(getApplication().getResources().getColor(R.color.black));
                xxxl.setTextColor(getApplication().getResources().getColor(R.color.black));

                l.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
                xxl.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
                xxxl.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
            }
        });
        xxl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfocus(view);
                size = "xxl";
                xxl.setTextColor(getApplication().getResources().getColor(R.color.white));
                l.setTextColor(getApplication().getResources().getColor(R.color.black));
                xl.setTextColor(getApplication().getResources().getColor(R.color.black));
                xxxl.setTextColor(getApplication().getResources().getColor(R.color.black));

                l.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
                xl.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
                xxxl.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
            }
        });
        xxxl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfocus(view);
                size = "xxxl";
                xxxl.setTextColor(getApplication().getResources().getColor(R.color.white));
                l.setTextColor(getApplication().getResources().getColor(R.color.black));
                xl.setTextColor(getApplication().getResources().getColor(R.color.black));
                xxl.setTextColor(getApplication().getResources().getColor(R.color.black));

                l.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
                xxl.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));
                xl.setBackground(ContextCompat.getDrawable(ProductPage.this, R.drawable.whitebg_with_border));

            }
        });

    }

    private void setfocus(View view) {
        view.setBackground(ContextCompat.getDrawable(this, R.drawable.roundbutton));

    }

    public void renewItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 1; i < 4; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Image " + i);
            if (i == 0) {
                sliderItem.setImageUrl(getIntent().getExtras().getString("imagea"));
            } else if (i == 1) {
                sliderItem.setImageUrl(getIntent().getExtras().getString("imageb"));
            } else {
                sliderItem.setImageUrl(getIntent().getExtras().getString("imagec"));
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }


}
