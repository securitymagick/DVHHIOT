package com.securitymagick.domain;

import java.text.DateFormat;
import java.util.Date;

public class NumericDate {
	private Long value;
    private static final long CONVERSION = 1000L;

    public NumericDate(Long value)
    {
        this.value = value;
    }

    public static NumericDate now()
    {
        return fromMilliseconds(System.currentTimeMillis());
    }

    public static NumericDate fromSeconds(Long secondsFromEpoch)
    {
        return new NumericDate(secondsFromEpoch);
    }

    public static NumericDate fromMilliseconds(Long millisecondsFromEpoch)
    {
        return fromSeconds(millisecondsFromEpoch / CONVERSION);
    }

    public void addSeconds(long seconds)
    {
        value += seconds;
    }

    /**
     * Returns a numeric value representing the number of seconds from
     * 1970-01-01T0:0:0Z UTC until the given UTC date/time
     * @return value
     */
    public Long getValue()
    {
        return value;
    }
    
    public void setValue(Long value)
    {
        this.value = value; 
    }

    public long getValueInMillis()
    {
        return getValue() * CONVERSION;  
    }

    public boolean isBefore(NumericDate when)
    {
        return value < when.getValue();
    }

    public boolean isOnOrAfter(NumericDate when)
    {
        return !isBefore(when);
    }

    public boolean isAfter(NumericDate when)
    {
        return value > when.getValue();
    }

    

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NumericDate)) {
			return false;
		}
		NumericDate other = (NumericDate) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value.toString();
	}	

}
