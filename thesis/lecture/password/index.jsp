<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�p�X���[�h�̕ύX") %>
		�w�Дԍ��Ƌ��p�X���[�h�A�V�p�X���[�h����͂��A[�ύX]�{�^�����N���b�N���ĉ������B<br>

		<form method="POST" action="./regist.jsp">
			<table border="0">
				<tr>
					<th bgcolor="#dddddd" align="right">�w�Дԍ�</th>
					<td><input type="text" name="student_id" size="10" />(5��)</td>
				</tr>
				<tr>
					<th bgcolor="#dddddd" align="right">���p�X���[�h</th>
					<td><input type="password" name="old_password" size="10" />(���o�^��Ԃł͒a����(xx/xx��4����)�ɐݒ肳��Ă��܂�)</td>
				</tr>
				<tr>
					<th bgcolor="#dddddd" align="right">�V�p�X���[�h</th>
					<td><input type="password" name="new_password" size="10" />(�a����(xx/xx��4����)�͕s��)</td>
				</tr>
			</table>

			<td colspan="2">
				<input type="submit" value="�ύX" />
				<input type="reset" value="���" />
			</td>
		</form>

		<a href="<%= ((UtilityBean) application.getAttribute("utility_bean")).getLoginPageURL() %>">���O�C���y�[�W</a>

		<%= html_bean.getFooter() %>