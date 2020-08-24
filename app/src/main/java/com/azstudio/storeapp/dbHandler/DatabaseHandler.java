package com.azstudio.storeapp.dbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.azstudio.storeapp.Models.cartModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Product_Details";
    private static final String TABLE_CART = "CartTable";
    private static final String SHIRT_NAME = "name";
    private static final String PRICE = "price";
    private static final String IMAGE = "image";
    private static final String ID = "id";
    private static final String PRODUCTID = "productid";
    private static final String SIZE = "size";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + SHIRT_NAME + " TEXT,"
                + PRICE + " TEXT,"
                + IMAGE + " TEXT,"
                + PRODUCTID + " TEXT,"
                + SIZE + " TEXT,"
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                + ")";
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(TABLE_CART, ID + "=" + id, null);
        db.close();
    }

    public void addcart(String name,
                        String price,
                        String image,
                        String productid,
                        String size

    ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SHIRT_NAME, name);
        values.put(PRICE, price);
        values.put(IMAGE, image);
        values.put(PRODUCTID, productid);
        values.put(SIZE, size);
        // Contact Phone

        // Inserting Row
        db.insert(TABLE_CART, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_CART);
        db.close();
    }
    public List<cartModel> getAllDetails() {
        List<cartModel> contactList = new ArrayList<cartModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                cartModel contact = new cartModel();
                contact.setName(cursor.getString(0));
                contact.setPrice(cursor.getString(1));
                contact.setImage(cursor.getString(2));
                contact.setProductid(cursor.getString(3));
                contact.setSize(cursor.getString(4));
                contact.setId(cursor.getInt(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list

        return contactList;
    }
}
