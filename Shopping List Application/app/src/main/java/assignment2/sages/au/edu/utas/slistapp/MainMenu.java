package assignment2.sages.au.edu.utas.slistapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {



    private Button btnAddItem;
    private Button btnNewList;
    private Button btnPurchase;
    private Button btnExpenditures;
    private ListView listView;
    private ListView listViewCart;
    //ArrayList<String> listItems=new ArrayList<String>();


    //ArrayAdapter<String> adapter;


    protected void onResume()
    {
        super.onResume();

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


        setContentView(R.layout.activity_main_menu);

        listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listViewCart = findViewById(R.id.listViewCart);
        listViewCart.setAdapter(arrayAdapter2);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnNewList = findViewById(R.id.newList);
        btnPurchase = findViewById(R.id.btnPurchase);
        btnExpenditures = findViewById(R.id.btnExpenditures);

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
                        saveArray(itemCostsShrunk,"itemCostsCart",getApplicationContext());
                        saveArray(itemQuantitiesShrunk, "itemQuantitiesCart", getApplicationContext());
                        saveArray(itemNamesShrunk, "itemNamesCart", getApplicationContext());

                        final String[] itemNamesCart = loadArray("itemNames", getApplicationContext());
                        final String[] itemQuantitiesCart = loadArray("itemQuantities", getApplicationContext());
                        final String[] itemCostsCart = loadArray("itemCosts", getApplicationContext());

                       itemList2.add(itemNamesCart[i] + " x " + itemQuantitiesCart[i] + " price: $" + itemCostsCart[i]);
                        arrayAdapter2.notifyDataSetChanged();
                        int size = itemNamesCart.length;

                //redundant?
                /*if(size == 0)
                {
                }
                else
                {
                    //populate listView
                    for(int ii = 0; ii < itemNamesCart.length; ii++)
                    {
                        if (itemNamesCart[ii] == null)
                        {
                            itemNamesCart[ii] = "NULL";
                        }
                        itemList2.add(itemNamesCart[i] + " x " + itemQuantitiesCart[i] + " price: $" + itemCostsCart[i]);
                    }
                    for(int ii = 0; ii < itemQuantities.length; ii++)
                    {
                        if (itemQuantitiesCart[ii] == null)
                        {
                            //default values
                            itemQuantitiesCart[ii] = "1";
                        }
                        //itemList.add(itemNames[i]);
                    }
                    for(int ii = 0; ii < itemCostsCart.length; ii++)
                    {
                        if (itemCostsCart[ii] == null)
                        {
                            //default values
                            itemCostsCart[ii] = "0";
                        }
                        //itemList.add(itemNames[i]);
                    }
                }
                arrayAdapter2.notifyDataSetChanged();
           */ }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainMenu.this);
                alert.setTitle("Complete Shop");
                alert.setMessage("Are you sure you're ready to complete your shop?");
                alert.setNegativeButton("Cancel", null);
                alert.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int test) {
                       String[] itemNamesCart = loadArray("itemNames", getApplicationContext());
                       String[] itemQuantitiesCart = loadArray("itemQuantities", getApplicationContext());
                       String[] itemCostsCart = loadArray("itemCosts", getApplicationContext());
                        saveArray(itemNamesCart,"boughtListNames",getApplicationContext());
                        saveArray(itemQuantitiesCart,"boughtListQuantities",getApplicationContext());
                        saveArray(itemCostsCart,"boughtListPrices",getApplicationContext());
                        startActivity(new Intent(MainMenu.this,expenditures.class));
                    }
                });
                alert.show();


            }
        });

                btnNewList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(MainMenu.this);
                        alert.setTitle("New List?");
                        alert.setMessage("Are you sure you want to start a new list, you will lose all persistent data?");
                        alert.setNegativeButton("Cancel", null);
                        alert.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int test) {
                                itemList.clear();
                                arrayAdapter.notifyDataSetChanged();
                                getSharedPreferences("itemListP", 0).edit().clear().commit();
                            }
                        });
                        alert.show();


                        //clears all persistent data

                        //itemList.remove(1);

                    }
                });

        btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this,addItem.class));
            }
        });

        btnExpenditures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this,expenditures.class));
            }
        });


        //this.onCreate(null);
    }
    //The methods load and save from 1. elKhatib S. String arrays and Object arrays in SharedPreferences. Sherifmobi. 2018. Available at: http://www.sherif.mobi/2012/05/string-arrays-and-object-arrays-in.html. Accessed May 28, 2018.
    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("itemListP", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }

    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("itemListP", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

}
