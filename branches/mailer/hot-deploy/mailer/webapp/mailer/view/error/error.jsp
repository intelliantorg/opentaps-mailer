<%--
 * Copyright (c) Intelliant
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (open.ant@intelliant.net)
--%>
<%@ page import="org.ofbiz.base.util.*" %>
<html>
<head>
<title>ERROR</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
</head>

<% String errorMsg = (String) request.getAttribute("_ERROR_MESSAGE_"); %>
<% errorMsg = UtilFormatOut.replaceString(errorMsg, "\n", "<br/>"); %>

<body bgcolor="#FFFFFF">
<div align="center">
  <br/>
  <table width="100%" border="1" height="200">
    <tr>
      <td>
        <table width="100%" border="0" height="200">
          <tr bgcolor="#CC6666"> 
            <td height="45"> 
              <div align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="4" color="#FFFFFF"><b>:ERROR MESSAGE:</b></font></div>
            </td>
          </tr>
          <tr> 
            <td>
              <div align="left"><span style="font: 10pt Courier">Error occurred @ "Mailer application"</span></div>
              <div align="left"><span style="font: 10pt Courier"><%=errorMsg%></span></div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<div align="center"></div>
</body>
</html>