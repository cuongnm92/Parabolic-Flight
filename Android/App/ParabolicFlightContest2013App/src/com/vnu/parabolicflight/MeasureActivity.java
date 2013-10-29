package com.vnu.parabolicflight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MeasureActivity extends Activity {

	private final String START_TIME = String
			.valueOf(System.currentTimeMillis());

	Button stopButton, returnButton;
	boolean notStop = true;
	boolean display = false;

	private SensorManager mSensorManager;
	private Sensor mGyroSensor, mAccSensor, mGraSensor;

	TextView gyroscope_xval, gyroscope_yval, gyroscope_zval;
	TextView accelerometer_xval, accelerometer_yval, accelerometer_zval;
	TextView gravity_xval, gravity_yval, gravity_zval;

	FileWriter accelerometerFile, gyroscopeFile, gravityFile;

	private ProgressDialog progressDialog;

	private PowerManager.WakeLock wl;
	private PowerManager pm;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measure);

		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "DIM");
		wl.acquire();

		gyroscope_xval = (TextView) findViewById(R.id.gyroscope_xval);
		gyroscope_yval = (TextView) findViewById(R.id.gyroscope_yval);
		gyroscope_zval = (TextView) findViewById(R.id.gyroscope_zval);

		accelerometer_xval = (TextView) findViewById(R.id.accelerometer_xval);
		accelerometer_yval = (TextView) findViewById(R.id.accelerometer_yval);
		accelerometer_zval = (TextView) findViewById(R.id.accelerometer_zval);

		gravity_xval = (TextView) findViewById(R.id.gravity_xval);
		gravity_yval = (TextView) findViewById(R.id.gravity_yval);
		gravity_zval = (TextView) findViewById(R.id.gravity_zval);
		
		returnButton = (Button) findViewById(R.id.return_button);
		returnButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent1 = new Intent(MeasureActivity.this, MainActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		
		stopButton = (Button) findViewById(R.id.stop_button);
		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				if (display) {
					final CharSequence[] items = { "accelerometer",
							"gyroscope", "gravity" };

					AlertDialog.Builder builder = new AlertDialog.Builder(
							MeasureActivity.this);
					builder.setTitle(R.string.title_display_dialog);
					builder.setItems(items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int item) {
									Intent intent = new Intent(
											MeasureActivity.this,
											DisplayActivity.class);
									Bundle b = new Bundle();
									b.putString("file", items[item].toString()
											+ "-" + START_TIME);
									intent.putExtras(b);
									startActivity(intent);
									finish();
								}
							});
					AlertDialog alert = builder.create();
					alert.show();

				} else {
					display = true;
					notStop = false;

					progressDialog = ProgressDialog.show(MeasureActivity.this,
							"", "Exporting data...");

					new Thread() {

						public void run() {

							try {
								accelerometerFile.close();
								gyroscopeFile.close();
								gravityFile.close();
							} catch (Exception e) {
								Log.e("exporting_data", e.getMessage());
							}

							progressDialog.dismiss();
						}

					}.start();

					stopButton.setText("DISPLAY LOG FILE");
				}
			}
		});

		initSensor();

		try {
			initFile();
		} catch (IOException e) {
		}
	}

	private void initSensor() {
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mGraSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
	}

	private void initFile() throws IOException {
		accelerometerFile = FileWriterPointer(START_TIME,"accelerometer");
		gyroscopeFile = FileWriterPointer(START_TIME, "gyroscope");
		gravityFile = FileWriterPointer(START_TIME, "gravity");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.measure, menu);
		return true;
	}

	private SensorEventListener mGyroListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			getGyroscope(event);
		}
	};

	private SensorEventListener mAccListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			getAccelerometer(event);
		}
	};

	private SensorEventListener mGraListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			getGravity(event);
		}

	};

	private void getAccelerometer(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		if (notStop) {
			try {
				accelerometerFile.write(String.valueOf(System.currentTimeMillis()) + "\n");
				accelerometerFile.write(Float.toString(x) + " " + Float.toString(y) + " " + Float.toString(z) + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			accelerometer_xval.setText("x:" + "\t\t" + Float.toString(x));
			accelerometer_yval.setText("y:" + "\t\t" + Float.toString(y));
			accelerometer_zval.setText("z:" + "\t\t" + Float.toString(z));
		}
	}

	private void getGyroscope(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		if (notStop) {
			try {
				gyroscopeFile.write(String.valueOf(System.currentTimeMillis()) + "\n");
				gyroscopeFile.write(Float.toString(x) + " " + Float.toString(y) + " " + Float.toString(z) + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

			gyroscope_xval.setText("x:" + "\t\t" + Float.toString(x));
			gyroscope_yval.setText("y:" + "\t\t" + Float.toString(y));
			gyroscope_zval.setText("z:" + "\t\t" + Float.toString(z));
		}
	}

	private void getGravity(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		if (notStop) {
			try {
				gravityFile.write(String.valueOf(System.currentTimeMillis()) + "\n");
				gravityFile.write(Float.toString(x) + " " + Float.toString(y) + " " + Float.toString(z) + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

			gravity_xval.setText("x:" + "\t\t" + Float.toString(x));
			gravity_yval.setText("y:" + "\t\t" + Float.toString(y));
			gravity_zval.setText("z:" + "\t\t" + Float.toString(z));
		}
	}

	public FileWriter FileWriterPointer(String StartTime, String type)
			throws IOException {
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/parabolic-flight/");

		if (!folder.exists())
			folder.mkdir();

		String dataFileName = "android-" + type + "-" + StartTime + ".txt";

		File DataFile = new File(folder, dataFileName);

		if (DataFile.exists())
			DataFile.delete();

		try {
			DataFile.createNewFile();
		} catch (IOException e) {
		}

		return new FileWriter(DataFile);
	}

	public void sensorRegister() {

		int rating = 50000;

		mSensorManager.registerListener(mGyroListener, mGyroSensor, rating);
		mSensorManager.registerListener(mAccListener, mAccSensor, rating);
		mSensorManager.registerListener(mGraListener, mGraSensor, rating);
	}

	public void sensorUnregister() {
		mSensorManager.unregisterListener(mGyroListener, mGyroSensor);
		mSensorManager.unregisterListener(mAccListener, mGyroSensor);
		mSensorManager.unregisterListener(mGraListener, mGraSensor);
	}

	@Override
	protected void onResume() {
		super.onResume();
		wl.acquire();
		sensorRegister();
	}

	@Override
	protected void onPause() {
		sensorUnregister();
		wl.release();
		super.onPause();
	}

	@Override
	public void onStop() {
		sensorUnregister();
		wl.release();
		super.onStop();
	}
}
