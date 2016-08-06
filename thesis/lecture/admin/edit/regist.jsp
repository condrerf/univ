<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String table_name = request.getParameter("table_name"); // �w�肳�ꂽ�e�[�u�������i�[
	String[] form_data = utility_bean.getFormData(database_bean.getColumnNames(table_name), request); // �t�H�[���f�[�^���i�[

	// �f�[�^�����o�^�ł���ꍇ
	if(database_bean.getRowData(table_name, form_data[0]) == null) {
		database_bean.insert(table_name, form_data); // �f�[�^�̑}��
	// �X�V���s���ꍇ
	} else {
		database_bean.update(table_name, form_data[0], form_data); // �f�[�^�̍X�V
	}

	// �o�^�f�[�^���J�u�u�`�f�[�^�ŁA�K�v�J�u�u�`ID���ݒ肳��Ă���ꍇ
	if (table_name.equals("opening_lecture") && form_data[2] != null) {
		String[] necessary_opening_lecture_data = database_bean.getRowData(table_name, form_data[2]); // �K�v�J�u�u�`�f�[�^���i�[

		// �K�v�J�u�u�`�f�[�^�̕K�v�J�u�u�`ID���ݒ肳��Ă��Ȃ��ꍇ
		if (necessary_opening_lecture_data[2] == null) {
			necessary_opening_lecture_data[2] = form_data[0]; // �o�^�f�[�^�̊J�u�u�`ID��K�v�J�u�u�`�f�[�^�̕K�v�J�u�u�`ID�ɐݒ�
			database_bean.update(table_name, form_data[2], necessary_opening_lecture_data); // �K�v�J�u�u�`�f�[�^�̍X�V
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	String japanese_table_name = utility_bean.getJapaneseWord(table_name); // �w�肳�ꂽ�e�[�u�����̓��{�ꖼ���i�[
	String data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, form_data[0]); // �f�[�^�����ԍ����i�[
%>
<%= html_bean.getHeader("�o�^�̊���") %>
		<%= japanese_table_name %>�f�[�^�̓o�^���������܂����B<br />
		<br />

		<a href="<%= utility_bean.getDataEditPageURL(table_name, form_data[0], null) %>">�o�^�f�[�^�̍ĕҏW</a> <a href="<%= utility_bean.getDataEditPageURL(table_name, null, data_affiliation_number) %>"><%= japanese_table_name %>�f�[�^�̐V�K�o�^</a> <a href="<%= utility_bean.getDataSelectPageURL(table_name, data_affiliation_number) %>"><%= japanese_table_name %>�f�[�^�̑I��</a> <a href="<%= utility_bean.getTableSelectPageURL() %>">�e�[�u���̑I��</a> <a href="<%= utility_bean.getAdminMainPageURL() %>">�����̑I��</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">���O�A�E�g</a><br />

		<%= html_bean.getFooter() %>
