<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String student_id = request.getParameter("student_id"); // �w�Дԍ����i�[
	String[][] opening_lecture_ids = utility_bean.getOpeningLectureIDs(request); // �J�u�u�`ID���i�[
	int semester_number = utility_bean.getSemesterNumber(); // �w���ԍ����i�[

	// �J�u�u�`�f�[�^�̓o�^
	registration: for (int i = 0; i < opening_lecture_ids.length; i++) {
		for (int j = 0; j < opening_lecture_ids[i].length; j++) {
			// �������W���u�`�̍ŏI�v�f�܂ŏI���Ă���ꍇ
			if (i == 5 && j == 5) {
				break registration; // ���[�v���甲����
			}

			String registration_opening_lecture_id = student_id + semester_number + i + j; // �o�^�J�u�u�`ID���i�[
			String[] registered_opening_lecture_data = database_bean.getRowData("registered_opening_lecture", registration_opening_lecture_id); // �o�^���ꂽ�J�u�u�`�Ńf�[�^���i�[

			// ���݃A�N�Z�X���Ă�������ɊJ�u�u�`���w�肳��Ă���ꍇ
			if (opening_lecture_ids[i][j] != null) {
				String[] registration_opening_lecture_data = {registration_opening_lecture_id, opening_lecture_ids[i][j]}; // �o�^�J�u�u�`�f�[�^���i�[

				// ���݃A�N�Z�X���Ă�������Ƀf�[�^���o�^����Ă���ꍇ
				if (registered_opening_lecture_data != null) {
					database_bean.update("registered_opening_lecture", registration_opening_lecture_id, registration_opening_lecture_data); // �J�u�u�`�f�[�^�̍X�V
				// �o�^����Ă��Ȃ��ꍇ
				} else {
					database_bean.insert("registered_opening_lecture", registration_opening_lecture_data); // �J�u�u�`�f�[�^�̓o�^
				}
			// �J�u�u�`���w�肳��Ă��炸�A���̓����Ƀf�[�^���o�^����Ă���ꍇ
			} else if (registered_opening_lecture_data != null) {
				database_bean.delete("registered_opening_lecture", registration_opening_lecture_id); // �J�u�u�`�f�[�^�̍폜
			}
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�o�^����") %>
		�f�[�^�̓o�^���������܂����B<br />
		<br />
		<a href="<%= utility_bean.getMainPageURL(student_id) %>">���C�u�`�̑I��</a> <a href="<%= utility_bean.getLogoutPageURL() %>">���O�A�E�g</a><br />

		<%= html_bean.getFooter() %>
