package com.practice.todolist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView lvItems;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
    EditText etNewItem;
    private final int REQUEST_CODE = 20;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        etNewItem=(EditText)findViewById(R.id.etNewItem);
        readItems();
        itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void addItem(View v) {
    	
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	saveItems();
    }
    
    public void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
    		@Override
    		public boolean onItemLongClick(AdapterView<?> aView,
    				View item, int pos, long id){
    			items.remove(pos);
    			itemsAdapter.notifyDataSetInvalidated();
    			saveItems();
    			return true;
    		}
    	});
    	lvItems.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    	      public void onItemClick(AdapterView<?> adapter, View v, int position,
    	            long arg3) 
    	      {
    				Intent editListView = new Intent(v.getContext(),EditItemActivity.class);
    				editListView.putExtra("POS",position);
    				editListView.putExtra("BODY", items.get(position));
    				startActivityForResult(editListView,REQUEST_CODE);
    	      }
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // REQUEST_CODE is defined above
      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
         // Extract name value from result extras
         String name = data.getExtras().getString("ItemName");
         int position = data.getExtras().getInt("POS");
         items.set(position, name);
         itemsAdapter.notifyDataSetInvalidated();
     	 saveItems();
      }
    } 
    
    
    
    
    
    private void readItems(){
    	File filesDir =getFilesDir();
    	File todoFile = new File(filesDir,"todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e){
    		items = new ArrayList<String>();
    		
    	}
    }
    
    private void saveItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir,"todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, items);
    	} catch(IOException e) {
    		e.printStackTrace();
    		
    	}
    }
    

}
