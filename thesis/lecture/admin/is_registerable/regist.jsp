<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String state = null; // ��Ԃ��i�[

	// ���C�o�^���s����悤�ɐݒ肳��Ă���ꍇ
	if (request.getParameter("is_registerable").equals("true")) {
		utility_bean.setIsRegisterable(true); // ���C�o�^���s����悤�ɐݒ�
		state = "�s����"; // "�s����"����Ԃɐݒ�
	// �ݒ肳��Ă��Ȃ��ꍇ
	} else {
		utility_bean.setIsRegisterable(false); // ���C�o�^���s���Ȃ��悤�ɐݒ�
		state = "�s���Ȃ�"; // "�s���Ȃ�"����Ԃɐݒ�
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�ݒ�̊���") %>
		���C�o�^��<%= state %>�悤�ɂ���ݒ肪�������܂����B<br />
		<br />

		<a href="<%= utility_bean.getAdminMainPageURL() %>">�����̑I��</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">���O�A�E�g</a><br />

		<%= html_bean.getFooter() %>
