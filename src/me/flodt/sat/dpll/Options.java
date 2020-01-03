package me.flodt.sat.dpll;

public class Options {
	private static boolean PLR = true;
	private static boolean OLR = true;
	private static boolean DEBUG = false;

	public static boolean PLRallowed() {
		return PLR;
	}

	public static void allowPLR() {
		PLR = true;
	}

	public static void disallowPLR() {
		PLR = false;
	}

	public static boolean OLRallowed() {
		return OLR;
	}

	public static void allowOLR() {
		OLR = true;
	}

	public static void disallowOLR() {
		OLR = false;
	}

	public static boolean debugAllowed() {
		return DEBUG;
	}

	public static void allowDebugOutput() {
		DEBUG = true;
	}

	public static void disallowDebugOutput() {
		DEBUG = false;
	}

	public static void reset() {
		allowOLR();
		allowPLR();
		disallowDebugOutput();
	}
}
