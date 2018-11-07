package s05;

import java.util.ArrayList;
import java.util.List;

public class GeneralSearchTreeMap<K extends Comparable<K>, V> {
	final int maxNodeSize; // should be odd
	private GSTNode root; // root node can be empty
	// ------------------------------------------------------------

	public GeneralSearchTreeMap(int maxNbOfChildren) {
		if (maxNbOfChildren % 2 == 0)
			maxNbOfChildren++;
		maxNodeSize = maxNbOfChildren;
		root = new GSTNode();
	}

	// ------------------------------------------------------------
	// returns null if key is not present
	public V get(K key) {
		return this.findVal(root, key);
	}
	
	public V findVal(GSTNode node, K key) {
		if (node.isLeaf()) {
			V value = null;
			for (int i = 0; i < node.nbOfKeys(); i++)
				if (node.getKey(i).compareTo(key) == 0)
					value = node.getVal(i);
			return value;
		}
		
		for (int i = 0; i < node.nbOfKeys(); i++) {
			if (key.compareTo(node.getKey(i)) < 0)
				return this.findVal(node.getSon(i), key);
		}
		return this.findVal(node.getSon(node.nbOfKeys()), key);
	}

	// ------------------------------------------------------------
	// count elements e in [min..max[ : min <= e < max
	public int nbOfKeysInRange(K min, K max) {
		return nbOfKeysInRange(root, min, max);
	}

	// ------------------------------------------------------------
	private int nbOfKeysInRange(GSTNode n, K min, K max) {
		if (n.isLeaf()) {
			int sumOfKeys = 0;
			for (int i = 0; i < n.nbOfKeys(); i++)
				if (n.getKey(i).compareTo(min) >= 0 && n.getKey(i).compareTo(max) < 0)
					sumOfKeys++;
			return sumOfKeys;
		}
		
		int sumOfKeys = 0;
		boolean[] isSmaller = new boolean[n.nbOfSons()];			//init to false
		for (int i = 0; i < n.nbOfKeys(); i++)
			isSmaller[i] = min.compareTo(n.getKey(i)) < 0;
			
		isSmaller[n.nbOfKeys()] = true;
		
		for (int i = n.nbOfKeys()-1; i > -1; i--)
			if (max.compareTo(n.getKey(i)) > 0 && isSmaller[i+1])
				sumOfKeys += this.nbOfKeysInRange(n.getSon(i+1), min, max);
				
		if (isSmaller[0])
			sumOfKeys += this.nbOfKeysInRange(n.getSon(0), min, max);
		
		return sumOfKeys;
	}

	// ------------------------------------------------------------
	public void put(K key, V val) {
		root.putFromRoot_DONT_USE(key, val);
	}

	public void remove(K key) {
		root.removeFromRoot_DONT_USE(key);
	}

	public boolean isEmpty() {
		return root.isEmpty();
	}

	public int size() {
		return root.size();
	}

	// ------------------------------------------------------------
	@Override
	public String toString() {
		return "" + root;
	}

	// ------------------------------------------------------------
	// ------------------------------------------------------------
	static java.util.Random rnd = new java.util.Random();

	public static void main(String[] args) {
		int n = 30;
		GeneralSearchTreeMap<Integer, Double> m;
		m = new GeneralSearchTreeMap<Integer, Double>(3);
		for (int i = 0; i < n; i++) {
			int e = rnd.nextInt(n);
			m.put(e, new Double(e));
		}
		System.out.println(m);
	}

	// =====================================================================
	// ==== GSTNode ===
	// =====================================================================
	class GSTNode {
		// ------------------------------------------------------------
		private boolean isLeaf_DONT_USE;
		private List<K> keys_DONT_USE; // only for leaves
		private List<V> vals_DONT_USE; // only for leaves
		private List<GSTNode> sons_DONT_USE; // only for non-leaves
		// ------------------------------------------------------------

		public GSTNode() {
			isLeaf_DONT_USE = true;
			keys_DONT_USE = new ArrayList<K>();
			vals_DONT_USE = new ArrayList<V>();
			sons_DONT_USE = new ArrayList<GSTNode>();
		}

		// ------------------------------------------------------------
		public GSTNode(GSTNode n, int from, int to) {
			this();
			isLeaf_DONT_USE = n.isLeaf_DONT_USE;
			if (isLeaf_DONT_USE) {
				for (int i = from; i < to; i++) {
					keys_DONT_USE.add(n.getKey(i));
					vals_DONT_USE.add(n.getVal(i));
				}
			} else {
				for (int i = from; i < to; i++) // one more son than key !
					sons_DONT_USE.add(n.getSon(i));
			}
		}

		// ------------------------------------------------------------
		public GSTNode(K key, V val) {
			this();
			isLeaf_DONT_USE = true;
			keys_DONT_USE.add(key);
			vals_DONT_USE.add(val);
		}

		// ------------------------------------------------------------
		public boolean isLeaf() {
			return isLeaf_DONT_USE;
		}

		public V getVal(int i) {
			return vals_DONT_USE.get(i);
		}

		public GSTNode getSon(int i) {
			return sons_DONT_USE.get(i);
		}

		public int nbOfSons() {
			return isLeaf_DONT_USE ? 0 : nbOfKeys() + 1;
		}

		// ------------------------------------------------------------
		public int nbOfKeys() {
			return isLeaf_DONT_USE ? keys_DONT_USE.size() : sons_DONT_USE.size() - 1;
		}

		// ------------------------------------------------------------
		public K getKey(int i) {
			if (isLeaf_DONT_USE)
				return keys_DONT_USE.get(i);
			return getSon(i + 1).minKey_DONT_USE();
		}

		// ------------------------------------------------------------
		int size() {
			if (isLeaf())
				return keys_DONT_USE.size();
			int res = 0;
			for (GSTNode s : sons_DONT_USE)
				res += s.size();
			return res;
		}

		// ------------------------------------------------------------
		boolean isEmpty() {
			return size() == 0;
		}

		// ------------------------------------------------------------
		private int indexOfFGK_DONT_USE(K key) {
			int i;
			for (i = 0; i < nbOfKeys(); i++) {
				if (getKey(i).compareTo(key) > 0)
					break;
			}
			return i;
		}

		// ------------------------------------------------------------
		private K minKey_DONT_USE() {
			if (isEmpty())
				return null;
			if (isLeaf_DONT_USE)
				return keys_DONT_USE.get(0);
			return sons_DONT_USE.get(0).minKey_DONT_USE();
		}

		// ------------------------------------------------------------
		private K maxKey_DONT_USE() {
			if (isEmpty())
				return null;
			if (isLeaf_DONT_USE)
				return keys_DONT_USE.get(keys_DONT_USE.size() - 1);
			return sons_DONT_USE.get(sons_DONT_USE.size() - 1).maxKey_DONT_USE();
		}

		// ------------------------------------------------------------
		private int nbOfKeysOrSons_DONT_USE() {
			return isLeaf_DONT_USE ? nbOfKeys() : nbOfSons();
		}

		private boolean isFull_DONT_USE() {
			return nbOfKeysOrSons_DONT_USE() > maxNodeSize;
		}

		private boolean isLight_DONT_USE() {
			return nbOfKeysOrSons_DONT_USE() <= maxNodeSize / 2;
		}

		private boolean canOfferOne_DONT_USE(int i) {
			if (i < 0 || i >= nbOfSons())
				return false;
			return sons_DONT_USE.get(i).nbOfKeysOrSons_DONT_USE() > maxNodeSize / 2 + 1;
		}

		// ------------------------------------------------------------
		private void putInLeaf_DONT_USE(K key, V val) {
			assert isLeaf_DONT_USE;
			if (keys_DONT_USE.contains(key))
				return;
			int i = 0;
			for (i = 0; i < keys_DONT_USE.size(); i++) {
				int cmp = getKey(i).compareTo(key);
				if (cmp == 0) {
					vals_DONT_USE.set(i, val);
					return;
				}
				if (cmp > 0)
					break;
			}
			keys_DONT_USE.add(i, key);
			vals_DONT_USE.add(i, val);
		}

		// ------------------------------------------------------------
		private void mergeWithSibling_DONT_USE(int i) {
			if (i != 0)
				i--;
			GSTNode a = getSon(i);
			GSTNode b = getSon(i + 1);
			assert (a.isLeaf() == b.isLeaf());
			if (a.isLeaf()) {
				a.keys_DONT_USE.addAll(b.keys_DONT_USE);
				a.vals_DONT_USE.addAll(b.vals_DONT_USE);
			} else
				a.sons_DONT_USE.addAll(b.sons_DONT_USE);
			sons_DONT_USE.remove(i + 1);
		}

		// ------------------------------------------------------------
		private void borrowFromLeft_DONT_USE(GSTNode crt, GSTNode left) {
			if (crt.isLeaf()) {
				int last = left.keys_DONT_USE.size() - 1;
				K k = left.keys_DONT_USE.remove(last);
				V v = left.vals_DONT_USE.remove(last);
				crt.keys_DONT_USE.add(0, k);
				crt.vals_DONT_USE.add(0, v);
			} else {
				GSTNode e = left.sons_DONT_USE.remove(left.sons_DONT_USE.size() - 1);
				crt.sons_DONT_USE.add(0, e);
			}
		}

		// ------------------------------------------------------------
		private void borrowFromRight_DONT_USE(GSTNode crt, GSTNode right) {
			if (crt.isLeaf()) {
				K k = right.keys_DONT_USE.remove(0);
				V v = right.vals_DONT_USE.remove(0);
				crt.keys_DONT_USE.add(k);
				crt.vals_DONT_USE.add(v);
			} else {
				GSTNode e = right.sons_DONT_USE.remove(0);
				crt.sons_DONT_USE.add(e);
			}
		}

		// ------------------------------------------------------------
		private boolean isBorrowing_DONT_USE(int i) {
			if (canOfferOne_DONT_USE(i - 1)) {
				borrowFromLeft_DONT_USE(sons_DONT_USE.get(i), sons_DONT_USE.get(i - 1));
				return true;
			}
			if (canOfferOne_DONT_USE(i + 1)) {
				borrowFromRight_DONT_USE(sons_DONT_USE.get(i), sons_DONT_USE.get(i + 1));
				return true;
			}
			return false;
		}

		// ------------------------------------------------------------
		private void removeFromLeaf_DONT_USE(K key) {
			int i = keys_DONT_USE.indexOf(key);
			if (i == -1)
				return;
			keys_DONT_USE.remove(i);
			vals_DONT_USE.remove(i);
		}

		// ------------------------------------------------------------
		void removeFromRoot_DONT_USE(K key) {
			remove_DONT_USE(key);
			if (isLeaf())
				return;
			if (nbOfSons() > 1)
				return;
			GSTNode us = sons_DONT_USE.remove(0); // skip unique son : height is decreased
			if (us.isLeaf()) {
				isLeaf_DONT_USE = true;
				keys_DONT_USE.addAll(us.keys_DONT_USE);
				vals_DONT_USE.addAll(us.vals_DONT_USE);
			} else {
				sons_DONT_USE.addAll(us.sons_DONT_USE);
			}
		}

		// ------------------------------------------------------------
		private void remove_DONT_USE(K key) {
			if (isEmpty())
				return;
			if (isLeaf()) {
				removeFromLeaf_DONT_USE(key);
				return;
			}
			int i = indexOfFGK_DONT_USE(key);
			GSTNode s = sons_DONT_USE.get(i);
			s.remove_DONT_USE(key);
			if (!s.isLight_DONT_USE())
				return;
			if (isBorrowing_DONT_USE(i))
				return;
			mergeWithSibling_DONT_USE(i);
		}

		// ------------------------------------------------------------
		void putFromRoot_DONT_USE(K key, V val) {
			put_DONT_USE(key, val);
			if (isFull_DONT_USE())
				split_DONT_USE(); // height increased !
		}

		// ------------------------------------------------------------
		private void put_DONT_USE(K key, V val) {
			if (isLeaf_DONT_USE) {
				putInLeaf_DONT_USE(key, val);
				return;
			}
			assert minKey_DONT_USE() != null && maxKey_DONT_USE() != null;
			// if (key.compareTo(minKey())<0) sons.add(0,new GSTNode<K,V>(key,val));
			// if (key.compareTo(maxKey())>0) sons.add( new GSTNode<K,V>(key,val));
			int i = indexOfFGK_DONT_USE(key);
			GSTNode s = sons_DONT_USE.get(i);
			s.put_DONT_USE(key, val);
			if (s.isFull_DONT_USE()) {
				s.split_DONT_USE();
				sons_DONT_USE.remove(i);
				sons_DONT_USE.add(i, s.getSon(0));
				sons_DONT_USE.add(i + 1, s.getSon(1));
			}
		}

		// ------------------------------------------------------------
		// GSTNode<K,V> upperHalf() { return null; }
		// ------------------------------------------------------------
		private void split_DONT_USE() {
			int n = nbOfKeys();
			if (!isLeaf_DONT_USE)
				n++; // one more son...
			int mid = n / 2;
			GSTNode a = new GSTNode(this, 0, mid);
			GSTNode b = new GSTNode(this, mid, n);
			keys_DONT_USE.clear();
			vals_DONT_USE.clear();
			isLeaf_DONT_USE = false;
			sons_DONT_USE.clear();
			sons_DONT_USE.add(a);
			sons_DONT_USE.add(b);
		}

		// ------------------------------------------------------------
		@Override
		public String toString() {
			return toString_DONT_USE("");
		}

		private String toString_DONT_USE(String prefix) {
			String s = "";
			if (isLeaf_DONT_USE) {
				s += prefix + "LEAF: ";
				for (K c : keys_DONT_USE)
					s += " " + c;
				s += "\n";
			} else {
				GSTNode f = sons_DONT_USE.get(0);
				for (GSTNode g : sons_DONT_USE)
					s += prefix + (g == f ? "." : "." + g.minKey_DONT_USE()) + "\n"
							+ g.toString_DONT_USE(prefix + "  ");
				s += prefix + ".\n";
			}
			return s;
		}
		// ------------------------------------------------------------
//    private boolean hasEmptyNodes_DONT_USE() {
//      if (isEmpty()) return true;
//      if (isLeaf_DONT_USE) return false;
//      boolean has=false;
//      for(GSTNode s:sons_DONT_USE) has |= s.hasEmptyNodes_DONT_USE();
//      return has;
//    }
		// ------------------------------------------------------------
	}
}
