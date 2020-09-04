package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;


public class MainActivity extends AppCompatActivity
{
List<String> items;
Button btnAdd;
EditText etItem;
RecyclerView recyclerView;
ItemsAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        recyclerView = findViewById(R.id.recyclerView);

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
                items.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                // add item to model
                items.add(todoItem);
                // notify the adapter that an item has been inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "item was added", Toast.LENGTH_SHORT).show();
                 saveItems();
            }
        });
    }
       private File getDataFile()
        {
            return new File(getFilesDir(), "data.txt");
        }
        // this function saves items by writing them into the data file
    private void loadItems(){
        try {
        items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
    }
    catch (IOException e)
    {
        Log.e("MainActivity", " Error", e);
        items = new ArrayList<>();
    }
    }

    // This function saves items  by writing them
    private void saveItems() {
    try {
    FileUtils.writeLines(getDataFile(), items);
     }
   catch (IOException e)
    {
   Log.e("MainActivity", " Error", e);
    }
  }
}
