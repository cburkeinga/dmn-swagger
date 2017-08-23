/*
 * CsvWriter class that generates csv files delimeted
 * by "a","b" so as not to interfere with strings that 
 * have a comma. Filenames automatically get postfixed 
 * with timestamp so as not to cause errors with
 * duplicate files.
 * 
 * 
 * @author Joe Aranbayev
 * 
 */
package com.cdk.client;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;


public class CsvWriter {
	private static Logger logger = Logger.getLogger(CsvWriter.class);
	
	private FileWriter fw;
	private PrintWriter out;

	final private String DELIM = "\",\"";

	private String csv_line="\"";

	CsvWriter(String fileName, String csvHeader){
		logger.info("writting to csv...");
		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyy HH.mm");
		String timeStamp = sdf.format(now);
		try {
			fw = new FileWriter(fileName+" "+timeStamp+".csv");
			out = new PrintWriter(fw);
		} catch (IOException e) {
			logger.error("Failed to write CSV", e);
		}
		//write filename to file
		out.println(fileName);
		//if header not empty write to file
		if(csvHeader!=null){
			out.println(csvHeader);
		}
	}

	public void writeColumn(String csvInput) {
		csv_line+=csvInput+DELIM;
	}

	public void writeLine(){

		//remove the delimeter from end of line
		csv_line = csv_line.substring(0, csv_line.length()-(DELIM.length()-1));

		// add line to print writer
		out.println(csv_line);
		// clear csv string
		csv_line="\"";
	}

	public void close(){
		//Flush the output to the file
		out.flush();

		//Close the Print Writer
		out.close();

		//Close the File Writer
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public static void main(String[] args){
		String line1 = "I am a happy dog and I like hotdogs";
		String line2 = "I am a happy cat and I like milk";

		String header = "col1,col2,col3,col4";

		CsvWriter csv_writer = new CsvWriter("test",header);

		String[] line1arr = line1.split(" ");
		String[] line2arr = line2.split(" ");

		for(String col : line1arr){
			csv_writer.writeColumn(col);
		}
		csv_writer.writeLine();
		for(String col : line2arr){
			csv_writer.writeColumn(col);
		}
		csv_writer.writeLine();
		csv_writer.close();
	}
	*/
}
