package com.greco.engine;

import com.greco.utils.Warnings;

public class DailyScheduleException extends Exception {

	private static final long serialVersionUID = 1L;

	public DailyScheduleException() {
		super(Warnings.getString("DailyScheduleException"));
	}
}
