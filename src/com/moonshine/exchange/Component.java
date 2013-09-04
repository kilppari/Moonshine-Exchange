/** ---------------------------------------------------------------------------
 * File:        Component.java
 * Author:      Pekka Mäkinen
 * Version:     1.1
 * Description: A class to store cocktail component information.
 *              Implements Parcelable for data serialization.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

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
	
	private int m_AmountIdx; /* Spinner's selected item */
	private int m_UnitIdx; /* Spinner's selected item */
	
	/**
	 * Constructor.
	 * @param name Name for the component
	 * @param amount_idx Index for the <code>component_amount</code> 
	 *        array in strings.xml
	 * @param unit_idx Index for the <code>component_unit</code>
	 *        array in strings.xml
	 */
	public Component( String name, int amount_idx, int unit_idx )
	{
		m_Name = name;
		m_AmountIdx = amount_idx;
		m_UnitIdx = unit_idx;
	}
	
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
		m_AmountIdx = component.getAmountIdx();
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
	 * Returns component's index for <code>component_amount</code>
	 * array in stings.xml
	 * @return Index for <code>component_amount</code>
	 */
	public int getAmountIdx() { return m_AmountIdx; }
	
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
	 * Sets component's index for <code>component_amount</code>
	 * array in strings.xml
	 * @param idx Index for <code>component_array</code>
	 */
	public void setAmountIdx( int idx ) { m_AmountIdx = idx; }
	
	/**
	 * Sets component's index for <code>component_unit</code>
	 * array in strings.xml
	 * @param idx Index for <code>component_unit</code>
	 */
	public void setUnitIdx( int idx ) { m_UnitIdx = idx; }
	
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
