<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.regex.Pattern, java.util.Vector" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String student_id = request.getParameter("student_id"); // �w�Дԍ����i�[
	String[][] opening_lecture_ids = utility_bean.getOpeningLectureIDs(request); // �J�u�u�`ID���i�[
	boolean[][][] is_error_existed = new boolean[opening_lecture_ids.length][opening_lecture_ids[0].length][2]; // �G���[�����݂��邩�ǂ������i�[
	Vector not_selected_necessary_opening_lecture_ids = new Vector(); // �I������Ă��Ȃ��K�v�J�u�u�`ID���i�[
	String[][] lecture_data = database_bean.getRowsData("lecture"); // �u�`�f�[�^���i�[
	int entered_year = utility_bean.getEnteredYear(student_id); // ���w�����N���i�[
	int[][] lecture_data_indexes = new int[opening_lecture_ids.length][opening_lecture_ids[0].length]; // �u�`�f�[�^�̃C���f�b�N�X���i�[
	int[] selected_opening_lecture_credits = new int[2]; // �I�����ꂽ�J�u�u�`�̒P�ʂ��i�[

	// �I�����ꂽ�J�u�u�`�̃`�F�b�N
	check_selected_opening_lecture: for (int i = 0; i < opening_lecture_ids.length; i++) {
		for (int j = 0; j < opening_lecture_ids[i].length; j++) {
			// �������W���u�`�̍ŏI�v�f�܂ŏI���Ă���ꍇ
			if (i == 5 && j == 5) {
				break check_selected_opening_lecture; // ���[�v���甲����
			}

			// ���݃A�N�Z�X���Ă�������ɊJ�u�u�`ID���I������Ă��Ȃ��ꍇ
			if (opening_lecture_ids[i][j].equals("null")) {
				continue; // ���̗v�f�ɏ������ڂ�
			}

			String[] opening_lecture_data = database_bean.getRowData("opening_lecture", opening_lecture_ids[i][j]); // �J�u�u�`�u�`�f�[�^���i�[

			// ���݃A�N�Z�X���Ă���J�u�u�`�ɕK�v�ȊJ�u�u�`������A���̊J�u�u�`���I������Ă��Ȃ��ꍇ
			if (opening_lecture_data[2] != null && !opening_lecture_data[2].equals(opening_lecture_ids[Integer.parseInt(opening_lecture_data[2].substring(1, 2))][Integer.parseInt(opening_lecture_data[2].substring(2, 3))])) {
				is_error_existed[i][j][0] = true; // ���݃A�N�Z�X���Ă�������ɕK�v�J�u�u�`�̃G���[�����݂���Ɛݒ�
				not_selected_necessary_opening_lecture_ids.addElement(opening_lecture_data[2]); // �I������Ă��Ȃ��K�v�J�u�u�`ID�̕t��
			}

			// �u�`�f�[�^�̃C���f�b�N�X�̎擾
			for (int k = 0; k < lecture_data.length; k++) {
				// ���݃A�N�Z�X���Ă���u�`�����݃A�N�Z�X���Ă�������ɑI�����ꂽ�u�`�Ɠ������ꍇ
				if (lecture_data[k][0].equals(opening_lecture_data[1])) {
					int credit = Integer.parseInt(lecture_data[k][2]); // �P�ʂ��i�[

					// 2000�N�ȍ~�ɓ��w�����w���ŁA���݃A�N�Z�X���Ă���u�`���ʔN�̂��̂ł���ꍇ
					if (entered_year > 2000 && lecture_data[k][0].substring(0, 1).equals("0")) {
						credit /= 2; // �P�ʂ𔼕��ɉ��H
					}

					selected_opening_lecture_credits[j / 5] += credit; // ���݃A�N�Z�X���Ă�������̗v�f�ɑI�����ꂽ�J�u�u�`�̒P�ʂ����Z
					lecture_data_indexes[i][j] = k; // ���݂̗v�f�ԍ������݃A�N�Z�X���Ă�������̍u�`�f�[�^�̃C���f�b�N�X�ɐݒ�
					break; // ���[�v���甲����
				}
			}
		}
	}

	String[][] mastered_lecture_data = database_bean.getRowsData("mastered_lecture", student_id); // ���C�ςݍu�`�f�[�^���i�[
	int another_course_number = (11 - utility_bean.getStudentAffiliationID(student_id)) / 6; // �ʃR�[�X�̔ԍ����i�[
	boolean is_seminar_i_mastered = false; // ���K���𗚏C���Ă��邩�ǂ������i�[
	int another_course_got_credit = 0; // �ʃR�[�X�̎擾�����P�ʂ��i�[

	// ���C�ςݍu�`�f�[�^���擾�ł����ꍇ
	if (mastered_lecture_data != null) {
		// �擾�����P�ʂƉ��K���𗚏C���Ă��邩�ǂ����̃`�F�b�N
		for (int i = 0; i < mastered_lecture_data.length; i++) {
			int lecture_affiliation_number = Integer.parseInt(mastered_lecture_data[i][3]); // �u�`�����ԍ����i�[

			// ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^���o�ϊw���̂��̂ł���ꍇ
			if (lecture_affiliation_number < selected_opening_lecture_credits.length) {
				// ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^�����K���̂��̂ł���ꍇ
				if (mastered_lecture_data[i][0].substring(5, 10).equals("06000")) {
					is_seminar_i_mastered = true; // ���K���𗚏C���Ă���Ɛݒ�
				}

				// �ݒ肳�ꂽ�ʃR�[�X�̔ԍ��ƌ��݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^�̍u�`�����ԍ����������ꍇ
				if (another_course_number == lecture_affiliation_number) {
					another_course_got_credit += Integer.parseInt(database_bean.getColumnData("lecture", 2, mastered_lecture_data[i][0].substring(5))); // �ʃR�[�X�̎擾�����P�ʂ̉��Z
				}
			}
		}
	}

	// �w�肳�ꂽ�w�Дԍ��̊w�������Ԏ�ŁA���K���𗚏C���Ă��Ȃ��ꍇ
	if (another_course_number == 1 && !is_seminar_i_mastered) {
		int credit_sum = selected_opening_lecture_credits[0] + selected_opening_lecture_credits[1]; // �P�ʂ̍��v���i�[
		String error_parameter_name = "�I���u�`�̒P�ʂ̍��v"; // �G���[�p�����[�^�����i�[

		// 2000�N�܂łɓ��w�����w���ł���ꍇ
		if (entered_year <= 2000) {
			int spring_semester_registered_opening_lecture_credit = 0; // �t�w���ɓo�^�����J�u�u�`�̒P�ʂ��i�[

			// ���݂��H�w���ł���ꍇ
			if (utility_bean.getSemesterNumber() == 2) {
				String[][] registered_opening_lecture_data = database_bean.getRowsData("registered_opening_lecture", student_id); // �o�^�ς݊J�u�u�`�f�[�^���i�[

				// �o�^�ς݊J�u�u�`�f�[�^���擾�ł����ꍇ
				if (registered_opening_lecture_data != null) {
					// �t�w���ɓo�^�����J�u�u�`�̒P�ʂƓo�^�ς݊J�u�u�`ID�̎擾
					for (int i = 0; i < registered_opening_lecture_data.length; i++) {
						// ���݃A�N�Z�X���Ă���o�^�����J�u�u�`�f�[�^���t�w���ɓo�^���ꂽ���̂ł���ꍇ
						if (registered_opening_lecture_data[i][0].substring(5, 6).equals("1")) {
							spring_semester_registered_opening_lecture_credit += Integer.parseInt(database_bean.getColumnData("lecture", 2, database_bean.getColumnData("opening_lecture", 1, registered_opening_lecture_data[i][1])));
						}
					}

					credit_sum += spring_semester_registered_opening_lecture_credit; // �t�w���ɓo�^�����J�u�u�`�̒P�ʂ�P�ʂ̍��v�ɉ��Z
				}
			}

			// �P�ʂ̍��v��56�𒴂��Ă���ꍇ
			if (credit_sum > 56) {
				// �t�w���ɓo�^�����J�u�u�`�̒P�ʂ�0�łȂ��ꍇ
				if (spring_semester_registered_opening_lecture_credit != 0) {
					error_parameter_name += "(�t�w���o�^�����܂�)"; // �G���[�p�����[�^���̕t��
				}

				application.getRequestDispatcher(utility_bean.getErrorPageURI("credit_error", error_parameter_name, credit_sum)).forward(request, response); // �G���[�y�[�W�ւ̓]��
			}
		// 2000�N�ȍ~�ɓ��w�����w���ŁA�P�ʂ̍��v��26�𒴂��Ă���ꍇ
		} else if (credit_sum > 26) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("credit_error", error_parameter_name, credit_sum)).forward(request, response); // �G���[�y�[�W�ւ̓]��
		}
	}

	int another_course_credit_sum = selected_opening_lecture_credits[another_course_number] + another_course_got_credit; // �ʃR�[�X�̒P�ʂ̍��v���i�[

	// �ʃR�[�X�̒P�ʂ̍��v��30�𒴂��Ă���ꍇ
	if(another_course_credit_sum > 30) {
		StringBuffer error_parameter_name = new StringBuffer(); // �G���[�p�����[�^�����i�[

		// �ʃR�[�X�̔ԍ������Ԃ̂��̂ɐݒ肳��Ă���ꍇ
		if (another_course_number == 0) {
			error_parameter_name.append("��"); // "��"���G���[�p�����[�^���ɕt��
		// ��Ԃ̂��̂ɐݒ肳��Ă���ꍇ
		} else {
			error_parameter_name.append("��"); // "��"���G���[�p�����[�^���ɕt��
		}

		error_parameter_name.append("�Ԏ�̍u�`�̒P�ʂ̍��v"); // �G���[�p�����[�^���̕t��

		// �ʃR�[�X�̎擾�����P�ʂ�����ꍇ
		if (another_course_got_credit != 0) {
			error_parameter_name.append("(�ߋ����C�����܂�)"); // �G���[�p�����[�^���̕t��
		}

		application.getRequestDispatcher(utility_bean.getErrorPageURI("another_course_credit_error", error_parameter_name.toString(), another_course_credit_sum)).forward(request, response); // �G���[�y�[�W�ւ̓]��
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	String[][] column_data = new String[opening_lecture_ids.length][opening_lecture_ids[0].length]; // �s�f�[�^���i�[
	int semester_number = utility_bean.getSemesterNumber(); // �w���ԍ����i�[

	semester_number = 1; // �e�X�g�p�̐ݒ�(�J���I����͍폜)

	Vector error_existed_opening_lecture_ids = new Vector(); // �G���[�����݂���J�u�u�`ID���i�[
	int final_class_number = opening_lecture_ids[0].length - 1; // �ŏI�����ԍ����i�[

	// �e��f�[�^�̎擾
	get_column_data: for (int i = 0; i < column_data.length; i++) {
		for (int j = 0; j < column_data[i].length; j++) {
			// �������W���u�`�̍ŏI�v�f�܂ŏI���Ă���ꍇ
			if (i == 5 && j == 5) {
				break get_column_data; // ���[�v���甲����
			}

			StringBuffer string_buffer_column_data = new StringBuffer(); // �s�f�[�^���i�[
			String registered_opening_lecture_id = database_bean.getColumnData("registered_opening_lecture", 1, student_id + semester_number + i + j); // �o�^�J�u�u�`ID���i�[

			// �o�^�J�u�u�`ID���擾�ł��A����ID�����݃A�N�Z�X���Ă���ID�ƈقȂ�ꍇ
			if (registered_opening_lecture_id != null && !registered_opening_lecture_id.equals(opening_lecture_ids[i][j])) {
				// �ȑO�̓o�^�󋵂̕t��
				string_buffer_column_data.append("<font color=\"gray\">" + database_bean.getColumnData("lecture", 1, database_bean.getColumnData("opening_lecture", 1, registered_opening_lecture_id)) + "<br />��<br /></font>\n");
				string_buffer_column_data.append("\t\t\t\t\t\t\t\t");
			}

			// ���݃A�N�Z�X���Ă�������ɍu�`���I������Ă��Ȃ��ꍇ
			if (opening_lecture_ids[i][j].equals("null")) {
				string_buffer_column_data.append("<font color=\"gray\">(�I���Ȃ�)</font>"); // �s�f�[�^�̐ݒ�
			// �I������Ă���ꍇ
			} else {
				String present_lecture_type_id = lecture_data[lecture_data_indexes[i][j]][0].substring(0, 5); // ���݂̍u�`����ID���i�[

				// ���݃A�N�Z�X���Ă�������ɓ���ލu�`�I���G���[���ݒ肳��Ă��Ȃ��ꍇ
				if (!is_error_existed[i][j][1]) {
					int day_number = i; // �j���ԍ����i�[
					int class_number = j; // �����ԍ����i�[

					// �A�N�Z�X�ʒu���W���u�`�̍ŏI�v�f�ɂȂ�܂ŌJ��Ԃ�
					while (day_number <= 4 || class_number <= 3) {
						// �����ԍ����ŏI�����ԍ��Ɠ������ꍇ
						if (class_number == final_class_number) {
							// ���̗j���̍ŏ��̎����ւ̐ݒ�
							day_number++;
							class_number = 0;
						// �������Ȃ��ꍇ
						} else {
							class_number++; // �����ԍ��̉��Z
						}

						// ���݃A�N�Z�X���Ă�������ɍu�`���I������Ă���A��r���̓����ɑI������Ă���u�`�Ɠ���ނł���ꍇ
						if(!opening_lecture_ids[day_number][class_number].equals("null") && lecture_data[lecture_data_indexes[day_number][class_number]][0].substring(0, 5).equals(present_lecture_type_id)) {
							// ����ލu�`�I���G���[�̐ݒ�
							is_error_existed[i][j][1] = true;
							is_error_existed[day_number][class_number][1] = true;
						}
					}
				}

				String lecture_name_font_color = null; // �u�`���̃t�H���g�J���[���i�[

				// ���݃A�N�Z�X���Ă�������ɕK�v�u�`���I���G���[�A���͓���ލu�`�I���G���[���ݒ肳��Ă���ꍇ
				if (is_error_existed[i][j][0] || is_error_existed[i][j][1]) {
					lecture_name_font_color = "red"; // �u�`���̃t�H���g�J���[��Ԃɐݒ�
					boolean is_same_type_lecture_added = false; // ����ނ̍u�`���t������Ă��邩�ǂ������i�[

					// ����ލu�`�I���G���[���ݒ肳��Ă���ꍇ
					if (is_error_existed[i][j][1]) {
						// ����ނ̍u�`�����ɃG���[�����݂���J�u�u�`ID�̃��X�g�ɕt������Ă��邩�ǂ����̃`�F�b�N
						for (int k = 0; k < error_existed_opening_lecture_ids.size(); k++) {
							String opening_lecture_id = error_existed_opening_lecture_ids.elementAt(k).toString(); // �J�u�u�`ID���i�[
							String lecture_type_id = lecture_data[lecture_data_indexes[Integer.parseInt(opening_lecture_id.substring(1, 2))][Integer.parseInt(opening_lecture_id.substring(2, 3))]][0].substring(0, 5); // �u�`����ID���i�[

							// ����ނ̍u�`���I������Ă���J�u�u�`ID���t������Ă���ꍇ
							if (lecture_type_id.equals(lecture_data[lecture_data_indexes[i][j]][0].substring(0, 5))) {
								is_same_type_lecture_added = true; // ����ނ̍u�`���t������Ă���Ɛݒ�
								break; // ���[�v���甲����
							}
						}
					}

					// �K�v�u�`���I���G���[�����݂���Ɛݒ肳��Ă���A���͓���ނ̍u�`���t������Ă��Ȃ��ꍇ
					if (is_error_existed[i][j][0] || !is_same_type_lecture_added) {
						// �K�v�u�`���I���G���[�����݂���Ɛݒ肳��Ă��āA����ނ̍u�`���t������Ă���ꍇ
						if (is_same_type_lecture_added) {
							// ����ލu�`�I���̃G���[�Ɖ��K���Ƙ_���w���̓����\���G���[�̐ݒ�̉���
							is_error_existed[i][j][1] = false;
						}

						error_existed_opening_lecture_ids.addElement(opening_lecture_ids[i][j]); // ���݃A�N�Z�X���Ă���J�u�u�`ID���G���[�����݂���J�u�u�`ID�ɕt��
					}
				// ��L�̏����𖞂����Ȃ��ꍇ
				} else {
					lecture_name_font_color = "black"; // �u�`���̃t�H���g�J���[�����ɐݒ�
				}

				// �o�^�f�[�^�̕t��
				string_buffer_column_data.append("<input type=\"hidden\" name=\"opening_lecture_id_" + i + j + "\" value=\"" + opening_lecture_ids[i][j] + "\" />\n");
				string_buffer_column_data.append("\t\t\t\t\t\t\t\t<font color=\"" + lecture_name_font_color + "\"><b>" + lecture_data[lecture_data_indexes[i][j]][1] + "</b></font>"); // �s�f�[�^�̕t��
			}

			column_data[i][j] = string_buffer_column_data.toString(); // �s�f�[�^�̎擾
		}
	}

	String title = null; // �^�C�g�����i�[
	StringBuffer message = new StringBuffer(); // ���b�Z�[�W���i�[
	String button_name = null; // �{�^���̖��O���i�[

	// �G���[�����݂���u�`������ꍇ
	if (!error_existed_opening_lecture_ids.isEmpty()) {
		title = "�I����e�̃G���["; // �^�C�g���̐ݒ�

		// ���b�Z�[�W�̕t��
		message.append("<b>�G���[���e:</b><br />\n");
		for (int i = 0; i < error_existed_opening_lecture_ids.size(); i++) {
			String opening_lecture_id = error_existed_opening_lecture_ids.elementAt(i).toString(); // �J�u�u�`ID���i�[
			int day_number = Integer.parseInt(opening_lecture_id.substring(1, 2)); // �j���ԍ����i�[
			int class_number = Integer.parseInt(opening_lecture_id.substring(2, 3)); // �N���X�ԍ����i�[
			String lecture_name = lecture_data[lecture_data_indexes[day_number][class_number]][1]; // �u�`�����i�[

			// �e�G���[�̑��݂��`�F�b�N���A���݂���G���[�̓��e��\��
			for (int j = 0; j < is_error_existed[day_number][class_number].length; j++) {
				// ���݃A�N�Z�X���Ă���J�u�u�`�Ɍ��݃A�N�Z�X���Ă����ނ̃G���[�����݂��Ă���ꍇ
				if (is_error_existed[day_number][class_number][j]) {
					String error_content = null; // �G���[���e���i�[

					// ���݂��Ă���G���[���K�v�u�`���I���G���[�ł���ꍇ
					if (j == 0) {
						String necessary_opening_lecture_id = not_selected_necessary_opening_lecture_ids.firstElement().toString(); // �K�v�J�u�u�`ID���i�[
						not_selected_necessary_opening_lecture_ids.removeElementAt(0); // ���p�����A�I������Ă��Ȃ��K�v�J�u�u�`ID�̍폜
						int necessary_opening_lecture_day_number = Integer.parseInt(necessary_opening_lecture_id.substring(1, 2)); // �K�v�J�u�u�`�̗j���ԍ����i�[
						final String[] DAY_OUTLINES = {"���j��", "�Ηj��", "���j��", "�ؗj��", "���j��", "�W���u�`"}; // �j���̌��o�����i�[
						StringBuffer error_caused_position = new StringBuffer(DAY_OUTLINES[necessary_opening_lecture_day_number]); // �G���[�����������ʒu���i�[

						// �K�v�J�u�u�`���W���u�`�̂��̂ł͂Ȃ��ꍇ
						if (necessary_opening_lecture_day_number != 5) {
							error_caused_position.append("�E" + (Integer.parseInt(necessary_opening_lecture_id.substring(2, 3)) + 1) + "������");
						}

						error_content = "�̗��C�ɕK�v��<font color=\"red\">" + database_bean.getColumnData("lecture", 1, database_bean.getColumnData("opening_lecture", 1, necessary_opening_lecture_id)) + "</font>(" + error_caused_position + "�J�u)���I������Ă��܂���"; // �G���[���e�̐ݒ�
					// ����ލu�`�I���G���[�ł���ꍇ
					} else {
						lecture_name = Pattern.compile("\\(").split(lecture_name)[0]; // �u�`���̉��H
						error_content = "��1�w����2�ȏ㗚�C���邱�Ƃ͂ł��܂���"; // �G���[���e�̐ݒ�
					}

					message.append("\t\t�E<font color=\"red\">" + lecture_name + "</font>" + error_content + "<br />\n"); // ���b�Z�[�W�̕t��
				}
			}
		}
		message.append("\t\t<br />\n");
		message.append("\t\t�I���y�[�W�ɖ߂�A�������s���ĉ������B");
	// �Ȃ��ꍇ
	} else {
		title = "�o�^���e�̊m�F"; // �^�C�g���̐ݒ�
		button_name = "�o�^"; // "�o�^"���{�^���̖��O�ɐݒ�
		message.append("�o�^���e���m�F���A��肪�Ȃ���Ή���[" + button_name + "]�{�^�����N���b�N���ĉ������B<br />"); // ���b�Z�[�W�̕t��
	}
%>
<%= html_bean.getHeader(title) %>
		<%= message %>

		<form method="POST" action="./regist.jsp">
			<input type="hidden" name="student_id" value="<%= student_id %>" />
			<%= html_bean.getOpeningLectureList(column_data, button_name) %>
		</form>
		<a href="<%= utility_bean.getMainPageURL(student_id) %>">�߂�</a><br />

		<%= html_bean.getFooter() %>
