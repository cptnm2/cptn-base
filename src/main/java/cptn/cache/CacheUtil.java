package cptn.cache;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cptn.util.ProcessResult;

/**
 * 自动查询工具， 自动从缓存中取数据，如果没有数据则直接获取
 * 
 */
public class CacheUtil {
	private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);

	private ICacheHelper ch;

	public CacheUtil(ICacheHelper ch) {
		this.ch = ch;
	}

	/**
	 * 可以选择使用缓存或者不使用
	 * 
	 * @param ob
	 *            需要调用方法的类的实例
	 * @param methodName
	 *            方法名
	 * @param list
	 *            参数集合 Object类型 按顺序传入参数 如果没有参数也要传一个空的list集合
	 * @param key
	 *            存入缓存中的健 String
	 * @param useCache
	 *            是否使用缓存
	 * @throws Exception
	 */
	public ProcessResult doOperationMethod(Object ob, String methodName, List<? extends Object> list, String key, int dura, boolean useCache) {
		ProcessResult pr = new ProcessResult();

		// 如果用缓存就先从缓存里面取
		if (useCache) {
			// 先从缓存里面取数据
			Object result = ch.get(key);
			// 如果能取到就将结果放入结果集返回
			if (null != result) {
				pr.setOK();
				pr.putDefData(result);
				return pr;
			}
		}
		
		// 拿到class对象
		Class<? extends Object> c = ob.getClass();
		@SuppressWarnings("rawtypes")
		Class[] parameterTypes = new Class[list.size()];
		
		// 获得参数Object
		Object[] arguments = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arguments[i] = list.get(i);
			parameterTypes[i] = Transformation(list.get(i));
		}
		
		Method method;
		try {
			method = c.getMethod(methodName, parameterTypes);
			pr.putDefData(method.invoke(ob, arguments));

			// 执行方法将返回值存入缓存
			ch.put(key, pr.getDefData(), dura);
			pr.setOK();
		} catch (Exception e) {
			pr.setErr();
			pr.addMsg(e.getMessage());
			logger.error("未找到执行方法", e.getMessage());
		}

		return pr;
	}

	/**
	 * 使用缓存时调用该方法
	 * 
	 * @param ob
	 *            需要调用方法的类的实例
	 * @param methodName
	 *            方法名
	 * @param list
	 *            参数集合 Object类型 按顺序传入参数 如果没有参数也要传一个空的list集合
	 * @param key
	 *            存入缓存中的健 String
	 * @throws Exception
	 */
	public ProcessResult doOperationMethod(Object ob, String methodName, List<? extends Object> list, String key,
			int dura) {
		return doOperationMethod(ob, methodName, list, key, dura, true);
	}

	/**
	 * 基本类型转换
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Class Transformation(Object object) {
		if (object == null) {
			return null;
		} else if (object.getClass().getName().equals("java.lang.Integer")) {
			return int.class;
		} else if (object.getClass().getName().equals("java.lang.Double")) {
			return double.class;
		} else if (object.getClass().getName().equals("java.lang.Long")) {
			return long.class;
		} else if (object.getClass().getName().equals("java.lang.Short")) {
			return short.class;
		} else if (object.getClass().getName().equals("java.lang.Boolean")) {
			return boolean.class;
		} else if (object.getClass().getName().equals("java.lang.Character")) {
			return char.class;
		} else if (object.getClass().getName().equals("java.lang.Float")) {
			return float.class;
		} else if (object.getClass().getName().equals("java.lang.Byte")) {
			return byte.class;
		} else {
			return object.getClass();
		}
	}
}
