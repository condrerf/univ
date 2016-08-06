<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.sql.SQLException" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String table_name = request.getParameter("table_name"); // �w�肳�ꂽ�e�[�u�������i�[
	String edit_type = request.getParameter("edit_type"); // �w�肳�ꂽ�ҏW�̎�ނ��i�[
	String[] form_component_names = utility_bean.getFormComponentNames(table_name); // �t�H�[���̍\���v�f�����i�[
	String[] form_data = utility_bean.getFormData(form_component_names, request); // �t�H�[���f�[�^���i�[
	String[] table_column_names = database_bean.getColumnNames(table_name); // �w�肳�ꂽ�e�[�u���̗񖼂��i�[
	String[] register_data = new String[table_column_names.length]; // �o�^�f�[�^���i�[
	String data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, request); // �f�[�^�����ԍ����i�[
	int register_data_index = 0; // �o�^�f�[�^�̃C���f�b�N�X���i�[

	// �t�H�[���f�[�^�̃`�F�b�N�Ɠo�^�f�[�^�ւ̊i�[
	for (int i = 0; i < form_data.length; i++) {
		// �w���f�[�^�̐V�K�o�^�ŁA���݃A�N�Z�X���Ă���t�H�[���f�[�^�̗v�f���p�X���[�h�ł���ꍇ
		if (table_name.equals("student") && edit_type.equals("new") && form_component_names[i].equals("password")) {
			register_data[register_data_index] = form_data[i + 1]; // ���N������ݒ�
			register_data_index++; // �o�^�f�[�^�̃C���f�b�N�X�̉��Z
			continue; // ���̗v�f�ɏ������ڂ�
		}

		// ���݃A�N�Z�X���Ă���t�H�[���f�[�^��lecture�e�[�u���̓���ށE�����f�[�^���ݎ��ɕʃf�[�^�Ƃ��ēo�^���邩�ǂ����̐ݒ�ł���ꍇ
		if (table_name.equals("lecture") && i == (form_data.length - 1)) {
			break; // ���[�v���甲����
		}

		// ���݃A�N�Z�X���Ă���t�H�[���f�[�^�̗v�f��null�A���͍u�`�e�[�u���̍u�`�ԍ��v�f�ɃA�N�Z�X���Ă��āA������ށE�����f�[�^���ݎ��ɕʃf�[�^�Ƃ��ēo�^����Ɛݒ肳��Ă���ꍇ
		if (form_data[i] == null || form_data[i].equals("null") || (table_name.equals("lecture") && edit_type.equals("edit") && i == 1 && form_data[form_data.length - 1] != null)) {
			// �o�^�f�[�^�̃C���f�b�N�X��ID���A����ID���̎��ɐݒ肳��Ă���ꍇ
			if (register_data_index <= 1) {
				int data_number; // �f�[�^�ԍ����i�[
				int data_number_position; // �f�[�^�ԍ��̈ʒu���i�[

				// �w�肳�ꂽ�e�[�u������student�ł���ꍇ
				if (table_name.equals("student")) {
					data_number = Integer.parseInt(register_data[0].substring(2)) * 100; // �w�ȏ����ԍ���100�{�̒l���f�[�^�ԍ��̏����l�ɐݒ�
					register_data[0] = register_data[0].substring(0, 2); // �w�������ԍ��Ɋ܂܂��w�ȏ����ԍ��̃J�b�g
					data_number_position = 2; // 2���f�[�^�ԍ��̈ʒu�ɐݒ�
				// ����ȊO�̃e�[�u�����ł���ꍇ
				} else {
					data_number = -1; // -1���f�[�^�ԍ��̏����l�ɐݒ�

					// �w�肳�ꂽ�e�[�u������course�ł���ꍇ
					if (table_name.equals("course")) {
						register_data[0] = ""; // �󔒂�ID�̗v�f�ɐݒ�
						register_data_index++; // �o�^�f�[�^�̃C���f�b�N�X�̉��Z
						data_number_position = 0; // 0���f�[�^�ԍ��̈ʒu�ɐݒ�
					// lecture�ł���ꍇ
					} else if (table_name.equals("lecture")) {
						// ����ނ̃f�[�^��o�^����ꍇ
						if (edit_type.equals("edit") && form_data[form_data.length - 1] != null) {
							edit_type = "new"; // �ҏW�̎�ނ̕ύX
							register_data[0] += form_data[1].substring(0, 2); // ID�ɍu�`��ޔԍ���t��
						}

						data_number_position = register_data[0].length(); // ID�̗v�f�̌������f�[�^�ԍ��̈ʒu�ɐݒ�
					// ����ȊO�̃e�[�u�����ł���ꍇ
					} else {
						data_number_position = data_affiliation_number.length(); // �f�[�^�����ԍ��̌������f�[�^�ԍ��̈ʒu�ɐݒ�
					}
				}

				int data_number_length; // �f�[�^�ԍ��̌������i�[

				// �w�肳�ꂽ�e�[�u������lecture�ł���ꍇ
				if (table_name.equals("lecture")) {
					data_number_length = 2; // �f�[�^�ԍ��̌�����2��ݒ�
				// ����ȊO�̃e�[�u�����ł���ꍇ
				} else {
					data_number_length = database_bean.getIDLength(table_name) - data_number_position; // ID�̌����ƃf�[�^�����ԍ��̌����̍���ݒ�
				}

				String[] registered_data_ids = database_bean.getColumnDataArray(table_name, 0, data_affiliation_number); // �w�肳�ꂽ�e�[�u���̎w�肳�ꂽ�f�[�^�����ԍ��ɓo�^����Ă���f�[�^��ID���i�[

				// ���������̃f�[�^�����ɓo�^����Ă���ꍇ
				if (registered_data_ids != null) {
					int start_registered_data_ids_index = 0; // �o�^�ς݃f�[�^��ID�̊J�n�C���f�b�N�X���i�[

					// ����ނ̍u�`�f�[�^��o�^����ꍇ
					if (table_name.equals("lecture") && form_data[form_data.length - 1] != null) {
						// �o�^����f�[�^�Ɠ���ނ̓o�^�ς݃f�[�^��ID���i�[����Ă���C���f�b�N�X�̎擾
						while (!register_data[0].equals(registered_data_ids[start_registered_data_ids_index].substring(0, data_number_position))) {
							start_registered_data_ids_index++;
						}
					}

					// �L���ȃf�[�^�ԍ��̎擾
					for (int j = start_registered_data_ids_index; j < registered_data_ids.length; j++) {
						int present_data_number = Integer.parseInt(registered_data_ids[j].substring(data_number_position, data_number_position + data_number_length)); // ���݂̃f�[�^�ԍ����i�[

						// ���݂̃f�[�^�ԍ����L�����Ă���f�[�^�ԍ�����1���������ꍇ
						if (present_data_number == data_number + 1) {
							data_number = present_data_number; // ���݂̃f�[�^�ԍ����L��
						// 1���������̂ł͂Ȃ��A����(�u�`�f�[�^��)�ԍ����������ē���ނ̃f�[�^��o�^����ꍇ
						} else if (present_data_number != data_number || form_data[form_data.length - 1] != null) {
							break; // ���[�v�𔲂���
						}
					}
				}

				data_number++; // �f�[�^�ԍ��̉��Z
				int data_number_length_subtract = data_number_length - new String("" + data_number).length(); // �w�肳�ꂽ�f�[�^�ԍ��̌����Ǝ擾�����f�[�^�ԍ��̌����̍����i�[

				// �w�肳�ꂽ�f�[�^�ԍ��̌����Ǝ擾�����f�[�^�ԍ��̌����̍���0�łȂ��ꍇ
				if (data_number_length_subtract != 0) {
					// ���̐�����ID��"0"��t��
					for (int j = 0; j < data_number_length_subtract; j++) {
						register_data[0] += "0";
					}
				}

				register_data[0] += data_number; // ���Z�����f�[�^��ޔԍ���ID�ɕt��

				// �w�肳�ꂽ�e�[�u������lecture�ŁAID�̗v�f�̌�����5�ł���ꍇ
				if (table_name.equals("lecture") && register_data[0].length() == 5) {
					register_data[0] += "00"; // ID�̕t��
				}
			// ID���̎��̎��ȍ~�ɐݒ肳��Ă���ꍇ
			} else {
				register_data_index++; // �o�^�f�[�^�̃C���f�b�N�X�̉��Z
			}
		// �󔒂ł���ꍇ
		} else if (form_data[i].equals("")) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", utility_bean.getJapaneseWord(form_component_names[i]))).forward(request, response); // �G���[�y�[�W�ւ̓]��
		// ��L�̏����𖞂����Ȃ��ꍇ
		} else {
			// ���݃A�N�Z�X���Ă���t�H�[���\���v�f�f�[�^�������݃A�N�Z�X���Ă���e�[�u���̗񖼂Ɠ������A���͓o�^�f�[�^��ID�̗v�f��null�ł���ꍇ
			if (form_component_names[i].equals(table_column_names[register_data_index]) || register_data[0] == null) {
				// ���݃A�N�Z�X���Ă���t�H�[���\���v�f�f�[�^�������݃A�N�Z�X���Ă���e�[�u���̗񖼂Ɠ������ꍇ
				if (form_component_names[i].equals(table_column_names[register_data_index])) {
					register_data[register_data_index] = form_data[i]; // �t�H�[���f�[�^�̗v�f��o�^�f�[�^�̗v�f�ɐݒ�
				// �������Ȃ��ꍇ
				} else {
					register_data[0] = form_data[i]; // �t�H�[���f�[�^�̗v�f��o�^�f�[�^��ID�̗v�f�ɐݒ�
				}

				register_data_index++; // �o�^�f�[�^�̃C���f�b�N�X�̉��Z
			// ��L�̏����𖞂����Ȃ��ꍇ
			} else {
				register_data[0] += form_data[i]; // �t�H�[���f�[�^�̗v�f�̕t��
			}
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	String[] table_row_data = database_bean.getRowData(table_name, register_data[0]); // �w�肳�ꂽ�e�[�u���̍s�f�[�^���i�[
	StringBuffer register_content = new StringBuffer(); // �o�^���e���i�[

	// �o�^���̎擾
	for (int i = 0; i < table_column_names.length; i++) {
		// ���݃A�N�Z�X���Ă���e�[�u���̗񖼂̃f�[�^��null�łȂ��ꍇ
		if (register_data[i] != null) {
			if (i > 0) {
				register_content.append("\t\t\t");
			}
			register_content.append("<input type=\"hidden\" name=\"" + table_column_names[i] + "\" value=\"" + register_data[i] + "\" />\n");
		}
	}
	register_content.append("\n");
	register_content.append("\t\t\t");

	// �e�[�u���̍s�f�[�^��null�łȂ��ꍇ(�f�[�^�̕ҏW���s���Ă���ꍇ)
	if(table_row_data != null) {
		// �ҏW���e�̎擾
		register_content.append("<table border=\"0\" cellpadding=\"0\" cellspacing\"0\">\n");
		register_content.append("\t\t\t\t<tr>\n");
		register_content.append("\t\t\t\t\t<th>�ҏW�O</th>\n");
		register_content.append("\t\t\t\t\t<td></td>\n");
		register_content.append("\t\t\t\t\t<th>�ҏW��</th>\n");
		register_content.append("\t\t\t\t</tr>\n");
		register_content.append("\t\t\t\t<tr valign=\"center\">\n");
		register_content.append("\t\t\t\t\t<td>\n");
		register_content.append("\t\t\t\t\t\t" + html_bean.getDataEditTable(table_column_names, table_row_data, 6));
		register_content.append("\t\t\t\t\t</td>\n");
		register_content.append("\t\t\t\t\t<td>��</td>\n");
		register_content.append("\t\t\t\t\t<td>\n");
		register_content.append("\t\t\t\t\t\t" + html_bean.getDataEditTable(table_column_names, register_data, 6));
		register_content.append("\t\t\t\t\t</td>\n");
		register_content.append("\t\t\t\t</tr>\n");
		register_content.append("\t\t\t</table>\n");
	// null�ł���ꍇ(�V�K�o�^�ł���ꍇ)
	} else {
		register_content.append(html_bean.getDataEditTable(table_column_names, register_data, 3)); // �o�^���e�̎擾
	}
%>
<%= html_bean.getHeader("�o�^���e�̊m�F") %>
		�o�^����<%= utility_bean.getJapaneseWord(table_name) %>�f�[�^�̓��e���m�F���A��肪�Ȃ����[�o�^]�{�^�����N���b�N���ĉ������B<br />

		<form method="POST" action="./regist.jsp">
			<input type="hidden" name="table_name" value="<%= table_name %>" />
			<input type="hidden" name="edit_type" value="<%= edit_type %>" />
			<%= register_content %>
			<input type="submit" value="�o�^" />
		</form>

		<a href="<%= request.getHeader("referer") %>">�߂�</a><br />

		<%= html_bean.getFooter() %>
