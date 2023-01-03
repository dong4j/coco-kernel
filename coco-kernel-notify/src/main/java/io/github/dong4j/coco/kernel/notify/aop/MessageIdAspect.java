package io.github.dong4j.coco.kernel.notify.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dong4j.coco.kernel.common.context.Trace;
import io.github.dong4j.coco.kernel.common.util.ClassUtils;
import io.github.dong4j.coco.kernel.notify.Message;
import io.github.dong4j.coco.kernel.notify.exception.NotifyException;

/**
 * <p>Description: aop 拦截生成 messageId </p>
 *
 * @author dong4j
 * @version 1.4.0
 * @email "mailto:dong4j@gmail.com"
 * @date 2020.05.08 13:49
 * @since 1.4.0
 */
@Aspect
@Component
public class MessageIdAspect {

    /**
     * Notify point cut
     *
     * @since 1.4.0
     */
    @Pointcut("execution(* io.github.dong4j.coco.kernel.notify.Notify.*(..))")
    public void notifyPointCut() {
        // nothing to do
    }

    /**
     * Before
     *
     * @param joinPoint join point
     * @since 1.4.0
     */
    @Before(value = "notifyPointCut()")
    @SuppressWarnings("unchecked")
    public void before(@NotNull JoinPoint joinPoint) {
        // 获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        for (Object argItem : obj) {
            if (argItem instanceof Message) {
                Class<?> idType = ClassUtils.getSuperClassT(argItem.getClass(), 0);

                if (((Message<?>) argItem).getMessageId() == null) {
                    if (Long.class.isAssignableFrom(idType)) {
                        Message<Long> message = (Message<Long>) argItem;
                        // todo-dong4j : (2022.03.27 20:48) [优先从 trace 中获取 traceId, 需要注意类型转换]
                        message.setMessageId(new Snowflake().nextId());
                    } else if (String.class.isAssignableFrom(idType)) {
                        Message<String> paramVO = (Message<String>) argItem;
                        String traceId = Trace.context().get();
                        traceId = StrUtil.isBlank(traceId) ? IdUtil.fastSimpleUUID() : traceId;
                        paramVO.setMessageId(traceId);
                    } else {
                        throw new NotifyException("暂不支持 [{}] 类型的 message id", idType.getSimpleName());
                    }
                }
            }
        }
    }

}
