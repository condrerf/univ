<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
%>
<%= html_bean.getHeader("ログアウト") %>
		どうもお疲れ様でした。<br />
		ブラウザはアクセス情報を記憶しておりますので、現在パソコンを他の方と共用されている場合はこのウィンドウを閉じて下さい。<br />
		<br />
		<a href="<%= utility_bean.getLoginPageURL() %>">ログインページ</a> <a href="<%= utility_bean.getPasswordChangeProcedurePageURL() %>">パスワードの変更</a><br />

		<%= html_bean.getFooter() %>
