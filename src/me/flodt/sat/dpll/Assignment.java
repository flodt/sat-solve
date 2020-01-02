package me.flodt.sat.dpll;

import me.flodt.sat.logic.AbstractLiteral;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Assignment {
	private final Map<AbstractLiteral, Value> assignments;

	public Assignment() {
		assignments = new HashMap<>();
	}

	public void putDontCare(AbstractLiteral literal) {
		assignments.put(literal, Value.DONT_CARE);
		assignments.put(literal.negated(), Value.DONT_CARE);
	}

	public void putTrue(AbstractLiteral literal) {
		assignments.put(literal, Value.TRUE);
		assignments.put(literal.negated(), Value.FALSE);
	}

	public void putTrueByRule(AbstractLiteral literal) {
		assignments.put(literal, Value.TRUE_RULE);
		assignments.put(literal.negated(), Value.FALSE_RULE);
	}

	public void putFalse(AbstractLiteral literal) {
		assignments.put(literal, Value.FALSE);
		assignments.put(literal.negated(), Value.TRUE);
	}

	public void putFalseByRule(AbstractLiteral literal) {
		assignments.put(literal, Value.FALSE_RULE);
		assignments.put(literal.negated(), Value.TRUE_RULE);
	}

	public Map<AbstractLiteral, Value> getAsMap() {
		return assignments;
	}

	public Value valueOf(AbstractLiteral literal) {
		return assignments.get(literal);
	}

	public boolean isFixedByRule(AbstractLiteral literal) {
		return (valueOf(literal).equals(Value.TRUE_RULE) || valueOf(literal).equals(Value.FALSE_RULE));
	}

	@Override
	public String toString() {
		return "{" +
				assignments.keySet()
						.stream()
						.sorted(AbstractLiteral.LITERAL_COMPARATOR)
						.map(literal -> literal.toString() + "=" + assignments.get(literal).toString())
						.collect(Collectors.joining(", ")) +
				"}";
	}
}
