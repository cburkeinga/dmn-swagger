The CDK Client java executable was written to satisfy DMN's data acquisition needs
CDK Client operation is as follows:
	1) parse arguments entered at runtime (command line)
	2) either execute encrypt, or get-data-extract function
	3) get values from .properties file
	4) issue request with passed in arguments and values in properties file
 	5) iterate through response and output required fields/attributes to CSV

Attributes are listed in the properties files; just make sure that they match the CDK field names exactly!

Usage:
		java -jar CDKClient.jar --encrypt <password>
		java -jar CDKClient.jar --customer-delta-extract <timestamp MM/DD/YYY>

Steps to execute client:
		1) run the encrypt password command and copy the base64-encoded cipher to cdk-client.properties
		2) as needed, modify the properties file with correct values
		3) run the customer-delta-extract command with the delta timestamp (MM/DD/YYYY)

File structure:
parent folder/
	CDKClient.jar
	cdk-client.properties
	output-file.csv


@author: Joe Aranbayev
