package com.terezanya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class FullTextActivity extends Activity {
	ArrayList<String> terezAnyaTextArray = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullScreen();
		setContentView(R.layout.activity_fulltext);
		
		InputStream is;
		String currentLine = "";
		String allText = "";
		try {
			is = getApplicationContext().getAssets().open("terezanya.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			while ((currentLine = bufferedReader.readLine()) != null) {
				allText += currentLine +"\n";
			}
			
		} catch (IOException e) {
			Log.e("onCreate", e.getMessage());
			e.printStackTrace();
		}
		
	   TextView resultField = (TextView)findViewById(R.id.fullText);
	   resultField.setText(allText);
		
	}
	
	private void setFullScreen(){
		// Make this activity, full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    		   WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
		// Hide the Action bar of this activity screen
		ActionBar actionBar = getActionBar();
		actionBar.hide();       
	}
	
	public void back(View view){
		finish();
	}

}
