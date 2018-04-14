/**
 * 
 */
package com.securitymagick.domain;

/**
 * @author NTISNS01
 *
 */
public class AdminDBItem {
	private Integer id = null;
	private String settingName = null;
	private String settingValue= null;
	
	
	
	/**
	 *  Default Constructor
	 */
	public AdminDBItem() {
	}



	/**
	 * Constructor
	 * 
	 * @param id
	 * @param settingName
	 * @param settingValue
	 */
	public AdminDBItem(Integer id, String settingName, String settingValue) {
		this.id = id;
		this.settingName = settingName;
		this.settingValue = settingValue;
	}


	/**
	 * @return the id
	 */
	public final Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public final void setId(Integer id) {
		this.id = id;
	}



	/**
	 * @return the settingName
	 */
	public final String getSettingName() {
		return settingName;
	}


	/**
	 * @param settingName the settingName to set
	 */
	public final void setSettingName(String settingName) {
		this.settingName = settingName;
	}


	/**
	 * @return the settingValue
	 */
	public final String getSettingValue() {
		return settingValue;
	}


	/**
	 * @param settingValue the settingValue to set
	 */
	public final void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}



	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdminDBItem [id=" + id + ", settingName=" + settingName + ", settingValue=" + settingValue + "]";
	}
	
	
	

}
