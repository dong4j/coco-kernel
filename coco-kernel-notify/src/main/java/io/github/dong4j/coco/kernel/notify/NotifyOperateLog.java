package io.github.dong4j.coco.kernel.notify;

import io.github.dong4j.coco.kernel.notify.entity.OperateLog;

/**
 * <p>Description:  </p>
 *
 * @author dong4j
 * @version 1.0.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.05.08 20:31
 * @since 1.0.0
 */
public interface NotifyOperateLog {

    /**
     * 保存日志
     *
     * @param object object
     * @since 1.0.0
     */
    void saveLog(OperateLog object);
}
