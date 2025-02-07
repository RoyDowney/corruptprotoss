package com.corruptprotoss.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author RoyDowney
 * @date 2025/2/6
 */

@Data
public class ReportImportExcelAnalysisDto {

    @ExcelProperty(value = "指标类别",index = 0)
    @ApiModelProperty(value = "指标类别")
    private String indexType;

    @ExcelProperty(value = "指标名称",index = 1)
    @ApiModelProperty(value = "指标名称")
    private String indexName;

    @ExcelProperty(value = "前半年/季度报表",index = 2)
    @ApiModelProperty(value = "前半年/季度报表")
    private String firstHalfOfTheYearOrQuarter;

    @ExcelProperty(value = "前一年报表",index = 3)
    @ApiModelProperty(value = "前一年报表")
    private String thePreviousYear;

    @ExcelProperty(value = "前两年报表",index = 4)
    @ApiModelProperty(value = "前两年报表")
    private String theFirstTwoYears;

    @ExcelProperty(value = "前三年报表",index = 5)
    @ApiModelProperty(value = "前三年报表")
    private String theFirstThreeYears;

    @ExcelIgnore
    private Integer rowIndex;

}
