package com.rtybase.ipmaths;

import java.net.InetAddress;
import java.util.Objects;

public class SubnetDetails {
	private final InetAddress addressPrefix;
	private final int prefixBits;

	public SubnetDetails(InetAddress addressPrefix, int prefixBits) {
		this.addressPrefix = addressPrefix;
		this.prefixBits = prefixBits;
	}

	public InetAddress getAddressPrefix() {
		return addressPrefix;
	}

	public int getPrefixBits() {
		return prefixBits;
	}

	@Override
	public int hashCode() {
		return Objects.hash(addressPrefix, prefixBits);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final SubnetDetails other = (SubnetDetails) obj;

		return Objects.equals(addressPrefix, other.addressPrefix)
				&& Objects.equals(prefixBits, other.prefixBits);
	}

	@Override
	public String toString() {
		return addressPrefix.getHostAddress() + "/" + prefixBits;
	}
}
