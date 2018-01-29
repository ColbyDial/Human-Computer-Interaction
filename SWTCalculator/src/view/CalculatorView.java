package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import controller.Controller;
import model.Model;
import model.ModelListener;

public class CalculatorView implements ModelListener {

	private final Shell shell;
	/*
	 * 
	 */
	private final Model model;
	public CalculatorView(final Display d, final Model model, final Controller controller)
	{
		shell = new Shell( d );
		this.model = model;
		
		shell.setSize( 300, 300 );
		shell.setText( "SWT Calc Demo" );
		
		init( d, controller );
		
		shell.open();
		
		model.addListener( this );
	}
	
	private void init(final Display display, final Controller controller) {
		
	}

	@Override
	public void expressionUpdated() {
		// TODO Auto-generated method stub
		
	}
	public boolean isDisposed()
	{
		return shell.isDisposed();
	}
}
