import org.eclipse.swt.widgets.Display;

import controller.Controller;
import model.Model;
import view.CalculatorView;

public class Driver {
	public static void main(String[] args) {
		//setup the Model
		final Model model = new Model();
		
		//setup the Controller
		final Controller controller = new Controller( model );
		
		//setup the View
		Display display = new Display();
		CalculatorView view = new CalculatorView(
				display, model, controller );
		
		//Event loop
		while( !view.isDisposed() ) {
			if( !display.readAndDispatch() ) {
				display.sleep();
			}
		}
	}
}
