package com.zyplayer.doc.grpc.framework.consts;

import com.google.protobuf.ByteString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Const {
	
	public static final Set<String> BASE_TYPE;
	static {
		BASE_TYPE = new HashSet<>();
		BASE_TYPE.add(String.class.getName());
		BASE_TYPE.add(Integer.class.getName());
		BASE_TYPE.add(Long.class.getName());
		BASE_TYPE.add(Double.class.getName());
		BASE_TYPE.add(Date.class.getName());
		BASE_TYPE.add(Byte.class.getName());
		BASE_TYPE.add(Float.class.getName());
		BASE_TYPE.add(BigDecimal.class.getName());
		BASE_TYPE.add(ByteString.class.getName());
		
		BASE_TYPE.add(char.class.getName());
		BASE_TYPE.add(int.class.getName());
		BASE_TYPE.add(long.class.getName());
		BASE_TYPE.add(double.class.getName());
		BASE_TYPE.add(byte.class.getName());
		BASE_TYPE.add(float.class.getName());
	}
}
