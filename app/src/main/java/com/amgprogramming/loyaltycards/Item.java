package com.amgprogramming.loyaltycards;

import android.app.Application;

public class Item extends Application{

	public String itemName, itemNumber, itemCategory, itemFormat, itemImage;
    public int itemId;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int value) {
        itemId = value;
    }

    public String getItemNumber() {
        return itemNumber;
    }
     
    public void setItemNumber(String text) {
        itemNumber = text;
    }
    
    public String getItemName() {         
        return itemName;
    }
     
    public void setItemName(String text) {        
    	itemName = text;         
    }

    public String getItemFormat() {
        return itemFormat;
    }

    public void setItemFormat(String text) {
        itemFormat = text;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String text) {
        itemCategory = text;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String text) {
        itemImage = text;
    }

}
