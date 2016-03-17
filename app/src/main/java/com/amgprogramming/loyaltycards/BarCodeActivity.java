package com.amgprogramming.loyaltycards;

import java.util.EnumMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class BarCodeActivity extends ActionBarActivity {

    int cardId;
    String cardName, cardNumber, cardCategory, cardFormat, cardImage;
    ImageView barcode, companyLogo;
    RelativeLayout barcodeBackground;
    TextView barCodeNumber, companyName, categoryText;
    DBHelper mydb;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.loyalty_cards_icon);
        menu.setDisplayUseLogoEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cardId = extras.getInt("id", 0);
            cardName = extras.getString("name");
            cardNumber = extras.getString("number");
            cardCategory = extras.getString("category");
            cardFormat = extras.getString("format");
            cardImage = extras.getString("image");
        }

        barcodeBackground = (RelativeLayout) findViewById(R.id.barcodeBackground);
        categoryText = (TextView) findViewById(R.id.category);
        companyName = (TextView) findViewById(R.id.companyName);
        companyLogo = (ImageView) findViewById(R.id.companyLogo);
        barcode = (ImageView) findViewById(R.id.imageViewBarCode);
        barCodeNumber = (TextView) findViewById(R.id.barCodeNumber);

        mydb = new DBHelper(this);
        SQLiteDatabase sqDb = mydb.getWritableDatabase();

        // barcode image
        Bitmap bitmap = null;
        //barcode = new ImageView(this);

        if(cardFormat.equalsIgnoreCase("EAN_13")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.EAN_13, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("EAN_8")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.EAN_8, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("UPC_EAN_EXTENSION")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.UPC_EAN_EXTENSION, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("UPC_A")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.UPC_A, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("UPC_E")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.UPC_E, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("CODE_128")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.CODE_128, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("CODE_93")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.CODE_93, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("CODE_39")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.CODE_39, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("RSS_14")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.RSS_14, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("RSS_EXPANDED")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.RSS_EXPANDED, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("PDF_417")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.PDF_417, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("AZTEC")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.AZTEC, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("QR_CODE")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.QR_CODE, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("MAXICODE")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.MAXICODE, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("CODABAR")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.CODABAR, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("DATA_MATRIX")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.DATA_MATRIX, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        if(cardFormat.equalsIgnoreCase("ITF")){
            try {
                bitmap = encodeAsBitmap(cardNumber, BarcodeFormat.ITF, 600, 300);
                barcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        companyName.setText(cardName);
        barCodeNumber.setText(cardNumber);
        setBackround(cardCategory);
    }

    public void setBackround(String category){
        categoryText.setText(category);

        if(category.equalsIgnoreCase("Gas")){
            barcodeBackground.setBackgroundResource(R.drawable.barcode_gas);
        }
        if(category.equalsIgnoreCase("Grocery")){
            barcodeBackground.setBackgroundResource(R.drawable.barcode_grocery);
        }
        if(category.equalsIgnoreCase("Other")){
            barcodeBackground.setBackgroundResource(R.drawable.barcode_other);
        }
        if(category.equalsIgnoreCase("Pharmacy")){
            barcodeBackground.setBackgroundResource(R.drawable.barcode_pharmacy);
        }
        if(category.equalsIgnoreCase("Restaurant")){
            barcodeBackground.setBackgroundResource(R.drawable.barcode_restaurant);
        }
        if(category.equalsIgnoreCase("Retail")){
            barcodeBackground.setBackgroundResource(R.drawable.barcode_retail);
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
        getMenuInflater().inflate(R.menu.menu_barcode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu) {
            return true;
        }
        if (id == R.id.editCard) {
            Intent intent = new Intent(BarCodeActivity.this, EditActivity.class);
            intent.putExtra("id", cardId);
            intent.putExtra("name", cardName);
            intent.putExtra("number", cardNumber);
            intent.putExtra("category", cardCategory);
            intent.putExtra("format", cardFormat);
            intent.putExtra("image", "");
            startActivity(intent);
            return true;
        }
        if (id == R.id.deleteCard) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BarCodeActivity.this);

            builder.setTitle("Confirm");
            builder.setMessage("Delete this card?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mydb = new DBHelper(ctx);
                    mydb.deleteCard(cardId);
                    Toast.makeText(getBaseContext(), "Card deleted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BarCodeActivity.this, MainActivity.class);
                    startActivity(intent);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
