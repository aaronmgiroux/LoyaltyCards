package com.amgprogramming.loyaltycards;

import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LoyaltyCards.db";
    public static final int DATABASE_VERSION = 2;
    public static final String CARDS_TABLE_NAME = "cards";
    public static final String CARDS_COLUMN_ID = "id";
    public static final String CARDS_COLUMN_NAME = "name";
    public static final String CARDS_COLUMN_NUMBER = "number";
    public static final String CARDS_COLUMN_CATEGORY = "category";
    public static final String CARDS_COLUMN_FORMAT = "format";
    public static final String CARDS_COLUMN_IMAGE = "image";
    ArrayList<Item> items = new ArrayList();
    Item card;
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        Log.d("DBHelper", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try {
            db.execSQL("CREATE TABLE " + CARDS_TABLE_NAME + " (" +
                            CARDS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            CARDS_COLUMN_NAME + " VARCHAR(255), " +
                            CARDS_COLUMN_NUMBER + " VARCHAR(255), " +
                            CARDS_COLUMN_CATEGORY + " VARCHAR(255), " +
                            CARDS_COLUMN_FORMAT + " VARCHAR(255), " +
                            CARDS_COLUMN_IMAGE + " VARCHAR(255));"
            );
            Log.d("DBHelper", "Table Created");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        try{
            db.execSQL("DROP TABLE IF EXISTS " + CARDS_TABLE_NAME);
            onCreate(db);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public long insertCard  (String name, String number, String category, String format, String image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("number", number);
        contentValues.put("category", category);
        contentValues.put("format", format);
        contentValues.put("image", image);
        long result = db.insert(CARDS_TABLE_NAME, null, contentValues);
        Log.d("DBHelper", "Entry Inserted into table");
        return result;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from cards where id=" + id + "", null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CARDS_TABLE_NAME);
        return numRows;
    }

    public long updateCard (Integer id, String name, String number, String category, String format, String image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("number", number);
        contentValues.put("category", category);
        contentValues.put("format", format);
        contentValues.put("image", image);
        long result = db.update(CARDS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return result;
    }

    public Integer deleteCard (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cards", "id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<Item> getAllCards()
    {
        //ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + CARDS_TABLE_NAME + " ORDER BY " + CARDS_COLUMN_NAME + " COLLATE NOCASE", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            card = new Item();
            card.setItemId(res.getInt(res.getColumnIndex(CARDS_COLUMN_ID)));
            card.setItemName(res.getString(res.getColumnIndex(CARDS_COLUMN_NAME)));
            card.setItemCategory(res.getString(res.getColumnIndex(CARDS_COLUMN_CATEGORY)));
            card.setItemFormat(res.getString(res.getColumnIndex(CARDS_COLUMN_FORMAT)));
            card.setItemNumber(res.getString(res.getColumnIndex(CARDS_COLUMN_NUMBER)));
            items.add(card);
            res.moveToNext();
        }
        return items;
    }

    public ArrayList<Item> getCardsByCategory(String category)
    {
        //ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + CARDS_TABLE_NAME +
                " WHERE " + CARDS_COLUMN_CATEGORY + " LIKE '" + category + "' " +
                "ORDER BY " + CARDS_COLUMN_NAME + " COLLATE NOCASE", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            card = new Item();
            card.setItemId(res.getInt(res.getColumnIndex(CARDS_COLUMN_ID)));
            card.setItemName(res.getString(res.getColumnIndex(CARDS_COLUMN_NAME)));
            card.setItemCategory(res.getString(res.getColumnIndex(CARDS_COLUMN_CATEGORY)));
            card.setItemFormat(res.getString(res.getColumnIndex(CARDS_COLUMN_FORMAT)));
            card.setItemNumber(res.getString(res.getColumnIndex(CARDS_COLUMN_NUMBER)));
            items.add(card);
            res.moveToNext();
        }
        return items;
    }
}
