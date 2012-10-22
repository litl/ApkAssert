package apkassert;

import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.io.ByteStreams;

// jarsigner -verify -verbose -certs test.apk
public class Cert {

	final static class CertException extends Exception {
		private static final long serialVersionUID = 4472200419286759584L;

		public CertException(final String message) {
			super(message);
		}
	}

	/**
	 * Verifies that all files not inside META-INF/ are signed. Verifies that
	 * the certificate does not contain the word debug.
	 * 
	 * @param apkPath
	 *            path to the apk to verify
	 * @throws Exception
	 *             if a file is unsigned and outside of META-INF/ or cert
	 *             contains the word debug
	 */
	public static void verify(final String apkPath) throws Exception {
		final boolean verify = true;
		final JarFile jar = new JarFile(apkPath, verify);
		final Enumeration<JarEntry> files = jar.entries();

		while (files.hasMoreElements()) {
			final JarEntry file = files.nextElement();
			final String name = file.getName();
			// Must read JarEntry before getting code signers.
			ByteStreams.toByteArray(jar.getInputStream(file));

			final CodeSigner[] signers = file.getCodeSigners();
			final boolean unsigned = signers == null;
			// Files in META-INF do not have to be signed.
			if (unsigned && !name.startsWith("META-INF/")) {
				throw new CertException(name + " not signed.");
			}

			// If the file is in META-INF/ without a signature
			// move onto the next file.
			if (unsigned) {
				continue;
			}

			for (final CodeSigner signer : signers) {
				final List<? extends Certificate> certificates = signer
						.getSignerCertPath().getCertificates();
				for (final Certificate certificate : certificates) {
					if (certificate instanceof X509Certificate) {
						final String certName = ((X509Certificate) certificate)
								.getSubjectDN().getName();
						if (certName.toLowerCase().contains("debug")) {
							throw new CertException(
									"Certificate contains Debug.\n" + certName);
						}
					}
				}
			}
		}
	}
}