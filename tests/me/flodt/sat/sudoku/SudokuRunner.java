package me.flodt.sat.sudoku;

import me.flodt.sat.dpll.Options;
import me.flodt.sat.dpll.SatisfiabilitySolution;
import me.flodt.sat.dpll.Solver;

/**
 * Just used for testing.
 */
@Deprecated
public class SudokuRunner {
	public static void main(String[] args) {
		Sudoku expected, starting;

		if (args.length == 0 || args[0].equals("hard")) {
			expected = Sudoku.parseStartingCondition(
					"4,1,3,8,6,5,2,9,7," +
							"9,5,6,2,3,7,4,8,1," +
							"2,8,7,4,9,1,5,3,6," +
							"8,6,2,9,7,3,1,5,4," +
							"3,4,1,5,8,6,9,7,2," +
							"7,9,5,1,4,2,3,6,8," +
							"1,3,8,6,5,4,7,2,9," +
							"5,2,9,7,1,8,6,4,3," +
							"6,7,4,3,2,9,8,1,5"
			);

			starting = Sudoku.parseStartingCondition(
					"4,1,0,0,6,5,0,0,7," +
							"0,0,6,0,0,7,4,8,0," +
							"2,0,7,4,9,0,0,0,6," +
							"0,6,0,0,7,0,1,0,0," +
							"3,0,1,5,0,0,0,7,2," +
							"0,9,0,0,4,2,3,0,8," +
							"1,0,8,6,0,0,0,2,9," +
							"0,2,0,0,1,8,6,4,0," +
							"6,0,0,3,0,0,0,1,0");

		} else {
			expected = Sudoku.parseStartingCondition(
					"4,1,3,8,6,5,2,9,7," +
							"9,5,6,2,3,7,4,8,1," +
							"2,8,7,4,9,1,5,3,6," +
							"8,6,2,9,7,3,1,5,4," +
							"3,4,1,5,8,6,9,7,2," +
							"7,9,5,1,4,2,3,6,8," +
							"1,3,8,6,5,4,7,2,9," +
							"5,2,9,7,1,8,6,4,3," +
							"6,7,4,3,2,9,8,1,5"
			);

			starting = Sudoku.parseStartingCondition(
					"4,1,0,8,6,5,2,9,7," +
							"9,5,6,2,3,7,0,8,1," +
							"2,8,7,4,9,1,5,3,6," +
							"8,6,2,9,7,3,1,5,4," +
							"0,4,1,5,8,6,9,7,2," +
							"7,9,5,1,4,2,3,6,8," +
							"1,3,8,6,5,0,7,2,9," +
							"5,2,9,7,1,8,6,4,3," +
							"6,7,4,3,0,9,8,1,5"
			);
		}

		runWith(expected, starting);
	}

	private static void runWith(Sudoku expected, Sudoku starting) {
		long start = System.currentTimeMillis();

		System.out.println(starting);

		Options.allowDebugOutput();
		SatisfiabilitySolution solution = new Solver().solve(starting.generateLogicalEquivalent());

		Sudoku solved = Sudoku.fromSATSolution(solution);
		System.out.println(solved);

		long end = System.currentTimeMillis();

		if (expected.toString().equals(solved.toString())) {
			System.out.println("Solution is correct!");
		} else {
			System.out.println("Solution is wrong!");
		}

		double seconds = (end - start) / 1000.0;
		System.out.println(seconds + " seconds");
	}
}
