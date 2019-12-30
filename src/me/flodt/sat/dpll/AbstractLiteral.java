package me.flodt.sat.dpll;

import java.util.Comparator;

public interface AbstractLiteral {
	Comparator<AbstractLiteral> LITERAL_COMPARATOR = Comparator
			.comparing(AbstractLiteral::isPositive)
			.reversed()
			.thenComparing(AbstractLiteral::descriptor);

	boolean isPositive();
	AbstractLiteral negated();
	String descriptor();
	AbstractLiteral clone();
}
