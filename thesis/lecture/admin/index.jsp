<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.HTMLBean" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("���O�C��(�Ǘ��y�[�W)") %>
		<%= html_bean.getLoginForm("administrator") %>

		<%= html_bean.getFooter() %>
