package com.dcdzsoft.print;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.TimeZone;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.math.BigDecimal;
import java.util.Calendar;

import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;

import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.util.StringUtils;
import com.dcdzsoft.util.NumberUtils;
import com.dcdzsoft.util.DateUtils;
import com.dcdzsoft.EduException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title: 智能自助包裹柜系统
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * <p>
 * Company: 杭州东城电子有限公司
 * </p>
 * 
 * @author wangzl
 * @version 1.0
 */

public class PrintUtils {
	/**
     * 输入日期格式为YYYYMMDD
     * @param date String
     * @return String
     */
    public static String formatTime(java.sql.Timestamp time)
    {
    	if(time == null)
            return "";

        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        
        return sdf.format(time);
    }
    
	public static Map initReportParameter(ResultSet rowset, String printType) throws EduException {
		Map parameters = new HashMap();

		parameters.put("printType", printType);
		if (rowset == null)
			return parameters;

		try {
			ResultSetMetaData metaData = rowset.getMetaData();

			while (rowset.next()) {
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
					case Types.VARCHAR:
					case Types.CHAR:
					case Types.DATE:
					case Types.TIMESTAMP:

					{
						value = rowset.getString(j + 1);
						if (value == null)
							value = "";
					}
					case Types.LONGVARCHAR: // text or long
					{
						value = RowSetUtils.getLongString(rowset, j + 1);

						if (value == null)
							value = "";

						break;
					}
					case Types.INTEGER:
					case Types.TINYINT:
					case Types.SMALLINT:
					case Types.BIGINT: {
						value = String.valueOf(rowset.getInt(j + 1));
						break;
					}
					case Types.NUMERIC:
					case Types.DECIMAL: {
						if (precision <= 0 && scale <= 0) {
							try {
								value = String.valueOf(rowset.getInt(j + 1));
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

					parameters.put(fieldName, value);
				}

			}
		} catch (SQLException e) {
			throw new EduException(e.getMessage());
		}

		return parameters;
	}

	public static Map initReportParameter(HttpServletRequest request,
			String printType) throws EduException {
		Map parameters = new HashMap();

		parameters.put("printType", printType);

		Enumeration enumNames = request.getParameterNames();
		while (enumNames.hasMoreElements()) {
			String name = enumNames.nextElement().toString().trim();
			String value = request.getParameter(name);
			if (value == null)
				value = "";

			parameters.put(name.toUpperCase(), value);
		}

		return parameters;
	}

	public static byte[] exportToPdf(ResultSet rset, Map parameters,
			String reportFilePath) throws EduException {
		byte[] bytes = new byte[0];
		try {
			if (rset == null) {
				JREmptyDataSource emptySource = new JREmptyDataSource(0);
				bytes = JasperRunManager.runReportToPdf(reportFilePath,
						parameters, emptySource);

			} else {
				PrintData pData = new PrintData(rset);
				bytes = JasperRunManager.runReportToPdf(reportFilePath,
						parameters, pData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new EduException(e.getMessage());
		} catch (JRException e) {
			e.printStackTrace();
			throw new EduException(e.getMessage());
		}

		return bytes;
	}

	public static byte[] exportToHtml(ResultSet rset, Map parameters,
			String reportFilePath) throws EduException {
		byte[] bytes = new byte[0];
		try {
			JRHtmlExporter exporter = new JRHtmlExporter();
			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			JasperPrint jasperPrint = null;

			if (rset == null) {
				JREmptyDataSource emptySource = new JREmptyDataSource(0);
				jasperPrint = JasperFillManager.fillReport(reportFilePath,
						parameters, emptySource);
			} else {
				PrintData pData = new PrintData(rset);
				jasperPrint = JasperFillManager.fillReport(reportFilePath,
						parameters, pData);
			}

			// 翻页的处理（style='page-break-before:always;）；
			exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
					"<br style='page-break-before:always;'>");
			exporter.setParameter(
					JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
					Boolean.FALSE);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
					"UTF-8");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);

			// // 注意图片的插入（JRHtmlExporterParameter.IMAGES_MAP）；
			// Map imagesMap = new HashMap();
			// session.setAttribute("IMAGES_MAP", imagesMap);
			// exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP,
			// imagesMap);
			// exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
			// "/servlet/reports.Image?image=");

			exporter.exportReport();
			bytes = oStream.toByteArray();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new EduException(e.getMessage());
		} catch (JRException e) {
			e.printStackTrace();
			throw new EduException(e.getMessage());
		}

		return bytes;
	}

	public static byte[] exportToXls(ResultSet rset, Map parameters,String reportFilePath) throws EduException {
		byte[] bytes = new byte[0];
		JRXlsExporter exporter = new JRXlsExporter(); // Excel
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();

		try {
			JasperReport myrpt = (JasperReport) JRLoader.loadObject(reportFilePath);

			// use reflect to set the private field of JasperReport
			// no margin for excel format, max pageheight
			// Field getDeclaredField(String name) -- 获得类声明的命名的字段(不搜素父类)
			java.lang.reflect.Field margin = JRBaseReport.class.getDeclaredField("leftMargin");
			margin.setAccessible(true);
			margin.setInt(myrpt, 0);

			margin = JRBaseReport.class.getDeclaredField("rightMargin");
			margin.setAccessible(true);
			margin.setInt(myrpt, 0);

			margin = JRBaseReport.class.getDeclaredField("topMargin");
			margin.setAccessible(true);
			margin.setInt(myrpt, 0);

			margin = JRBaseReport.class.getDeclaredField("bottomMargin");
			margin.setAccessible(true);
			margin.setInt(myrpt, 0);

			java.lang.reflect.Field pageheight = JRBaseReport.class.getDeclaredField("pageHeight");
			pageheight.setAccessible(true);
			pageheight.setInt(myrpt, Integer.MAX_VALUE);

			// dont print group header on each page
			if (null != myrpt.getGroups()) {
				for (int i = 0; i < myrpt.getGroups().length; i++) {
					myrpt.getGroups()[i].setReprintHeaderOnEachPage(false);
				}
			}

			JasperPrint jasperPrint = null;
			if (rset == null) {
				JREmptyDataSource emptySource = new JREmptyDataSource(0);
				jasperPrint = JasperFillManager.fillReport(myrpt, parameters,
						emptySource);
			} else {
				PrintData pData = new PrintData(rset);
				jasperPrint = JasperFillManager.fillReport(myrpt, parameters,
						pData);
			}

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
			exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE,true);

			// 让Excel看起来整齐 不要有空白 首先把所有的Field设成一样高, 对齐! 把所在Band的高度也设成和Field一样高,
			// 让Field正好放入Band. 然后调整Field的宽度, 让每个Field都相邻,没有空隙
			exporter.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			// ????
			exporter.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
					Boolean.TRUE);

			// 保留GridLine 把每个Field或者Static Text框的''Transparent''属性都勾上
			// if("true".equalsIgnoreCase(displayLine))
			exporter.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.TRUE);

			// exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,Boolean.TRUE);
			// exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED,Boolean.FALSE);

			exporter.exportReport();

			bytes = oStream.toByteArray();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new EduException(e.getMessage());
		} catch (JRException e) {
			e.printStackTrace();
			throw new EduException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new EduException(e.getMessage());
		}

		return bytes;
	}
}
