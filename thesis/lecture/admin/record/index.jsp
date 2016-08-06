<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.Vector" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String opening_lecture_id = request.getParameter("id"); // �w�肳�ꂽ�J�u�u�`ID���i�[
	String[] student_ids = utility_bean.getStudentIDs(opening_lecture_id); // �w�Дԍ����i�[

	// �w�Дԍ����擾�ł��Ȃ������ꍇ
	if (student_ids == null) {
		application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", "�J�u�u�`ID", opening_lecture_id)).forward(request, response); // �G���[�y�[�W�ւ̓]��
	}

	String[] scores = new String[student_ids.length]; // �_�����i�[
	String lecture_id = database_bean.getColumnData("opening_lecture", 1, opening_lecture_id); // �w�肳�ꂽ�J�u�u�`ID�ɊY������u�`ID���i�[

	// �e�w���̓_���̎擾
	for (int i = 0; i < student_ids.length; i++) {
		scores[i] = database_bean.getColumnData("mastered_lecture", 1, student_ids[i] + lecture_id);
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("���т̕ҏW") %>
		(�u�`��: <b><%= database_bean.getColumnData("lecture", 1, lecture_id)%></b>)<br />
		<br />

		�e�w���ɑ΂��ē_����ݒ肵�A[���M]�{�^�����N���b�N���ĉ������B<br />
		<form method="POST" action="./confirm.jsp">
			<input type="hidden" name="opening_lecture_id" value="<%= opening_lecture_id %>" />
			<%= html_bean.getOpeningLectureScoreList("edit", student_ids, scores) %>
			<input type="submit" value="���M" />
		</form>

		<a href="<%= utility_bean.getOpeningLectureDataSelectPageURL(opening_lecture_id) %>">�u�`�̑I��</a><br />

		<%= html_bean.getFooter() %>
