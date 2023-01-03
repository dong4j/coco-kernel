package com.baomidou.mybatisplus.core.metadata;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排序元素载体
 *
 * @author HCL
 * @version 1.0.0
 * @email "mailto:Spark.Team@gmail.com"
 * @date 2023.01.03 09:57
 * @since 2023.1.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {
    /** serialVersionUID */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 需要进行排序的字段
     */
    private String column;
    /**
     * 是否正序排列，默认 true
     */
    private boolean asc = true;

    /**
     * Asc
     *
     * @param column column
     * @return the order item
     * @since 2023.1.1
     */
    public static OrderItem asc(String column) {
        return build(column, true);
    }

    /**
     * Desc
     *
     * @param column column
     * @return the order item
     * @since 2023.1.1
     */
    public static OrderItem desc(String column) {
        return build(column, false);
    }

    /**
     * Ascs
     *
     * @param columns columns
     * @return the list
     * @since 2023.1.1
     */
    public static List<OrderItem> ascs(String... columns) {
        return Arrays.stream(columns).map(OrderItem::asc).collect(Collectors.toList());
    }

    /**
     * Descs
     *
     * @param columns columns
     * @return the list
     * @since 2023.1.1
     */
    public static List<OrderItem> descs(String... columns) {
        return Arrays.stream(columns).map(OrderItem::desc).collect(Collectors.toList());
    }

    /**
     * Build
     *
     * @param column column
     * @param asc    asc
     * @return the order item
     * @since 2023.1.1
     */
    private static OrderItem build(String column, boolean asc) {
        return new OrderItem(column, asc);
    }
}
