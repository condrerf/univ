<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String state = null; // 状態を格納

	// 履修登録が行えるように設定されている場合
	if (request.getParameter("is_registerable").equals("true")) {
		utility_bean.setIsRegisterable(true); // 履修登録が行えるように設定
		state = "行える"; // "行える"を状態に設定
	// 設定されていない場合
	} else {
		utility_bean.setIsRegisterable(false); // 履修登録が行えないように設定
		state = "行えない"; // "行えない"を状態に設定
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("設定の完了") %>
		履修登録を<%= state %>ようにする設定が完了しました。<br />
		<br />

		<a href="<%= utility_bean.getAdminMainPageURL() %>">処理の選択</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">ログアウト</a><br />

		<%= html_bean.getFooter() %>
