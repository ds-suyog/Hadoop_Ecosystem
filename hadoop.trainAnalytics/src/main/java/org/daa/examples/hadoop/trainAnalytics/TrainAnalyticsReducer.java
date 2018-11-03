package org.daa.examples.hadoop.trainAnalytics;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TrainAnalyticsReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	int countPipe=0, displayFlag = 0;
	static int distinctTrain=0, totalTrainRecords=0, q3=0, avgSpeed = 0; 
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context)
			throws IOException, InterruptedException {
		
		//System.out.println("In Reducerr");
		

		String strKey = "" + key; //converting type 'Text' into String for operations
		
		//Key = 1010. trainNo
		if(strKey.matches("^\\d{2,4}$")) {
			//System.out.println("key = " + strKey);
			int sum1=0;
			for(IntWritable val : values) {
				if (strKey.equalsIgnoreCase("|")){	
					System.out.println("| match");
					countPipe+=1;
				}
				//System.out.println("key = " + key + ", value = " + val);
				sum1 = sum1 + val.get();
			}
			
			totalTrainRecords+=sum1; 
			distinctTrain+=1; 
			System.out.println("key = " + strKey + ", totTrainRecords = " + totalTrainRecords + ", distinctTrain = " + distinctTrain);
			context.write(key, new IntWritable(sum1));	
		}
		
		//key = W1100. W -> direction, 1100 -> 11th hour, values -> 1
		else if(strKey.matches("^[A-Z]\\d{2,4}$")) {
			//System.out.println("key = " + strKey);
			int sum2=0;
			for(IntWritable val : values) {
				//System.out.println("key = " + key + ", value = " + val);
				sum2 = sum2 + val.get();
			}
			q3+=sum2;
			System.out.println("key = " + strKey + ", total = " + sum2 + ", q3 ="  + q3);
			context.write(key, new IntWritable(sum2));
				
		}
		
		//keyQ4 = W11001003.  W -> direction, 1100 -> 11th hour, 1003 -> trainNo., values -> speed
		else if(strKey.matches("^[A-Z]\\d{2,4}\\d{2,4}$")) {
			//System.out.println("key = " + strKey);
			int sum3=0, count =0;
			for(IntWritable val : values) {
				//System.out.println("key = " + key + ", value = " + val);
				sum3 = sum3 + val.get();
				count+=1;
			}
			avgSpeed = sum3/count;
			System.out.println("key = " + strKey + ", count = " + count + ", average Speed = " + avgSpeed);
			context.write(key, new IntWritable(avgSpeed));
				
		} 

		//context.write(new Text("hello"), new IntWritable(111));  #m #o/p goes in part-r-00000

	}	
}
