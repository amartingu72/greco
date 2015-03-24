package com.greco.engine;

import com.greco.utils.Warnings;

public class BeforeHandExceededException extends Exception {

	private static final long serialVersionUID = 1L;

	public BeforeHandExceededException() {
		super(Warnings.getString("BeforeHandExceededException"));
	}
}
