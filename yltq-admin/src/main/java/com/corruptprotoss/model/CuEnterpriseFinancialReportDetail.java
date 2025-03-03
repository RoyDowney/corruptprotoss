/**
 * Copyright: 互融云
 *
 * @author: RoyDowney
 * @version: V1.0
 * @Date: 2025-01-17 14:27:10 
 */
package com.corruptprotoss.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> NewAppOrgFinancialReportDetail </p>
 *
 * @author: RoyDowney
 * @Date: 2025-01-17 14:27:10 
 */
@Data
@ApiModel(value = "组织财务报表明细实体类")
public class CuEnterpriseFinancialReportDetail {

	/**
	* 主键id
	*/
    @ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "财务主报表id")
	private Long financialReportId;

	/**
	* 类别
	*/
    @ApiModelProperty(value = "类别")
	private String dicPkey;

	/**
	* 科目
	*/
    @ApiModelProperty(value = "科目")
	private String dicValue;

	/**
	* 字典项key
	*/
    @ApiModelProperty(value = "字典项key")
	private String dicMkey;

	/**
	* 期初金额
	*/
    @ApiModelProperty(value = "期初金额")
	private BigDecimal startMoney;

	/**
	* 期末金额
	*/
    @ApiModelProperty(value = "期末金额")
	private BigDecimal endMoney;

	/**
	* 附注
	*/
    @ApiModelProperty(value = "附注")
	private String remark;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	* 创建时间
	*/
    @ApiModelProperty(value = "创建时间")
	private Date created;

}
