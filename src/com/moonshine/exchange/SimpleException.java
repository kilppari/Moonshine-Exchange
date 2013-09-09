/** ---------------------------------------------------------------------------
 * File:        SimpleException.java
 * Author:      Pekka Mäkinen
 * Description: Simple class extending IOException to provide
 *              some additional data and logging.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.io.IOException;

import android.util.Log;

/**
 * Simple extension to IOException for providing some additional data and 
 * logging.
 */
public class SimpleException extends IOException {

	// Custom data objects
	private String m_Type;
	private String m_Value;
	
	// Compulsory ID
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param type Custom type for the exception.
	 * @param value Custom value for the exception.
	 */
	SimpleException( IOException e, String type, String value )
	{
		super( e );
		m_Type = type;
		m_Value = value;
		Log.v( "SimpleException", "Type: " + type + "\nValue: " + value );
	}

	/**
	 * Returns the custom type of this exception.
	 * @return Custom type of the exception.
	 */
	public String getType() { return m_Type; }
	
	/**
	 * Returns the custom value of this exception.
	 * @return Custom value of the exception.
	 */
	public String getValue() { return m_Value; }

}
