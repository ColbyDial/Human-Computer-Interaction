package controller;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import model.Model;

public class Controller
{
private final Model model;
	
	public Controller( final Model model )
	{
		this.model = model;
	}
	
	public SelectionListener getSelectionListener (final char valueToAppend)
	{
		return new SelectionAdapter()
		{
			public void widgetSelected(final SelectionEvent e)
			{
				//model.append(valueToAppend);
			}
		};
	}
	
	public SelectionListener getEvaluateSelectionListener()
	{
		return new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				//model.evaluate();
			}
		};
	}
}
