package org.mybatis.hbatis.spring;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.hbatis.orm.HbatisStatementBuilder;
import org.mybatis.hbatis.orm.mapper.HbatisMapper;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SqlSessionDaoSupport
 * 
 * @author zhen.zhang
 * @date 2014年12月7日
 */
public class HbatisSqlSessionDaoSupport extends SqlSessionDaoSupport {

	private static Logger logger = LoggerFactory.getLogger(HbatisSqlSessionDaoSupport.class);

	private static Configuration hbatisCfg;

	private List<SqlSessionFactory> factorys;

	public static boolean VPM_STATEMENT_INITILIZED = false;

	public HbatisSqlSessionDaoSupport() {
		factorys = Arrays.asList();
	}

	public HbatisSqlSessionDaoSupport(SqlSessionFactory... factory) {
		factorys = Arrays.asList(factory);
	}

	public Configuration getHbatisCfg() {
		return hbatisCfg == null ? this.getSqlSession().getConfiguration() : hbatisCfg;

	}

	private static synchronized void initHbatisStatements(Configuration cfg) throws Exception {
		if (!cfg.hasMapper(HbatisMapper.class)) {
			cfg.addMapper(HbatisMapper.class);
		}
		HbatisStatementBuilder builder = new HbatisStatementBuilder(hbatisCfg, cfg);
		builder.initDao();

	}

	public static void setHbatisSessionFactory(SqlSessionFactory sessionFactory) throws Exception {
		hbatisCfg = sessionFactory.getConfiguration();
		initHbatisStatements(hbatisCfg);
	}

	@Override
	protected void initDao() throws Exception {
		if (!VPM_STATEMENT_INITILIZED) {
			logger.info("Init sqlSessionFactory");

			initHbatisStatements(this.getHbatisCfg());

			VPM_STATEMENT_INITILIZED = true;
		}
		for (SqlSessionFactory f : factorys) {
			logger.info("Preprocess sqlSessionFactory {}", f.toString());
			Configuration cfg = f.getConfiguration();
			HbatisStatementBuilder builder = new HbatisStatementBuilder(this.getHbatisCfg(), cfg);
			builder.initDao();
		}

	}

}
