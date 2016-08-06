<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.Calendar" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String opening_lecture_id = request.getParameter("opening_lecture_id"); // �w�肳�ꂽ�J�u�u�`ID���i�[
	String[] student_ids = utility_bean.getStudentIDs(opening_lecture_id); // �w�Дԍ����i�[
	String[] scores = utility_bean.getFormData(student_ids, request); // �_�����i�[
	String lecture_id = database_bean.getColumnData("opening_lecture", 1, opening_lecture_id); // �w�肳�ꂽ�J�u�u�`ID�ɊY������u�`ID���i�[
	String lecture_affiliation_number = "" + (Integer.parseInt(opening_lecture_id.substring(2, 3)) / 5); // �u�`�����ԍ����i�[

	// �e���уf�[�^�̓o�^
	for (int i = 0; i < student_ids.length; i++) {
		String mastered_lecture_id = student_ids[i] + lecture_id; // ���C�ςݍu�`ID���i�[
		int mastered_year = Calendar.getInstance().get(Calendar.YEAR); // ���C�N���i�[

		// ���݂̌���1������3���̊Ԃł���ꍇ
		if (Calendar.getInstance().get(Calendar.MONTH) < 3) {
			mastered_year--; // ���C�N�̌��Z
		}

		String[] mastered_lecture_data = {mastered_lecture_id, scores[i], "" + mastered_year, lecture_affiliation_number}; // ���C�ςݍu�`�f�[�^���i�[

		// ���݃A�N�Z�X���Ă���v�f�Ɠ������C�ςݍu�`ID�œo�^���ꂽ���C�ςݍu�`�f�[�^�����݂��Ȃ��ꍇ
		if (database_bean.getRowData("mastered_lecture", mastered_lecture_id) == null) {
			// ���݃A�N�Z�X���Ă���v�f�ɓ_�����ݒ肳��Ă���ꍇ(60�_�ȏ�̃f�[�^)
			if (scores[i] != null) {
				database_bean.insert("mastered_lecture", mastered_lecture_data); // ���C�ςݍu�`�f�[�^�̑}��
			}
		// ���C�ςݍu�`�f�[�^�����݂��Ă���A�_�����ݒ肳��Ă���ꍇ
		} else if (scores[i] != null) {
			database_bean.update("mastered_lecture", mastered_lecture_id, mastered_lecture_data); // ���C�ςݍu�`�f�[�^�̍X�V
		// ���C�ςݍu�`�f�[�^�����݂��Ă���A�_�����ݒ肳��Ă��Ȃ��ꍇ
		} else {
			database_bean.delete("mastered_lecture", mastered_lecture_id); // ���C�ςݍu�`�f�[�^�̍폜
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�o�^�̊���") %>
		���т̓o�^���������܂����B<br />
		<br />

		<a href="<%= utility_bean.getRecordEditPageURL(opening_lecture_id) %>">���т̍ĕҏW</a> <a href="<%= utility_bean.getOpeningLectureDataSelectPageURL(opening_lecture_id) %>">�u�`�̑I��</a> <a href="<%= utility_bean.getAdminMainPageURL() %>">�����̑I��</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">���O�A�E�g</a><br />
		<br />

		<%= html_bean.getFooter() %>
