/** ---------------------------------------------------------------------------
 * File:        DisplayRecipeActivity.java
 * Author:      Pekka M�kinen
 * Version:     1.2
 * Description: Activity for displaying cocktail recipe.
 * ----------------------------------------------------------------------------
 */
package com.moonshine.exchange;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity for displaying a cocktail recipe. <p>
 * Gets recipe data through a Parcelable object when the intent for this 
 * activity is triggered. Layout is defined in <code>recipe_view.xml</code> 
 */
public class DisplayRecipeActivity extends Activity {
	
	Cocktail m_Recipe;
	TextView m_NameView;
	LinearLayout m_IngredientView;
	TextView m_InstructionsView;
	TextView m_DescriptionView;
	ImageView m_ImageView;

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) 
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.recipe_view );

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled( true );
		}

		Intent intent = getIntent();

		// Get the Parcelable cocktail object
		m_Recipe = intent.getParcelableExtra( MainActivity.EXTRA_RECIPE );

		// Get references to UI-elements.
		m_IngredientView = ( LinearLayout )findViewById( R.id.recipe_components );
		m_InstructionsView = (TextView)findViewById( R.id.recipe_instructions );
		m_DescriptionView = ( TextView )findViewById( R.id.recipe_description );
		m_ImageView = ( ImageView )findViewById( R.id.recipe_image );

		setContents();
	}

	/**
	 * Fills this activity's layout with contents.
	 */
	private void setContents()
	{
		this.setTitle( m_Recipe.getName() );
/*
		Resources res = getResources();
		// Get values of possible amounts and units.
		String[] amount_strings = res.getStringArray( 
			R.array.component_amount );
		String[] unit_strings = res.getStringArray( 
			R.array.component_units );
*/
		//String paragraph = new String("");

		// Get layout inflater for making component view
		LayoutInflater inflater = ( LayoutInflater )
			getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		
		// Margin parameters between one component's data values.
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT );
		llp.setMargins( 0, 0, 8, 0 );
		
		/* Get component data and make a new row into the component 
		 * layout out of each component.
		 */
		ArrayList< Component > components = m_Recipe.getComponents();
		Iterator< Component > it = components.iterator();
		while( it.hasNext() )
		{
			Component comp = it.next();
			
			LinearLayout comp_view = ( LinearLayout )inflater.inflate(
				R.layout.recipe_component_view, null );
			
			TextView amount_view = ( TextView )
				comp_view.findViewById( R.id.amount_view );
			amount_view.setText( comp.getAmount() );
			if( comp.getAmount() != null )
			{
				amount_view.setLayoutParams( llp );
			}
			
			TextView unit_view = ( TextView )
				comp_view.findViewById( R.id.unit_view );
			unit_view.setText( comp.getUnit() );
			if( comp.getUnit() != null )
			{
				unit_view.setLayoutParams( llp );
			}
			
			TextView name_view = ( TextView )
				comp_view.findViewById( R.id.name_view );
			name_view.setText( comp.getName() );

			m_IngredientView.addView( comp_view );
		}

		m_InstructionsView.setText( m_Recipe.getMethod() );
		m_DescriptionView.setText( m_Recipe.getDescription() );
		
		String image = m_Recipe.getImage();
		
		/* If recipe has an image associated to it, load it using BitmapFactory
		 * and also rotate the image in case it is not in up right position.
		 */
		if( !image.isEmpty() )
		{
			File dir = Environment.getExternalStorageDirectory();
			File img_file = new File( dir, "DCIM/Camera/" + image );
			Bitmap bm = BitmapFactory.decodeFile( img_file.getAbsolutePath() );
			
	        try {
	            ExifInterface exif = new ExifInterface( 
	            	img_file.getAbsolutePath() );
	            int orientation = exif.getAttributeInt( 
	            	ExifInterface.TAG_ORIENTATION, 1 );
	            Log.d( "EXIF", "Exif: " + orientation );
	            Matrix matrix = new Matrix();
	            if( orientation == 6 ) {
	                matrix.postRotate( 90 );
	            }
	            else if( orientation == 3 ) {
	                matrix.postRotate( 180 );
	            }
	            else if( orientation == 8 ) {
	                matrix.postRotate( 270 );
	            }
	            // rotating bitmap
	            bm = Bitmap.createBitmap( 
	            	bm, 0, 0, bm.getWidth(), 
	            	bm.getHeight(), matrix, true );
	        }
	        catch ( Exception e ) {
    	    	Toast.makeText( getApplicationContext(),
        	    	"Problem reading the EXIF data of the image: " +
        	    	"Orientation could not be determined.", 
        	    	Toast.LENGTH_SHORT ).show();
	        }
			m_ImageView.setImageBitmap( bm );
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch ( item.getItemId() ) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			//NavUtils.navigateUpFromSameTask( this );
            Intent intent = new Intent( this, MainActivity.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
			return true;
		}
		return super.onOptionsItemSelected( item );
	}

}
