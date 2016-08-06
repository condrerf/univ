<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("ログイン") %>
		<%= html_bean.getLoginForm("student") %>

		<a href="<%= ((UtilityBean) application.getAttribute("utility_bean")).getPasswordChangeProcedurePageURL() %>">パスワードの変更</a><br />

		<%= html_bean.getFooter() %>
