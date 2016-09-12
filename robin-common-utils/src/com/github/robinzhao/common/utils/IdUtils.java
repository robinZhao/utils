package com.github.robinzhao.common.utils;

import java.util.UUID;


class IdUtils{
	public  static String toString19(UUID uuid) {
	   String spliter = "-";
	   Long mostSigBits=uuid.getMostSignificantBits();
	   Long leastSigBits = uuid.getLeastSignificantBits();
	   return (digits(mostSigBits >> 32, 8) + spliter +
            digits(mostSigBits >> 16, 4) + spliter +
            digits(mostSigBits, 4) + spliter +
            digits(leastSigBits >> 48, 4) + spliter +
            digits(leastSigBits, 12));
    }

	public static String digits(long val, int digits) {  
	    long hi = 1L << (digits * 4);  
	    return NumberUtils.toString(hi | (val & (hi - 1)), NumberUtils.MAX_RADIX);
	}  
	
	public static String randomUUID19(){
		UUID uuid = UUID.randomUUID();
		return toString19(uuid).substring(1);
	}
	
    public static UUID fromString19(String name) {
        String[] components = name.split("-");
        if (components.length != 5)
            throw new IllegalArgumentException("Invalid UUID string: "+name);
        for (int i=0; i<5; i++)
            components[i] = components[i];

        long mostSigBits = NumberUtils.decode62(components[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= NumberUtils.decode62(components[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= NumberUtils.decode62(components[2]).longValue();

        long leastSigBits = NumberUtils.decode62(components[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= NumberUtils.decode62(components[4]).longValue();
        return new UUID(mostSigBits, leastSigBits);
    }
	
	public static void main(String[] args){
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.getMostSignificantBits());
		System.out.println(uuid.getLeastSignificantBits());
		String uuidStr = toString19(uuid);
		System.out.println(uuid.toString());
		System.out.println(uuidStr);
		System.out.println(IdUtils.fromString19(uuidStr));
		System.out.println(toString19(IdUtils.fromString19(uuidStr)));
	}
}