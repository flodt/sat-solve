package me.flodt.sat.dpll;

import me.flodt.sat.logic.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DPLLTest {
	@ParameterizedTest
	@MethodSource("getTestClauseSetsAndAssignments")
	@DisplayName("DPLL algorithm")
	void dpllSolver(AbstractClauseSet clauseSet, Assignment assignments, boolean satisfiable) {
		SatisfiabilitySolution solution = new Solver().solve(clauseSet);

		assertEquals(satisfiable, solution.isSatisfiable());

		if (satisfiable) {
			for (AbstractLiteral lit : assignments.getAsMap().keySet()) {
				assertEquals(assignments.valueOf(lit).toString(), solution.valueOf(lit).toString());
			}
		}
	}

	static Stream<Arguments> getTestClauseSetsAndAssignments() {
		return Stream.of(
				exampleOne(),
				exampleTwo(),
				contradiction(),
				tautology(),
				trivialTautology(),
				trivialContradiction(),
				dontCares()
		);
	}

	static Arguments exampleOne() {
		Literal A = new Literal(true, "A");
		Literal B = new Literal(true, "B");
		Literal C = new Literal(true, "C");
		Literal D = new Literal(true, "D");

		Literal NOT_A = A.negated();
		Literal NOT_B = B.negated();
		Literal NOT_C = C.negated();
		Literal NOT_D = D.negated();

		ClauseSet clauseSet = ClauseSet.of(
				Clause.of(C, NOT_A, NOT_D),
				Clause.of(D, NOT_A, NOT_C),
				Clause.of(A, C, NOT_B),
				Clause.of(C, D, NOT_A),
				Clause.of(NOT_A, NOT_C, NOT_D),
				Clause.of(A, B, NOT_C),
				Clause.of(A, B, C)
		);

		//expected solution is
		Assignment assignments = new Assignment();
		assignments.putFalse(A);
		assignments.putTrue(B);
		assignments.putTrue(C);
		assignments.putTrue(D);

		return Arguments.of(clauseSet, assignments, true);
	}

	static Arguments exampleTwo() {
		Literal p = new Literal(true, "p");
		Literal q = new Literal(true, "q");
		Literal t = new Literal(true, "t");
		Literal u = new Literal(true, "u");
		Literal y = new Literal(true, "y");

		Literal not_p = p.negated();
		Literal not_q = q.negated();
		Literal not_t = t.negated();
		Literal not_u = u.negated();
		Literal not_y = y.negated();

		ClauseSet clauseSet = ClauseSet.of(
				Clause.of(u),
				Clause.of(p, not_y),
				Clause.of(y, not_t, not_u, not_q),
				Clause.of(not_y, not_q),
				Clause.of(y, q, not_p),
				Clause.of(t),
				Clause.of(q, not_t, not_y, not_u, not_p)
		);

		//expected solution is
		Assignment assignments = new Assignment();
		assignments.putFalse(p);
		assignments.putFalse(q);
		assignments.putTrue(t);
		assignments.putTrue(u);
		assignments.putFalse(y);

		return Arguments.of(clauseSet, assignments, true);
	}

	static Arguments contradiction() {
		Literal a = new Literal(true, "a");

		ClauseSet clauseSet = ClauseSet.of(
				Clause.of(a),
				Clause.of(a.negated())
		);

		return Arguments.of(clauseSet, null, false);
	}

	static Arguments tautology() {
		Literal a = new Literal(true, "a");

		ClauseSet clauseSet = ClauseSet.of(
				Clause.of(a, a.negated())
		);

		Assignment assignments = new Assignment();
		assignments.putTrue(a);

		return Arguments.of(clauseSet, assignments, true);
	}

	static Arguments trivialTautology() {
		return Arguments.of(ClauseSet.empty(), new Assignment(), true);
	}

	static Arguments trivialContradiction() {
		return Arguments.of(ClauseSet.of(Clause.of()), null, false);
	}

	static Arguments dontCares() {
		Literal a = new Literal(true, "a");
		Literal b = new Literal(true, "b");

		Assignment assignments = new Assignment();
		assignments.putTrue(a);
		assignments.putDontCare(b);

		return Arguments.of(
				ClauseSet.of(
						Clause.of(
								a, b
						)
				),
				assignments,
				true
		);
	}
}