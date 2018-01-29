package model;

import java.awt.Color;

/**
 * Main class for the model of this MVC demo.
 */
public class Model {
	private int numberOfSides = 7;
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