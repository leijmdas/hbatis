package org.mybatis.hbatis.core.util;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.hbatis.core.type.JdbcType;
/**
 * 默认java type->jdbc type
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 */
public final class DefaultJdbcTypeRegistry {

	private static final Map<Class<?>, JdbcType> jdbcTypeMap = new HashMap<Class<?>, JdbcType>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5302727745166102178L;

		{
			put(Integer.class, JdbcType.INTEGER);
			put(Long.class, JdbcType.BIGINT);
			put(Short.class, JdbcType.SMALLINT);
			put(Float.class, JdbcType.FLOAT);
			put(Double.class, JdbcType.DOUBLE);
			put(BigDecimal.class, JdbcType.DECIMAL);

			put(String.class, JdbcType.VARCHAR);

			put(Byte.class, JdbcType.TINYINT);

			put(Boolean.class, JdbcType.BOOLEAN);

			put(Date.class, JdbcType.TIMESTAMP);

			put(Blob.class, JdbcType.BLOB);

		}
	};
	
	public static JdbcType getDefaultJdbcType(Class<?> clazz){
		return jdbcTypeMap.get(clazz);
	}
}
