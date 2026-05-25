package com.inspiration_work.model.vo;

/**
 * @author RoyDowney
 * @date 2025/2/8
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p> FinancialTargetDataVo </p>
 *
 * @author: RoyDowney
 * @Date: 2025-01-17 11:19:34
 */
@Data
@ApiModel(value = "企业财务指标vo")
public class FinancialTargetDataVo {

    @ApiModelProperty(value = "名称")
    private String resultName;

    @ApiModelProperty(value = "resultKey")
    private String resultKey;

    @ApiModelProperty(value = "公式")
    private String formula;

    @ApiModelProperty(value = "结果")
    private BigDecimal resultValue;

    @ApiModelProperty(value = "备注")
    private String remark;

}
