// Name: Ruben Calderon
// CruzID: rucalder
// Date: May 19, 2019
// Class: 12B/M
// Description: Contains provate Node class as well as a linked list data structure using Node
// File Name: Queue.java

public class Queue {
	
	private class Node{
		Node next;
		Object item;
		
		public Node(Object newItem) {
			this.next=null;
			this.item =newItem;
		}
	}
	
	private int queueSize;
	private Node head;
	private Node tail;
	
	public Queue(){
		head = null;
		queueSize = 0;
		tail = null;
	}
	
	// isEmpty()
	// 	// pre: none
	// 		// post: returns true if this Queue is empty, false otherwise
	public boolean isEmpty() {
	   return this.length() == 0;
   }

	// length()
	//    // pre: none
	//       // post: returns the length of this Queue.
	public int length() {
	   return queueSize;
   }

// getObj()
// returns item at index i of the linked list
public Object getObj(int i) {
	   Node N = this.head;
	   for(int j = 0; j < i; j++) {
		   N = N.next;
	   }
	   return N.item;
   }

	// enqueue()
	//    // adds newItem to back of this Queue
	//       // pre: none
	//          // post: !isEmpty()
	public void enqueue(Object newItem) {
	   if(this.isEmpty()) {
		   this.head = new Node(newItem);
		   this.tail = head;
	   }
	   else {
		   Node N = new Node(newItem);
		   tail.next = N;
		   tail = N;
		   tail.next = null;
	   }
	   queueSize++;
   }

// dequeue()
//    // deletes and returns item from front of this Queue
//       // pre: !isEmpty()
//          // post: this Queue will have one fewer element
	public Object dequeue() throws QueueEmptyException{
	   if(this.isEmpty()) {
		   throw new QueueEmptyException("Error: Empty queue.");
	   }      
	   else if(head == tail) {
		   Node temp = head;
		   head = null;
		   tail = null;
		   queueSize--;
		   return temp.item;
	   }
	   Node temp = head;
	   head = head.next;  
	   temp.next = null;
	   queueSize--;
	   return temp.item;
   }

// peek()
//    // pre: !isEmpty()
//       // post: returns item at front of Queue
	public Object peek() throws QueueEmptyException{
	   if(this.isEmpty()) {
		   throw new QueueEmptyException("Error: Empty queue.");
	   }
	   return head.item;
   }

// dequeueAll()
//    	// sets this Queue to the empty state
//    	   	// pre: !isEmpty()
//    	   	   	// post: isEmpty()
	public void dequeueAll() throws QueueEmptyException{
   		if(this.isEmpty()) {
 		   throw new QueueEmptyException("Error: Empty queue.");
   		}
   		else {
	   		head = null;
	   		tail = null;
	   		queueSize = 0;
   		}
   	}

// toString()
//    	// overrides Object's toString() method
	public String toString() {
   		String temp = "";
   		Node N = head;
   		while(N!=null) {
   			temp += N.item + " ";
   			N = N.next;
   		}
   		return temp;
   	}
}    	
