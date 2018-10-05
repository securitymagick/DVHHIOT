package com.securitymagick.domain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class BadRandomToken implements RandomToken {

	private Integer howBadIsIt = 10;	
	
	/**
	 *  Default Contructor
	 */
	public BadRandomToken() {
	}


	/**
	 * @param howBadIsIt
	 */
	public BadRandomToken(Integer howBadIsIt) {
		if (howBadIsIt  < 5) {
			this.howBadIsIt = 5;
		} else if (howBadIsIt > 100) {
			this.howBadIsIt = 100;
		} else {
			this.howBadIsIt = howBadIsIt;
		}
	}



	@Override
	public String getNewToken() {
		Random rand = new Random();
		Integer newValue = rand.nextInt(howBadIsIt);
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
