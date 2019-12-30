package me.flodt.sat.dpll;

import java.util.Objects;

@SuppressWarnings("MethodDoesntCallSuperMethod")
public class Literal implements AbstractLiteral {
	private final boolean positive;
	private final String descriptor;

	public Literal(boolean positive, String descriptor) {
		this.positive = positive;
		this.descriptor = descriptor;
	}

	@Override
	public boolean isPositive() {
		return positive;
	}

	@Override
	public String descriptor() {
		return descriptor;
	}

	@Override
	public Literal negated() {
		return new Literal(!isPositive(), descriptor);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;

		if (o instanceof Literal) {
			return (this.isPositive() == ((Literal) o).isPositive()) && (this.descriptor().equals(((Literal) o).descriptor()));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(positive, descriptor);
	}

	@Override
	public String toString() {
		return (isPositive() ? "" : "¬") + descriptor;
	}

	@Override
	public AbstractLiteral clone() {
		return new Literal(positive, descriptor);
	}
}
