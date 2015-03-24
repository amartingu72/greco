package com.greco.engine;

import com.greco.utils.Warnings;

public class SoMuchTimeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SoMuchTimeException() {
		super(Warnings.getString("SoMuchTimeException"));
	}
}
