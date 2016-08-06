<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String student_id = request.getParameter("student_id"); // 学籍番号を格納
	String[][] opening_lecture_ids = utility_bean.getOpeningLectureIDs(request); // 開講講義IDを格納
	int semester_number = utility_bean.getSemesterNumber(); // 学期番号を格納

	// 開講講義データの登録
	registration: for (int i = 0; i < opening_lecture_ids.length; i++) {
		for (int j = 0; j < opening_lecture_ids[i].length; j++) {
			// 処理が集中講義の最終要素まで終えている場合
			if (i == 5 && j == 5) {
				break registration; // ループから抜ける
			}

			String registration_opening_lecture_id = student_id + semester_number + i + j; // 登録開講講義IDを格納
			String[] registered_opening_lecture_data = database_bean.getRowData("registered_opening_lecture", registration_opening_lecture_id); // 登録された開講講義でデータを格納

			// 現在アクセスしている日時に開講講義が指定されている場合
			if (opening_lecture_ids[i][j] != null) {
				String[] registration_opening_lecture_data = {registration_opening_lecture_id, opening_lecture_ids[i][j]}; // 登録開講講義データを格納

				// 現在アクセスしている日時にデータが登録されている場合
				if (registered_opening_lecture_data != null) {
					database_bean.update("registered_opening_lecture", registration_opening_lecture_id, registration_opening_lecture_data); // 開講講義データの更新
				// 登録されていない場合
				} else {
					database_bean.insert("registered_opening_lecture", registration_opening_lecture_data); // 開講講義データの登録
				}
			// 開講講義が指定されておらず、その日時にデータが登録されている場合
			} else if (registered_opening_lecture_data != null) {
				database_bean.delete("registered_opening_lecture", registration_opening_lecture_id); // 開講講義データの削除
			}
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("登録完了") %>
		データの登録が完了しました。<br />
		<br />
		<a href="<%= utility_bean.getMainPageURL(student_id) %>">履修講義の選択</a> <a href="<%= utility_bean.getLogoutPageURL() %>">ログアウト</a><br />

		<%= html_bean.getFooter() %>
