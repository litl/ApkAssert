package apkassert.example;

import java.io.File;

import apkassert.Cert;
import apkassert.InstallEnum;
import apkassert.InstallLocation;

public class Sample {

	public static long sizeInMB(final String apkPath) {
		// bytes / 1024 = KB
		// KB / 1024 = MB
		final File apk = new File(apkPath);
		return apk.length() / (1024 * 1024);
	}
	public static void check(final String apkPath) {
		final InstallEnum install = InstallLocation.fromApk(apkPath);
		System.out.println("Install location: " + install);
		
		final boolean lessThan50MB = sizeInMB(apkPath) < 50;
		System.out.println("Less than 50 MB? " + lessThan50MB);
		
		try {
			Cert.verify(apkPath);
			System.out.println("Verified successfully.");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: apkPath");
			System.exit(0);
		}
		check(args[0]);
		
//		check("test/apk/AutoManifest.apk");
	}
}