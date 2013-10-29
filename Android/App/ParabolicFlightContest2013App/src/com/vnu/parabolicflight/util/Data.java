package com.vnu.parabolicflight.util;

public class Data {
	private float x, y, z;
	private long time;

	public Data(float x, float y, float z, long time) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.time = time;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getZ() {
		return z;
	}
	
	public void setTime(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}
}
