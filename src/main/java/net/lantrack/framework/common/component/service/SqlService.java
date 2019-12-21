package net.lantrack.framework.common.component.service;

import java.util.List;
import java.util.Map;

/**
 * 自定义sql
 * @author lin
 */
public interface SqlService {

	/**
	 * 查询多个结果
	 * @param sql
	 * @return
	 */
	List<Map<String, Object>> queryListMap(String sql);

	/**
	 * 查询单个结果
	 * @param sql
	 * @return
	 */
	Map<String, Object> querySingleMap(String sql);

	/**
	 * 查询单个对象
	 * @param sql
	 * @param clazz
	 * @return
	 */
	<T>T querySingle(String sql, Class<T> clazz);

	/**
	 * 查询list
	 * @param sql
	 * @param clazz
	 * @return
	 */
	<T> List<T> queryList(String sql, Class<T> clazz);

	/**
	 * sql语句更新
	 * @param sql
	 */
	void updateSql(String sql);

	/**
	 * sql删除
	 * @param sql
	 */
	void deleteSql(String sql);

	/**
	 * sql插入
	 * @param sql
	 */
	void insertSql(String sql);

}
