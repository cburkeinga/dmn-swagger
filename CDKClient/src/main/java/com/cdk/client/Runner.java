/*
 * Class serves as the main orchestration task that calls other
 * classes in the package. Steps are as follows:
 * 	1) parse arguments entered at runtime
 * 	2) either execute encrypt, or get-data-extract function
 * 	3) get values from .properties file
 * 	4) issue request with passed in arguments and properties file
 *  5) iterate through response and output required fields/attributes to CSV
 * 
 * Attributes are listed in the properties files; just make sure that listed
 * attributes match the CDK field names exactly!
 * 
 * Usage:
 * 		java -jar CDKClient.jar --encrypt <password>
 * 		java -jar CDKClient.jar --customer-delta-extract <timestamp MM/DD/YYY>
 * 
 * Steps to run:
 * 		1) run the encrypt password command and copy the base64 encoded cipher
 * 		to cdk-client.properties
 * 		2) as needed, modify the properties file with correct values
 * 		3) run the customer-delta-dump command and enter the delta timestamp
 * 
 * @author: Joe Aranbayev
 * 
 * 
 */

package com.cdk.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.json.JSONArray;
import org.json.JSONObject;

public class Runner {
	
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {
        //set log level
		logger.setLevel(Level.TRACE);
        Logger.getRootLogger().addAppender(new ConsoleAppender(
              new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
        logger.info("CDK Client v1");
        
        //parse args
        //--encrypt <password>
        if(args.length<2)
        	exit();
        if(args[0].compareTo("--encrypt")==0)
        	encrypt(args[1]);
        
        //--get-customer-delta <timestamp>
        if(args[0].compareTo("--customer-delta-extract")!=0)
        	exit();
        
        //properties
        Properties props = new Properties();
        InputStream is;
        try {
        	is = new FileInputStream("cdk-client.properties");
			props.load(is);
		} catch (IOException e) {
			logger.error("Failure getting properties file", e);
			System.exit(0);
		}
        logger.setLevel(Level.toLevel(props.getProperty("log.level")));
        
        // talk to CDK
		Map<String,String> formMap = new HashMap<String,String>();
    	formMap.put("dealerId", props.getProperty("dealerId"));
    	formMap.put("queryId", props.getProperty("queryId"));
    	formMap.put("deltaDate", args[1]);//pass in through args
    	
		CDKRestClient cdk = new CDKRestClient();
		Encryption encr = new Encryption();
		JSONObject json = cdk.postRequest(props.getProperty("host"), props.getProperty("path"), 
				formMap,props.getProperty("username"),encr.decrypt(props.getProperty("password")));
		logger.trace(json);
		
		JSONArray jarray = json.getJSONObject("HelpCustomer").getJSONArray("Customer");
		String attrArray[] = props.getProperty("attribute.list").split(",");
		
		//generate header
		String header ="\"";
		for(String attr:attrArray){
			header+=attr+"\",\"";
		}
		header=header.substring(0, header.length()-2);//trim off last comma
		CsvWriter csvout = new CsvWriter("CDK Customer Extract",header);
		
		//iterate through json object and write to csv
		for(int i=0;i<jarray.length();i++){
			for(String attr:attrArray){
				if(jarray.getJSONObject(i).get(attr) instanceof String){
					csvout.writeColumn(jarray.getJSONObject(i).getString(attr));
				} else {
					csvout.writeColumn(Long.toString(jarray.getJSONObject(i).getLong(attr)));
				}
			}
			csvout.writeLine();
		}
		csvout.close();
        logger.info("done!");
	}
	
	public static void exit(){
		logger.error("Usage:\njava -jar CDKClient.jar --encrypt <password>"
				+ "\njava -jar CDKClient.jar --customer-delta-extract <timestamp MM/DD/YYY>");
		System.exit(0);
	}
	
	public static void encrypt(String key){
    	Encryption encr = new Encryption();
    	encr.encrypt(key);
    	logger.info("Copy the encrypted text to cdk-client.properties password");
    	System.exit(0);
	}

}
