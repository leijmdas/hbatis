package org.mybatis.hbatis.orm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mybatis.hbatis.orm.sql.KeyGeneratorBuilder;
/**
 * VPM configruation
 * @author zz
 * @date 2014年10月3日
 * @email zhen.zhang@vipshop.com
 */
public class HBatisConfiguration {

	private static Properties props = new Properties();
	static {
		// load config
		InputStream in = null;
		try {
			in = HBatisConfiguration.class.getResourceAsStream("hbatis.properties");
			props.load(in);
		} catch (Exception e) {
			throw new RuntimeException("cannot find config properties:hbatis.properties", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {

			}
		}

	}

	/**
	 * 获取sqlsource class
	 * 
	 * @param dialect
	 * @param methodName
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getSqlSourceClass(String dialect, String methodName) throws ClassNotFoundException {
		String key = "sqlBuilder.%s.%s";
		String className = props.getProperty(String.format(key, dialect, methodName));
		if (className == null) {
			className = props.getProperty(String.format(key, "default", methodName));
		}
		if (className == null) {
			throw new RuntimeException("couldn't find statement sqlsource[dialect:" + dialect + ",methodName:" + methodName + "]");
		}
		return Class.forName(className);

	}
	
	@SuppressWarnings("unchecked")
	public static Class<KeyGeneratorBuilder> getKeyGeneratorBuilder(String dialect) throws ClassNotFoundException{
		String key = "keyBuilder.%s";
		String className = props.getProperty(String.format(key, dialect));
		if (className == null) {
			className = props.getProperty(String.format(key, "default"));
		}
		if (className == null) {
			throw new RuntimeException("couldn't find key builder[dialect:" + dialect+ "]");
		}
		return (Class<KeyGeneratorBuilder>) Class.forName(className);
	}
}
