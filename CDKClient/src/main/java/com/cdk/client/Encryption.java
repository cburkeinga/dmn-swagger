/*
 * Class provides convenience encryption functions
 * to encrypt/decrypt passwords
 * 
 * @author: Joe Aranbayev
 * 
 * 
 */

package com.cdk.client;

import java.util.zip.CRC32;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.log4j.Logger;

/*
 * j.aranbayev
 * Encryption class to handle password encrypt/decrypt
 * TODO: store key in some file
 */

public class Encryption {
    //private static Logger logger = Logger.getLogger(Encryption.class.getName());
	final private static Logger logger = Logger.getLogger(Encryption.class);
    private byte[] key;
    
    public Encryption(String key) {
        this.key=key.getBytes();
        //logger.debug("Starting encryption with custom key...");
    }
    
    public Encryption() {
        key = "UbI>ij&]aXJ>ec)~".getBytes();
        //logger.debug("Starting encryption with default key...");
    }
    
    public String decrypt(String cipher){
        logger.debug("Decrypting cipher: "+cipher);
        String s = null;
        try{
            SecretKeySpec aesKeySpec = new SecretKeySpec(key, "AES");
            Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    
            byte[] cipherIn = DatatypeConverter.parseBase64Binary(cipher);//cipher.getBytes();
            aesCipher.init(Cipher.DECRYPT_MODE, aesKeySpec);
            byte[] clearTextDecrypt = aesCipher.doFinal(cipherIn);
            s = new String(clearTextDecrypt);
        }catch(Exception e){
            logger.error("Encryption error: ",e);
        }
        return s;
    }
    
    public String encrypt(String clear){
        String s = null;
        try{
            SecretKeySpec aesKeySpec = new SecretKeySpec(key, "AES");
            
            Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aesCipher.init(Cipher.ENCRYPT_MODE, aesKeySpec);

            byte[] clearText = clear.getBytes();
            byte[] cipherText = aesCipher.doFinal(clearText);
            //convert to Base64
            s=DatatypeConverter.printBase64Binary(cipherText);
            logger.info(" Encrypted Text: "+s);
        }catch(Exception e){
            logger.error("Encryption error: ",e);
        }
        return s;
    }
    
	public String getCRC(String s){
		CRC32 crc = new CRC32();
		crc.update(s.getBytes());
		return Long.toString(crc.getValue());
	}
    
    public static void main(String[] args) {
        Encryption enc = new Encryption();
        enc.encrypt("test");
    }
}

