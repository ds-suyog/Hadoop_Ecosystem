package org.daa.examples.hadoop.trainAnalytics;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class LengthCountMapOnlyJob extends Configured implements Tool {
	
	public static void main(String[] args) throws Exception{
		
		String[] args2 = {"hdfs://localhost:9000/emp_table.dta/emp_table.dta",
				"hdfs://localhost:9000/user/abhay/output5"};
		
		int exitCode = ToolRunner.run(new LengthCountMapOnlyJob(), args2);
		System.exit(exitCode);
	}
 
	public int run(String[] args) throws Exception {
		
		if (args.length != 2) {
			System.err.printf("Usage: %s [generic options] <input> <output>\n",
					getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
	
		Configuration config = this.getConf();
		
		// Job job = new org.apache.hadoop.mapreduce.Job(config, "MyMapOnlyJob");
		Job job = new org.apache.hadoop.mapreduce.Job();
		job.setJarByClass(LengthCountMapOnlyJob.class);
		job.setJobName("WordCounter");
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

//		FileInputFormat.addInputPath(job, new Path("/emp_table.dta/emp_table.dta"));
//		FileOutputFormat.setOutputPath(job, new Path("/user/abhay/output5"));
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		job.setMapperClass(TrainAnalyticsMapper.class);
		// job.setReducerClass(TrainAnalyticsReducer.class);
		
		// Can also be set via -D command line option to ToolRunner
		job.setNumReduceTasks(0);
	
		int returnValue = job.waitForCompletion(true) ? 0:1;
		System.out.println("job.isSuccessful " + job.isSuccessful());
		return returnValue;
	}
}
