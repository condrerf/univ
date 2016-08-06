<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String[] ids = request.getParameterValues("id"); // IDを格納

	// IDが指定されていない場合
	if(ids == null) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("no_data_specified_error")).forward(request, response); // エラーページへの転送
	}

	String table_name = request.getParameter("table_name"); // 指定されたテーブル名を格納
	String button_name = "実行"; // ボタンの名前を格納
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("削除内容の確認") %>
		削除する<%= utility_bean.getJapaneseWord(table_name) %>データの内容を確認し、問題がなければ[<%= button_name %>]ボタンをクリックして下さい。<br />
		<br />

		<%= html_bean.getDataList("delete", table_name, ids, button_name) %>

		<a href="<%= utility_bean.getDataSelectPageURL(table_name, utility_bean.getDataAffiliationNumber(table_name, ids[0])) %>">戻る</a><br />

		<%= html_bean.getFooter() %>
