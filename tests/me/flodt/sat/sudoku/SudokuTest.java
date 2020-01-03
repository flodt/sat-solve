package me.flodt.sat.sudoku;

import me.flodt.sat.dpll.Options;
import me.flodt.sat.dpll.SatisfiabilitySolution;
import me.flodt.sat.dpll.Solver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {
	@Test
	@DisplayName("Solves simple Sudoku")
	void solvesSimpleSudoku() {
		Sudoku expected = Sudoku.parseStartingCondition(
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

		Sudoku starting = Sudoku.parseStartingCondition(
				"4,0,3,8,6,5,2,9,7," +
						"9,5,6,2,3,7,4,8,1," +
						"2,8,7,4,9,1,5,3,6," +
						"8,6,2,9,7,3,1,5,4," +
						"3,4,1,5,8,6,9,7,2," +
						"7,9,5,1,4,2,3,6,8," +
						"1,3,8,6,5,4,7,2,9," +
						"5,2,9,7,1,8,6,4,3," +
						"6,7,4,3,2,9,8,1,5"
		);

		Options.allowDebugOutput();
		Sudoku actual = Sudoku.fromSATSolution(
				new Solver().solve(starting.generateLogicalEquivalent())
		);
		System.out.println(actual);

		assertEquals(expected.toString(), actual.toString());
		assertEquals(1, actual.valueAt(1, 2).intValue());
	}

	@Test
	@Disabled
	@DisplayName("Solves actual Sudoku")
	void solvesSudoku() {
		Sudoku expected = Sudoku.parseStartingCondition(
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

		Sudoku starting = Sudoku.parseStartingCondition(
				"4,1,0,0,6,5,0,0,7," +
						"0,0,6,0,0,7,4,8,0," +
						"2,0,7,4,9,0,0,0,6," +
						"0,6,0,0,7,0,1,0,0," +
						"3,0,1,5,0,0,0,7,2," +
						"0,9,0,0,4,2,3,0,8," +
						"1,0,8,6,0,0,0,2,9," +
						"0,2,0,0,1,8,6,4,0," +
						"6,0,0,3,0,0,0,1,0");

		System.out.println(starting);

		Options.allowDebugOutput();
		SatisfiabilitySolution solution = new Solver().solve(starting.generateLogicalEquivalent());

		Sudoku solved = Sudoku.fromSATSolution(solution);
		System.out.println(solved);

		assertEquals(expected.toString(), solved.toString());
	}
}