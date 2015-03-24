package com.greco.engine;

import com.greco.utils.Warnings;

public class TimeUnitUnavailableException extends Exception {

	private static final long serialVersionUID = 1L;

	public TimeUnitUnavailableException() {
		super(Warnings.getString("TimeUnitUnavailableException"));
	}
}
