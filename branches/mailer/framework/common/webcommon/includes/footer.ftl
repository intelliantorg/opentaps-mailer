<#--
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
-->

<#-- This file has been modified by Open Source Strategies, Inc. -->


<#assign nowTimestamp = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp()>

<div id="footer">
    <p><a href="http://jigsaw.w3.org/css-validator/"><img src="<@ofbizContentUrl>/images/vcss.gif</@ofbizContentUrl>" alt="Valid CSS!"/></a>
    <a href="http://validator.w3.org/check?uri=referer"><img src="<@ofbizContentUrl>/images/valid-xhtml10.png</@ofbizContentUrl>" alt="Valid XHTML 1.0!"/></a>
    <a href="http://sourceforge.net/projects/opentaps"><img src="${request.getScheme()}://sourceforge.net/sflogo.php?group_id=145855&amp;type=1" width="88" height="31" border="0" alt="SourceForge.net Logo" /></a>  <!-- IMPORTANT!  DO NOT REMOVE THIS -->
    </p>
    <p>Copyright (c) 2001-${nowTimestamp?string("yyyy")} The Apache Software Foundation - <a href="http://www.apache.org" target="_blank">www.apache.org</a><br />
    Powered by <a href="http://ofbiz.apache.org" target="_blank">Apache OFBiz</a></p>
    <p><a href="http://www.opentaps.org" class="tabletext">opentaps Open Source ERP + CRM</a><#if uiLabelMap?exists> ${uiLabelMap.CommonReleaseVersion}</#if>.<br/>
    opentaps is a trademark of <a href="http://www.opensourcestrategies.com">Open Source Strategies, Inc.</a></p>
</div>
</body>
</html>
