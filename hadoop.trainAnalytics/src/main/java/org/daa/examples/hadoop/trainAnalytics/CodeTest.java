package org.daa.examples.hadoop.trainAnalytics;

import java.util.ArrayList;

//Test Code snippets here

public class CodeTest {

	public static void main(String[] args) {
		// your code snippets here
		//System.out.println("Hello IDE!!!!"+args[0]);
		System.out.println((1+"E").getClass());
		String str1 = "E" + 9000 ;
		String str2 = "E" + 9000;
		String str3 = 9000 + "W";
		String str4 = 9000 + "W";
		System.out.println(str1 + "  " + str2 + "  " + str3);
		System.out.println(str1.getClass());
		System.out.println(str3.getClass());
		System.out.println(str1==str2);
		System.out.println(str3==str4);
		
		ArrayList<String> listt = new ArrayList<String> ();
		listt.add("1001");
		listt.add("0900");
		listt.add("60");
		listt.add("E");
		System.out.println(listt.get(0));
		System.out.println(listt.get(1));
		String keyQ3 = listt.get(3) + listt.get(1);
		System.out.println(keyQ3);
		String keyQ4 = listt.get(3) + listt.get(1) + listt.get(0);
		System.out.println(keyQ4);
		
		String [] sa = new String [4];
		sa[0] = str1;
		sa[2] = str3;
		System.out.println(sa[0].getClass());
		
		String str = "0999";
		System.out.println(str.substring(0,2));
		System.out.println(Integer.parseInt(str)<1000);
		System.out.println((Integer.toString(1000)).getClass());	
		System.out.println(Integer.toString(1000) + "E");
		str = (Integer.parseInt(str)<1000)? "0900" : ((Integer.parseInt(str)<1100)? "1000" : "1100");
		System.out.println(str);
		double i=0,j =0;
		System.out.println("i and j = " + i + " " + j);
		j=23/2.0;
		System.out.println(j);
	}
}
