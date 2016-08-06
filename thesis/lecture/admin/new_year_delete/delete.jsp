<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String[] student_ids = database_bean.getColumnDataArray("student", 0); // �w�Дԍ����i�[

	// �w�Дԍ����擾�ł����ꍇ
	if (student_ids != null) {
		// �e�w�Дԍ��̊w�����폜�����𖞂����Ă��邩�ǂ������`�F�b�N���A�������Ă���ꍇ�͍폜
		student_check: for (int i = 0; i < student_ids.length; i++) {
			int affiliation_year = utility_bean.getAffiliationYear(student_ids[i]); // �ݐДN�����i�[

			// ���݃A�N�Z�X���Ă���w�Дԍ��̊w���̍ݐДN����4�����ł���ꍇ
			if (affiliation_year < 4) {
				continue; // ���̗v�f�ɏ������ڂ�
			// 12�𒴂��Ă���ꍇ
			} else if (affiliation_year > 12) {
				database_bean.delete("student", student_ids[i]); // �f�[�^�̍폜
				continue; // ���̗v�f�ɏ������ڂ�
			}

			String[][] mastered_lecture_data = database_bean.getRowsData("mastered_lecture", student_ids[i]); // ���C�ςݍu�`�f�[�^���i�[

			// ���C�ς݃f�[�^���擾�ł��Ȃ������ꍇ
			if (mastered_lecture_data == null) {
				continue; // ���̗v�f�ɏ������ڂ�
			}

			int student_affiliation_id = utility_bean.getStudentAffiliationID(student_ids[i]); // �w������ID���i�[
			int[] got_credits = new int[4]; // �擾�����P�ʂ��i�[
			boolean is_seminar_mastered = false; // ���K(��b�A���͑��)�𗚏C���Ă��邩�ǂ������i�[
			int affiliated_subject_mastered_lecture_count = 0; // �����w�Ȃ̗��C�u�`�����i�[
			int required_subject_mastered_count = 0; // �K�C�Ȗڂ̗��C�����i�[
			int[] elective_required_subject_mastered_count = new int[3]; // �I��K�C�Ȗڂ̗��C�����i�[

			// ���C�󋵂̎擾
			for (int j = 0; j < mastered_lecture_data.length; j++) {
				String lecture_id = mastered_lecture_data[j][0].substring(5); // �u�`ID���i�[
				int course_id = Integer.parseInt(lecture_id.substring(0, 1)); // �Ȗ�ID���i�[
				got_credits[course_id] += Integer.parseInt(database_bean.getColumnData("lecture", 2, lecture_id)); // ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^��������ȖڂɒP�ʂ����Z

				// ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�����Ȗڂ̂��̂ł͂Ȃ��ꍇ
				if (course_id != 0) {
					continue; // ���̗v�f�ɏ������ڂ�
				}

				int lecture_affiliation_number = Integer.parseInt(lecture_id.substring(1, 2)); // �u�`�����ԍ����i�[
				int lecture_type_number = Integer.parseInt(lecture_id.substring(3, 5)); // �u�`��ޔԍ����i�[

				// ���݃A�N�Z�X���Ă��闚�C�ςݍu�`�ƌ��݃A�N�Z�X���Ă���w�Дԍ��̊w�����������Ă���w�Ȃ������łȂ��ꍇ
				if (lecture_affiliation_number != student_affiliation_id) {
					// �u�`�����̂��̑��ɑ����Ă��Ȃ��ꍇ
					if (lecture_affiliation_number != 6) {
						continue; // ���̗v�f�ɏ������ڂ�
					}

					// ���K���ł���ꍇ
					if (lecture_type_number == 1) {
						// ���݃A�N�Z�X���Ă���w�Дԍ��̊w�������Ԏ�ł���ꍇ
						if (student_affiliation_id != 6) {
							is_seminar_mastered = true; // ���K�𗚏C���Ă���Ɛݒ�
						}
					// ��b���K�ł���ꍇ
					} else if (lecture_type_number == 3) {
						// ���݃A�N�Z�X���Ă���w�Дԍ��̊w������Ԏ�ł���ꍇ
						if (student_affiliation_id == 6) {
							is_seminar_mastered = true; // ���K�𗚏C���Ă���Ɛݒ�
						}
					}

					continue; // ���̗v�f�ɏ������ڂ�
				}

				affiliated_subject_mastered_lecture_count++; // �����w�Ȃ̗��C�u�`���̉��Z
				String chair_number = lecture_id.substring(2, 3); // �u���ԍ����i�[

				// ���݃A�N�Z�X���Ă��闚�C�ςݍu�`���o�ϊw�Ȃ̂��̂ł���ꍇ
				if (lecture_affiliation_number == 0) {
					// �u������b���_�ł���ꍇ
					if (chair_number.equals("0")) {
						// �}�N���o�ϊw�T�_�A�~�N���o�ϊw�T�_�A���͐����o�ϊw�T�_�ł���ꍇ
						if (lecture_type_number == 1 || lecture_type_number == 2 || lecture_type_number == 9) {
							required_subject_mastered_count++; // �K�C�Ȗڂ̗��C���̉��Z
						// �}�N���o�ϊw�A�~�N���o�ϊw�A���͐����o�ϊwI�ł���ꍇ
						} else if (lecture_type_number == 3 || lecture_type_number == 5 || lecture_type_number == 10) {
							elective_required_subject_mastered_count[0]++; // 1��ޖڂ̑I��K�C�Ȗڂ̗��C�������Z
						// �o�ϊw�̊�b�c�[���A���͌o�ϊw�j�ł���ꍇ
						} else if (lecture_type_number == 0 || lecture_type_number == 17) {
							elective_required_subject_mastered_count[1]++; // 2��ޖڂ̑I��K�C�Ȗڂ̗��C�������Z
						}
					// ���p�o�Ϙ_�ł���ꍇ
					} else if (chair_number.equals("1")) {
						// ���v�wI�A���͍����w���_I�ł���ꍇ
						if (lecture_type_number == 0 || lecture_type_number == 13) {
							elective_required_subject_mastered_count[1]++; // 2��ޖڂ̑I��K�C�Ȗڂ̗��C�������Z
						}
					// ����o�Ϙ_�ł���ꍇ
					} else if (chair_number.equals("2")) {
						// �o�ϐ���I�A���͎Љ��I�ł���ꍇ
						if (lecture_type_number == 0 || lecture_type_number == 16) {
							elective_required_subject_mastered_count[1]++; // 2��ޖڂ̑I��K�C�Ȗڂ̗��C�������Z
						}
					// ��r�o�Ϙ_�ł���ꍇ
					} else if (chair_number.equals("3")) {
						// �o�ώj�T�_�A���͍��یo�Ϙ_I�ł���ꍇ
						if (lecture_type_number == 0 || lecture_type_number == 9) {
							elective_required_subject_mastered_count[1]++; // 2��ޖڂ̑I��K�C�Ȗڂ̗��C�������Z
						}
					// ��b���������ł���ꍇ
					} else {
						elective_required_subject_mastered_count[1]++; // 2��ޖڂ̑I��K�C�Ȗڂ̗��C�������Z
					}
				// �t�@�C�i���X�w�Ȃ̂��̂ł���ꍇ
				} else if (lecture_affiliation_number == 1) {
					// �u�����t�@�C�i���X�v��ł���ꍇ
					if (chair_number.equals("0")) {
						// �t�@�C�i���X�T�_I�A����II�ł���ꍇ
						if (lecture_type_number < 2) {
							required_subject_mastered_count++; // �K�C�Ȗڂ̗��C���̉��Z
						// ��L�ȊO�̍u�`�ł���ꍇ
						} else {
							elective_required_subject_mastered_count[0]++; // 1��ޖڂ̑I��K�C�Ȗڂ̗��C�������Z
						}
					// �t�@�C�i���X�s��ł���ꍇ
					} else if (chair_number.equals("1")) {
						elective_required_subject_mastered_count[1]++; // 2��ޖڂ̑I��K�C�Ȗڂ̗��C�������Z
					// �t�@�C�i���X�E�V�X�e���ł���ꍇ
					} else if (chair_number.equals("2")) {
						elective_required_subject_mastered_count[2]++; // 3��ޖڂ̑I��K�C�Ȗڂ̗��C�������Z
					}
				// ��v���w�Ȃ̂��̂ŁA�u����������v�ł���A�u�`����LI�A���͕�LII�ł���ꍇ
				} else if (lecture_affiliation_number == 3 && chair_number.equals("0") && lecture_type_number <= 1) {
					required_subject_mastered_count++; // �K�C�Ȗڂ̗��C���̉��Z
				}
			}

			int[] necessary_credits = utility_bean.getNecessaryCredits(student_ids[i]); // �K�v�ȒP�ʂ��i�[

			// ���Ə����𖞂����Ă��邩�ǂ����̃`�F�b�N
			for (int j = 0; j < got_credits.length; j++) {
				// ���݃A�N�Z�X���Ă���Ȗڂɑ�����擾�P�ʂ��K�v�ȒP�ʂɒB���Ă��Ȃ��ꍇ
				if (got_credits[j] < necessary_credits[j]) {
					continue student_check; // ���̊w���v�f�ɏ������ڂ�
				}
			}

			// ���K�𗚏C���Ă��Ȃ��ꍇ
			if (!is_seminar_mastered) {
				continue; // ���̗v�f�ɏ������ڂ�
			}

			// �����w�Ȃ̗��C�u�`����10�����ł���ꍇ
			if (affiliated_subject_mastered_lecture_count < 10) {
				continue; // ���̗v�f�ɏ������ڂ�
			}

			// ���݃A�N�Z�X���Ă���w�Дԍ��̊w�����o�ϊw�Ȃɏ������Ă���ꍇ
			if (student_affiliation_id == 0) {
				// �K�C�ȖځE�I��K�C�Ȗڂ̏����𖞂����Ă��Ȃ��ꍇ
				if (required_subject_mastered_count < 3 || elective_required_subject_mastered_count[0] < 2 || elective_required_subject_mastered_count[1] < 5) {
					continue; // ���̗v�f�ɏ������ڂ�
				}
			// �t�@�C�i���X�w�Ȃł���ꍇ
			} else if (student_affiliation_id == 1) {
				// �K�C�ȖځE�I��K�C�Ȗڂ̏����𖞂����Ă��Ȃ��ꍇ
				if (required_subject_mastered_count < 2 || elective_required_subject_mastered_count[0] < 2 || elective_required_subject_mastered_count[1] < 2 || elective_required_subject_mastered_count[2] < 2) {
					continue; // ���̗v�f�ɏ������ڂ�
				}
			// ��v���w�ȂŁA�K�C�Ȗڂ̏����𖞂����Ă��Ȃ��ꍇ
			} else if (student_affiliation_id == 3 && required_subject_mastered_count < 2) {
				continue; // ���̗v�f�ɏ������ڂ�
			}

			database_bean.delete("student", student_ids[i]); // �f�[�^�̍폜
		}
	}

	String[] registered_opening_lecture_ids = database_bean.getColumnDataArray("registered_opening_lecture", 0); // �o�^�ς݊J�u�u�`ID���i�[

	// �o�^�ς݊J�u�u�`ID���擾�ł����ꍇ
	if (registered_opening_lecture_ids != null) {
		// �e�o�^�ς݊J�u�u�`�f�[�^�̍폜
		for (int i = 0; i < registered_opening_lecture_ids.length; i++) {
			database_bean.delete("registered_opening_lecture", registered_opening_lecture_ids[i]);
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�폜�̊���") %>
		�f�[�^�̍폜���������܂����B<br />
		<br />

		<a href="<%= utility_bean.getAdminMainPageURL() %>">�����̑I��</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">���O�A�E�g</a><br />

		<%= html_bean.getFooter() %>
