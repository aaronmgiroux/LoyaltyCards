package com.amgprogramming.loyaltycards;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {
	 
    int resource;
    String response;
    Context context;
    TextView itemName, itemNumber;
    ImageView itemImage;
    
    //Initialize adapter
    public ItemAdapter(Context context, int resource, ArrayList<Item> items) {
        super(context, resource, items);
        this.resource = resource;
    }
     
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout alertView;
        //Get the current alert object
        Item item = getItem(position);
         
        //Inflate the view
        if(convertView==null)
        {
            alertView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, alertView, true);
        }
        else
        {
            alertView = (LinearLayout) convertView;
        }       
        
        //Get the text boxes from the listitem.xml file
        itemName =(TextView)alertView.findViewById(R.id.itemName);
        itemNumber =(TextView)alertView.findViewById(R.id.itemNumber);
        itemImage =(ImageView)alertView.findViewById(R.id.itemImage);

        //Assign the appropriate text
        itemName.setText(item.getItemName());
        itemNumber.setText(item.getItemNumber());

        //Assign the appropriate image
        setCardImage(item.getItemCategory());

        return alertView;
    }

    public void setCardImage(String category){
        if(category.equalsIgnoreCase("All")){
            itemImage.setBackgroundResource(R.drawable.all_icon);
        }
        if(category.equalsIgnoreCase("Gas")){
            itemImage.setBackgroundResource(R.drawable.gas_icon);
        }
        if(category.equalsIgnoreCase("Grocery")){
            itemImage.setBackgroundResource(R.drawable.grocery_icon);
        }
        if(category.equalsIgnoreCase("Other")){
            itemImage.setBackgroundResource(R.drawable.other_icon);
        }
        if(category.equalsIgnoreCase("Pharmacy")){
            itemImage.setBackgroundResource(R.drawable.pharmacy_icon);
        }
        if(category.equalsIgnoreCase("Restaurant")){
            itemImage.setBackgroundResource(R.drawable.restaurant_icon);
        }
        if(category.equalsIgnoreCase("Retail")){
            itemImage.setBackgroundResource(R.drawable.retail_icon);
        }
    }
 
}
