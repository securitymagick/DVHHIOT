package com.securitymagick.domain;

/**
 * @author leggosgirl
 *
 */
public class UpdateHabitForm {

	private String habit;
	
	public UpdateHabitForm() {
		super();
	}
	
	
	public UpdateHabitForm(String habit) {
		super();
		this.habit = habit;
	}
	
	public String getHabit() {
		return habit;
	}
	public void setHabit(String habit) {
		this.habit = habit;
	}


	@Override
	public String toString() {
		return "UpdateHabitForm [" + "habit=" + habit + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((habit == null) ? 0 : habit.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateHabitForm other = (UpdateHabitForm) obj;
		if (habit == null) {
			if (other.habit != null)
				return false;
		} else if (!habit.equals(other.habit))
			return false;
		return true;
	}
	
	
	
}
