package me.flodt.sat.dpll;

public class SatisfiabilitySolution {
	private final boolean satisfiable;
	private final Assignment assignments;

	public SatisfiabilitySolution() {
		satisfiable = false;
		assignments = null;
	}

	public SatisfiabilitySolution(Assignment assignments) {
		satisfiable = true;
		this.assignments = assignments;
	}

	public boolean isSatisfiable() {
		return satisfiable;
	}

	public Value valueOf(AbstractLiteral literal) {
		if (assignments != null) {
			return assignments.valueOf(literal);
		} else {
			return Value.INVALID;
		}
	}

	@Override
	public String toString() {
		return "SatisfiabilitySolution{" +
				"satisfiable=" + satisfiable +
				", assignments=" + ((assignments == null) ? ("impossible") : (assignments)) +
				'}';
	}
}
