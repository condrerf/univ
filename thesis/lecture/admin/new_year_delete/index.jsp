<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
%>
<%= html_bean.getHeader("削除の実行の確認") %>
		新年度到来によるデータの削除を行います。<br />
		これを実行すると、卒業や退学の理由で学籍を失った学生データと旧年度の履修登録データが削除されます。<br />
		それがよろしければ、下の[実行]ボタンをクリックして下さい。<br />

		<form method="POST" action="./delete.jsp">
			<input type="submit" value="実行" />
		</form>

		<a href="<%= utility_bean.getAdminMainPageURL() %>">処理の選択</a><br />

		<%= html_bean.getFooter() %>
