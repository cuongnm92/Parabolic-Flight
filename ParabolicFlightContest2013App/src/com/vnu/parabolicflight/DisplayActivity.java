package com.vnu.parabolicflight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends Activity {

	String fileName;
	Button returnButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		fileName = b.getString("file");

		TextView tv = (TextView) findViewById(R.id.file_content);
		tv.setText(readFile());

		TextView tv1 = (TextView) findViewById(R.id.display_title);
		tv1.setText("Log File : " + fileName);

		returnButton = (Button) findViewById(R.id.return_button);
		returnButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent1 = new Intent(DisplayActivity.this,
						MainActivity.class);
				startActivity(intent1);
				finish();
			}
		});
	}

	private StringBuilder readFile() {

		File dataFile = new File(Environment.getExternalStorageDirectory()
				.toString(), fileName);

		// Read text from file
		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			String line;

			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}

			br.close();
		} catch (IOException e) {
		}

		return text;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}

}