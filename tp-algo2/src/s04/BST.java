package s04;

// =======================
public class BST<E extends Comparable<E>> {
	protected BTree<E> tree;
	protected int crtSize;

	public BST() {
		tree = new BTree<E>();
		crtSize = 0;
	}

	public BST(E[] tab) { // PRE sorted, no duplicate
		// TODO - A COMPLETER
	}

	/** returns where e is, or where it should be inserted as a leaf */
	protected BTreeItr<E> locate(E e) {
		if (this.tree == null) {			//?TODO? ?WTF!!?
			this.tree = new BTree<E>();		//scchould work without this uggly if statement
			return new BTreeItr<E>(this.tree);
		}
		return this.locateInSubTree(e, new BTreeItr<E>(this.tree));
	}
	
	private BTreeItr<E> locateInSubTree(E e, BTreeItr<E> itr) {
		if (itr.isBottom()) return itr;						//e not found. e would be inserted here.
		int diff = e.compareTo(itr.consult());				//negative if e smaller
		e.compareTo(itr.consult());
		if (diff == 0) return itr;							//e found at this position
		if (diff < 0)
			return this.locateInSubTree(e, itr.left());		//e is somewhere in the left subtree
		return this.locateInSubTree(e, itr.right());		//e is somewhere in the right subtree
	}

	public void add(E e) {
		BTreeItr<E> ePos =  this.locate(e);
		if (ePos.isBottom())								//if e must be inserted at the bottom
			ePos.insert(e);										//insert it
															//else e is already in the tree
	}

	public void remove(E e) {
		BTreeItr<E> ePos =  this.locate(e);
		if (ePos.isBottom()) 								//e not found, nothing to remove
			return;
		
		if (!ePos.hasLeft() && !ePos.hasRight()) {			//e is a node
			ePos.up().cut();									//remove e
			return ;
		}
		
		if (ePos.hasLeft() && ePos.hasRight()) {
			BTree<E> leftTree = ePos.left().cut();			//save children of e
			BTree<E> rightTree = ePos.right().cut();
			ePos.cut();										//remove e
			BTreeItr<E> itr = new BTreeItr<E>(leftTree);
			
			if (itr.isBottom())
				ePos.cut();
			
			while(!itr.right().isBottom())					//go to right most biggest) element
				itr = itr.right();
			
			ePos.paste(itr.cut());
			ePos.left().paste(leftTree);
			ePos.right().paste(rightTree);
		}
		
		BTree<E> lower;
		if (ePos.hasLeft() && !ePos.hasRight()) 			//e hase only left child
			lower = ePos.left().cut();							//break link below the element to remove
		else												//only right
			lower = ePos.right().cut();							//break link below the element to remove
		ePos.cut();
		ePos.paste(lower);
		
		
			
	}

	public boolean contains(E e) {
		BTreeItr<E> ti = locate(e);
		return !ti.isBottom();
	}

	public int size() {
		return crtSize;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public E minElt() {
		BTreeItr<E> itr = new BTreeItr<E>(this.tree);
		if (!itr.hasLeft() && !itr.hasRight())
			return itr.consult();
		while(!itr.isBottom())
			itr = itr.left();
		return itr.up().consult();
	}

	public E maxElt() {
		BTreeItr<E> itr = new BTreeItr<E>(this.tree);
		if (!itr.hasLeft() && !itr.hasRight())
			return itr.consult();
		while(!itr.isBottom())
			itr = itr.right();
		return itr.up().consult();
	}

	@Override
	public String toString() {
		return "" + tree;
	}

	public String toReadableString() {
		String s = tree.toReadableString();
		s += "size==" + crtSize + "\n";
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
