package s17;

import java.util.*;

// ------------------------------------------------------------
public class Dijkstra {
    public static final HashSet<Integer> RES = new HashSet<>();

    // ------------------------------------------------------------
    static class Vertex implements Comparable<Vertex> {
        public final int vid;
        public final int pty;

        public Vertex(int vid, int pty) {
            this.vid = vid;
            this.pty = pty;
        }

        @Override
        public int compareTo(Vertex v) {
            return Integer.compare(this.pty, v.pty);
        }
    }

    // ------------------------------------------------------------
    // POST : minDist[i] is the min distance from a to i
    //                      MAX_VALUE if i is not reachable, 0 for a
    //         parent[i] is the parent of i in the corresponding tree
    //                      -1 if i is not reachable, and for a
    public static void dijkstra(WeightedDiGraph g, int a,
                                int[] minDist, int[] parent) {

        for (int i = 0; i < g.nbOfVertices(); i++) {
            minDist[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }

        System.out.println(g.toString());

        boolean[] visited = new boolean[g.nbOfVertices()];  //all to false by default
        minDist[a] = 0;
        parent[a] = -1;
        PtyQueue<Integer, Integer> nextVisit = new PtyQueue<Integer, Integer>();
        nextVisit.enqueue(a, minDist[a]);
        while (!nextVisit.isEmpty()) {
            int actualNode = nextVisit.dequeue();
            if (visited[actualNode]) continue;
            visited[actualNode] = true;

            for (int n : g.neighboursFrom(actualNode)) {
                if (minDist[n] > minDist[actualNode] + g.edgeWeight(actualNode, n)) {
                    minDist[n] = minDist[actualNode] + g.edgeWeight(actualNode, n);
                    parent[n] = actualNode;
                }
                nextVisit.enqueue(n, minDist[n]);
            }
        }

        return; // TODO - A COMPLETER...
    }
    // ------------------------------------------------------------

    /**
     * returns all vertices in vid's strongly connected component
     */
    static Set<Integer> strongComponentOf(WeightedDiGraph g, int vid) {
        Set<Integer> res = RES;
        // TODO - A COMPLETER...
        return res;
    }

    // ------------------------------------------------------------
    public static void main(String[] args) {
        int nVertices = 6; // int nEdges = 12;
        final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5;
        int[] srcs = {A, A, A, B, B, D, D, D, D, E, F, F};
        int[] dsts = {B, C, F, F, C, A, B, C, E, A, D, E};
        int[] costs = {12, 6, 14, 1, 7, 9, 3, 2, 4, 5, 10, 11};

        WeightedDiGraph g = new WeightedDiGraph(nVertices, srcs, dsts, costs);
        System.out.println("Input Graph: " + g);

        int n = g.nbOfVertices();
        int[] minCost = new int[n];
        int[] parent = new int[n];
        for (int a = 0; a < n; a++) {
            dijkstra(g, a, minCost, parent);
            System.out.println("\nMinimal distances from " + a);
            for (int i = 0; i < minCost.length; i++) {
                String s = "to " + i + ":";
                if (minCost[i] == Integer.MAX_VALUE) s += " unreachable";
                else s += " total " + minCost[i] + ", parent " + parent[i];
                System.out.println(s);
            }
        }
    }
}
