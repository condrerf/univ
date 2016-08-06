<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("パスワードの変更") %>
		学籍番号と旧パスワード、新パスワードを入力し、[変更]ボタンをクリックして下さい。<br>

		<form method="POST" action="./regist.jsp">
			<table border="0">
				<tr>
					<th bgcolor="#dddddd" align="right">学籍番号</th>
					<td><input type="text" name="student_id" size="10" />(5桁)</td>
				</tr>
				<tr>
					<th bgcolor="#dddddd" align="right">旧パスワード</th>
					<td><input type="password" name="old_password" size="10" />(仮登録状態では誕生日(xx/xxの4文字)に設定されています)</td>
				</tr>
				<tr>
					<th bgcolor="#dddddd" align="right">新パスワード</th>
					<td><input type="password" name="new_password" size="10" />(誕生日(xx/xxの4文字)は不可)</td>
				</tr>
			</table>

			<td colspan="2">
				<input type="submit" value="変更" />
				<input type="reset" value="取消" />
			</td>
		</form>

		<a href="<%= ((UtilityBean) application.getAttribute("utility_bean")).getLoginPageURL() %>">ログインページ</a>

		<%= html_bean.getFooter() %>