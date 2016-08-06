<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*"%>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納

	// 管理用ログインページからのアクセスである場合
	if (request.getHeader("referer").equals(utility_bean.getAdminLoginPageURL())) {
		String administrator_id = request.getParameter("administrator_id"); // 入力された管理者IDを格納

		// 管理者IDが入力されていない場合
		if (administrator_id.equals("")) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "管理者ID")).forward(request, response); // エラーページへの転送
		}

		String registered_password = ((DatabaseBean) application.getAttribute("database_bean")).getColumnData("administrator", 0, administrator_id); // 登録パスワードを格納

		// パスワードが取得できなかった場合
		if (registered_password == null) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", "管理者ID")).forward(request, response); // エラーページへの転送
		}

		String inputed_password = request.getParameter("password"); // 入力されたパスワードを格納

		// 入力されたパスワードが登録内容と一致しない場合
		if (!inputed_password.equals(registered_password)) {
			// パスワードが入力されていない場合
			if (inputed_password.equals("")) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "パスワード")).forward(request, response); // エラーページへの転送
			// 入力されている場合
			} else {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("not_accorded_error", "パスワード")).forward(request, response); // エラーページへの転送
			}
		}

		// 入力された管理者IDがメンテナンスを行っている管理者IDと等しくない場合
		if (!administrator_id.equals(utility_bean.getMaintainingAdministratorID())) {
			// UtilityBeanの同期化
			synchronized (utility_bean) {
				// メンテナンス中ではない場合
				if (!utility_bean.isMaintained()) {
					utility_bean.setMaintainingAdministratorID(administrator_id); // メンテナンスを行っている管理者IDの設定
				// メンテナンス中である場合
				} else {
					application.getRequestDispatcher(utility_bean.getErrorPageURI("another_administrator_maintaining_error")).forward(request, response); // エラーページへの転送
				}
			}
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("処理の選択") %>
		行いたい処理を選択して下さい。<br />
		<br />

		・<a href="new_year_delete/index.jsp">新年度到来によるデータの削除</a><br />
		<br />

		・<a href="is_registerable/index.jsp">履修登録の可否の設定</a><br />
		<br />

		・<a href="<%= utility_bean.getDataSelectPageURL() %>?processing_type=record">成績の登録</a><br />
		<br />

		・<a href="<%= utility_bean.getTableSelectPageURL() %>">各種データの編集</a><br />
		<br />

		<a href="./logout.jsp">ログアウト</a><br />

		<%= html_bean.getFooter() %>
