package s16;

import java.util.*;

// ----------------------------------------------------------------------
public class GraphUtils {
    // ----------------------------------------------------------------------
    static class GraphTraversalElt implements Comparable<GraphTraversalElt> {
        public final int from, to;
        public final BTreeItr<Integer> whereToPaste;
        public final int weight;

        public GraphTraversalElt(int from, int to, int weight, BTreeItr<Integer> t) {
            this.to = to;
            this.from = from;
            whereToPaste = t;
            this.weight = weight;
        }

        public int compareTo(GraphTraversalElt o) {
            if (weight > o.weight) return +1;
            if (weight < o.weight) return -1;
            return 0;
        }
    }

    // ----------------------------------------------------------------------
    public static BTree<Integer> depthFirstForest(DiGraph g, int startVid) {
        BTreeItr<Integer> ti = new BTree<Integer>().root();
        int n = g.nbOfVertices();
        boolean[] isVisited = new boolean[n];
        ti.paste(depthFirst(g, startVid, isVisited));
        for (int i = 0; i < n; i++) {
            ti = ti.rightMost();
            ti.paste(depthFirst(g, i, isVisited));
        }
        return ti.whole();
    }

    // --------------------
    private static BTree<Integer> depthFirst(DiGraph g, int startVid, boolean[] isVisited) {
        BTreeItr<Integer> ti = (new BTree<Integer>()).root();
        if (isVisited[startVid])
            return ti.whole();
        ti.insert(startVid);
        isVisited[startVid] = true;
        ti = ti.left();
        for (int child : g.neighboursFrom(startVid)) {
            if (!isVisited[child]) {
                ti.paste(depthFirst(g, child, isVisited));
                ti = ti.right();
            }
        }
        return ti.whole();
    }

    // ----------------------------------------------------------------------
    public static BTree<Integer> breadthFirstForest(DiGraph g, int startVid) {
        BTreeItr<Integer> ti = new BTree<Integer>().root();
        int n = g.nbOfVertices();
        boolean[] isVisited = new boolean[n];
        ti.paste(breadthFirst(g, startVid, isVisited));
        for (int i = 0; i < n; i++) {
            ti = ti.rightMost();
            ti.paste(breadthFirst(g, i, isVisited));
        }
        return ti.whole();
    }

    // --------------------
    private static BTree<Integer> breadthFirst(DiGraph g, int startVid, boolean[] isVisited) {
        Queue<GraphTraversalElt> fifo = new LinkedList<GraphTraversalElt>();
        GraphTraversalElt gte;
        BTreeItr<Integer> root = new BTree<Integer>().root();
        int vid;
        BTreeItr<Integer> t;
        gte = new GraphTraversalElt(-1, startVid, 0, root);
        fifo.add(gte);
        while (!fifo.isEmpty()) {
            gte = fifo.remove();
            vid = gte.to;
            t = gte.whereToPaste;
            if (!t.isBottom()) t = t.left().rightMost();  //... else it is the root !
            if (isVisited[vid]) continue;
            isVisited[vid] = true;
            t.insert(new Integer(vid));
            for (int i : g.neighboursFrom(vid)) {
                gte = new GraphTraversalElt(vid, i, 0, t);
                fifo.add(gte);
            }
        }
        return root.whole();
    }

    // ----------------------------------------------------------------------
    public static BTree<Integer> bestFirstForest(WeightedDiGraph g, int startVid) {
        BTreeItr<Integer> ti = new BTree<Integer>().root();
        int n = g.nbOfVertices();
        boolean[] isVisited = new boolean[n];
        ti.paste(bestFirst(g, startVid, isVisited));
        for (int i = 0; i < n; i++) {
            ti = ti.rightMost();
            ti.paste(bestFirst(g, i, isVisited));
        }
        return ti.whole();
    }

    // --------------------
    private static BTree<Integer> bestFirst(WeightedDiGraph g, int startVid,
                                            boolean[] isVisited) {
        PriorityQueue<GraphTraversalElt> queue = new PriorityQueue<GraphTraversalElt>();
        GraphTraversalElt gte;
        BTreeItr<Integer> root = new BTree<Integer>().root();
        int vid;
        BTreeItr<Integer> t;
        gte = new GraphTraversalElt(-1, startVid, 0, root);
        queue.add(gte);
        while (!queue.isEmpty()) {
            gte = queue.remove();
            vid = gte.to;
            t = gte.whereToPaste;
            if (!t.isBottom()) t = t.left().rightMost();  //... else it is the root !
            if (isVisited[vid]) continue;
            isVisited[vid] = true;
            t.insert(new Integer(vid));
            for (int i : g.neighboursFrom(vid)) {
                gte = new GraphTraversalElt(vid, i, g.edgeWeight(vid, i), t);
                queue.add(gte);
            }
        }
        return root.whole();
    }

    // ----------------------------------------------------------------------
    public static boolean[][] transitiveClosure(DiGraph g) {
        int n = g.nbOfVertices();
        boolean[][] res = new boolean[n][n];
        int i, j, k;
        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++)
                res[i][j] = g.isEdge(i, j);
        for (k = 0; k < n; k++)
            for (i = 0; i < n; i++)
                for (j = 0; j < n; j++)
                    res[i][j] |= res[i][k] && res[k][j];
        return res;
    }

    // ----------------------------------------------------------------------
    public static List<Integer> pathBetween(DiGraph g, int fromVid, int toVid) {
        return null;
    }

    // ----------------------------------------------------------------------
    //returns null if not found
    public static BTreeItr<Integer> locate(BTreeItr<Integer> t, Object e) {
        if (t.isBottom()) return null;
        if (t.consult().equals(e)) return t.alias();
        BTreeItr<Integer> res = locate(t.left(), e);
        if (res != null) return res;
        return locate(t.right(), e);
    }

    // ----------------------------------------------------------------------
    // ============================================================
    // ============================================================
    private static void printPath(int u, int v, List<Integer> p) {
        System.out.print("\npath from " + u + " to " + v + " : ");
        if (p == null) System.out.print("none");
        else
            for (int w : p)
                System.out.print(" " + w);
    }

    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        int nbV = 6;
        //int nbE = 12;
        final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5;
        int[] srcs = {A, A, A, B, B, D, D, D, D, E, F, F};
        int[] dsts = {B, C, F, F, C, A, B, C, E, A, D, E};
        int[] costs = {12, 6, 14, 1, 7, 9, 3, 2, 4, 5, 10, 11};

        WeightedDiGraph dg = new WeightedDiGraph(nbV, srcs, dsts, costs);
        DiGraph g = new DiGraph(dg);
        System.out.println("Input Graph: " + dg);

        BTree<Integer> t = depthFirstForest(g, 0);
        System.out.println("Depth   First Forest: " + t.toReadableString());

        t = breadthFirstForest(g, 0);
        System.out.println("Breadth First Forest: " + t.toReadableString());

        t = bestFirstForest(dg, 0);
        System.out.println("Best First Forest: " + t.toReadableString());

        List<Integer> p;
        boolean[][] isReachable = transitiveClosure(g);
        for (int u = 0; u < nbV; u++)
            for (int v = 0; v < nbV; v++) {
                if (u == v) continue;
                p = g.pathBetween(u, v);
                printPath(u, v, p);
                ok((p != null) == (isReachable[u][v]));
                p = pathBetween(g, u, v);
                printPath(u, v, p);
            }
    }

    // --------------------
    static void ok(boolean b) {
        if (b) return;
        throw new RuntimeException("property not verified");
    }
}
