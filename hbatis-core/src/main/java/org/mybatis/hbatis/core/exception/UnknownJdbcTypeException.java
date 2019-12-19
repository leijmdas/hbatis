package org.mybatis.hbatis.core.exception;

public class UnknownJdbcTypeException extends RuntimeException {
	
	private Class<?> jdbcType;
	public UnknownJdbcTypeException(Class<?> clazz) {
		this.jdbcType = clazz;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Class<?> getJdbcType() {
		return jdbcType;
	}

}
