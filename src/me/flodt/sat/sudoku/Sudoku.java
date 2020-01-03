package me.flodt.sat.sudoku;

import me.flodt.sat.dpll.SatisfiabilitySolution;
import me.flodt.sat.logic.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static me.flodt.sat.sudoku.SudokuEntry.*;

@SuppressWarnings("DuplicatedCode")
public class Sudoku {
	private final SudokuEntry[][] contents;

	public Sudoku() {
		contents = new SudokuEntry[9][9];
		for (SudokuEntry[] line : contents) {
			Arrays.fill(line, EMPTY);
		}
	}

	public Sudoku(SudokuEntry[][] contents) {
		this.contents = contents;
	}

	public void setValueAt(int line, int row, int value) {
		contents[line - 1][row - 1] = fromInt(value);
	}

	public SudokuEntry valueAt(int line, int row) {
		return contents[line - 1][row - 1];
	}

	public static Sudoku fromSATSolution(SatisfiabilitySolution solution) {
		if (!solution.isSatisfiable()) {
			throw new IllegalArgumentException("Sudoku is not solvable");
		}
		
		SudokuEntry[][] generated = new SudokuEntry[9][9];
		
		solution.literals().stream()
				.filter(AbstractLiteral::isPositive)
				.map(lit -> (SudokuLiteral) lit)
				.filter(lit -> solution.valueOf(lit).isTrue())
				.forEach(s -> generated[s.getLine() - 1][s.getRow() - 1] = s.getEntry());
		
		return new Sudoku(generated);
	}

	public AbstractClauseSet generateLogicalEquivalent() {
		Set<AbstractClause> rawSet = new HashSet<>();

		//conditions:
		//every number in every line
		for (int number = 1; number <= 9; number++) {
			for (int line = 1; line <= 9; line++) {
				Set<AbstractLiteral> litSet = new HashSet<>();

				for (int row = 1; row <= 9; row++) {
					litSet.add(new SudokuLiteral(fromInt(number), line, row, true));
				}

				rawSet.add(new Clause(litSet));
			}
		}

		//every number in every row
		for (int number = 1; number <= 9; number++) {
			for (int row = 1; row <= 9; row++) {
				Set<AbstractLiteral> litSet = new HashSet<>();

				for (int line = 1; line <= 9; line++) {
					litSet.add(new SudokuLiteral(fromInt(number), line, row, true));
				}

				rawSet.add(new Clause(litSet));
			}
		}

		//every number in every square
		for (int number = 1; number <= 9; number++) {
			Set<AbstractLiteral> square1 = new HashSet<>();
			for (int line = 1; line <= 3; line++) {
				for (int row = 1; row <= 3; row++) {
					square1.add(new SudokuLiteral(fromInt(number), line, row, true));
				}
			}
			rawSet.add(new Clause(square1));

			Set<AbstractLiteral> square2 = new HashSet<>();
			for (int line = 1; line <= 3; line++) {
				for (int row = 4; row <= 6; row++) {
					square2.add(new SudokuLiteral(fromInt(number), line, row, true));
				}
			}
			rawSet.add(new Clause(square2));

			Set<AbstractLiteral> square3 = new HashSet<>();
			for (int line = 1; line <= 3; line++) {
				for (int row = 7; row <= 9; row++) {
					square3.add(new SudokuLiteral(fromInt(number), line, row, true));
				}
			}
			rawSet.add(new Clause(square3));

			//---

			Set<AbstractLiteral> square4 = new HashSet<>();
			for (int line = 4; line <= 6; line++) {
				for (int row = 1; row <= 3; row++) {
					square4.add(new SudokuLiteral(fromInt(number), line, row, true));
				}
			}
			rawSet.add(new Clause(square4));

			Set<AbstractLiteral> square5 = new HashSet<>();
			for (int line = 4; line <= 6; line++) {
				for (int row = 4; row <= 6; row++) {
					square5.add(new SudokuLiteral(fromInt(number), line, row, true));
				}
			}
			rawSet.add(new Clause(square5));

			Set<AbstractLiteral> square6 = new HashSet<>();
			for (int line = 4; line <= 6; line++) {
				for (int row = 7; row <= 9; row++) {
					square6.add(new SudokuLiteral(fromInt(number), line, row, true));
				}
			}
			rawSet.add(new Clause(square6));

			//---

			Set<AbstractLiteral> square7 = new HashSet<>();
			for (int line = 7; line <= 9; line++) {
				for (int row = 1; row <= 3; row++) {
					square7.add(new SudokuLiteral(fromInt(number), line, row, true));
				}
			}
			rawSet.add(new Clause(square7));

			Set<AbstractLiteral> square8 = new HashSet<>();
			for (int line = 7; line <= 9; line++) {
				for (int row = 4; row <= 6; row++) {
					square8.add(new SudokuLiteral(fromInt(number), line, row, true));
				}
			}
			rawSet.add(new Clause(square8));

			Set<AbstractLiteral> square9 = new HashSet<>();
			for (int line = 7; line <= 9; line++) {
				for (int row = 7; row <= 9; row++) {
					square9.add(new SudokuLiteral(fromInt(number), line, row, true));
				}
			}
			rawSet.add(new Clause(square9));
		}

		//every field has only one number
		for (int line = 1; line <= 9; line++) {
			for (int row = 1; row <= 9; row++) {
				for (int x = 1; x <= 9; x++) {
					for (int y = 1; y <= 9; y++) {
						if (x == y) continue;

						rawSet.add(Clause.of(
								new SudokuLiteral(fromInt(x), line, row, false),
								new SudokuLiteral(fromInt(y), line, row, false)
						));
					}
				}
			}
		}

		//starting configuration
		for (int i = 0; i < contents.length; i++) {
			for (int j = 0; j < contents[i].length; j++) {
				SudokuEntry temp = contents[i][j];

				if (temp != EMPTY) {
					rawSet.add(Clause.of(new SudokuLiteral(temp, i + 1, j + 1, true)));
				}
			}
		}

		return new ClauseSet(rawSet);
	}

	public static Sudoku parseStartingCondition(String condition) {
		Sudoku sudoku = new Sudoku();

		String[] fields = condition.split(",");

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals("0")) {
				continue;
			}

			int value = Integer.parseInt(fields[i]);

			int line = (i / 9) + 1;
			int row = (i % 9) + 1;

			sudoku.setValueAt(line, row, value);
		}

		return sudoku;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Sudoku sudoku = (Sudoku) o;
		return Arrays.equals(contents, sudoku.contents);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(contents);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("-------------------------\n");

		for (int i = 0; i < 3; i++) {
			sb.append("| ");

			for (int j = 0; j < 3; j++) {
				sb.append(contents[i][j]).append(" ");
			}
			sb.append("| ");
			for (int j = 3; j < 6; j++) {
				sb.append(contents[i][j]).append(" ");
			}
			sb.append("| ");
			for (int j = 6; j < 9; j++) {
				sb.append(contents[i][j]).append(" ");
			}

			sb.append("|\n");
		}

		sb.append("-------------------------\n");

		for (int i = 3; i < 6; i++) {
			sb.append("| ");

			for (int j = 0; j < 3; j++) {
				sb.append(contents[i][j]).append(" ");
			}
			sb.append("| ");
			for (int j = 3; j < 6; j++) {
				sb.append(contents[i][j]).append(" ");
			}
			sb.append("| ");
			for (int j = 6; j < 9; j++) {
				sb.append(contents[i][j]).append(" ");
			}

			sb.append("|\n");
		}

		sb.append("-------------------------\n");

		for (int i = 6; i < 9; i++) {
			sb.append("| ");

			for (int j = 0; j < 3; j++) {
				sb.append(contents[i][j]).append(" ");
			}
			sb.append("| ");
			for (int j = 3; j < 6; j++) {
				sb.append(contents[i][j]).append(" ");
			}
			sb.append("| ");
			for (int j = 6; j < 9; j++) {
				sb.append(contents[i][j]).append(" ");
			}

			sb.append("|\n");
		}

		sb.append("-------------------------\n");

		return sb.toString();
	}
}
