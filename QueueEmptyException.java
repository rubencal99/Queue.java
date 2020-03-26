// Name: Ruben Calderon
// CruzID: rucalder
// Date: May 19, 2019
// Class: 12b/M
// Description: Throws QueueEmptyException when calling a function on an empty queue.
// File Name: QueueEmptyException.java

public class QueueEmptyException extends RuntimeException{
	public QueueEmptyException (String s) {
		super(s);
	}
}
