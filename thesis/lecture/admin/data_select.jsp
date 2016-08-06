<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String processing_type = request.getParameter("processing_type"); // 指定された処理の種類を格納
	String table_name = null; // テーブル名を格納

	// 処理の種類が指定されている場合
	if (processing_type != null) {
		table_name = "opening_lecture"; // 開講講義テーブルをテーブル名に設定
	// 指定されていない場合
	} else {
		processing_type = "edit"; // "edit"を処理の種類に設定
		table_name = request.getParameter("table_name"); // 指定されたテーブル名の取得
	}

	String data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, request); // 指定されたデータ所属番号を格納
	String data_affiliation_number_name = utility_bean.getDataAffiliationNumberName(table_name); // データ所属番号名を格納
	String[] ids = null; // 指定されたテーブル名とデータ所属番号に所属するデータのIDを格納

	// 指定されたテーブル名がデータ所属番号のないテーブルであるか、データ所属番号が取得できた場合
	if (data_affiliation_number_name == null || data_affiliation_number != null) {
		ids = ((DatabaseBean) application.getAttribute("database_bean")).getColumnDataArray(table_name, 0, data_affiliation_number); // IDの取得
	}

	String[][] data_affiliation_number_form_component_data = utility_bean.getDataAffiliationNumberFormComponentData(table_name); // データ所属番号のフォーム構成要素データを格納

	// データ所属番号が取得でき、そのデータ所属番号に所属するデータが指定されたテーブルに存在しない場合
	if (data_affiliation_number != null && ids == null) {
		String error_parameter_value = null; // エラーパラメータ値を格納

		// エラーパラメータ値の取得
		for (int i = 0; i < data_affiliation_number_form_component_data[0].length; i++) {
			// 現在アクセスしているデータ所属番号が指定されたデータ所属番号と等しい場合
			if (data_affiliation_number_form_component_data[0][i].equals(data_affiliation_number)) {
				error_parameter_value = data_affiliation_number + "(" + data_affiliation_number_form_component_data[1][i] + ")"; // エラーパラメータ値の設定
				break; // ループから抜ける
			}
		}

		application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", utility_bean.getJapaneseWord(utility_bean.getDataAffiliationNumberName(table_name)), error_parameter_value)).forward(request, response); // エラーページへの転送
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	StringBuffer main_content = new StringBuffer(); // メイン内容を格納

	// 指定された処理の種類が"edit"である場合
	if (processing_type.equals("edit")) {
		// データの新規登録ページへのリンクを付加
		main_content.append("<a href=\"" + utility_bean.getDataEditPageURL(table_name, null, data_affiliation_number) + "\">" + utility_bean.getJapaneseWord(table_name) + "データの新規登録</a><br />\n");
		main_content.append("\t\t<br />\n");
	// "edit"でない場合
	} else {
		main_content.append("\n"); // 改行コードの付加
	}

	// データ所属番号のフォーム構成要素データが取得できた場合
	if (data_affiliation_number_form_component_data != null) {
		main_content.append("\t\t<form method=\"POST\" action=\"" + utility_bean.getDataSelectPageURL() + "\">\n");

		// 指定された処理の種類が"edit"でない場合
		if (!processing_type.equals("edit")) {
			main_content.append("\t\t\t<input type=\"hidden\" name=\"processing_type\" value=\"" + processing_type + "\" />\n");
		// "edit"である場合
		} else {
			main_content.append("\t\t\t<input type=\"hidden\" name=\"table_name\" value=\"" + table_name + "\" />\n");
		}

		main_content.append("\t\t\t" + utility_bean.getJapaneseWord(data_affiliation_number_name) + ": " + html_bean.getSelectBox(data_affiliation_number_name, data_affiliation_number, data_affiliation_number_form_component_data[0], data_affiliation_number_form_component_data[1], true, 3) + "\n");
		main_content.append("\t\t\t<input type=\"submit\" value=\"検索\" />\n");
		main_content.append("\t\t</form>\n");
	// データ所属番号のフォーム構成要素データが取得できず、データ所属番号名が取得できなかった場合
	} else if (data_affiliation_number_name != null) {
		main_content.append("\t\t" + utility_bean.getJapaneseWord(table_name) + "データの作成に必要なデータが作成されていません。<br />\n");
	}

	// IDが指定されていない場合
	if (ids == null) {
		// データ所属番号名が取得できなかった場合
		if (data_affiliation_number_name == null) {
			main_content.append("\t\t現在" + utility_bean.getJapaneseWord(table_name) + "データは登録されていません。上のリンクをクリックして新規登録を行って下さい。<br />\n");
		// 取得できた場合
		} else {
			main_content.append("\t\t上の検索フォームで条件を設定し、[検索]ボタンをクリックして下さい。<br />\n"); // メッセージの取得
		}

		main_content.append("\t\t<br />\n");
	// 指定されている場合
	} else {
		String button_name = null; // ボタンの名前を格納

		// 指定された処理の種類が"edit"である場合
		if (processing_type.equals("edit")) {
			button_name = "削除"; // "削除"をボタンの名前に設定
		}

		main_content.append("\t\t" + html_bean.getDataList(processing_type, table_name, ids, button_name) + "\n"); // データ一覧表の取得
	}

	String title = null; // タイトルを格納

	// 指定された処理の種類が"edit"である場合
	if (processing_type.equals("edit")) {
		main_content.append("\t\t<a href=\"" + utility_bean.getTableSelectPageURL() + "\">テーブルの選択</a><br />"); // テーブル選択ページのリンクを付加
		title = utility_bean.getJapaneseWord(table_name) + "データ"; // テーブル名+"データ"をタイトルに設定
	// "edit"でない場合
	} else {
		main_content.append("\t\t<a href=\"" + utility_bean.getAdminMainPageURL() + "\">処理の選択</a><br />");
		title = "講義"; // "講義"をタイトルに設定
	}
%>
<%= html_bean.getHeader(title + "の選択") %>
		<%= main_content %>

		<%= html_bean.getFooter() %>
