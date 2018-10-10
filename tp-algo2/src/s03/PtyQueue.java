package s03;

public class PtyQueue<E, P extends Comparable<P>> {
  private final Heap<HeapElt> heap;
  // ------------------------------------------------------------
  public PtyQueue() {
    heap = new Heap<HeapElt>();
  }
  // ------------------------------------------------------------
  public boolean isEmpty() {
    return heap.isEmpty();
  }

  /** Adds an element elt with priority pty */ 
  public void enqueue(E elt, P pty) {
    heap.add(new HeapElt(pty, elt));
  }
  
  /** Returns the element with highest priority. PRE: !isEmpty() */ 
  public E consult() {
    return heap.min().element;
  }

  /** Returns the priority of the element with highest priority. PRE: !isEmpty() */ 
  public P consultPty() {
    return heap.min().priority;
  }
  
  /** Removes and returns the element with highest priority. PRE: !isEmpty() */ 
  public E dequeue() {
    return heap.removeMin().element;
  }

  @Override public String toString() {
    return heap.toString(); 
  }
  //=============================================================
  class HeapElt implements Comparable<HeapElt> {
    P priority;
    E element;
    
    public HeapElt(P thePty, E theElt) {
    	element = theElt;
    	priority = thePty;
    }

    @Override public int compareTo(HeapElt ele) {
      return ele.priority.compareTo(priority);
    }
  }
}

