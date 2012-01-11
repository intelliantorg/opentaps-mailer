/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/

/* This file has been modified by Open Source Strategies, Inc. */

package org.ofbiz.webapp.taglib;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * AbstractParameterTag - Tag which support child parameter tags.
 */
public abstract class AbstractParameterTag extends TagSupport {

    private Map inParameters = null;
    private Map outParameters = null;

    public void addInParameter(Object name, Object value) {
        if (this.inParameters == null)
            this.inParameters = new HashMap();
        inParameters.put(name, value);
    }

    public Map getInParameters() {
        if (this.inParameters == null)
            return new HashMap();
        else
            return this.inParameters;
    }

    public void addOutParameter(Object name, Object alias) {
        if (this.outParameters == null)
            this.outParameters = new HashMap();
        outParameters.put(name, alias);
    }

    public Map getOutParameters() {
        if (this.outParameters == null)
            return new HashMap();
        else
            return this.outParameters;
    }

    public int doStartTag() throws JspTagException {
        inParameters = new HashMap();
        return EVAL_BODY_INCLUDE;
    }

    public abstract int doEndTag() throws JspTagException;

}