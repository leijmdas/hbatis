package org.mybatis.hbatis.orm.criteria;
/**
 * 分页范围
 * @author zz
 * @date 2015年5月12日
 */
public class PageRange {
	
	/**
	 * 起始
	 */
	private int start;
	/**
	 * 限制大小
	 */
	private int limit;
	
	public PageRange(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
