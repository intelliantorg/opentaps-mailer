/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

/* This file has been modified by Open Source Strategies, Inc. */


package org.ofbiz.base.util;

import java.io.OutputStream;
import java.io.IOException;

/**
 * StringOutputStream
 */
public class StringOutputStream extends OutputStream {

    protected StringBuffer buffer = new StringBuffer();

    public void write(byte[] b) throws IOException {
        buffer.append(new String(b));
    }

    public void write(byte[] b, int off, int len) throws IOException {
        buffer.append(new String(b, off, len));
    }

    public void write(int b) throws IOException {
        byte[] byteArr = new byte[] { (byte) b };        
        buffer.append(new String(byteArr));
    }

    public String toString() {
        return buffer.toString();
    }
}
