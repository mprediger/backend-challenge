package com.invillia.acme.util;

public class Util {

	public static String surroundStringWith(String string, String with) {
		StringBuilder sb = new StringBuilder();
		sb.append(with);
		sb.append(string);
		sb.append(with);
		return sb.toString();
	}
}
