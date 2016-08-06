<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	String table_name = request.getParameter("table_name"); // �w�肳�ꂽ�e�[�u�������i�[
	String[] ids = request.getParameterValues("id"); // �w�肳�ꂽID���i�[

	// �e�s�f�[�^�̍폜
	for(int i = 0; i < ids.length; i++) {
		database_bean.delete(table_name, ids[i]);
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String japanese_table_name = utility_bean.getJapaneseWord(table_name); // �w�肳�ꂽ�e�[�u�����̓��{�ꖼ���i�[
%>
<%= html_bean.getHeader("�폜�̊���") %>
		<%= japanese_table_name %>�f�[�^�̍폜���������܂����B<br />
		<br />
		<a href="<%= utility_bean.getDataSelectPageURL(table_name, utility_bean.getDataAffiliationNumber(table_name, ids[0])) %>"><%= japanese_table_name %>�f�[�^�̑I��</a> <a href="<%= utility_bean.getTableSelectPageURL() %>">�e�[�u���̑I��</a> <a href="<%= utility_bean.getAdminMainPageURL() %>">�����̑I��</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">���O�A�E�g</a><br />

		<%= html_bean.getFooter() %>
