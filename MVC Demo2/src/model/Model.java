package model;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.Collection;

/**
 * Main class for the model of this MVC demo.
 */
public class Model {
	private int numberOfSides = 7;
	private File[] imageFiles;
	private Image[] imagesCache;

	private Color color = Color.BLACK;
	
	private Observer observer;
	
	public void setObserver( final Observer observer )
	{
		this.observer = observer;
	}
	
	public int getNumberSides()
	{
		return numberOfSides;
	}
	
	public void setNumberSides( final int sides )
	{
		numberOfSides = sides;
		//notify listeners
		if( observer != null ) {
			observer.sidesChanged();
		}
	}
	
	public void setImageFiles( final Collection<File> imageFiles)
	{
		this.imageFiles = imageFiles.toArray( new File[ imageFiles.size() ] );
		imagesCache = new Image[ imageFiles.size() ];

		
		if( observer != null ) {
			observer.pathChanged();
		}
	}
	
	public String getPath()
	{
		return filePath;
	}
	
	public void setColor( final Color color )
	{
		this.color = color;
		if( observer != null ) {
			observer.sidesChanged();
		}
	}
	
	public Color getColor()
	{
		return color;
	}
}