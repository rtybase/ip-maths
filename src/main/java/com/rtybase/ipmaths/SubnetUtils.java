package com.rtybase.ipmaths;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class SubnetUtils {

	public static final int IP_V4_BYTE_SIZE = 4;
	public static final int IP_V4_BIT_SIZE = 8 * IP_V4_BYTE_SIZE;

	public static final int IP_V6_BYTE_SIZE = 16;
	public static final int IP_V6_BIT_SIZE = 8 * IP_V6_BYTE_SIZE;

	private SubnetUtils() {
		// by design
	}

	public static InetAddress convertIpv4ToIpv6(InetAddress address) throws UnknownHostException {
		Objects.requireNonNull(address, "address must not be null!");

		if (address instanceof Inet4Address) {
			byte[] ipV4bytes = address.getAddress();

			byte[] ipV6bytes = new byte[IP_V6_BYTE_SIZE];
			ipV6bytes[10] = (byte) 0xff;
			ipV6bytes[11] = (byte) 0xff;
			ipV6bytes[12] = ipV4bytes[0];
			ipV6bytes[13] = ipV4bytes[1];
			ipV6bytes[14] = ipV4bytes[2];
			ipV6bytes[15] = ipV4bytes[3];
			return Inet6Address.getByAddress(null, ipV6bytes, -1);
		} else {
			return address;
		}
	}

	public static SubnetConverter cidrConverterWith(int ipv4PrefixBits, int ipv6PrefixBits) {
		return new SubnetConverter(ipv4PrefixBits, ipv6PrefixBits);
	}

	public static SubnetValidator subnetValidatorWith(String ipPrefix, int ipPrefixBits) throws UnknownHostException {
		return new SubnetValidator(ipPrefix, ipPrefixBits);
	}

	public static SubnetValidator subnetValidatorWith(String cidrNotatedSubnet) throws UnknownHostException {
		Objects.requireNonNull(cidrNotatedSubnet, "cidrNotatedSubnet must not be null!");

		String[] args = cidrNotatedSubnet.split("/");
		if (args.length == 2) {
			return new SubnetValidator(args[0], Integer.parseInt(args[1]));
		} else {
			throw new IllegalArgumentException("Prvided 'cidrNotatedSubnet' has a wrong format!");
		}
	}
}
