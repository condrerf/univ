<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
%>
<%= html_bean.getHeader("�폜�̎��s�̊m�F") %>
		�V�N�x�����ɂ��f�[�^�̍폜���s���܂��B<br />
		��������s����ƁA���Ƃ�ފw�̗��R�Ŋw�Ђ��������w���f�[�^�Ƌ��N�x�̗��C�o�^�f�[�^���폜����܂��B<br />
		���ꂪ��낵����΁A����[���s]�{�^�����N���b�N���ĉ������B<br />

		<form method="POST" action="./delete.jsp">
			<input type="submit" value="���s" />
		</form>

		<a href="<%= utility_bean.getAdminMainPageURL() %>">�����̑I��</a><br />

		<%= html_bean.getFooter() %>
