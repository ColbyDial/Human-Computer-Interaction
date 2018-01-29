package model;

import java.util.List;
import java.util.ArrayList;

public class Model {

	private final List<ModelListener> listeners = new ArrayList<ModelListener>();

	
	public void addListener(final ModelListener listener) {
		listeners.add(listener);
	}
}
