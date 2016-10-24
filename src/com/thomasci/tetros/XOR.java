package com.thomasci.tetros;

public class XOR {
	public static long random(long x) {
		return shift(Long.rotateRight(shift(x), 32));
	}
	
	private static long shift(long x) {
		x ^= (x << 21);
		x ^= (x >>> 35);
		x ^= (x << 4);
		return x;
	}
}
