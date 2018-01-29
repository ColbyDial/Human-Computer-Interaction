package model;

public class Node<T> 
{
	public Node<T> leftChild;
	public Node<T> rightChild;
	public Node<T> parent;
	public T data;
	
	public Node()
	{
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
		this.data = null;
	}
	
	public Node(T t)
	{
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
		this.data = t;
	}
	
	public Node(T t, Node<T> parentNode)
	{
		this.data = t;
		this.parent = parentNode;
		this.leftChild = null;
		this.rightChild = null;
	}
	

}
