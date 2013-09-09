package com.vnu.parabolicflight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.vnu.parabolicflight.itemlist.DataAdapter;
import com.vnu.parabolicflight.itemlist.StringData;

import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DisplayActivity extends Activity {

	private String name;
	private Button returnButton;
	
	private ListView listview;
	private DataAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		name = b.getString("file");

		TextView tv1 = (TextView) findViewById(R.id.display_title);
		tv1.setText("Log File : " + "android-" + name + ".txt");
		
		returnButton = (Button) findViewById(R.id.return_button);
		returnButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent1 = new Intent(DisplayActivity.this,
						MainActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		
		listview = (ListView) findViewById(R.id.listview);
		final ArrayList<StringData> list = readFile();
		
		adapter = new DataAdapter(this, list);  

		listview.setAdapter(adapter);
	}

	private ArrayList<StringData> readFile() {

		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/parabolic-flight/");

		String dataFileName = "android-" + name + ".txt";
		File dataFile = new File(folder, dataFileName);

		ArrayList<StringData> l = new ArrayList<StringData>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			String line , line1 = null, line2 = null;
			int index = 0;

			while ((line = br.readLine()) != null) {
				if (index == 0) {
					line1 = line;
				} else {
					line2 = line;
					l.add(new StringData(line2,line1));
				}
				
				index = 1 - index;
			}

			br.close();
		} catch (IOException e) {
		}

		return l;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}
}
