package com.vnu.parabolicflight.util;

import java.io.File;
import java.io.IOException;

import com.vnu.parabolicflight.database.DatabaseHandler;

import android.os.Environment;

public class DataExporting {

	public static void Exporting(String StartTime,String type,DatabaseHandler database) {

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
		
		database.exportToFile(DataFile, type);
	}
}
