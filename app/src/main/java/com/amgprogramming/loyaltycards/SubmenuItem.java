package com.amgprogramming.loyaltycards;

import android.app.Application;

public class SubmenuItem extends Application{
	public String itemName;
	public int itemTypeId;
    
    public int getItemTypeId() {         
        return itemTypeId;
    }
     
    public void setItemTypeId(int value) {        
    	itemTypeId = value;         
    }
    
    public String getItemName() {         
        return itemName;
    }
     
    public void setItemName(String text) {        
    	itemName = text;         
    }

}
