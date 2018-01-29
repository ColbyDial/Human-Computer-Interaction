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

public class CalculatorView implements ModelListener
{

	private Text expression;
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
	
	private void init(final Display display, final Controller controller)
	{
		expression = new Text( shell, SWT.BORDER );
		expression.setLayoutData( new GridData(SWT.FILL, SWT.TOP, false, true, 3, 1 ) );
		expression.setFont( new Font( display, "Courier", 20, SWT.DEFAULT ) );
		
		final GridData buttonLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true );
		
		Button add = new Button( shell, SWT.PUSH );
		add.setText( "+" );
		add.setLayoutData( buttonLayoutData );
		add.addSelectionListener( controller.getSelectionListener( '+' ) );
		
		Button evaluate = new Button( shell, SWT.PUSH );
		evaluate.setText( "=" );
		evaluate.setLayoutData( buttonLayoutData );
		evaluate.addSelectionListener( controller.getEvaluateSelectionListener() );	
		
		Button nine = new Button( shell, SWT.PUSH );
		nine.setText( "9" );
		nine.setLayoutData( buttonLayoutData );
		nine.addSelectionListener( controller.getSelectionListener( '9' ) );
		
		Button eight = new Button( shell, SWT.PUSH );
		eight.setText( "8" );
		eight.setLayoutData( buttonLayoutData );
		eight.addSelectionListener( controller.getSelectionListener( '8' ) );
		
		Button seven = new Button( shell, SWT.PUSH );
		seven.setText( "7" );
		seven.setLayoutData( buttonLayoutData );
		seven.addSelectionListener( controller.getSelectionListener( '7' ) );
		
		Button six = new Button( shell, SWT.PUSH );
		six.setText( "6" );
		six.setLayoutData( buttonLayoutData );
		six.addSelectionListener( controller.getSelectionListener( '6' ) );
		
		Button five = new Button( shell, SWT.PUSH );
		five.setText( "5" );
		five.setLayoutData( buttonLayoutData );
		five.addSelectionListener( controller.getSelectionListener( '5' ) );
		
		Button four = new Button( shell, SWT.PUSH );
		four.setText( "4" );
		four.setLayoutData( buttonLayoutData );
		four.addSelectionListener( controller.getSelectionListener( '4' ) );
		
		Button three = new Button( shell, SWT.PUSH );
		three.setText( "3" );
		three.setLayoutData( buttonLayoutData );
		three.addSelectionListener( controller.getSelectionListener( '3' ) );
		
		Button two = new Button( shell, SWT.PUSH );
		two.setText( "2" );
		two.setLayoutData( buttonLayoutData );
		two.addSelectionListener( controller.getSelectionListener( '2' ) );		
		
		Button one = new Button( shell, SWT.PUSH );
		one.setText( "1" );
		one.setLayoutData( buttonLayoutData );
		one.addSelectionListener( controller.getSelectionListener( '1' ) );
		
		Button zero = new Button( shell, SWT.PUSH );
		zero.setText( "0" );
		zero.setLayoutData( buttonLayoutData );
		zero.addSelectionListener( controller.getSelectionListener( '0' ) );
		
		
		shell.setLayout( new GridLayout( 3, true ) );
	}

	@Override
	public void expressionUpdated()
	{
		// TODO Auto-generated method stub
		
	}
	public boolean isDisposed()
	{
		return shell.isDisposed();
	}
}
