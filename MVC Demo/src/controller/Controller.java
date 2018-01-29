package controller;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;

/**
 * Main class for the controller of this MVC demo.
 */
public class Controller
	implements ChangeListener, ItemListener
{
	private final Model model;
	
	public Controller( final Model model )
	{
		this.model = model;
	}
	
	@Override
	public void stateChanged( ChangeEvent e )
	{
		final JSpinner spinner = ((JSpinner)e.getSource());
		final int sides = ((Number)spinner.getValue()).intValue();
		
		model.setNumberSides( sides );
	}
	
	@Override
	public void itemStateChanged( final ItemEvent e ) {
		String color = (String)e.getItem();
		if( "Black".equalsIgnoreCase( color ) ) {
			model.setColor( Color.BLACK );
		}
		else if( "blue".equalsIgnoreCase( color ) ) {
			model.setColor( Color.BLUE );
		}
		else if( "red".equalsIgnoreCase( color ) ) {
			model.setColor( Color.RED );
		}
		else if( "green".equalsIgnoreCase( color ) ) {
			model.setColor( Color.GREEN );
		}
	}
}