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

public class SubmenuItemAdapter extends ArrayAdapter<SubmenuItem> {
	 
    int resource;
    String response;
    Context context;
    TextView itemText;
    ImageView itemImage;
    
    //Initialize adapter
    public SubmenuItemAdapter(Context context, int resource, ArrayList<SubmenuItem> items) {
        super(context, resource, items);
        this.resource = resource; 
    }
     
     
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout alertView;
        //Get the current alert object
        SubmenuItem recentItem = getItem(position);
         
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
        itemImage =(ImageView)alertView.findViewById(R.id.submenuItemImage);
        itemText =(TextView)alertView.findViewById(R.id.submenuItemText);
         
        //Assign the appropriate image        
        switch(recentItem.getItemTypeId()) {
	        case 1:	          
	        	itemImage.setBackgroundResource(R.mipmap.edit_icon);
	        break;
	        case 2:	          
	        	itemImage.setBackgroundResource(R.mipmap.delete_icon);
	        break;
        }
        //Assign the appropriate text (New, List, Back)
        itemText.setText("" + recentItem.getItemName());
        
        return alertView;
    }
 
}
