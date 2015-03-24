package com.greco.engine;

import com.greco.utils.Warnings;

public class TooEarlyException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * Recurso no diponible tan pronto
	 * @param msg HH:MM. Indica la hora a partir de la que está disponible,.
	 */
	public TooEarlyException(String msg) {
		super(Warnings.getString("TooLateException") + msg);
	}
}
