<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	utility_bean.releaseMaintainingAdministratorID(); // メンテナンスを行っている管理者IDの解放
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("ログアウト完了") %>
		ログアウトが完了しました。<br />
		<br />

		<a href="<%= utility_bean.getAdminLoginPageURL() %>">ログインページ(管理ページ)</a>

		<%= html_bean.getFooter() %>
