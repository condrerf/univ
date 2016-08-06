<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String student_id = utility_bean.getFullDigitAlteredStudentID(request.getParameter("student_id")); // 入力された学籍番号を半角に変換して格納
	utility_bean.checkStudentID(student_id, request, response); // 学籍番号のチェック
	String[] student_data = database_bean.getRowData("student", student_id); // 学生データを格納

	// 学生データが取得できなかった場合
	if (student_data == null) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", "学籍番号")).forward(request, response); // エラーページへの転送
	}

	String old_password = request.getParameter("old_password"); // 入力された旧パスワードを格納

	// 旧パスワードが入力されていない場合
	if(old_password.equals("")) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "旧パスワード")).forward(request, response); // エラーページへの転送
	}

	// 入力された旧パスワードが登録内容と一致しない場合
	if(!old_password.equals(student_data[2])) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("not_accorded_error", "旧パスワード")).forward(request, response); // エラーページへの転送
	}

	String new_password = request.getParameter("new_password"); // 入力された新パスワードを格納

	// 新パスワードが入力されていない場合
	if(new_password.equals("")) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "新パスワード")).forward(request, response); // エラーページへの転送
	}

	// 新パスワードが生年月日と等しい場合
	if(new_password.equals(student_data[3])) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("birthday_used_error")).forward(request, response); // エラーページへの転送
	}

	// パスワードの更新
	student_data[2] = new_password;
	database_bean.update("student", student_id, student_data);

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("手続き完了") %>
		<%= student_data[1] %>さんのパスワードを"<b><%= new_password %></b>"に設定しました。忘れないようにして下さい。<br />
		<br />

		<a href="<%= request.getHeader("referer") %>">戻る</a> <a href="<%= utility_bean.getLoginPageURL() %>">ログインページ</a><br />

		<%= html_bean.getFooter() %>
