<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String[] select_box_ids = {"true", "false"}; // セレクトボックスのIDを格納
	String[] select_box_outlines = {"行える", "行えない"}; // セレクトボックスの見出しを格納
	int select_box_index; // 選択欄のインデックスを格納

	// 履修登録が可能であると設定されている場合
	if (utility_bean.isRegisterable()) {
		select_box_index = 0; // 0を選択欄のインデックスに設定
	// 設定されていない場合
	} else {
		select_box_index = 1; // 1を選択欄のインデックスに設定
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("可否の選択") %>
		履修登録を行えるようにするかどうかを選択し、[設定]ボタンをクリックして下さい。<br />
		<br />
		(現在は<b><%= select_box_outlines[select_box_index] %></b>ように設定されています)<br />
		<br />

		<form method="POST" action="./regist.jsp">
			開講講義を<%= html_bean.getSelectBox("is_registerable", select_box_ids[select_box_index], select_box_ids, select_box_outlines, 3) %>ようにする
			<input type="submit" value="設定" />
		</form>

		<a href="<%= utility_bean.getAdminMainPageURL() %>">処理の選択</a><br />

		<%= html_bean.getFooter() %>
