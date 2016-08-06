<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String student_id = request.getParameter("student_id"); // ���͂��ꂽ�w�Дԍ����i�[
	String referer_url = request.getHeader("referer"); // �Q�ƌ���URL���i�[
	String login_page_url = utility_bean.getLoginPageURL(); // ���O�C���y�[�W��URL���i�[

	// ���O�C���y�[�W����̃A�N�Z�X�ł���ꍇ
	if (referer_url.equals(login_page_url)) {
		student_id = utility_bean.getFullDigitAlteredStudentID(student_id); // �w�Дԍ��𔼊p�ɕϊ�
		utility_bean.checkStudentID(student_id, request, response); // �w�Дԍ��̃`�F�b�N
	}

	String[] student_data = database_bean.getRowData("student", student_id); // �w���f�[�^���i�[

	// ���O�C���y�[�W����̃A�N�Z�X�ł���ꍇ
	if (referer_url.equals(login_page_url)) {
		// �w���f�[�^���擾�ł��Ȃ������ꍇ
		if (student_data == null) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", "�w�Дԍ�")).forward(request, response); // �G���[�y�[�W�ւ̓]��
		}

		// �p�X���[�h�����o�^��Ԃ̂܂܂ł���ꍇ
		if(student_data[2].equals(student_data[3])) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("password_not_changed_error")).forward(request, response); // �G���[�y�[�W�ւ̓]��
		}

		String password = request.getParameter("password"); //�p�X���[�h���i�[

		// �p�X���[�h���o�^���e�ƈ�v���Ȃ��ꍇ
		if(!password.equals(student_data[2])) {
			// �p�X���[�h�����͂���Ă��Ȃ��ꍇ
			if(password.equals("")) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "�p�X���[�h")).forward(request, response); // �G���[�y�[�W�ւ̓]��
			// ���͂���Ă���ꍇ
			} else {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("not_accorded_error", "�p�X���[�h")).forward(request, response); // �G���[�y�[�W�ւ̓]��
			}
		}
	}

	String[][] mastered_lecture_data = database_bean.getRowsData("mastered_lecture", student_id); // ���C�ςݍu�`�f�[�^���i�[
	int student_affiliation_id = utility_bean.getStudentAffiliationID(student_id); // �w������ID���i�[
	Vector[] mastered_lecture_data_indexes = null; // ���C�ςݍu�`�f�[�^�̃C���f�b�N�X���i�[
	int affiliation_year = utility_bean.getAffiliationYear(student_id); // �ݐДN�����i�[
	int entered_year = utility_bean.getEnteredYear(student_id); // ���w�����N���i�[
	int[] got_credits = new int[2]; // �擾�����P�ʂ��i�[
	boolean is_seminar_i_mastered = false; // ���K���𗚏C���Ă��邩�ǂ������i�[
	boolean is_thesis_lead_mastered = false; // �_���w���𗚏C���Ă��邩�ǂ������i�[
	boolean[] is_old_document_decipherment_1_mastered = new boolean[2]; // �Õ������1�𗚏C���Ă��邩�ǂ������i�[

	// ���C�ςݍu�`�f�[�^���擾�ł��Ă���ꍇ
	if (mastered_lecture_data != null) {
		// ���Ԏ�ł���ꍇ
		if (student_affiliation_id != -6) {
			mastered_lecture_data_indexes = new Vector[affiliation_year]; // �ݐДN���𗚏C�ςݍu�`�f�[�^�̃C���f�b�N�X�̗v�f���ɐݒ�

			// ���C�ςݍu�`�f�[�^�̃C���f�b�N�X���i�[����I�u�W�F�N�g�̃C���X�^���X�̐���
			for (int i = 0; i < mastered_lecture_data_indexes.length; i++) {
				mastered_lecture_data_indexes[i] = new Vector();
			}
		}

		// ���K���𗚏C���Ă��邩�ǂ����̃`�F�b�N�ƁA���C�ςݍu�`�f�[�^�̃C���f�b�N�X�̎擾
		for (int i = 0; i < mastered_lecture_data.length; i++) {
			int got_credits_index = Integer.parseInt(mastered_lecture_data[i][3]); // �擾�����P�ʂ̃C���f�b�N�X���i�[

			// �擾�����P�ʂ̃C���f�b�N�X���o�ϊw���̂��̂ł���ꍇ
			if (got_credits_index < got_credits.length) {
				got_credits[got_credits_index] += Integer.parseInt(database_bean.getColumnData("lecture", 2, mastered_lecture_data[i][0].substring(5))); // ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^��������N���X�ɒP�ʂ����Z
			}

			String lecture_type_id = mastered_lecture_data[i][0].substring(5, 10); // �u�`����ID���i�[

			// ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^�����K���̂��̂ł���ꍇ
			if (lecture_type_id.equals("06000")) {
				is_seminar_i_mastered = true; // ���K���𗚏C���Ă���Ɛݒ�
			}

			// ��Ԏ�ł���ꍇ
			if (student_affiliation_id == 6) {
				// ���K���𗚏C���Ă���Ɛݒ肳��Ă���ꍇ
				if (is_seminar_i_mastered) {
					break; // ���[�v���甲����
				}

				// ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^���_���w���̂��̂ł���ꍇ
				if (lecture_type_id.equals("06002")) {
					is_thesis_lead_mastered = true; // �_���w���𗚏C���Ă���Ɛݒ�
				}
			// ���Ԏ�ł���ꍇ
			} else {
				// ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^���w�����ʉȖڂɏ������Ă���ꍇ
				if (lecture_type_id.substring(0, 1).equals("1")) {
					// �Õ������A1�̂��̂ł���ꍇ
					if (lecture_type_id.equals("10061")) {
						is_old_document_decipherment_1_mastered[0] = true; // �Õ������A1�𗚏C���Ă���Ɛݒ�
					// �Õ������B1�̂��̂ł���ꍇ
					} else if (lecture_type_id.equals("10063")) {
						is_old_document_decipherment_1_mastered[1] = true; // �Õ������B1�𗚏C���Ă���Ɛݒ�
					}
				}

				mastered_lecture_data_indexes[Integer.parseInt(mastered_lecture_data[i][2]) - entered_year].addElement("" + i); // ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^�̃C���f�b�N�X�̐ݒ�
			}
		}
	}

	int present_semester_number = utility_bean.getSemesterNumber(); // ���݂̊w���ԍ����i�[



	present_semester_number = 1; // �e�X�g�p�̐ݒ�(�J���I����͍폜)



	String[][] registered_opening_lecture_data = database_bean.getRowsData("registered_opening_lecture", student_id); // �o�^�ς݊J�u�u�`�f�[�^���i�[
	String[][] registered_opening_lecture_ids = new String[6][7]; // �o�^�ς݊J�u�u�`ID���i�[

	// �o�^�ς݊J�u�u�`�f�[�^���擾�ł����ꍇ
	if (registered_opening_lecture_data != null) {
		// 2000�N�܂łɓ��w�������Ԏ�̉��K���𗚏C���Ă��Ȃ��w���ŁA���݂��H�w���ł���ꍇ
		if (entered_year <= 2000 && student_affiliation_id != 6 && !is_seminar_i_mastered && present_semester_number == 2) {
			int spring_semester_registered_opening_lecture_credit = 0; // �t�w���ɓo�^�����J�u�u�`�̒P�ʂ��i�[

			// �t�w���ɓo�^�����J�u�u�`�̒P�ʂ̎擾
			for (int i = 0; i < registered_opening_lecture_data.length; i++) {
				// ���݃A�N�Z�X���Ă���o�^�ς݊J�u�u�`���t�w���ɓo�^���ꂽ���̂ł���ꍇ
				if (registered_opening_lecture_data[i][0].substring(5, 6).equals("1")) {
					spring_semester_registered_opening_lecture_credit += Integer.parseInt(database_bean.getColumnData("lecture", 2, database_bean.getColumnData("opening_lecture", 1, registered_opening_lecture_data[i][1]))); // �t�w���ɓo�^�����J�u�u�`�̒P�ʂ̉��Z
				}
			}

			// �t�w���ɓo�^�����J�u�u�`�̒P�ʐ��̍��v��56�ɒB���Ă���ꍇ
			if (spring_semester_registered_opening_lecture_credit == 56) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("credit_error")).forward(request, response); // �G���[�y�[�W�ւ̓]��
			}
		}

		// �o�^�ς݊J�u�u�`ID�̎擾
		for (int i = 0; i < registered_opening_lecture_data.length; i++) {
			int semester_number = Integer.parseInt(registered_opening_lecture_data[i][1].substring(0, 1)); // �w���ԍ����i�[

			// ���݃A�N�Z�X���Ă���o�^�ς݊J�u�u�`�f�[�^���Q�Ƃ��Ă���J�u�u�`���ʔN�A���͌��݂̊w���̂��̂ł���ꍇ
			if (semester_number == 0 || semester_number == present_semester_number) {
				registered_opening_lecture_ids[Integer.parseInt(registered_opening_lecture_data[i][1].substring(1, 2))][Integer.parseInt(registered_opening_lecture_data[i][1].substring(2, 3))] = registered_opening_lecture_data[i][1]; // ���݃A�N�Z�X���Ă���o�^�ς݊J�u�u�`ID��ݒ�
			}
		}
	}

	int year; // �w�N���i�[
	boolean is_sports_science_mastered = false; // �X�|�[�c�Ȋw�𗚏C���Ă��邩�ǂ������i�[
	boolean[][] is_foreign_language_i_mastered = new boolean[database_bean.getColumnDataArray("lecture_affiliation", 0, "2").length][is_old_document_decipherment_1_mastered.length]; // �O����I�𗚏C���Ă��邩�ǂ������i�[

	// ��Ԏ�ł���ꍇ
	if (student_affiliation_id == 6) {
		// �ݐДN����4�𒴂��Ă���ꍇ
		if (affiliation_year > 4) {
			year = 4; // �w�N��4�ɐݒ�
		// �����Ă��Ȃ��ꍇ
		} else {
			year = affiliation_year; // �ݐДN�����w�N�ɐݒ�
		}
	// ���Ԏ�ł���ꍇ
	} else {
		// �ݐДN����1�ł���ꍇ
		if (affiliation_year == 1) {
			year = 1; // �w�N��1�ɐݒ�
		// 1�łȂ��ꍇ
		} else {
			year = 2; // �w�N��2�ɐݒ�
		}

		// ���C�ςݍu�`�f�[�^���擾�ł��Ă��āA�w�N��2�ȏ�ɐݒ肳��Ă���ꍇ
		if (mastered_lecture_data != null && year >= 2) {
			int[] credits = new int[2]; // �P�ʂ��i�[
			boolean is_basic_seminar_mastered = false; // ��b���K�𗚏C���Ă��邩�ǂ������i�[
			int[] got_english_i_credits = new int[2]; // �擾�����p��I�̒P�ʂ��i�[
			int got_english_ii_credit = 0; // �擾�����p��II�̒P�ʂ��i�[
			int got_second_foreign_language_credit = 0; // �擾������2�O����̒P�ʂ��i�[

			// ���C�󋵂̎擾�Ɗw�N�̐ݒ�
			for (int i = 0; i < mastered_lecture_data_indexes.length; i++) {
				for (int j = 0; j < mastered_lecture_data_indexes[i].size(); j++) {
					String lecture_id = mastered_lecture_data[Integer.parseInt(mastered_lecture_data_indexes[i].elementAt(j).toString())][0].substring(5); // �u�`ID���i�[
					String lecture_type_id = lecture_id.substring(0, 5); // �u�`����ID���i�[

					// ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^����b���K�̂��̂ł���ꍇ
					if (lecture_type_id.equals("06003")) {
						is_basic_seminar_mastered = true; // ��b���K�𗚏C���Ă���Ɛݒ�
					// �X�|�[�c�Ȋw�̂��̂ł���ꍇ
					} else if (lecture_type_id.equals("10052")) {
						is_sports_science_mastered = true; // �X�|�[�c�Ȋw�𗚏C���Ă���Ɛݒ�
					// ��b���K�ƃX�|�[�c�Ȋw�ȊO�̍u�`�ł���ꍇ
					} else {
						int course_id = Integer.parseInt(lecture_id.substring(0, 1)); // �Ȗ�ID���i�[
						int credit = Integer.parseInt(database_bean.getColumnData("lecture", 2, lecture_id)); // �P�ʂ��i�[

						// ���ȖځA���͊w�����ʉȖڂ̍u�`�ł���ꍇ
						if (course_id <= 1) {
							credits[course_id] += credit; // �P�ʂ̉��Z
						// �O����̍u�`�ł���ꍇ
						} else if (course_id == 2) {
							int lecture_affiliation_number = Integer.parseInt(lecture_type_id.substring(1, 2)); // �u�`�����ԍ����i�[

							// �p��̂��̂ł���ꍇ
							if (lecture_affiliation_number == 0) {
								// �p��II�̂��̂ł���ꍇ
								if (lecture_type_id.equals("20004") || lecture_type_id.equals("20005")) {
									got_english_ii_credit += credit; // �p��II�̒P�ʂ̉��Z
								// �p��Ia�̂��̂ł���ꍇ
								} else if (lecture_type_id.equals("20000") || lecture_type_id.equals("20001") || lecture_type_id.equals("20007") || lecture_type_id.equals("20008")) {
									got_english_i_credits[0] += credit; // �p��Ia�̒P�ʂ̉��Z
								// �p��Ib�̂��̂ł���ꍇ
								} else if (lecture_type_id.equals("20002") || lecture_type_id.equals("20003") || lecture_type_id.equals("20009") || lecture_type_id.equals("20010")) {
									got_english_i_credits[1] += credit; // �p��Ib�̒P�ʂ̉��Z
								}
							// �p��ȊO�̊O����ł���ꍇ
							} else {
								got_second_foreign_language_credit += credit; // ��2�O����̒P�ʂ̉��Z
								int lecture_type_number = Integer.parseInt(lecture_type_id.substring(3)); // �u�`��ޔԍ����i�[

								// Ia�A����Ib�ł���ꍇ
								if (lecture_type_number < is_foreign_language_i_mastered[lecture_affiliation_number].length) {
									is_foreign_language_i_mastered[lecture_affiliation_number][lecture_type_number] = true; // ���݃A�N�Z�X���Ă����ނ̊O����Ia�A����Ib�𗚏C���Ă���Ɛݒ�
								}
							}
						}
					}
				}

				// �w�N��2�ɐݒ肳��A�ݐДN����3�ȏ�ł���ꍇ
				if (year == 2 && affiliation_year >= 3) {
					// 3�񐶂ւ̐i�������𖞂����Ă���ꍇ
					if (is_basic_seminar_mastered && is_sports_science_mastered && ((got_english_i_credits[0] + got_english_i_credits[1]) + got_english_ii_credit) >= 4 && got_second_foreign_language_credit >= 4 && (credits[0] + credits[1]) >= 36) {
						// ���݂̗v�f�ԍ����ŏI�̂��̂ł���ꍇ
						if (i == (mastered_lecture_data_indexes.length - 1)) {
							year = 3; // �w�N��3�ɐݒ�
						// �ŏI�̂��̂łȂ��ꍇ
						} else {
							year = 4; // �w�N��4�ɐݒ�
						}
					}
				}
			}

			// �p��I�𗚏C���Ă��邩�ǂ����̐ݒ�
			for (int i = 0; i < got_english_i_credits.length; i++) {
				// ���݃A�N�Z�X���Ă����ނ̉p��I�̎擾�P�ʂ�2�ɒB���Ă���ꍇ
				if (got_english_i_credits[i] == 2) {
					is_foreign_language_i_mastered[0][i] = true; // ���݃A�N�Z�X���Ă����ނ̉p��I�𗚏C���Ă���Ɛݒ�
				}
			}
		}
	}

	Vector[][] opening_lecture_data_indexes = new Vector[registered_opening_lecture_ids.length][registered_opening_lecture_ids[0].length]; // �J�u�u�`�f�[�^�̃C���f�b�N�X���i�[
	Vector[][] lecture_data_indexes = new Vector[registered_opening_lecture_ids.length][registered_opening_lecture_ids[0].length]; // �u�`�f�[�^�̃C���f�b�N�X���i�[

	// Vector�v�f�̏�����
	for (int i = 0; i < opening_lecture_data_indexes.length; i++) {
		for (int j = 0; j < opening_lecture_data_indexes[i].length; j++) {
			opening_lecture_data_indexes[i][j] = new Vector();
			lecture_data_indexes[i][j] = new Vector();
		}
	}

	String[][] lecture_data = database_bean.getRowsData("lecture"); // �u�`�f�[�^���i�[
	String[][] opening_lecture_data = database_bean.getRowsData("opening_lecture"); // �J�u�u�`�f�[�^���i�[
	int present_year = Calendar.getInstance().get(Calendar.YEAR); // ���݂̔N���i�[
	int master_year_number_column_index = (student_affiliation_id / 6) + 3; // ���C�N���ԍ����̃C���f�b�N�X���i�[
	final int[][] MASTERABLE_YEAR_RANGES = {{1, 1}, {1, 2}, {1, 4}, {2, 2}, {2, 4}, {3, 3}, {3, 4}, {4, 4}}; // ���C�\�N���͈̔͂��i�[

	// �I���\�ȊJ�u�u�`�Ɋ֌W����J�u�u�`�f�[�^�ƍu�`�f�[�^�̃C���f�b�N�X�̎擾
	for (int i = 0; i < opening_lecture_data.length; i++) {
		int semester_number = Integer.parseInt(opening_lecture_data[i][0].substring(0, 1)); // �w���ԍ����i�[
		int day_number = Integer.parseInt(opening_lecture_data[i][0].substring(1, 2)); // �j���ԍ����i�[
		int class_number = Integer.parseInt(opening_lecture_data[i][0].substring(2, 3)); // �N���X�ԍ����i�[

		// ���݂��t�w���ł���ꍇ
		if (present_semester_number == 1) {
			// ���݃A�N�Z�X���Ă���J�u�u�`���H�w���̂��̂ł���ꍇ
			if (semester_number == 2) {
				break; // ���[�v���甲����
			}
		// �H�w���ł���ꍇ
		} else {
			// ���݃A�N�Z�X���Ă���J�u�u�`���ʔN�̂��̂ł���ꍇ
			if (semester_number == 0) {
				// ���̍u�`���t�w���ɓo�^���Ă��Ȃ��ꍇ
				if (registered_opening_lecture_ids[day_number][class_number] == null || !registered_opening_lecture_ids[day_number][class_number].equals(opening_lecture_data[i][0])) {
					continue; // ���̗v�f�ɏ������ڂ�
				}
			// �t�w���̂��̂ł���ꍇ
			} else if (semester_number == 1) {
				continue; // ���̗v�f�ɏ������ڂ�
			// �H�w���̂��̂ŁA�J�u���������ɒʔN�̍u�`���t�w���ɓo�^���Ă���ꍇ
			} else if (registered_opening_lecture_ids[day_number][class_number] != null && registered_opening_lecture_ids[day_number][class_number].substring(0, 1).equals("0")) {
				continue; // ���̗v�f�ɏ������ڂ�
			}
		}

		// ���݃A�N�Z�X���Ă���J�u�u�`�����ԂɊJ�u�������̂ł���ꍇ
		if (class_number <= 4) {
			// �w�肳�ꂽ�w�Дԍ��̊w������Ԏ�ŁA���ԂɊJ�u�����u�`��30�P�ʎ擾���Ă���ꍇ
			if (student_affiliation_id == 6 && got_credits[0] == 30) {
				continue; // ���̗v�f�ɏ������ڂ�
			}
		// ��ԂɊJ�u�������̂ł���ꍇ
		} else {
			// �w�肳�ꂽ�w�Дԍ��̊w�������Ԏ�ŁA��ԂɊJ�u�����u�`��30�P�ʎ擾���Ă���ꍇ
			if (student_affiliation_id != 6 && got_credits[1] == 30) {
				continue; // ���̗v�f�ɏ������ڂ�
			}
		}

		boolean is_mastered = false; // ���C�ς݂ł��邩�ǂ������i�[

		// ���C�ςݍu�`�f�[�^���擾�ł��Ă���ꍇ
		if (mastered_lecture_data != null) {
			String lecture_type_id = opening_lecture_data[i][1].substring(0, 5); // �u�`���ID���i�[

			// ���݃A�N�Z�X���Ă���J�u�u�`ID�ɊY������u�`�����C�ς݂ł��邩�ǂ����̃`�F�b�N
			for (int j = 0; j < mastered_lecture_data.length; j++) {
				// ���݃A�N�Z�X���Ă���J�u�u�`�f�[�^�����u�`���ID�����C�ςݍu�`�f�[�^�̗��C�ςݍu�`ID�̍u�`���ID�����Ɠ������ꍇ
				if (lecture_type_id.equals(mastered_lecture_data[j][0].substring(5, 10))) {
					is_mastered = true; // ���C�ς݂ł��邱�Ƃ̐ݒ�
					break; // ���[�v���甲����
				}
			}

			// ���݃A�N�Z�X���Ă���J�u�u�`ID�ɊY������u�`�����C�ς݂ł���ꍇ
			if (is_mastered) {
				continue; // ���̗v�f�ɏ������ڂ�
			}
		}

		int lecture_data_index = 0; // �u�`�f�[�^�̃C���f�b�N�X���i�[

		// �u�`�f�[�^�̃C���f�b�N�X�̎擾
		for (int j = 0; j < lecture_data.length; j++) {
			// ���݃A�N�Z�X���Ă���J�u�u�`�f�[�^�̎��u�`ID�����݃A�N�Z�X���Ă���u�`ID�Ɠ������ꍇ
			if (opening_lecture_data[i][1].equals(lecture_data[j][0])) {
				lecture_data_index = j; // ���݂̗v�f�ԍ����C���f�b�N�X�ɐݒ�
				break; // ���[�v���甲����
			}
		}

		int masterable_year_number = Integer.parseInt(lecture_data[lecture_data_index][master_year_number_column_index + 2]); // ���C�\�N���ԍ����i�[

		// ���݃A�N�Z�X���Ă���u�`�f�[�^�̗��C�\�N�������C�s�A���͊w�N�����C�\�N���͈̔͂ɓ����Ă��Ȃ��ꍇ
		if (masterable_year_number == 8 || year < MASTERABLE_YEAR_RANGES[masterable_year_number][0] || MASTERABLE_YEAR_RANGES[masterable_year_number][1] < year) {
			continue; // ���̗v�f�ɏ������ڂ�
		}

		int[] masterable_years = {present_year - 11, present_year}; // ���C�\�N���i�[

		// ���C�\�N�̎擾
		for (int j = 7; j < 9; j++) {
			if (lecture_data[lecture_data_index][j] != null) {
				masterable_years[j - 7] = Integer.parseInt(lecture_data[lecture_data_index][j]);
			}
		}

		// ���w�N�����݃A�N�Z�X���Ă���u�`�̗��C�\�N�͈͓̔��ɓ����Ă��Ȃ��ꍇ
		if (entered_year < masterable_years[0] || masterable_years[1] < entered_year) {
			continue; // ���̗v�f�ɏ������ڂ�
		}

		// �w�肳�ꂽ�w�Дԍ��̊w�������Ԏ�ŁA���݃A�N�Z�X���Ă���u�`�ɑ΂��A�w�肳�ꂽ�w�Дԍ��̊w�����������Ă���w�Ȃ����C�s�ɐݒ肳��Ă���ꍇ
		if (student_affiliation_id != 6 && lecture_data[lecture_data_index][student_affiliation_id + 9] != null) {
			continue; // ���̗v�f�ɏ������ڂ�
		}

		String lecture_affiliation_number = lecture_data[lecture_data_index][0].substring(0, 3); // �u�`�����ԍ����i�[
		int lecture_type_number = Integer.parseInt(lecture_data[lecture_data_index][0].substring(3, 5)); // �u�`��ޔԍ����i�[
		String lecture_division_number = "" + Integer.parseInt(lecture_data[lecture_data_index][0].substring(5)); // �u�`�敪�ԍ����i�[

		// ���݃A�N�Z�X���Ă���u�`�����̂��̑��ɑ����Ă���ꍇ
		if (lecture_affiliation_number.equals("060")) {
			// ���K���A���K���A���͊�b���K(��w����Z�~�i�[)�ł���ꍇ
			if (lecture_type_number <= 3 && lecture_type_number != 2) {
				int lecturer_column_index; // �����̗�̃C���f�b�N�X���i�[

				// ��b���K�ł���ꍇ
				if (lecture_type_number == 3) {
					lecturer_column_index = 5; // 5�������̗�̃C���f�b�N�X�ɐݒ�
				// ���K���A���͉��K���ł���ꍇ
				} else {
					// ���K���ŁA�_���w���𗚏C���Ă���ꍇ
					if (lecture_type_number == 0) {
						if (is_thesis_lead_mastered) {
							continue; // ���̗v�f�ɏ������ڂ�
						}
					// ���K���ŁA���K���𗚏C���Ă��Ȃ��ꍇ
					} else if (!is_seminar_i_mastered) {
						continue; // ���̗v�f�ɏ������ڂ�
					}

					lecturer_column_index = 14; // 14�������̗�̃C���f�b�N�X�ɐݒ�
				}

				// ���݃A�N�Z�X���Ă���u�`�̋������w�肳�ꂽ�w�Дԍ��̊w���̋����łȂ��ꍇ
				if (!database_bean.getColumnDataArray("opening_lecture_lecturer", 1, opening_lecture_data[i][0])[0].equals(student_data[lecturer_column_index])) {
					continue; // ���̗v�f�ɏ������ڂ�
				}
			// ��񃊃e���V�[�ł���ꍇ
			} else if (lecture_type_number == 4) {
				// ���ԂɊJ�u�������̂ŁA�w�肳�ꂽ�w�����������Ă���N���X�ł͂Ȃ��ꍇ
				if (student_affiliation_id != 6 && lecture_data[lecture_data_index][1].indexOf(student_data[6]) == -1) {
					continue; // ���̗v�f�ɏ������ڂ�
				}
			// �O�����������ŁA�w�肳�ꂽ�w�Дԍ��̊w���̊w�N��2�Ō��݂��t�w���ł���ꍇ
			} else if (lecture_type_number == 5 && (year == 2 && present_semester_number == 1)) {
				continue; // ���̗v�f�ɏ������ڂ�
			}
		// �w�����ʂɑ����Ă���ꍇ
		} else if (lecture_affiliation_number.equals("100")) {
			// ���݃A�N�Z�X���Ă���u�`�����N�Ɛ����ł���ꍇ
			if (lecture_type_number == 73) {
				// �w�肳�ꂽ�w�����������Ă���N���X�ł͂Ȃ��ꍇ
				if(lecture_data[lecture_data_index][1].indexOf(student_data[7]) == -1) {
					continue; // ���̗v�f�ɏ������ڂ�
				}
			// �X�|�[�c�Ȋw�ł���ꍇ
			} else if (lecture_type_number == 52) {
				// ���ԂɊJ�u�������̂ŁA�S���Q���̃N���X�ł��w�肳�ꂽ�w�����������Ă���N���X�ł��Ȃ��ꍇ
				if (student_affiliation_id != 6 && !lecture_division_number.equals("0") && lecture_data[lecture_data_index][1].indexOf(student_data[8]) == -1) {
					continue; // ���̗v�f�ɏ������ڂ�
				}
			// ��w���K�ł���ꍇ
			} else if (lecture_type_number <= 56 && lecture_type_number >= 60) {
				// �w�肳�ꂽ�w�Дԍ��̊w���̊w�N��2�Ō��݂��t�w���ł���ꍇ
				if (year == 2 && present_semester_number == 1) {
					continue; // ���̗v�f�ɏ������ڂ�
				}

				int language_number = lecture_type_number - 56; // ��w�ԍ����i�[

				// ���݃A�N�Z�X���Ă����w���K�Ɋ֌W����O����I�𗚏C���Ă��邩�ǂ����̃`�F�b�N
				for (int j = 0; j < is_foreign_language_i_mastered[language_number].length; j++) {
					// ���݃A�N�Z�X���Ă����ނ̊O����I�𗚏C���Ă��Ȃ��ꍇ
					if (!is_foreign_language_i_mastered[language_number][j]) {
						continue; // ���̗v�f�ɏ������ڂ�
					}
				}
			// �Õ������A2�ł���ꍇ
			} else if (lecture_type_number == 62) {
				// �Õ������A1�𗚏C���Ă��Ȃ��ꍇ
				if (!is_old_document_decipherment_1_mastered[0]) {
					continue; // ���̗v�f�ɏ������ڂ�
				}
			// �Õ������B2�ł���ꍇ
			} else if (lecture_type_number == 64) {
				// �Õ������B1�𗚏C���Ă��Ȃ��ꍇ
				if (!is_old_document_decipherment_1_mastered[1]) {
					continue; // ���̗v�f�ɏ������ڂ�
				}
			// ���{����ŁA�w�肳�ꂽ�w�������{�l�ł���ꍇ
			} else if ((lecture_type_number >= 65 && lecture_type_number <= 68) && student_data[4].equals("0")) {
				continue; // ���̗v�f�ɏ������ڂ�
			}
		// �O����ɑ����Ă���ꍇ
		} else if (lecture_data[lecture_data_index][0].substring(0, 1).equals("2")) {
			// ���݃A�N�Z�X���Ă���u�`�����{��ŁA�w�肳�ꂽ�w�Дԍ��̊w�������{�l�ł���ꍇ
			if (lecture_affiliation_number.equals("260") && student_data[4].equals("0")) {
				continue; // ���̗v�f�ɏ������ڂ�
			}

			// ���݃A�N�Z�X���Ă���u�`�����ԂɊJ�u�������̂ł���ꍇ
			if (student_affiliation_id != 6) {
				// ���݃A�N�Z�X���Ă���u�`���p��ł���ꍇ
				if (lecture_affiliation_number.equals("200")) {
					// ���݃A�N�Z�X���Ă���u�`����w�p�����A�p��Ia�A�p��b��b�A�p��Ib�A�p��IIh�A���͉p��IIa�ł���ꍇ
					if (lecture_type_number >= 4 && lecture_type_number <= 10 && lecture_type_number != 6) {
						int class_column_index; // �N���X�̗�̃C���f�b�N�X���i�[

						// ���݃A�N�Z�X���Ă���u�`���p��IIh�ł���ꍇ
						if (lecture_type_number == 4) {
							class_column_index = 10; // 10���N���X�̗�̃C���f�b�N�X�ɐݒ�
						// �p��IIa�ł���ꍇ
						} else if (lecture_type_number == 5) {
							class_column_index = 11; // 11���N���X�̗�̃C���f�b�N�X�ɐݒ�
						// ��w�p�����A�p��Ia�A�p��b��b�A���͉p��Ib�ł���ꍇ
						} else {
							// ��w�p�����A���͉p��Ia�ł���ꍇ
							if (lecture_type_number == 7 || lecture_type_number == 8) {
								// �p��Ia(�V��)�𗚏C���Ă���ꍇ
								if (is_foreign_language_i_mastered[0][0]) {
									continue; // ���̗v�f�ɏ������ڂ�
								}
							// �p��b��b�A���͉p��Ib�ŁA�p��Ib(�V��)�𗚏C���Ă���ꍇ
							} else if (is_foreign_language_i_mastered[0][1]) {
								continue; // ���̗v�f�ɏ������ڂ�
							}

							class_column_index = 9; // 9���N���X�̗�̃C���f�b�N�X�ɐݒ�
						}

						// ���݃A�N�Z�X���Ă���u�`���p��II�A���͉p��I�ōݐДN����1�ł���A�w�肳�ꂽ�w�������݃A�N�Z�X���Ă���u�`�̏������Ă���N���X�ł͂Ȃ��ꍇ
						if ((lecture_type_number <= 5 || affiliation_year == 1) && lecture_data[lecture_data_index][1].indexOf(student_data[class_column_index]) == -1) {
							continue; // ���̗v�f�ɏ������ڂ�
						}
					}
				// ���{��Ɖp��ȊO�̊O����ł���ꍇ
				} else {
					// ���݃A�N�Z�X���Ă���u�`���w�肳�ꂽ�w�Дԍ��̊w�����I����������łȂ��ꍇ
					if (!lecture_data[lecture_data_index][0].substring(1, 2).equals(student_data[12])) {
						// �w�肳�ꂽ�w�Дԍ��̊w���̍ݐДN����1�A���͌��݃A�N�Z�X���Ă���u�`���ė��C�N���X�ł���ꍇ
						if(affiliation_year == 1 || lecture_data[lecture_data_index][1].indexOf("��") != -1) {
							continue; // ���̗v�f�ɏ������ڂ�
						}
					// �I����������ł���ꍇ
					} else {
						// ���݃A�N�Z�X���Ă���u�`���w�肳�ꂽ�w�����������Ă���N���X�ł��ė��C�N���X�ł��Ȃ��ꍇ
						if (lecture_data[lecture_data_index][1].indexOf(student_data[13]) == -1 && lecture_data[lecture_data_index][1].indexOf("��") == -1) {
							continue; // ���̗v�f�ɏ������ڂ�
						}

						// ���݃A�N�Z�X���Ă���u�`���ė��C�N���X�ł���ꍇ
						if (lecture_data[lecture_data_index][1].indexOf("��") != -1) {
							// ������Ia�Eb(��)�ł���ꍇ
							if (lecture_affiliation_number.equals("240") && lecture_type_number == 4) {
								// Ia��Ib�����ɗ��C���Ă���ꍇ
								if (is_foreign_language_i_mastered[4][0] && is_foreign_language_i_mastered[4][1]) {
									continue; // ���̗v�f�ɏ������ڂ�
								}
							// ������Ia�Eb(��)�ȊO�̍ė��C�N���X�ŁA�ݐДN����2�ȉ��ł���ꍇ
							} else if (affiliation_year <= 2) {
								continue; // ���̗v�f�ɏ������ڂ�
							}
						}
					}
				}
			}
		}

		// �C���f�b�N�X�̕t��
		opening_lecture_data_indexes[day_number][class_number].addElement("" + i);
		lecture_data_indexes[day_number][class_number].addElement("" + lecture_data_index);
	}

	// �w������ID����Ԏ�̂��̂łȂ��ꍇ
	if (student_affiliation_id != 6) {
		// ��Ԃ̍u�`�f�[�^�̒��ɒ��Ԃ̍u�`�f�[�^�Ɠ�����ނ̃f�[�^���܂܂�Ă��邩�ǂ������`�F�b�N���A�܂܂�Ă���ꍇ�͍폜
		for (int i = 0; i < 5; i++) {
			for (int j = 5; j < registered_opening_lecture_ids[i].length; j++) {
				for (int k = 0; k < opening_lecture_data_indexes[i][j].size(); k++) {
					String lecture_type_id = lecture_data[Integer.parseInt(lecture_data_indexes[i][j].elementAt(k).toString())][0].substring(0, 5); // �u�`���ID���i�[

					same_lecture_data_check: for (int l = 0; l < 5; l++) {
						for (int m = 0; m < 5; m++) {
							for (int n = 0; n < opening_lecture_data_indexes[l][m].size(); n++) {
								// ���݃A�N�Z�X���Ă����Ԃ̍u�`�f�[�^�Ɠ�����ނ̍u�`�f�[�^�����Ԃ̍u�`�f�[�^�̒��Ɋ܂܂�Ă����ꍇ
								if(lecture_data[Integer.parseInt(lecture_data_indexes[l][m].elementAt(n).toString())][0].substring(0, 5).equals(lecture_type_id)) {
									// ���݃A�N�Z�X���Ă���f�[�^�̃C���f�b�N�X�̍폜
									opening_lecture_data_indexes[i][j].removeElementAt(k);
									lecture_data_indexes[i][j].removeElementAt(k);

									k--; // ���݃A�N�Z�X���Ă���f�[�^�̗v�f�ԍ��̌��Z
									break same_lecture_data_check; // ���[�v���甲����
								}
							}
						}
					}
				}
			}
		}
	}

	// �K�v�J�u�u�`ID���ݒ肳��Ă���J�u�u�`�f�[�^�ɑ΂��Ă��̊J�u�u�`ID�̃f�[�^���������A���݂��Ȃ��ꍇ�͍폜
	for (int i = 0; i < registered_opening_lecture_ids.length; i++) {
		for (int j = 0; j < registered_opening_lecture_ids[i].length; j++) {
			for (int k = 0; k < opening_lecture_data_indexes[i][j].size(); k++) {
				int opening_lecture_data_index = Integer.parseInt(opening_lecture_data_indexes[i][j].elementAt(k).toString()); // �J�u�u�`�f�[�^�̃C���f�b�N�X���i�[

				// �K�v�J�u�u�`ID���ݒ肳��Ă��Ȃ��ꍇ
				if (opening_lecture_data[opening_lecture_data_index][2] == null) {
					continue; // ���̗v�f�ɏ������ڂ�
				}

				int day_number = Integer.parseInt(opening_lecture_data[opening_lecture_data_index][2].substring(1, 2)); // �j���ԍ����i�[
				int class_number = Integer.parseInt(opening_lecture_data[opening_lecture_data_index][2].substring(2, 3)); // �N���X�ԍ����i�[
				boolean is_necessary_opening_lecture_not_found = true; // �K�v�J�u�u�`��������Ȃ��������ǂ������i�[

				// �K�v�J�u�u�`�ɓ�����J�u�u�`ID���擾�����J�u�u�`ID���ɑ��݂��邩�ǂ����̃`�F�b�N
				for (int l = 0; l < opening_lecture_data_indexes[day_number][class_number].size(); l++) {
					// ���݃A�N�Z�X���Ă���J�u�u�`ID���w�肳�ꂽ�K�v�J�u�u�`ID�Ɠ������ꍇ
					if (opening_lecture_data[Integer.parseInt(opening_lecture_data_indexes[day_number][class_number].elementAt(l).toString())][0].equals(opening_lecture_data[opening_lecture_data_index][2])) {
						is_necessary_opening_lecture_not_found = false; // �K�v�J�u�u�`�����������Ɛݒ�
						break; // ���[�v���甲����
					}
				}

				// �K�v�J�u�u�`��������Ȃ������ꍇ
				if (is_necessary_opening_lecture_not_found) {
					// ���݃A�N�Z�X���Ă���f�[�^�̃C���f�b�N�X�̍폜
					opening_lecture_data_indexes[i][j].removeElementAt(k);
					lecture_data_indexes[i][j].removeElementAt(k);

					k--; // ���݃A�N�Z�X���Ă���f�[�^�̗v�f�ԍ��̌��Z
				}
			}
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
	String[][] column_data = new String[registered_opening_lecture_ids.length][registered_opening_lecture_ids[0].length]; // ��f�[�^���i�[

	// ��f�[�^�̎擾
	for (int i = 0; i < column_data.length; i++) {
		for (int j = 0; j < column_data[i].length; j++) {
			String column_data_name = "opening_lecture_id_" + i + j; // ��f�[�^�̖��O���i�[

			// ���݂��H�w���ŁA���݃A�N�Z�X���Ă�������ɊJ�u�u�`���o�^����Ă��āA���̍u�`���ʔN�̂��̂ł���ꍇ
			if (present_semester_number == 2 && registered_opening_lecture_ids[i][j] != null && registered_opening_lecture_ids[i][j].substring(0, 1).equals("0")) {
				StringBuffer string_buffer_column_data = new StringBuffer(); // ��f�[�^���i�[

				// ��f�[�^�̎擾
				string_buffer_column_data.append("<input type=\"hidden\" name=\"" + column_data_name + "\" value=\"" + registered_opening_lecture_ids[i][j] + "\" />\n");
				string_buffer_column_data.append("\t\t\t\t\t\t\t\t<b>" + lecture_data[Integer.parseInt(lecture_data_indexes[i][j].firstElement().toString())][1] + "</b>");

				column_data[i][j] = string_buffer_column_data.toString(); // ��f�[�^�̐ݒ�
			// ��L�̏����𖞂����Ȃ��ꍇ
			} else {
				// �I�𗓃f�[�^�̗v�f���̐ݒ�
				String[] select_box_ids = new String[opening_lecture_data_indexes[i][j].size() + 1]; // �I�𗓂�ID���i�[
				String[] select_box_outlines = new String[select_box_ids.length]; // �I�𗓂̌��o�����i�[

				select_box_outlines[0] = "(�I���Ȃ�)�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@"; // "�I���Ȃ�"���̐ݒ�
				int select_box_index = 1; // �I�𗓂̃C���f�b�N�X���i�[

				// �g���Ă��Ȃ��J�u�u�`�f�[�^���c���Ă���ԌJ��Ԃ�
				while (opening_lecture_data_indexes[i][j].size() > 0) {
					int minimum_master_year_number_index = 0; // �ł����������C�N���ԍ��̃C���f�b�N�X���i�[
					int minimum_master_year_number = Integer.parseInt(lecture_data[Integer.parseInt(lecture_data_indexes[i][j].elementAt(minimum_master_year_number_index).toString())][master_year_number_column_index]); // �ł����������C�N���ԍ����i�[

					// �ł����������C�N���ԍ��̃C���f�b�N�X�̎擾
					for (int l = 1; l < opening_lecture_data_indexes[i][j].size(); l++) {
						int master_year_number = Integer.parseInt(lecture_data[Integer.parseInt(lecture_data_indexes[i][j].elementAt(l).toString())][master_year_number_column_index]); // ���C�N���ԍ����i�[

						// ���݃A�N�Z�X���Ă���v�f�ԍ��̍u�`�f�[�^�������C�N���ԍ����ł����������C�N���ԍ��̃C���f�b�N�X�̍u�`�f�[�^�̂��̂����������ꍇ
						if (master_year_number < minimum_master_year_number) {
							minimum_master_year_number_index = l; // ���݂̗v�f�ԍ����ł����������C�N���ԍ��̃C���f�b�N�X�ɐݒ�
							minimum_master_year_number = master_year_number; // ���݂̗��C�N���ԍ����ł����������C�N���ԍ��ɐݒ�
						}
					}

					// �I�𗓃f�[�^�̐ݒ�
					select_box_ids[select_box_index] = opening_lecture_data[Integer.parseInt(opening_lecture_data_indexes[i][j].elementAt(minimum_master_year_number_index).toString())][0];
					select_box_outlines[select_box_index] = lecture_data[Integer.parseInt(lecture_data_indexes[i][j].elementAt(minimum_master_year_number_index).toString())][1];

					select_box_index++; // �I�𗓂̃C���f�b�N�X�̉��Z

					// �ł����������C�u�`�ԍ��ɊY������f�[�^�̍폜
					opening_lecture_data_indexes[i][j].removeElementAt(minimum_master_year_number_index);
					lecture_data_indexes[i][j].removeElementAt(minimum_master_year_number_index);
				}

				column_data[i][j] = html_bean.getSelectBox(column_data_name, registered_opening_lecture_ids[i][j], select_box_ids, select_box_outlines, 8); // ��f�[�^�̐ݒ�
			}
		}
	}

	String[] semester_types = {"�t", "�H"}; // �w���̎�ނ��i�[
%>
<%= html_bean.getHeader("���C�u�`�̑I��(" + semester_types[present_semester_number - 1] + "�w��)") %>
		(�w�Дԍ�: <b><%= student_id %></b> ����: <b><%= student_data[1] %></b>)<br />
		<br />

		<a href="./report.jsp?student_id=<%= student_id %>">����܂ł̗��C��</a><br />
		<br />

		���C�o�^�������u�`��I�����A�I�����܂����牺��[���M]�{�^�����N���b�N���ĉ������B<br />
		(���C�ς݂Ȃǂ̏��������u�ł��Ȃ��u�`�͑I�𗓂��珜�O����Ă��܂�)<br />
		<br />

		<form method="POST" action="./confirm.jsp">
			<input type="hidden" name="student_id" value="<%= student_id %>" />
			<%= html_bean.getOpeningLectureList(column_data, "���M") %>
		</form>
		<a href="<%= utility_bean.getLogoutPageURL() %>">���O�A�E�g</a><br />

		<%= html_bean.getFooter() %>
