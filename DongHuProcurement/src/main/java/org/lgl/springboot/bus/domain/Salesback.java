package org.lgl.springboot.bus.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LGL
 * @since 2020-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_salesback")
public class Salesback implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerid;

    private String paytype;

    private Date salesbacktime;

    private Double salebackprice;

    private String operateperson;

    private Integer number;

    private String remark;

    private Integer goodsid;

    //客户姓名
    @TableField(exist = false)
    private String customername;

    //商品名称
    @TableField(exist = false)
    private String goodsname;

    //商品规格
    @TableField(exist = false)
    private String size;


}
