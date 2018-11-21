package s08;

public class SplayTree<K extends Comparable<K>> {
	BTree<K> tree = new BTree<K>();
	int crtSize = 0;

	// --------------------------------------------------
	public SplayTree() {
		super();
	}

	// --------------------------------------------------
	public void add(K e) {
		if (contains(e))
			return; // This "splays" the tree!
		crtSize++;
		//System.out.printf("addItem : %s \n", e.toString());
		BTreeItr<K> itr = this.tree.root();
		
		if (itr.isBottom()) {
			itr.insert(e);
			return;
		}
			
		
		boolean addBigger = e.compareTo(itr.consult()) > 0;
		BTree<K> tmp = null;
		if (addBigger) {
			if (itr.hasRight())
				tmp = itr.right().cut();
		} else {
			if (itr.hasLeft())
				tmp = itr.left().cut();
		}
		
		BTree<K> tmpTree = itr.cut();
		
		itr.insert(e);
		
		if (addBigger) {
			itr.left().paste(tmpTree);
			if (tmp != null)
				itr.right().paste(tmp);
		} else {
			itr.right().paste(tmpTree);
			if (tmp != null)
				itr.left().paste(tmp);
		}
	}

	// --------------------------------------------------
	public void remove(K e) {
		if (!contains(e))
			return; // This "splays" the tree!
		crtSize--;
		if (tree.root().hasLeft() && tree.root().hasRight()) {
			BTree<K> oldRight = tree.root().right().cut();
			tree = tree.root().left().cut();
			BTreeItr<K> maxInLeft = tree.root().rightMost().up();
			BTreeItr<K> ti = splayToRoot(maxInLeft); // now tree has no right subtree!
			ti.right().paste(oldRight);
		} else { // the tree has only one child
			if (tree.root().hasLeft())
				tree = tree.root().left().cut();
			else
				tree = tree.root().right().cut();
		}
	}

	// --------------------------------------------------
	public boolean contains(K e) {
		if (isEmpty())
			return false;
		BTreeItr<K> ti = locate(e);
		boolean absent = ti.isBottom();
		if (absent)
			ti = ti.up();
		ti = splayToRoot(ti);
		return !absent;
	}

	// --------------------------------------------------
	protected BTreeItr<K> locate(K e) {
		BTreeItr<K> ti = tree.root();
		while (!ti.isBottom()) {
			K c = ti.consult();
			if (e.compareTo(c) == 0)
				break;
			if (e.compareTo(c) < 0)
				ti = ti.left();
			else
				ti = ti.right();
		}
		return ti;
	}

	// --------------------------------------------------
	public int size() {
		return crtSize;
	}

	// --------------------------------------------------
	public boolean isEmpty() {
		return size() == 0;
	}

	// --------------------------------------------------
	public K minElt() {
		BTreeItr<K> itr = this.tree.root();
		while(itr.hasLeft()) {
			itr = itr.left();
		}
		return itr.consult();
	}

	// --------------------------------------------------
	public K maxElt() {
		BTreeItr<K> itr = this.tree.root();
		while(itr.hasRight()) {
			itr = itr.right();
		}
		return itr.consult();
	}

	// --------------------------------------------------
	public String toString() {
		return "" + tree.toReadableString() + "SIZE:" + size();
	}

	// --------------------------------------------------
	// --- Non-public methods
	// --------------------------------------------------
	// PRE: ! ti.isBottom()
	// RETURNS: root position
	// WARNING: ti is no more valid
	private BTreeItr<K> splayToRoot(BTreeItr<K> ti) {	
		//System.out.println("splay : " + ti.consult());
		while(!ti.isRoot()) {
			if (ti.up().isRoot()) {
				return applyZig(ti);
			} else {
				if (ti.isLeftArc() != ti.up().isLeftArc())
					ti = applyZigZag(ti);
				else {
					ti = applyZigZig(ti);
				}
			}
		}
		//System.out.println("splayed : " + ti.consult());
		return ti;
	}

	// --------------------------------------------------
	// PRE / RETURNS : Zig situation (see schemas)
	// WARNING: ti is no more valid
	private BTreeItr<K> applyZig(BTreeItr<K> ti) {
		boolean leftZig = ti.isLeftArc();
		ti = ti.up();
		if (leftZig)
			ti.rotateRight();
		else
			ti.rotateLeft();
		return ti;
	}

	// --------------------------------------------------
	// PRE / RETURNS : ZigZig situation (see schemas)
	// WARNING: ti is no more valid
	private BTreeItr<K> applyZigZig(BTreeItr<K> ti) {
		boolean leftZigZig = ti.isLeftArc();
		//assert(ti.up().isLeftArc() == leftZigZig);
		//System.out.println("isLeft : " + leftZigZig);
		ti = ti.up().up();
		if (leftZigZig) {
			ti.rotateRight();
			ti.rotateRight();
		} else {
			ti.rotateLeft();
			ti.rotateLeft();
		}
		return ti;
	}

	// --------------------------------------------------
	// PRE / RETURNS : ZigZag situation (see schemas)
	// WARNING: ti is no more valid
	private BTreeItr<K> applyZigZag(BTreeItr<K> ti) {
		boolean leftZigZag = ti.isLeftArc();
		BTreeItr<K> parentItr = ti.up();
		if (leftZigZag) {
			parentItr.rotateRight();
			parentItr = parentItr.up();
			parentItr.rotateLeft();
		} else {
			parentItr.rotateLeft();
			parentItr = parentItr.up();
			parentItr.rotateRight();
		}
		return parentItr;
	}
	// --------------------------------------------------
}
