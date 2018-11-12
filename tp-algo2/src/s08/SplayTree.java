package s08;

public class SplayTree<K extends Comparable<K>> {
  BTree<K> tree = new BTree<K>();
  int crtSize = 0;
  // --------------------------------------------------
  public SplayTree() { super(); }
  // --------------------------------------------------
  public void    add     (K e) {
    if (contains(e)) return;  // This "splays" the tree!
    crtSize++;
    // TODO - A COMPLETER...
  }
  // --------------------------------------------------
  public void    remove  (K e) {
    if (! contains(e)) return; // This "splays" the tree!
    crtSize--;
    if (tree.root().hasLeft() && tree.root().hasRight()) {
      BTree<K> oldRight = tree.root().right().cut();
      tree=tree.root().left().cut();
      BTreeItr<K> maxInLeft= tree.root().rightMost().up();
      BTreeItr<K> ti=splayToRoot(maxInLeft); // now tree has no right subtree!
      ti.right().paste(oldRight);
    } else {  // the tree has only one child
      if (tree.root().hasLeft()) tree=tree.root().left() .cut();
      else                       tree=tree.root().right().cut();
    }
  }
  // --------------------------------------------------
  public boolean contains(K e) {
    if (isEmpty()) return false;
    BTreeItr<K> ti=locate(e);
    boolean absent=ti.isBottom();
    if (absent) ti=ti.up();
    ti=splayToRoot(ti);
    return !absent;
  }
  // --------------------------------------------------
  protected  BTreeItr<K> locate(K e) {
    BTreeItr<K> ti = tree.root();
    while(!ti.isBottom()) {
      K c = ti.consult();
      if (e.compareTo(c)==0) break;
      if (e.compareTo(c)< 0) ti = ti.left();
      else                   ti = ti.right();
    }
    return ti;
  }
  // --------------------------------------------------
  public int     size()    { return crtSize;}
  // --------------------------------------------------
  public boolean isEmpty() { return size() == 0;}
  // --------------------------------------------------
  public K    minElt() { 
    return null; // TODO - A COMPLETER...
  }
  // --------------------------------------------------
  public K    maxElt() {
    return null; // TODO - A COMPLETER...
  }
  // --------------------------------------------------
  public String toString() {
    return ""+tree.toReadableString()+"SIZE:"+size();
  }
  // --------------------------------------------------
  // --- Non-public methods
  // --------------------------------------------------
  // PRE:     ! ti.isBottom()
  // RETURNS: root position
  // WARNING: ti is no more valid
  private BTreeItr<K> splayToRoot(BTreeItr<K> ti) {
    return null; // TODO - A COMPLETER...
  }
  // --------------------------------------------------  
  // PRE / RETURNS : Zig situation (see schemas)
  // WARNING: ti is no more valid
  private BTreeItr<K> applyZig(BTreeItr<K> ti) {
    boolean leftZig=ti.isLeftArc();
    ti=ti.up();
    if (leftZig) ti.rotateRight();
    else         ti.rotateLeft();
    return ti;
  }
  // --------------------------------------------------
  // PRE / RETURNS : ZigZig situation (see schemas)
  // WARNING: ti is no more valid
  private BTreeItr<K> applyZigZig(BTreeItr<K> ti) {
    return null; // TODO - A COMPLETER...
  }
  // --------------------------------------------------
  // PRE / RETURNS : ZigZag situation (see schemas)
  // WARNING: ti is no more valid
  private BTreeItr<K> applyZigZag(BTreeItr<K> ti) {
    return null; // TODO - A COMPLETER...
  }
  // --------------------------------------------------
}
