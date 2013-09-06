/** ---------------------------------------------------------------------------
 * File:        ContentParser.java
 * Author:      Pekka Mäkinen
 * Description: This class implements access to the JSON content file for 
 *              reading and writing cocktail data.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.util.JsonReader;
import android.util.JsonWriter;

/**
 * Provides access to a JSON data file for
 * reading and writing cocktail data.
 */
public class ContentParser {
	
	private static ContentParser m_Instance;
	private ArrayList< Cocktail > m_RecipeList;
	private String[] m_Amounts;
	private String[] m_Units;

	private ContentParser()
	{
		m_RecipeList = new ArrayList< Cocktail >();
	}
	
	public static ContentParser getInstance()
	{
		if( m_Instance == null )
		{
			m_Instance = new ContentParser();
		}
		return m_Instance;
	}
	
	public void setAmounts( String[] amounts ) { m_Amounts = amounts; }
	public void setUnits( String[] units ) { m_Units = units; }
	/**
	 * Constructor.
	 * @param amount_strings Array of amount strings, e.g., 1/2, 1, part...
	 * @param unit_strings Array of unit strings, e.g., ml, cl, teaspoon...
	 */
	/*
	public ContentParser( String[] amount_strings, String[] unit_strings ) {
		m_RecipeList = new ArrayList< Cocktail >();
		m_Amounts = amount_strings;
		m_Units = unit_strings;
	}*/
	
	/**
	 * Returns list of the cocktail recipes.
	 * @return ArrayList of the cocktail recipes.
	 */
	public ArrayList< Cocktail >getRecipeList() { return m_RecipeList; }
	
	/**
	 * Loads the JSON stream and parses the contents into Cocktail objects
	 * and adds the objects into the recipe list.
	 * @param is Handle to the InputStream containing the JSON data
	 * @throws IOException
	 */
	public void loadJsonData( InputStream is ) throws SimpleException
	{
		try
		{
			m_RecipeList.clear();
			JsonReader reader = new JsonReader( 
				new InputStreamReader( is, "UTF-8" ) );
	
			reader.beginArray();
			while ( reader.hasNext() ) {
				m_RecipeList.add( readJsonCocktail( reader ) );
			}
			reader.endArray();
			reader.close();
		}
		catch( SimpleException se )
		{
			throw se;
		}
		catch( IOException e )
		{
			SimpleException se = new SimpleException( e,
				"Parse error while parsing JSON file", 
				null );
			throw se;
		}
	}
	/**
	 * Reads JSON cocktail data and parses it into Cocktail object.
	 * @param reader Handle to the JSON stream reader
	 * @return Cocktail object
	 * @throws IOException
	 */
	public Cocktail readJsonCocktail( JsonReader reader ) 
		throws IOException 
	{
		Cocktail cocktail = new Cocktail();
		try
		{
			reader.beginObject();
			while ( reader.hasNext() ) {
				String token = reader.nextName();
				if( token.equals( "Name" ) )
					{
					cocktail.setName( reader.nextString() );
					}
				else if( token.equals( "BaseSpirit" ) )
				{
					reader.beginArray();
					while( reader.hasNext() )
					{
						cocktail.addBaseSpirit( reader.nextString() );
					}
					//cocktail.setBaseSpirit( reader.nextString() );
					reader.endArray();
				}
				else if( token.equals( "Description" ) )
				{
					cocktail.setDescription( reader.nextString() );
				}
				else if( token.equals( "Method" ) )
				{
					cocktail.setMethod( reader.nextString() );
				}
				else if( token.equals( "Components" ) ) {
					reader.beginArray();
					while ( reader.hasNext() )
					{
						Component component = readJsonComponent( reader );
						if( component != null ) {
							cocktail.addComponent( component );
						}
					}
					reader.endArray();
				}
				else if( token.equals( "References" ) )
				{
					reader.beginArray();
					while( reader.hasNext() )
					{
						cocktail.addReference( reader.nextString() );
					}
					reader.endArray();
				}
				else if( token.equals( "Tags" ) )
				{
					reader.beginArray();
					while( reader.hasNext() )
					{
						cocktail.addTag( reader.nextString() );
					}
					reader.endArray();
				}
				else if( token.equals( "Image" ) )
				{
					cocktail.setImage( reader.nextString() );
				}
				else { 
					reader.skipValue();
			    }
			}
			reader.endObject();
		}
		catch( SimpleException se )
		{
			throw se;
		}
		catch( IOException e )
		{
			SimpleException se = new SimpleException( e,
				"Parse error while parsing JSON cocktail object", 
				cocktail.getName() );
			throw se;
		}
		
		return cocktail;
	}
	
	/**
	 * Writes a new cocktail recipe into the JSON file as a new JSON object.
	 * @param json_file The JSON input file to be updated
	 * @param cocktail The input cocktail
	 * @throws IOException
	 */
	public void writeJsonCocktail( File json_file, Cocktail cocktail ) 
		throws IOException
	{
		// Create new output stream for the input cocktail's JSON data.
		ByteArrayOutputStream ostream_new_cocktail = new ByteArrayOutputStream();
		JsonWriter writer = new JsonWriter( 
			new OutputStreamWriter( ostream_new_cocktail, "UTF-8" ) );

	    writer.setIndent("    "); // 4 spaces
	    
		// Write the cocktail recipe into JSON object
	    writer.beginObject();
	    writer.name( "Name" ).value( cocktail.getName() );
	    writer.name( "BaseSpirit" ).value( cocktail.getBaseSpirits().get( 0 ) );
	    writer.name( "Components" );
	    writer.beginArray();
	    ArrayList< Component > components = cocktail.getComponents();
	    
	    for( int i = 0; i < components.size(); i++ )
	    {
		    writer.beginObject();
		    writer.name( "Name" ).value( components.get( i ).getName() );
		    writer.name( "Amount" ).value( m_Amounts[ components.get( i ).getAmountIdx() ] );
		    writer.name( "Unit" ).value( m_Units[ components.get( i ).getUnitIdx() ] );
		    writer.endObject();
	    }
	    writer.endArray();
	    
	    writer.name( "Method" ).value( cocktail.getMethod() );
	    writer.name( "Description" ).value( cocktail.getDescription() );
	    writer.endObject();
	    writer.close();
	    
		/* Read current contents of the JSON file and allocate buffer of 
	     * appropriate size, and the fill it with the JSON data 
	     */
		InputStream stream = new FileInputStream( json_file );
	    byte[] buffer = new byte[ stream.available() ];
	    while ( stream.read( buffer ) != -1 );
		stream.close();
		
		/* Create file output stream for the new content which is combination 
		 * of the old JSON and the new JSON data.
		 *   
		 * (I.e., The new cocktail recipe is added into the recipe list)
		 * The recipe needs to be added before the JSON file ending delimiter,
		 *  the ']}'-pair.
		 */
		FileOutputStream ostream = new FileOutputStream( json_file );
		
	    int len = buffer.length;
	    for( int i = 1; i < len; i++ )
	    {
	    	if( buffer[ len - ( i + 1 ) ] == ']' && buffer[ len - i ] == '}' )
	    	{
	    		// Ending delimiter found, write the file.
	    	    ostream.write( buffer, 0, len - ( i + 1 ) );
	    	    ostream.write( ",\n".getBytes() );
	    	    ostream.write( ostream_new_cocktail.toByteArray() );
	    	    ostream.write( "\n]}".getBytes() );
	    		break;
		    }
	    }
	    ostream.close();
	}
	
	/**
	 * Reads component data from a JSON component object.
	 * @param reader Handle to JsonReader
	 * @return Component object
	 * @throws IOException
	 */
	public Component readJsonComponent( JsonReader reader ) throws IOException {
		// Component data strings
		String comp_name = null;
		String comp_amount = null;
		String comp_unit = null;

		// Read component data from JSON handle into data strings
		try 
		{
			reader.beginObject();
			while( reader.hasNext() ) {
				String token = reader.nextName();
				if( token.equals( "Name" ) ) { comp_name = reader.nextString(); }
				else if( token.equals( "Amount" ) ) { comp_amount = reader.nextString(); }
				else if( token.equals( "Unit" ) ) { comp_unit = reader.nextString(); }
				else { 
					reader.skipValue(); 
				}
			}
			reader.endObject();
		}
		catch( IOException e )
		{
			SimpleException se = new SimpleException( e,
				"Parse error while parsing JSON cocktail component object", 
				comp_name + " " + comp_amount + " " + comp_unit );
			throw se;
		}
		
		// Create new component object if name was found, otherwise return null.
		if( comp_name != null  )
		{
			Component component = new Component( 
				comp_name, 
				comp_amount,
				comp_unit );
			/*
				compAmountStrToIdx( comp_amount ), 
				compUnitStrToIdx( comp_unit ) );
				*/
			return component;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns array index of <code>component_amount</code> corresponding 
	 * to the given amount string.
	 * @param amount_str Amount string of which index is to be returned.
	 * @return Index to array <code>component_amount</code>
	 */
	int compAmountStrToIdx( String amount_str )
	{
		if( amount_str != null )
			{
			for( int i = 0; i < 24; i++ ) {
				if( m_Amounts[ i ].equals( amount_str ) ) { return i; }
			}
		}
		return 0;
	}

	/**
	 * Returns array index of <code>component_unit</code> corresponding 
	 * to the given amount string.
	 * @param unit_str Amount string of which index is to be returned.
	 * @return Index to array <code>component_unit</code>
	 */
	int compUnitStrToIdx( String unit_str )
	{
		if( unit_str != null )
		{
			for( int i = 0; i < 9; i++ ) {
				if( m_Units[ i ].equals( unit_str ) ) { return i; }
			}
		}
		return 0;
	}
	
	/**
	 * Sorts m_Recipe list by cocktail names from a to z.
	 */
	public void sortListByName()
	{
		Collections.sort( m_RecipeList, new Comparator< Cocktail >() {
			@Override
			public int compare( Cocktail c1, Cocktail c2 ) {
				return c1.getName().compareToIgnoreCase( c2.getName() );
			}
		});
	}
	
	/**
	 * Searches cocktail list for a cocktail by name and returns its index.
	 * Returns -1 if cocktail was not found. 
	 * @param name Cocktail to be searched for
	 */
	public int getCocktailIndexByName( String name )
	{
		for( int i = 0; i < m_RecipeList.size(); i++ )
		{
			if( m_RecipeList.get( i ).getName().equalsIgnoreCase( name ) )
			{
				return i;
			}
		}
		return -1;
	}
}

