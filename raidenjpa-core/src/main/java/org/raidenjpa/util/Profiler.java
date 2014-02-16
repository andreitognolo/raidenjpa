package org.raidenjpa.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Profiler {

	private static Profiler INSTANCE;
	
	private Map<String, Long> initTimes = new HashMap<String, Long>();
	
	private Map<String, Long> duration = new HashMap<String, Long>();
	
	public void start(String name) {
		initTimes.put(name, System.currentTimeMillis());
	}

	public void finish(String name) {
		Long init = initTimes.get(name);
		long diff = System.currentTimeMillis() - init;
		duration.put(name, diff);
	}
	
	public void showReport() {
		List<Entry<String, Long>> entry = new ArrayList<Entry<String, Long>>(duration.entrySet());
		
		Collections.sort(entry, new Comparator<Entry<String, Long>>() {

			public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
			
		});
		
		System.out.println("Show Report (desc ordem)");
		
		for (int i = 0; i < 20; i++) {
			System.out.println(entry.get(i).getKey() + " - " + entry.get(i).getValue());
		}
	}
	
	public static Profiler me() {
		if (INSTANCE == null) {
			INSTANCE = new Profiler();
		}
		
		return INSTANCE;
	}
}