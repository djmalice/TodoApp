package com.practice.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends Activity {
		String itemBody;
		int itemPos;
		EditText etItemName;
		Button btSaveItem;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		itemBody = getIntent().getStringExtra("BODY");
		itemPos = getIntent().getIntExtra("POS",0);
		etItemName=(EditText)findViewById(R.id.etItemName);
		etItemName.setText(itemBody);
		etItemName.setCursorVisible(true);
		etItemName.setSelection(itemBody.length());
		btSaveItem=(Button)findViewById(R.id.btSaveItem);
		setupButtonListener();
	}

	public void setupButtonListener() {
		btSaveItem.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				 // Prepare data intent 
				  Intent data = new Intent();
				  // Pass relevant data back as a result
				  data.putExtra("ItemName", etItemName.getText().toString());
				  data.putExtra("POS", itemPos);
				  // Activity finished ok, return the data
				  setResult(RESULT_OK, data); // set result code and bundle data for response
				  finish(); // closes the activity, pass data to parent
			}
		});
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
