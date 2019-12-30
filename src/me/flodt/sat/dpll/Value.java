package me.flodt.sat.dpll;

public enum Value {
	TRUE,
	FALSE,
	DONT_CARE,
	TRUE_RULE,
	FALSE_RULE,
	INVALID;

	@Override
	public String toString() {
		switch (this) {
			case TRUE:
			case TRUE_RULE:
				return "true";
			case FALSE:
			case FALSE_RULE:
				return "false";
			case DONT_CARE:
				return "don't care";
			case INVALID:
			default:
				return "invalid";
		}
	}
}
