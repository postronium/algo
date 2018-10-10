package s04;
// =======================
public class BST<E extends Comparable<E>> {
  protected BTree<E> tree;
  protected int   crtSize;

  public BST() {
    tree = new BTree<E>();
    crtSize = 0;
  }

  public BST(E [] tab) {  // PRE sorted, no duplicate
    // TODO - A COMPLETER
  }

  /** returns where e is, or where it should be inserted as a leaf */
  protected  BTreeItr<E> locate(E e) {
    return null; // TODO - A COMPLETER
  }

  public void add(E e) {
    // TODO - A COMPLETER
  }

  public void remove(E e) {
    // TODO - A COMPLETER
  }

  public boolean contains(E e) {
    BTreeItr<E> ti = locate(e);
    return ! ti.isBottom();
  }

  public int size() {
    return crtSize;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public E minElt() {
    return null;  // TODO - A COMPLETER
  }

  public E maxElt() {
    return null;  // TODO - A COMPLETER
  }

  @Override public String toString() {
    return ""+tree;
  }
  
  public String toReadableString() {
    String s = tree.toReadableString();
    s += "size=="+crtSize+"\n";
    return s;
  }
  
  // --------------------------------------------------
  // --- Non-public methods
  // --------------------------------------------------
  
  private BTree<E> optimalBST(E[] sorted, int left, int right) {
    BTree<E> r = new BTree<E>();
    BTreeItr<E> ri = r.root();
    // TODO - A COMPLETER 
    return r;
  }
}
