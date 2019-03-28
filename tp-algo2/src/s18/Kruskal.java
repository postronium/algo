package s18;

import java.util.PriorityQueue;
import java.util.Queue;

public class Kruskal {

    private static class PriorityEdge implements Comparable<PriorityEdge> {

        WeightedUGraph.Edge e;

        public PriorityEdge(WeightedUGraph.Edge e) {
            this.e = e;
        }

        @Override
        public int compareTo(PriorityEdge edge) {
            return e.weight - edge.e.weight;
        }
    }

    /* RETURNS: the total weight of the Minimum Spanning Tree
     * PRE:     res has the same vertices as g, but no edge
     * POST:    res is the resulting sub graph
     */
    public static int kruskal(WeightedUGraph g, WeightedUGraph res) {
        // Hint: find the appropriate PriorityQueue constructor, and
        //       use a lambda expression (together with Integer.compare()).
        Queue<PriorityEdge> edgesByWeight = new PriorityQueue<>();
        for (WeightedUGraph.Edge e : g.allEdges()) {
            //edgesByWeight.add((WeightedUGraph.Edge edge) -> e.weight - edge.weight);
            edgesByWeight.add(new PriorityEdge(e));
        }

        DisjSets vertexSet = new DisjSets(g.nbOfVertices());

        int totalWeight = 0;
        while (!edgesByWeight.isEmpty()) {
            WeightedUGraph.Edge e = edgesByWeight.remove().e;
            if (!vertexSet.isInSame(e.from, e.to)) {
                vertexSet.union(e.from, e.to);
                res.putEdge(e.from, e.to, e.weight);
                totalWeight += e.weight;
            }
        }

        return totalWeight;
    }

    // ------------------------------------------------------------
    public static void main(String[] args) {
        int nVertices = 6; // int nEdges = 12;
        final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5;
        int[] srcs = {A, A, A, B, B, D, D, D, D, E, F, F};
        int[] dsts = {B, C, F, F, C, A, B, C, E, A, D, E};
        int[] weights = {12, 6, 14, 1, 7, 9, 3, 2, 4, 11, 10, 5};

        WeightedUGraph g = new WeightedUGraph(nVertices, srcs, dsts, weights);
        System.out.println("Input Graph: " + g);

        WeightedUGraph res = new WeightedUGraph(g.nbOfVertices());
        int cost = kruskal(g, res);
        System.out.println("\nMinimal Spanning Tree of cost: " + cost);
        for (WeightedUGraph.Edge e : g.allEdges())
            System.out.println(e.from + " to " + e.to + " of cost " + e.weight);
    }
}
