<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
%>
<%= html_bean.getHeader("���O�A�E�g") %>
		�ǂ��������l�ł����B<br />
		�u���E�U�̓A�N�Z�X�����L�����Ă���܂��̂ŁA���݃p�\�R���𑼂̕��Ƌ��p����Ă���ꍇ�͂��̃E�B���h�E����ĉ������B<br />
		<br />
		<a href="<%= utility_bean.getLoginPageURL() %>">���O�C���y�[�W</a> <a href="<%= utility_bean.getPasswordChangeProcedurePageURL() %>">�p�X���[�h�̕ύX</a><br />

		<%= html_bean.getFooter() %>
