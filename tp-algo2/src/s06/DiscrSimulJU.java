package s06;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

public class DiscrSimulJU {
	
	private static int DEFAULT_STOP_TIME = 10000;
	private static int NB_TEST_CYCLES = 1000;
	private static int MAX_TELLERS = 20;
	private static int MAX_AVG_TRANS = 50;
	private static int MAX_AVG_ARRIVAL_INTERVAL = 50;
	
	//no teller means"
	//	0 served clients
	@Test
	public void testNoTeller() {
		Random r = new Random();
		for (int i = 0; i < NB_TEST_CYCLES; i++) {
			DiscrSimul sim = getRandomParamSimulation(r, 0);
			assertTrue(sim.getNbOfClientsServed() == 0);
		}
	}
	
	//FreeTellerTime can never be higher than DEFAULT_STOP_TIME*nbOfTellers
	@Test
	public void testFreeTellerTime() {
		Random r = new Random();
		for (int i = 0; i < NB_TEST_CYCLES; i++) {
			int nbOfTellers = r.nextInt(MAX_TELLERS);
			DiscrSimul sim = getRandomParamSimulation(r, nbOfTellers);
			assertTrue(sim.getFreeTellersTime() <= nbOfTellers*DEFAULT_STOP_TIME);
		}
	}
	
	//peak time can never exceed DEFAULT_STOP_TIME
	@Test
	public void testPeakTime() {
		Random r = new Random();
		for (int i = 0; i < NB_TEST_CYCLES; i++) {
			DiscrSimul sim = getRandomParamSimulation(r);
			assertTrue(
					"Peak time sould be smaller than " + DEFAULT_STOP_TIME + 
					" but is " + sim.getPeakTime(),
					sim.getPeakTime() <= DEFAULT_STOP_TIME);
		}
	}
	
	//total teller work time should always be smaller than
	//	peaktime*nbOfTellers + tellerFreeTime
	@Test
	public void testTotalTellerTime() {
		Random r = new Random();
		for (int i = 0; i < NB_TEST_CYCLES; i++) {
			int nbOfTellers = r.nextInt(MAX_TELLERS);
			int totalWorkTime = nbOfTellers*DEFAULT_STOP_TIME;
			DiscrSimul sim = getRandomParamSimulation(r, nbOfTellers);
			assertTrue(sim.getFreeTellersTime() + sim.getPeakTime()*nbOfTellers <= totalWorkTime);
		}
	}
	
	/*
	 * Helper functions
	 */
	public DiscrSimul getRandomParamSimulation(Random r, int nbTeller) {
		DiscrSimul sim = new DiscrSimul(nbTeller, 
				1+r.nextInt(MAX_AVG_TRANS-1), 1+r.nextInt(MAX_AVG_ARRIVAL_INTERVAL-1));
		sim.runSimulation(DEFAULT_STOP_TIME);
		return sim;
	}
	
	public DiscrSimul getRandomParamSimulation(Random r) {
		return getRandomParamSimulation(r, 1+r.nextInt(MAX_TELLERS-1));
	}
}
