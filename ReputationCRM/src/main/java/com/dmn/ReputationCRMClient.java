package com.dmn;

import java.util.List;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
//import com.dmn.Customer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import io.swagger.client.api.CustomerApi;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.model.Customer;
import io.swagger.client.model.CustomerWhoHaveNotResponded;




/**
 * Hello world!
 *
 */
public class ReputationCRMClient 
{
	private static Logger logger = Logger.getLogger(ReputationCRMClient.class);
	private final CustomerApi api = new CustomerApi();
	
	public ReputationCRMClient() {
		// TODO Auto-generated constructor stub
	}

	private void initAPI(String apikey) {
		// TODO Auto-generated method stub
		
		logger.info("Initializing API with key: " + apikey);
		ApiClient defaultClient = Configuration.getDefaultApiClient();
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey(apikey);
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

	}

	/**
     * Create a Customer AND a Feedback / Review Request Sequence with Automatic Scheduling (Set a Delay before sending the First Feedback / Review Request)
     *
     * This API call creates a Customer in Reputation Builder, AND schedules an Automatic Email AND / OR SMS Feedback / Review Request Sequence, WITH A DELAY BEFORE SENDING THE FIRST FEEDBACK / REVIEW REQUEST, based on settings of the Company / Location -&gt; Notifications -&gt; Email AND SMS Tabs 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createCustomerAutomaticWithDelayReviewRequestTest(Customer c) throws ApiException {
        
    	String sendtype = "1,2";
    	String delayBeforeFirstReviewRequest = "10";
        
        api.createCustomerAutomaticWithDelayReviewRequest(c.getCompanyid(), c.getFirstname(), c.getLastname(), sendtype, delayBeforeFirstReviewRequest,c.getEmail(), c.getMobile(), c.getAddress(), c.getZipcode(), c.getCity(), c.getState(), c.getCountry(), c.getLanguage(), c.getNotes());

        // TODO: test validations
    }
	
	
	
    /**
     * Create a Customer AND a Feedback / Review Request Sequence with Automatic Scheduling (No delay before sending the First Feedback / Review Request)
     *
     * This API call creates a Customer in Reputation Builder, AND schedules an Automatic Email AND / OR SMS Feedback / Review Request Sequence, WITH NO DELAY BEFORE SENDING THE FIRST FEEDBACK / REVIEW REQUEST, based on settings of the Company / Location -&gt; Notifications -&gt; Email AND SMS Tabs 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    
    public String createCustomerAutomatic(Customer c) throws ApiException {
    	String sendtype = "1,2";
        String response = api.createCustomerAutomatic(c.getCompanyid(), c.getFirstname(), c.getLastname(), sendtype, c.getEmail(), c.getMobile(), c.getAddress(), c.getZipcode(), c.getCity(), c.getState(), c.getCountry(), c.getLanguage(), c.getNotes());
        logger.info("Create Customer Automatic Test result: " + response);
        // TODO: test validations
        return response;
    }
    
    /**
     * Create a Customer AND Feedback / Review Requests
     *
     * This API call creates a Customer in Reputation Builder AND Feedback / Review Request #1 (Email #1 AND/OR SMS #1 ONLY). Feedback / Review Request(s) are then sent immediately 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createCustomerImmediateFirstReviewRequest(Customer c) throws ApiException {
        String companyid = null;
        String firstname = null;
        String lastname = null;
        String sendtype = "1";
        String email = null;
        String mobile = null;
        String address = null;
        String zipcode = null;
        String city = null;
        String state = null;
        String country = null;
        String language = null;
        String notes = null;

        api.createCustomerImmediateFirstReviewRequest(c.getCompanyid(), c.getFirstname(), c.getLastname(), sendtype, c.getEmail(), c.getMobile(), c.getAddress(), c.getZipcode(), c.getCity(), c.getState(), c.getCountry(), c.getLanguage(), c.getNotes());

        // TODO: test validations
    }
      
    
    /**
     * Get Information on ALL Customers of a Company / Location / Feedback Page
     *
     * This API call returns information from Reputation Builder on ALL existing Customers of a Company / Location / Feedback Page
     *
     * @throws ApiException
     *          if the Api call fails
     */
    
    public void getAllCustomersBycompanyId(String companyid) throws ApiException {
        
        List<Customer> response = api.getAllCustomersBycompanyId(companyid);
        
        logger.info("Called Get all Customers.. now print");
        
        for (Customer cust : response) {
            // 1 - can call methods of element
        	System.out.println("Email: " + cust.getEmail() + " " + cust.getCountry() + " " + cust.getLanguage());
        	
            // ...
        }
        // TODO: test validations
    }
    
    /**
     * Get a list of Customers who have not yet submitted their Review / Feedback
     *
     * This API call returns the list of Customers of a Company / Location / Feedback Page who have not yet responded to Feedback / Review Requests
     *
     * @throws ApiException
     *          if the Api call fails
     */
    //@Test
    public void getCustomersWhoHaveNotYetSubmittedReviewTest() throws ApiException {
        String companyid = null;
        List<CustomerWhoHaveNotResponded> response = api.getCustomersWhoHaveNotYetSubmittedReview(companyid);

        // TODO: test validations
    }
    
    /**
     * Get Information of ONE Customer
     *
     * This API call returns information from Reputation Builder on ONE existing Customer
     *
     * @throws ApiException
     *          if the Api call fails
     */
    //@Test
    public void getSingleCustomer(String customerid) throws ApiException {
        
        List<Customer> response = api.getSingleCustomer(customerid);

        // TODO: test validations
    }
    
    /**
     * Update the record of ONE Customer
     *
     * This API call updates information from Reputation Builder on ONE existing Customer
     *
     * @throws ApiException
     *          if the Api call fails
     */
    //@Test
    public void updateCustomerTest() throws ApiException {
        String customerid = null;
        String firstname = null;
        String lastname = null;
        String notes = null;
        String email = null;
        String mobile = null;
        String address = null;
        String zipcode = null;
        String city = null;
        String state = null;
        String country = null;
        String language = null;
        api.updateCustomer(customerid, firstname, lastname, notes, email, mobile, address, zipcode, city, state, country, language);

        // TODO: test validations
    }

	private static void exit(){
		logger.error("usage:\njava -jar ReputationCRMClient.jar <source filename.csv> <properties file>");
		System.exit(0);
	}
	
	
	
    public static void main( String[] args )
    {
    	PropertyConfigurator.configure("log4j.properties");
    	logger.setLevel(Level.INFO);
    	logger.info( "Lets Do This!");
        String inputfile = args[0];
        String configfile = args[1];
        ReputationCRMClient rcc = new ReputationCRMClient();
        CsvReader cr = new CsvReader(inputfile);
        
        Properties prop = new Properties();
        InputStream input = null;
       
        try {
        	
        	input = new FileInputStream(configfile);

    		// load a properties file
    		prop.load(input);
        	
    		// get the property value and print it out
    		logger.info(prop.getProperty("companyid"));
    		logger.info(prop.getProperty("apikey"));
    		
        	rcc.initAPI(prop.getProperty("apikey"));
    		
    		Customer c = new Customer();
     		c.setCompanyid(prop.getProperty("companyid"));
    		c.setFirstname("CHARLES");
            c.setLastname("BURKE");          
            c.setEmail("CBURKE@BURKEITCONSULTING.COM");
            c.setMobile("7708737936");
            c.setNotes("Test user");
            c.setAddress("1202 Little hawk");
            c.setCity("Lawrenceville");
            c.setState("GA");
            c.setZipcode("30043");
            c.setCountry("United States");
            c.setLanguage("en");
            
    		try {
    			String result = rcc.createCustomerAutomatic(c);
    			//rcc.createCustomerImmediateFirstReviewRequest(c);
    			//rcc.createCustomerAutomaticWithDelayReviewRequestTest(c);
    			logger.info(result);
    			//logger.info("Called create customer");
    			rcc.getAllCustomersBycompanyId(c.getCompanyid());
    		} catch (Exception e) {
    			//logger.error(e.getMessage());
    			e.printStackTrace();
    		}
    		

    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
        logger.info("End Execution");
        
    }

}
