/**
 * Driver class with main method for executing the Swing-based Image Viewer
 * application.
 * 
 * Just run this Driver class with no arguments.
 * 
 * 9 ways of advancing the currently-viewed photo:
 * 1. Menu items in the Navigation menu
 * 2. Keyboard shortcuts (on the menu items)
 * 3. Buttons (lower part of the window)
 * 4. Combo box to select image filename (upper part of the window)
 * 5. Spinner to select image by number (lower part of the window)
 * 6. Slideshow mode where image advances every 1.5 seconds
 * 7. Context menu (right-click on image) to select image by file name
 * 8. Mouse click (left-click on image to advance)
 * 9. Mouse scrollwheel (hover on image and scroll mouse wheel)
 * 
 * @author nic
 */
public class Driver
{
	public static void main( final String[] args )
	{
		MainFrame frame = new MainFrame();
		
		frame.setVisible( true );
	}
}