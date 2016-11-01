package com.mo9.util;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;


/**
 * json转换工具类
 * 
 * @author Jian
 * @date 2012-8-17
 */
public class JsonUtil {
	private static String[] DATE_FORMATS = new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd" };

	public static String toJson(Object obj) {
		String jsonString = null;
		if (obj != null) {
			if (obj instanceof List) {
				jsonString = list2Json((List) obj);
			} else {
				jsonString = obj2Json(obj);
			}
		}

		return jsonString;
	}

	public static String list2Json(final List<?> list) {
		return list2Json(list, null, new String[0], null);
	}

	public static String list2Json(final List<?> list, String[] excludes) {
		return list2Json(list, null, excludes, null);
	}

	public static String list2Json(final List<?> list, String[] excludes, Set<String> codeFields) {
		return list2Json(list, null, excludes, codeFields);
	}

	/**
	 * 根据List自动生成jquery_easyui_grid所需的json数据
	 * 
	 * @param list
	 *            列表数据
	 * @param dateformat
	 *            日期格式化
	 * @param excludes
	 *            不需要生成json的字段名列表(如果包含one-2-many或者many-2-one的字段一定要包含在excludes里)
	 */
	private static String list2Json(final List<?> list, String dateformat, String[] excludes, Set<String> codeFields) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("rows", list);
		return obj2Json(map, dateformat, excludes, codeFields);
	}

	public static String array2Json(final Object obj) {
		return array2Json(obj, null, new String[0]);
	}

	public static String array2Json(final Object obj, String dateformat) {
		return array2Json(obj, dateformat, new String[0]);
	}

	public static String array2Json(final Object obj, String dateformat, String[] excludes) {
		JsonConfig config = getJsonConfig(excludes, dateformat, null);
		String jsonString = JSONArray.fromObject(obj, config).toString();
		return jsonString;
	}

	public static String obj2Json(final Object obj) {
		return obj2Json(obj, null, null, null);
	}

	public static String obj2Json(final Object obj, String[] excludes) {
		return obj2Json(obj, null, excludes, null);
	}

	public static String obj2Json(final Object obj, String[] excludes, Set<String> codeFields) {
		return obj2Json(obj, null, excludes, codeFields);
	}

	private static String obj2Json(final Object obj, String dateformat, String[] excludes, Set<String> codeFields) {
		JsonConfig config = getJsonConfig(excludes, dateformat, codeFields);
		if (obj instanceof List) {
			return JSONArray.fromObject(obj, config).toString();
		}
		JSONObject jsonObject = JSONObject.fromObject(obj, config);
		Set set = jsonObject.entrySet();
		List<String> lstRemoveKey = new ArrayList<String>();
		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry<String, Object> map = (Map.Entry<String, Object>) it.next();
			if (map.getValue() instanceof JSONNull
					|| (map.getValue() instanceof String && StringUtils.isBlank((String) map.getValue()))) {
				lstRemoveKey.add(map.getKey());
			}
		}
		for (String key : lstRemoveKey) {
			jsonObject.remove(key);
		}
		String jsonString = jsonObject.toString();
		return jsonString;
	}

	/**
	 * 把jsonString转成对象
	 * 
	 * @param clazz
	 * @param jsonString
	 * @param root
	 * @return
	 */
	public static List<?> json2List(Class<?> clazz, String jsonString, String root) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray = jsonObject.getJSONArray(root);
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(DATE_FORMATS));
		return JSONArray.toList(jsonArray, clazz);
	}

	public static List<?> json2List(Class<?> clazz, String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(DATE_FORMATS));
		return JSONArray.toList(jsonArray, clazz);
	}

	/**
	 * 把jsonString转成对象
	 * 
	 * @param <T>
	 * @param clazz
	 * @param jsonString
	 * @return
	 */
	public static <T> T json2Object(Class<T> clazz, String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(DATE_FORMATS));
		return (T) JSONObject.toBean(jsonObject, clazz);
	}

	private static JsonConfig getJsonConfig(final String[] excludes, String dateformat, Set<String> codeFields) {
		JsonConfig config = new JsonConfig();
		if (excludes != null && excludes.length > 0) {
			config.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					boolean isExcluede = false;
					for (String exclude : excludes) {
						if (name.equals(exclude)) {
							isExcluede = true;
							break;
						}
					}
					return isExcluede;
				}
			});
		}
		config.setAllowNonStringKeys(false);
		config.setExcludes(excludes);
//		config.setIgnoreTransientFields(true);//如果打开，继承的类会抛异常
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(dateformat));
		config.registerJsonValueProcessor(Boolean.class, new BooleanJsonValueProcessor());
		config.registerJsonValueProcessor(String.class, new Code2NameJsonValueProcessor(codeFields));
		return config;

	}

	/**
	 * 注册日期格式化类
	 * 
	 * @author user
	 * 
	 */
	public static class DateJsonValueProcessor implements JsonValueProcessor {

		private DateFormat dateFormat;

		/**
		 * 构造方法.
		 * 
		 * @param datePattern
		 *            日期格式
		 */
		public DateJsonValueProcessor(String datePattern) {
			if (datePattern != null) {
				try {
					dateFormat = new SimpleDateFormat(datePattern);
				} catch (Exception ex) {
					dateFormat = new SimpleDateFormat(DateOperator.FORMAT_STR);
				}
			}
		}

		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			if (null == value)
				return "";
			return process(value);
		}

		public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
			if (null == value)
				return "";
			return process(value);
		}

		private Object process(Object value) {
			Date date = (Date) value;
			String strContent = null;
			if (dateFormat == null) {
				boolean isTime = DateOperator.isTime(date);
				if (isTime) {
					strContent = DateOperator.formatDate(date, DateOperator.FORMAT_STR_WITH_TIME);
				} else {
					strContent = DateOperator.formatDate(date);
				}
			} else {
				strContent = dateFormat.format(date);
			}
			return strContent;
		}

	}

	public static class BooleanJsonValueProcessor implements JsonValueProcessor {

		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			if (null == value)
				return "";
			return process(value);
		}

		public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
			if (null == value)
				return "";
			return process(value);
		}

		private Object process(Object value) {
			return BooleanUtils.toStringTrueFalse((Boolean) value);
		}

	}

	public static class Code2NameJsonValueProcessor implements JsonValueProcessor {
		private Set<String> fields = null;

		public Code2NameJsonValueProcessor(Set<String> fields) {
			this.fields = fields;
		}

		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			if (null == value)
				return "";
			return value;
		}

		public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
			if (StringUtils.isBlank((String) value))
				return "";
			return process(key, (String) value);
		}

		private Object process(String key, String value) {
			String rtnValue = value;
			if (fields != null) {
				if (fields.contains(key)) {

				}
			}
			return rtnValue;
		}

	}

	// ------EasyUI专用方法和常量-----//

	public static String JSON_NAME = "_easy_grid";

	public static String INSERT_RECORDS_KEY = "_inserted";

	public static String UPDATE_RECORDS_KEY = "_updated";

	public static String DELETE_RECORDS_KEY = "_deleted";

	public static <T> List<T> getInsertRecords(Class<T> clazz, HttpServletRequest request) {
		return getJsonObject(clazz, INSERT_RECORDS_KEY, request);
	}

	public static <T> List<T> getUpdatedRecords(Class<T> clazz, HttpServletRequest request) {
		return getJsonObject(clazz, UPDATE_RECORDS_KEY, request);
	}

	public static <T> List<T> getDeletedRecords(Class<T> clazz, HttpServletRequest request) {
		return getJsonObject(clazz, DELETE_RECORDS_KEY, request);
	}

	@SuppressWarnings({ "deprecation" })
	private static <T> List<T> getJsonObject(Class<T> clazz, String key, HttpServletRequest request) {

		String json = request.getParameter(JSON_NAME);
		JSONObject jsonObject = JSONObject.fromObject(json);
		if ( jsonObject != null ) {
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(DATE_FORMATS));
			return JSONArray.toList(jsonArray, clazz);
		} else
			return new ArrayList<T>(0);
	}

	/**
	 * 返回成功信息 {'success':successInfo}
	 * 
	 * @param successInfo
	 */
	public static void renderSuccess(String successInfo, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", successInfo);
		RenderUtil.renderJson(map, response);
	}

	/**
	 * 返回失败信息 {'failure':failInfo}
	 * 
	 * @param failInfo
	 */
	public static void renderFailure(String failInfo, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("failure", failInfo);
		RenderUtil.renderJson(map, response);
	}

	public static void renderJson(HttpServletResponse response, final Page<?> page) {
		renderJson(response, page, null, new String[0], null,null);
	}

	public static void renderJson(HttpServletResponse response, final Page<?> page, Map<String, Object> footer) {

		renderJson(response, page, null, new String[0], null, footer);
	}

	public static void renderJson(HttpServletResponse response, final Page<?> page, Set<String> codeFields) {
		renderJson(response, page, null, new String[0], codeFields,null);
	}

	public static void renderJson(HttpServletResponse response, final Page<?> page, String[] excludes) {
		renderJson(response, page, null, excludes, null,null);
	}

	public static void renderJson(HttpServletResponse response, final Page<?> page, String[] excludes,
			Map<String, Object> footer) {
		renderJson(response, page, excludes, footer, null);
	}
	
	public static void renderJson(HttpServletResponse response, final Page<?> page, String[] excludes,
			Map<String, Object> footer,String dateformat) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());
		map.put("footer", new Map[] { footer });
		renderDataJson(response, map, dateformat, excludes, null);
	}

	public static void renderJson(HttpServletResponse response, final Page<?> page, String[] excludes,
			Set<String> codeFields) {
		renderJson(response, page, null, excludes, codeFields,null);
	}

	/**
	 * 根据Page自动生成jquery_easyui_grid所需的json数据，包含分页信息
	 * 
	 * @param page
	 *            翻页信息
	 * @param dateformat
	 *            日期格式化
	 * @param excludes
	 *            不需要生成json的字段名列表(如果包含one-2-many或者many-2-one的字段一定要包含在excludes里)
	 */
	private static void renderJson(HttpServletResponse response, final Page<?> page, String dateformat,
			String[] excludes, Set<String> codeFields, Map<String, Object> footer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());
		if (footer != null && footer.size() > 0) {
			map.put("footer", new Map[] { footer });
		}
		renderDataJson(response, map, dateformat, excludes, codeFields);
	}

	private static void renderDataJson(HttpServletResponse response, final Object obj, String dateformat,
			String[] excludes, Set<String> codeFields) {
		JsonConfig config = getJsonConfig(excludes, dateformat, codeFields);
		String jsonString = JSONObject.fromObject(obj, config).toString();
		RenderUtil.renderText(jsonString, response);
	}

	public static void renderListJson(HttpServletResponse response, final List<?> list) {
		renderListJson(response, list, null, new String[0], null, null);
	}

	public static void renderListJson(HttpServletResponse response, final List<?> list, Map<String, Object> footer) {
		renderListJson(response, list, null, new String[0], null, footer);
	}
	public static void renderListJson(HttpServletResponse response, final List<?> list, Map<String, Object> footer, String[] excludes) {
		renderListJson(response, list, null, excludes, null, footer);
	}

	public static void renderListJson(HttpServletResponse response, final List<?> list, String[] excludes) {
		renderListJson(response, list, null, excludes, null, null);
	}

	public static void renderListJson(HttpServletResponse response, final List<?> list, String[] excludes,
			Set<String> codeFields) {
		renderListJson(response, list, null, excludes, codeFields, null);
	}

	/**
	 * 根据List自动生成jquery_easyui_grid所需的json数据
	 * 
	 * @param list
	 *            列表数据
	 * @param dateformat
	 *            日期格式化
	 * @param excludes
	 *            不需要生成json的字段名列表(如果包含one-2-many或者many-2-one的字段一定要包含在excludes里)
	 */
	private static void renderListJson(HttpServletResponse response, final List<?> list, String dateformat,
			String[] excludes, Set<String> codeFields, Map<String, Object> footer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("rows", list);
		if (footer != null && footer.size() > 0) {
			map.put("footer", new Map[] { footer });
		}
		renderDataJson(response, map, dateformat, excludes, codeFields);
	}

	// -------end----------//

	public static void main(String[] args) {
		/*System.out.println("aaaaaaaaaaaaaa");
		String str = "asdfffffffffffffff";
		str = obj2Json(str);
		System.out.println(str);*/
		
		String abc = "{\"partner_id\":\"MPA20150729000001\", \"merch_id\":\"MC20150729047714\", \"c_merch_id\":\"2\", \"trans_id\":\"MPT201602198420337\", \"amount\":\"1\",\"out_trade_no\":\"abed12312329\", \"trans_status\":\"200\", \"payment_type\":\"2\", \"attach\":\"\", \"subject\":\"abc\", \"body\":\"\", \"currency_type\":\"1\",\"result\":\"0\", \"res_info\":\"ok\", \"sign\":\"605d92878f92b0a57e14887e8e379130\"}";
		Map<String, String> params = JsonUtil.parseJSON2Map(abc);
		System.out.println(params.get("sign"));

	}
	
	public static Map<String, String> parseJSON2Map(String jsonStr){  
        Map<String, String> map = new HashMap<String, String>();  
        //最外层解析  
        JSONObject json = JSONObject.fromObject(jsonStr);  
        for(Object k : json.keySet()){  
            Object v = json.get(k);   
            map.put(k.toString(), v.toString());
        }  
        return map;  
    }  
}
