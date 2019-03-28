package s18;

import java.util.List;
import java.util.ArrayList;

public class BipartiteAnalysis {
    private final UGraph graph;
    private boolean isBipartite;
    private final boolean[] isVisited;
    private final boolean[] isWhite;  // meaningful if isBipartite
    private final int[] parent;   // the traversal tree structure
    private List<Integer> oddCycle; // meaningful if !isBipartite

    public BipartiteAnalysis(UGraph g) {
        int n = g.nbOfVertices();
        graph = g;
        isVisited = new boolean[n];
        isWhite = new boolean[n];
        parent = new int[n];

        isBipartite = true;
        for (int v = 0; v < g.nbOfVertices() && isBipartite; v++) {
            dft(v);
        }
    }

    // Depth-first traversal
    // PRE: vid is not visited, but has been assigned a color
    // POST: either the whole subtree has been colored,
    //           or an odd cycle has been found via a back edge
    //              (meaning the graph is not bipartite).
    // Caution: exit the traversal once you find an "odd-cycle";
    //          a "back edge" is later met as a "forward" one...
    private void dft(int vid) {
        if (isVisited[vid]) return;
        recDft(vid, false);
    }


    private void recDft(int vid, boolean white) {
        isVisited[vid] = true;
        isWhite[vid] = white;
        for (int n : graph.neighboursOf(vid)) {
            if (isVisited[n]) {
                if (white == isWhite[n]) {
                    rememberOddCycle(n, vid);
                    isBipartite = false;
                    break;
                }
                continue;
            }
            parent[n] = vid;
            recDft(n, !white);
            if (!isBipartite) break;
        }
    }

    // build the oddCycle list with the tree path: <descendantId, ..., ancestorId>
    private void rememberOddCycle(int ancestorId, int descendantId) {
        oddCycle = getSubCycle(ancestorId, descendantId);
    }

    private ArrayList<Integer> getSubCycle(int ancestorId, int descendantId) {
        ArrayList oddCycle = new ArrayList<Integer>();
        oddCycle.add(descendantId);
        if (ancestorId == descendantId || parent[descendantId] == -1)
            return oddCycle;

        oddCycle.addAll(getSubCycle(ancestorId, parent[descendantId]));

        return oddCycle;
    }

    public boolean isBipartite() {
        return isBipartite;
    }

    public int colorOf(int vid) {
        assert isBipartite();
        return isWhite[vid] ? 0 : 1;
    }

    public List<Integer> anOddCycle() {
        assert !isBipartite();
        return new ArrayList<Integer>(oddCycle);
    }
}
