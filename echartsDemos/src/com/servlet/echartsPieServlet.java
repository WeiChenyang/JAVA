package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class echartsServlet
 */
public class echartsPieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public echartsPieServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject json = new JSONObject();
		List<String> list1 = new ArrayList<String>();
		List<Integer> list2 = new ArrayList<Integer>();
		List<Integer> list3 = new ArrayList<Integer>();
		list1.add("Android");
		list1.add("IOS");
		list1.add("PC");
		list1.add("Other");
		list2.add(420);
		list2.add(200);
		list2.add(100);
		list2.add(360);
		list3.add(500);
		list3.add(700);
		list3.add(600);
		list3.add(400);
		json.put("name", list1);
		json.put("data", list2);
		json.put("data2", list3);
		System.out.println(json);
		response.getWriter().write(json.toString());
		
		//request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		List<String> list = new ArrayList<String>();
		json.put("Android", 500);
		json.put("IOS", 200);
		json.put("PC", 360);
		json.put("Ohter", 100);
		System.out.println(json);
		list.add(json.toString());
		System.out.println(list.toString());
	}
}
