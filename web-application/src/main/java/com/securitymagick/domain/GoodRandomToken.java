package com.securitymagick.domain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom; 

public class GoodRandomToken implements RandomToken {

	/**
	 *  Default Constructor
	 */
	public GoodRandomToken() {
		
	}

	@Override
	public String getNewToken() {
		// create instance of SecureRandom class 
        SecureRandom rand = new SecureRandom(); 
        // range 100000 to 999999;
        Integer newValue = rand.nextInt(900000) + 100000;
        String hexValue = Integer.toHexString(newValue);
        try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(hexValue));
			return String.format("%032x", new BigInteger(1, md5.digest()));
		} catch (NoSuchAlgorithmException e) {
			return String.format("%032x", hexValue);
		}
	}

}
