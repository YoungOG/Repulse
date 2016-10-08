package code.young.repulse.utils;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class EbolaCalc {

	public static double[] getEstimations(double rankingA, double rankingB) {
		double[] ret = new double[2];
		double estA = 1.0D / (1.0D + Math.pow(10.0D, (rankingB - rankingA) / 400.0D));
		double estB = 1.0D / (1.0D + Math.pow(10.0D, (rankingA - rankingB) / 400.0D));
		ret[0] = estA;
		ret[1] = estB;
		return ret;
	}

	public static int getConstant(int ranking) {
		if (ranking < 1000) {
			return 16;
		}
		if (ranking < 1401) {
			return 12;
		}
		return 8;
	}

	public static int[] getNewRankings(int rankingA, int rankingB, boolean victoryA) {
		double[] ests;
		int[] ret = new int[2];
		ests = getEstimations(rankingA, rankingB);
		int newRankA = (int) (rankingA + getConstant(rankingA) * ((victoryA ? 1 : 0) - ests[0]));
		int newRankB = (int) (rankingB + getConstant(rankingB) * ((victoryA ? 0 : 1) - ests[1]));
		ret[0] = Math.round(newRankA);
		ret[1] = Math.round(newRankB);
		return ret;
	}
}
