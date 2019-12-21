package net.lantrack.framework.common.component.service.impl;

import net.lantrack.framework.common.annotation.ColumnMapping;
import net.lantrack.framework.common.component.dao.IDao;
import net.lantrack.framework.common.component.service.SqlService;
import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.framework.common.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *@Description 自定义Sql操作数据库
 *@Author dahuzi
 *@Date 2019/11/9  14:32
 */
@Service
public class SqlServiceImpl implements SqlService {

	@Autowired
	private IDao iDao;


	@Override
	public void insertSql(String sql) {
		if(StringUtils.isBlank(sql)) {
			return ;
		}
		try {
			iDao.insertSql(sql);
		} catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}
	}

	@Override
	public void updateSql(String sql) {
		if(StringUtils.isBlank(sql)) {
			return ;
		}
		try {
			iDao.updateBySql(sql);
		} catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}
	}

	@Override
	public void deleteSql(String sql) {
		if(StringUtils.isBlank(sql)) {
			return;
		}
		try {
			iDao.deleteBySql(sql);
		} catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}
	}

	@Override
	public <T> T querySingle(String sql, Class<T> clazz) {
		if(StringUtils.isBlank(sql)) {
			return null;
		}
		try {
			Map<String, Object> single = iDao.querySingleBySql(sql);
			return mappingResult(clazz,single);
		} catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}

	}

	@Override
	public <T> List<T> queryList(String sql, Class<T> clazz) {
		if(StringUtils.isBlank(sql)) {
			return null;
		}
		try {
			List<Map<String,Object>> list = iDao.queryListBySql(sql);
			List<T> resultList = new ArrayList<>(list.size());
			for(Map<String, Object> result:list) {
				resultList.add(mappingResult(clazz,result));
			}
			return resultList;
		} catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}

	}
	/**
	 * 将结果集映射到对象
	 * @param clazz
	 * @param resultMap
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @date 2018年7月15日
	 */
	private <T> T mappingResult(Class<T> clazz,Map<String, Object> resultMap) throws InstantiationException, IllegalAccessException {
		T t = clazz.newInstance();
		Field[] declaredFields = clazz.getDeclaredFields();
		for(Field field:declaredFields) {
			String columnName = null;
			ColumnMapping column = field.getAnnotation(ColumnMapping.class);
			if(column!=null) {
				columnName = column.value();
			}else {
				columnName = field.getName();
			}
			if(columnName != null && resultMap.containsKey(columnName)) {
				field.setAccessible(true);
				Object val = resultMap.get(columnName);
				String fieldType = field.getType().getName();
				switch (fieldType) {
				case "java.lang.String":
					field.set(t,val==null?"":val.toString());
					break;
				case "java.lang.Integer":
					field.set(t,val==null?null:Integer.valueOf(val.toString()));
					break;
				case "java.lang.Long":
					field.set(t,val==null?null:Long.valueOf(val.toString()));
				case "java.util.Date":
					field.set(t,val==null?null: DateUtil.parseDate(val));
					break;
				default:
					break;
				}

			}
		}
		return t;
	}


	@Override
	public List<Map<String, Object>> queryListMap(String sql) {
		if(StringUtils.isBlank(sql)) {
			return null;
		}
		try {
			return iDao.queryListBySql(sql);
		} catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> querySingleMap(String sql) {
		if(StringUtils.isBlank(sql)) {
			return null;
		}
		try {
			return iDao.querySingleBySql(sql);
		} catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}
	}


}
