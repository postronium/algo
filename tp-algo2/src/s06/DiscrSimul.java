package s06;

import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DiscrSimul {
  //============================================================
  private static class SimulEvent implements Comparable<SimulEvent> {
    final int     who;       // client identification number
    final int     date;      // when the event will occur
    final boolean isArrival; // "arrival" or "departure" event

    SimulEvent(int who, int date, boolean isArrival) {
      this.who       = who;
      this.date      = date;
      this.isArrival = isArrival;
    }
    @Override
    public int compareTo(SimulEvent o) {
      if (date < o.date) return -1;
      if (date > o.date) return +1;
      return 0;
    }
  }
  //============================================================
  //-------------------------------------- Simulation basics
  private int crtTime;
  private PriorityQueue<SimulEvent> events;
  private final Random rnd = new Random();
  //-------------------------------------- The world state at one instant
  private Queue<Integer> clientQueue;
  private int lastClient; 
  private int freeTellers;
  //-------------------------------------- The world parameters
  private final int avgTransactionDuration;
  private final int avgArrivalInterval;
  private final int nbOfTellers;
  //-------------------------------------- Statistics stuff
  private int peakTime;
  private int freeTellersTime;
  private int nbOfClientsServed;
  //------------------------------------------------------------
  public DiscrSimul(int nbOfTellers, int avgTrans, 
                    int avgArrivalInterval) {
    this.nbOfTellers = nbOfTellers;
    this.avgTransactionDuration = avgTrans;
    this.avgArrivalInterval = avgArrivalInterval;
  }
  //------------------------------------------------------------
  public void runSimulation(int stoppingTime) {
    // TODO - A COMPLETER

    // initializations
    freeTellers = nbOfTellers;
    clientQueue = new LinkedList<>();
    events = new PriorityQueue<>();
    // ... 
    
    // post the first arrival
    //...
    
    // main loop
    //...
  }
  //------------------------------------------------------------
  public void printStatistics() {
    System.out.println("Total duration of free tellers = " + freeTellersTime);
    System.out.println("               Total peak time = " + peakTime);
    System.out.println("          Nb of served clients = " + nbOfClientsServed); 
    int benefit=(nbOfClientsServed-freeTellersTime/avgTransactionDuration);
    System.out.println("    Benefit (silly) estimation = "+ benefit);
    System.out.println();
  }
  //------------------------------------------------------------
  // Private methods
  //------------------------------------------------------------
  private void handleEvent(SimulEvent e) {
    // TODO - A COMPLETER
  }
  //------------------------------------------------------------
  private void handleArrival(SimulEvent e) {
    // TODO - A COMPLETER
  }
  //------------------------------------------------------------
  private void handleDeparture(SimulEvent e) {
    // TODO - A COMPLETER
  }
  //------------------------------------------------------------
  private void handleStartTransaction(int clientId) {
    // TODO - A COMPLETER
  }
  //------------------------------------------------------------
  private int rndTransactionDuration() {
    return (int)(Math.round(nextNegExp(rnd, avgTransactionDuration)));
  }
  //------------------------------------------------------------
  private int rndArrivalInterval() {
    return (int)(Math.round(nextNegExp(rnd, avgArrivalInterval)));
  }
  //------------------------------------------------------------
  // exponential law
  private static double nextNegExp(Random r, double expectedValue) {
    return expectedValue * (-Math.log(1.0 - r.nextDouble()));  
  }

  //------------------------------------------------------------
  //------------------------------------------------------------
  //------------------------------------------------------------
  public static void main(String [] args) {
    int a=5, b=70, c=20, d=100000; // tellers,transTime,arrivalDelay,stopTime
    if (args.length == 4) {
      a = Integer.parseInt(args[0]);
      b = Integer.parseInt(args[1]);
      c = Integer.parseInt(args[2]);
      d = Integer.parseInt(args[3]);
    }
    System.out.println("===== Simulations parameters: "
        +"tellers("+a+") transTime("+b+") arrivalDelay("+c+") stopTime("+d+")");
    DiscrSimul o = new DiscrSimul(a, b, c);
    o.runSimulation(d);
    o.printStatistics();
    o.runSimulation(d);
    o.printStatistics();

    // Varying the number of tellers...
    for(a=1; a<10; a++) {
      System.out.println("===== Simulations parameters: "
          +"tellers("+a+") transTime("+b+") arrivalDelay("+c+") stopTime("+d+")");
      o = new DiscrSimul(a, b, c);
      o.runSimulation(d);
      o.printStatistics();
    }
  }
}
