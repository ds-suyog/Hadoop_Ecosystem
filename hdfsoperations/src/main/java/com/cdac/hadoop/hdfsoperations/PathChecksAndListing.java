package com.cdac.hadoop.hdfsoperations;

import java.io.IOException;
import java.net.URI;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

public class PathChecksAndListing {

	public static void main(String[] args) {

		// String hdfsUri = args[0];
		String hdfsUri = "hdfs://localhost:9000/";
		// String hdfsUri = "/";
		Path hdfsPath = new Path(hdfsUri);

		Configuration config = new Configuration();
		config.set("fs.defaultFS", "hdfs://localhost:9000/");

		for (Entry<String, String> conf : config) {
			System.out.println(conf.getKey() + "=" + conf.getValue());
		}

		System.out.println("======== Given config =======  \n " + config);

		try {
			FileSystem fs = FileSystem.get(URI.create(hdfsUri), config);
			System.out.println(fs.getScheme());

			String fsPathLoc = "/";
			Path fsPath = new Path(fsPathLoc);
			if (fs.exists(fsPath)) {
				System.out.println("Path " + fsPathLoc + " exists");
			}else{
				System.out.println("Path " + fsPathLoc + " does not exists");
			}
			
			RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(fsPath, false);
			
			while(iterator.hasNext()){
				System.out.println(iterator.next().getPath());
			}
					

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		// }
	}

}
