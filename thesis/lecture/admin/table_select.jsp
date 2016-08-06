<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	final String[] TABLE_NAMES = {"course", "lecture_affiliation", "chair", "student", "lecturer", "lecture", "opening_lecture", "opening_lecture_lecturer"}; // �e�[�u�������i�[
	String[] japanese_table_names = new String[TABLE_NAMES.length]; // ���{��̃e�[�u�������i�[

	// ���{��̃e�[�u�����̎擾
	for (int i = 0; i < TABLE_NAMES.length; i++) {
		japanese_table_names[i] = utility_bean.getJapaneseWord(TABLE_NAMES[i]);
	}
%>
<%= html_bean.getHeader("�e�[�u���̑I��") %>
		�ҏW�������f�[�^���������Ă���e�[�u����I�����ĉ������B<br />
		<br />

		<form method="POST" action="<%= utility_bean.getDataSelectPageURL() %>">
			<%= html_bean.getSelectBox("table_name", null, TABLE_NAMES, japanese_table_names, 3) %>�e�[�u��
			<input type="submit" value="�ݒ�" />
		</form>

		<a href="<%= utility_bean.getAdminMainPageURL() %>">�����̑I��</a><br />

		<%= html_bean.getFooter() %>
