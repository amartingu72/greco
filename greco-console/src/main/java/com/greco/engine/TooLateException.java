package com.greco.engine;

import com.greco.utils.Warnings;

public class TooLateException extends Exception {

	
	private static final long serialVersionUID = 1L;
	/**
	 * Recurso no diponible a esa hora.
	 * @param msg HH:MM - Hora hasta la que está disponible.
	 */
	public TooLateException(String msg) {
		super(Warnings.getString("TooLateException" + msg));
	}
}
