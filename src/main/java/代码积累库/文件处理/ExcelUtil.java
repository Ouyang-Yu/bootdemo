package 代码积累库.文件处理;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * <dependency>
 * <groupId>org.apache.poi</groupId>
 * <artifactId>poi</artifactId>
 * <version>4.1.2</version>
 * </dependency>
 *
 * <dependency>
 * <groupId>org.apache.poi</groupId>
 * <artifactId>poi-ooxml</artifactId>
 * <version>4.1.2</version>
 * </dependency>
 */
@Slf4j
@SuppressWarnings("typo")
public class ExcelUtil {
    /**
     * @param response            响应体
     * @param all                 要写入的集合
     * @param excelName           Excel名字和第一个sheet的名字
     * @param columns             列名
     * @param columnToGetFiledMap 列名与类get方法对应的集合
     * @param <T>                 集合中的类
     */
    @SneakyThrows
    public static <T> void downLoadExcel(HttpServletResponse response, List<T> all, String excelName, String[] columns, HashMap<String, Function<T, Object>> columnToGetFiledMap) {
        // 设置response属性
        String fileNameURL = URLEncoder.encode(excelName + ".xlsx", StandardCharsets.UTF_8);
        // 设置contentType
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // *** 设置header(处理文档名包含中文)
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);

        if (columns == null || columns.length == 0) {
            columns = columnToGetFiledMap.keySet().toArray(new String[0]);
            // 如果前端没有传columns,就把map中取出全部的列给去下载
        }

        HSSFWorkbook workbook = ExcelUtil.createWorkbook("sheet1", excelName, columns);


        HSSFSheet sheet = workbook.getSheetAt(0);
        AtomicInteger index = new AtomicInteger();
        String[] finalColumns = columns;
        all.forEach(item -> {
            index.getAndIncrement();
            HSSFRow row = sheet.createRow(index.get() + 1);// spare for SheetTitle and columnTitle
            for (int i = 0; i < finalColumns.length; i++) {
                int finalI = i;
                Function<T, Object> tObjectFunction = Optional.ofNullable(columnToGetFiledMap.get(finalColumns[i])).orElseThrow(
                        () -> new RuntimeException(MessageFormat.format("找不到{0}列名对应的实体类字段", finalColumns[finalI]))
                );
                Object columnValue = tObjectFunction.apply(item);

                row.createCell(i).setCellValue(new HSSFRichTextString(columnValue == null ?
                                                                      ""
                                                                                          : columnValue.toString()));
            }
        });
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);// 文件写入到输出流
            outputStream.close();
            outputStream.flush();
        } catch (IOException e) {
            log.info("下载数据出错:{}", e.getClass().getName());
        }
    }

    public static HSSFWorkbook createWorkbook(String sheetName, String title, String[] header) {
        if (header == null || header.length == 0) {
            return null;
        }
        if (sheetName == null) {
            sheetName = "sheet1";
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 设置 sheetName
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(18);
        // 标题样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFCellStyle headerStyle1 = workbook.createCellStyle(); {
            headerStyle1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
            headerStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle1.setWrapText(true);
            headerStyle1.setAlignment(HorizontalAlignment.CENTER);
            headerStyle1.setAlignment(HorizontalAlignment.CENTER_SELECTION);
            headerStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        }


        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFCell cell0 = sheet.createRow(0).createCell(0); {
            cell0.setCellValue(title);
            cell0.setCellStyle(headerStyle1);
        }
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, header.length - 1);
        sheet.addMergedRegion(region);


        // 创建标题行
        HSSFRow sheetRow = sheet.createRow(1);
        // 遍历添加表头
        for (int i = 0; i < header.length; i++) {
            // 创建一个单元格
            HSSFCell cell = sheetRow.createCell(i);
            // 创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            // 填充标题
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        return workbook;
    }
}

