<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String student_id = utility_bean.getFullDigitAlteredStudentID(request.getParameter("student_id")); // ���͂��ꂽ�w�Дԍ��𔼊p�ɕϊ����Ċi�[
	utility_bean.checkStudentID(student_id, request, response); // �w�Дԍ��̃`�F�b�N
	String[] student_data = database_bean.getRowData("student", student_id); // �w���f�[�^���i�[

	// �w���f�[�^���擾�ł��Ȃ������ꍇ
	if (student_data == null) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", "�w�Дԍ�")).forward(request, response); // �G���[�y�[�W�ւ̓]��
	}

	String old_password = request.getParameter("old_password"); // ���͂��ꂽ���p�X���[�h���i�[

	// ���p�X���[�h�����͂���Ă��Ȃ��ꍇ
	if(old_password.equals("")) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "���p�X���[�h")).forward(request, response); // �G���[�y�[�W�ւ̓]��
	}

	// ���͂��ꂽ���p�X���[�h���o�^���e�ƈ�v���Ȃ��ꍇ
	if(!old_password.equals(student_data[2])) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("not_accorded_error", "���p�X���[�h")).forward(request, response); // �G���[�y�[�W�ւ̓]��
	}

	String new_password = request.getParameter("new_password"); // ���͂��ꂽ�V�p�X���[�h���i�[

	// �V�p�X���[�h�����͂���Ă��Ȃ��ꍇ
	if(new_password.equals("")) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "�V�p�X���[�h")).forward(request, response); // �G���[�y�[�W�ւ̓]��
	}

	// �V�p�X���[�h�����N�����Ɠ������ꍇ
	if(new_password.equals(student_data[3])) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("birthday_used_error")).forward(request, response); // �G���[�y�[�W�ւ̓]��
	}

	// �p�X���[�h�̍X�V
	student_data[2] = new_password;
	database_bean.update("student", student_id, student_data);

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�葱������") %>
		<%= student_data[1] %>����̃p�X���[�h��"<b><%= new_password %></b>"�ɐݒ肵�܂����B�Y��Ȃ��悤�ɂ��ĉ������B<br />
		<br />

		<a href="<%= request.getHeader("referer") %>">�߂�</a> <a href="<%= utility_bean.getLoginPageURL() %>">���O�C���y�[�W</a><br />

		<%= html_bean.getFooter() %>
