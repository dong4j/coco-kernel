package io.github.dong4j.coco.kernel.common.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <p>Description: mservice层的dto父类, 创建原因是创建统一的CRUD方法</p>
 *
 * @param <T> parameter
 * @author dong4j
 * @version 1.5.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.07.01 17:49
 * @since 1.5.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractServiceDTO<T extends Serializable> extends BaseDTO<T> {
    /** serialVersionUID */
    @Serial
    private static final long serialVersionUID = 1L;
    /** Create time */
    private Date createTime;
    /** Update time */
    private Date updateTime;
}
