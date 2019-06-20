package assignment2.sages.au.edu.utas.slistapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class addItem extends AppCompatActivity {



    private EditText itemNameField;
    private EditText itemQuantityField;
    private EditText itemCostField;
    private EditText itemPreview;
    private EditText itemPreview2;
    private Button previewBtn;
    private Button confirmBtn;
    public ListView listView;
    public ListView suggestListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String[] itemOptionNames = new String[5];
        itemOptionNames[0] = "Hat";
        itemOptionNames[1] = "Carrot";
        itemOptionNames[2] = "Hamster";
        itemOptionNames[3] = "Socks";
        itemOptionNames[4] = "Shirt";
        final String[] itemOptionPrices = new String[5];
        itemOptionPrices[0] = "15";
        itemOptionPrices[1] = "2";
        itemOptionPrices[2] = "20";
        itemOptionPrices[3] = "6";
        itemOptionPrices[4] = "25";

        //Used for debugging and removing all shared preferences
       //getSharedPreferences("itemListP", 0).edit().clear().commit();

        final ArrayList<String> itemList = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemList);
        final ArrayList<String> itemList2 = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemList2);

        //Load existing list from user preferences
        //load all item names from user preferences for list
        final String[] itemNames = loadArray("itemNames", getApplicationContext());
        final String[] itemQuantities = loadArray("itemQuantities", getApplicationContext());
        final String[] itemCosts = loadArray("itemCosts", getApplicationContext());

        int size = itemNames.length;

        for(int i = 0; i< itemOptionNames.length; i++)
        {
            itemList2.add(itemOptionNames[i] + " x 1 price: $" + itemOptionPrices[i]);
        }

        //redundant?
        if(size == 0)
        {
        }
        else
        {
           //populate listView
            for(int i = 0; i < itemNames.length; i++)
            {
                if (itemNames[i] == null)
                {
                    itemNames[i] = "NULL";
                }
                itemList.add(itemNames[i] + " x " + itemQuantities[i] + " price: $" + itemCosts[i]);
            }
            for(int i = 0; i < itemQuantities.length; i++)
            {
                if (itemQuantities[i] == null)
                {
                    //default values
                    itemQuantities[i] = "1";
                }
                //itemList.add(itemNames[i]);
            }
            for(int i = 0; i < itemCosts.length; i++)
            {
                if (itemCosts[i] == null)
                {
                    //default values
                    itemCosts[i] = "0";
                }
                //itemList.add(itemNames[i]);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemNameField = findViewById(R.id.itemNameField);
        itemQuantityField = findViewById(R.id.itemQuantityField);
        itemCostField = findViewById(R.id.itemCostField);
        previewBtn = findViewById(R.id.btnPreviewItem);
        confirmBtn = findViewById(R.id.btnConfirmItem);
        itemPreview = findViewById(R.id.itemPreview);
        itemPreview2 = findViewById(R.id.itemPreview2);
        listView = findViewById(R.id.listView);
        suggestListView = findViewById(R.id.listView4);

        listView.setAdapter(arrayAdapter);
        suggestListView.setAdapter(arrayAdapter2);

        previewBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String itemQuantity = (itemQuantityField.getText().toString());
                String itemToAdd = (itemNameField.getText().toString());
                String displayText = "Item Name: " + itemToAdd;
                String displayText2 = "Item Quantity: " + itemQuantity;
                itemPreview.setText(displayText);
                itemPreview2.setText(displayText2);

            }
        });


        suggestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                final String s = suggestListView.getItemAtPosition(i).toString();
                AlertDialog.Builder adb=new AlertDialog.Builder(addItem.this);
                adb.setTitle("Add Item");
                adb.setMessage("Are you sure you want to add " + s + " to your list?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String[] itemNames2 = loadArray("itemNames", getApplicationContext());
                        String[] itemQuantities = loadArray("itemQuantities", getApplicationContext());
                        final String[] itemCosts = loadArray("itemCosts", getApplicationContext());

                        int listLength = itemNames2.length;

                        if (listLength >= 1)
                        {

                            listLength++;
                            String correctLengthArray[] = new String[listLength];
                            String itemToAddNewLength[] = new String[listLength];
                            String itemCostNewLength[]= new String[listLength];

                            for (int ii = 0; ii < itemNames2.length; ii++)
                            {
                                itemToAddNewLength[ii] = itemQuantities[ii];
                                correctLengthArray[ii] = itemNames2[ii];
                                itemCostNewLength[ii] = itemCosts[ii];
                            }
                            itemToAddNewLength[itemToAddNewLength.length -1] = itemOptionNames[i];
                            correctLengthArray[correctLengthArray.length -1] = "1";
                            itemCostNewLength[itemCostNewLength.length -1] = itemOptionPrices[i];
                            itemList.add(itemOptionNames[i] + " x " + "1" + " price: $" + itemOptionPrices[i]);
                            saveArray(itemCostNewLength,"itemCosts",getApplicationContext());
                            saveArray(correctLengthArray, "itemNames", getApplicationContext());
                            saveArray(itemToAddNewLength, "itemQuantities", getApplicationContext());
                            arrayAdapter.notifyDataSetChanged();

                        }
                        String[] itemNames3 = loadArray("itemNames", getApplicationContext());

                        if (listLength == 1)
                        {
                            itemNames3[0] = itemOptionNames[i] + " x " + "1" + " price: $" + itemOptionPrices[i];
                            arrayAdapter.notifyDataSetChanged();
                        }

                        if (listLength == 0)
                        {
                            listLength++;
                            String correctLengthArray[] = new String[listLength];
                            String correctQuantityArray[] = new String[listLength];
                            String itemCostNewLength[]= new String[listLength];
                            itemCostNewLength[itemCostNewLength.length -1] = itemOptionPrices[i];
                            correctQuantityArray[correctQuantityArray.length -1] = "1";
                            correctLengthArray[correctLengthArray.length -1] = itemOptionNames[i];
                            itemList.add(itemOptionNames[i] + " x " + "1" + " price: $" + itemOptionPrices[i]);
                            saveArray(correctLengthArray, "itemNames", getApplicationContext());
                            saveArray(correctQuantityArray, "itemQuantities", getApplicationContext());
                            saveArray(itemCostNewLength,"itemCosts",getApplicationContext());
                            arrayAdapter.notifyDataSetChanged();
                        }

                    }});
                adb.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                final String[] itemNames = loadArray("itemNames", getApplicationContext());
                final String[] itemQuantities = loadArray("itemQuantities", getApplicationContext());
                final String[] itemCosts = loadArray("itemCosts", getApplicationContext());

                final String[] itemNamesShrunk = new String[itemNames.length -1];
                final String[] itemQuantitiesShrunk = new String[itemNames.length -1];
                final String[] itemCostsShrunk = new String[itemNames.length -1];

                String s = listView.getItemAtPosition(i).toString();
               final AlertDialog.Builder adb=new AlertDialog.Builder(addItem.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete  " + s + "?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        itemList.remove(i);
                        arrayAdapter.notifyDataSetChanged();


                        //For properly removing items from persistent data, shrinking array by re-creating and removing specific items relating to index location
                        boolean switched = false;
                        for (int ii = 0; ii < itemCostsShrunk.length; ii++)
                        {
                            if (itemNames[ii] == itemNames[i])
                            {
                                itemNamesShrunk[ii] = itemNames[ii + 1];
                                itemQuantitiesShrunk[ii] = itemQuantities[ii + 1];
                                itemCostsShrunk[ii] = itemCosts[ii + 1];
                                switched = true;
                            }
                            else
                            {
                                if (switched)
                                {
                                    itemNamesShrunk[ii] = itemNames[ii + 1];
                                    itemQuantitiesShrunk[ii] = itemQuantities[ii + 1];
                                    itemCostsShrunk[ii] = itemCosts[ii + 1];
                                }
                                else
                                {
                                    itemNamesShrunk[ii] = itemNames[ii];
                                    itemQuantitiesShrunk[ii] = itemQuantities[ii];
                                    itemCostsShrunk[ii] = itemCosts[ii];
                                }
                            }
                        }
                        saveArray(itemCostsShrunk,"itemCosts",getApplicationContext());
                        saveArray(itemQuantitiesShrunk, "itemQuantities", getApplicationContext());
                        saveArray(itemNamesShrunk, "itemNames", getApplicationContext());
                    }});
                adb.show();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] itemNames2 = loadArray("itemNames", getApplicationContext());
                String[] itemQuantities = loadArray("itemQuantities", getApplicationContext());
                final String[] itemCosts = loadArray("itemCosts", getApplicationContext());

                String itemQuantity = (itemQuantityField.getText().toString());
                String itemToAdd = (itemNameField.getText().toString());
                String itemCost = (itemCostField.getText().toString());


                int itemQuantInt = Integer.parseInt(itemQuantity);

                int listLength = itemNames2.length;

                if (listLength >= 1)
                {

                    listLength++;
                    String correctLengthArray[] = new String[listLength];
                    String itemToAddNewLength[] = new String[listLength];
                    String itemCostNewLength[]= new String[listLength];

                    for (int i = 0; i < itemNames2.length; i++)
                    {
                        itemToAddNewLength[i] = itemQuantities[i];
                        correctLengthArray[i] = itemNames2[i];
                        itemCostNewLength[i] = itemCosts[i];
                    }
                    itemToAddNewLength[itemToAddNewLength.length -1] = itemQuantity;
                    correctLengthArray[correctLengthArray.length -1] = itemToAdd;
                    itemCostNewLength[itemCostNewLength.length -1] = itemCost;
                    itemList.add(itemToAdd + " x " + itemQuantity + " price: $" + itemCost);
                    saveArray(itemCostNewLength,"itemCosts",getApplicationContext());
                    saveArray(itemToAddNewLength, "itemQuantities", getApplicationContext());
                    saveArray(correctLengthArray, "itemNames", getApplicationContext());
                    arrayAdapter.notifyDataSetChanged();

                }

                String[] itemNames3 = loadArray("itemNames", getApplicationContext());

                if (listLength == 1)
                {
                    itemNames3[0] = itemToAdd + " x " + itemQuantInt + " price: $" + itemCost;
                    arrayAdapter.notifyDataSetChanged();
                }

                if (listLength == 0)
                {
                    listLength++;
                    String correctLengthArray[] = new String[listLength];
                    String correctQuantityArray[] = new String[listLength];
                    String itemCostNewLength[]= new String[listLength];
                    itemCostNewLength[itemCostNewLength.length -1] = itemCost;
                    correctQuantityArray[correctQuantityArray.length -1] = itemQuantity;
                    correctLengthArray[correctLengthArray.length -1] = itemToAdd;
                    itemList.add(itemToAdd + " x " + itemQuantity + " price: $" + itemCost);
                    saveArray(correctLengthArray, "itemNames", getApplicationContext());
                    saveArray(correctQuantityArray, "itemQuantities", getApplicationContext());
                    saveArray(itemCostNewLength,"itemCosts",getApplicationContext());
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });



    }
    //Load Array
    //The methods load and save from 1. elKhatib S. String arrays and Object arrays in SharedPreferences. Sherifmobi. 2018. Available at: http://www.sherif.mobi/2012/05/string-arrays-and-object-arrays-in.html. Accessed May 28, 2018.
    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("itemListP", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }

    //Save Array
    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("itemListP", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }
}
