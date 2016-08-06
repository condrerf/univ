<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String processing_type = request.getParameter("processing_type"); // �w�肳�ꂽ�����̎�ނ��i�[
	String table_name = null; // �e�[�u�������i�[

	// �����̎�ނ��w�肳��Ă���ꍇ
	if (processing_type != null) {
		table_name = "opening_lecture"; // �J�u�u�`�e�[�u�����e�[�u�����ɐݒ�
	// �w�肳��Ă��Ȃ��ꍇ
	} else {
		processing_type = "edit"; // "edit"�������̎�ނɐݒ�
		table_name = request.getParameter("table_name"); // �w�肳�ꂽ�e�[�u�����̎擾
	}

	String data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, request); // �w�肳�ꂽ�f�[�^�����ԍ����i�[
	String data_affiliation_number_name = utility_bean.getDataAffiliationNumberName(table_name); // �f�[�^�����ԍ������i�[
	String[] ids = null; // �w�肳�ꂽ�e�[�u�����ƃf�[�^�����ԍ��ɏ�������f�[�^��ID���i�[

	// �w�肳�ꂽ�e�[�u�������f�[�^�����ԍ��̂Ȃ��e�[�u���ł��邩�A�f�[�^�����ԍ����擾�ł����ꍇ
	if (data_affiliation_number_name == null || data_affiliation_number != null) {
		ids = ((DatabaseBean) application.getAttribute("database_bean")).getColumnDataArray(table_name, 0, data_affiliation_number); // ID�̎擾
	}

	String[][] data_affiliation_number_form_component_data = utility_bean.getDataAffiliationNumberFormComponentData(table_name); // �f�[�^�����ԍ��̃t�H�[���\���v�f�f�[�^���i�[

	// �f�[�^�����ԍ����擾�ł��A���̃f�[�^�����ԍ��ɏ�������f�[�^���w�肳�ꂽ�e�[�u���ɑ��݂��Ȃ��ꍇ
	if (data_affiliation_number != null && ids == null) {
		String error_parameter_value = null; // �G���[�p�����[�^�l���i�[

		// �G���[�p�����[�^�l�̎擾
		for (int i = 0; i < data_affiliation_number_form_component_data[0].length; i++) {
			// ���݃A�N�Z�X���Ă���f�[�^�����ԍ����w�肳�ꂽ�f�[�^�����ԍ��Ɠ������ꍇ
			if (data_affiliation_number_form_component_data[0][i].equals(data_affiliation_number)) {
				error_parameter_value = data_affiliation_number + "(" + data_affiliation_number_form_component_data[1][i] + ")"; // �G���[�p�����[�^�l�̐ݒ�
				break; // ���[�v���甲����
			}
		}

		application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", utility_bean.getJapaneseWord(utility_bean.getDataAffiliationNumberName(table_name)), error_parameter_value)).forward(request, response); // �G���[�y�[�W�ւ̓]��
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	StringBuffer main_content = new StringBuffer(); // ���C�����e���i�[

	// �w�肳�ꂽ�����̎�ނ�"edit"�ł���ꍇ
	if (processing_type.equals("edit")) {
		// �f�[�^�̐V�K�o�^�y�[�W�ւ̃����N��t��
		main_content.append("<a href=\"" + utility_bean.getDataEditPageURL(table_name, null, data_affiliation_number) + "\">" + utility_bean.getJapaneseWord(table_name) + "�f�[�^�̐V�K�o�^</a><br />\n");
		main_content.append("\t\t<br />\n");
	// "edit"�łȂ��ꍇ
	} else {
		main_content.append("\n"); // ���s�R�[�h�̕t��
	}

	// �f�[�^�����ԍ��̃t�H�[���\���v�f�f�[�^���擾�ł����ꍇ
	if (data_affiliation_number_form_component_data != null) {
		main_content.append("\t\t<form method=\"POST\" action=\"" + utility_bean.getDataSelectPageURL() + "\">\n");

		// �w�肳�ꂽ�����̎�ނ�"edit"�łȂ��ꍇ
		if (!processing_type.equals("edit")) {
			main_content.append("\t\t\t<input type=\"hidden\" name=\"processing_type\" value=\"" + processing_type + "\" />\n");
		// "edit"�ł���ꍇ
		} else {
			main_content.append("\t\t\t<input type=\"hidden\" name=\"table_name\" value=\"" + table_name + "\" />\n");
		}

		main_content.append("\t\t\t" + utility_bean.getJapaneseWord(data_affiliation_number_name) + ": " + html_bean.getSelectBox(data_affiliation_number_name, data_affiliation_number, data_affiliation_number_form_component_data[0], data_affiliation_number_form_component_data[1], true, 3) + "\n");
		main_content.append("\t\t\t<input type=\"submit\" value=\"����\" />\n");
		main_content.append("\t\t</form>\n");
	// �f�[�^�����ԍ��̃t�H�[���\���v�f�f�[�^���擾�ł����A�f�[�^�����ԍ������擾�ł��Ȃ������ꍇ
	} else if (data_affiliation_number_name != null) {
		main_content.append("\t\t" + utility_bean.getJapaneseWord(table_name) + "�f�[�^�̍쐬�ɕK�v�ȃf�[�^���쐬����Ă��܂���B<br />\n");
	}

	// ID���w�肳��Ă��Ȃ��ꍇ
	if (ids == null) {
		// �f�[�^�����ԍ������擾�ł��Ȃ������ꍇ
		if (data_affiliation_number_name == null) {
			main_content.append("\t\t����" + utility_bean.getJapaneseWord(table_name) + "�f�[�^�͓o�^����Ă��܂���B��̃����N���N���b�N���ĐV�K�o�^���s���ĉ������B<br />\n");
		// �擾�ł����ꍇ
		} else {
			main_content.append("\t\t��̌����t�H�[���ŏ�����ݒ肵�A[����]�{�^�����N���b�N���ĉ������B<br />\n"); // ���b�Z�[�W�̎擾
		}

		main_content.append("\t\t<br />\n");
	// �w�肳��Ă���ꍇ
	} else {
		String button_name = null; // �{�^���̖��O���i�[

		// �w�肳�ꂽ�����̎�ނ�"edit"�ł���ꍇ
		if (processing_type.equals("edit")) {
			button_name = "�폜"; // "�폜"���{�^���̖��O�ɐݒ�
		}

		main_content.append("\t\t" + html_bean.getDataList(processing_type, table_name, ids, button_name) + "\n"); // �f�[�^�ꗗ�\�̎擾
	}

	String title = null; // �^�C�g�����i�[

	// �w�肳�ꂽ�����̎�ނ�"edit"�ł���ꍇ
	if (processing_type.equals("edit")) {
		main_content.append("\t\t<a href=\"" + utility_bean.getTableSelectPageURL() + "\">�e�[�u���̑I��</a><br />"); // �e�[�u���I���y�[�W�̃����N��t��
		title = utility_bean.getJapaneseWord(table_name) + "�f�[�^"; // �e�[�u����+"�f�[�^"���^�C�g���ɐݒ�
	// "edit"�łȂ��ꍇ
	} else {
		main_content.append("\t\t<a href=\"" + utility_bean.getAdminMainPageURL() + "\">�����̑I��</a><br />");
		title = "�u�`"; // "�u�`"���^�C�g���ɐݒ�
	}
%>
<%= html_bean.getHeader(title + "�̑I��") %>
		<%= main_content %>

		<%= html_bean.getFooter() %>
