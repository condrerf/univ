<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.Vector" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String opening_lecture_id = request.getParameter("id"); // 指定された開講講義IDを格納
	String[] student_ids = utility_bean.getStudentIDs(opening_lecture_id); // 学籍番号を格納

	// 学籍番号が取得できなかった場合
	if (student_ids == null) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", "開講講義ID", opening_lecture_id)).forward(request, response); // エラーページへの転送
	}

	String[] scores = new String[student_ids.length]; // 点数を格納
	String lecture_id = database_bean.getColumnData("opening_lecture", 1, opening_lecture_id); // 指定された開講講義IDに該当する講義IDを格納

	// 各学生の点数の取得
	for (int i = 0; i < student_ids.length; i++) {
		scores[i] = database_bean.getColumnData("mastered_lecture", 1, student_ids[i] + lecture_id);
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("成績の編集") %>
		(講義名: <b><%= database_bean.getColumnData("lecture", 1, lecture_id)%></b>)<br />
		<br />

		各学生に対して点数を設定し、[送信]ボタンをクリックして下さい。<br />
		<form method="POST" action="./confirm.jsp">
			<input type="hidden" name="opening_lecture_id" value="<%= opening_lecture_id %>" />
			<%= html_bean.getOpeningLectureScoreList("edit", student_ids, scores) %>
			<input type="submit" value="送信" />
		</form>

		<a href="<%= utility_bean.getOpeningLectureDataSelectPageURL(opening_lecture_id) %>">講義の選択</a><br />

		<%= html_bean.getFooter() %>
