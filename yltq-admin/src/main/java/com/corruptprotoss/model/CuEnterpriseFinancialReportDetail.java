/**
 * Copyright: 互融云
 *
 * @author: RoyDowney
 * @version: V1.0
 * @Date: 2025-01-17 14:27:10 
 */
package com.corruptprotoss.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

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

	@Column(name= "financialReportId")
	@ApiModelProperty(value = "财务主报表id")
	private Long financialReportId;

	/**
	* 类别
	*/
	@Column(name= "dicPkey")
    @ApiModelProperty(value = "类别")
	private String dicPkey;

	/**
	* 科目
	*/
	@Column(name= "dicValue")
    @ApiModelProperty(value = "科目")
	private String dicValue;

	/**
	* 字典项key
	*/
	@Column(name= "dicMkey")
    @ApiModelProperty(value = "字典项key")
	private String dicMkey;

	/**
	* 期初金额
	*/
	@Column(name= "startMoney")
    @ApiModelProperty(value = "期初金额")
	private BigDecimal startMoney;

	/**
	* 期末金额
	*/
	@Column(name= "endMoney")
    @ApiModelProperty(value = "期末金额")
	private BigDecimal endMoney;

	/**
	* 附注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "附注")
	private String remark;

	@Column(name= "sort")
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	* 创建时间
	*/
	@Column(name= "created")
    @ApiModelProperty(value = "创建时间")
	private Date created;

}
