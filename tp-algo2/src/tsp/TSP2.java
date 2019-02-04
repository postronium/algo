package tsp;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//======================================================================
public class TSP2 implements TSP {
	
	/*
	 * Class to store the point with its visited state
	 * That way we can store the visited boolean in the 
	 * QuadTree and avoid us to search the visited state of
	 * a point in an array
	 * 
	 * This may not be the prettiest implementation
	 */
	private static class TSPPointWrapper {
		final double x;
		final double y;
		TSPPointWrapper next;
		TSPPointWrapper previous;
		int index;
		public TSPPointWrapper(TSPPoint pt, int index) {
			x = pt.x;
			y = pt.y;
			this.index = index;
		}
	}
	
	public void salesman(TSPPoint[] t, int[] path) {
		int i, j, pathPt;
		int n = t.length;
		int closestNb = 0;
		TSPPointWrapper closestPt, thisPt;
		double shortestDist;
		
		//fill the QuadTree
		LinkedList<TSPPointWrapper> pts = new LinkedList<TSPPointWrapper>();
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		for (i = 0; i < n; i++) {
			if (minX > t[i].x)
				minX = t[i].x;
			if (minY > t[i].y)
				minY = t[i].y;
			if (maxX < t[i].x)
				maxX = t[i].x;
			if (maxY < t[i].y)
				maxY = t[i].y;
			pts.add(new TSPPointWrapper(t[i], i));
		}

		//start at somepoint
		thisPt = pts.getLast();
		pathPt = 0;
		path[pathPt++] = thisPt.index;
		if (thisPt == null)
			return;
		
		QuadTree space = new QuadTree(pts, minX, minY, maxX, maxY);
		space.splitTree(space.root);
		
		//get first neightboorhoot sector
		QuadTree.QuadTreeSector neigboorhood = space.getLeafOfPoint(thisPt);	//get leaf with thisPt in it
		List<TSPPointWrapper> nbPts = neigboorhood.payload;							//get all points of leaf
		
		
	}

	static private double sqr(double a) {
		return a * a;
	}

	static private double distance(TSPPointWrapper p1, TSPPointWrapper p2) {
		return Math.sqrt(sqr(p1.x - p2.x) + sqr(p1.y - p2.y));
	}
	
	private static class QuadTree {
		
		//Axial aligned bounding box
		private class AABox {
			
			public static final int X1 = 0;
			public static final int Y1 = 1;
			public static final int X2 = 2;
			public static final int Y2 = 3;
			public static final int MX = 4;
			public static final int MY = 5;
			
			/* y
			 * /---\
			 * |2 3|
			 * |0 1|
			 * \---/x
			 */
			public void fromParent(AABox parent, int index) {
				double[] pBox = parent.b;
				switch (index) {
					case 0:
						b[X1] = pBox[X1];
						b[Y1] = pBox[Y1];
						b[X2] = pBox[MX];
						b[Y2] = pBox[MY];
						break;
					case 1:
						b[X1] = pBox[MX];
						b[Y1] = pBox[Y1];
						b[X2] = pBox[X2];
						b[Y2] = pBox[Y2];
						break;
					case 2:
						b[X1] = pBox[X1];
						b[Y1] = pBox[MY];
						b[X2] = pBox[MX];
						b[Y2] = pBox[Y2];
						break;
					case 3:
						b[X1] = pBox[MX];
						b[Y1] = pBox[MY];
						b[X2] = pBox[X2];
						b[Y2] = pBox[Y2];
						break;
				}
				
				//calc middle
				b[MX] = (b[X2]-b[X1])/2 + b[X1];
				b[MY] = (b[Y2]-b[Y1])/2 + b[Y1];
			}
			
			double[] b = new double[6];
		}
		
		public static final int N_ITEMS_SECTOR = 2500;
		
		QuadTreeSector root;
		
		private class QuadTreeSector {
			AABox box = new AABox();
			QuadTreeSector[] children = new QuadTreeSector[4];
			LinkedList<TSPPointWrapper> payload = new LinkedList<TSPPointWrapper>();
			QuadTreeSector parent;
		}
		
		public QuadTree(LinkedList<TSPPointWrapper> payload, double minX, double minY, double maxX, double maxY) {
			root = new QuadTreeSector();
			root.box.b[AABox.X1] = minX;
			root.box.b[AABox.X2] = maxX;
			root.box.b[AABox.Y1] = minY;
			root.box.b[AABox.Y2] = maxY;
			root.box.b[AABox.MX] = (maxX-minX)/2 + minX;
			root.box.b[AABox.MY] = (maxY-minY)/2 + minY;
			root.payload = payload;
		}
		
		public void splitTree(QuadTreeSector sector) {
			if (sector.payload.size() > N_ITEMS_SECTOR) {
				mkBabys(sector);
				for (int i = 0; i < 4; i++) {
					splitTree(sector.children[i]);
				}
			}
		}
		
		public void mkBabys(QuadTreeSector parent) {
			parent.children[0] = new QuadTreeSector();
			parent.children[1] = new QuadTreeSector();
			parent.children[2] = new QuadTreeSector();
			parent.children[3] = new QuadTreeSector();
			for (TSPPointWrapper pt : parent.payload) {
				int childIndex = pt.x < parent.box.b[AABox.MX] ? 0 : 1;
				childIndex += pt.y < parent.box.b[AABox.MY] ? 0 : 2;
				parent.children[childIndex].payload.add(pt);
				parent.children[childIndex].parent = parent;
			}
			for (int i = 0; i < 4; i++)
				parent.children[i].box.fromParent(parent.box, i);
		}
		
		public QuadTreeSector getLeafOfPoint(TSPPointWrapper pt) {
			return getLeafOfPoint(pt.x, pt.y, root);
		}
		
		public QuadTreeSector getLeafOfPoint(double x, double y) {
			return getLeafOfPoint(x, y, root);
		}
		
		private QuadTreeSector getLeafOfPoint(double x, double y, QuadTreeSector sector) {
			if (sector.children[0] != null) {
				int childIndex = x < sector.box.b[AABox.MX] ? 0 : 1;
				childIndex += y < sector.box.b[AABox.MY] ? 0 : 2;
				return getLeafOfPoint(x, y, sector.children[childIndex]);
			} else {
				return sector;
			}
		}
		
		public List<QuadTreeSector> getAllLeaves(QuadTreeSector node) {
			List<QuadTreeSector> children = new LinkedList<QuadTreeSector>();
			if (node.children[0] == null) {
				children.add(node);
			} else {
				children.addAll(getAllLeaves(node.children[0]));
				children.addAll(getAllLeaves(node.children[1]));
				children.addAll(getAllLeaves(node.children[2]));
				children.addAll(getAllLeaves(node.children[3]));
			}
			
			return children;
		}
		
	}
}
