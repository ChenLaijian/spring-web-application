package com.spring.web.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * 
 * 项目名字:ddknow<br>
 * 类描述:<br>
 * 创建人:wengmd<br>
 * 创建时间:2014年11月6日<br>
 * 修改人:<br>
 * 修改时间:2014年11月6日<br>
 * 修改备注:<br>
 * 
 * @version 0.1<br>
 */
public class JsonUtils {
	private static final Logger logger = LoggerFactory
            .getLogger(JsonUtils.class);
	
	public static final String ENCODE = "utf-8";

	private static final String ERROR_MESSAGE = "jsonStr2Bean Error:{}";

	/**
	 * json 字符串转为bean对象
	 * 
	 * @param json 待转换Json字符串
	 * @param valueType 转换后对象
	 */
	public static <T> T jsonStr2Bean(String json, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T result = null;
		try {
			result = mapper.readValue(json, valueType);
		} catch (IOException e) {
			logger.error(ERROR_MESSAGE, e);
		}
		return result;
	}

	/**
	 * json 转为 List<Test>
	 * 
	 * @param json
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws Exception
	 */
	public static <T> T jsonStr2Bean(String json, Class<T> collectionClass, Class<?>... elementClasses)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
		return mapper.readValue(json, javaType);
	}

	/**
	 * bean 转 json str
	 * 
	 * @param bean
	 * @return
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	public static String bean2JsonStr(Object bean) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.writeValueAsString(bean);
	}

	public static List<String> json2List(String s) throws JsonParseException, JsonMappingException, IOException {
		List<String> rt = jsonStr2Bean(s, List.class, String.class);
		return rt;
	}

	public static String list2Json(List<String> ls) throws JsonProcessingException {
		return bean2JsonStr(ls);
	}

	public static String _bean2JsonStr(Object bean) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String result = "";
		try {
			result = mapper.writeValueAsString(bean);
		} catch (JsonProcessingException e) {
			logger.error(ERROR_MESSAGE, e);
		}
		return result;
	}

	public static String _list2Json(List<String> ls) {
		String result = "";
		try {
			result =  bean2JsonStr(ls);
		} catch (JsonProcessingException e) {
			logger.error(ERROR_MESSAGE, e);
		}
		return result;
	}

	public static String timestamp2String(Timestamp timestamp) {
		return null;
	}

	public static Map<String, String> json2Map(String s) throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> rt = (Map<String, String>) jsonStr2Bean(s, Map.class, String.class, String.class);
		return rt;
	}

	public static String map2Json(Map<String, String> map) throws JsonProcessingException {
		return bean2JsonStr(map);
	}
	
	public static String _map2Json(Map<String, String> map) {
		try {
			return map2Json(map);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}



	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			logger.error(ERROR_MESSAGE, sw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}

	/**
	 * 公共毫秒获取时间格式长度
	 * 
	 * @param millis
	 * @return
	 */
	public static String getTimeLenDisplay(long millis) {
		// 求天
		String day = null;
		long dayl = millis / (24 * 60 * 60 * 1000);
		if (dayl < 0)
			dayl = 0;
		if (dayl <= 0) {
			day = "";
		} else {
			day = String.valueOf(dayl) + " ";
		}

		long dayly = millis % (24 * 60 * 60 * 1000);
		// 求小时
		String hh = null;
		long lh = dayly / (60 * 60 * 1000);
		if (lh < 0)
			lh = 0;
		if (lh < 10) {
			hh = "0" + String.valueOf(lh);
		} else
			hh = String.valueOf(lh);

		long lhy = millis % (24 * 60 * 60 * 1000);

		// 求分
		String mm = null;
		long lm = lhy / (60 * 1000);
		if (lm < 0)
			lm = 0;
		if (lm < 10)
			mm = "0" + String.valueOf(lm);
		else
			mm = String.valueOf(lm);

		long lmy = lhy % (60 * 1000);
		// 求秒
		String ss = null;
		long ls = lmy / 1000;
		if (ls < 0)
			ls = 0;
		if (ls < 10)
			ss = "0" + String.valueOf(ls);
		else
			ss = String.valueOf(ls);

		String rt = day + hh + ":" + mm + ":" + ss;

		return rt;
	}

	public static String uuid() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}

	public static Timestamp str2Timestamp(String timestr) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		long ltime = DateTime.parse(timestr, format).getMillis();
		return new Timestamp(ltime);
	}

	public static String timestamp2sqoop(Timestamp time) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTime dateTime = new DateTime(time);
		return dateTime.toString(format);
	}
	
	public static String timestamp2longong(Timestamp time) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dateTime = new DateTime(time);
		return dateTime.toString(format);
	}


	public static String propertiesFile2String(Properties config) {
		ByteArrayOutputStream outProp = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(outProp);
		config.list(out);
		try {
			return outProp.toString("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(ERROR_MESSAGE, e);
			return null;
		}
	}

	public static String toDayBetweenDot(int days) {
		// DateTimeFormatter format = DateTimeFormat .forPattern("yyyy.MM.dd");
		// DateTime toDay = new DateTime();
		// String toDayStr = toDay.toString(format);
		// DateTime btDay = toDay.plusDays(days);
		// String btDayStr = btDay.toString(format);
		// String rt = null;
		// if (days > 0) {
		// rt = toDayStr + "-" + btDayStr;
		// } else {
		// rt = btDayStr + "-" + toDayStr;
		// }
		return "2015.09.15-2015.10.26";
	}

	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	private static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		//System.out.println(l);
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}
}
