package com;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Main {
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		/*JSONArray jsonArray = new JSONArray();
		json.put("Android", 420);
		json.put("IOS",200);
		json.put("PC",360);
		json.put("Other",100);
		System.out.println(json);
		jsonArray.add(json);
		System.out.println(jsonArray);*/
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		list1.add("Android");
		list1.add("IOS");
		list1.add("PC");
		list1.add("Other");
		list2.add(420);
		list2.add(200);
		list2.add(100);
		list2.add(360);
		json.put("name", list1);
		json.put("data", list2);
		System.out.println(json);
	}
}
