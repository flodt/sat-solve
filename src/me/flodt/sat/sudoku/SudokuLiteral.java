package me.flodt.sat.sudoku;

import me.flodt.sat.logic.AbstractLiteral;

import java.util.Objects;

@SuppressWarnings("MethodDoesntCallSuperMethod")
public class SudokuLiteral implements AbstractLiteral {
	private final SudokuEntry entry;
	private final int line;
	private final int row;
	private final boolean positive;

	public SudokuLiteral(SudokuEntry entry, int line, int row, boolean positive) {
		this.entry = entry;
		this.line = line;
		this.row = row;
		this.positive = positive;
	}

	@Override
	public boolean isPositive() {
		return positive;
	}

	@Override
	public AbstractLiteral negated() {
		return new SudokuLiteral(entry, line, row, !isPositive());
	}

	@Override
	public String descriptor() {
		return entry.intValue() + "_(" + line + ", " + row + ")";
	}

	public SudokuEntry getEntry() {
		return entry;
	}

	public int getAsInt() {
		return entry.intValue();
	}

	public int getLine() {
		return line;
	}

	public int getRow() {
		return row;
	}

	@Override
	public AbstractLiteral clone() {
		return new SudokuLiteral(entry, line, row, positive);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SudokuLiteral that = (SudokuLiteral) o;
		return line == that.line &&
				row == that.row &&
				positive == that.positive &&
				entry == that.entry;
	}

	@Override
	public int hashCode() {
		return Objects.hash(entry, line, row, positive);
	}

	@Override
	public String toString() {
		return ((positive) ? "" : "¬ ") + descriptor();
	}
}
