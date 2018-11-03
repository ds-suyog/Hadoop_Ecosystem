package org.daa.examples.hadoop.trainAnalytics;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
 
public class TrainAnalyticsMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
 
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	static int numRecords = 0;
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		System.out.println("in mapperrr");		
		System.out.println("line = " + line); 	// line = 1000 | 1000 | 60 | E //mine
		StringTokenizer st = new StringTokenizer(line," | "); 
		
		int garbageFlag=0, counter=0; //mine
		String [] tokenArray = new String [4];
		
		while(st.hasMoreTokens()){
			counter+=1;
			tokenArray[counter-1] = st.nextToken();
		
			System.out.println("counter= " + counter + ", token = " + tokenArray[counter-1]);
			
			//additional code to handle garbage data: regex match 2-4 digits. direction column excluded 
			if (counter!=4){									
				//if(tok.matches("^-?\\d+$")){System.out.println("token match = " + tokenArray[ctr-1]);}
				if(tokenArray[counter-1].matches("^\\d{2,4}$")){System.out.println("token match = " + tokenArray[counter-1]);}
				else {	
					garbageFlag=1;
					break;}
			}	
			
			//keeping floor value, so 0900 means [0900,1000), 1000 means [1000,1100)..0900 represent 9th hour, and so on..
			if(counter==2) {
				tokenArray[counter-1] = (Integer.parseInt(tokenArray[counter-1])<1000)? "0900" : ((Integer.parseInt(tokenArray[counter-1])<1100)? "1000" : "1100");
			}
	
			word.set(tokenArray[counter-1]);
			if(counter==1)	context.write(word,one);
				
		}

		//iii. How many trains are traveling in each direction in each hour
		String keyQ3 = tokenArray[3] + tokenArray[1];
		//iv.  What is the average speed of each train traveling in each direction in each hour.
		String keyQ4 = tokenArray[3] + tokenArray[1] + tokenArray[0];
		
		System.out.println("keyQ3 =" + keyQ3 + ", keyQ4 = " + keyQ4);
		
		word.set(keyQ3);
		if(garbageFlag==0)	context.write(word,one);
		//setting speed as value, so I can calculate average at reducer
		word.set(keyQ4);
		if(garbageFlag==0)	context.write(word,new IntWritable(Integer.parseInt(tokenArray[2])));
		
		if(garbageFlag==0) numRecords+=1;
		System.out.println("number of records = " + numRecords + "\n");
		
		// Add a custom counter
		//context.getCounter("MyCounterGroup", "MyCounter").increment(1);

	}
}
