/** ---------------------------------------------------------------------------
 * File:        Cocktail.java
 * Author:      Pekka Mäkinen
 * Description: A class to store cocktail information.
 *              Implements Parcelable for data serialisation.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Provides storage for cocktail data.
 * Implements Parcelable for data serialization.
 */
public class Cocktail implements Parcelable {
	
	/* Compulsory data */
	private String m_Name;
	//private String m_BaseSpirit;
	private ArrayList< String > m_BaseSpirits;
	private String m_Method;
	private ArrayList< Component > m_Components;
	
	/* Optional data */
	private String m_Description;
	private String m_BaseRecipe;
	private ArrayList< String > m_References;
	private ArrayList< String > m_Tags;
	private String m_Image;
	
	/* Constructors */
	/**
	 * Simple constructor. Creates empty array list for the components.
	 */
	public Cocktail(){
		m_Name = null;
		m_Method = null;
		m_Components = new ArrayList< Component >();
		m_BaseSpirits = null;
	}
	
	/**
	 * Constructor. Initializes all the compulsory data with given arguments.
	 * @param name Name of the cocktail
	 * @param base_spirit Base spirit of the cocktail
	 * @param components Components of the cocktail
	 * @param method Instructions for making the cocktail
	 */
	public Cocktail( String name, String base_spirit, 
		ArrayList< Component > components, String method )
	{
		m_Name = name;
		m_Method = method;
		m_Components = new ArrayList< Component >( components );
		m_References = null;
		m_Tags = null;
		m_BaseSpirits = null;
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
	
	/**
	 * Returns cocktail's base recipe.
	 * @return Base recipe of the cocktail.
	 */
	public String getBaseRecipe() { return m_BaseRecipe; }
	
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
	 * Returns a list of cocktail tags.
	 * @return Tags of the cocktail
	 */
	public ArrayList< String > getTags() { return m_Tags; }
	
	/**
	 * Returns a list of cocktail's base spirits.
	 * @return Base spirits of the cocktail.
	 */
	public ArrayList< String > getBaseSpirits() { return m_BaseSpirits; }
	
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
	
	/**
	 * Adds tag into the cocktail's tag list.
	 * @param tag Tag to be added.
	 */
	public void addTag( String tag ) 
	{
		if( m_Tags != null )
		{
			m_Tags.add( tag ); 
		}
		else
		{
			m_Tags = new ArrayList< String >();
			m_Tags.add( tag );
		}
	}
	
	/**
	 * Adds tag into the cocktail's tag list.
	 * @param tag Tag to be added.
	 */
	public void addBaseSpirit( String base_spirit ) 
	{
		if( m_BaseSpirits != null )
		{
			m_BaseSpirits.add( base_spirit ); 
		}
		else
		{
			m_BaseSpirits = new ArrayList< String >();
			m_BaseSpirits.add( base_spirit );
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
    	
    	if( m_BaseSpirits != null )
    	{
    		out.writeInt( m_BaseSpirits.size() );
    		for( int i = 0; i < m_BaseSpirits.size(); i++ )
    		{
    			out.writeString( m_BaseSpirits.get( i ) );
    		}
    	}
    	if( m_References != null )
    	{
        	out.writeInt( m_References.size() );
        	for( int i = 0; i < m_References.size(); i++ )
        	{
        		out.writeString( m_References.get( i ) );
        	}
    	}
    	if( m_Tags != null )
    	{
    		out.writeInt( m_Tags.size() );
    		for( int i = 0; i < m_Tags.size(); i++ )
    		{
    			out.writeString( m_Tags.get( i ) );
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
    	/* Read values in the same order than 
    	 * what was written in writeToParcel.
    	 */
    	
    	m_Name = in.readString();

    	int temp_count = in.readInt();
    	m_Components = new ArrayList< Component >();
    	for( int i = 0; i < temp_count; i++ )
    	{
    		m_Components.add( new Component( ( Component )
    			in.readParcelable( Component.class.getClassLoader() ) ) );
    	}

    	m_Method = in.readString();
    	m_Description = in.readString();
    	m_Image = in.readString();
    	
    	temp_count = in.readInt();
    	for( int i = 0; i < temp_count; i++ )
    	{
    		addBaseSpirit( in.readString() );
    	}
    	
    	temp_count = in.readInt();
    	for( int i = 0; i < temp_count; i++ )
    	{
    		addReference( in.readString() );
    	}
    	
    	temp_count = in.readInt();
    	for( int i = 0; i < temp_count; i++ )
    	{
    		addTag( in.readString() );
    	}
    }
}
