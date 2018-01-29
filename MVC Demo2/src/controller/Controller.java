package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;

/**
 * Main class for the controller of this MVC demo.
 */
public class Controller
	implements ChangeListener, ItemListener, ActionListener
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

	@Override
	public void actionPerformed(ActionEvent arg0) {

		//JFileChooser for user to select directory
		JFileChooser c = new JFileChooser();
		//only directories
		c.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		//if they don't cancel (i.e. say "Ok")...
		if( c.showOpenDialog( MainFrame.this ) == JFileChooser.APPROVE_OPTION ) {
			final File dir = c.getSelectedFile();
			final List<File> imageFiles = new ArrayList<File>();
			//take array of known image file suffixes, convert to a List,
			//then to a HashSet for fast O(1) lookup
			final Set<String> types = new HashSet<String>(
					Arrays.asList( new String[] {
							"png", "jpg", "jpeg", "bmp", "gif"
					} ) );
			//make a List of all child files of the directory which are one
			//of the expected/known image types above
			for( final File child : dir.listFiles() ) {
				//test the file's extension (in lowercase)
				final String ext = child.getName().substring(
						child.getName().lastIndexOf( '.' ) + 1 ).toLowerCase();
				if( types.contains( ext ) ) {
					imageFiles.add( child );
				}
			}
			
			model.setImageFiles( imageFiles );
				
		
	}
}