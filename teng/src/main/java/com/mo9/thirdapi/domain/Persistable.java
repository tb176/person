/**
 *  File: com.gamaxpay.domain.model.Persistable.java
 *  Description:
 *      本类主要用于封装基本的可用hibernate持久化的pojo对象.
 *
 *  Copyright 2004-2008 Gamax Interactive. All rights reserved.
 *  Date        Author      Changes
 *  2011-7-8	Eric Cao      创建
 */
package com.mo9.thirdapi.domain;

import java.io.Serializable;

/**
 * 
 * <p>基本持久化对象</p>
 * 
 * <p>本类主要用于封装基本的可用hibernate持久化的pojo对象.</p>
 * 
 * @author Eric Cao
 * 
 */
public abstract class Persistable implements Serializable
{
    /**
     * 序列化版本ID
     */
    private static final long serialVersionUID = 6656955726938933461L;
    
    /**
     * 标识
     */
    protected long id;

    /**
     * @return the id
     */
    public abstract long getId();

    /**
     * @param id the id to set
     */
    public void setId(long id)
    {
        this.id = id;
    }
    
    

}
