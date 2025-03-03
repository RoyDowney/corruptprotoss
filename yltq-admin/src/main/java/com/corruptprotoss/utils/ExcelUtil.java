package com.corruptprotoss.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExcelUtil {
    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";



    /**
     * 导出excel
     * @param sheetName
     * @param title
     * @param values
     * @param wb
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        // 声明列对象
        HSSFCell cell = null;

        // 创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        // 创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    /**
     * @Title: analyExcel
     * @Description: 解析Excel
     * @param file
     */
    @SuppressWarnings("deprecation")
    public static List<List<String>> analyExcel(File file) throws FileNotFoundException, IOException {
        Workbook wb = getWorkbook(file);
        Sheet sheet = wb.getSheetAt(0);
        List<List<String>> list = new ArrayList<List<String>>();
        for(int i=0;i<=sheet.getLastRowNum();i++){
            org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
            List<String> list0 = new ArrayList<String>();
            //添加判断第一个单元格是否为空，为空这行不读
            int firstCellNum = row.getFirstCellNum();
            row.getCell(firstCellNum).setCellType(CellType.STRING);
            if(null == row.getCell(firstCellNum) || "".equals(row.getCell(firstCellNum).getStringCellValue().trim())){ continue; }
            for(int j=0;j<row.getLastCellNum();j++){

                //null表示单元格未使用过
                if (row.getCell(j) != null) {
                    row.getCell(j).setCellType(CellType.STRING);
                    if(!"".equals(row.getCell(j).getStringCellValue().trim())){
                        list0.add(row.getCell(j).getStringCellValue());
                    }else{
                        list0.add("");
                    }
                } else {
                    list0.add("");
                }
            }
            list.add(list0);
        }
        return list;

    }

    /**
     * @Title: getWorkbook
     * @Description: 根据文件后缀获取不同的工作簿
     * @param file
     * @throws
     */
    public static Workbook getWorkbook(File file) throws IOException {
        Workbook workbook = null;
        String filePath = file.getName();
        InputStream is = new FileInputStream(file);
        if (filePath.endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (filePath.endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(is);
        }
        return workbook;
    }
    /**
     * @Title: getWorkbook
     * @Description: 根据文件后缀获取不同的工作簿
     * @param inputStream
     * @param extension  文件
     * @return
     * @throws IOException     后缀
     * @throws
     */
    public static Workbook getWorkbook(InputStream inputStream,String extension) throws IOException {
        Workbook workbook = null;
        if (extension.endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (extension.endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        if(inputStream != null ){
            inputStream.close();
        }
        return workbook;
    }
    /**
     * 文件检查
     * @throws FileNotFoundException

     */
    public static void preReadCheck(File file) throws FileNotFoundException, Exception {
        // 常规检查
        //File file = new File(filePath);
        String filePath = file.getName();
        if (!file.exists()) {
            throw new FileNotFoundException("传入的文件不存在：" + filePath);
        }
        if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
            throw new  Exception("传入的文件不是excel");
        }
    }
    /**
     * 取单元格的值
     * @param cell 单元格对象
     * @param treatAsStr 为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Object getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }
        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(CellType.STRING);
        }
        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            if(DateUtil.isCellDateFormatted(cell)){
                Date theDate = cell.getDateCellValue();
                return DateUtils.dateToString(theDate,"yyyy-MM-dd");
            }else{
                return cell.getNumericCellValue();
            }
        } else {
            return cell.getStringCellValue();
        }
    }

    /**
     * excel解析类
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String[]> getExcelData(MultipartFile file) throws IOException{
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = ExcelUtil.getWorkBooks(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了所有行,如果要循环除第一行以外的就firstRowNum+1
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getLastCellNum();
                    String[] cells = new String[row.getLastCellNum()];
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
        }
        return list;
    }

    // 读取excel数据 解析结果删除加粗合计数据行
    public static List<String[]> getExcelDataIsBold(MultipartFile file) throws IOException {
        checkFile(file);
        Workbook workbook = ExcelUtil.getWorkBooks(file);
        if (workbook == null) {
            return Collections.emptyList();
        }

        List<String[]> list = new ArrayList<>();
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);
            if (sheet == null) {
                continue;
            }

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (isRowBold(row, workbook)) {
                    rowIterator.remove();
                }
            }
            for (int rowNum = sheet.getFirstRowNum(); rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                String[] cells = new String[row.getLastCellNum()];
                for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    cells[cellNum] = getCellValue(cell);
                }
                list.add(cells);
            }
        }
        return list;
    }

    private static boolean isRowBold(Row row, Workbook workbook) {
        for (Cell cell : row) {
            Font font = workbook.getFontAt(cell.getCellStyle().getFontIndex());
            if (font.getBold()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检查文件
     * @param file
     * @throws IOException
     */
    public static void checkFile(MultipartFile file) throws IOException{
        //判断文件是否存在
        if(null == file){
            throw new RuntimeException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith("xls") && !fileName.endsWith("xlsx")){
            throw  new RuntimeException(fileName + "不是excel文件");
        }
    }

    /**
     * 获取工作簿
     * @param file
     * @return
     */
    public static  Workbook getWorkBooks(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007 及2007以上
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return workbook;
    }


    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case NUMERIC: //数字
                cellValue = stringDateProcess(cell);
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * string转换工具类
     * @param cell
     * @return
     */
    public static String stringDateProcess(Cell cell){
        String result = new String();
        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
            SimpleDateFormat sdf = null;
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {// 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            double value = cell.getNumericCellValue();
            Date date = DateUtil
                    .getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            // 单元格设置成常规
            if (temp.equals("General")) {
                format.applyPattern("#");
            }
            result = format.format(value);
        }

        return result;
    }

}
