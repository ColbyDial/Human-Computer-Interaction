package model;

import java.util.List;
import java.util.ArrayList;
import model.Tree;


public class Model
{
	
	private Tree<String> expressionTree = new Tree<String>(null);
	private final List<ModelListener> listeners = new ArrayList<ModelListener>();

	
	public void addListener(final ModelListener listener)
	{
		listeners.add(listener);
	}
}
