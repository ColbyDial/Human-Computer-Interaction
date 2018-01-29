import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * An ImagePanel is a {@link JPanel} with contents for showing an image (scaled
 * to fit the available space), and various controls for changing it.  The
 * image(s) are taken from a collection of {@link File}s passed in to
 * {@link #setImageFiles(Collection)}.
 * 
 * @author nic
 */
public class ImagePanel
	extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/*
	 * Mnemonic character definitions
	 */
	private static final char NEXT_MNEMONIC = 'n';
	private static final char PREVIOUS_MNEMONIC = 'p';
	private static final char SLIDESHOW_MNEMONIC = 's';
	
	/*
	 * Accelerator (combined with Ctrl key) character definitions
	 */
	private static final int NEXT_ACCELERATOR = KeyEvent.VK_N;
	private static final int PREVIOUS_ACCELERATOR = KeyEvent.VK_P;
	private static final int SLIDESHOW_ACCELERATOR = KeyEvent.VK_S;

	public ImagePanel()
	{
		//initialize all the ImagePanel components
		init();
	}
	
	private JLabel imageLabel; //where the image is shown
	private JComponent[] components; //components to enable on setting a file list
	private JSpinner spinner; //spinner, needs updated on image change
	private JLabel count; //label for count of total images
	private JComboBox<String> combo; //combo box for selecting image by file name
	//two checkbox-based widgets (menu vs. button) for starting/stopping a
	//slideshow
	private JCheckBoxMenuItem slideMenu;
	private JCheckBox slideButton;
	
	private void init()
	{
		imageLabel = new JLabel();
		//start label with a helpful text hint on where to start
		imageLabel.setText( "Select Open from File menu" );
		imageLabel.setHorizontalAlignment( SwingConstants.CENTER );
		//on size change, resize the current image
		imageLabel.addComponentListener( new ComponentAdapter() {
			public void componentResized( ComponentEvent e )
			{
				if( imageFiles != null ) {
					setImage( index );
				}
			}
		} );
		//on mouse wheel advance image forward/back by wheel click count
		imageLabel.addMouseWheelListener( new MouseWheelListener() {
			public void mouseWheelMoved( final MouseWheelEvent e )
			{
				if( imageFiles != null ) {
					setImage( (index + e.getWheelRotation()) % imageFiles.length );
				}
			}
		} );
		//on left (button1) click, advance image
		imageLabel.addMouseListener( new MouseAdapter() {
			public void mouseClicked( MouseEvent e )
			{
				if( imageFiles != null && e.getButton() == MouseEvent.BUTTON1 ) {
					setImage( (index + 1) % imageFiles.length );
				}
			}
		} );
		
		setLayout( new BorderLayout() );
		
		add( imageLabel, BorderLayout.CENTER );
		
		//a JPanel of buttons/controls for the bottom of the ImagePanel
		final JPanel buttons = new JPanel();
		add( buttons, BorderLayout.SOUTH );
		//FlowLayout is the default, and has nice buffer space between widgets
//		buttons.setLayout( new FlowLayout() );
		
		//previous image button
		final JButton prev = new JButton();
//		prev.setText( "Previous" );
		prev.setIcon( new ImageIcon( getClass().getResource( "previous.png" ) ) );
		prev.addActionListener( new PreviousImageAction() );
		prev.setEnabled( false );
		
		//next image button
		final JButton next = new JButton();
//		next.setText( "Next" );
		next.setIcon( new ImageIcon( getClass().getResource( "next.png" ) ) );
		next.addActionListener( new NextImageAction() );
		next.setEnabled( false );
		
		//spinner for next/previous or typing in image number
		spinner = new JSpinner();
		spinner.addChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e )
			{
				final int v = (Integer)spinner.getValue();
				//make sure not the invoke setImage if the value is the same
				//as the current image (because setImage ends up setting the
				//Spinner value, leading to a feedback loop)
				if( v != index + 1 ) {
					setImage( v - 1 );
				}
			}
		} );
		spinner.setEnabled( false );
		
		//combo box for selecting image by file name
		combo = new JComboBox<String>();
		combo.setEditable( false );
		combo.setEnabled( false );
		combo.addItemListener( new ItemListener() {
			public void itemStateChanged( ItemEvent e )
			{
				if( index != combo.getSelectedIndex() ) {
					setImage( combo.getSelectedIndex() );
				}
			}
		} );
		JPanel comboPanel = new JPanel(); //use a panel for its gaps
		comboPanel.add( combo );
		add( comboPanel, BorderLayout.NORTH );
		combo.setVisible( false );
		
		slideButton = new JCheckBox( "Slideshow" );
		slideButton.setEnabled( false );
		
		//simple label for showing count of total images
		count = new JLabel();
		
		//array of components that initialize disable, but want enabled when
		//some image files are set in setImageFiles(...)
		components = new JComponent[] { prev, next, spinner, combo, slideButton };
		buttons.add( prev );
		buttons.add( next );
		buttons.add( spinner );
		buttons.add( count );
		buttons.add( slideButton );
	}
	
	void initializeMenus( final JMenuBar menubar )
	{
		//Create a navigation menubar menu for next and previous
		final JMenu nav = new JMenu( "Navigation" );
		nav.setMnemonic( 'n' );
		menubar.add( nav );
		
		final JMenuItem forward = new JMenuItem( "Next" );
		try {
			final Image i = ImageIO.read( getClass().getResource( "next.png" ) );
			forward.setIcon( new ImageIcon( i.getScaledInstance( 16, 16, Image.SCALE_SMOOTH )) );
		}
		catch( final Exception e ) {
			//oh well, no icon image, we still have the text
		}
		forward.setAccelerator(
				KeyStroke.getKeyStroke( NEXT_ACCELERATOR, InputEvent.CTRL_DOWN_MASK ) );
		forward.setMnemonic( NEXT_MNEMONIC );
		forward.addActionListener( new NextImageAction() );
		
		final JMenuItem back = new JMenuItem( "Previous" );
		try {
			final Image i = ImageIO.read( getClass().getResource( "previous.png" ) );
			back.setIcon( new ImageIcon( i.getScaledInstance( 16, 16, Image.SCALE_SMOOTH )) );
		}
		catch( final Exception e ) {
			//oh well, no icon image, we still have the text
		}
		back.setAccelerator(
				KeyStroke.getKeyStroke( PREVIOUS_ACCELERATOR, InputEvent.CTRL_DOWN_MASK ) );
		back.setMnemonic( PREVIOUS_MNEMONIC );
		back.addActionListener( new PreviousImageAction() );
		
		slideMenu = new JCheckBoxMenuItem( "Slideshow" );
		try {
			final Image i = ImageIO.read( getClass().getResource( "slideshow.png" ) );
			slideMenu.setIcon( new ImageIcon( i.getScaledInstance( 16, 16, Image.SCALE_SMOOTH )) );
		}
		catch( final Exception e ) {
			//oh well, no icon image, we still have the text
		}
		slideMenu.setMnemonic( SLIDESHOW_MNEMONIC );
		slideMenu.setAccelerator(
				KeyStroke.getKeyStroke( SLIDESHOW_ACCELERATOR, InputEvent.CTRL_DOWN_MASK ) );
		slideMenu.setSelected( false );
		final ActionListener l = new SlideshowAction( slideMenu, slideButton );
		slideMenu.addActionListener( l );
		slideButton.addActionListener( l );
		
		nav.add( forward );
		nav.add( back );
		nav.addSeparator();
		nav.add( slideMenu );
	}

	private File[] imageFiles;
	private int index;
	
	public void setImageFiles( final Collection<File> imageFiles )
	{
		this.imageFiles = imageFiles.toArray( new File[ imageFiles.size() ] );
		imagesCache = new Image[ imageFiles.size() ];
		
		//create a context (popup) menu with all image file names to select
		//from, and populate the combo box
		final JPopupMenu menu = new JPopupMenu();
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		int idx = 0;
		for( final File f : this.imageFiles ) {
			final JMenuItem i = new JMenuItem( f.getName() );
			final int fidx = idx;
			i.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed( ActionEvent e )
				{
					setImage( fidx );
				}
			} );
			menu.add( i );
			model.addElement( f.getName() );
			idx++;
		}
		imageLabel.setComponentPopupMenu( menu );
		combo.setModel( model );
		//re-layout the ImagePanel since the JComboBox needs resized to fit
		//its longest item text
		doLayout();
		
		//if we had image files, enable components, initialize spinner and
		//count label, and set the current image to the first one
		if( this.imageFiles.length > 0 ) {
			for( final JComponent c : components ) {
				c.setEnabled( true );
			}
			spinner.setModel(
					new SpinnerNumberModel( 1, 1, imageFiles.size(), 1 ) );
			count.setText( " of " + imageFiles.size() );
			combo.setVisible( true );
			setImage( 0 );
		}
	}
	
	private Image[] imagesCache;
	
	void setImage( int index )
	{
		//if we haven't setup images yet... do nothing
		if( imageFiles == null || imageFiles.length == 0 ) {
			return;
		}
		//allow wrapping around the bottom of the list
		while( index < 0 ) {
			index += imageFiles.length;
		}
		
		this.index = index;
		//if we haven't read the image from disk, yet, do so
		if( imagesCache[index] == null ) {
			try {
				final Image i = ImageIO.read( imageFiles[index] );
				imagesCache[index] = i;
			}
			catch( final IOException ioe ) {
				System.err.println( "Error reading " + imageFiles[index] );
				ioe.printStackTrace();
			}
		}
		
		//get target and original image dimensions, so we can scale while
		//keeping aspect ratio
		final Dimension targetSize = imageLabel.getSize();
		final int w = imagesCache[index].getWidth( null ),
				h = imagesCache[index].getHeight( null );
		
		//scale the image to keep aspect ratio but fill as much of the screen
		//as possible
		final Image scaled;
		if( (double)w / h < targetSize.getWidth() / targetSize.getHeight() ) {
			//height is constraining dimension
			scaled = imagesCache[index].getScaledInstance(
					(int)(targetSize.getHeight() * w / h),
					(int)targetSize.getHeight(),
					Image.SCALE_FAST );
		}
		else {
			scaled = imagesCache[index].getScaledInstance(
					(int)targetSize.getWidth(),
					(int)(targetSize.getWidth() * h / w),
					Image.SCALE_FAST );
		}
		
		imageLabel.setIcon( new ImageIcon( scaled ) );
		imageLabel.setText( null );
		
		//update the spinner
		spinner.setValue( (Integer)(index + 1) );
		combo.setSelectedIndex( index );
	}

	private final class SlideshowAction
		implements ActionListener
	{
		private final JCheckBoxMenuItem menu;
		private final JCheckBox button;
		
		private boolean state;
		
		private Timer t;

		private SlideshowAction( JCheckBoxMenuItem slide, JCheckBox button )
		{
			this.menu = slide;
			this.button = button;
		}

		public void actionPerformed( ActionEvent e )
		{
			if( e.getSource() == menu ) {
				state = menu.isSelected();
			}
			else if( e.getSource() == button ) {
				state = button.isSelected();
			}
			if( state ) {
				if( t == null ) {
					t = new Timer( 1500, new NextImageAction() );
				}
				if( !t.isRunning() ) {
					t.start();
				}
			}
			else {
				if( t != null && t.isRunning()) {
					t.stop();
				}
			}
			
			if( menu.isSelected() != state ) {
				menu.setSelected( state );
			}
			if( button.isSelected() != state ) {
				button.setSelected( state );
			}
		}
	}

	private final class NextImageAction
		implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			if( imageFiles != null ) {
				setImage( (index + 1) % imageFiles.length );
			}
		}
	}

	private final class PreviousImageAction
		implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			if( imageFiles != null ) {
				setImage( (index - 1) % imageFiles.length );
			}
		}
	}
}