package s02;
public class IntQueueChained {
  //======================================================================
  /* TODO: adapt using pseudo-pointers instead of queue node objects
   * "Memory management" code:
   * - define "memory" arrays, the NIL constant, and firstFreeCell
   * - define allocate/deallocate, with automatic array expansion
   * "User" code:
   * - modify enqueue/dequeue/..., keeping the same logic/algorithm
   * - test
   */
  //======================================================================
  static class QNode {
    final int    elt;
    QNode next = null;
    // ----------
    public QNode(int e) {elt=e;}
  }
  //======================================================================
  private QNode front;
  private QNode back;
  // ------------------------------
  public IntQueueChained() {}
  // --------------------------
  public void enqueue (int elt) {
    QNode aux = new QNode(elt);
    if (back==null) {
      back = aux; front = aux;
    } else {
      back.next = aux;
      back = aux;
    } 
  }
  // --------------------------
  public boolean isEmpty() {
    return back==null;
  }
  // --------------------------
  public int consult() {
    return front.elt;
  }
  // --------------------------
  public int dequeue() {
    int e = front.elt;
    if (front == back) {
      back = null; front = null;
    } else {
      front = front.next;
    }
    return e;
  }
}
