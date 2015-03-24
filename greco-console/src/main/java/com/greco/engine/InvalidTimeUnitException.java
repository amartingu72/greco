package com.greco.engine;

import com.greco.utils.Warnings;

public class InvalidTimeUnitException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTimeUnitException(int timeUnit) {
		super(timeUnit + " " +Warnings.getString("InvalidTimeUnitException"));
	}
}
