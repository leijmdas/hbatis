package org.mybatis.hbatis.orm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.mybatis.hbatis.orm.annotation.EnableEntityResultMapping;
import org.mybatis.hbatis.orm.mapper.HbatisMapper;
import org.mybatis.hbatis.orm.sql.KeyGeneratorBuilder;
import org.mybatis.hbatis.orm.sql.ResultMapsBuilder;
import org.mybatis.hbatis.orm.sql.SqlBuilder;
import org.mybatis.hbatis.orm.sql.TableMappingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 声明创建（实现继承)
 * 
 * @author zz
 * @date 2014年9月8日
 * @email zhen.zhang@vipshop.com
 */
public class HbatisStatementBuilder {

	private static final Logger logger = LoggerFactory.getLogger(HbatisStatementBuilder.class);

	private SqlSourceBuilder sqlSourceBuilder;

	private static final Set<Method> baseMethodSet = new HashSet<Method>();

	/**
	 * 数据库类型
	 */
	private String dialect = "mysql";

	private Collection<Class<?>> mappers;

	private Configuration configuration;
	/**
	 * 模板
	 */
	private Configuration hbatisCfg;

	private static Class<?> baseMapperClass = HbatisMapper.class;

	public HbatisStatementBuilder(Configuration hbatisCfg, Configuration configuration) {
		this.hbatisCfg = hbatisCfg;
		this.configuration = configuration;
		MapperRegistry r = configuration.getMapperRegistry();

		this.mappers = r.getMappers();

	}

	private KeyGeneratorBuilder keyGeneratorBuilder;
	static {
		Method[] methods = baseMapperClass.getMethods();
		for (Method m : methods) {
			baseMethodSet.add(m);
		}
		logger.info("BaseMapper({}) statements:{}", baseMapperClass, baseMethodSet);

	}

	public void initDao() throws Exception {

		if (this.mappers == null || this.mappers.isEmpty())
			return;
		sqlSourceBuilder = new SqlSourceBuilder(configuration);
		// key generator
		Class<KeyGeneratorBuilder> keyBuilderClass = HBatisConfiguration.getKeyGeneratorBuilder(this.dialect);
		this.keyGeneratorBuilder = keyBuilderClass.newInstance();

		// scan package mapper

		for (Class<?> mapperClass : mappers) {

			if (baseMapperClass.isAssignableFrom(mapperClass) && !baseMapperClass.equals(mapperClass)) {
				logger.info("Preprocessing mapper interface:{}", mapperClass);
				addResultMap(mapperClass);
				for (Method m : baseMethodSet) {
					addStatement(mapperClass, m);
				}
			}
		}
	}

	

	private Class<?> getEntityClassByInterface(Class<?> mapperClass) {
		ParameterizedType pt = (ParameterizedType) mapperClass.getGenericInterfaces()[0];

		Class<?> entityClass = (Class<?>) (pt.getActualTypeArguments()[0]);
		return entityClass;
	}

	private Method getMapperMethod(Class<?> mapperClass, Method baseMethod) {
		Method[] methods = mapperClass.getMethods();
		for (int i = 0, len = methods.length; i < len; i++) {
			Method m = methods[i];
			if (m.getName().equals(baseMethod.getName())) {
				return m;
			}
		}
		throw new RuntimeException("method not found");
	}

	private synchronized String addStatement(Class<?> mapperClass, Method m) {
		Method mapperMethod = this.getMapperMethod(mapperClass, m);

		String methodName = mapperMethod.getName();
		// 定义子mapper的statement1
		String statementId = mapperClass.getName() + "." + methodName;

		if (configuration.hasStatement(statementId)) {
			return statementId;
		}
		String baseStatementId = baseMapperClass.getName() + "." + methodName;
		logger.debug("statement id:{},baseStatementId:{}", statementId, baseStatementId);
		MappedStatement mappedSt = null;
		Class<?> entityClass = this.getEntityClassByInterface(mapperClass);
		if (hbatisCfg.hasStatement(baseStatementId)) {
			// 复制声明
			MappedStatement baseStatement = hbatisCfg.getMappedStatement(baseStatementId);

			if (!configuration.hasStatement(statementId)) {
				// 创建新的statement,并封装相应的resultType
				mappedSt = cloneStatement(mapperClass, mapperMethod, entityClass, baseStatement, statementId,
						baseStatementId);

			}
		} else {
			// 通过sql builder创建

			SqlBuilder sqlBuilder;
			try {
				Class<?> sqlSourceClass = HBatisConfiguration.getSqlSourceClass(this.dialect, methodName);
				Constructor<?> constructor = sqlSourceClass.getDeclaredConstructor(SqlSourceBuilder.class, Class.class);
				sqlBuilder = (SqlBuilder) constructor.newInstance(this.sqlSourceBuilder, entityClass);

			} catch (Exception e) {
				throw new RuntimeException("build statement error[id:" + statementId + "]", e);
			}

			mappedSt = this.buildNewStatement(mapperClass, mapperMethod, entityClass, statementId, sqlBuilder);

		}
		if (mappedSt != null) {
			// 添加进configuration即可
			configuration.addMappedStatement(mappedSt);
		}
		return statementId;
	}
	/**
	 * 通过SQL BUILDER创建
	 * @param mapperClass
	 * @param mapperMethod
	 * @param entityClass
	 * @param statementId
	 * @param sqlBuilder
	 * @return
	 */
	private MappedStatement buildNewStatement(Class<?> mapperClass, Method mapperMethod, Class<?> entityClass,
			String statementId, SqlBuilder sqlBuilder) {
		Builder statementBuilder = new Builder(this.configuration, statementId,
				sqlBuilder, sqlBuilder.getSqlCommandType());

		if (sqlBuilder.getResultType() != null && sqlBuilder.getResultMappingList() != null) {
			List<ResultMap> resultMaps = new ArrayList<ResultMap>();
			ResultMap.Builder resultMapBuilder = new ResultMap.Builder(configuration, statementBuilder.id() + "_Inline",
					sqlBuilder.getResultType(), sqlBuilder.getResultMappingList());
			resultMaps.add(resultMapBuilder.build());
			statementBuilder.resultMaps(resultMaps);
		}
		if (SqlCommandType.INSERT.equals(sqlBuilder.getSqlCommandType())) {
			keyGeneratorBuilder.build(statementBuilder, entityClass);
		}

		return statementBuilder.build();
	}
	/**
	 * 通过已声明创建
	 * @param mapperClass
	 * @param method
	 * @param entityClass
	 * @param baseStatement
	 * @param statementId
	 * @param baseStatementId
	 * @return
	 */
	private MappedStatement cloneStatement(Class<?> mapperClass, Method method, Class<?> entityClass,
			MappedStatement baseStatement, String statementId, String baseStatementId) {
		Builder statementBuilder = new Builder(baseStatement.getConfiguration(),
				statementId, baseStatement.getSqlSource(), baseStatement.getSqlCommandType());
		statementBuilder.resultMaps(baseStatement.getResultMaps());

		this.setResultMap(statementBuilder, mapperClass, method);

		return statementBuilder.build();
	}
	
	private void addResultMap(Class<?> mapperClass) {
		Class<?> entityClass = this.getEntityClassByInterface(mapperClass);
		String resultMapId = mapperClass.getName() + "._ResultMap";
		if (!configuration.hasResultMap(resultMapId)) {
			List<ResultMapping> resultMappings = ResultMapsBuilder.buildResultMappings(configuration,
					TableMappingUtil.getEntityMapping(entityClass));

			ResultMap.Builder builder = new ResultMap.Builder(configuration, resultMapId, entityClass, resultMappings);
			ResultMap rm = builder.build();
			configuration.addResultMap(rm);
		}
	}
	private void setResultMap(Builder statementBuilder, Class<?> mapperClass, Method m) {
		if (m.getAnnotation(EnableEntityResultMapping.class) != null) {
			String resultMapId = mapperClass.getName() + "._ResultMap";
			statementBuilder.resultMaps(Arrays.asList(configuration.getResultMap(resultMapId)));
		}
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

}
