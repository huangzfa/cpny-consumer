package com.cpny.customer.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author huangzhongfa
 * @since 2019-06-11
 */
@Data
@Accessors(chain = true)
@TableName("pgy_user")
public class PgyUser extends Model<PgyUser> {

    private static final long serialVersionUID = 1L;

    /**
     * user_id取自profile的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 所属产品id
     */
    @TableField("product_id")
    private Integer productId;
    /**
     * 所属应用id
     */
    @TableField("app_id")
    private Integer appId;
    /**
     * 全局用户id，MD5【姓名+身份证号码】
     */
    @TableField("global_user_id")
    private String globalUserId;
    /**
     * 用户名-手机号-脱敏
     */
    @TableField("user_name")
    private String userName;
    /**
     * 用户名-手机号密文无解，用于匹配
     */
    @TableField("user_name_md5")
    private String userNameMd5;
    /**
     * 用户名-手机号密文可解
     */
    @TableField("user_name_encrypt")
    private String userNameEncrypt;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 密码的盐值
     */
    private String salt;
    /**
     * 所属推广渠道id
     */
    @TableField("channel_id")
    private Integer channelId;
    /**
     * 添加时间
     */
    @TableField("add_time")
    private Date addTime;
    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;
    /**
     * 不等于0表示删除 删除为id
     */
    @TableField("is_delete")
    private Long isDelete;


    public static final String ID = "id";

    public static final String PRODUCT_ID = "product_id";

    public static final String APP_ID = "app_id";

    public static final String GLOBAL_USER_ID = "global_user_id";

    public static final String USER_NAME = "user_name";

    public static final String USER_NAME_MD5 = "user_name_md5";

    public static final String USER_NAME_ENCRYPT = "user_name_encrypt";

    public static final String PWD = "pwd";

    public static final String SALT = "salt";

    public static final String CHANNEL_ID = "channel_id";

    public static final String ADD_TIME = "add_time";

    public static final String MODIFY_TIME = "modify_time";

    public static final String IS_DELETE = "is_delete";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
