package com.iemr.common.bengen.service;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iemr.common.bengen.utils.Generator;

@Service
public class BengenService {
	private Logger logger = LoggerFactory.getLogger(BengenService.class);
	
	public String getFileName(String threadName, String name) {
		logger.info("BengenService.getFileName start");
		StringBuilder sb = new StringBuilder();
		sb.append(name).append("-").append(threadName).append("-")
			.append(LocalDateTime.now()).append(".csv");
		logger.info("ENV: path" + System.getenv("PATH")
						+ " user: " + System.getenv("USERNAME") 
						+ " temp: " + System.getenv("TEMP"));
		logger.info("BengenService.getFileName end = " + sb.toString());
		return sb.toString();
	}
	
	public List<String> getQueryString(int num) {
		logger.info("BengenService.getQueryString start - num = " + num);
		long start = System.currentTimeMillis();
		List<String> queryList = new ArrayList<String>();
		if(num > 0) {
			for(int i = 0; i < num; i++) {
				Generator s = new Generator();
				StringBuilder sb = new StringBuilder();
				sb.append(0).append(",").append(s.generateBeneficiaryId()).append(",")
			    	.append(false).append(",").append(false).append(",,,,,,,,")
			    	.append("'bengenApp'").append(",").append(0);
				
				queryList.add(sb.toString());
				logger.info("GenR = " + sb.toString());
			}
		}
			    
		logger.info("BengenService.getQueryString end - list = " + queryList.size() 
				+ " :time taken (ms) = " + (System.currentTimeMillis() - start));
	    return queryList;
	}
	
	
	public void encryption() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		/**
		 * SecretKey
		 * Cipher
		 * KeyGenerator
		 */
		String txt = "this is a text message!";
		
		KeyGenerator gen = KeyGenerator.getInstance("AES");
		gen.init(256); // 128 bit encryption
		SecretKey key = gen.generateKey(); // generates a 256 bit secret key
		
		// Encrypt
		Cipher encAesCipher = Cipher.getInstance("AES");
		encAesCipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encTxt = encAesCipher.doFinal(txt.getBytes());
		
		// Decrypt
		Cipher decAesCipher = Cipher.getInstance("AES");
		decAesCipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decTxt = decAesCipher.doFinal(encTxt);
		
		logger.info("txt: " + txt 
				+ "\nencTxt: " + DatatypeConverter.printHexBinary(encTxt)
				+ "\ndecTxt: " + DatatypeConverter.printHexBinary(decTxt));
	}
	
	public void hashing() throws NoSuchAlgorithmException {
		String txt = "this is a text message!";
		
		/**
		 * SecureRandom
		 * MessageDigest
		 * 
		 */
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] bytes = new byte[64];
		sr.nextBytes(bytes); // updates the bytes with salt
		md.update(bytes);
		
		// Hash
		byte[] hashedTxt = md.digest(txt.getBytes());
		
		logger.info("txt: " + txt 
				+ "\ndecTxt: " + DatatypeConverter.printHexBinary(hashedTxt));
	}
}
