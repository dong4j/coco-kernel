/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.github.dong4j.coco.kernel.common.timer;

import java.util.Set;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Schedules {@link TimerTask}s for one-time future execution in a background
 * thread.
 *
 * @author Spark.Team
 * @version 1.0.0
 * @email "mailto:Spark.Team@gmail.com"
 * @date 2023.01.03 09:58
 * @since 2023.1.1
 */
@SuppressWarnings("all")
public interface Timer {

    /**
     * Schedules the specified {@link TimerTask} for one-time execution after
     * the specified delay.
     *
     * @param task  task
     * @param delay delay
     * @param unit  unit
     * @return a handle which is associated with the specified task
     * @throws IllegalStateException      if this timer has been {@linkplain #stop() stopped} already
     * @throws RejectedExecutionException if the pending timeouts are too many and creating new timeout
     *                                    can cause instability in the system.
     * @since 2023.1.1
     */
    Timeout newTimeout(TimerTask task, long delay, TimeUnit unit);

    /**
     * Releases all resources acquired by this {@link Timer} and cancels all
     * tasks which were scheduled but not executed yet.
     *
     * @return the handles associated with the tasks which were canceled by     this method
     * @since 2023.1.1
     */
    Set<Timeout> stop();

    /**
     * the timer is stop
     *
     * @return true for stop
     * @since 2023.1.1
     */
    boolean isStop();
}
