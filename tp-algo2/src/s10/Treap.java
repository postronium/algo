package s10;

import java.util.Random;

//--------------------------------------------------
public class Treap<E extends Comparable<E>> {
	
	public static int nAdd = 0;
	public static int nRm = 0;
	public static int nAddLeftRotation = 0;
	public static int nRmLeftRotation = 0;
	public static int nAddRightRotation = 0;
	public static int nRmRightRotation = 0;
	
	
	// ============================================================
	static class TreapElt<E extends Comparable<E>> implements Comparable<TreapElt<E>> {
		static Random rnd = new Random();
		// -----------------------
		private final E elt;
		private int pty;

		// -----------------------
		public TreapElt(E e) {
			elt = e;
			pty = rnd.nextInt();
		}

		public int pty() {
			return pty;
		}

		public E elt() {
			return elt;
		}

		public int compareTo(TreapElt<E> o) {
			return elt.compareTo(o.elt);
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (this.getClass() != o.getClass())
				return false;
			if (elt == null)
				return false;
			return elt.equals(((TreapElt<?>) o).elt);
		}

		@Override
		public String toString() {
			return "" + elt + "#" + pty;
		}

		@Override
		public int hashCode() {
			return elt.hashCode();
		}
	}

	// ============================================================
	private final BST<TreapElt<E>> bst;
	int rm = 0;

	// --------------------------
	public Treap() {
		bst = new BST<TreapElt<E>>();
	}

	public void add(E e) {
		BTreeItr.nRotateLeft = 0;
		BTreeItr.nRotateRight = 0;
		
		TreapElt<E> newE = new TreapElt<E>(e);
		BTreeItr<TreapElt<E>> itr = bst.tree.root();
		while (!itr.isBottom()) { // Go down like in BSTrees
			int cmp = newE.compareTo(itr.consult());
			if (cmp > 0)
				itr = itr.right();
			else if (cmp == 0)
				return; /// Already in tree
			else
				itr = itr.left();
		}

		itr.insert(newE); // Insert
		bst.crtSize++;

		// push it up to the right position
		percolateUp(itr);
		
		nAddLeftRotation += BTreeItr.nRotateLeft;
		nAddRightRotation += BTreeItr.nRotateRight;
		nAdd++;
	}

	public void remove(E e) {
		BTreeItr.nRotateLeft = 0;
		BTreeItr.nRotateRight = 0;
		
		BTreeItr<TreapElt<E>> ti = locate(e);
		if (ti.isBottom())
			return ;
		
		ti.consult().pty = Integer.MAX_VALUE;
		siftDownAndCut(ti);
		
		nRmLeftRotation += BTreeItr.nRotateLeft;
		nRmRightRotation += BTreeItr.nRotateRight;
		nRm++;
	}

	private BTreeItr<TreapElt<E>> locate(E e) {
		BTreeItr<TreapElt<E>> ti = bst.tree.root();
		while (!ti.isBottom()) {
			E c = ti.consult().elt();
			if (e.compareTo(c) == 0)
				break;
			if (e.compareTo(c) < 0)
				ti = ti.left();
			else
				ti = ti.right();
		}
		return ti;
	}

	public boolean contains(E e) {
		return bst.contains(new TreapElt<E>(e));
	}

	public int size() {
		return bst.crtSize;
	}

	public E minElt() {
		return bst.minElt().elt();
	}

	public E maxElt() {
		return bst.maxElt().elt();
	}

	public String toString() {
		return bst.toString();
	}

	// --------------------------------------------------
	// --- Non-public methods
	// --------------------------------------------------
	private void siftDownAndCut(BTreeItr<TreapElt<E>> ti) {
		while (!ti.isBottom()) {
			if (ti.hasLeft() && ti.hasRight()) {
				if (isLess(ti.right(), ti.left())) {
					ti.rotateRight();
					ti = ti.right();
				} else {
					ti.rotateLeft();
					ti = ti.left();
				}
			} else if (ti.hasLeft()) {
				ti.rotateRight();
				ti = ti.right();
			} else if (ti.hasRight()) {
				ti.rotateLeft();
				ti = ti.left();
			} else {
				ti.cut();
				bst.crtSize--;
				return;
			}
		}
		System.out.println("Strange behaviour");
		assert (false);
	}

	private void percolateUp(BTreeItr<TreapElt<E>> ti) {
		while ((!ti.isRoot()) && isLess(ti, ti.up())) {
			if (ti.isLeftArc()) {
				ti = ti.up();
				ti.rotateRight();
			} else {
				ti = ti.up();
				ti.rotateLeft();
			}
		}
	}

	private boolean isLess(BTreeItr<TreapElt<E>> a, BTreeItr<TreapElt<E>> b) {
		TreapElt<E> ca = a.consult();
		TreapElt<E> cb = b.consult();
		return ca.pty() < cb.pty();
	}
	
	public static void printStatistics() {
		System.out.println("rotateLeft for an add " + (float)nAddLeftRotation/nAdd);
		System.out.println("rotateRight for an add " + (float)nAddRightRotation/nAdd);
		
		System.out.println("rotateLeft for a rm " + (float)nRmLeftRotation/nRm);
		System.out.println("rotateRight for a rm " + (float)nRmRightRotation/nRm);
		
	}
}
