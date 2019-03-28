package s19;

import java.util.HashSet;
import java.util.Set;

public class ArticulationPoints {
    // =============================================================
    static class ArtData {
        int preOrderCounter = 0;
        int[] noAnc, noPre;
        boolean[] isArtPoint, isVisited;
        int nbOfRootSons = 0;
        int rootVid;

        ArtData(int nVertices, int root) {
            noPre = new int[nVertices];
            noAnc = new int[nVertices];
            isVisited = new boolean[nVertices];
            isArtPoint = new boolean[nVertices];
            rootVid = root;
        }
    }

    // =============================================================
    private static void findArt(UGraph g, int vid, int parentVid, ArtData art) {
        Set<Integer> children = g.neighboursOf(vid);

    }

    // POST : noAnc[vid] and isArtPoint[vid] are known
    public static Set<Integer> articulationPoints(UGraph g, int startVid) {
        Set<Integer> artPointSet = new HashSet<>();
        ArtData art = new ArtData(g.nbOfVertices(), startVid);
        art.isVisited[startVid] = true;
        art.noAnc[startVid] = art.noPre[startVid] = art.preOrderCounter++;
        for (int n : g.neighboursOf(startVid)) {
            if (art.isVisited[n]) {
                articulationPoints(g, n);
                if (art.noAnc[n] > art.noAnc[startVid]) art.isArtPoint[startVid] = true;
                if (art.noAnc[n] < art.noAnc[startVid])
                    art.noAnc[startVid] = art.noAnc[n];
            } else {
                if (art.noAnc[n] < art.noAnc[startVid])
                    art.noAnc[startVid] = art.noAnc[n];
            }
        }

        for (int i = 0; i < art.isArtPoint.length; i++)
            if (art.isArtPoint[i])
                artPointSet.add(i);


        return artPointSet;
    }

    // ------------------------------------------------------------
    public static Set<Integer> articulationPoints(UGraph g) {
        if (g.nbOfVertices() == 0) return new HashSet<>();
        return articulationPoints(g, 0);
    }

    // ------------------------------------------------------------
    public static void main(String[] args) {
        int j;
        final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, J = 9, K = 10, L = 11, M = 12;
        int nVertices = 13;
        int[] srcs = {A, A, A, A, C, D, D, E, E, G, G, G, H, J, J, J, L};
        int[] dsts = {F, C, B, G, G, E, F, F, G, L, J, H, I, K, L, M, M};
        UGraph g = new UGraph(nVertices, srcs, dsts);
        System.out.println(g + "\n");
        Set<Integer> art = articulationPoints(g, D);
        System.out.print("Points d'articulation: " + art);
        System.out.println();
        for (j = 0; j < nVertices; j++) {
            Set<Integer> res = articulationPoints(g, j);
            if (!res.equals(art))
                throw new RuntimeException("not the same result for different starting nodes");
        }
    }
}

