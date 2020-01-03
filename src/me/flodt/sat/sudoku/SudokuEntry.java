package me.flodt.sat.sudoku;

public enum SudokuEntry {
	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
	SIX(6), SEVEN(7), EIGHT(8), NINE(9), EMPTY(0);

	private final int value;

	SudokuEntry(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	public int intValue() {
		return value;
	}

	public static SudokuEntry fromInt(int i) {
		switch (i) {
			case 1:
				return ONE;
			case 2:
				return TWO;
			case 3:
				return THREE;
			case 4:
				return FOUR;
			case 5:
				return FIVE;
			case 6:
				return SIX;
			case 7:
				return SEVEN;
			case 8:
				return EIGHT;
			case 9:
				return NINE;
			default:
				return EMPTY;
		}
	}
}
