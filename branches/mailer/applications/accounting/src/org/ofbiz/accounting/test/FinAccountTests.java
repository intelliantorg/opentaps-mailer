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


package org.ofbiz.accounting.test;

import junit.framework.TestCase;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.GenericDispatcher;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.base.util.UtilMisc;

import java.util.Map;

import javolution.util.FastMap;

/**
 * FinAccountTests
 */
public class FinAccountTests extends TestCase {

    protected LocalDispatcher dispatcher = null;
    protected GenericValue userLogin = null;

    public FinAccountTests(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        GenericDelegator delegator = GenericDelegator.getGenericDelegator("test");
        dispatcher = GenericDispatcher.getLocalDispatcher("test-dispatcher", delegator);
        userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", "system"));
    }

    protected void tearDown() throws Exception {
    }

    public void testCreateFinAccount() throws Exception {
        Map ctx = FastMap.newInstance();
        ctx.put("finAccountId", "TESTACCOUNT1");
        ctx.put("finAccountName", "Test Financial Account");
        ctx.put("finAccountTypeId", "BANK_ACCOUNT");
        ctx.put("userLogin", userLogin);
        Map resp = dispatcher.runSync("createFinAccount", ctx);
        assertEquals("Service result success", ModelService.RESPOND_SUCCESS, resp.get(ModelService.RESPONSE_MESSAGE));        
    }

    public void testDeposit() throws Exception {
        Map ctx = FastMap.newInstance();
        ctx.put("finAccountId", "TESTACCOUNT1");
        ctx.put("amount", new Double(100.00));
        ctx.put("userLogin", userLogin);
        Map resp = dispatcher.runSync("finAccountDeposit", ctx);
        Double balance = (Double) resp.get("balance");
        assertEquals(balance.doubleValue(), 100.00, 0.0);
    }

    public void testWithdraw() throws Exception {
        Map ctx = FastMap.newInstance();
        ctx.put("finAccountId", "TESTACCOUNT1");
        ctx.put("amount", new Double(50.00));
        ctx.put("userLogin", userLogin);
        Map resp = dispatcher.runSync("finAccountWithdraw", ctx);
        Double previousBalance = (Double) resp.get("previousBalance");
        Double balance = (Double) resp.get("balance");
        assertEquals((balance.doubleValue() + 50.00), previousBalance.doubleValue(), 0.0);
    }
}
