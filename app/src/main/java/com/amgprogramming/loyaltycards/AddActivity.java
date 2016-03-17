package com.amgprogramming.loyaltycards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class AddActivity extends ActionBarActivity {

    String cardNumber = "", cardFormat = "", companyName = "",cardImage = "", cardCategory = "";
    Button scanBtn, saveBtn, cancelBtn;
    Spinner categories;
    TextView formatTxt, contentTxt,  companyNameText;
    ImageView barcode;
    Bitmap bitmap = null;
    Item card;
    ArrayList<Item> items = new ArrayList();
    private static final String[] category_array = {"Gas", "Grocery", "Other", "Pharmacy", "Restaurant", "Retail"};
    private DBHelper mydb ;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cardCategory = extras.getString("category");
        }

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.loyalty_cards_icon);
        menu.setDisplayUseLogoEnabled(true);

        companyNameText = (TextView)findViewById(R.id.companyNameText);
        categories = (Spinner) findViewById(R.id.categories);
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        barcode = (ImageView)findViewById(R.id.barcode);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        saveBtn = (Button)findViewById(R.id.saveCard);
        cancelBtn = (Button)findViewById(R.id.cancelCard);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_spinner_item, category_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);
        if(cardCategory.trim().equalsIgnoreCase("")) categories.setSelection(adapter.getPosition("Other"));
        else categories.setSelection(adapter.getPosition(cardCategory));

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                cardCategory = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        scanBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                IntentIntegrator scanIntegrator = new IntentIntegrator(AddActivity.this);
                scanIntegrator.initiateScan();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                companyName = companyNameText.getText().toString();

                if(companyName.trim().equalsIgnoreCase("")){
                    Toast.makeText(getBaseContext(), "Enter a company name", Toast.LENGTH_SHORT).show();
                }
                else if(cardNumber.trim().equalsIgnoreCase("")){
                    Toast.makeText(getBaseContext(), "Scan a new barcode", Toast.LENGTH_SHORT).show();
                }
                else{
                    card = new Item();
                    card.setItemName(companyName);
                    card.setItemFormat(cardFormat);
                    card.setItemNumber(cardNumber);

                    mydb = new DBHelper(ctx);
                    long result = mydb.insertCard(companyName, cardNumber, cardCategory, cardFormat, cardImage);
                    if(result < 0){
                        Toast.makeText(getBaseContext(), "New card NOT saved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getBaseContext(), "New card saved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {

            String contents = scanningResult.getContents();
            if(contents != null) {
                //we have a result
                cardNumber = scanningResult.getContents();
                cardFormat = scanningResult.getFormatName();
                scanBtn.setVisibility(View.GONE);

                formatTxt.setText("Format: " + cardFormat);
                if (cardFormat.equalsIgnoreCase("EAN_13")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.EAN_13, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("EAN_8")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.EAN_8, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("UPC_EAN_EXTENSION")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.UPC_EAN_EXTENSION, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("UPC_A")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.UPC_A, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("UPC_E")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.UPC_E, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("CODE_128")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.CODE_128, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("CODE_93")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.CODE_93, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("CODE_39")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.CODE_39, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("RSS_14")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.RSS_14, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("RSS_EXPANDED")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.RSS_EXPANDED, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("PDF_417")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.PDF_417, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("AZTEC")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.AZTEC, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("QR_CODE")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.QR_CODE, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("MAXICODE")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.MAXICODE, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("CODABAR")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.CODABAR, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("DATA_MATRIX")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.DATA_MATRIX, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                if (cardFormat.equalsIgnoreCase("ITF")) {
                    try {
                        bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.ITF, 600, 300);
                        barcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }

                contentTxt.setText("" + cardNumber);
            }
            else{
                cardNumber = "";
                Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            cardNumber = "";
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**************************************************************
     * getting from com.google.zxing.client.android.encode.QRCodeEncoder
     *
     * See the sites below
     * http://code.google.com/p/zxing/
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/EncodeActivity.java
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/QRCodeEncoder.java
     */

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
