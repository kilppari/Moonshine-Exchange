/** ---------------------------------------------------------------------------
 * File:        Component.java
 * Author:      Pekka Mäkinen
 * Description: A class to store cocktail component information.
 *              Implements Parcelable for data serialization.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Provides storage for component data.
 * Implements Parcelable for data serialization.
 */
public class Component implements Parcelable{

	private String m_Name;
	private String m_Unit;
	private String m_Amount;
	
	private int m_UnitIdx; /* Spinner's selected item */
	
	public final static List< String > COMPONENT_AMOUNTS = setComponentAmounts();

	/**
	 * Constructor.
	 * @param name Name for the component
	 * @param amount Amount of the component
	 * @param unit Unit of the component
	 */
	public Component( String name, String amount, String unit )
	{
		m_Name = name;
		m_Amount = amount;
		m_Unit = unit;
	}
	
	/**
	 * Copy constructor.
	 * @param component Component to be copied.
	 */
	public Component( Component component )
	{
		m_Name = component.getName();
		m_Amount = component.getAmount();
		m_Unit = component.getUnit();
		m_UnitIdx = component.getUnitIdx();
	}

	/**
	 * Returns component name.
	 * @return Name of the component.
	 */
	public String getName() { return m_Name; }
	
	/**
	 * Returns component unit.
	 * @return Unit of the component
	 */
	public String getUnit() { return m_Unit; }
	
	/**
	 * Returns component amount.
	 * @return Amount of the component.
	 */
	public String getAmount() { return m_Amount; }
	
	/**
	 * Returns component's index for <code>COMPONENT_AMOUNTS</code> array.
	 * If no match is found, returns 0
	 * @return Index for <code>COMPONENT_AMOUNTS</code>
	 */
	public int getAmountIdx() 
	{ 
		for( int i = 0; i < COMPONENT_AMOUNTS.size(); i++ )
		{
			if( COMPONENT_AMOUNTS.get( i ).equalsIgnoreCase( m_Amount ) )
				return i;
		}
		// ID not found, default to 0
		return 0;
	}
	
	/**
	 * Returns component's index for <code>component_unit</code>
	 * array in stings.xml
	 * @return Index for <code>component_unit</code>
	 */
	public int getUnitIdx() { return m_UnitIdx; }

	
	/* Setters */

	/**
	 * Sets component name.
	 * @param name Name for the component.
	 */
	public void setName( String name ) { m_Name = name; }
	
	/**
	 * Sets component amount.
	 * @param amount Amount for the component.
	 */
	public void setAmount( String amount ) { m_Amount = amount; }
	
	/**
	 * Sets component unit.
	 * @param unit Unit for the component.
	 */
	public void setUnit( String unit ) { m_Unit = unit; }
	
	/**
	 * Sets component's index for <code>component_unit</code>
	 * array in strings.xml
	 * @param idx Index for <code>component_unit</code>
	 */
	public void setUnitIdx( int idx ) { m_UnitIdx = idx; }
	
	/**
	 * Initializes the list m_ComponentAmounts.
	 */
	private static List< String > setComponentAmounts()
	{
		List< String > amounts = new ArrayList< String >();

		// First option shall be empty
		amounts.add("");
		
		// Special sets for fractions.
		amounts.add("\u00BC");  // 1/4
		amounts.add("\u2153");  // 1/3
		amounts.add("\u00BD");  // 1/2
		amounts.add("\u2154");  // 2/3
		amounts.add("\u00BE");  // 3/4
		
		// Numbers from 1 to 100.
		for( int i = 1; i < 101; i++ )
		{
			amounts.add( Integer.toString( i ) );
		}
		
		return  amounts;
	}
	
	/* -- Implement Parcelable below --------------------------------------- */
	
	/*
	 * (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
    public int describeContents() 
    {
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    public void writeToParcel( Parcel out, int flags ) 
    {
    	out.writeString( m_Name );
    	out.writeString( m_Amount );
    	out.writeString( m_Unit );
    }

    // Define Creator for the Parcelable
    public static final Parcelable.Creator< Component > CREATOR = 
    	new Parcelable.Creator< Component >() 
    	{
	        public Component createFromParcel( Parcel in ) {
	            return new Component( in );
	        }
	
	        public Component[] newArray( int size ) {
	            return new Component[ size ];
	        }
    	};

    /**
     * Constructor from Parcel object.
     * @param in Parcel object containing component data.
     */
    private Component( Parcel in )
    {
    	m_Name = in.readString();
    	m_Amount = in.readString();
    	m_Unit = in.readString();
    }
	
}
