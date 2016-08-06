// �p�b�P�[�W�̐錾
package bean;

// Bean�̓���ɕK�v�ȃp�b�P�[�W�̃C���|�[�g
import java.io.Serializable;

// ���\�b�h���̏����ŗ��p����p�b�P�[�W�̃C���|�[�g
import bean.*;
import java.sql.SQLException;

/**
 * HTML�Ɋւ��鏈����񋟂���Bean
 * @author Ryo Fukushima
 */
public class HTMLBean implements Serializable {
	/** DatabaseBean���i�[ */
	private DatabaseBean databaseBean = null;

	/** UtilityBean���i�[ */
	private UtilityBean utilityBean = null;

	/** �R���X�g���N�^ */
	public HTMLBean() {
	}

	/**
	 * DatabaseBean��ݒ肷��
	 * @param database_bean DatabaseBean
	 */
	public void setDatabaseBean(DatabaseBean database_bean) {
		databaseBean = database_bean;
	}

	/**
	 * UtilityBean��ݒ肷��
	 * @param utility_bean UtilityBean
	 */
	public void setUtilityBean(UtilityBean utility_bean) {
		utilityBean = utility_bean;
	}

	/**
	 * �w�b�_��Ԃ�
	 * @param title �^�C�g��
	 * @return �w�b�_
	 */
	public synchronized String getHeader(String title) {
		StringBuffer header = new StringBuffer(); // �w�b�_���i�[

		// �w�b�_�̎擾
		header.append("<html>\n");
		header.append("\t<head>\n");
		header.append("\t\t<title>" + title + " <���C�o�^�V�X�e��></title>\n");
		header.append("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + utilityBean.getApplicationURL() + "style.css\">\n");
		header.append("\t\t<meta name=\"content-language\" content=\"ja\">\n");
		header.append("\t</head>\n");
		header.append("\n");
		header.append("\t<body>\n");
		header.append("\t\t<table border=\"0\" width=\"500\" cellspacing=\"0\" cellpadding=\"0\">\n");
		header.append("\t\t\t<tr>\n");
		header.append("\t\t\t\t<td class=\"title\" width=\"15\"></td>\n");
		header.append("\t\t\t\t<td><font size=\"3\" color=\"blue\"><b>" + title + "</b></font></td>\n");
		header.append("\t\t\t</tr>\n");
		header.append("\t\t\t<tr>\n");
		header.append("\t\t\t\t<td class=\"title\" colspan=\"2\" height=\"2\"></td>\n");
		header.append("\t\t\t</tr>\n");
		header.append("\t\t</table>\n");
		header.append("\t\t<br />\n");
		header.append("\t\t<br />\n");

		return header.toString(); // �w�b�_��Ԃ�
	}

	/**
	 * �t�b�^��Ԃ�
	 * @return �t�b�^
	 */
	public String getFooter() {
		StringBuffer footer = new StringBuffer(); // �t�b�^���i�[

		// �t�b�^�̎擾
		footer.append("<hr />\n");
		footer.append("\t\t<div align=\"right\">\n");
		footer.append("\t\t\tWritten by Ryo Fukushima<br />\n");
		footer.append("\t\t\t(00858, Faculty of Economics, Shiga University)<br />\n");
		footer.append("\t\t\t<br />\n");
		footer.append("\t\t\tInternet Explorer 4.0 or higher(Japanese and CSS(Cascading Style Sheet) supporting browser)<br />\n");
		footer.append("\t\t\t is recommended for viewing this Web page.<br />\n");
		footer.append("\t\t</div>\n");
		footer.append("\t</body>\n");
		footer.append("</html>\n");

		return footer.toString(); // �t�b�^��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�A�J�E���g�̃��O�C���t�H�[����Ԃ�
	 * @param account_name �A�J�E���g��
	 * @return ���O�C���t�H�[��
	 * @exception SQLException
	 */
	public synchronized String getLoginForm(String account_name) throws SQLException {
		String id_name = null; // ID�����i�[
		String main_page_url = null; // ���C���y�[�W��URL���i�[

		// �w�肳�ꂽ�A�J�E���g�����w���ł���ꍇ
		if(account_name.equals("student")) {
			id_name = "�w�Дԍ�"; // "�w�Дԍ�"��ID���ɐݒ�
			main_page_url = utilityBean.getMainPageURL(); // ���C���y�[�W��URL�̎擾
		// �w�肳�ꂽ�A�J�E���g�����Ǘ��҂ł���ꍇ
		} else if(account_name.equals("administrator")) {
			id_name = "�Ǘ���ID"; // "�Ǘ���ID"��ID���ɐݒ�
			main_page_url = utilityBean.getAdminMainPageURL(); // �Ǘ��p���C���y�[�W��URL�̎擾
		}

		StringBuffer login_form = new StringBuffer(); // ���O�C���t�H�[�����i�[

		// ���O�C���t�H�[���̎擾
		login_form.append("<form method=\"POST\" action=\"" + main_page_url + "\">\n");
		login_form.append("\t\t\t" + id_name + "�ƃp�X���[�h����͂��A[���M]�{�^�����N���b�N���ĉ������B<br>\n");
		login_form.append("\t\t\t<table border=\"0\">\n");
		login_form.append("\t\t\t\t<tr>\n");
		login_form.append("\t\t\t\t\t<th align=\"right\">"+ id_name + "</th>\n");
		login_form.append("\t\t\t\t\t<td><input type=\"text\" name=\"" + account_name + "_id\" size=\"10\" /></td>\n");
		login_form.append("\t\t\t\t</tr>\n");
		login_form.append("\t\t\t\t<tr>\n");
		login_form.append("\t\t\t\t\t<th align=\"right\">�p�X���[�h</th>\n");
		login_form.append("\t\t\t\t\t<td><input type=\"password\" name=\"password\" size=\"10\" /></td>\n");
		login_form.append("\t\t\t\t</tr>\n");
		login_form.append("\t\t\t\t<tr>\n");
		login_form.append("\t\t\t\t\t<td colspan=\"2\">\n");
		login_form.append("\t\t\t\t\t\t<input type=\"submit\" value=\"���M\" />\n");
		login_form.append("\t\t\t\t\t\t<input type=\"reset\" value=\"���\" />\n");
		login_form.append("\t\t\t\t\t</td>\n");
		login_form.append("\t\t\t\t</tr>\n");
		login_form.append("\t\t\t</table>\n");
		login_form.append("\t\t</form>\n");

		return login_form.toString(); // ���O�C���t�H�[����Ԃ�
	}

	/**
	 * �w�肳�ꂽ��ނ���J�u�u�`�ꗗ�\���쐬���A�����Ԃ�
	 * @param column_data ��f�[�^
	 * @param button_name �{�^����
	 * @return �J�u�u�`�ꗗ�\
	 * @exception SQLException
	 */
	public synchronized String getOpeningLectureList(String[][] column_data, String button_name) throws SQLException {
		final int TABLE_WIDTH = 1140; // �e�[�u�������i�[
		final int CLASS_OUTLINE_WIDTH = 10; // �������o�������i�[
		final int COLUMN_WIDTH = 225; // �񕝂��i�[
		StringBuffer opening_lecture_list = new StringBuffer(); // �J�u�u�`�ꗗ�\���i�[

		// �J�u�u�`�ꗗ�\�̎擾
		opening_lecture_list.append("<center>\n");
		opening_lecture_list.append("\t\t\t\t<table border=\"1\" width=\"" + TABLE_WIDTH + "\" cellpadding=\"0\" cellspacing=\"0\">\n");
		opening_lecture_list.append("\t\t\t\t\t<tr>\n");
		opening_lecture_list.append("\t\t\t\t\t\t<th width=\"" + CLASS_OUTLINE_WIDTH + "\"></th>\n");
		String[] day_names = {"��", "��", "��", "��", "��"}; // �j�������i�[
		for (int i = 0; i < day_names.length; i++) {
			opening_lecture_list.append("\t\t\t\t\t\t<th width=\"" + COLUMN_WIDTH + "\">" + day_names[i] + "�j��</th>\n");
		}
		opening_lecture_list.append("\t\t\t\t\t</tr>\n");
		for (int i = 0; i < column_data[0].length; i++) {
			opening_lecture_list.append("\t\t\t\t\t<tr>\n");
			opening_lecture_list.append("\t\t\t\t\t\t<th>" + (i + 1) + "������</th>\n");

			// ��f�[�^�̕\��
			for (int j = 0; j < day_names.length; j++) {
				opening_lecture_list.append("\t\t\t\t\t\t<td>\n");
				opening_lecture_list.append("\t\t\t\t\t\t\t<center>\n");
				opening_lecture_list.append("\t\t\t\t\t\t\t\t" + column_data[j][i] + "\n");
				opening_lecture_list.append("\t\t\t\t\t\t\t</center>\n");
				opening_lecture_list.append("\t\t\t\t\t\t</td>\n");
			}

			opening_lecture_list.append("\t\t\t\t\t</tr>\n");
		}
		opening_lecture_list.append("\t\t\t\t</table>\n");
		opening_lecture_list.append("\t\t\t\t<br>\n");
		opening_lecture_list.append("\t\t\t\t<table border=\"1\" width=\"" + TABLE_WIDTH + "\" cellpadding=\"0\" cellspacing=\"0\">\n");
		opening_lecture_list.append("\t\t\t\t\t<tr>\n");
		opening_lecture_list.append("\t\t\t\t\t\t<th width=\"" + CLASS_OUTLINE_WIDTH + "\">�W���u�`</th>\n");
		for (int i = 0; i < day_names.length; i++) {
			opening_lecture_list.append("\t\t\t\t\t\t<td width=\"" + COLUMN_WIDTH + "\">\n");
			opening_lecture_list.append("\t\t\t\t\t\t\t<center>\n");
			opening_lecture_list.append("\t\t\t\t\t\t\t\t" + column_data[5][i] + "\n");
			opening_lecture_list.append("\t\t\t\t\t\t\t</center>\n");
			opening_lecture_list.append("\t\t\t\t\t\t</td>\n");
		}
		opening_lecture_list.append("\t\t\t\t\t</tr>\n");
		opening_lecture_list.append("\t\t\t\t</table>\n");
		if (button_name != null) {
			opening_lecture_list.append("\t\t\t\t<table border=\"0\" width=\"" + TABLE_WIDTH + "\" cellpadding=\"0\" cellspacing=\"0\">\n");
			opening_lecture_list.append("\t\t\t\t\t<tr>\n");
			opening_lecture_list.append("\t\t\t\t\t\t<td><center><input type=\"submit\" value=\"" + button_name + "\" /></center></td>\n");
			opening_lecture_list.append("\t\t\t\t\t</tr>\n");
			opening_lecture_list.append("\t\t\t\t</table>\n");
		}
		opening_lecture_list.append("\t\t\t</center>");

		return opening_lecture_list.toString(); // �J�u�u�`�ꗗ�\��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�����̎�ށE�e�[�u�����EID����f�[�^�ꗗ�\���쐬���A�����Ԃ�
	 * @param processing_type �����̎��
	 * @param table_name �e�[�u����
	 * @param ids ID
	 * @param button_name �{�^���̖��O
	 * @return �f�[�^�ꗗ�\
	 * @exception SQLException
	 */
	public String getDataList(String processing_type, String table_name, String[] ids, String button_name) throws SQLException {
		String[] table_column_names = databaseBean.getColumnNames(table_name); // �e�[�u���̗񖼂��i�[
		String first_column_outline = utilityBean.getJapaneseWord(table_column_names[1]); // 1��ڂ̌��o�����i�[
		String destination_page_url = null; // ���Đ�y�[�W��URL���i�[
		StringBuffer data_list = new StringBuffer(); // �f�[�^�ꗗ�\���i�[

		// �w�肳�ꂽ�����̎�ނ�"delete"�ł���ꍇ
		if (processing_type.equals("delete")) {
			destination_page_url = "./delete.jsp"; // ���Đ�y�[�W��URL�̐ݒ�
		// "delete"�ȊO�̎�ނł���ꍇ
		} else {
			data_list.append(first_column_outline + "���N���b�N����ƁA�Y������");

			// �w�肳�ꂽ�����̎�ނ�"record"�ł���ꍇ
			if (processing_type.equals("record")) {
				data_list.append("�u�`�̐��ѕҏW�y�[�W�Ɉړ����܂��B<br />\n");
			// "edit"�ł���ꍇ
			} else {
				destination_page_url = "delete/confirm.jsp"; // ���Đ�y�[�W��URL�̐ݒ�
				String japanese_table_name = utilityBean.getJapaneseWord(table_name); // �e�[�u�����̓��{�ꖼ���i�[

				data_list.append(japanese_table_name + "�f�[�^�̕ҏW�y�[�W�Ɉړ����܂��B<br />\n");
				data_list.append("\t\t�폜�`�F�b�N�{�b�N�X�Ƀ`�F�b�N������[" + button_name + "]�{�^�����N���b�N����ƁA�w�肳�ꂽ" + japanese_table_name + "�f�[�^���폜����܂��B<br />\n");
			}

			data_list.append("\t\t<br />\n");
		}

		int tab_count; // �^�u�����i�[

		// ���Đ�y�[�W��URL���ݒ肳��Ă��Ȃ��ꍇ
		if (destination_page_url == null) {
			tab_count = 2; // 2���^�u���ɐݒ�
		// �ݒ肳��Ă���ꍇ
		} else {
			tab_count = 3; // 3���^�u���ɐݒ�

			// �w�肳�ꂽ�����̎�ނ�"edit"�ł���ꍇ
			if (processing_type.equals("edit")) {
				data_list.append("\t\t");
			}
			data_list.append("<form method=\"POST\" action=\"" + destination_page_url + "\">\n");
			data_list.append("\t\t\t<input type=\"hidden\" name=\"table_name\" value=\"" + table_name + "\" />\n");
		}

		int outline_output_count; // ���o���̏o�͉񐔂��i�[

		// ID����2�ȏ�ł���ꍇ
		if (ids.length > 1) {
			outline_output_count = 2; // ���o���̏o�͉񐔂�2�ɐݒ�
		// 1�ł���ꍇ
		} else {
			outline_output_count = 1; // ���o���̏o�͉񐔂�1�ɐݒ�
		}

		String second_column_outline = utilityBean.getJapaneseWord(table_column_names[0]); // 2�s�ڂ̌��o�����i�[

		// �f�[�^�ꗗ�\�̎擾
		data_list.append(getTab(tab_count) + "<table border=\"1\" bgcolor=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\">\n");
		data_list.append(getTab(tab_count + 1) + "<tr height=\"20\">\n");
		for (int i = 0; i < outline_output_count; i++) {
			data_list.append(getTab(tab_count + 2) + "<th>" + first_column_outline + "</th>\n");
			data_list.append(getTab(tab_count + 2) + "<th>" + second_column_outline + "</th>\n");

			// �w�肳�ꂽ�����̎�ނ�"edit"�ł���ꍇ
			if (processing_type.equals("edit")) {
				data_list.append("\t\t\t\t\t<th>�폜</th>\n");
			}
		}
		data_list.append("\t\t\t\t</tr>\n");
		int id_index = 0; // ID�̃C���f�b�N�X���i�[
		while (id_index < ids.length) {
			int data_output_count; // �f�[�^�̏o�͉񐔂��i�[

			// ID�̃C���f�b�N�X��ID�����2�ȏ㏬�����ꍇ
			if (id_index <= (ids.length - 2)) {
				data_output_count = 2; // �f�[�^�̏o�͉񐔂�2�ɐݒ�
			// 1�ȉ��ł���ꍇ
			} else {
				data_output_count = 1; // �f�[�^�̏o�͉񐔂�1�ɐݒ�
			}

			// �s�̎擾
			data_list.append(getTab(tab_count + 1) + "<tr height=\"25\">\n");
			for (int i = 0; i < data_output_count; i++) {
				String[] row_data = databaseBean.getRowData(table_name, ids[id_index]); // �s�f�[�^���i�[
				String first_column_data = row_data[1]; // 1��ڂ̃f�[�^���i�[

				// �w�肳�ꂽ�e�[�u������"opening_lecture"���܂܂�Ă���ꍇ
				if (table_name.indexOf("opening_lecture") != -1) {
					String first_column_data_table_name = null; // 1��ڂ̃f�[�^�̃e�[�u�������i�[

					// �w�肳�ꂽ�e�[�u������opening_lecture�ł���ꍇ
					if (table_name.equals("opening_lecture")) {
						first_column_data_table_name = "lecture"; // lecture��1��ڂ̃f�[�^�̃e�[�u�����ɐݒ�
					// opening_lecture_lecturer�ł���ꍇ
					} else {
						first_column_data_table_name = "lecturer"; // lecturer��1��ڂ̃f�[�^�̃e�[�u�����ɐݒ�
					}

					first_column_data += "(" + databaseBean.getColumnData(first_column_data_table_name, 1, row_data[1]) + ")"; // �f�[�^�̕t��
				}

				// �w�肳�ꂽ�����̎�ނ�"delete"�ł���ꍇ
				if (processing_type.equals("delete")) {
					// �����N�Ȃ��̗v�f�̎擾
					data_list.append("\t\t\t\t\t<input type=\"hidden\" name=\"id\" value=\"" + ids[id_index] + "\" />\n");
					data_list.append("\t\t\t\t\t<td>" + first_column_data + "</td>\n");
				// "delete"�ȊO�̎�ނł���ꍇ
				} else {
					String edit_page_url = null; // �ҏW�y�[�W��URL���i�[

					// �w�肳�ꂽ�����̎�ނ�"record"�ł���ꍇ
					if (processing_type.equals("record")) {
						edit_page_url = utilityBean.getRecordEditPageURL(ids[id_index]);
					// "edit"�ł���ꍇ
					} else {
						edit_page_url = utilityBean.getDataEditPageURL(table_name, ids[id_index], null);
					}

					data_list.append(getTab(tab_count + 2) + "<td><a href=\"" + edit_page_url + "\">" + first_column_data + "</a></td>\n"); // �����N�t���̗v�f�̎擾
				}

				data_list.append(getTab(tab_count + 2) + "<td>" + ids[id_index] + "</td>\n");

				// �w�肳�ꂽ�����̎�ނ�"edit"�ł���ꍇ
				if (processing_type.equals("edit")) {
					data_list.append("\t\t\t\t\t<td align=\"center\"><input type=\"checkbox\" name=\"id\" value=\"" + ids[id_index] + "\" /></td>\n"); // �폜�`�F�b�N�{�b�N�X�̎擾
				}

				id_index++; // ID�̃C���f�b�N�X�̉��Z
			}
			data_list.append(getTab(tab_count + 1) + "</tr>\n");
		}
		data_list.append(getTab(tab_count) + "</table>\n");
		if (button_name == null) {
			data_list.append("\t\t<br />\n");
		} else {
			data_list.append("\t\t\t<input type=\"submit\" value=\"" + button_name + "\" />\n");
			data_list.append("\t\t</form>");
		}

		return data_list.toString(); // �f�[�^�ꗗ�\��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�t�H�[���\���v�f�f�[�^�̃f�[�^�ҏW�e�[�u����Ԃ�
	 * @param form_component_names �t�H�[���\���v�f��
	 * @param form_component_contents �t�H�[���\���v�f�̓��e
	 * @param tab_count �^�u��
	 * @return �f�[�^�ҏW�e�[�u��
	 */
	public String getDataEditTable(String[] form_component_names, String[] form_component_contents, int tab_count) {
		StringBuffer data_edit_table = new StringBuffer(); // �f�[�^�ҏW�e�[�u�����i�[

		// �f�[�^�ҏW�e�[�u���̎擾
		data_edit_table.append("<table border=\"1\" bgcolor=\"white\" cellpadding=\"0\" cellspacing=\"0\">\n");
		for (int i = 0; i < form_component_names.length; i++) {
			data_edit_table.append(getTab(tab_count + 1) + "<tr>\n");
			data_edit_table.append(getTab(tab_count + 2) + "<th width=\"200\" height=\"25\" align=\"right\">" + utilityBean.getJapaneseWord(form_component_names[i]) + "</th>\n");
			data_edit_table.append(getTab(tab_count + 2) + "<td width=\"200\">\n");
			data_edit_table.append(getTab(tab_count + 3) + form_component_contents[i] + "\n");
			data_edit_table.append(getTab(tab_count + 2) + "</td>\n");
			data_edit_table.append(getTab(tab_count + 1) + "</tr>\n");
		}
		data_edit_table.append(getTab(tab_count) + "</table>\n");

		return data_edit_table.toString(); // �f�[�^�ҏW�e�[�u����Ԃ�
	}

	/**
	 * �w�肳�ꂽ��ށE�w�Дԍ��E�_������J�u�u�`�̓_�����X�g���쐬���A�����Ԃ�
	 * @param type ���
	 * @param student_ids �w�Дԍ�
	 * @param scores �_��
	 * @return �J�u�u�`�̓_�����X�g
	 * @exception SQLException
	 */
	public String getOpeningLectureScoreList(String type, String[] student_ids, String[] scores) throws SQLException {
		String[] from_100_to_0_array = utilityBean.getArray(100, 0); // 100����0�̔z����i�[
		String[] score_select_box_ids = new String[from_100_to_0_array.length + 1]; // �_���I�𗓂�ID���i�[
		String[] score_select_box_outlines = new String[score_select_box_ids.length]; // �_���I�𗓂̌��o�����i�[

		// �_���I�𗓃f�[�^��1�ڂ̗v�f�̐ݒ�
		score_select_box_ids[0] = "";
		score_select_box_outlines[0] = "(���o�^)";

		// �_���I�𗓃f�[�^��2�ڈȍ~�̗v�f�̐ݒ�
		for (int i = 1; i < score_select_box_ids.length; i++) {
			int from_100_to_0_array_index = i - 1; // 100����0�̔z��̃C���f�b�N�X���i�[

			// �_���I�𗓃f�[�^�̗v�f�̐ݒ�
			score_select_box_ids[i] = from_100_to_0_array[from_100_to_0_array_index];
			score_select_box_outlines[i] = from_100_to_0_array[from_100_to_0_array_index];
		}

		final String[] SCORE_COLOR_NAMES = {"red", "purple", "green", "blue"}; // �_���̐F�̎�ނ��i�[
		StringBuffer opening_lecture_score_list = new StringBuffer(); // �J�u�u�`�̓_�����X�g���i�[

		// �J�u�u�`�̓_�����X�g�̎擾
		opening_lecture_score_list.append("<table border=\"1\" bgcolor=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\">\n");
		opening_lecture_score_list.append("\t\t\t\t<tr height=\"20\">\n");
		opening_lecture_score_list.append("\t\t\t\t\t<th>�w�Дԍ�</th>\n");
		opening_lecture_score_list.append("\t\t\t\t\t<th width=\"100\">����</th>\n");
		opening_lecture_score_list.append("\t\t\t\t\t<th width=\"80\">�_��</th>\n");
		opening_lecture_score_list.append("\t\t\t\t</tr>\n");
		for (int i = 0; i < student_ids.length; i++) {
			opening_lecture_score_list.append("\t\t\t\t<tr height=\"25\">\n");
			opening_lecture_score_list.append("\t\t\t\t\t<td>" + student_ids[i] + "</td>\n");
			opening_lecture_score_list.append("\t\t\t\t\t<td>" + databaseBean.getColumnData("student", 1, student_ids[i]) + "</td>\n");
			opening_lecture_score_list.append("\t\t\t\t\t<td align=\"center\">");

			// �w�肳�ꂽ��ނ�"edit"�ł���ꍇ
			if (type.equals("edit")) {
				opening_lecture_score_list.append(getSelectBox(student_ids[i], scores[i], score_select_box_ids, score_select_box_outlines, 5)); // �_���̑I�𗓂̕t��
			// "edit"�łȂ��ꍇ
			} else {
				int grade = (Integer.parseInt(scores[i]) - 50) / 10; // �������i�[

				// �擾����������0�����ł���ꍇ
				if (grade < 0) {
					grade = 0; // 0�𓙋��ɐݒ�
				// 3�𒴂��Ă���ꍇ
				} else if (grade > 3) {
					grade = 3; // 3�𓙋��ɐݒ�
				}

				// �擾����������0�ȊO�ł���ꍇ
				if (grade != 0) {
					opening_lecture_score_list.append("<input type=\"hidden\" name=\"" + student_ids[i] + "\" value=\"" + scores[i] + "\" />"); // hidden�\���v�f�̕t��
				}

				opening_lecture_score_list.append("<font color=\"" + SCORE_COLOR_NAMES[grade] + "\">" + scores[i] + "(" + utilityBean.getEvaluation(scores[i]) + ")</font>"); // �_���̕t��
			}

			opening_lecture_score_list.append("</td>\n");
			opening_lecture_score_list.append("\t\t\t\t</tr>\n");
		}
		opening_lecture_score_list.append("\t\t\t</table>\n");

		return opening_lecture_score_list.toString(); // �J�u�u�`�̓_�����X�g��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�f�[�^����Z���N�g�{�b�N�X���쐬���A�����Ԃ�
	 * @param name ���O
	 * @param id ID
	 * @param option_ids �I�v�V������ID
	 * @param tab_count �^�u��
	 * @return �Z���N�g�{�b�N�X
	 */
	public String getSelectBox(String name, String id, String[] list_ids, int tab_count) {
		return getSelectBox(name, id, list_ids, null, true, tab_count);
	}

	/**
	 * �w�肳�ꂽ�f�[�^����Z���N�g�{�b�N�X���쐬���A�����Ԃ�
	 * @param name ���O
	 * @param id ID
	 * @param option_ids �I�v�V������ID
	 * @param option_outlines �I�v�V�����̌��o��
	 * @param tab_count �^�u��
	 * @return �Z���N�g�{�b�N�X
	 */
	public synchronized String getSelectBox(String name, String id, String[] list_ids, String[] list_outlines, int tab_count) {
		return getSelectBox(name, id, list_ids, list_outlines, false, tab_count);
	}

	/**
	 * �w�肳�ꂽ�f�[�^����Z���N�g�{�b�N�X���쐬���A�����Ԃ�
	 * @param name ���O
	 * @param id ID
	 * @param option_ids �I�v�V������ID
	 * @param option_outlines �I�v�V�����̌��o��
	 * @param is_id_displayed ID��\�����邩�ǂ����̐ݒ�
	 * @param tab_count �^�u��
	 * @return �Z���N�g�{�b�N�X
	 */
	public synchronized String getSelectBox(String name, String id, String[] option_ids, String[] option_outlines, boolean is_id_displayed, int tab_count) {
		StringBuffer select_box = new StringBuffer(); // �Z���N�g�{�b�N�X���i�[

		// �Z���N�g�{�b�N�X�̎擾
		select_box.append("<select name=\"" + name + "\">\n");
		if (option_ids != null) {
			for (int i = 0; i < option_ids.length; i++) {
				select_box.append(getTab(tab_count + 1) + "<option value=\"" + option_ids[i] + "\"");

				// �w�肳�ꂽID��null�ł͂Ȃ��A���݂̃I�v�V����ID�̗v�f�Ɠ������ꍇ
				if (id != null && id.equals(option_ids[i])) {
					select_box.append(" selected"); // "selected"�̕t��
				}

				select_box.append(">");

				// ID��\������悤�ɐݒ肳��Ă���ꍇ
				if (is_id_displayed) {
					select_box.append(option_ids[i]);
				}

				// �I�v�V�����̌��o����null�ł͂Ȃ��ꍇ
				if (option_outlines != null) {
					// ID��\������悤�ɐݒ肳��Ă���ꍇ
					if (is_id_displayed) {
						select_box.append("(" + option_outlines[i] + ")"); // ���ʂ��������o���̎擾
					// �ݒ肳��Ă��Ȃ��ꍇ
					} else {
						select_box.append(option_outlines[i]); // ���o���̎擾
					}
				}

				select_box.append("</option>\n");
			}
		}
		select_box.append(getTab(tab_count) + "</select>");

		return select_box.toString(); // �Z���N�g�{�b�N�X��Ԃ�
	}

	/**
	 * �w�肳�ꂽ���̃^�u�L�[��Ԃ�
	 * @param tab_count �^�u�̐�
	 * @return �^�u�L�[
	 */
	public String getTab(int tab_count) {
		StringBuffer tab = new StringBuffer(); // �^�u�L�[���i�[

		// �w�肳�ꂽ���̃^�u�L�[�̕t��
		for(int i = 0; i < tab_count; i++) {
			tab.append("\t");
		}

		return tab.toString(); // �^�u�L�[��Ԃ�
	}
}
