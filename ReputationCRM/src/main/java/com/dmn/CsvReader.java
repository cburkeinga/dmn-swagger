package com.dmn;

/*
 * CsvReader class that reads the provided filepath and 
 * parses the releaseId and applicationId into respective
 * ArrayLists. Getter methods implemented to query resulting
 * ArrayLists.
 * 
 * 
 * @author Joe Aranbayev
 * 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class CsvReader {
	private static Logger logger = Logger.getLogger(CsvReader.class);
	
	private ArrayList<String> fnameList;
	private ArrayList<String> lnameList;
	private ArrayList<String> emailList;
	private ArrayList<String> phoneList;
	
	CsvReader(String file){
		fnameList = new ArrayList<String>();
		lnameList = new ArrayList<String>();
		emailList = new ArrayList<String>();
		phoneList = new ArrayList<String>();
		
		logger.info("getting file: "+file);
		BufferedReader br = null;
        String line = "";
        String delimeter = ",";

        try {

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(delimeter);
                phoneList.add(data[3]);
                emailList.add(data[2]);
                lnameList.add(data[1]);
                fnameList.add(data[0]);
            }

        } catch (FileNotFoundException e) {
           logger.error(e);
        } catch (IOException e) {
        	logger.error(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                	logger.error(e);
                }
            }
        }
	}
	
	public String getPhone(int index){
		return phoneList.get(index);
	}
	
	public String getEmail(int index){
		return emailList.get(index);
	}
	public String getLname(int index){
		return lnameList.get(index);
	}

	public String getFname(int index){
		return fnameList.get(index);
	}
	
	public int getLineCount(){
		return fnameList.size();
	}
	/*
	public static void main(String[] args) {
		CsvReader creader = new CsvReader("/Users/ja064498/Desktop/input_file.csv");
		logger.info(creader.getApplicationId(0));
		logger.info(creader.getReleaseId(0));
		
		
	}
	*/
}
