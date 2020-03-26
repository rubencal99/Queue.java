// Name: Ruben Calderon
// CruzID: rucalder
// Class: 12B/M
// Date: May 19, 2019
// Description: Conatins the prototypes of all the methods used in Queue.java
// File Name: QueueInterface.java

public interface QueueInterface{

   // isEmpty()
   //    // pre: none
   //       // post: returns true if this Queue is empty, false otherwise
    public boolean isEmpty();

   // length()
   //    // pre: none
   //       // post: returns the length of this Queue.
   public int length();

   // enqueue()
   //    // adds newItem to back of this Queue
   //       // pre: none
   //          // post: !isEmpty()
   public void enqueue(Object newItem);

   // dequeue()
   //    // deletes and returns item from front of this Queue
   //       // pre: !isEmpty()
   //          // post: this Queue will have one fewer element
   public Object dequeue() throws QueueEmptyException;

   // peek()
   //    // pre: !isEmpty()
   //       // post: returns item at front of Queue
    public Object peek() throws QueueEmptyException;

   // dequeueAll()
   //    // sets this Queue to the empty state
   //       // pre: !isEmpty()
   //          // post: isEmpty()
    public void dequeueAll() throws QueueEmptyException;

   // toString()
   //    // overrides Object's toString() method
    public String toString();
}
