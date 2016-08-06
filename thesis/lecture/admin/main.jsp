<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*"%>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[

	// �Ǘ��p���O�C���y�[�W����̃A�N�Z�X�ł���ꍇ
	if (request.getHeader("referer").equals(utility_bean.getAdminLoginPageURL())) {
		String administrator_id = request.getParameter("administrator_id"); // ���͂��ꂽ�Ǘ���ID���i�[

		// �Ǘ���ID�����͂���Ă��Ȃ��ꍇ
		if (administrator_id.equals("")) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "�Ǘ���ID")).forward(request, response); // �G���[�y�[�W�ւ̓]��
		}

		String registered_password = ((DatabaseBean) application.getAttribute("database_bean")).getColumnData("administrator", 0, administrator_id); // �o�^�p�X���[�h���i�[

		// �p�X���[�h���擾�ł��Ȃ������ꍇ
		if (registered_password == null) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", "�Ǘ���ID")).forward(request, response); // �G���[�y�[�W�ւ̓]��
		}

		String inputed_password = request.getParameter("password"); // ���͂��ꂽ�p�X���[�h���i�[

		// ���͂��ꂽ�p�X���[�h���o�^���e�ƈ�v���Ȃ��ꍇ
		if (!inputed_password.equals(registered_password)) {
			// �p�X���[�h�����͂���Ă��Ȃ��ꍇ
			if (inputed_password.equals("")) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "�p�X���[�h")).forward(request, response); // �G���[�y�[�W�ւ̓]��
			// ���͂���Ă���ꍇ
			} else {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("not_accorded_error", "�p�X���[�h")).forward(request, response); // �G���[�y�[�W�ւ̓]��
			}
		}

		// ���͂��ꂽ�Ǘ���ID�������e�i���X���s���Ă���Ǘ���ID�Ɠ������Ȃ��ꍇ
		if (!administrator_id.equals(utility_bean.getMaintainingAdministratorID())) {
			// UtilityBean�̓�����
			synchronized (utility_bean) {
				// �����e�i���X���ł͂Ȃ��ꍇ
				if (!utility_bean.isMaintained()) {
					utility_bean.setMaintainingAdministratorID(administrator_id); // �����e�i���X���s���Ă���Ǘ���ID�̐ݒ�
				// �����e�i���X���ł���ꍇ
				} else {
					application.getRequestDispatcher(utility_bean.getErrorPageURI("another_administrator_maintaining_error")).forward(request, response); // �G���[�y�[�W�ւ̓]��
				}
			}
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�����̑I��") %>
		�s������������I�����ĉ������B<br />
		<br />

		�E<a href="new_year_delete/index.jsp">�V�N�x�����ɂ��f�[�^�̍폜</a><br />
		<br />

		�E<a href="is_registerable/index.jsp">���C�o�^�̉ۂ̐ݒ�</a><br />
		<br />

		�E<a href="<%= utility_bean.getDataSelectPageURL() %>?processing_type=record">���т̓o�^</a><br />
		<br />

		�E<a href="<%= utility_bean.getTableSelectPageURL() %>">�e��f�[�^�̕ҏW</a><br />
		<br />

		<a href="./logout.jsp">���O�A�E�g</a><br />

		<%= html_bean.getFooter() %>
