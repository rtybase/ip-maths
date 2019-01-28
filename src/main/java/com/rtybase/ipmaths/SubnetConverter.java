package com.rtybase.ipmaths;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import com.google.common.base.Preconditions;
import com.google.common.net.InetAddresses;

//https://google.github.io/guava/releases/23.5-jre/api/docs/com/google/common/net/InetAddresses.html 
//for more details
public class SubnetConverter {

	private final int ipv4PrefixBits;
	private final int ipv6PrefixBits;

	/* package */ SubnetConverter(int ipv4PrefixBits, int ipv6PrefixBits) {
		Preconditions.checkArgument(ipv4PrefixBits >= 0, "ipv4PrefixBits must be positive!");
		Preconditions.checkArgument(ipv6PrefixBits >= 0, "ipv6PrefixBits must be positive!");

		this.ipv4PrefixBits = ipv4PrefixBits;
		this.ipv6PrefixBits = ipv6PrefixBits;
	}

	// http://www.dnsjava.org/doc/index.html?org/xbill/DNS/ClientSubnetOption.html
	// for more details
	public SubnetDetails toCidrNotation(String ip) throws UnknownHostException {
		Objects.requireNonNull(ip, "ip must not be null!");

		InetAddress address = InetAddresses.forString(ip);
		if (address instanceof Inet4Address) {
			return computeSubnetDetails(address, ipv4PrefixBits, SubnetUtils.IP_V4_BIT_SIZE);
		} else {
			return computeSubnetDetails(address, ipv6PrefixBits, SubnetUtils.IP_V6_BIT_SIZE);
		}
	}

	private static SubnetDetails computeSubnetDetails(InetAddress address, int prefixBits, int addressSizeInBits)
			throws UnknownHostException {
		byte[] bytes = address.getAddress();
		int bitsToTrim = addressSizeInBits - prefixBits;
		if (bitsToTrim > 0) {
			address = trimBits(bytes, bitsToTrim);
		} else {
			prefixBits = addressSizeInBits;
		}
		return new SubnetDetails(address, prefixBits);
	}

	private static InetAddress trimBits(byte[] bytes, int bitsToTrim) throws UnknownHostException {
		int s = bitsToTrim;
		for (int i = bytes.length - 1; (s > 0) && (i >= 0); i--, s -= 8) {
			if (s < 8) {
				bytes[i] >>= s;
				bytes[i] <<= s;
			} else {
				bytes[i] = 0;
			}
		}
		return InetAddress.getByAddress(bytes);
	}
}
