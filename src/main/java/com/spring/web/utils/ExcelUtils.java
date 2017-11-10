/**
 * Copyright 2015-2099 the original author or authors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.spring.web.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sample Code
 *
 * @since 1.2.6
 * @since JDK 1.7
 * @version %version%
 * @author 782372
 * @see
 * @category analysis
 * @serial exclude
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static void exportExcel(OutputStream out, List<List<String>> objects) throws Exception {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet();


        Map<String, HSSFCellStyle> styles = createStyles(hssfWorkbook);

        // 表格header
        HSSFRow titleRowFirst = sheet.createRow(0);
        int rowFirstcolumnNum = 0;
        for (String kpiName : objects.get(0)) {
            titleRowFirst.setHeight((short) (2 * 256));
            HSSFCell cellTitle = titleRowFirst.createCell(rowFirstcolumnNum++);
            cellTitle.setCellType(HSSFCell.CELL_TYPE_STRING);
            cellTitle.setCellStyle(styles.get("cell_header_title"));
            cellTitle.setCellValue(kpiName);
        }
        // 表格内容
        for (int i = 1; i < objects.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            List<String> rowStr = objects.get(i);
            int index = 0;
            for (String aRowStr : rowStr) {
                HSSFCell first = row.createCell(index++);
                first.setCellType(HSSFCell.CELL_TYPE_STRING);
                first.setCellStyle(styles.get("cell_data_default"));
                first.setCellValue(aRowStr);
            }
        }
        try {
            hssfWorkbook.write(out);
        } catch (IOException e) {
            logger.debug("输出流异常");
            throw new Exception(e);
        }
    }

    public static Map<String, HSSFCellStyle> createStyles(HSSFWorkbook wb) {
        Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();

        // ----------------------标题样式---------------------------
        HSSFCellStyle cell_header_title = wb.createCellStyle();
        HSSFFont font_header_title = wb.createFont();
        font_header_title.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体
        font_header_title.setFontHeight((short) (12 * 20));
        font_header_title.setFontName("Times New Roman");// 字体样式
        cell_header_title.setFont(font_header_title);
        cell_header_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        cell_header_title.setWrapText(true);
        cell_header_title.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // cell_header_title.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        cell_header_title.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        cell_header_title.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cell_header_title.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        cell_header_title.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        cell_header_title.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        styles.put("cell_header_title", cell_header_title);

        // -----------------------设置字符样式---------------------------

        HSSFCellStyle cell_data_default = wb.createCellStyle();
        HSSFFont font_data_default = wb.createFont();
        font_data_default.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font_data_default.setFontHeight((short) (11 * 20));
        font_data_default.setFontName("Times New Roman");// 字体样式
        cell_data_default.setFont(font_data_default);
        cell_data_default.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        styles.put("cell_data_default", cell_data_default);

        return styles;
    }

}
