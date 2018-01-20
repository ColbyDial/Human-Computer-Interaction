import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class myGalleryFrame {
	
	
	public static int myFunction1() {
		System.out.println("My Function got called! ");
		JFileChooser chooser= new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("choosertitle");
	    //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		int choice = chooser.showOpenDialog(chooser);
		if (choice != JFileChooser.APPROVE_OPTION) return choice;

		File chosenFile = chooser.getSelectedFile();
		ArrayList<String> myImages = listFilesForFolder(chosenFile);
	}

	public static void main(String[] Args) {
		//ArrayList<String> myImages = new ArrayList<String>();
		//File chosenFile;
		
		System.out.println("Hello World");
		
		//create -------------------------------------------
		
		//create and set up window frame
		final JFrame frame = new JFrame("My Gallery Viewer");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create panels ------------------------------------
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		JPanel eastPanel = new JPanel();
		JPanel westPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		
		
		//create and add components to panels --------------
		
			//North Panel ----------------------------------
				//create -------
				JLabel userLabel = new JLabel("My Gallery Viewer");
			
				//add ----------
				northPanel.add(userLabel);
	
			//South Panel ----------------------------------
				//create -------
				JButton JFCLauncher = new JButton("Browse");
				JFCLauncher.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {

						myFunction1();
						//this is where I was trying to give my main class the file path for the directory
						//I think it would have looked something like this
						//
						//for each item in returnedArrayListOfFileNames
						// myImages = returnedDirectoryFilePath + "/" + returnedArrayListOfFileNames[1]
					}
				});
	
				//add ----------
				southPanel.add(JFCLauncher);
		
			//East Panel -----------------------------------
		
			//West Panel -----------------------------------
		
			//Center Panel ---------------------------------
				
				//create -------	
				
				
				//BufferedImage image = null;
				// try
				// {
				//   image = ImageIO.read(new File(filename));
				// }
				// catch (Exception e)
				// {
		        //  e.printStackTrace();
		        //  System.exit(1);
		        //}
				//ImageIcon imageIcon = new ImageIcon(image);
				//JLabel jLabel = new JLabel();
		        //jLabel.setIcon(imageIcon);
		        
				//add ----------
				//centerPanel.add(jLabel);
				
		
		//add components to frame --------------------------
		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.add(eastPanel, BorderLayout.EAST);
		frame.add(westPanel, BorderLayout.WEST);
		frame.add(centerPanel, BorderLayout.CENTER);

		
		//initialize
		frame.setVisible(true);
		
	
		
	}
	
	public static ArrayList<String> listFilesForFolder(final File folder) {
		ArrayList<String> fileNames = new ArrayList<String>();
		System.out.println("Files available in directory ----------");
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	            fileNames.add(fileEntry.getName());
	        }
	    }
	    return fileNames;
	}
		
}

class ActionHandler implements ActionListener {
	ArrayList<String> myImages;
	File chosenFile;
	
	public ActionHandler (myGalleryFrame mainClass) {
	//now getters of the main class can be called 
	}
	 
	public void actionPerformed(ActionEvent e) {
	//create file chooser, set to open only folders
	JFileChooser chooser= new JFileChooser();
	chooser.setCurrentDirectory(new java.io.File("."));
	chooser.setDialogTitle("choosertitle");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	chooser.setAcceptAllFileFilterUsed(false);

	 int choice = chooser.showOpenDialog(chooser);
	 if (choice != JFileChooser.APPROVE_OPTION) return;

	 chosenFile = chooser.getSelectedFile();
	// myImages = mainClass.listFilesForFolder(chosenFile);
	}
}
