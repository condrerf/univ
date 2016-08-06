<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.*" %>
<%!
	/**
	 * �w�肳�ꂽ�f�[�^����text�\���v�f���쐬���A�����Ԃ�
	 * @param name ���O
	 * @param value �l
	 * @return text�\���v�f
	 */
	private String getTextComponent(String name, String value) {
		StringBuffer text_component = new StringBuffer(); // text�\���v�f���i�[

		// text�\���v�f�̎擾
		text_component.append("<input type=\"text\" name=\"" + name + "\" ");
		if (value != null) {
			text_component.append("value=\"" + value + "\" "); // �l�̕t��
		}
		text_component.append("/>");

		return text_component.toString(); // text�\���v�f��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�f�[�^����t�H�[���\���v�f���쐬���A�����Ԃ�
	 * @param type ���
	 * @param name ���O
	 * @param id ID
	 * @param option_ids �I�𗓂�ID
	 * @param option_outlines �I�𗓂̌��o��
	 * @return �t�H�[���\���v�f
	 */
	private String getFormComponent(String type, String name, String id, String[] option_ids, String[] option_outlines) {
		StringBuffer form_component = new StringBuffer(); // �t�H�[���\���v�f���i�[

		// �t�H�[���\���v�f�̎擾
		for (int i = 0; i < option_ids.length; i++) {
			// �\������I�𗓂�2�ڈȍ~�̂��̂ł���ꍇ
			if (i > 0) {
				form_component.append("\t\t\t\t\t\t");
			}

			form_component.append("<input type=\"" + type + "\" name=\""+ name + "\" value=\"" + option_ids[i] + "\"");

			// �w�肳�ꂽID�����݂̑I�𗓂�ID�Ɠ������A���͎w�肳�ꂽ��ނ�radio�ŕ\������I�𗓂�1�ڂ̂��̂�ID���w�肳��Ă��Ȃ�
			if ((id != null && id.equals(option_ids[i])) || (type.equals("radio") && i == 0 && id == null)) {
				form_component.append(" checked");
			}

			form_component.append(" />");

			// �w�肳�ꂽ��ނ�radio�ł���ꍇ
			if (type.equals("radio")) {
				// �I�𗓂̌��o�����w�肳��Ă���ꍇ
				if (option_outlines != null) {
					form_component.append(option_outlines[i]);
				// �w�肳��Ă��Ȃ��ꍇ
				} else {
					form_component.append(option_ids[i]);
				}
			}

			form_component.append("\n");
		}

		return form_component.toString(); // �t�H�[���\���v�f��Ԃ�
	}
%>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String table_name = request.getParameter("table_name"); // �w�肳�ꂽ�e�[�u�������i�[
	String edit_type = null; // �ҏW�̎�ނ��i�[
	String id = request.getParameter("id"); // �w�肳�ꂽID���i�[
	String data_affiliation_number = null; // �f�[�^�����ԍ����i�[
	String[] form_component_names = utility_bean.getFormComponentNames(table_name); // �t�H�[���̍\���v�f�����i�[
	String[] form_component_data = new String[form_component_names.length]; // �t�H�[���̍\���v�f�f�[�^���i�[

	// ID���w�肳��Ă���ꍇ
	if (id != null) {
		edit_type = "edit"; // "edit"��ҏW�̎�ނɐݒ�
		data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, id); // �f�[�^�����ԍ��̎擾
		String[] table_row_data = database_bean.getRowData(table_name, id); // �e�[�u���̍s�f�[�^���i�[
		String table_row_data_data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, table_row_data[0]); // �w�肳�ꂽ�e�[�u���E�s�f�[�^�̃f�[�^�����ԍ����i�[
		Vector vector_form_component_data = new Vector(); // �t�H�[���̍\���v�f�f�[�^���i�[

		// �f�[�^�����ԍ����擾�ł��Ȃ������ꍇ
		if (table_row_data_data_affiliation_number == null) {
			vector_form_component_data.addElement(table_row_data[0]); // ID�̗v�f�̕t��
		// �擾�ł����ꍇ
		} else {
			vector_form_component_data.addElement(table_row_data_data_affiliation_number); // �f�[�^�����ԍ��̗v�f�̕t��
			vector_form_component_data.addElement(table_row_data[0].substring(table_row_data_data_affiliation_number.length())); // �f�[�^�ԍ��̗v�f�̕t��
		}

		// ID�ȊO�̗v�f�̕t��
		for (int i = 1; i < table_row_data.length; i++) {
			vector_form_component_data.addElement(table_row_data[i]);
		}

		// �t�H�[���̍\���v�f�f�[�^�̎擾
		for (int i = 0; i < vector_form_component_data.size(); i++) {
			form_component_data[i] = (String) vector_form_component_data.elementAt(i);
		}
	// �w�肳��Ă��Ȃ��ꍇ
	} else {
		edit_type = "new"; // "new"��ҏW�̎�ނɐݒ�
		data_affiliation_number = request.getParameter("data_affiliation_number"); // �w�肳�ꂽ�f�[�^�����ԍ��̎擾

		// �f�[�^�����ԍ����擾�ł����ꍇ
		if (data_affiliation_number != null) {
			form_component_data[0] = data_affiliation_number; // �f�[�^�����ԍ����t�H�[���\���v�f�̃f�[�^�����ԍ��v�f�ɐݒ�
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	Vector vector_form_component_names = new Vector(); // �t�H�[���̍\���v�f�����i�[
	Vector vector_form_component_contents = new Vector(); // �t�H�[���̍\���v�f���e���i�[

	// �w�肳�ꂽ�e�[�u�����Ȗڃe�[�u���ł���ꍇ
	if (table_name.equals("course")) {
		// �f�[�^�̕ҏW�ł���ꍇ
		if (edit_type.equals("edit")) {
			// �Ȗ�ID�̍\���v�f�̐ݒ�
			vector_form_component_names.addElement(form_component_names[0]);
			vector_form_component_contents.addElement("<input type=\"hidden\" name=\"" + form_component_names[0] + "\" value=\"" + form_component_data[0] + "\" />" + form_component_data[0] + "\n");
		}

		// �Ȗږ��̍\���v�f�̐ݒ�
		vector_form_component_names.addElement(form_component_names[1]);
		vector_form_component_contents.addElement(getTextComponent(form_component_names[1], form_component_data[1]));
	// ���̑��̃e�[�u���ł���ꍇ
	} else {
		// �f�[�^�����ԍ��ƃf�[�^�ԍ��̍\���v�f�̐ݒ�
		String[][] data_affiliation_number_form_component_data = utility_bean.getDataAffiliationNumberFormComponentData(table_name); // �f�[�^�����ԍ��̃t�H�[���\���v�f�f�[�^���i�[
		vector_form_component_names.addElement(form_component_names[0]);
		if (edit_type.equals("new")) {
			vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[0], form_component_data[0], data_affiliation_number_form_component_data[0], data_affiliation_number_form_component_data[1], true, 6));
		} else {
			vector_form_component_contents.addElement("<input type=\"hidden\" name=\"" + form_component_names[0] + "\" value=\"" + form_component_data[0] + "\" />" + form_component_data[0] + "\n");
			vector_form_component_names.addElement(form_component_names[1]);
			vector_form_component_contents.addElement("<input type=\"hidden\" name=\"" + form_component_names[1] + "\" value=\"" + form_component_data[1] + "\" />" + form_component_data[1] + "\n");
		}

		// �w�肳�ꂽ�e�[�u�����J�u�u�`�e�[�u���ł���ꍇ
		if (table_name.equals("opening_lecture")) {
			// �u�`ID�̍\���v�f�̐ݒ�
			String[][] lecture_data = database_bean.getRowsData("lecture"); // �u�`�f�[�^���i�[
			String[] lecture_ids = null; // �u�`ID���i�[
			String[] lecture_names = null; // �u�`�����i�[
			if (lecture_data != null) {
				// �u�`�f�[�^�̗v�f���̐ݒ�
				lecture_ids = new String[lecture_data.length];
				lecture_names = new String[lecture_data.length];

				// �u�`�f�[�^�̎擾
				for (int i = 0; i < lecture_data.length; i++) {
					lecture_ids[i] = lecture_data[i][0];
					lecture_names[i] = lecture_data[i][1];
				}
			}
			vector_form_component_names.addElement(form_component_names[2]);
			vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[2], form_component_data[2], lecture_ids, lecture_names, true, 6));

			// �K�v�J�u�u�`ID�̍\���v�f�̐ݒ�
			String[][] opening_lecture_data = database_bean.getRowsData("opening_lecture"); // �J�u�u�`�f�[�^���i�[
			String[] necessary_opening_lecture_ids = null; // �K�v�J�u�u�`ID���i�[
			if (opening_lecture_data == null) {
				necessary_opening_lecture_ids = new String[1]; // 1��K�v�J�u�u�`ID�̗v�f���ɐݒ�
			} else {
				necessary_opening_lecture_ids = new String[opening_lecture_data.length + 1]; // �u�`ID��+1��K�v�J�u�u�`ID�̗v�f���ɐݒ�
			}
			String[] necessary_opening_lecture_names = new String[necessary_opening_lecture_ids.length]; // �K�v�J�u�u�`�����i�[
			necessary_opening_lecture_names[0] = "�Ȃ�"; // �K�v�J�u�u�`���̐ݒ�
			for (int i = 1; i < necessary_opening_lecture_ids.length; i++) {
				int opening_lecture_data_index = i - 1; // �J�u�u�`�f�[�^�̃C���f�b�N�X���i�[
				necessary_opening_lecture_ids[i] = opening_lecture_data[opening_lecture_data_index][0]; // �K�v�J�u�u�`ID�̎擾

				// �K�v�J�u�u�`���̎擾
				for (int j = 0; j < lecture_data.length; j++) {
					// ���݃A�N�Z�X���Ă���J�u�u�`�f�[�^�����u�`ID�����݃A�N�Z�X���Ă���u�`�f�[�^�̍u�`ID�Ɠ������ꍇ
					if (opening_lecture_data[opening_lecture_data_index][1].equals(lecture_data[j][0])) {
						necessary_opening_lecture_names[i] = lecture_data[j][1]; // �K�v�J�u�u�`���̎擾
						break; // ���[�v���甲����
					}
				}
			}
			vector_form_component_names.addElement(form_component_names[3]);
			vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[3], form_component_data[3], necessary_opening_lecture_ids, necessary_opening_lecture_names, true, 6));
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			// �������̍\���v�f�̐ݒ�
			vector_form_component_names.addElement(form_component_names[2]);
			vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[2], form_component_data[2], database_bean.getColumnDataArray("lecturer", 0), database_bean.getColumnDataArray("lecturer", 1), true, 6));
		// ���̑��̃e�[�u���ł���ꍇ
		} else {
			// ���O�̍\���v�f�̐ݒ�
			vector_form_component_names.addElement(form_component_names[2]);
			vector_form_component_contents.addElement(getTextComponent(form_component_names[2], form_component_data[2]));

			// �w�肳�ꂽ�e�[�u�����w���e�[�u���ł���ꍇ
			if (table_name.equals("student")) {
				// �f�[�^�̕ҏW�ł���ꍇ
				if (edit_type.equals("edit")) {
					// �p�X���[�h�̍\���v�f�̐ݒ�
					vector_form_component_names.addElement(form_component_names[3]);
					vector_form_component_contents.addElement("<input type=\"hidden\" name=\"" + form_component_names[3] + "\" value=\"" + form_component_data[3] + "\" />" + form_component_data[3] + "\n");
				}

				// ���N�����̍\���v�f�̐ݒ�
				int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // �����i�[
				int year = Calendar.getInstance().get(Calendar.YEAR); // ���݂̔N���i�[
				int year_day = 365; // 1�N�̓������i�[
				if ((year % 4) == 0 && (year % 100) != 0 || (year % 400) == 0) {
					days[1] = 29; // 2���̓�����29���ɐݒ�
					year_day = 366; // 1�N�̓�����366�ɐݒ�
				}
				String[] birthday_ids = new String[year_day]; // �a������ID���i�[
				String[] birthday_outlines = new String[year_day]; // �a�����̌��o�����i�[
				int birthday_index = 0; // �a�����̃C���f�b�N�X���i�[

				for (int i = 1; i <= 12; i++) {
					String month = null; // �����i�[

					// �l��10�����ł���ꍇ
					if (i < 10) {
						month = "0" + i; // ���̑O��"0"��t�����l��ݒ�
					// 10�ȏ�ł���ꍇ
					} else {
						month = "" + i; // ���̒l��ݒ�
					}

					for (int j = 1; j <= days[i - 1]; j++) {
						String day = null; // �����i�[

						// �l��10�����ł���ꍇ
						if (j < 10) {
							day = "0" + j; // // ���̑O��"0"��t�����l��ݒ�
						// 10�ȏ�ł���ꍇ
						} else {
							day = "" + j; // ���̒l��ݒ�
						}

						birthday_ids[birthday_index] = month + day; // �a������ID�̎擾
						birthday_outlines[birthday_index] = month + "��" + day + "��"; // �a�����̌��o���̎擾
						birthday_index++; // �C���f�b�N�X�̉��Z
					}
				}
				vector_form_component_names.addElement(form_component_names[4]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[4], form_component_data[4], birthday_ids, birthday_outlines, true, 6));

				// ���Ђ̍\���v�f�̐ݒ�
				String[] nationality_ids = {"0", "1"}; // ���Ђ�ID���i�[
				String[] nationality_names = {"���{", "�O��"}; // ���Ж����i�[
				vector_form_component_names.addElement(form_component_names[5]);
				vector_form_component_contents.addElement(getFormComponent("radio", form_component_names[5], form_component_data[5], nationality_ids, nationality_names));

				String[] special_lecture_affiliation_ids = database_bean.getColumnDataArray("lecture_affiliation", 0, "0"); // ���̍u�`����ID���i�[
				Vector vector_lecturer_ids = new Vector(); // ����ID���i�[
				Vector vector_lecturer_names = new Vector(); // ���������i�[

				// �����f�[�^�̎擾
				for (int i = 0; i < special_lecture_affiliation_ids.length - 1; i++) {
					String[] lecturer_ids = database_bean.getColumnDataArray("lecturer", 0, special_lecture_affiliation_ids[i].substring(1)); // ����ID���i�[

					for (int j = 0; j < lecturer_ids.length; j++) {
						vector_lecturer_ids.addElement(lecturer_ids[j]);
						vector_lecturer_names.addElement(database_bean.getColumnData("lecturer", 1, lecturer_ids[j]));
					}
				}

				String[][] lecturer_data = new String[2][vector_lecturer_ids.size()]; // �����f�[�^���i�[

				// �����f�[�^�̎擾
				for (int i = 0; i < lecturer_data[0].length; i++) {
					lecturer_data[0][i] = (String) vector_lecturer_ids.elementAt(i);
					lecturer_data[1][i] = (String) vector_lecturer_names.elementAt(i);
				}

				// ��b���K����ID�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[6]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[6], form_component_data[6], lecturer_data[0], lecturer_data[1], true, 6));

				String[] from_0_to_20_array = utility_bean.getArray(0, 20); // 0����19�̔ԍ��̔z����i�[
				String[] from_0_to_9_array = utility_bean.getArray(0, 9); // 0����9�̔ԍ��̔z����i�[

				// ��񃊃e���V�[�N���X�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[7]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[7], form_component_data[7], from_0_to_20_array, 6));

				// ���N�Ɛ����N���X�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[8]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[8], form_component_data[8], from_0_to_9_array, 6));

				// �X�|�[�c�Ȋw�N���X�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[9]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[9], form_component_data[9], from_0_to_20_array, 6));

				// �p��I�N���X�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[10]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[10], form_component_data[10], from_0_to_20_array, 6));

				// �p��IIa�N���X�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[11]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[11], form_component_data[11], from_0_to_20_array, 6));

				// �p��IIb�N���X�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[12]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[12], form_component_data[12], from_0_to_20_array, 6));

				// �O����ID�̍\���v�f�̐ݒ�
				String[][] foreign_language_data = database_bean.getRowsData("lecture_affiliation", "2"); // �O����f�[�^���i�[
				String[][] second_foreign_language_data = new String[2][foreign_language_data.length - 2]; // ��2�O����f�[�^���i�[
				for (int i = 1; i < foreign_language_data.length - 1; i++) {
					int second_foreign_language_data_index = i - 1; // ��2�O����f�[�^�̃C���f�b�N�X���i�[

					// ��2�O����f�[�^�̎擾
					second_foreign_language_data[0][second_foreign_language_data_index] = foreign_language_data[i][0].substring(1);
					second_foreign_language_data[1][second_foreign_language_data_index] = foreign_language_data[i][1];
				}
				vector_form_component_names.addElement(form_component_names[13]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[13], form_component_data[13], second_foreign_language_data[0], second_foreign_language_data[1], true, 6));

				// �O����N���X�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[14]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[14], form_component_data[14], from_0_to_9_array, 6));

				// �[�~����ID�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[15]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[15], form_component_data[15], lecturer_data[0], lecturer_data[1], true, 6));
			// �u�`�e�[�u���ł���ꍇ
			} else if (table_name.equals("lecture")) {
				// �P�ʂ̍\���v�f�̐ݒ�
				String[] credits = utility_bean.getArray(1, 4);
				vector_form_component_names.addElement(form_component_names[3]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[3], form_component_data[3], credits,  6));

				Vector vector_year_outlines = new Vector(); // �N���̌��o�����i�[

				// �N���̌��o���̎擾
				for (int i = 1; i <= 4; i++) {
					for(int j = i; j <= 4; j++) {
						String year_outline = "" + i; // �N���̌��o�����i�[

						// 2�̒l���������Ȃ��ꍇ
						if (i != j) {
							if (j == 3) {
								continue; // ���̗v�f�ɏ������ڂ�
							}

							year_outline += "�`" + j; // ��������̒l�̕t��
						}

						vector_year_outlines.addElement(year_outline + "��"); // �N���̌��o���̕t��
					}
				}
				vector_year_outlines.addElement("���C�s��");

				String[] year_ids = new String[vector_year_outlines.size()]; // �N����ID���i�[
				String[] year_outlines = new String[year_ids.length]; // �N���̌��o�����i�[

				// �N���f�[�^�̎擾
				for (int i = 0; i < year_ids.length; i++) {
					year_ids[i] = "" + i;
					year_outlines[i] = (String) vector_year_outlines.elementAt(i);
				}

				// �ڈ��N��(���Ԏ�)�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[4]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[4], form_component_data[4], year_ids, year_outlines, true, 6));

				// �ڈ��N��(��Ԏ�)�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[5]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[5], form_component_data[5], year_ids, year_outlines, true, 6));

				// ���Ԏ��u�\�N���̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[6]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[6], form_component_data[6], year_ids, year_outlines, true, 6));

				// ��Ԏ��u�\�N���̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[7]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[7], form_component_data[7], year_ids, year_outlines, true, 6));

				// �w�������N�̎擾
				int present_year = Calendar.getInstance().get(Calendar.YEAR); // ���݂̔N���i�[
				String[] years = utility_bean.getArray(present_year - 11, present_year); // �N���i�[
				String[] student_affiliation_years = new String[years.length + 1]; // �w�������N���i�[
				for (int i = 1; i < student_affiliation_years.length; i++) {
					student_affiliation_years[i] = years[i - 1];
				}

				// ���C�\�N�n�[�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[8]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[8], form_component_data[8], student_affiliation_years, 6));

				// ���C�\�N�I�[�̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[9]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[9], form_component_data[9], student_affiliation_years, 6));

				String[] string_true = {"1"}; // true(1)���i�[

				// �o�ϊw�Ȃ����C�\�ł��邩�ǂ����̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[10]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[10], form_component_data[10], string_true, null));

				// �t�@�C�i���X�w�Ȃ����C�\�ł��邩�ǂ����̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[11]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[11], form_component_data[11], string_true, null));

				// ��ƌo�c�w�Ȃ����C�\�ł��邩�ǂ����̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[12]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[12], form_component_data[12], string_true, null));

				// ��v���w�Ȃ����C�\�ł��邩�ǂ����̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[13]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[13], form_component_data[13], string_true, null));

				// ���Ǘ��w�Ȃ����C�\�ł��邩�ǂ����̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[14]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[14], form_component_data[14], string_true, null));

				// �Љ�V�X�e���w�Ȃ����C�\�ł��邩�ǂ����̍\���v�f�̐ݒ�
				vector_form_component_names.addElement(form_component_names[15]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[15], form_component_data[15], string_true, null));

				// �f�[�^�̕ҏW�ł���ꍇ
				if (edit_type.equals("edit")) {
					// ����ނ̃f�[�^�����݂���ꍇ�ɕʂ̔ԍ��œo�^����邩�ǂ����̍\���v�f�̐ݒ�
					vector_form_component_names.addElement(form_component_names[16]);
					vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[16], form_component_data[16], string_true, null));
				}
			}
		}
	}

	form_component_names = new String[vector_form_component_names.size()]; // �t�H�[���̍\���v�f���̍ď�����
	String[] form_component_contents = new String[form_component_names.length]; // �t�H�[���̍\���v�f���e���i�[

	// �t�H�[���̍\���v�f�f�[�^�̎擾
	for (int i = 0; i < form_component_names.length; i++) {
		form_component_names[i] = (String) vector_form_component_names.elementAt(i);
		form_component_contents[i] = (String) vector_form_component_contents.elementAt(i);
	}

	String japanese_table_name = utility_bean.getJapaneseWord(table_name); // �w�肳�ꂽ�e�[�u�����̓��{�ꖼ���i�[
%>
<%= html_bean.getHeader(japanese_table_name + "�f�[�^��" + utility_bean.getJapaneseWord(edit_type)) %>
		���e�̕ҏW���s���A[���M]�{�^�����N���b�N���ĉ������B<br />
		<form method="POST" action="./confirm.jsp">
			<input type="hidden" name="table_name" value="<%= table_name %>" />
			<input type="hidden" name="edit_type" value="<%= edit_type %>" />
			<%= html_bean.getDataEditTable(form_component_names, form_component_contents, 3) %>
			<input type="submit" value="���M" />
			<input type="reset" value="���" />
		</form>
		<a href="<%= utility_bean.getDataSelectPageURL(table_name, data_affiliation_number) %>"><%= japanese_table_name %>�f�[�^�̑I��</a><br />

		<%= html_bean.getFooter() %>
