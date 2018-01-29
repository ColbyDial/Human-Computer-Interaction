package driver;

import controller.Controller;
import model.Model;
import view.Window;

public class Driver {

	public static void main(String[] args)
	{
		//create "the Model"
		Model model = new Model();
		
		//create "the Controller"
		Controller controller = new Controller( model );
		
		//create "the View"
		Window w = new Window( model, controller );
	}

}