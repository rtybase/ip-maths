# ip-maths
A utility library for IPv4 and IPv6 maths. It allows to ...

#### 1. Convert IPv4 into IPv6

```java
		SubnetUtils.convertIpv4ToIpv6(InetAddresses.forString("192.168.1.20"));
```

The result is `0:0:0:0:0:ffff:c0a8:114`.

#### 2. Check if an IP belongs to a subnet
For IPv4

```java
		SubnetValidator validator = SubnetUtils.subnetValidatorWith("192.168.0.0/16");
		assertTrue(validator.isInSubnet("192.168.1.20"));
```

For IPv6

```java
		SubnetValidator validator = SubnetUtils.subnetValidatorWith("2607:f818:0:0:0:0:0:0/32");
		assertTrue(validator.isInSubnet("2607:f818:200:201:616c:4b4e:f19e:cee5"));
```

#### 3. Convert and IP to a subnet.
This is useful for "hiding" client IP addresses, like ENDS Ckient Subnet [(ECS)](https://developers.google.com/speed/public-dns/docs/ecs).
For IPv4 and IPv6

```java
		SubnetConverter converter = SubnetUtils.cidrConverterWith(24, 32);
		
		assertEquals(converter.toCidrNotation("192.168.1.20").toString(), "192.168.1.0/24");
		assertEquals(converter.toCidrNotation("2607:f818:200:201:616c:4b4e:f19e:cee5").toString(), "2607:f818:0:0:0:0:0:0/32");
```
