<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	String table_name = request.getParameter("table_name"); // 指定されたテーブル名を格納
	String[] ids = request.getParameterValues("id"); // 指定されたIDを格納

	// 各行データの削除
	for(int i = 0; i < ids.length; i++) {
		database_bean.delete(table_name, ids[i]);
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String japanese_table_name = utility_bean.getJapaneseWord(table_name); // 指定されたテーブル名の日本語名を格納
%>
<%= html_bean.getHeader("削除の完了") %>
		<%= japanese_table_name %>データの削除が完了しました。<br />
		<br />
		<a href="<%= utility_bean.getDataSelectPageURL(table_name, utility_bean.getDataAffiliationNumber(table_name, ids[0])) %>"><%= japanese_table_name %>データの選択</a> <a href="<%= utility_bean.getTableSelectPageURL() %>">テーブルの選択</a> <a href="<%= utility_bean.getAdminMainPageURL() %>">処理の選択</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">ログアウト</a><br />

		<%= html_bean.getFooter() %>
