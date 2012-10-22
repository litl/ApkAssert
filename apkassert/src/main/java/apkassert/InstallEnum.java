package apkassert;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public enum InstallEnum {
	// Omitted is the same as internal, a value of one.
	// For the purposes of this enum, omitted is -1
	// until the value() method is called.
	OMITTED(-1, "omitted"), AUTO(0, "auto"), INTERNAL(1, "internalOnly"), EXTERNAL(
			2, "preferExternal");

	private static final ImmutableMap<Integer, InstallEnum> map;

	private final int value;
	private final String string;

	/**
	 * Returns value of install location. Omitted is 1, the same as internal.
	 * 
	 * @return int value of install location
	 */
	public int value() {
		// Omitted defaults to internal: a value of 1.
		return value == -1 ? 1 : value;
	}

	static {
		final Builder<Integer, InstallEnum> builder = ImmutableMap.builder();

		for (InstallEnum key : InstallEnum.values()) {
			builder.put(key.value, key);
		}

		map = builder.build();
	}

	private InstallEnum(final int value, final String string) {
		this.value = value;
		this.string = string;
	}

	/**
	 * Returns enum from an int value.
	 * 
	 * <pre>
	 * -1 = omitted
	 *  0 = auto
	 *  1 = internalOnly
	 *  2 = preferExternal
	 * </pre>
	 * 
	 * @param val
	 *            the integer value to convert to a location enum
	 * @return a location enum
	 */
	public static InstallEnum from(int val) {
		return map.get(val);
	}

	@Override
	public String toString() {
		return string;
	}
}