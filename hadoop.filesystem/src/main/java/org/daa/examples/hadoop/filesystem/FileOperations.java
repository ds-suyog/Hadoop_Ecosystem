package org.daa.examples.hadoop.filesystem;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;

public class FileOperations {

	private Configuration conf;

	private FileSystem fs;
	
	String HDFSUri = "hdfs://localhost:9000/";

	public FileOperations() throws IOException {
		conf = new Configuration();
		conf.set("fs.defaultFS", HDFSUri);
		System.out.println("fs.defaultFS : " + conf.get("fs.defaultFS"));

		fs = FileSystem.get(conf);
	}

	public void list(Path path) throws FileNotFoundException, IOException {

		System.out.println("Printing list files... ");
			
		RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(path, false);
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getPath());
		}
		System.out.println("Done...");
	}

	public void read(Path path) throws IOException {
		
		if ( fs.exists(path) ) {
			System.out.println("Path exists");
		} else {
			System.out.println("Path does not exist");
		}
		
		FSDataInputStream inputStream = fs.open(path);
		IOUtils.copyBytes(inputStream, System.out, 50, false);
	    fs.close();		
	}
	
	private void printHomeDir() {
		System.out.println("Printing home dir");
		System.out.println(fs.getHomeDirectory());
	}
	
	private void printFsStatistics() throws IOException {
		System.out.println("Printing FS statistics");
		fs.printStatistics();
		// System.out.println(fs.printStatistics(););
	}
	
	public static void main(String[] args) throws IOException {

		FileOperations ops = new FileOperations();
		String pathLoc = "/emp_table.dta/emp_table.dta";
		ops.list(new Path (pathLoc));
		ops.read(new Path(pathLoc));
		ops.printHomeDir();
		ops.printFsStatistics();
	}

}
