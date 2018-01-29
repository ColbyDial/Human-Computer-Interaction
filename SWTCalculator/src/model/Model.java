package model;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

import model.Infix;

public class Model
{
	Infix fix=new Infix();
	public String expression = "0";
	String appendage = "";
	
	
	private final List<ModelListener> listeners = new ArrayList<ModelListener>();

	
	public void addListener(final ModelListener listener)
	{
		listeners.add(listener);
	}


	public void append(char valueToAppend) {
		
		switch(valueToAppend) {
		case'C':
			expression = "0";
			notifyListeners();
			break;
		case'X':
			if (expression != "" && expression.length() > 0) {
		        expression = expression.substring(0, expression.length() - 1);
		        expression = expression.replaceAll("X", "");
		        notifyListeners();
		    }
		default:
			if(expression == "0") {
				expression = "";
			}
			if(valueToAppend != 'X') {
				appendage = Character.toString(valueToAppend);
				expression = expression + appendage;
				notifyListeners();
			}
			break;
		}
		

	}

	private void notifyListeners()
	{
		for( final ModelListener listener : listeners ) {
			listener.expressionUpdated();
		}
	}

	public void evaluate() {
		// TODO Auto-generated method stub
		double evaluated = fix.infix(expression);
		System.out.println(expression+"="+fix.infix(expression));
		expression = Double.toString(evaluated);
		notifyListeners();
		expression = "0";
	}
}
