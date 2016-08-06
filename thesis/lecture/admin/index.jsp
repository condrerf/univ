<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.HTMLBean" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("ログイン(管理ページ)") %>
		<%= html_bean.getLoginForm("administrator") %>

		<%= html_bean.getFooter() %>
