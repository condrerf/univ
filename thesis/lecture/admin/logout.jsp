<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	utility_bean.releaseMaintainingAdministratorID(); // �����e�i���X���s���Ă���Ǘ���ID�̉��
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("���O�A�E�g����") %>
		���O�A�E�g���������܂����B<br />
		<br />

		<a href="<%= utility_bean.getAdminLoginPageURL() %>">���O�C���y�[�W(�Ǘ��y�[�W)</a>

		<%= html_bean.getFooter() %>
