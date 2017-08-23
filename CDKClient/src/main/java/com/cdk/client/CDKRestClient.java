/*
 * Class issues calls to CDK REST services.
 * Current implementation has been tested with POST Help Customer Extract
 * service. postRequest method has been written generally enough
 * to make any available call (hasn't been tested).
 * 
 * @author: Joe Aranbayev
 * 
 * 
 */

package com.cdk.client;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONObject;
import org.json.XML;

public class CDKRestClient {
	
	private static Logger logger = Logger.getLogger(CDKRestClient.class);
	private SSLContext sslContext;
	
	public CDKRestClient(){
		logger.info("talking to CDK...");

        //create sslcontext that accepts all certs
	    try {
			sslContext = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e) {
			logger.fatal(e);
		}
        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        try {
			sslContext.init(null, new TrustManager[] { tm }, null);
		} catch (KeyManagementException e) {
			logger.fatal(e);
		}
	}
	
	public JSONObject postRequest(String target,String uri,
			Map<String,String> formMap,String username,String password){

        //Create client object
        Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
        
        //set api target
        WebTarget webTarget = client.target(target);
        
        //add basic auth header
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
        webTarget.register(feature);
        
        //Populate form
        WebTarget restMethod = webTarget.path(uri);
        if(formMap!=null){
	        Iterator<Entry<String, String>> it = formMap.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry<String,String> pair = (Map.Entry<String,String>)it.next();
	            restMethod = restMethod.queryParam(pair.getKey(), pair.getValue());      
	//            logger.debug(pair.getKey() + " = " + pair.getValue() + " : " + formMap.get(pair.getKey()).getClass());
	            it.remove(); // avoids a ConcurrentModificationException
	        }
        }
        
        logger.debug("REST path: "+restMethod.getUri());

        long startTime = System.nanoTime();
        Response response = restMethod.request().post(null);
        long endTime = System.nanoTime();
		double elapsedTime = (endTime-startTime)/1e6;
        logger.debug("Response status code: "+response.getStatus()+
        		"\tResponse status info: "+response.getStatusInfo()+
        		"\tResponse time: "+elapsedTime+"ms");

        String XMLs = response.readEntity(String.class);
        JSONObject json = XML.toJSONObject(XMLs);
        logger.trace(json);
        return json;

	}

}
