package com.zyplayer.doc.grpc.framework.consts;

import com.google.protobuf.ByteString;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
public class Const {
	
	/** 所有基础类型 */
	public static final Map<String, Class<?>> BASE_TYPE;
	static {
		BASE_TYPE = new HashMap<>();
		BASE_TYPE.put(String.class.getName(), String.class);
		BASE_TYPE.put(Integer.class.getName(), Integer.class);
		BASE_TYPE.put(Long.class.getName(), Long.class);
		BASE_TYPE.put(Double.class.getName(), Double.class);
		BASE_TYPE.put(Date.class.getName(), Date.class);
		BASE_TYPE.put(Byte.class.getName(), Byte.class);
		BASE_TYPE.put(Float.class.getName(), Float.class);
		BASE_TYPE.put(BigDecimal.class.getName(), BigDecimal.class);
		BASE_TYPE.put(ByteString.class.getName(), ByteString.class);
		
		BASE_TYPE.put(char.class.getName(), char.class);
		BASE_TYPE.put(int.class.getName(), int.class);
		BASE_TYPE.put(long.class.getName(), long.class);
		BASE_TYPE.put(double.class.getName(), double.class);
		BASE_TYPE.put(byte.class.getName(), byte.class);
		BASE_TYPE.put(float.class.getName(), float.class);
	}
}
