package apkassert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;

import pxb.android.axml.AxmlReader;
import pxb.android.axml.AxmlVisitor;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

/** Defaults to OMITTED **/
final class LocationValue {
	public InstallEnum value = InstallEnum.OMITTED;
}

public class InstallLocation {

	/** Returns InstallEnum. Defaults to omitted if not found. **/
	public static InstallEnum getInstallLocation(final byte[] manifest) {
		final LocationValue location = new LocationValue();

		try {
			final AxmlReader reader = new AxmlReader(manifest);
			reader.accept(new AxmlVisitor() {

				@Override
				public NodeVisitor first(String namespace, String name) {
					// Visit manifest
					NodeVisitor visitor = super.first(namespace, name);

					// Extract manifest installLocation attr
					return new NodeVisitor(visitor) {
						@Override
						public void attr(String namespace, String name,
								int resourceId, int type, Object obj) {
							if (name.contentEquals("installLocation")) {
								location.value = InstallEnum
										.from((Integer) obj);
							}
						}
					};
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return location.value;
	}

	/** Returns InstallEnum or null on error. **/
	public static InstallEnum fromManifest(final String androidManifestPath) {
		try {
			final byte[] manifest = Files
					.toByteArray(new File(androidManifestPath));
			return getInstallLocation(manifest);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/** Returns InstallEnum or null on error. **/
	public static InstallEnum fromApk(final String apkPath) {
		try {
			final JarFile jar = new JarFile(apkPath);
			final InputStream stream = jar.getInputStream(jar
					.getEntry("AndroidManifest.xml"));
			final byte[] manifest = new byte[stream.available()];
			ByteStreams.readFully(stream, manifest);

			return getInstallLocation(manifest);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}