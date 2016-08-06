<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	String error_type = request.getParameter("error_type"); // 指定されたエラーの種類を格納
	String error_name = null; // エラー名を格納
	String error_message = null; // エラーメッセージを格納
	String error_parameter_name = request.getParameter("error_parameter_name"); // エラーパラメータ名を格納
	String error_parameter_value = request.getParameter("error_parameter_value"); // エラーパラメータ値を格納

	// 指定されたエラーの種類がアクセスエラーである場合
	if (error_type.equals("access_error")) {
		error_name = "アクセスエラー"; // エラー名の設定
		error_message = "不正なアクセスです。リンクを利用して正しくアクセスして下さい。"; // エラーメッセージの設定
	// 履修登録拒否エラーである場合
	} else if (error_type.equals("registration_denied_error")) {
		error_name = "履修登録拒否エラー"; // エラー名の設定
		error_message = "履修登録が行えないように設定されており、手続きを行うことができません。"; // エラーメッセージの設定
	// メンテナンスエラーである場合
	} else if (error_type.equals("maintenance_error")) {
		error_name = "メンテナンスによるエラー"; // エラー名の設定
		error_message = "現在は管理者がメンテナンスを行っているため、手続きが行えません。しばらく後に再読み込みを試行して下さい。"; // エラーメッセージの設定
	// 文字未入力エラーである場合
	} else if (error_type.equals("no_character_inputed_error")) {
		error_name = "文字未入力エラー"; // エラー名の設定
		error_message = error_parameter_name + "が入力されていません。"; // エラーメッセージの設定
	// 桁数エラーである場合
	} else if (error_type.equals("length_error")) {
		error_name = "桁数エラー"; // エラー名の設定
		error_message = error_parameter_name + "が指定された桁数で入力されていません。"; // エラーメッセージの設定
	// 文字混入エラーである場合
	} else if (error_type.equals("character_included_error")) {
		error_name = "文字混入エラー"; // エラー名の設定
		error_message = error_parameter_name + "に数字以外の文字が含まれています。"; // エラーメッセージの設定
	// 未登録エラーである場合
	} else if (error_type.equals("not_registered_error")) {
		error_name = "未登録エラー"; // エラー名の設定
		error_message = "指定された" + error_parameter_name + "に該当するデータは登録されていません。"; // エラーメッセージの設定
	// パスワード未変更エラーである場合
	} else if (error_type.equals("password_not_changed_error")) {
		error_name = "パスワード未変更エラー"; // エラー名の設定
		error_message = "パスワードが仮登録状態のままです。変更の手続きを行って下さい。"; // エラーメッセージの設定
	// 不一致エラーである場合
	} else if (error_type.equals("not_accorded_error")) {
		error_name = "不一致エラー"; // エラー名の設定
		error_message = "指定された" + error_parameter_name + "が登録内容と一致しません。"; // エラーメッセージの設定
	// 単位エラーである場合
	} else if (error_type.equals("credit_error")) {
		error_name = "単位エラー"; // エラー名の設定

		// エラーパラメータ名が指定されていない場合
		if (error_parameter_name == null) {
			error_message = "春学期に登録された講義の単位の合計が、1年に履修可能な単位の最大値である56に達しています。この値を超える登録はできません。"; // エラーメッセージの設定
		} else {
			error_message = error_parameter_name + "が、履修可能な単位の最大値を超えています。"; // エラーメッセージの設定
		}
	// 別コースの単位エラーである場合
	} else if (error_type.equals("another_course_credit_error")) {
		error_name = "別コースの単位エラー"; // エラー名の設定
		error_message = error_parameter_name + "が、別コースの履修可能な単位の最大値である30を超えています。"; // エラーメッセージの設定
	// 生年月日使用エラーである場合
	} else if (error_type.equals("birthday_used_error")) {
		error_name = "生年月日使用エラー"; // エラー名の設定
		error_message = "パスワードに生年月日が使用されています。セキュリティ上から許可できません。"; // エラーメッセージの設定
	// 別の管理者によるメンテナンスエラーである場合
	} else if (error_type.equals("another_administrator_maintaining_error")) {
		error_name = "別の管理者のメンテナンスによるエラー"; // エラー名の設定
		error_message = "現在は別の管理者がメンテナンスを行っているため、手続きが行えません。しばらく後に再読み込みを試行して下さい。"; // エラーメッセージの設定
	// データ未指定エラーである場合
	} else if (error_type.equals("no_data_specified_error")) {
		error_name = "データ未指定エラー"; // エラー名の設定
		error_message = "削除するデータが指定されていません。"; // エラーメッセージの設定
	// 削除エラーである場合
	} else if (error_type.equals("delete_error")) {
		error_name = "削除エラー"; // エラー名の設定
		error_message = "指定された" + error_parameter_name + "は他のテーブルで使用されているために削除が行えません。"; // エラーメッセージの設定
	}

	String error_content = null; // エラー内容を格納

	// エラーパラメータ値が指定されていない場合
	if (error_parameter_value != null) {
		error_content = "(エラーが発生した" + error_parameter_name + ": \"<b>" + error_parameter_value + "</b>\")<br />"; // エラー内容の設定
	// 指定されている場合
	} else {
		error_content = ""; // エラー内容を空白に設定
	}

	String link = null; // リンクを格納

	// 指定されたエラーの種類がアクセスエラーである場合
	if (error_type.equals("access_error")) {
		String link_url = null; // リンクのURLを格納
		UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納

		// 要求を受けたページが管理ディレクトリに属している場合
		if (request.getRequestURL().toString().indexOf(utility_bean.getAdminDirectoryURL()) != -1) {
			link_url = utility_bean.getAdminLoginPageURL(); // 管理用のログインページをリンクのURLの設定
		// 属していない場合
		} else {
			link_url = utility_bean.getLoginPageURL(); // ログインページをリンクのURLの設定
		}

		link = "<a href=\"" + link_url + "\">ログインページ</a><br />"; // リンクの取得
	// その他のエラーである場合
	} else {
		link = ""; // リンクを空白に設定
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader(error_name) %>
		<font color="red"><%= error_message %></font><br />
		<%= error_content %>
		<br />

		<%= link %>

		<%= html_bean.getFooter() %>
