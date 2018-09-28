package s03;

public class PtyQueue<E, P extends Comparable<P>> {
  private final Heap<HeapElt> heap;
  // ------------------------------------------------------------
  public PtyQueue() {
    heap=null; // TODO - A COMPLETER
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
    return null; // TODO - A COMPLETER 
  }

  /** Returns the priority of the element with highest priority. PRE: !isEmpty() */ 
  public P consultPty() {
    return null; // TODO - A COMPLETER 
  }
  
  /** Removes and returns the element with highest priority. PRE: !isEmpty() */ 
  public E dequeue() {
    return null; // TODO - A COMPLETER 
  }

  @Override public String toString() {
    return heap.toString(); 
  }
  //=============================================================
  class HeapElt implements Comparable<HeapElt> {
    // TODO A COMPLETER
    
    public HeapElt(P thePty, E theElt) {
      // TODO A COMPLETER
    }

    @Override public int compareTo(HeapElt arg0) {
      return 0; // TODO A COMPLETER
    }
  }
}

