<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	final String[] TABLE_NAMES = {"course", "lecture_affiliation", "chair", "student", "lecturer", "lecture", "opening_lecture", "opening_lecture_lecturer"}; // テーブル名を格納
	String[] japanese_table_names = new String[TABLE_NAMES.length]; // 日本語のテーブル名を格納

	// 日本語のテーブル名の取得
	for (int i = 0; i < TABLE_NAMES.length; i++) {
		japanese_table_names[i] = utility_bean.getJapaneseWord(TABLE_NAMES[i]);
	}
%>
<%= html_bean.getHeader("テーブルの選択") %>
		編集したいデータが所属しているテーブルを選択して下さい。<br />
		<br />

		<form method="POST" action="<%= utility_bean.getDataSelectPageURL() %>">
			<%= html_bean.getSelectBox("table_name", null, TABLE_NAMES, japanese_table_names, 3) %>テーブル
			<input type="submit" value="設定" />
		</form>

		<a href="<%= utility_bean.getAdminMainPageURL() %>">処理の選択</a><br />

		<%= html_bean.getFooter() %>
