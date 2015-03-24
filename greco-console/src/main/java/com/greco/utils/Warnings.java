package com.greco.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Warnings {
	private static final String BUNDLE_NAME = "com.greco.warnings"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Warnings() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
