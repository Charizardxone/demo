package com.example.security.define;

/**
 * 常量
 * @author yfc
 * @date 2023/10/26 15:03
 */
public class Constant {

    public static final String NUMBER_CODE_KEY = "cfy:number:code:";
    public static final String MOBILE_CODE_KEY = "cfy:mobile:code:";
    public static final String AUTHENTICATION_TOKEN = "cfy:token:";

    public static final String TOKEN = "token";
    public static final String TOKEN_ENTRY_POINT_URL = "/auth/login";
    public static final String TOKEN_LOGOUT_URL = "/auth/logout";
    public static final int TOKEN_EXPIRE = 60 * 60 * 24 * 7;

	/** 超级管理员ID */
	public static final String SUPER_ADMIN = "0";

	public static final int CODE_SIZE = 4;

    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),
        /**
         * minio
         */
        MINIO(4);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
