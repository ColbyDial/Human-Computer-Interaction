import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * MainFrame is a {@link JFrame} for displaying an {@link ImagePanel} and other
 * basic controls.
 * 
 * @author nic
 */
public class MainFrame
	extends JFrame
{
	private static final long serialVersionUID = 2269971701250845501L;
	
	private ImagePanel imagePanel;

	public MainFrame()
	{
		super( "Image Viewer" );
		
		//initialize all the Frame contents and state
		init();
	}
	
	private void init()
	{
		//frame state: size and close operation
		setSize( 600, 400 );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		//initialize the main image viewing pane: ImagePanel
		initPane();
		//initalize the menu system
		initMenus();
	}
	
	private void initMenus()
	{
		final JMenuItem open = new JMenuItem( "Open" );
		try {
			open.setIcon( new ImageIcon( getClass().getResource( "open.png" ) ) );
		}
		catch( final Exception e ) {
			//ignore, no icon is no big deal
		}
		//mnemonic allows hitting this key when the menu is open
		open.setMnemonic( 'o' );
		//accelerator allows hitting this keystroke/combination at any time
		open.setAccelerator(
				KeyStroke.getKeyStroke( KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK ) );
		open.addActionListener( new OpenDirectoryAction() );
		
		//a Quit menu item to allow quitting easily
		final JMenuItem exit = new JMenuItem( "Quit" );
		try {
			exit.setIcon( new ImageIcon( getClass().getResource( "quit.png" ) ) );
		}
		catch( final Exception e ) {
			//ignore, no icon is no big deal
		}
		exit.setMnemonic( 'q' );
		exit.setAccelerator(
				KeyStroke.getKeyStroke( KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK ) );
		exit.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e )
			{
				MainFrame.this.dispose();
				System.exit( 0 );
			}
		} );
		
		final JMenu file = new JMenu( "File" );
		file.setMnemonic( 'f' );
		file.add( open );
		file.add( exit );
		
		final JMenuItem helpItem = new JMenuItem( "Help" );
		try {
			helpItem.setIcon( new ImageIcon( getClass().getResource( "help.png" ) ) );
		}
		catch( final Exception e ) {
			//ignore, no icon is no big deal
		}
		helpItem.setMnemonic( 'h' );
		helpItem.setAccelerator( KeyStroke.getKeyStroke(
				KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK ) );
		helpItem.addActionListener( new ActionListener() {
			public void actionPerformed( final ActionEvent e )
			{
				JOptionPane.showMessageDialog( MainFrame.this,
						"Open a directory containing image files with the Open menu item\n"
						+ "in the File menu.  Then cycle through images using: (1) menu\n"
						+ "items, (2) keyboard shortcuts (Ctrl+N and Ctrl+P), (3) buttons,\n"
						+ "(4) combo box selecting image file by name, (5) spinner selecting\n"
						+ "image by order index, (6) slideshow auto-cycling by selecting\n"
						+ "slideshow checkbox or menu item or Ctrl+S, (7) right-click on\n"
						+ "image to select file by name, (8) left-click on image, or (9)\n"
						+ "mouse scroll wheel when cursor is on image." );
			}
		} );
		
		final JMenu help = new JMenu( "Help" );
		help.setMnemonic( 'h' );
		help.add( helpItem );
		
		final JMenuBar menubar = new JMenuBar();
		menubar.add( file );
		
		//allow the ImagePanel to inject some menus/menu items
		imagePanel.initializeMenus( menubar );
		
		menubar.add( help );
		
		setJMenuBar( menubar );
	}
	
	/**
	 * Initialize the contents of the MainFrame's content pane.
	 */
	private void initPane()
	{
		//ImagePanel is self-contained
		imagePanel = new ImagePanel();
		
		//put it in the CENTER of a BorderLayout, so it stretches to the full
		//window size
		getContentPane().setLayout( new BorderLayout() );
		getContentPane().add( imagePanel, BorderLayout.CENTER );
	}

	private final class OpenDirectoryAction
		implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
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
				
				imagePanel.setImageFiles( imageFiles );
			}
		}
	}
}