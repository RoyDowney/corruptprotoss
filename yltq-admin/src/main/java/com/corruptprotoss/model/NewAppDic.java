/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-31 11:31:20 
 */
package com.corruptprotoss.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.beans.Transient;
import java.util.List;


/**
 * <p> NewAppDic </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-31 11:31:20 
 */
@Data
@ApiModel(value = "数据字典实体类")
public class NewAppDic {

	/**
	* 主键
	*/
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 字典项key
	*/
    @ApiModelProperty(value = "字典项key")
	private String mkey;

	/**
	* 字典分类key
	*/
    @ApiModelProperty(value = "字典分类key")
	private String pkey;

	/**
	* 字典项名称
	*/
    @ApiModelProperty(value = "字典项名称")
	private String name;

	/**
	* 字典项值
	*/
    @ApiModelProperty(value = "字典项值")
	private String value;

	/**
	* 分类层级 1-1级 2-2级 3-3级 。。。
	*/
    @ApiModelProperty(value = "分类层级 1-1级 2-2级 3-3级 。。。")
	private String type;

	/**
	* 扩展字段1
	*/
    @ApiModelProperty(value = "扩展字段1")
	private String remark1;

	/**
	* 扩展字段2
	*/
    @ApiModelProperty(value = "扩展字段2")
	private String remark2;

	/**
	* 扩展字段3
	*/
    @ApiModelProperty(value = "扩展字段3")
	private String remark3;
	/**
	 * 0未删除1已删除
	 */
	@ApiModelProperty(value = "是否删除")
	private Integer isDeleted;

	private List<NewAppDic> children;

}
