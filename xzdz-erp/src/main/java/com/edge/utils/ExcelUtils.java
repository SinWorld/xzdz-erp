package com.edge.utils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtils {

	/**
	 * @Description: 导出Excel
	 * @param workbook
	 * @param sheetNum   (sheet的位置，0表示第一个表格中的第一个sheet)
	 * @param sheetTitle （sheet的名称）
	 * @param headers    （表格的列标题）
	 * @param result     （表格的数据）
	 * @param out        （输出流）
	 * @throws Exception
	 */
	public void exportExcel(HSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers,
			List<List<String>> result, OutputStream out) throws Exception {

		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetTitle);
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth(20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();

		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setLocked(true); // 设置列的锁定状态为未锁定
		sheet.protectSheet("123456");

		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		// 指定当单元格内容显示不下时自动换行
		style.setWrapText(true);

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);

			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text.toString());
		}
		// 遍历集合数据，产生数据行
		if (result != null) {
			int index = 1;
			for (List<String> m : result) {
				row = sheet.createRow(index);

				for (int i = 0; i < m.size(); i++) {
					HSSFCell cell = row.createCell(i);
					if (i != m.size() - 1) {
						// 将未锁定样式添加至要设置为不可编辑的列。
						row.getCell(i).setCellStyle(style1);
					} else {
						CellStyle ss = workbook.createCellStyle();
						ss.setLocked(false);
						row.getCell(i).setCellStyle(ss);
					}
					String str = m.get(i);
					if (str == null) {
						str = "";
					}
					cell.setCellValue(str);
				}

				index++;
			}
		}

	}

	public void exportExcel(HSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] head0, String[] head1,
			String[] headnum0, String[] headnum1, List<List<String>> apkDate, OutputStream os) {

		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetTitle);
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth(20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();

		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setLocked(true); // 设置列的锁定状态为未锁定
		sheet.protectSheet("123456");
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		// 指定当单元格内容显示不下时自动换行
		style.setWrapText(true);

		// 第一行表头标题
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		for (int i = 0; i < head0.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(head0[i]);
			cell.setCellStyle(style);
		}
		// 动态合并单元格
		for (int i = 0; i < headnum0.length; i++) {
			String[] temp = headnum0[i].split(",");
			Integer startrow = Integer.parseInt(temp[0]);
			Integer overrow = Integer.parseInt(temp[1]);
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);
			sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
		}
		// 设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
		row = sheet.createRow(1);// 因为下标从0开始，所以这里表示的是excel中的第四行
		for (int i = 0; i < head0.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);// 设置excel中第四行的1、2、7、8列的边框
			if (i > 1 && i < 6) {
				for (int j = 0; j < head1.length; j++) {
					cell = row.createCell(j + 2);
					cell.setCellValue(head1[j]);// 给excel中第四行的3、4、5、6列赋值（"温度℃", "湿度%", "温度℃", "湿度%"）
					cell.setCellStyle(style);// 设置excel中第四行的3、4、5、6列的边框
				}
			}
		}
		// 动态合并单元格
		for (int i = 0; i < headnum1.length; i++) {
			String[] temp = headnum1[i].split(",");
			Integer startrow = Integer.parseInt(temp[0]);
			Integer overrow = Integer.parseInt(temp[1]);
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);
			sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
		}

		// 遍历集合数据，产生数据行
		if (apkDate != null) {
			int index = 2;
			for (List<String> m : apkDate) {
				row = sheet.createRow(index);

				for (int i = 0; i < m.size(); i++) {
					cell = row.createCell(i);
					// 将未锁定样式添加至要设置为不可编辑的列。
					row.getCell(i).setCellStyle(style1);
					String str = m.get(i);
					if (str == null) {
						str = "";
					}
					cell.setCellValue(str);
				}

				index++;
			}
		}
		List<PoiModel> poiModels = new ArrayList<PoiModel>();
		int[] mergeIndex = { 0, 1 };
		if (null != workbook) {
			Iterator iterator = apkDate.iterator();
			int index = 2;/* 这里1是从excel的第三行开始，第一二行已经塞入标题了 */
			while (iterator.hasNext()) {
				row = sheet.createRow(index);
				/* 取得当前这行的map，该map中以key，value的形式存着这一行值 */
				List<String> map = (List<String>) iterator.next();
				/* 循环列数，给当前行塞值 */
				// { "漏洞类型", "统计事项", "整体情况", "整体情况", "\"未整改\"漏洞情况",
				// "\"新发现\"漏洞情况"};//在excel中的第3行每列的参数

				for (int i = 0; i < head0.length; i++) {
					String old = "";
					/* old存的是上一行统一位置的单元的值，第一行是最上一行了，所以从第二行开始记 */
					if (index > 2) {
						old = poiModels.get(i) == null ? "" : poiModels.get(i).getContent();
					}
					/* 循环需要合并的列 */
					for (int j = 0; j < mergeIndex.length; j++) {
						if (index == 2) {
							/* 记录第三行的开始行和开始列 */
							PoiModel poiModel = new PoiModel();
							poiModel.setOldContent(map.get(i).toString());
							poiModel.setContent(map.get(i).toString());
							poiModel.setRowIndex(2);
							poiModel.setCellIndex(i);
							poiModels.add(poiModel);
							break;
						} else if (i > 0 && mergeIndex[j] == i) {/* 这边i>0也是因为第一列已经是最前一列了，只能从第二列开始 */
							/* 当前同一列的内容与上一行同一列不同时，把那以上的合并, 或者在当前元素一样的情况下，前一列的元素并不一样，这种情况也合并 */
							/*
							 * 如果不需要考虑当前行与上一行内容相同，但是它们的前一列内容不一样则不合并的情况，把下面条件中||poiModels.get(i).getContent()
							 * .equals(map.get(title[i])) && !poiModels.get(i -
							 * 1).getOldContent().equals(map.get(title[i-1]))去掉就行
							 */
							if (!poiModels.get(i).getContent().equals(map.get(i))
									|| poiModels.get(i).getContent().equals(map.get(i))
											&& !poiModels.get(i - 1).getOldContent().equals(map.get(i - 1))) {
								/* 当前行的当前列与上一行的当前列的内容不一致时，则把当前行以上的合并 */
								CellRangeAddress cra = new CellRangeAddress(poiModels.get(i).getRowIndex()/* 从第二行开始 */,
										index - 1/* 到第几行 */, poiModels.get(i).getCellIndex()/* 从某一列开始 */,
										poiModels.get(i).getCellIndex()/* 到第几列 */);
								// 在sheet里增加合并单元格
								sheet.addMergedRegion(cra);
								/* 重新记录该列的内容为当前内容，行标记改为当前行标记，列标记则为当前列 */
								poiModels.get(i).setContent(map.get(i));
								poiModels.get(i).setRowIndex(index);
								poiModels.get(i).setCellIndex(i);
							}
						}
						/* 处理第一列的情况 */
						if (mergeIndex[j] == i && i == 0 && !poiModels.get(i).getContent().equals(map.get(i))) {
							/* 当前行的当前列与上一行的当前列的内容不一致时，则把当前行以上的合并 */
							CellRangeAddress cra = new CellRangeAddress(poiModels.get(i).getRowIndex()/* 从第二行开始 */,
									index - 1/* 到第几行 */, poiModels.get(i).getCellIndex()/* 从某一列开始 */,
									poiModels.get(i).getCellIndex()/* 到第几列 */);
							// 在sheet里增加合并单元格
							sheet.addMergedRegion(cra);
							/* 重新记录该列的内容为当前内容，行标记改为当前行标记 */
							poiModels.get(i).setContent(map.get(i));
							poiModels.get(i).setRowIndex(index);
							poiModels.get(i).setCellIndex(i);
						}

						/* 最后一行没有后续的行与之比较，所以当到最后一行时则直接合并对应列的相同内容 */
						if (mergeIndex[j] == i && index == apkDate.size() + 1) {
							CellRangeAddress cra = new CellRangeAddress(poiModels.get(i).getRowIndex()/* 从第二行开始 */,
									index/* 到第几行 */, poiModels.get(i).getCellIndex()/* 从某一列开始 */,
									poiModels.get(i).getCellIndex()/* 到第几列 */);
							// 在sheet里增加合并单元格
							sheet.addMergedRegion(cra);
						}
					}
					cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(map.get(i));
					cell.setCellStyle(style1);
					/* 在每一个单元格处理完成后，把这个单元格内容设置为old内容 */
					poiModels.get(i).setOldContent(old);
				}
				index++;
			}
		}

	}
}