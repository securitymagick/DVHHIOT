/**
 * 
 */
package com.securitymagick.domain;

import java.util.Random;
/**
 * @author leggosgirl
 *
 */
public class AuthToken {
	private String token=null;
	private Integer x =null;
	private Integer y = null;
	private Integer z = null;
	private Integer uid = null;
	private String helper= null;
	
	private Integer base = 999999;
	private String[] strStarter = {"Pp", "sK", "bY", "Lm", "Cg", "nA"};
	private String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMOPQRSTUVWXYZ";
	private Integer[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};

	/**
	 * @param token
	 */
	public AuthToken(String token) {
		this.token = token;
	}
	
	public AuthToken(Integer uid) {
		this.uid = uid;
		Random rand = new Random();

		this.x = rand.nextInt(6);
		this.z = rand.nextInt(10);
		this.y = rand.nextInt(9)+1;
		
		Integer alphaPosition = rand.nextInt(alpha.length());
		Integer baseToken = base - (uid * primes[z] + y);
		String extra = "";
		if (baseToken.toString().length() < 6) {
			Integer pos;
			for (int i=baseToken.toString().length(); i<6; i++) {
				pos = rand.nextInt(alpha.length());
				extra += alpha.substring(pos, pos+1);
			}
		}
		this.token = strStarter[x] + z.toString() + y.toString() + x.toString() + alpha.substring(alphaPosition, alphaPosition+1) + extra + baseToken.toString();
	}

	public boolean parseToken() {
		if (token == null) {			
			helper = "Token = null";
			return false;
		}
		if (token.length() != 12) {			
			helper = "Token length wrong: " + token.length();
			return false;
		}
		
		String start = token.substring(0, 2);
		try {
			this.z = Integer.parseInt(token.substring(2, 3));
			this.y = Integer.parseInt(token.substring(3, 4));
			this.x = Integer.parseInt(token.substring(4, 5));
		} catch (NumberFormatException e) {
			helper = "Error parsing x,y,z" + e.getMessage();
			return false;
		}
		if (!strStarter[x].equals(start)) {
			helper = "strStarter check: " + start + ":" + strStarter[x];
			return false;
		}
		
		String next = token.substring(5, 6);
		if (!alpha.contains(next)) {
			helper = "randome alpha issue: " + next;
			return false;
		}
		
		String strBaseToken = token.substring(6);
		String first = strBaseToken.substring(0, 1);
		while (alpha.contains(first) && strBaseToken.length() > 1) {
			strBaseToken = strBaseToken.substring(1, strBaseToken.length());
			first = strBaseToken.substring(0, 1);
		}
		try {
			Integer baseToken = Integer.parseInt(strBaseToken);		
			helper = "Got " + baseToken.toString() + "," + y.toString() + "," + primes[z].toString();
			this.uid = (base - baseToken - y) / primes[z];
		} catch (NumberFormatException e) {
			helper = "Error parsing basetoken: " + e.getMessage();
			return false;
		}
		return true;
		
	}

	/**
	 * @return the token
	 */
	public final String getToken() {
		return token;
	}

	/**
	 * @return the uid
	 */
	public final Integer getUid() {
		return uid;
	}

	/**
	 * @return the helper
	 */
	public String getHelper() {
		return helper;
	}

	/**
	 * @param helper the helper to set
	 */
	public void setHelper(String helper) {
		this.helper = helper;
	}

	
}
