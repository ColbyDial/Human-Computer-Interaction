package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import controller.Controller;
import model.Model;
import model.Observer;

/**
 * Main class for the View portion of the MVC demo.
 */
public class Window
	extends JFrame
	implements Observer
{
	public Window( final Model model, final Controller controller )
	{
		super( "MVC Demo" );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		setSize( 400, 400 );
		
		setLayout( new BorderLayout() );
		
		final JPanel panel = new JPanel();
		panel.setLayout( new FlowLayout() );
		
		final JLabel spinLabel = new JLabel( "Number of Sides:" );
		panel.add( spinLabel );
		final JSpinner spinner = new JSpinner( new SpinnerNumberModel( 7, 3, 15, 1 ) );
		spinner.addChangeListener( controller );
		panel.add(spinner);
		
		final JLabel colorLabel = new JLabel( "Color:" );
		panel.add( colorLabel );
		final JComboBox<String> color = new JComboBox<String>(
				new String[] { "Black", "Blue", "Red", "Green" } );
		color.addItemListener( controller );
		panel.add( color );
		
		add( panel, BorderLayout.NORTH );
		add( new PolygonViewer( model ), BorderLayout.CENTER );
		
		//register View as the observer of the Model
		model.setObserver( this );
		
		setVisible( true );
	}
	
	@Override
	public void sidesChanged()
	{
		repaint();
	}
}