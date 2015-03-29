package com.terezanya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	ArrayList<String> terezAnyaTextArray = new ArrayList<String>();
	String prevSearchText = "";
	String line2 = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullScreen();
		setContentView(R.layout.activity_main);
		
		
		Button clearSearchButton = (Button) findViewById(R.id.clearSearchFieldId);
		clearSearchButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		 	   EditText searchField = (EditText)findViewById(R.id.searchField);	   
			   searchField.setText("");
			   prevSearchText = "";
		    }
		});		
		
		Button clearAllButton = (Button) findViewById(R.id.clearAllButtonId);		
		
		
		clearAllButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		 	   TextView resultField = (TextView)findViewById(R.id.resultField);
			   resultField.setText("");
			   TextView inputField = (TextView)findViewById(R.id.inputField);
			   inputField.setText("");			   
			   EditText searchField = (EditText)findViewById(R.id.searchField);	   
			   searchField.setText("");
			   prevSearchText = "";
		    }
		});	
		/* Java 1.8 is not working
		clearAllButton.setOnClickListener((View v) -> {
		 	   TextView resultField = (TextView)findViewById(R.id.resultField);
			   resultField.setText("");
			   TextView inputField = (TextView)findViewById(R.id.inputField);
			   inputField.setText("");			   
			   EditText searchField = (EditText)findViewById(R.id.searchField);	   
			   searchField.setText("");
			   prevSearchText = "";						
		});*/

		//with lambda
		//clearAllButton.setOnClickListener((View v) -> {    // do something here //});
		//change
		
		EditText searchField = (EditText)findViewById(R.id.searchField);
		searchField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	        @Override
	        public void onFocusChange(View v, boolean hasFocus) {
	            if (!hasFocus) {
	                hideKeyboard(v);
	            }
	        }
	    });		
				
		InputStream is;
		String currentLine = "";
		try {
			is = getApplicationContext().getAssets().open("terezanya.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			while ((currentLine = bufferedReader.readLine()) != null) {
				terezAnyaTextArray.add(currentLine);
			}
			
		} catch (IOException e) {
			Log.e("onCreate", e.getMessage());
			e.printStackTrace();
		}
	   
		
	}
	
	public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }	
	
	private void setFullScreen(){
		// Make this activity, full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    		   WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
		// Hide the Action bar of this activity screen
		ActionBar actionBar = getActionBar();
		actionBar.hide();       
	}
	
   public void startSearch(View view){
	   EditText searchField = (EditText)findViewById(R.id.searchField);	   
	   String searchedText = searchField.getText().toString();
	   
	   Log.d("startSearch", "searchedText:"+searchedText);

	   if (!isEmpty(prevSearchText) && prevSearchText.equals(searchedText)){
		   searchedText = line2;
		   searchField.setText(line2);
		   Log.d("startSearch", "searchedText2:"+searchedText);
	   } 
	   for (int i = 0; i < terezAnyaTextArray.size(); i++) {
		   String line0 = terezAnyaTextArray.get(i);
		   String line1 = "";
		   line2 = "";
		   int currentPoz = i;
		   while(isEmpty(line1) && currentPoz < terezAnyaTextArray.size() -1){
			   currentPoz++;
			   line1 = terezAnyaTextArray.get(currentPoz);
		   }
		   while(isEmpty(line2) && currentPoz < terezAnyaTextArray.size() -1){
			   currentPoz++;
			   line2 = terezAnyaTextArray.get(currentPoz);
		   }

		   if (!isEmpty(searchedText) && line0.toUpperCase().contains(searchedText.toUpperCase())){
			   TextView inputField = (TextView)findViewById(R.id.inputField);
			   inputField.setText(line0);			   
			   TextView resultField = (TextView)findViewById(R.id.resultField);
			   resultField.setText(line1 + "\n\n" + line2);
			   prevSearchText = searchedText;
			   break;
		   } else {
			   TextView inputField = (TextView)findViewById(R.id.inputField);
			   inputField.setText("");			   
			   TextView resultField = (TextView)findViewById(R.id.resultField);
			   resultField.setText("SZARUL KERESTÉL VAGY VÉGE A DALNAK, IGYÁL EGYET!!!");
		   }
	   }
	   InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	   mgr.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
   }

   public void showAll(View view){
	   Intent intent = new Intent(getApplicationContext(), FullTextActivity.class);
	   startActivity(intent);
   }
   
   public void randomText(View view){
	   
	   boolean randomTextFound = false;
	   do{
		   Random rand = new Random();
		   int randomNr = rand.nextInt(terezAnyaTextArray.size());
		   if (!isEmpty(terezAnyaTextArray.get(randomNr))){
			   randomTextFound = true;
			   EditText searchField = (EditText)findViewById(R.id.searchField);
			   searchField.setText(terezAnyaTextArray.get(randomNr));
			   TextView inputField = (TextView)findViewById(R.id.inputField);
			   inputField.setText(terezAnyaTextArray.get(randomNr));
			   TextView resultField = (TextView)findViewById(R.id.resultField);
			   resultField.setText("");
			   prevSearchText = "";
		   }
	   } while(!randomTextFound);
	   
   }
   
	private boolean isEmpty(String parameter) {
		if (parameter == null) return true;

		return "".equals(parameter);
	}   
   

}
