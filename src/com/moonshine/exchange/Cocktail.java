/** ---------------------------------------------------------------------------
 * File:        Cocktail.java
 * Author:      Pekka Mäkinen
 * Version:     1.1
 * Description: A class to store cocktail information.
 *              Implements Parcelable for data serialisation.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Provides storage for cocktail data.
 * Implements Parcelable for data serialization.
 */
public class Cocktail implements Parcelable {
	
	/* Compulsory data */
	private String m_Name;
	private String m_BaseSpirit;
	private String m_Method;
	private ArrayList< Component > m_Components;
	
	/* Optional data */
	private String m_Description;
	private String m_BaseRecipe;
	private ArrayList< String > m_References;
	private String m_Image;
	
	/* Constructors */
	/**
	 * Simple constructor. Creates empty array list for the components.
	 */
	public Cocktail(){
		m_Name = null;
		m_BaseSpirit = null;
		m_Method = null;
		m_Components = new ArrayList< Component >();
	}
	
	/**
	 * Constructor. Initialises all the compulsory data with given arguments.
	 * @param name Name of the cocktail
	 * @param base_spirit Base spirit of the cocktail
	 * @param components Components of the cocktail
	 * @param method Instructions for making the cocktail
	 */
	public Cocktail( String name, String base_spirit, 
		ArrayList< Component > components, String method )
	{
		m_Name = name;
		m_BaseSpirit = base_spirit;
		m_Method = method;
		m_Components = new ArrayList< Component >( components );
		m_References = null;
	}

	/**
	 * toString implementation, returns cocktail name.
	 * @return Name of the cocktail.
	 */
	public String toString() { return m_Name; }
	
	/* Getters */
	/**
	 * Returns cocktail name.
	 * @return Name of the cocktail.
	 */
	public String getName() { return m_Name; }
	
	/**
	 * Returns cocktail components.
	 * @return ArrayList of the components
	 */
	public ArrayList< Component > getComponents() { 
		return new ArrayList< Component >( m_Components ); 
		}
	/**
	 * Returns cocktail instructions (method).
	 * @return Method for the cocktail.
	 */
	public String getMethod() { return m_Method; }
	

//	public String getReferences() 	{ return m_References; }
	
	/**
	 * Returns cocktail's base recipe.
	 * @return Base recipe of the cocktail.
	 */
	public String getBaseRecipe() { return m_BaseRecipe; }
	
	/**
	 * Returns cocktail's base spirit.
	 * @return Base spirit of the cocktail.
	 */
	public String getBaseSpirit() { return m_BaseSpirit; }
	
	/**
	 * Returns cocktail description.
	 * @return Description of the cocktail.
	 */
	public String getDescription() { return m_Description; }
	
	/**
	 * Returns cocktail references.
	 * @return References of the cocktail in array list.
	 */
	public ArrayList< String > getReferences() { return m_References; }
	
	/**
	 * Returns cocktail image's filename.
	 * @return Name of the image.
	 */
	public String getImage() { return m_Image; }
	
	/* Setters */
	
	/**
	 * Sets cocktail name.
	 * @param name Name for the cocktail.
	 */
	public void setName( String name ) 			
		{ m_Name = name; }
	
	/**
	 * Sets cocktail method.
	 * @param method Method for the cocktail.
	 */
	public void setMethod( String method ) 
		{ m_Method = method; }
	
	/**
	 * Sets cocktail base spirit.
	 * @param base_spirit Base spirit for the cocktail.
	 */
	public void setBaseSpirit( String base_spirit ) 
		{ m_BaseSpirit = base_spirit; }
	
	/**
	 * Sets cocktail base recipe.
	 * @param base_recipe Base recipe for the cocktail.
	 */
	public void setBaseRecipe( String base_recipe ) 
		{ m_BaseRecipe = base_recipe; }
	
	/**
	 * Sets cocktail description.
	 * @param description Description for the cocktail.
	 */
	public void setDescription( String description ) 
		{ m_Description = description; }
	
	/**
	 * Sets cocktail image's filename
	 * @param image Filename for the cocktail's image.
	 */
	public void setImage( String image )
		{ m_Image = image; }
	
/*
	public void setReferences( String references ) 
		{ m_References = references; }
*/
	/**
	 * Adds component into the cocktails component list.
	 * @param component Component to be added.
	 */
	public void addComponent( Component component ) 
		{ m_Components.add( component ); }
	
	/**
	 * Clears the cocktails component list.
	 */
	public void clearComponents() { m_Components.clear(); }
	
	/**
	 * Removes a component from the component list by name.
	 * Name search ignores case.
	 * @param component_name NAme of the component to be removed.
	 * @return true if component was found and removed, otherwise false.
	 */
	public boolean removeComponent( String component_name )
	{
		for( int i = 0; i < m_Components.size(); i++ )
		{
			if( m_Components.get( i ).getName().equalsIgnoreCase(
				component_name ) )
			{
				m_Components.remove( i );
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds reference into the cocktail's reference list.
	 * @param reference Reference to be added.
	 */
	public void addReference( String reference ) 
	{
		if( m_References != null )
		{
			m_References.add( reference ); 
		}
		else
		{
			m_References = new ArrayList< String >();
			m_References.add( reference );
		}
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
     * Writes cocktail data into Parcel object.
     * (non-Javadoc)
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    public void writeToParcel( Parcel out, int flags ) 
    {
    	out.writeString( m_Name );
    	out.writeInt( m_Components.size() );
    	for( int i = 0; i < m_Components.size(); i++ )
    	{
    		out.writeParcelable( m_Components.get( i ), flags );
    	}

    	out.writeString( m_Method );
    	out.writeString( m_Description );
    	out.writeString( m_Image );
    	if( m_References != null )
    	{
        	out.writeInt( m_References.size() );
        	for( int i = 0; i < m_References.size(); i++ )
        	{
        		out.writeString( m_References.get( i ) );
        	}
    	}
    }

    /*
     * Creator for the Parcelable
     */
    public static final Parcelable.Creator< Cocktail > CREATOR = 
    	new Parcelable.Creator<Cocktail>() 
    	{
	        public Cocktail createFromParcel( Parcel in ) {
	            return new Cocktail( in );
	        }
	
	        public Cocktail[] newArray( int size ) {
	            return new Cocktail[ size ];
	        }
    	};

    /**
    * Constructor from Parcel object.
    * @param in Parcel object containing cocktail data.
    */
    private Cocktail( Parcel in )
    {
    	m_Name = in.readString();

    	int component_count = in.readInt();
    	m_Components = new ArrayList< Component >();
    	for( int i = 0; i < component_count; i++ )
    	{
    		m_Components.add( new Component( ( Component )
    			in.readParcelable( Component.class.getClassLoader() ) ) );
    	}

    	m_Method = in.readString();
    	m_Description = in.readString();
    	m_Image = in.readString();
    	
    	int reference_count = in.readInt();
    	for( int i = 0; i < reference_count; i++ )
    	{
    		addReference( in.readString() );
    	}
    }
}
