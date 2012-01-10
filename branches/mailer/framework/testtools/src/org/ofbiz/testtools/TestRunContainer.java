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

package org.ofbiz.testtools;

import javolution.util.FastMap;
import junit.framework.*;
import org.ofbiz.base.container.Container;
import org.ofbiz.base.container.ContainerException;
import org.ofbiz.base.util.Debug;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter;

import java.io.*;
import java.util.Enumeration;
import java.util.Map;

/**
 * A Container implementation to run the tests configured through this testtools stuff.
 */
public class TestRunContainer implements Container {

    public static final String module = TestRunContainer.class.getName();
    public static final String logDir = "runtime/logs/";

    protected TestResult results = null;
    protected String configFile = null;
    protected String component = null;

    /**
     * @see org.ofbiz.base.container.Container#init(java.lang.String[], java.lang.String)
     */
    public void init(String[] args, String configFile) {
        this.configFile = configFile;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                String argument = args[i];
                // arguments can prefix w/ a '-'. Just strip them off
                if (argument.startsWith("-")) {
                    int subIdx = 1;
                    if (argument.startsWith("--")) {
                        subIdx = 2;
                    }
                    argument = argument.substring(subIdx);
                }

                // parse the arguments
                if (argument.indexOf("=") != -1) {
                    String argumentName = argument.substring(0, argument.indexOf("="));
                    String argumentVal = argument.substring(argument.indexOf("=") + 1);

                    if ("component".equalsIgnoreCase(argumentName)) {
                        this.component = argumentVal;
                    }
                }
            }
        }
    }

    public boolean start() throws ContainerException {
        //ContainerConfig.Container jc = ContainerConfig.getContainer("junit-container", configFile);

        // get the tests to run
        JunitSuiteWrapper jsWrapper = new JunitSuiteWrapper(component);

        // load the tests into the suite
        TestSuite suite = new TestSuite();
        jsWrapper.populateTestSuite(suite);

        JUnitTest test = new JUnitTest();
        test.setName("tests");

        // create the XML logger
        JunitXmlListener xml;
        try {
            xml = new JunitXmlListener(new FileOutputStream(logDir + "tests.xml"));
        } catch (FileNotFoundException e) {
            throw new ContainerException(e);
        }


        // holder for the results
        results = new TestResult();
        results.addListener(new DebugTestListener(suite.countTestCases()));
        results.addListener(xml);

        // add the suite to the xml listener
        xml.startTestSuite(test);

        // run the tests
        suite.run(results);

        test.setCounts(results.runCount(), results.failureCount(), results.errorCount());
        xml.endTestSuite(test);

        // dispay the results
        Debug.log("[JUNIT] Pass: " + results.wasSuccessful() + " | # Tests: " + results.runCount() + " | # Failed: " +
                results.failureCount() + " # Errors: " + results.errorCount(), module);
        if (Debug.importantOn()) {
            Debug.log("[JUNIT] ----------------------------- ERRORS ----------------------------- [JUNIT]", module);
            Enumeration err = results.errors();
            if (!err.hasMoreElements()) {
                Debug.log("None");
            } else {
                while (err.hasMoreElements()) {
                    Object error = err.nextElement();
                    Debug.log("--> " + error, module);
                    if (error instanceof TestFailure) {
                        Debug.log(((TestFailure) error).trace());
                    }
                }
            }
            Debug.log("[JUNIT] ------------------------------------------------------------------ [JUNIT]", module);
            Debug.log("[JUNIT] ---------------------------- FAILURES ---------------------------- [JUNIT]", module);
            Enumeration fail = results.failures();
            if (!fail.hasMoreElements()) {
                Debug.log("None");
            } else {
                while (fail.hasMoreElements()) {
                    Object failure = fail.nextElement();
                    Debug.log("--> " + failure, module);
                    if (failure instanceof TestFailure) {
                        Debug.log(((TestFailure) failure).trace());
                    }
                }
            }
            Debug.log("[JUNIT] ------------------------------------------------------------------ [JUNIT]", module);
        }

        return true;
    }

    public void stop() throws ContainerException {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new ContainerException(e);
        }
    }

    class JunitXmlListener extends XMLJUnitResultFormatter {

        Map startTimes = FastMap.newInstance();

        public JunitXmlListener(OutputStream out) {
            this.setOutput(out);
        }

        public void startTestSuite(JUnitTest suite) {
            startTimes.put(suite.getName(), Long.valueOf(System.currentTimeMillis()));
            super.startTestSuite(suite);
        }

        public void endTestSuite(JUnitTest suite) throws BuildException {
            Long startTime = (Long) startTimes.get(suite.getName());
            suite.setRunTime(System.currentTimeMillis() - startTime.longValue());
            super.endTestSuite(suite);
        }
    }
}
