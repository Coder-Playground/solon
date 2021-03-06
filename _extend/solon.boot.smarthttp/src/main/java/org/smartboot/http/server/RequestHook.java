/*******************************************************************************
 * Copyright (c) 2017-2020, org.smartboot. All rights reserved.
 * project name: smart-http
 * file name: BaseHttpRequestHook.java
 * Date: 2020-03-31
 * Author: sandao (zhengjunweimail@163.com)
 ******************************************************************************/

package org.smartboot.http.server;

/**
 * @author 三刀
 * @version V1.0 , 2020/3/31
 */
interface RequestHook {
    Request getRequest();
}
