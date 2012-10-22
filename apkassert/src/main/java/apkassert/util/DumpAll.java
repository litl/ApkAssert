package apkassert.util;

import java.io.File;
import java.io.IOException;

import pxb.android.axml.AxmlReader;
import pxb.android.axml.AxmlVisitor.NodeVisitor;
import pxb.android.axml.DumpAdapter;
import pxb.android.axml.DumpAdapter.DumpNodeAdapter;

import com.google.common.io.Files;

public class DumpAll {

	public static DumpNodeAdapter createDumpNode(final NodeVisitor visitor) {
		return new DumpNodeAdapter(visitor) {
			@Override
			public NodeVisitor child(final String namespace, final String name) {
				final NodeVisitor visitor = super.child(namespace, name);
				return createDumpNode(visitor);
			}
		};
	}

	public static void dumpToStandardOut(final byte[] manifest) {
		final AxmlReader reader = new AxmlReader(manifest);
		try {
			reader.accept(new DumpAdapter() {
				@Override
				public DumpNodeAdapter first(final String namespace,
						final String name) {
					final NodeVisitor visitor = super.first(namespace, name);
					return createDumpNode(visitor);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void dumpXML(final String xmlPath) {
		try {
			final byte[] manifest = Files.toByteArray(new File(xmlPath));
			dumpToStandardOut(manifest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		dumpXML("test/xml/AutoManifest.apk/AndroidManifest.xml");
	}
}