package org.mybatis.hbatis.core;


import java.util.List;

/**
 * 约束接口
 * @author zz
 * @date 2014年7月31日
 * @param <E,T>
 */
public interface Restriction<E,T> {
	/**
	 * 等于
	 * @param value
	 * @return
	 */
	Criterion<E,T> eq(T value);
	/**
	 * 不等于
	 * @param value
	 * @return
	 */
	Criterion<E,T> ne(T value);
	/**
	 * 大于
	 * @param value
	 * @return
	 */
	Criterion<E,T> gt(T value);
	/**
	 * 大于或等于
	 * @param value
	 * @return
	 */
	Criterion<E,T> ge(T value);
	/**
	 * 小于
	 * @param value
	 * @return
	 */
	Criterion<E,T> lt(T value);
	/**
	 * 小于等于
	 * @param value
	 * @return
	 */
	Criterion<E,T> le(T value);
	
	/**
	 * 是空值
	 * @return
	 */
	Criterion<E,T> isNull();
	/**
	 * 非空值
	 * @return
	 */
	Criterion<E,T> isNotNull();
	/**
	 * 在两者之间(>= and <=)
	 * @param value1
	 * @param value2
	 * @return
	 */
	Criterion<E,T> between(T value1, T value2);
	/**
	 * 在两者之外
	 * @param value1
	 * @param value2
	 * @return
	 */
	Criterion<E,T> notBetween(T value1, T value2);
	/**
	 * 在列表中
	 * @param list
	 * @return
	 */
	Criterion<E,T> in(List<T> list);
	/**
	 * 不在列表中
	 * @param list
	 * @return
	 */
	Criterion<E,T> notin(List<T> list);
	
	/**
	 * like
	 * @param list
	 * @return
	 */
	Criterion<E,T> like(T value);
	
	/**
	 * notlike
	 * @param list
	 * @return
	 */
	Criterion<E,T> notlike(T value);
	
}
