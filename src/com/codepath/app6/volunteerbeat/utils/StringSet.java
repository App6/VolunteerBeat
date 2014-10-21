package com.codepath.app6.volunteerbeat.utils;

import java.util.HashSet;

public class StringSet extends HashSet<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean containsValue(String val) {
		boolean found = false;
		for (String s : this) {
			found = s.equalsIgnoreCase(val);
			if (found) {
				break;
			}
		}
		return found;
	}

	public String toCommaSepString() {
		StringBuilder strBld = new StringBuilder();
		for (String t : this) {
			strBld.append(t).append(",");
		}
		return strBld.toString();
	}

	public static StringSet fromCommaSepString(String input) {
		StringSet set = new StringSet();
		if (input != null) {
			String[] vals = input.split(",");
			for (String s : vals) {
				if (s != null && !s.isEmpty()) {
					set.add(s);
				}
			}
		}
		return set;
	}
	
	public void addValue(String val) {
		if (! contains(val)) {
			add(val);
		}
	}
	
	public void removeValue(String val){
		String obj = null;
		for (String s : this) {
			if (s.equalsIgnoreCase(val)) {
				obj = s;
				break;
			}
		}
		remove(obj);
	}
}
