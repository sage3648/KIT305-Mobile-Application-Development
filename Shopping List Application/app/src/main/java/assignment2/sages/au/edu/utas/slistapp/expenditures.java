package assignment2.sages.au.edu.utas.slistapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class expenditures extends AppCompatActivity {

    private ListView listView;
    private TextView cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditures);

        final ArrayList<String> itemList = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemList);

        final String[] itemNames = loadArray("boughtListNames",getApplicationContext());
        final String[] itemQuantities = loadArray("boughtListQuantities",getApplicationContext());
        final String[] itemCosts = loadArray("boughtListPrices",getApplicationContext());

        /*saveArray(itemNamesCart,"boughtListNames",getApplicationContext());
        saveArray(itemQuantitiesCart,"boughtListQuantities",getApplicationContext());
        saveArray(itemCostsCart,"boughtListPrices",getApplicationContext());*/
        int size = itemNames.length;


        int totalCost = 0;

        for(int i = 0; i < itemCosts.length; i++)
        {
            int cost = Integer.parseInt(itemCosts[i]);
            totalCost = totalCost + cost;
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

        listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        cost = findViewById(R.id.spendings);

        cost.setText("Total Spending = " + totalCost);
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
}
