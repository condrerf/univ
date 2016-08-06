<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.Calendar" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String opening_lecture_id = request.getParameter("opening_lecture_id"); // 指定された開講講義IDを格納
	String[] student_ids = utility_bean.getStudentIDs(opening_lecture_id); // 学籍番号を格納
	String[] scores = utility_bean.getFormData(student_ids, request); // 点数を格納
	String lecture_id = database_bean.getColumnData("opening_lecture", 1, opening_lecture_id); // 指定された開講講義IDに該当する講義IDを格納
	String lecture_affiliation_number = "" + (Integer.parseInt(opening_lecture_id.substring(2, 3)) / 5); // 講義所属番号を格納

	// 各成績データの登録
	for (int i = 0; i < student_ids.length; i++) {
		String mastered_lecture_id = student_ids[i] + lecture_id; // 履修済み講義IDを格納
		int mastered_year = Calendar.getInstance().get(Calendar.YEAR); // 履修年を格納

		// 現在の月が1月から3月の間である場合
		if (Calendar.getInstance().get(Calendar.MONTH) < 3) {
			mastered_year--; // 履修年の減算
		}

		String[] mastered_lecture_data = {mastered_lecture_id, scores[i], "" + mastered_year, lecture_affiliation_number}; // 履修済み講義データを格納

		// 現在アクセスしている要素と同じ履修済み講義IDで登録された履修済み講義データが存在しない場合
		if (database_bean.getRowData("mastered_lecture", mastered_lecture_id) == null) {
			// 現在アクセスしている要素に点数が設定されている場合(60点以上のデータ)
			if (scores[i] != null) {
				database_bean.insert("mastered_lecture", mastered_lecture_data); // 履修済み講義データの挿入
			}
		// 履修済み講義データが存在しており、点数が設定されている場合
		} else if (scores[i] != null) {
			database_bean.update("mastered_lecture", mastered_lecture_id, mastered_lecture_data); // 履修済み講義データの更新
		// 履修済み講義データが存在しており、点数が設定されていない場合
		} else {
			database_bean.delete("mastered_lecture", mastered_lecture_id); // 履修済み講義データの削除
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("登録の完了") %>
		成績の登録が完了しました。<br />
		<br />

		<a href="<%= utility_bean.getRecordEditPageURL(opening_lecture_id) %>">成績の再編集</a> <a href="<%= utility_bean.getOpeningLectureDataSelectPageURL(opening_lecture_id) %>">講義の選択</a> <a href="<%= utility_bean.getAdminMainPageURL() %>">処理の選択</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">ログアウト</a><br />
		<br />

		<%= html_bean.getFooter() %>
