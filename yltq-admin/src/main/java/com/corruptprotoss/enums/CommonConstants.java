package com.corruptprotoss.enums;

/**
 * @author RoyDowney
 * @date 2019/10/29
 */
public interface CommonConstants {

	/**
	 * header 中租户ID
	 */
	String TENANT_ID = "TENANT-ID";

	/**
	 * header 中版本信息
	 */
	String VERSION = "VERSION";

	/**
	 * 租户ID
	 */
	Integer TENANT_ID_1 = 1;

	/**
	 * 删除
	 */
	Integer STATUS_DEL = 0;

	/**
	 * 正常
	 */
	Integer STATUS_NORMAL = 1;

	/**
	 * 锁定
	 */
	Integer STATUS_LOCK = 9;

	/**
	 * 菜单树根节点
	 */
	Integer MENU_TREE_ROOT_ID = -1;

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * 前端工程名
	 */
	String FRONT_END_PROJECT = "aldserver-ui";

	/**
	 * 后端工程名
	 */
	String BACK_END_PROJECT = "aldserver";

	/**
	 * 公共参数
	 */
	String ALD_PUBLIC_PARAM_KEY = "ALD_PUBLIC_PARAM_KEY";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;

	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 默认存储bucket
	 */
	String BUCKET_NAME = "aldserver";

	/**
	 * 滑块验证码
	 */
	String IMAGE_CODE_TYPE = "blockPuzzle";

	/**
	 * 验证码开关
	 */
	String CAPTCHA_FLAG = "captcha_flag";

	/**
	 * 密码传输是否加密
	 */
	String ENC_FLAG = "enc_flag";

}
