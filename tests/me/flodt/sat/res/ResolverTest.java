package me.flodt.sat.res;

import me.flodt.sat.logic.AbstractClauseSet;
import me.flodt.sat.logic.Clause;
import me.flodt.sat.logic.ClauseSet;
import me.flodt.sat.logic.Literal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ResolverTest {
	@ParameterizedTest
	@MethodSource("getTestClauseSetsAndResolutions")
	@DisplayName("Resolution algorithm")
	void resolver(AbstractClauseSet clauseSet, boolean expected) {
		Resolution resolution = new Resolver().resolve(clauseSet);

		assertEquals(expected, resolution.isContradictory());
	}

	static Stream<Arguments> getTestClauseSetsAndResolutions() {
		return Stream.of(
				exampleOne(),
				trivialTautology(),
				trivialContradiction(),
				tautology(),
				contradiction()
		);
	}

	static Arguments exampleOne() {
		Literal p = new Literal(true, "p");
		Literal s = new Literal(true, "s");
		Literal q = new Literal(true, "q");
		Literal r = new Literal(true, "r");

		ClauseSet clauseSet = ClauseSet.of(
				Clause.of(q.negated(), s),
				Clause.of(p.negated(), q, s),
				Clause.of(p),
				Clause.of(r, s.negated()),
				Clause.of(p.negated(), r.negated(), s.negated())
		);

		return Arguments.of(clauseSet, true);
	}

	static Arguments trivialTautology() {
		return Arguments.of(ClauseSet.empty(), false);
	}

	static Arguments trivialContradiction() {
		return Arguments.of(ClauseSet.of(Clause.of()), true);
	}

	static Arguments tautology() {
		Literal a = new Literal(true, "a");

		return Arguments.of(ClauseSet.of(Clause.of(a, a.negated())), false);
	}

	static Arguments contradiction() {
		Literal a = new Literal(true, "a");

		return Arguments.of(ClauseSet.of(Clause.of(a), Clause.of(a.negated())), true);
	}
}