<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("���O�C��") %>
		<%= html_bean.getLoginForm("student") %>

		<a href="<%= ((UtilityBean) application.getAttribute("utility_bean")).getPasswordChangeProcedurePageURL() %>">�p�X���[�h�̕ύX</a><br />

		<%= html_bean.getFooter() %>
