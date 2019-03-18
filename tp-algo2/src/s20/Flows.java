package s20;
import java.util.*;

public class Flows {
  //==================================================
  public static class MaxFlowResult {
    public WeightedDiGraph flow;
    public int       throughput;
    public boolean[] isInSourceGroup; // gives the Min-Cut solution
  }
  //==================================================
  public static MaxFlowResult maxFlow(WeightedDiGraph capacity, 
                                      int source,  int sink) {
    WeightedDiGraph resid = initialResid(capacity);
    WeightedDiGraph flow  = initialFlow (capacity);
    
    while(true) {
      break; // TODO - A COMPLETER...
    }
    suppressEmptyEdges(flow);
    // TODO - A COMPLETER...
    return null;
  }

  public static void suppressEmptyEdges(WeightedDiGraph g) {
    for(WeightedDiGraph.Edge e:g.allEdges())
      if (e.weight == 0) 
        g.removeEdge(e.from, e.to);
  }

  public static int minStep(WeightedDiGraph g, List<Integer> path ) {
    return 0; // TODO - COMPLETER...
  }

  public static WeightedDiGraph initialResid(WeightedDiGraph cap) {
    return null; // TODO - COMPLETER...
  }

  public static WeightedDiGraph initialFlow(WeightedDiGraph cap) {
    return null; // TODO - COMPLETER...
  }

  public static void updateFlow(WeightedDiGraph flow,
                                WeightedDiGraph resid,
                                List<Integer>   path,
                                int             benefit) {
    // TODO - COMPLETER...
  }

  private static void addCost(WeightedDiGraph g, int from, int to, int delta) {
    // TODO - COMPLETER...
  }
  // ------------------------------------------------------------
  // ------------------------------------------------------------
  // ------------------------------------------------------------
  public static void main(String [] args) {
    int nVertices = 6;  //int nEdges = 12;
    final int A=0, B=1, C=2, D=3, E=4, F=5;
    int    [] srcs  = {A, A, A, B, B, D, D, D, D, E, F, F };
    int    [] dsts  = {B, C, F, F, C, A, B, C, E, A, D, E };
    int    [] costs = {12,6, 14,1, 7, 9, 3, 2, 4, 5, 10,11};
    
    WeightedDiGraph g = new WeightedDiGraph(nVertices, srcs, dsts, costs);
    System.out.println("                Input Graph: " + g);
    
    int source=0, sink=2;
    System.out.println("              Source vertex: "+ source);
    System.out.println("                Sink vertex: "+ sink);
    MaxFlowResult result = maxFlow(g, source, sink);
    System.out.println("               Maximal flow: "+ result.flow);
    System.out.println("           Total throughput: "+ result.throughput);
    System.out.println("Source-group in the min-cut: "+ groupFromArray(result.isInSourceGroup));    
  }
  
  static List<Integer> groupFromArray(boolean[] t) {
    List<Integer> res=new LinkedList<>();
    for(int i=0; i<t.length; i++)
      if (t[i]) res.add(i);
    return res;
  }

}
