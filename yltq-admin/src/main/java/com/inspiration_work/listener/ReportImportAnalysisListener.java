package com.inspiration_work.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson2.JSON;
import com.inspiration_work.dto.ReportImportExcelAnalysisDto;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author RoyDowney
 * @date 2025/2/6
 */

@Slf4j
public class ReportImportAnalysisListener  extends AnalysisEventListener<ReportImportExcelAnalysisDto> {

    private int rowIndex = 0;

    private static final int HEAD_ROW_NUM = 1;

    private List<ReportImportExcelAnalysisDto> list = new ArrayList<>();

    private List<CellExtra> cellExtraList = new ArrayList<>();

    public ReportImportAnalysisListener() {}

    public List<ReportImportExcelAnalysisDto> getList() {
        return list;
    }

    @Override
    public void invoke(ReportImportExcelAnalysisDto excelData, AnalysisContext analysisContext) {
        excelData.setRowIndex(++rowIndex); // 设置行号
        log.info(" data -> {}", excelData);
        list.add(excelData);
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info(" extra -> {}", JSON.toJSONString(extra));
        CellExtraTypeEnum type = extra.getType();
        switch (type) {
            case MERGE: {
                if (extra.getRowIndex() >= HEAD_ROW_NUM) {
                    cellExtraList.add(extra);
                }
                break;
            }
            default:{
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info(" doAfterAllAnalysed");
        //读取完成 填充合并过的单元格
        if (cellExtraList != null && cellExtraList.size() > 0) {
            mergeExcelData(list, cellExtraList, HEAD_ROW_NUM);
        }
    }


    //合并Excel数据
    private void mergeExcelData(List<ReportImportExcelAnalysisDto> excelDataList, List<CellExtra> cellExtraList, int headRowNum) {
        cellExtraList.forEach(cellExtra -> {
            int firstRowIndex = cellExtra.getFirstRowIndex() - headRowNum;
            int lastRowIndex = cellExtra.getLastRowIndex() - headRowNum;
            int firstColumnIndex = cellExtra.getFirstColumnIndex();
            int lastColumnIndex = cellExtra.getLastColumnIndex();
            //获取初始值 合并单元格左上角的值
            Object initValue = getInitValueFromList(firstRowIndex, firstColumnIndex, excelDataList);
            //设置值 把合并单元格左上角的值 设置到合并区域的每一个单元格
            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                for (int j = firstColumnIndex; j <= lastColumnIndex; j++) {
                    setInitValueToList(initValue, i, j, excelDataList);
                }
            }
        });
    }


    //初始值为列表
    private void setInitValueToList(Object filedValue, Integer rowIndex, Integer columnIndex, List<ReportImportExcelAnalysisDto> data) {
        ReportImportExcelAnalysisDto object = data.get(rowIndex);

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == columnIndex) {
                    try {
                        field.set(object, filedValue);
                        break;
                    } catch (IllegalAccessException e) {
                        log.error("设置合并单元格的值异常：{}", e.getMessage());
                    }
                }
            }
        }
    }

    //从列表中获取初始值
    private Object getInitValueFromList(Integer firstRowIndex, Integer firstColumnIndex, List<ReportImportExcelAnalysisDto> data) {
        Object filedValue = null;
        ReportImportExcelAnalysisDto object = data.get(firstRowIndex);
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == firstColumnIndex) {
                    try {
                        filedValue = field.get(object);
                        break;
                    } catch (IllegalAccessException e) {
                        log.error("设置合并单元格的初始值异常：{}", e.getMessage());
                    }
                }
            }
        }
        return filedValue;
    }

    public static void main(String[] args) {

        List<Integer> integers = Arrays.asList(1, 3, 5);
        StringJoiner sj = new StringJoiner(",","","");

        for (Integer integer : integers) {
            sj.add(integer.toString());
        }
        System.out.println(sj);

        List<String> words = Arrays.asList("Java", "Python", "Go");
        String result = words.stream().collect(Collectors.joining());
        // 输出：JavaPythonGo

        String withDelimiter = words.stream()
                .collect(Collectors.joining(", "));
        // 输出：Java, Python, Go





    }
}
