package com.vnu.parabolicflight.util;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataExporting {

	public static void FileMaker(String StartTime, String type) {
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
	}

	public static void Exporting(String StartTime, String type,
			ArrayList<Data> list) {

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

		int n = list.size();
		Data d;

		try {
			FileWriter fw = new FileWriter(DataFile);

			for (int i = 0; i < n; i++) {
				d = list.get(i);
				fw.write(Long.toString(d.getTime()) + "\n");
				fw.write(Float.toString(d.getX()) + " "
						+ Float.toString(d.getY()) + " "
						+ Float.toString(d.getZ()) + "\n");
			}

			fw.close();

		} catch (IOException e) {
		}

	}
}
