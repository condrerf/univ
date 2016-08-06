<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String opening_lecture_id = request.getParameter("opening_lecture_id"); // �w�肳�ꂽ�J�u�u�`ID���i�[
	String[] student_ids = utility_bean.getStudentIDs(opening_lecture_id); // �w�Дԍ����i�[
	String[] scores = utility_bean.getFormData(student_ids, request); // �_�����i�[

	// �_�������͂���Ă��邩�ǂ����̃`�F�b�N
	for (int i = 0; i < student_ids.length; i++) {
		// ���݃A�N�Z�X���Ă���_�����󔒂ł���ꍇ
		if (scores[i].equals("")) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "�w�Дԍ�\"" + student_ids[i] + "\"�̓_��")).forward(request, response); // �G���[�y�[�W�ւ̓]��
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�o�^���e�̊m�F") %>
		�o�^���e���m�F���A��肪�Ȃ����[�o�^]�{�^�����N���b�N���ĉ������B<br />
		(60�_����(�s��)�̊w���̃f�[�^�͗��C���F�߂��Ȃ����ߓo�^����܂���)<br />

		<form method="POST" action="./regist.jsp">
			<input type="hidden" name="opening_lecture_id" value="<%= opening_lecture_id %>" />
			<%= html_bean.getOpeningLectureScoreList("confirm", student_ids, scores) %>
			<input type="submit" value="�o�^" />
		</form>

		<a href="<%= request.getHeader("referer") %>">�߂�</a><br />

		<%= html_bean.getFooter() %>
