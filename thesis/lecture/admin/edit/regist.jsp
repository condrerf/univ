<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String table_name = request.getParameter("table_name"); // 指定されたテーブル名を格納
	String[] form_data = utility_bean.getFormData(database_bean.getColumnNames(table_name), request); // フォームデータを格納

	// データが未登録である場合
	if(database_bean.getRowData(table_name, form_data[0]) == null) {
		database_bean.insert(table_name, form_data); // データの挿入
	// 更新を行う場合
	} else {
		database_bean.update(table_name, form_data[0], form_data); // データの更新
	}

	// 登録データが開講講義データで、必要開講講義IDが設定されている場合
	if (table_name.equals("opening_lecture") && form_data[2] != null) {
		String[] necessary_opening_lecture_data = database_bean.getRowData(table_name, form_data[2]); // 必要開講講義データを格納

		// 必要開講講義データの必要開講講義IDが設定されていない場合
		if (necessary_opening_lecture_data[2] == null) {
			necessary_opening_lecture_data[2] = form_data[0]; // 登録データの開講講義IDを必要開講講義データの必要開講講義IDに設定
			database_bean.update(table_name, form_data[2], necessary_opening_lecture_data); // 必要開講講義データの更新
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	String japanese_table_name = utility_bean.getJapaneseWord(table_name); // 指定されたテーブル名の日本語名を格納
	String data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, form_data[0]); // データ所属番号を格納
%>
<%= html_bean.getHeader("登録の完了") %>
		<%= japanese_table_name %>データの登録が完了しました。<br />
		<br />

		<a href="<%= utility_bean.getDataEditPageURL(table_name, form_data[0], null) %>">登録データの再編集</a> <a href="<%= utility_bean.getDataEditPageURL(table_name, null, data_affiliation_number) %>"><%= japanese_table_name %>データの新規登録</a> <a href="<%= utility_bean.getDataSelectPageURL(table_name, data_affiliation_number) %>"><%= japanese_table_name %>データの選択</a> <a href="<%= utility_bean.getTableSelectPageURL() %>">テーブルの選択</a> <a href="<%= utility_bean.getAdminMainPageURL() %>">処理の選択</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">ログアウト</a><br />

		<%= html_bean.getFooter() %>
