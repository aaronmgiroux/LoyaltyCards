package com.amgprogramming.loyaltycards;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    int selectedItemId;
    String selectedCategory = "All", selectedItemName, selectedItemNumber, selectedItemCategory;
    String selectedItemFormat, selectedItemImage;
    ListView listViewCards, submenuList;
    ImageView categoryIcon;
    RelativeLayout mainScreen;
    ItemAdapter arrayAdapter;
    TextView categoryType, categoryNumber;
    Item item, selectedItem;
    SubmenuItem submenuItemEdit, submenuItemDelete, selectedSubmenuItem;
    SubmenuItemAdapter submenuArrayAdapter;
    AlertDialog alert;
    AlertDialog.Builder builder;
    private String TAG = "Database Call";
    ArrayList<Item> items = new ArrayList<Item>();
    ArrayList <SubmenuItem> submenuItems = new ArrayList<SubmenuItem>();
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.loyalty_cards_icon);
        menu.setDisplayUseLogoEnabled(true);

        mainScreen = (RelativeLayout) findViewById(R.id.mainScreen);
        categoryIcon = (ImageView) findViewById(R.id.categoryIcon);
        categoryType = (TextView) findViewById(R.id.categoryType);
        categoryNumber = (TextView) findViewById(R.id.categoryNumber);
        listViewCards = (ListView) findViewById(R.id.listViewCards);

        mydb = new DBHelper(this);
        SQLiteDatabase sqDb = mydb.getWritableDatabase();

        if(selectedCategory.equalsIgnoreCase("All")){
            AsyncCallGetAllCards task = new AsyncCallGetAllCards(MainActivity.this);
            task.execute();
        }
        else{
            AsyncCallGetCards task = new AsyncCallGetCards(MainActivity.this);
            task.execute();
        }

        listViewCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                selectedItem = (Item) (listViewCards.getItemAtPosition(myItemInt));
                selectedItemId = selectedItem.getItemId();
                selectedItemName = selectedItem.getItemName();
                selectedItemNumber = selectedItem.getItemNumber();
                selectedItemCategory = selectedItem.getItemCategory();
                selectedItemFormat = selectedItem.getItemFormat();
                selectedItemImage = selectedItem.getItemImage();

                Intent intent = new Intent(MainActivity.this, BarCodeActivity.class);
                intent.putExtra("id", selectedItemId);
                intent.putExtra("name", selectedItemName);
                intent.putExtra("number", selectedItemNumber);
                intent.putExtra("category", selectedItemCategory);
                intent.putExtra("format", selectedItemFormat);
                intent.putExtra("image", "");
                startActivity(intent);
            }
        });

        listViewCards.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> myAdapter, View myView,
                                           int myItemInt, long mylng) {

                selectedItem = (Item) (listViewCards.getItemAtPosition(myItemInt));
                selectedItemId = selectedItem.getItemId();
                selectedItemName = selectedItem.getItemName();
                selectedItemNumber = selectedItem.getItemNumber();
                selectedItemCategory = selectedItem.getItemCategory();
                selectedItemFormat = selectedItem.getItemFormat();
                selectedItemImage = selectedItem.getItemImage();

                openMenu();
                return true;
            }
        });
    }

    public void openMenu(){
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.main_submenu, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        submenuList = (ListView) dialoglayout.findViewById(R.id.submenuItemList);

        builder.setTitle(selectedItem.getItemName());

        // set listview of submenu items here (New, List, Back)
        submenuItemEdit = new SubmenuItem();
        submenuItemEdit.setItemTypeId(1);
        submenuItemEdit.setItemName("Edit");

        submenuItemDelete = new SubmenuItem();
        submenuItemDelete.setItemTypeId(2);
        submenuItemDelete.setItemName("Delete");

        submenuItems.clear();
        submenuItems.add(submenuItemEdit);
        submenuItems.add(submenuItemDelete);

        submenuArrayAdapter = new SubmenuItemAdapter(MainActivity.this, R.layout.submenu_item, submenuItems);
        submenuList.setAdapter(submenuArrayAdapter);

        builder.setView(dialoglayout);
        alert = builder.create();
        alert.show();

        submenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                selectedSubmenuItem = (SubmenuItem) (submenuList.getItemAtPosition(myItemInt));

                switch (selectedSubmenuItem.getItemTypeId()) {
                    case 1:
                        alert.dismiss();
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra("id", selectedItemId);
                        intent.putExtra("name", selectedItemName);
                        intent.putExtra("number", selectedItemNumber);
                        intent.putExtra("category", selectedItemCategory);
                        intent.putExtra("format", selectedItemFormat);
                        intent.putExtra("image", "");
                        startActivity(intent);
                        break;
                    case 2:
                        alert.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setTitle("Confirm");
                        builder.setMessage("Delete this card?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mydb.deleteCard(selectedItemId);
                                Toast.makeText(getBaseContext(), "Card deleted", Toast.LENGTH_LONG).show();

                                if(selectedCategory.equalsIgnoreCase("All")){
                                    AsyncCallGetAllCards task = new AsyncCallGetAllCards(MainActivity.this);
                                    task.execute();
                                }
                                else{
                                    AsyncCallGetCards task = new AsyncCallGetCards(MainActivity.this);
                                    task.execute();
                                }
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                        break;
                }
            }
        });
    }

    public void setIcon(String category){
        if(category.equalsIgnoreCase("All")){
            categoryIcon.setBackgroundResource(R.drawable.all_icon);
        }
        if(category.equalsIgnoreCase("Gas")){
            categoryIcon.setBackgroundResource(R.drawable.gas_icon);
        }
        if(category.equalsIgnoreCase("Grocery")){
            categoryIcon.setBackgroundResource(R.drawable.grocery_icon);
        }
        if(category.equalsIgnoreCase("Other")){
            categoryIcon.setBackgroundResource(R.drawable.other_icon);
        }
        if(category.equalsIgnoreCase("Pharmacy")){
            categoryIcon.setBackgroundResource(R.drawable.pharmacy_icon);
        }
        if(category.equalsIgnoreCase("Restaurant")){
            categoryIcon.setBackgroundResource(R.drawable.restaurant_icon);
        }
        if(category.equalsIgnoreCase("Retail")){
            categoryIcon.setBackgroundResource(R.drawable.retail_icon);
        }
    }

    // run this class when confirm button is clicked
    public class AsyncCallGetAllCards extends AsyncTask<String, Void, Void> {
        /** progress dialog to show user that the backup is processing. */
        private ProgressDialog dialog;
        /** application context. */
        private MainActivity activity;
        /** application context. */
        private Context context;

        public AsyncCallGetAllCards(MainActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");

            items.clear();
            items = mydb.getAllCards();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            setIcon(selectedCategory);
            categoryType.setText("" + selectedCategory);
            categoryNumber.setText("(" + items.size() + ")");

            if(items.size() == 0){
                mainScreen.setBackgroundResource(R.drawable.no_cards);
                arrayAdapter = new ItemAdapter(MainActivity.this, R.layout.layout_card_list, items);
                listViewCards.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
            else{
                mainScreen.setBackgroundResource(0);
                arrayAdapter = new ItemAdapter(MainActivity.this, R.layout.layout_card_list, items);
                listViewCards.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");

            this.dialog.setTitle("Loyalty Cards");
            this.dialog.setMessage("Loading...");
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
            // Do nothing
        }
    }

    // run this class when confirm button is clicked
    public class AsyncCallGetCards extends AsyncTask<String, Void, Void> {
        /** progress dialog to show user that the backup is processing. */
        private ProgressDialog dialog;
        /** application context. */
        private MainActivity activity;
        /** application context. */
        private Context context;

        public AsyncCallGetCards(MainActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");

            items.clear();
            items = mydb.getCardsByCategory(selectedCategory);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            setIcon(selectedCategory);
            categoryType.setText("" + selectedCategory);
            categoryNumber.setText("(" + items.size() + ")");

            if(items.size() == 0){
                mainScreen.setBackgroundResource(R.drawable.no_cards);
                arrayAdapter = new ItemAdapter(MainActivity.this, R.layout.layout_card_list, items);
                listViewCards.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
            else{
                mainScreen.setBackgroundResource(0);
                arrayAdapter = new ItemAdapter(MainActivity.this, R.layout.layout_card_list, items);
                listViewCards.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");

            this.dialog.setTitle("Loyalty Cards");
            this.dialog.setMessage("Loading...");
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
            // Do nothing
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add:
                //openAddForm();
                if(items.size() == 0){
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    intent.putExtra("category", selectedCategory);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    intent.putExtra("category", "");
                    startActivity(intent);
                }
                return true;
            case R.id.action_filter:
                return true;
            case R.id.allCards:
                selectedCategory = "All";
                AsyncCallGetAllCards task = new AsyncCallGetAllCards(MainActivity.this);
                task.execute();
                return true;
            case R.id.gasCards:
                selectedCategory = "Gas";
                AsyncCallGetCards task2 = new AsyncCallGetCards(MainActivity.this);
                task2.execute();
                return true;
            case R.id.groceryCards:
                selectedCategory = "Grocery";
                AsyncCallGetCards task3 = new AsyncCallGetCards(MainActivity.this);
                task3.execute();
                return true;
            case R.id.otherCards:
                selectedCategory = "Other";
                AsyncCallGetCards task4 = new AsyncCallGetCards(MainActivity.this);
                task4.execute();
                return true;
            case R.id.pharmacyCards:
                selectedCategory = "Pharmacy";
                AsyncCallGetCards task5 = new AsyncCallGetCards(MainActivity.this);
                task5.execute();
                return true;
            case R.id.restaurantCards:
                selectedCategory = "Restaurant";
                AsyncCallGetCards task6 = new AsyncCallGetCards(MainActivity.this);
                task6.execute();
                return true;
            case R.id.retailCards:
                selectedCategory = "Retail";
                AsyncCallGetCards task7 = new AsyncCallGetCards(MainActivity.this);
                task7.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
