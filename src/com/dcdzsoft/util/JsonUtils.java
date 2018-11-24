package com.dcdzsoft.util;

import javax.sql.RowSet;

import java.math.BigDecimal;
import java.sql.Types;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.lang.reflect.*;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import net.sf.json.util.JSONUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcdzsoft.EduException;
import com.dcdzsoft.constant.BaseErrorCode;
import com.dcdzsoft.sda.db.*;


public class JsonUtils {
	private static final Log log = LogFactory.getLog(JsonUtils.class.getName());

	protected JsonUtils() {
	}

	

	public static String getSuccessJSONStr(String str, Object result) {
		if (StringUtils.isEmpty(str))
			str = "";
		str = encodeQuotedString(str);

		if (result == null)
			result = "";
		result = encodeQuotedString(result.toString());

		String msg = "{\"success\":true,\"data\":{\"msg\":" + str
				+ ",\"result\": " + result + "}}";

		return msg;
	}

	public static String getErrorJSONStr(String str, Throwable e) {
		if (StringUtils.isEmpty(str))
			str = "";
		if (e != null)
			str = encodeQuotedString(str + e.getMessage());
		else
			str = encodeQuotedString(str);

		String msg = "{\"success\":false,\"errors\":{\"msg\":" + str + "}}";

		return msg;
	}

	public static String getJSONFromRowSet(RowSet rowset, int totalCount)
			throws EduException {
		StringBuffer sb = new StringBuffer(8192); // 8k大小
		if (totalCount == 0)
			totalCount = RowSetUtils.getCountOfRowSet(rowset);

		try {
			ResultSetMetaData metaData = rowset.getMetaData();

			/*
			 * sb.append("metaData:{\n");
			 * sb.append("totalProperty:'totalCount'\n");
			 * sb.append(",root:'rows'\n"); sb.append(",fields:[\n");
			 * 
			 * for (int i = 0; i < metaData.getColumnCount(); i++) { String
			 * fieldName = metaData.getColumnName(i + 1); int jdbcType =
			 * metaData.getColumnType(i + 1); int width =
			 * metaData.getPrecision(i + 1); int scale = metaData.getScale(i +
			 * 1);
			 * 
			 * if(i > 0) sb.append(","); sb.append("{name:'" + fieldName + "'");
			 * 
			 * switch (jdbcType) { case Types.CHAR: case Types.VARCHAR: case
			 * Types.LONGVARCHAR: { //avoid delphi throw "Invalid parameter"
			 * exception if (width == 0) width = 1;
			 * 
			 * sb.append(",type:'string'}"); break; } case Types.INTEGER: {
			 * sb.append(",type:'int'}"); break; } case Types.NUMERIC: {
			 * sb.append(",type:'float'}"); break; } default: { throw new
			 * EduException(ErrorCode.ERR_NOTSUPPORTDATATYPE); } }
			 * sb.append("\n"); }
			 * 
			 * sb.append("]},\n");
			 */
			sb.append("{success:true,\n");
			sb.append("totalCount:" + totalCount + "\n");
			sb.append(",rows:[\n");

			int count = 0;
			while (rowset.next()) {
				if (count > 0)
					sb.append(",");
				sb.append("{");
				for (int j = 0; j < metaData.getColumnCount(); j++) {
					String value = "";

					String columnLabel = metaData.getColumnLabel(j + 1);
					String fieldName = metaData.getColumnName(j + 1);
					
					if(StringUtils.isNotEmpty(columnLabel))
						fieldName = columnLabel;
					
					int jdbcType = metaData.getColumnType(j + 1);
					int precision = metaData.getPrecision(j + 1);
					int scale = metaData.getScale(j + 1);

					switch (jdbcType) {
					case Types.NVARCHAR:
					case Types.VARCHAR:
					case Types.CHAR: {
						value = rowset.getString(j + 1);
						if (value == null)
							value = "\"\"";
						else
							value = encodeQuotedString(value);

						break;
					}
					case Types.DATE: {
						java.sql.Date dValue = RowSetUtils.getDateValue(rowset,
								j + 1);
						if (dValue == null)
							value = "\"\"";
						else {
							value = DateUtils.dateToString(dValue, null);
							value = encodeQuotedString(value);
						}

						break;
					}
					case Types.TIMESTAMP: {
						java.sql.Timestamp dValue = RowSetUtils
								.getTimestampValue(rowset, j + 1);
						if (dValue == null)
							value = "\"\"";
						else {
							value = DateUtils.timestampToString(dValue, null);
							value = encodeQuotedString(value);
						}

						break;
					}
					case Types.LONGVARCHAR: // text or long
					case Types.CLOB: {
						value = RowSetUtils.getLongString(rowset, j + 1);

						if (value == null)
							value = "\"\"";
						else
							value = encodeQuotedString(value);

						break;
					}
					case Types.INTEGER:
					case Types.TINYINT:
					case Types.SMALLINT:
					case Types.BIGINT: {
						value = String.valueOf(rowset.getLong(j + 1));
						break;
					}
					case Types.DOUBLE:{
						value = String.valueOf(rowset.getDouble(j + 1));
						break;
					}
					case Types.NUMERIC:
					case Types.DECIMAL: {
						// 视图里面取出来的为何都是零NVL(,0) AS name 就没有precision和scale
						if (precision <= 0 && scale <= 0) {
							try {
								value = String.valueOf(rowset.getLong(j + 1));
							} catch (java.sql.SQLException e) {
								BigDecimal bd = rowset.getBigDecimal(j + 1);
								if (bd != null) {
									// bd = bd.setScale(scale,
									// BigDecimal.ROUND_HALF_UP);

									value = bd.toString();
									// if (StringUtils.isNotEmpty(value))
									// value = value + ".0";
								}
							}
						} else {
							BigDecimal bd = rowset.getBigDecimal(j + 1);
							if (bd != null) {
								bd = bd.setScale(scale,
										BigDecimal.ROUND_HALF_UP);

								value = bd.toString();
								if (StringUtils.isNotEmpty(value) && scale <= 0)
									value = value + ".0";
							}
						}
						break;
					}

					default: {
						// Should never happen
						break;
					}
					}

					if (j > 0)
						sb.append(",");

					sb.append(fieldName.toUpperCase() + ":" + value);
				}

				count++;
				sb.append("}\n");
			}
		} catch (SQLException e) {
			log.error("[sqlcode]:" + e.getErrorCode() + " [errmsg]:"
					+ e.getMessage());
			throw new EduException(BaseErrorCode.ERR_CONVERTXMLDATA);
		} finally {
			RowSetUtils.closeResultSet(rowset);
		}

		sb.append("]\r\n");
		sb.append("}");

		return sb.toString();
	}

	/**
	 * rset 转换为 List
	 * @param rset
	 * @param clazz
	 * @return
	 * @throws EduException
	 */
	public static java.util.List getListFromRowSet(RowSet rset, Class clazz) throws EduException {
    	java.util.List list = new java.util.LinkedList();
    	
    	if(rset == null)
    		return list;
    	
        int count = RowSetUtils.getCountOfRowSet(rset);
        
        Field[] fields = clazz.getFields();
        String fieldName = "";
        Class fieldType = null;
        
        while (RowSetUtils.rowsetNext(rset)) {
        	try
        	{
	        	Object dto = clazz.newInstance();
	            
	           for (int i = 0; i < fields.length; i++) {
	                fieldName = fields[i].getName();
	                fieldType = fields[i].getType();
	
	                if (fieldType == String.class) {
	                    fields[i].set(dto,RowSetUtils.getStringValue(rset,fieldName));
	                }
	                //else if(fieldType == short.class){}
	                else if (fieldType == int.class) {
	                    fields[i].setInt(dto,RowSetUtils.getIntValue(rset,fieldName));
	                } else if (fieldType == long.class) {
	                    fields[i].setLong(dto,RowSetUtils.getLongValue(rset,fieldName));
	                }
	                //else if(fieldType == float.class){}
	                else if (fieldType == double.class) {
	                    fields[i].setDouble(dto,RowSetUtils.getDoubleValue(rset,fieldName));
	                }
	                //else if(fieldType == byte.class){}
	                //else if(fieldType == boolean.class){}
	                else if (fieldType == java.sql.Date.class) {
	                    fields[i].set(dto,RowSetUtils.getDateValue(rset,fieldName));
	                } else if (fieldType == java.sql.Timestamp.class) {
	                    fields[i].set(dto,RowSetUtils.getTimestampValue(rset,fieldName));
	                }
	            }

                list.add(dto);
            } catch (Exception e) {
                System.err.println(fieldName + ":" + e.getMessage());
                e.printStackTrace();
            }
        }

        return list;
    }

	public static String encodeQuotedString(String str) {

		if (StringUtils.isEmpty(str)) {
			return "\"\"";
		}

		char b;
		char c = 0;
		int i;
		int len = str.length();
		StringBuffer sb = new StringBuffer(len + 4);
		String t;

		sb.append('\"');
		for (i = 0; i < len; i += 1) {
			b = c;
			c = str.charAt(i);
			switch (c) {
			case '\\':
			case '"':
				sb.append('\\');
				sb.append(c);
				break;
			case '/':
				if (b == '<') {
					sb.append('\\');
				}
				sb.append(c);
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			default:
				if (c < ' ') {
					t = "000" + Integer.toHexString(c);
					sb.append("\\u").append(t.substring(t.length() - 4));
				} else {
					sb.append(c);
				}
			}
		}
		sb.append('\"');
		return sb.toString();
	}

	/**
	 * 生成c#的日期格式
	 * 
	 * @param value
	 * @return
	 */
	public static String dateToJsonStr(Object value) {
		if (value == null)
			value = DateUtils.getMinDate();

		return "\"/Date(" + ((java.util.Date) value).getTime() + "+0800)/\"";
	}

	public static String jsonStringValue(JSONObject json, String name) {
		String result = "";
		try {
			result = json.getString(name);
		} catch (Exception e) {

		}

		return result;
	}

	public static int jsonIntValue(JSONObject json, String name) {
		int result = 0;
		try {
			result = json.getInt(name);
		} catch (Exception e) {

		}

		return result;
	}

	public static double jsonDoubleValue(JSONObject json, String name) {
		double result = 0D;
		try {
			result = json.getDouble(name);
		} catch (Exception e) {

		}

		return result;
	}

	public static long jsonLongValue(JSONObject json, String name) {
		long result = 0L;
		try {
			result = json.getLong(name);
		} catch (Exception e) {

		}

		return result;
	}

	public static long jsonDateTimeValue(JSONObject json, String name) {
		long result = 0L;
		try {
			String str = json.getString(name);

			if (str.startsWith("/Date(")) {
				int index = str.indexOf('+');
				if(index == -1)
					index = str.indexOf(' ');
				
				if(index != -1){
					String substr = str.substring(6, index);
	
					result = NumberUtils.parseLong(substr);
				}
			} else
				result = json.getLong(name);

		} catch (Exception e) {

		}

		return result;
	}

	/**
	 * json对象转换为Bean
	 * 
	 * @param jsonObject
	 * @param inClass
	 * @return
	 */
	public static Object jsonObjectToBean(JSONObject jsonObject, Class inClass) {
		Object dto = null;

		if (jsonObject == null)
			return dto;

		try {
			dto = inClass.newInstance();

			Field[] fields = inClass.getFields();
			String fieldName = "";
			Class fieldType = null;
			String methodName = "";
			String paramName = "";
			String typeName = "";

			Method method = null;
			for (int i = 0; i < fields.length; i++) {
				fieldName = fields[i].getName();
				fieldType = fields[i].getType();
				methodName = "set" + fieldName;
				paramName = fieldName;

				method = inClass.getMethod(methodName, fieldType);

				// typeName = fieldType.getName();
				if (fieldType == String.class) {
					method.invoke(dto,
							JsonUtils.jsonStringValue(jsonObject, fieldName));
				}
				// else if(fieldType == short.class){}
				else if (fieldType == int.class) {
					method.invoke(
							dto,
							new Integer(JsonUtils.jsonIntValue(jsonObject,
									fieldName)));
				} else if (fieldType == long.class) {
					method.invoke(
							dto,
							new Long(JsonUtils.jsonLongValue(jsonObject,
									fieldName)));
				}
				// else if(fieldType == float.class){}
				else if (fieldType == double.class) {
					method.invoke(
							dto,
							new Double(JsonUtils.jsonDoubleValue(jsonObject,
									fieldName)));
				}
				// else if(fieldType == byte.class){}
				// else if(fieldType == boolean.class){}
				else if (fieldType == java.sql.Date.class) {
					method.invoke(
							dto,
							new java.sql.Date(JsonUtils.jsonDateTimeValue(
									jsonObject, fieldName)));
				} else if (fieldType == java.sql.Timestamp.class) {
					method.invoke(
							dto,
							new java.sql.Timestamp(JsonUtils.jsonDateTimeValue(
									jsonObject, fieldName)));
				}
			}
		} catch (Exception e) {

		}

		return dto;
	}

	/**
	 * json对象转换为public DTO
	 * 
	 * @param jsonObject
	 * @param inClass
	 * @return
	 */
	public static Object jsonObjectToDTO(JSONObject jsonObject, Class inClass) {
		Object dto = null;

		if (jsonObject == null)
			return dto;

		try {
			dto = inClass.newInstance();

			Field[] fields = inClass.getFields();
			String fieldName = "";
			Class fieldType = null;

			for (int i = 0; i < fields.length; i++) {
				fieldName = fields[i].getName();
				fieldType = fields[i].getType();

				if (fieldType == String.class) {
					fields[i].set(dto,
							JsonUtils.jsonStringValue(jsonObject, fieldName));
				}
				// else if(fieldType == short.class){}
				else if (fieldType == int.class) {
					fields[i].setInt(dto,
							JsonUtils.jsonIntValue(jsonObject, fieldName));
				} else if (fieldType == long.class) {
					fields[i].setLong(dto,
							JsonUtils.jsonLongValue(jsonObject, fieldName));
				}
				// else if(fieldType == float.class){}
				else if (fieldType == double.class) {
					fields[i].setDouble(dto,
							JsonUtils.jsonDoubleValue(jsonObject, fieldName));
				}
				// else if(fieldType == byte.class){}
				// else if(fieldType == boolean.class){}
				else if (fieldType == java.sql.Date.class) {
					fields[i].set(
							dto,
							new java.sql.Date(JsonUtils.jsonDateTimeValue(
									jsonObject, fieldName)));
				} else if (fieldType == java.sql.Timestamp.class) {
					fields[i].set(
							dto,
							new java.sql.Timestamp(JsonUtils.jsonDateTimeValue(
									jsonObject, fieldName)));
				}
			}
		} catch (Exception e) {

		}

		return dto;
	}

	/**
	 * json对象转换为List DTO
	 * 
	 * @param jsonObject
	 * @param inClass
	 * @return
	 */
	public static List jsonObjectToListDTO(JSONArray jsonArray, Class inClass) {
		java.util.List dtoList = new java.util.LinkedList();

		if (jsonArray == null)
			return dtoList;

		try {
			Object[] dtoObjs = jsonArray.toArray();
			for (int j = 0; j < dtoObjs.length; j++) {
				JSONObject jsonObject = (JSONObject) dtoObjs[j];
				Object dto = inClass.newInstance();

				Field[] fields = inClass.getFields();
				String fieldName = "";
				Class fieldType = null;
				String methodName = "";
				String paramName = "";
				String typeName = "";

				Method method = null;
				for (int i = 0; i < fields.length; i++) {
					fieldName = fields[i].getName();
					fieldType = fields[i].getType();

					if (fieldType == String.class) {
						fields[i].set(dto, JsonUtils.jsonStringValue(
								jsonObject, fieldName));
					}
					// else if(fieldType == short.class){}
					else if (fieldType == int.class) {
						fields[i].setInt(dto,
								JsonUtils.jsonIntValue(jsonObject, fieldName));
					} else if (fieldType == long.class) {
						fields[i].setLong(dto,
								JsonUtils.jsonLongValue(jsonObject, fieldName));
					}
					// else if(fieldType == float.class){}
					else if (fieldType == double.class) {
						fields[i].setDouble(dto, JsonUtils.jsonDoubleValue(
								jsonObject, fieldName));
					}
					// else if(fieldType == byte.class){}
					// else if(fieldType == boolean.class){}
					else if (fieldType == java.sql.Date.class) {
						fields[i].set(
								dto,
								new java.sql.Date(JsonUtils.jsonDateTimeValue(
										jsonObject, fieldName)));
					} else if (fieldType == java.sql.Timestamp.class) {
						fields[i].set(
								dto,
								new java.sql.Timestamp(JsonUtils
										.jsonDateTimeValue(jsonObject,
												fieldName)));
					}
				}

				dtoList.add(dto);
			}

		} catch (Exception e) {

		}

		return dtoList;
	}

	/**
	 * dto 转换 json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String dtoToJson(Object obj) {
		Class<?> classInfo = obj.getClass();
		Field[] fields = classInfo.getFields();
		String fieldName = "";
		Class<?> fieldType = null;
		Object value = "";

		StringBuffer sb = new StringBuffer(512);
		sb.append("{");

		try {
			for (int i = 0; i < fields.length; i++) {
				fieldName = fields[i].getName();
				fieldType = fields[i].getType();
				value = fields[i].get(obj);

				if (i > 0)
					sb.append(",");

				sb.append("\"" + fieldName + "\":");

				if (fieldType == java.sql.Date.class
						|| fieldType == java.sql.Timestamp.class) {
					sb.append(JsonUtils.dateToJsonStr(value));
				} else {
					sb.append(JSONUtils.valueToString(value));
				}
			}
		} catch (Exception e) {
			log.error("[dtoToJson:]" + e.getMessage());
		}

		sb.append("}");

		return sb.toString();
	}

	

}
