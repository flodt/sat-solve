package me.flodt.sat.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("MethodDoesntCallSuperMethod")
public class Clause implements AbstractClause {
	private final Set<AbstractLiteral> contents;

	public Clause(Set<AbstractLiteral> contents) {
		this.contents = contents;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Clause) {
			return contents.equals(((Clause) obj).contents);
		} else {
			return false;
		}
	}

	@Override
	public Set<AbstractLiteral> getContents() {
		return contents;
	}

	@Override
	public boolean containsLiteral(AbstractLiteral literal) {
		return contents.contains(literal);
	}

	@Override
	public void removeLiteral(AbstractLiteral literal) {
		contents.remove(literal);
	}

	@Override
	public boolean isEmpty() {
		return contents.isEmpty();
	}

	@Override
	public boolean isSingleton() {
		return contents.size() == 1;
	}

	@Override
	public AbstractLiteral getSingletonLiteral() {
		return getContents().stream().findFirst().orElseThrow();
	}

	public static Clause of(AbstractLiteral... literals) {
		Set<AbstractLiteral> set = new HashSet<>(Arrays.asList(literals));
		return new Clause(set);
	}

	public static AbstractClause empty() {
		return new Clause(new HashSet<>());
	}

	@Override
	public String toString() {
		return "{" +
				contents +
				'}';
	}

	@Override
	public Iterator<AbstractLiteral> iterator() {
		return getContents().iterator();
	}

	@Override
	public AbstractClause clone() {
		Set<AbstractLiteral> newSet = new HashSet<>();

		for (AbstractLiteral literal : contents) {
			newSet.add(literal.clone());
		}

		return new Clause(newSet);
	}
}
