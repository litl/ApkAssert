package apkassert;

import static org.junit.Assert.*;

import org.junit.Test;
import apkassert.InstallEnum;

public class InstallEnumTest {
	final static boolean debug = false;

	@Test
	public void testInstallLocEnum() {
		// -1 omitted must report value 1 internal.
		final int[] fromValues = { -1, 0, 1, 2 };
		final int[] reportedValues = { 1, 0, 1, 2 };
		final InstallEnum[] locs = InstallEnum.values();

		for (int i = 0; i < fromValues.length; i++) {
			if (debug) {
				System.out.print(i + " " + fromValues[i] + " " + locs[i]);
				System.out.println(" " + locs[i].value() + " "
						+ reportedValues[i]);
			}
			// Ensure enums created with .from() match expected enums.
			assertTrue(InstallEnum.from(fromValues[i]) == locs[i]);
			// Ensure enum values match expected reported values.
			assertTrue(locs[i].value() == reportedValues[i]);
		}
	}
}