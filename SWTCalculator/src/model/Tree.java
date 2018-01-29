package model;

import java.util.ArrayList;
import java.util.List;
import model.Node;

public class Tree<T> 
{
	public Node<T> root;
	
	public Tree()
	{
		root = new Node<T>(null);
	}
	
	public Tree(T t)
	{
		root = new Node<T>(t);
	}
	
	public Node<T> getLeftChild(Node<T> node)
	{
		return node.leftChild;
	}
}