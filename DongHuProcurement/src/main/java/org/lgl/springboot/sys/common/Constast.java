package org.lgl.springboot.sys.common;

public class Constast {
    /**
     * 状态码
     */
    public static final Integer OK = 200;
    public static final Integer ERROR = -1 ;

    /**
     * 菜单权限类型
     */
    public static final String TYPE_MENU = "menu";
    public static final String TYPE_PERMISSION = "permission";

    /**
     * 可用状态
     */
    public static final Object AVAILABLE_TRUE = 1;
    public static final Object AVAILABLE_FALSE = 0;

    /**
     * 用户类型
     */
    public static final Object USER_TYPE_SUPER = 0;
    public static final Object USER_TYPE_NORMAL = 1;

    /**
     * 展开类型
     */
    public static final Object OPEN_TRUE = 1;
    public static final Object OPEN_FALSE = 1;

    /**
     * 用户默认密码
     */
    public static final String USER_DEFAULT_PWD = "123456";

    /**
     * hash散列次数
     */
    public static final Integer HASHITERATIONS = 2;

    /**
     * 商品默认图片
     */
    public static final String DEFAULT_IMG_GOODS = "/images/1.jpg";

    /**
     * 图片默认地址
     */
    public static final String IMAGES_DEFAULTGOODSIMG_JPG = "images/2.jpg";
}
