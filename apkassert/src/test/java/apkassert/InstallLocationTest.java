package apkassert;

import static org.junit.Assert.*;

import org.junit.Test;

import apkassert.InstallEnum;

import static apkassert.InstallLocation.*;

public class InstallLocationTest {

	final static InstallEnum[] locs = InstallEnum.values();
	final static String[] apks = { "DefaultManifest", "AutoManifest",
			"InternalManifest", "ExternalManifest" };
	@Test
	public void testFromApk() {
		for (int i = 0; i < apks.length; i++) {
			assertTrue(locs[i] == fromApk("test/apk/" + apks[i] + ".apk"));
		}
	}

	@Test
	public void testFromManifest() {
		for (int i = 0; i < apks.length; i++) {
			assertTrue(locs[i] == fromManifest("test/xml/" + apks[i] + ".apk/AndroidManifest.xml"));
		}
	}
}