<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String[] ids = request.getParameterValues("id"); // ID���i�[

	// ID���w�肳��Ă��Ȃ��ꍇ
	if(ids == null) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("no_data_specified_error")).forward(request, response); // �G���[�y�[�W�ւ̓]��
	}

	String table_name = request.getParameter("table_name"); // �w�肳�ꂽ�e�[�u�������i�[
	String button_name = "���s"; // �{�^���̖��O���i�[
	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�폜���e�̊m�F") %>
		�폜����<%= utility_bean.getJapaneseWord(table_name) %>�f�[�^�̓��e���m�F���A��肪�Ȃ����[<%= button_name %>]�{�^�����N���b�N���ĉ������B<br />
		<br />

		<%= html_bean.getDataList("delete", table_name, ids, button_name) %>

		<a href="<%= utility_bean.getDataSelectPageURL(table_name, utility_bean.getDataAffiliationNumber(table_name, ids[0])) %>">�߂�</a><br />

		<%= html_bean.getFooter() %>
