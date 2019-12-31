package me.flodt.sat.logic;

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
	boolean equals(Object o);
}
