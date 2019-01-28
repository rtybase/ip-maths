package com.rtybase.ipmaths;

import java.net.UnknownHostException;

public class SubnetValidator {
	private final SubnetConverter converter;
	private final SubnetDetails subnet;

	/* package */ SubnetValidator(String ipPrefix, int ipPrefixBits) throws UnknownHostException {
		converter = new SubnetConverter(ipPrefixBits, ipPrefixBits);
		subnet = converter.toCidrNotation(ipPrefix);
	}

	public boolean isInSubnet(String ip) throws UnknownHostException {
		SubnetDetails ipSubnet = converter.toCidrNotation(ip);
		return subnet.equals(ipSubnet);
	}
}
