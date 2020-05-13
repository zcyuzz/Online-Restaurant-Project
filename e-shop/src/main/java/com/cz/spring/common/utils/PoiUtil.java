package com.cz.spring.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


public class PoiUtil {


    public static void exportData(List<String[]> data, String fileName, HttpServletResponse response) throws IOException {
        exportData(exportData(data), fileName, response);
    }

 
    public static void exportData(HSSFWorkbook wb, String fileName, HttpServletResponse response) throws IOException {
        response.setContentType("application/force-download");
        String newName;
        try {
            newName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            newName = fileName;
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + newName + ".xls");
        wb.write(response.getOutputStream());
    }

  
    public static HSSFWorkbook exportData(List<String[]> data) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet0");
        for (int i = 0; i < data.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            for (int j = 0; j < data.get(i).length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(data.get(i)[j]);
            }
        }
        return wb;
    }

  
    public static String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        result = formatDate(cell.getDateCellValue());
                    } else {
                        result = cell.getNumericCellValue();
                    }
                    break;
                case BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    result = cell.getCellFormula();
                    break;
                case ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case BLANK:
                    break;
            }
        }
        return result.toString();
    }

   
    public static List<String[]> importData(InputStream inputStream) {
        return importData(inputStream, 0, 0);
    }

    
    public static List<String[]> importData(InputStream inputStream, int startRow, int startCell) {
        List<String[]> list = new ArrayList<>();
        try {
            HSSFWorkbook excel = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = excel.getSheetAt(0);
            for (int i = startRow; i < sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    String[] item = new String[row.getLastCellNum() - startCell];
                    for (int j = startCell; j < row.getLastCellNum(); j++) {
                        HSSFCell cell = row.getCell(j);
                        item[j - startCell] = getCellValue(cell);
                    }
                    list.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<String[]> iterator = list.iterator();
        while (iterator.hasNext()) {
            String[] next = iterator.next();
            boolean isEmpty = true;
            for (String str : next) {
                if (!(str == null || str.trim().isEmpty())) {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                iterator.remove();
            }
        }
        return list;
    }

    public static List<Map<String, Object>> strListToMap(List<String[]> strList, String[] names) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (String[] item : strList) {
            Map<String, Object> map = new HashMap<>();
            for (int j = 0; j < item.length; j++) {
                if (j < names.length) {
                    map.put(names[j], item[j]);
                }
            }
            mapList.add(map);
        }
        return mapList;
    }

 
    public static <T> List<T> strListToObject(List<String[]> strList, String[] names, Class<T> classz) {
        return JSONUtil.parseArray(JSON.toJSONString(strListToMap(strList, names)), classz);
    }


    private static String formatDate(Date date) {
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
