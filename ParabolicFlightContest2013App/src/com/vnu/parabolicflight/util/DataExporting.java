package com.vnu.parabolicflight.util;

import android.annotation.SuppressLint;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@SuppressLint("SimpleDateFormat")
public class DataExporting {

	public static void Exporting(ArrayList<Data> list, File fileData) {
		int n = list.size();
		Data d;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

		try {
			FileWriter fw = new FileWriter(fileData);

			for (int i = 0; i < n; i++) {
				d = list.get(i);
				fw.write(df.format(d.getTime()) + " " + Long.toString(d.getTime()) + "\n");
				fw.write(Float.toString(d.getX()) + " " + Float.toString(d.getY()) + " " + Float.toString(d.getZ()) + "\n");
			}

			fw.close();

		} catch (IOException e) {
		}

	}
}