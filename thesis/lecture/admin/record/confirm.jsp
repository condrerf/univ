<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String opening_lecture_id = request.getParameter("opening_lecture_id"); // 指定された開講講義IDを格納
	String[] student_ids = utility_bean.getStudentIDs(opening_lecture_id); // 学籍番号を格納
	String[] scores = utility_bean.getFormData(student_ids, request); // 点数を格納

	// 点数が入力されているかどうかのチェック
	for (int i = 0; i < student_ids.length; i++) {
		// 現在アクセスしている点数が空白である場合
		if (scores[i].equals("")) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "学籍番号\"" + student_ids[i] + "\"の点数")).forward(request, response); // エラーページへの転送
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("登録内容の確認") %>
		登録内容を確認し、問題がなければ[登録]ボタンをクリックして下さい。<br />
		(60点未満(不可)の学生のデータは履修が認められないため登録されません)<br />

		<form method="POST" action="./regist.jsp">
			<input type="hidden" name="opening_lecture_id" value="<%= opening_lecture_id %>" />
			<%= html_bean.getOpeningLectureScoreList("confirm", student_ids, scores) %>
			<input type="submit" value="登録" />
		</form>

		<a href="<%= request.getHeader("referer") %>">戻る</a><br />

		<%= html_bean.getFooter() %>
