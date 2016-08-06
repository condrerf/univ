<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String student_id = request.getParameter("student_id"); // �w�肳�ꂽ�w�Дԍ����i�[
	String[][] mastered_lecture_data = database_bean.getRowsData("mastered_lecture", student_id); // ���C�ςݍu�`�f�[�^���i�[
	StringBuffer mastered_lecture_condition = new StringBuffer(); // ���C�ςݍu�`�̏󋵂��i�[
	int[] lecture_sums = new int[5]; // �u�`�̍��v���i�[
	int[] credit_sums = new int[5]; // �P�ʂ̍��v���i�[

	// ���C�ςݍu�`�f�[�^���擾�ł��Ȃ������ꍇ
	if (mastered_lecture_data == null) {
		mastered_lecture_condition.append("�@���C�ς݂̍u�`�͂���܂���<br />\n"); // ���b�Z�[�W�̕t��
	// �擾�ł����ꍇ
	} else {
		StringBuffer[] mastered_lecture_tables = new StringBuffer[2]; // ���C�ςݍu�`�e�[�u�����i�[
		int last_mastered_lecture_data_index = (mastered_lecture_data.length - 1); // ���C�ςݍu�`�f�[�^�̏I�[�̃C���f�b�N�X���i�[
		int[] last_mastered_lecture_data_indexes = {last_mastered_lecture_data_index / 2, last_mastered_lecture_data_index}; // ���C�ςݍu�`�f�[�^�̏I�[�̃C���f�b�N�X���i�[
		int mastered_lecture_data_index = 0; // ���C�ςݍu�`�f�[�^�̃C���f�b�N�X���i�[

		// �J�u�u�`�e�[�u���̎擾
		for (int i = 0; i < mastered_lecture_tables.length; i++) {
			mastered_lecture_tables[i] = new StringBuffer(); // ���݃A�N�Z�X���Ă���J�u�u�`�e�[�u���̏�����

			// ���C�ςݍu�`�f�[�^�̃C���f�b�N�X�����݃A�N�Z�X���Ă��闚�C�ςݍu�`�f�[�^�̏I�[�̃C���f�b�N�X���z���Ă��Ȃ��ꍇ
			if (mastered_lecture_data_index <= last_mastered_lecture_data_indexes[i]) {
				// ���o���̎擾
				mastered_lecture_tables[i].append("\t\t\t\t\t<table border=\"1\" bgcolor=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\">\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t<tr>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th width=\"200\">�u�`��</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th>�P��</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th width=\"50\" colspan=\"2\">�]��</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th>�N�x</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t</tr>\n");

				// ���C�󋵂̎擾
				while (mastered_lecture_data_index <= last_mastered_lecture_data_indexes[i]) {
					String lecture_id = mastered_lecture_data[mastered_lecture_data_index][0].substring(5); // ���C�ςݍu�`ID�Ɋ܂܂��u�`ID���i�[
					String score = null; // �_�����i�[

					// ���C�ςݍu�`�f�[�^�̓_������null�ł���ꍇ
					if (mastered_lecture_data[mastered_lecture_data_index][1] == null) {
						score = "�@"; // �_�����󔒂ɐݒ�
					// null�łȂ��ꍇ
					} else {
						score = mastered_lecture_data[mastered_lecture_data_index][1]; // �_���̎擾
					}

					// ���C�󋵂̎擾
					mastered_lecture_tables[i].append("\t\t\t\t\t\t<tr height=\"20\">\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td>" + database_bean.getColumnData("lecture", 1, lecture_id) + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"right\">" + database_bean.getColumnData("lecture", 2, lecture_id) + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"right\">" + score + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"center\">" + utility_bean.getEvaluation(mastered_lecture_data[mastered_lecture_data_index][1]) + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"right\">" + mastered_lecture_data[mastered_lecture_data_index][2] + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t</tr>\n");

					int course_id = Integer.parseInt(mastered_lecture_data[mastered_lecture_data_index][0].substring(5, 6)); // �Ȗ�ID���i�[
					int credit = Integer.parseInt(database_bean.getColumnData("lecture", 2, mastered_lecture_data[mastered_lecture_data_index][0].substring(5))); // �P�ʐ����i�[

					// �u�`���ƒP�ʂ̉��Z
					lecture_sums[course_id]++;
					credit_sums[course_id] += credit;
					lecture_sums[4]++;
					credit_sums[4] += credit;

					mastered_lecture_data_index++; // ���C�ςݍu�`�f�[�^�̃C���f�b�N�X�̉��Z
				}
			}
			mastered_lecture_tables[i].append("\t\t\t\t\t</table>\n");
		}

		// ���C�ςݍu�`�̏󋵂̎擾
		mastered_lecture_condition.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		mastered_lecture_condition.append("\t\t\t<tr valign=\"top\">\n");
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append(mastered_lecture_tables[0]);
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append("\t\t\t\t<td width=\"20\">�@</td>\n");
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append(mastered_lecture_tables[1]);
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append("\t\t\t</tr>\n");
		mastered_lecture_condition.append("\t\t</table>");
	}

	int[] necessary_credits = utility_bean.getNecessaryCredits(student_id); // �K�v�ȒP�ʂ��i�[
	String[] outlines = {"���Ȗ�", null, "�O����Ȗ�", "����O�̒P��", "���Ə��v�P��"}; // ���o�����i�[

	// �w�肳�ꂽ�w�������w�����N��2002�N�ȍ~�ł���ꍇ
	if (utility_bean.getEnteredYear(student_id) >= 2002) {
		outlines[1] = "�S�w���ʉȖ�"; // "�S�w"�����ʉȖڂ̖��O�ɐݒ�
	// 2002�N�ȑO�ł���ꍇ
	} else {
		outlines[1] = "�w�����ʉȖ�"; // "�w�����ʉȖ�"�����ʉȖڂ̖��O�ɐݒ�
	}

	StringBuffer got_credit_condition = new StringBuffer(); // �擾�����P�ʂ̏󋵂��i�[

	// �擾�����P�ʂ̏󋵂̎擾
	for (int i = 0; i < outlines.length; i++) {
		// 2�ڈȍ~�̃f�[�^�ł���ꍇ
		if (i > 0) {
			got_credit_condition.append("\t\t\t");
		}

		got_credit_condition.append("<tr height=\"20\">\n");
		got_credit_condition.append("\t\t\t\t<th align=\"left\">" + outlines[i] + "</td>\n");
		got_credit_condition.append("\t\t\t\t<td>�@</td>\n");
		got_credit_condition.append("\t\t\t\t<td align=\"right\">" + necessary_credits[i] + "</td>\n");
		got_credit_condition.append("\t\t\t\t<td align=\"right\">" + lecture_sums[i] + "</td>\n");
		got_credit_condition.append("\t\t\t\t<td align=\"right\">" + credit_sums[i] + "</td>\n");
		got_credit_condition.append("\t\t\t</tr>");

		// �ŏI�v�f�ȊO�̃f�[�^�ł���ꍇ
		if (i < outlines.length - 1) {
			got_credit_condition.append("\n");
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("����܂ł̗��C��") %>
		(�u�`�̗��C��)<br />
		<%= mastered_lecture_condition %>
		<br />

		(�P�ʂ̎擾��)<br />
		<table border="1" bgcolor="#ffffff" cellpadding="0" cellspacing="0">
			<tr>
				<th rowspan="2">����n��</th>
				<th colspan="2">���Ɨv��</th>
				<th colspan="2">�C����</th>
			</tr>
			<tr>
				<th>�Ȗ�</th>
				<th>�P��</th>
				<th>�Ȗ�</th>
				<th>�P��</th>
			</tr>
			<%= got_credit_condition %>
		</table>
		<br />

		<a href="<%= utility_bean.getMainPageURL(student_id) %>">���C�u�`�̑I��</a><br />

		<%= html_bean.getFooter() %>
