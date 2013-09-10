package com.vnu.parabolicflight;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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

import com.vnu.parabolicflight.database.DatabaseHandler;
import com.vnu.parabolicflight.database.Data;
import com.vnu.parabolicflight.util.DataExporting;

public class MeasureActivity extends Activity {

	Button stopButton, returnButton;
	boolean notStop = true;
	boolean display = false;
	
	private final String START_TIME = String.valueOf(System.currentTimeMillis()); 
	
	private SensorManager mSensorManager;
	private Sensor mGyroSensor, mAccSensor, mGraSensor;

	TextView gyroscope_xval, gyroscope_yval, gyroscope_zval;
	TextView accelerometer_xval, accelerometer_yval, accelerometer_zval;
	TextView gravity_xval, gravity_yval, gravity_zval;

	private ProgressDialog progressDialog;
	
	private PowerManager.WakeLock wl;
	private PowerManager pm;

	DatabaseHandler database;
	
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

		stopButton = (Button) findViewById(R.id.stop_button);
		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				if (display) {
					final CharSequence[] items = {"accelerometer", "gyroscope", "gravity"};

					AlertDialog.Builder builder = new AlertDialog.Builder(MeasureActivity.this);
					builder.setTitle(R.string.title_display_dialog);
					builder.setItems(items, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	Intent intent = new Intent(MeasureActivity.this,
									DisplayActivity.class);
							Bundle b = new Bundle();
							b.putString("file", items[item].toString() + "-" + START_TIME);
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
								DataExporting.Exporting(START_TIME,"accelerometer",database);
								DataExporting.Exporting(START_TIME,"gyroscope",database);
								DataExporting.Exporting(START_TIME, "gravity",database);
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
		initData();
	}

	private void initData() {
		database = new DatabaseHandler(this);
	}

	private void initSensor() {
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mGraSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
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
			database.addAccelerometerData(new Data(x, y, z, System.currentTimeMillis()));
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
			database.addGyroscopeData(new Data(x, y, z, System.currentTimeMillis()));
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
			database.addGravityData(new Data(x, y, z, System.currentTimeMillis()));

			gravity_xval.setText("x:" + "\t\t" + Float.toString(x));
			gravity_yval.setText("y:" + "\t\t" + Float.toString(y));
			gravity_zval.setText("z:" + "\t\t" + Float.toString(z));
		}
	}

	public void sensorRegister() {

		// int rating = 100000;
		
		// mSensorManager.registerListener(mGyroListener, mGyroSensor, rating);
		// mSensorManager.registerListener(mAccListener, mAccSensor, rating);
		// mSensorManager.registerListener(mGraListener, mGraSensor, rating);
		
		mSensorManager.registerListener(mGyroListener, mGyroSensor, SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(mGyroListener, mAccSensor, SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(mGyroListener, mGraSensor, SensorManager.SENSOR_DELAY_FASTEST);
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
