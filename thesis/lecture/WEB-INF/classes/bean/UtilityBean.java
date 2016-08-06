// �p�b�P�[�W�̐錾
package bean;

// Bean�̓���ɕK�v�ȃp�b�P�[�W�̃C���|�[�g
import java.io.Serializable;

// ���\�b�h���̏����ŗ��p����p�b�P�[�W�̃C���|�[�g
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * �f�[�^�x�[�X�y��HTML�Ɋ֌W���Ȃ�������񋟂���Bean
 * @author Ryo Fukushima
 */
public class UtilityBean implements Serializable {
	/** �w�������ԍ����i�[ */
	public final int[] STUDENT_AFFILIATION_NUMBERS = {1, 4, 5, 7, 8, 9, 0};

	/** ���O�C���y�[�W�����i�[ */
	private final String LOGIN_PAGE_NAME = "index.jsp";

	/** ���C���y�[�W�����i�[ */
	private final String MAIN_PAGE_NAME = "main.jsp";

	/** ���O�A�E�g�y�[�W�����i�[ */
	private final String LOGOUT_PAGE_NAME = "logout.jsp";

	/** DatabaseBean���i�[ */
	private DatabaseBean databaseBean = null;

	/** �����e�i���X���s���Ă���Ǘ���ID���i�[ */
	private String maintainingAdministratorID = null;

	/** ���C�o�^���s���邩�ǂ������i�[ */
	private boolean isRegisterable = false;

	/** �A�v���P�[�V������URL���i�[ */
	private String applicationURL = null;

	/** �R���X�g���N�^ */
	public UtilityBean() {
	}

	/**
	 * DatabaseBean��ݒ肷��
	 * @param database_bean DatabaseBean
	 */
	public void setDatabaseBean(DatabaseBean database_bean) {
		databaseBean = database_bean;
	}

	/**
	 * �����e�i���X���s���Ă���Ǘ���ID��ݒ肷��
	 * @param maintaining_administrator_id �Ǘ���ID
	 */
	public void setMaintainingAdministratorID(String maintaining_administrator_id) {
		maintainingAdministratorID = maintaining_administrator_id;
	}

	/**
	 * �����e�i���X���s���Ă���Ǘ���ID��Ԃ�
	 * @return �Ǘ���ID
	 */
	public String getMaintainingAdministratorID() {
		return maintainingAdministratorID;
	}

	/** �����e�i���X���s���Ă���Ǘ���ID��������� */
	public void releaseMaintainingAdministratorID() {
		maintainingAdministratorID = null;
	}

	/**
	 * �����e�i���X���ł��邩�ǂ�����Ԃ�
	 * @return true����false
	 */
	public boolean isMaintained() {
		return (maintainingAdministratorID != null ? true : false);
	}

	/**
	 * ���C�o�^���s���邩�ǂ�����ݒ肷��
	 * @return true����false
	 */
	public void setIsRegisterable(boolean is_registerable) {
		isRegisterable = is_registerable;
	}

	/**
	 * ���C�o�^���s���邩�ǂ�����Ԃ�
	 * @return true����false
	 */
	public boolean isRegisterable() {
		return isRegisterable;
	}

	/**
	 * �A�v���P�[�V������URL��ݒ肷��
	 * @param request �v�����
	 */
	public void setApplicationURL(HttpServletRequest request) {
		applicationURL = Pattern.compile("/").split(request.getProtocol())[0].toLowerCase() + "://" + request.getServerName() + request.getContextPath() + "/";
	}

	/**
	 * �A�v���P�[�V������URL��Ԃ�
	 * @return �A�v���P�[�V������URL
	 */
	public String getApplicationURL() {
		return applicationURL;
	}

	/**
	 * ���O�C���y�[�W��URL��Ԃ�
	 * @return ���O�C���y�[�W��URL
	 */
	public String getLoginPageURL() {
		return applicationURL + LOGIN_PAGE_NAME;
	}

	/**
	 * ���C���y�[�W��URL��Ԃ�
	 * @return ���C���y�[�W��URL
	 */
	public String getMainPageURL() {
		return applicationURL + MAIN_PAGE_NAME;
	}

	/**
	 * ���C���y�[�W��URL��Ԃ�
	 * @param student_id �w�Дԍ�
	 * @return ���C���y�[�W��URL
	 */
	public synchronized String getMainPageURL(String student_id) {
		return getMainPageURL() + "?student_id=" + student_id;
	}

	/**
	 * ���O�A�E�g�y�[�W��URL��Ԃ�
	 * @return ���O�A�E�g�y�[�W��URL
	 */
	public String getLogoutPageURL() {
		return applicationURL + LOGOUT_PAGE_NAME;
	}

	/**
	 * �p�X���[�h�ύX�葱���y�[�W��URL��Ԃ�
	 * @return �p�X���[�h�ύX�葱���y�[�W��URL
	 */
	public String getPasswordChangeProcedurePageURL() {
		return applicationURL + "password/index.jsp";
	}

	/**
	 * �Ǘ��f�B���N�g����URL��Ԃ�
	 * @return �Ǘ��f�B���N�g����URL
	 */
	public String getAdminDirectoryURL() {
		return getApplicationURL() + "admin/";
	}

	/**
	 * �Ǘ��p���O�C���y�[�W��URL��Ԃ�
	 * @return �Ǘ��p���O�C���y�[�W��URL
	 */
	public String getAdminLoginPageURL() {
		return getAdminDirectoryURL() + LOGIN_PAGE_NAME;
	}

	/**
	 * �Ǘ��p���C���y�[�W��URL��Ԃ�
	 * @return �Ǘ��p���C���y�[�W��URL
	 */
	public String getAdminMainPageURL() {
		return getAdminDirectoryURL() + MAIN_PAGE_NAME;
	}

	/**
	 * �Ǘ��p���O�A�E�g�y�[�W��URL��Ԃ�
	 * @return �Ǘ��p���O�A�E�g�y�[�W��URL
	 */
	public String getAdminLogoutPageURL() {
		return getAdminDirectoryURL() + LOGOUT_PAGE_NAME;
	}

	/**
	 * �e�[�u���I���y�[�W��URL��Ԃ�
	 * @return �e�[�u���I���y�[�W��URL
	 */
	public String getTableSelectPageURL() {
		return getAdminDirectoryURL() + "table_select.jsp";
	}

	/**
	 * �f�[�^�I���y�[�W��URL��Ԃ�
	 * @return �f�[�^�I���y�[�W��URL
	 */
	public String getDataSelectPageURL() {
		return getAdminDirectoryURL() + "data_select.jsp";
	}

	/**
	 * �w�肳�ꂽ�e�[�u�����̃f�[�^�I���y�[�W��URL��Ԃ�
	 * @param table_name �e�[�u����
	 * @return �f�[�^�I���y�[�W��URL
	 * @exception SQLException
	 */
	public String getDataSelectPageURL(String table_name) throws SQLException {
		return getDataSelectPageURL() + "?table_name=" + table_name;
	}

	/**
	 * �w�肳�ꂽ�e�[�u�����E�f�[�^�����ԍ�����f�[�^�I���y�[�W��URL���쐬���A�����Ԃ�
	 * @param table_name �e�[�u����
	 * @param data_affiliation_number �f�[�^�����ԍ�
	 * @return �f�[�^�I���y�[�W��URL
	 * @exception SQLException
	 */
	public String getDataSelectPageURL(String table_name, String data_affiliation_number) throws SQLException {
		StringBuffer admin_data_select_page_url = new StringBuffer(getDataSelectPageURL(table_name)); // �f�[�^�I���y�[�W��URL���i�[

		// �w�肳�ꂽ�f�[�^�����ԍ���null�łȂ��A�w�肳�ꂽ�f�[�^�����ԍ��̃f�[�^�����݂���ꍇ
		if (data_affiliation_number != null && databaseBean.getRowsData(table_name, data_affiliation_number) != null) {
			admin_data_select_page_url.append("&" + getDataAffiliationNumberName(table_name) + "=" + data_affiliation_number); // �p�����[�^�̕t��
		}

		return admin_data_select_page_url.toString(); // �f�[�^�I���y�[�W��URL��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�J�u�u�`ID�̍u�`�Ɠ��������ɊJ�u�����J�u�u�`�f�[�^�̑I���y�[�W�ւ�URL��Ԃ�
	 * @param opening_lecture_id �J�u�u�`ID
	 * @return �J�u�u�`�f�[�^�I���y�[�W��URL
	 */
	public String getOpeningLectureDataSelectPageURL(String opening_lecture_id) {
		return getDataSelectPageURL() + "?processing_type=record&" + getDataAffiliationNumberName("opening_lecture") + "=" + getDataAffiliationNumber("opening_lecture", opening_lecture_id);
	}

	/**
	 * �w�肳�ꂽ�e�[�u�����EID�E�f�[�^�����ԍ�����Ǘ��f�[�^�ҏW�y�[�W��URL���쐬���A�����Ԃ�
	 * @param table_name �e�[�u����
	 * @param id ID
	 * @param data_affiliation_number �f�[�^�����ԍ�
	 * @return �Ǘ��f�[�^�ҏW�y�[�W��URL
	 */
	public String getDataEditPageURL(String table_name, String id, String data_affiliation_number) {
		StringBuffer admin_data_edit_page_url = new StringBuffer(getAdminDirectoryURL() + "edit/index.jsp?table_name=" + table_name); // �Ǘ��f�[�^�ҏW�y�[�W��URL���i�[

		// ID���w�肳��Ă���ꍇ
		if (id != null) {
			admin_data_edit_page_url.append("&id=" + id); // ID�̕t��
		// �f�[�^�����ԍ����w�肳��Ă���ꍇ
		} else if (data_affiliation_number != null) {
			admin_data_edit_page_url.append("&data_affiliation_number=" + data_affiliation_number); // �f�[�^�����ԍ��̕t��
		}

		return admin_data_edit_page_url.toString(); // �Ǘ��f�[�^�ҏW�y�[�W��URL��Ԃ�
	}

	/**
	 * ���ѕҏW�y�[�W��URL��Ԃ�
	 * @param id ID
	 * @return ���ѕҏW�y�[�W��URL
	 */
	public String getRecordEditPageURL(String id) {
		return getAdminDirectoryURL() + "record/index.jsp?id=" + id;
	}

	/**
	 * �w�肳�ꂽ��ނ̃G���[�y�[�W��URI��Ԃ�
	 * @param error_type �G���[�̎��
	 * @return �G���[�y�[�W��URI
	 * @exception UnsupportedEncodingException
	 */
	public synchronized String getErrorPageURI(String error_type) throws UnsupportedEncodingException {
		return getErrorPageURI(error_type, null, null); // �p�����[�^��null�Ɏw�肵�����s���ʂ�Ԃ�
	}

	/**
	 * �w�肳�ꂽ��ނ̃G���[�y�[�W��URI��Ԃ�
	 * @param error_type �G���[�̎��
	 * @param error_parameter_name �G���[�p�����[�^�̖��O
	 * @return �G���[�y�[�W��URI
	 * @exception UnsupportedEncodingException
	 */
	public synchronized String getErrorPageURI(String error_type, String error_parameter_name) throws UnsupportedEncodingException {
		return getErrorPageURI(error_type, error_parameter_name, null); // �p�����[�^�l��null�Ɏw�肵�����s���ʂ�Ԃ�
	}

	/**
	 * �w�肳�ꂽ��ނ̃G���[�y�[�W��URI��Ԃ�
	 * @param error_type �G���[�̎��
	 * @param error_parameter_name �G���[�p�����[�^�̖��O
	 * @param error_parameter_value �G���[�p�����[�^�̒l
	 * @return �G���[�y�[�W��URI
	 * @exception UnsupportedEncodingException
	 */
	public synchronized String getErrorPageURI(String error_type, String error_parameter_name, int error_parameter_value) throws UnsupportedEncodingException {
		return getErrorPageURI(error_type, error_parameter_name, "" + error_parameter_value); // �p�����[�^�l�𕶎���^�ɕύX�������s���ʂ�Ԃ�
	}

	/**
	 * �w�肳�ꂽ��ނ̃G���[�y�[�W��URI��Ԃ�
	 * @param error_type �G���[�̎��
	 * @param error_error_parameter_name �G���[�p�����[�^�̖��O
	 * @param error_parameter_value �G���[�p�����[�^�̒l
	 * @return �G���[�y�[�W��URI
	 * @exception UnsupportedEncodingException
	 */
	public synchronized String getErrorPageURI(String error_type, String error_parameter_name, String error_parameter_value) throws UnsupportedEncodingException {
		StringBuffer error_page_url = new StringBuffer("/error.jsp?error_type=" + error_type); // �G���[�y�[�W��URL���i�[

		// �p�����[�^�����ݒ肳��Ă���ꍇ
		if (error_parameter_name != null) {
			error_page_url.append("&error_parameter_name=" + URLEncoder.encode(error_parameter_name, "Shift_JIS")); // �p�����[�^���̕t��
		}

		// �p�����[�^�l���ݒ肳��Ă���ꍇ
		if (error_parameter_value != null) {
			error_page_url.append("&error_parameter_value=" + URLEncoder.encode(error_parameter_value, "Shift_JIS")); // �p�����[�^�l�̕t��
		}

		return error_page_url.toString(); // �G���[�y�[�W��URL��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�w�Дԍ��Ɋ܂܂��S�p�����𔼊p�ɕϊ����ĕԂ�
	 * @param student_id �w�Дԍ�
	 * @return ���p����
	 */
	public synchronized String getFullDigitAlteredStudentID(String student_id) {
		final String[] FULL_DIGITS = {"�O", "�P", "�Q", "�R", "�S", "�T", "�U", "�V", "�W", "�X"}; // �S�p�������i�[

		// �S�p�����𔼊p�ɕϊ�
		for (int i = 0; i < FULL_DIGITS.length; i++) {
			student_id = Pattern.compile(FULL_DIGITS[i]).matcher(student_id).replaceAll("" + i);
		}

		return student_id; // �w�Дԍ���Ԃ�
	}

	/**
	 * �w�肳�ꂽ�����ɕ������܂܂�Ă��邩�ǂ�����Ԃ�
	 * @param number ����
	 * @return true����false
	 */
	public synchronized boolean isCharacterIncluded(String number) {
		// ������1�������`�F�b�N
		for (int i = 0; i < number.length(); i++) {
			// ���݃`�F�b�N���Ă��镶���������łȂ��ꍇ
			if(!Character.isDigit(number.charAt(i))) {
				return true; // �܂܂�Ă���ƕԂ�
			}
		}

		return false; // �܂܂�Ă��Ȃ��ƕԂ�
	}

	/**
	 * �w�肳�ꂽ�w�Дԍ����`�F�b�N���A�G���[������ꍇ�͓]������
	 * @param student_id �w�Дԍ�
	 * @param request �v�����
	 * @param response �������
	 * @exception IOException
	 * @exception ServletException
	 * @exception UnsupportedEncodingException
	 */
	public synchronized void checkStudentID(String student_id, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, UnsupportedEncodingException {
		ServletContext application = request.getSession().getServletContext(); // �A�v���P�[�V���������i�[

		// �w�Дԍ������͂���Ă��Ȃ��ꍇ
		if (student_id.equals("")) {
			application.getRequestDispatcher(getErrorPageURI("no_character_inputed_error", "�w�Дԍ�")).forward(request, response); // �G���[�y�[�W�ւ̓]��
		}

		// �w�Дԍ���5���œ��͂���Ă��Ȃ��ꍇ
		if (student_id.length() != 5) {
			application.getRequestDispatcher(getErrorPageURI("length_error", "�w�Дԍ�", student_id)).forward(request, response); // �G���[�y�[�W�ւ̓]��
		}

		// �w�Дԍ��ɕ������܂܂�Ă���ꍇ
		if(isCharacterIncluded(student_id)) {
			application.getRequestDispatcher(getErrorPageURI("character_included_error", "�w�Дԍ�", student_id)).forward(request, response); // �G���[�y�[�W�ւ̓]��
		}
	}

	/**
	 * �w�肳�ꂽ�w�Дԍ��̊w�������w�����N��Ԃ�
	 * @param student_id �w�Дԍ�
	 * @return ���w�����N
	 */
	public synchronized int getEnteredYear(String student_id) {
		int present_year = Calendar.getInstance().get(Calendar.YEAR); // ���݂̔N���i�[
		int entered_year = Integer.parseInt(((present_year - (present_year % 100)) / 100) + student_id.substring(0, 2)); // �w�肳�ꂽ�w�Дԍ��̊w�������w�����Ǝv����N(���݂̔N�̏�2��+�w�Дԍ��̍�2��)���i�[

		// ���w�����Ǝv����N�̒l�����݂̔N���������ꍇ
		if (entered_year > present_year) {
			entered_year -= 100; // 100�N���炷
		}

		return entered_year; // ���w�����N��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�w�Дԍ��̊w���̍ݐДN����Ԃ�
	 * @param student_id �w�Дԍ�
	 * @return �ݐДN��
	 */
	public synchronized int getAffiliationYear(String student_id) {
		int affiliation_year = Calendar.getInstance().get(Calendar.YEAR) - getEnteredYear(student_id); // �ݐДN����Ԃ�
		return (Calendar.getInstance().get(Calendar.MONTH) < 3 ? affiliation_year : affiliation_year + 1); // ���݂̌���3���܂łł���΂��̂܂܂̒l�A�����łȂ����+1�̒l��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�w�Дԍ��Ɋ܂܂��w�������ԍ�����w������ID��Ԃ�
	 * @param student_id �w�Дԍ�
	 * @return �w������ID
	 */
	public synchronized int getStudentAffiliationID(String student_id) {
		// �w�������ԍ��ɂ�镪��
		switch (Integer.parseInt(student_id.substring(2, 3))) {
			// �w�肳�ꂽ�w�������ԍ����o�ϊw�Ȃ̂��̂ł���ꍇ
			case 1:
			case 2:
			case 3:
				return 0; // 0��Ԃ�
			// �t�@�C�i���X�w�Ȃ̂��̂ł���ꍇ
			case 4:
				return 1; // 1��Ԃ�
			// ��ƌo�c�w�Ȃ̂��̂ł���ꍇ
			case 5:
			case 6:
				return 2; // 2��Ԃ�
			// ��v���w�Ȃ̂��̂ł���ꍇ
			case 7:
				return 3; // 3��Ԃ�
			// ���Ǘ��w�Ȃ̂��̂ł���ꍇ
			case 8:
				return 4; // 4��Ԃ�
			// �Љ�V�X�e���w�Ȃ̂��̂ł���ꍇ
			case 9:
				return 5; // 5��Ԃ�
			// ��Ԏ�̂��̂ł���ꍇ
			default:
				return 6; // 6��Ԃ�
		}
	}

	/**
	 * �w���ԍ���Ԃ�
	 * @return �w���ԍ�
	 */
	public int getSemesterNumber() {
		int month = Calendar.getInstance().get(Calendar.MONTH); // �����i�[
		return (3 <= month && month <= 8 ? 1 : 2); // 4������9���ł���ꍇ��1�A����ȊO�̏ꍇ��2��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�v����񂩂�J�u�u�`ID���擾���A�����Ԃ�
	 * @param request �v�����
	 * @return �J�u�u�`ID
	 */
	public synchronized String[][] getOpeningLectureIDs(HttpServletRequest request) {
		String[][] opening_lecture_ids = new String[6][7]; // �J�u�u�`ID���i�[

		// �J�u�u�`ID�̎擾
		for (int i = 0; i < opening_lecture_ids.length; i++) {
			for (int j = 0; j < opening_lecture_ids[i].length; j++) {
				opening_lecture_ids[i][j] = request.getParameter("opening_lecture_id_" + i + j);
			}
		}

		return opening_lecture_ids; // �J�u�u�`ID��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�_���̕]����Ԃ�
	 * @param score �_��
	 * @return �]��
	 */
	public synchronized String getEvaluation(String score) {
		// �_�����w�肳��Ă��Ȃ��ꍇ
		if (score == null) {
			return "�F��"; // "�F��"��Ԃ�
		// �w�肳��Ă���ꍇ
		} else {
			int grade = (Integer.parseInt(score) - 50) / 10; // �������i�[

			// ������0�ł���ꍇ
			if (grade <= 0) {
				return "�s��"; // "�s��"��Ԃ�
			// 1�ł���ꍇ
			} else if (grade == 1) {
				return "��"; // "��"��Ԃ�
			// 2�ł���ꍇ
			} else if (grade == 2) {
				return "��"; // "��"��Ԃ�
			// 3�ȏ�ł���ꍇ
			} else {
				return "�D"; // "�D"��Ԃ�
			}
		}
	}

	/**
	 * �w�肳�ꂽ�w�Дԍ��̊w�����K�v�ȒP�ʂ�Ԃ�
	 * @param student_id �w�Дԍ�
	 * @return �K�v�ȒP��
	 */
	public synchronized int[] getNecessaryCredits(String student_id) {
		int[] necessary_credits = {92, 0, 0, 0, 124}; // �K�v�ȒP�ʂ��i�[

		// �w�肳�ꂽ�w������Ԏ�ł���ꍇ
		if (getStudentAffiliationID(student_id) == 6) {
			necessary_credits[1] = 24; // ���ʉȖڂ̕K�v�ȒP�ʐ���24�ɐݒ�
			necessary_credits[2] = 8; // �O����Ȗڂ̕K�v�ȒP�ʐ���8�ɐݒ�
		// ���Ԏ�ł���ꍇ
		} else {
			necessary_credits[1] = 20; // ���ʉȖڂ̕K�v�ȒP�ʐ���20�ɐݒ�
			necessary_credits[2] = 12; // �O����Ȗڂ̕K�v�ȒP�ʐ���12�ɐݒ�
		}

		return necessary_credits; // �K�v�ȒP�ʂ�Ԃ�
	}

	/**
	 * �w�肳�ꂽ�p�P�����{��ɒu�������A�����Ԃ�
	 * @param english_word �p�P��
	 * @return ���{��ɒu��������ꂽ�p�P��
	 */
	public synchronized String getJapaneseWord(String english_word) {
		String[][] replace_words = {{"_", ""}, {"studentid", "�w�Дԍ�"}, {"id", "ID"}, {"new", "�V�K�o�^"}, {"credit", "�P��"}, {"edit", "�ҏW"}, {"course", "�Ȗ�"}, {"name", "��"}, {"lecturer", "����"}, {"openinglecture", "�J�u�u�`"}, {"lecture", "�u�`"}, {"affiliation", "����"}, {"number", "�ԍ�"}, {"chair", "�u��"}, {"student", "�w��"}, {"subject", "�w��"}, {"password", "�p�X���[�h"}, {"birthday", "�a����"}, {"nationality", "����"}, {"basicseminar", "��b���K"}, {"infomationliteracy", "��񃊃e���V�["}, {"class", "�N���X"}, {"healthandlife", "���N�Ɛ���"}, {"sportsscience", "�X�|�[�c�Ȋw"}, {"english", "�p��"}, {"second", "��2"}, {"foreignlanguage", "�O����"}, {"seminar", "�[�~"}, {"day", "���Ԏ�"}, {"night", "��Ԏ�"}, {"masteryear", "���C�N��"}, {"masterableyear", "���C�\�N"}, {"registeredasanotherdata", "�ʃf�[�^�Ƃ��ēo�^"}, {"start", "�n�["}, {"end", "�I�["}, {"economy", "�o��"}, {"finance", "�t�@�C�i���X"}, {"enterprisemanagement", "��ƌo�c"}, {"accounts", "��v"}, {"information", "���"}, {"management", "�Ǘ�"}, {"societysystem", "�Љ�V�X�e��"}, {"unmasterable", "�𗚏C�s�ɂ���"}, {"is", ""}, {"necessary", "�K�v"}, {"i", "I"}}; // �u�������錾�t���i�[

		// �e��̒u������
		for (int i = 0; i < replace_words.length; i++) {
			english_word = Pattern.compile(replace_words[i][0]).matcher(english_word).replaceAll(replace_words[i][1]);
		}

		return english_word; // ���{��ɒu��������ꂽ�p�P��
	}

	/**
	 * �ݒ肳�ꂽ�e�[�u�����̃f�[�^�����ԍ��̖��O��Ԃ�
	 * @param table_name �e�[�u����
	 * @return �f�[�^�����ԍ��̖��O
	 */
	public String getDataAffiliationNumberName(String table_name) {
		String data_affiliation_number_name = null; // �f�[�^�����ԍ��̖��O���i�[

		// �w�肳�ꂽ�e�[�u�������u�`�����e�[�u���ł���ꍇ
		if (table_name.equals("lecture_affiliation")) {
			data_affiliation_number_name = "course_id"; // "course_id"���f�[�^�����ԍ��̖��O�ɐݒ�
		// �u���e�[�u���ł���ꍇ
		} else if (table_name.equals("chair")) {
			data_affiliation_number_name = "subject_id"; // "subject_id"���f�[�^�����ԍ��̖��O�ɐݒ�
		// �w���e�[�u���ł���ꍇ
		} else if (table_name.equals("student")) {
			data_affiliation_number_name = "student_affiliation_number"; // "student_affiliation_number"���f�[�^�����ԍ��̖��O�ɐݒ�
		// �����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecturer")) {
			data_affiliation_number_name = "lecturer_affiliation_number"; // "lecturer_affiliation_number"���f�[�^�����ԍ��̖��O�ɐݒ�
		// lecture�ł���ꍇ
		} else if (table_name.equals("lecture")) {
			data_affiliation_number_name = "lecture_affiliation_number"; // "lecture_affiliation_number"���f�[�^�����ԍ��̖��O�ɐݒ�
		// �J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture")) {
			data_affiliation_number_name = "opening_lecture_affiliation_number"; // "opening_lecture_affiliation_number"���f�[�^�����ԍ��̖��O�ɐݒ�
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			data_affiliation_number_name = "opening_lecture_id"; // "opening_lecture_id"���f�[�^�����ԍ��̖��O�ɐݒ�
		}

		return data_affiliation_number_name; // �f�[�^�����ԍ��̖��O��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u�����ɉ����Ďw�肳�ꂽID�Ɋ܂܂��f�[�^�����ԍ���Ԃ�
	 * @param table_name �e�[�u����
	 * @param id ID
	 * @return �f�[�^�����ԍ�
	 */
	public String getDataAffiliationNumber(String table_name, String id) {
		String data_affiliation_number = null; // �f�[�^�����ԍ����i�[

		// �w�肳�ꂽ�e�[�u�����u�`�����e�[�u���ł���ꍇ
		if (table_name.equals("lecture_affiliation")) {
			data_affiliation_number = id.substring(0, 1); // �w�肳�ꂽID��1�����ڂ��f�[�^�����ԍ��ɐݒ�
		// �u���e�[�u���ł���ꍇ
		} else if (table_name.equals("chair")) {
			data_affiliation_number = id.substring(0, 1); // �w�肳�ꂽID��1�����ڂ��f�[�^�����ԍ��ɐݒ�
		// �u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture")) {
			data_affiliation_number = id.substring(0, 3); // �w�肳�ꂽID��1�����ڂ���3�����ڂ܂ł��f�[�^�����ԍ��ɐݒ�
		// �����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecturer")) {
			data_affiliation_number = id.substring(0, 1); // �w�肳�ꂽID��1�����ڂ��f�[�^�����ԍ��ɐݒ�
		// �J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture")) {
			data_affiliation_number = id.substring(0, 3); // �w�肳�ꂽID��1�����ڂ���3�����ڂ܂ł��f�[�^�����ԍ��ɐݒ�
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			data_affiliation_number = id.substring(0, 5); // �w�肳�ꂽID��1�����ڂ���5�����ڂ܂ł��f�[�^�����ԍ��ɐݒ�
		// �w���e�[�u���ł���ꍇ
		} else if (table_name.equals("student")) {
			data_affiliation_number = id.substring(0, 3); // �w�肳�ꂽID��1�����ڂ���3�����ڂ܂ł��f�[�^�����ԍ��ɐݒ�
		}

		return data_affiliation_number; // �f�[�^�����ԍ���Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u�����E�v����񂩂�f�[�^�����ԍ����擾���A�����Ԃ�
	 * @param table_name �e�[�u����
	 * @param request �v�����
	 * @return �f�[�^�����ԍ�
	 */
	public String getDataAffiliationNumber(String table_name, HttpServletRequest request) {
		String data_affiliation_number_name = getDataAffiliationNumberName(table_name); // �f�[�^�����ԍ������i�[
		return (data_affiliation_number_name != null ? request.getParameter(data_affiliation_number_name) : null); // �f�[�^�����ԍ������擾�ł����ꍇ�̓f�[�^�����ԍ��A�擾�ł��Ȃ������ꍇ��null��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u���̃f�[�^�����ԍ��̃t�H�[���\���v�f�f�[�^��Ԃ�
	 * @param table_name �e�[�u����
	 * @return �f�[�^�����ԍ��̃t�H�[���\���v�f�f�[�^
	 * @exception SQLException
	 */
	public String[][] getDataAffiliationNumberFormComponentData(String table_name) throws SQLException {
		String[] form_component_ids = null; // �t�H�[���\���v�f��ID���i�[
		String[] form_component_outlines = null; // �t�H�[���\���v�f�̌��o�����i�[

		// �w�肳�ꂽ�e�[�u�����u�`�����e�[�u���ł���ꍇ
		if (table_name.equals("lecture_affiliation")) {
			String[][] course_data = databaseBean.getRowsData("course"); // �Ȗڃf�[�^���i�[

			// �Ȗڃf�[�^���擾�ł����ꍇ
			if (course_data != null) {
				// �t�H�[���\���v�f�f�[�^�̗v�f���̐ݒ�
				form_component_ids = new String[course_data.length];
				form_component_outlines = new String[course_data.length];

				// �t�H�[���\���v�f�f�[�^�̎擾
				for (int i = 0; i < course_data.length; i++) {
					form_component_ids[i] = course_data[i][0];
					form_component_outlines[i] = course_data[i][1];
				}
			}
		// �u���e�[�u���A���͊w���e�[�u���ł���ꍇ
		} else if (table_name.equals("chair") || table_name.equals("student")) {
			String[][] lecture_affiliation_data = databaseBean.getRowsData("lecture_affiliation", "0"); // �u�`�����f�[�^���i�[

			// �u�`�����f�[�^���擾�ł����ꍇ
			if (lecture_affiliation_data != null) {
				String[][] subject_data = new String[2][lecture_affiliation_data.length - 1]; // �w�ȃf�[�^���i�[

				// �w�ȃf�[�^�̎擾
				for (int i = 0; i < subject_data[0].length; i++) {
					subject_data[0][i] = lecture_affiliation_data[i][0].substring(1);
					subject_data[1][i] = lecture_affiliation_data[i][1];
				}

				// �w�肳�ꂽ�e�[�u�������u���e�[�u���ł���ꍇ
				if (table_name.equals("chair")) {
					form_component_ids = new String[subject_data[0].length]; // �t�H�[���\���v�fID�̗v�f���̐ݒ�
					form_component_outlines = new String[form_component_ids.length]; // �t�H�[���\���v�f���o���̗v�f���̐ݒ�

					// �\���v�f�f�[�^�̎擾
					for (int i = 0; i < form_component_ids.length; i++) {
						form_component_ids[i] = subject_data[0][i];
						form_component_outlines[i] = subject_data[1][i];
					}
				// �w���e�[�u���ł���ꍇ
				} else {
					String[][] student_affiliation_data = new String[2][subject_data[0].length + 1]; // �w�������f�[�^���i�[

					// �w�������f�[�^�̎擾
					for (int i = 0; i < subject_data[0].length; i++) {
						student_affiliation_data[0][i] = subject_data[0][i];
						student_affiliation_data[1][i] = subject_data[1][i];
					}
					student_affiliation_data[0][subject_data[0].length] = "6";
					student_affiliation_data[1][subject_data[1].length] = "��Ԏ�";

					int year = Calendar.getInstance().get(Calendar.YEAR); // �N���i�[
					String[] years = getArray(year, year - 11); // �N���i�[
					int component_count = years.length * student_affiliation_data[0].length; // �\���v�f�����i�[
					form_component_ids = new String[component_count]; // ID�̗v�f���̐ݒ�
					form_component_outlines = new String[component_count]; // ���o���̗v�f���̐ݒ�

					// �\���v�f�f�[�^�̎擾
					for (int i = 0; i < years.length; i++) {
						String student_affiliation_year = years[i].substring(2); // �w���̏����N���i�[

						for (int j = 0; j < STUDENT_AFFILIATION_NUMBERS.length; j++) {
							int index = (i * STUDENT_AFFILIATION_NUMBERS.length) + j; // �C���f�b�N�X���i�[
							form_component_ids[index] = student_affiliation_year + STUDENT_AFFILIATION_NUMBERS[Integer.parseInt(student_affiliation_data[0][j])]; // ID�̎擾
							form_component_outlines[index] = years[i] + "�N�E" + student_affiliation_data[1][j]; // ���o���̎擾
						}
					}
				}
			}
		// �����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecturer")) {
			String[][] lecture_affiliation_data = databaseBean.getRowsData("lecture_affiliation", "0"); // �u�`�����f�[�^���i�[

			// �u�`�����f�[�^���擾�ł����ꍇ
			if (lecture_affiliation_data != null) {
				// �t�H�[���\���v�f�f�[�^�̗v�f���̐ݒ�
				form_component_ids = new String[lecture_affiliation_data.length];
				form_component_outlines = new String[lecture_affiliation_data.length];

				// �t�H�[���\���v�f�f�[�^�̎擾ID�̎擾
				for (int i = 0;i < lecture_affiliation_data.length; i++) {
					form_component_ids[i] = lecture_affiliation_data[i][0].substring(1);
					form_component_outlines[i] = lecture_affiliation_data[i][1];
				}
			}
		// �u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture")) {
			String[][] course_data = databaseBean.getRowsData("course"); // �Ȗڃf�[�^���i�[

			// �Ȗڃf�[�^���擾�ł����ꍇ
			if (course_data != null) {
				Vector vector_form_component_ids = new Vector(); // �t�H�[���\���v�f��ID���i�[
				Vector vector_form_component_outlines = new Vector(); // �t�H�[���\���v�f�̌��o�����i�[

				// �\���v�f�f�[�^�̎擾
				for (int i = 0; i < course_data.length; i++) {
					String[][] lecture_affiliation_data = databaseBean.getRowsData("lecture_affiliation", course_data[i][0]); // �u�`�����f�[�^���i�[

					// �u�`�����f�[�^���擾�ł��Ȃ������ꍇ
					if (lecture_affiliation_data == null) {
						// �Ȗڃf�[�^�̍\���v�f�f�[�^�ւ̕t��
						vector_form_component_ids.addElement(course_data[i][0] + "00");
						vector_form_component_outlines.addElement(course_data[i][1]);
					// �擾�ł����ꍇ
					} else {
						int lecture_affiliation_number_length = getDataAffiliationNumber("lecture_affiliation", lecture_affiliation_data[0][0]).length(); // �u�`�����ԍ��̌������i�[

						// �u�`�����f�[�^�̕t��
						for (int j = 0; j < lecture_affiliation_data.length; j++) {
							String outline = course_data[i][1] + "�E" + lecture_affiliation_data[j][1]; // ���o�����i�[
							String[][] chair_data = null; // �u���f�[�^���i�[

							// ���݃A�N�Z�X���Ă���Ȗڃf�[�^�����f�[�^�ł���ꍇ
							if (course_data[i][0].equals("0")) {
								chair_data = databaseBean.getRowsData("chair", lecture_affiliation_data[j][0].substring(lecture_affiliation_number_length)); // �u���f�[�^�̎擾
							}

							// �u���f�[�^��null�ł���ꍇ
							if (chair_data == null) {
								// �Ȗڃf�[�^�ƍu�`�����f�[�^�̍\���v�f�f�[�^�ւ̕t��
								vector_form_component_ids.addElement(lecture_affiliation_data[j][0] + "0");
								vector_form_component_outlines.addElement(outline);
							// null�łȂ��ꍇ
							} else {
								int chair_affiliation_number_length = getDataAffiliationNumber("chair", chair_data[0][0]).length(); // �u�������ԍ��̌������i�[

								// �Ȗڃf�[�^�ƍu�`�����f�[�^�A�u���f�[�^�̍\���v�f�f�[�^�ւ̕t��
								for (int k = 0; k < chair_data.length; k++) {
									vector_form_component_ids.addElement(lecture_affiliation_data[j][0] + chair_data[k][0].substring(chair_affiliation_number_length));
									vector_form_component_outlines.addElement(outline + "�E" + chair_data[k][1]);
								}
							}
						}
					}
				}

				form_component_ids = new String[vector_form_component_ids.size()]; // ID�̗v�f���̐ݒ�
				form_component_outlines = new String[form_component_ids.length]; // ���o���̗v�f���̐ݒ�

				// �\���v�f�f�[�^�̎擾
				for (int i = 0; i < form_component_ids.length; i++) {
					form_component_ids[i] = (String) vector_form_component_ids.elementAt(i);
					form_component_outlines[i] = (String) vector_form_component_outlines.elementAt(i);
				}
			}
		// �J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture")) {
			final String[] SEMESTER_NAMES = {"�ʔN", "�t�w��", "�H�w��"}; // �w�������i�[
			final String[] DAY_NAMES = {"��", "��", "��", "��", "��"}; // �j�������i�[
			final int CLASS_COUNT = 7; // ���������i�[
			final int CONCENTRATION_LECTURE_COUNT = 5; // �W���u�`�����i�[
			form_component_ids = new String[(SEMESTER_NAMES.length * DAY_NAMES.length * CLASS_COUNT) + ((SEMESTER_NAMES.length - 1) * CONCENTRATION_LECTURE_COUNT)]; // �t�H�[���\���v�fID�̗v�f���̐ݒ�
			form_component_outlines = new String[form_component_ids.length]; // �t�H�[���\���v�f���̗v�f���̐ݒ�
			int form_component_data_index = 0; // �t�H�[���\���v�f�f�[�^�̃C���f�b�N�X���i�[

			// �t�H�[���\���v�f�f�[�^�̎擾
			for (int i = 0; i < SEMESTER_NAMES.length; i++) {
				// �ʏ�u�`�f�[�^�̎擾
				for (int j = 0; j < DAY_NAMES.length; j++) {
					for (int k = 0; k < CLASS_COUNT; k++) {
						form_component_ids[form_component_data_index] = i + "" + j + "" + k; // �t�H�[���\���v�fID�̐ݒ�
						form_component_outlines[form_component_data_index] = SEMESTER_NAMES[i] + "�E" + DAY_NAMES[j] + "�j���E" + (k + 1) + "������"; // �t�H�[���\���v�f���o���̐ݒ�
						form_component_data_index++; // �t�H�[���\���v�f�f�[�^�̃C���f�b�N�X�̉��Z
					}
				}

				// ���݃A�N�Z�X���Ă���w���v�f���ʔN�ȊO�ł���ꍇ
				if (i > 0) {
					// �W���u�`�f�[�^�̎擾
					for (int j = 0; j < CONCENTRATION_LECTURE_COUNT; j++) {
						form_component_ids[form_component_data_index] = i + "" + DAY_NAMES.length + "" + j; // �t�H�[���\���v�fID�̐ݒ�
						form_component_outlines[form_component_data_index] = SEMESTER_NAMES[i] + "�E" + "�W���u�`(" + (j + 1) + ")"; // �t�H�[���\���v�f���o���̐ݒ�
						form_component_data_index++; // �t�H�[���\���v�f�f�[�^�̃C���f�b�N�X�̉��Z
					}
				}
			}
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			String[][] opening_lecture_data = databaseBean.getRowsData("opening_lecture"); // �J�u�u�`�f�[�^���i�[

			// �J�u�u�`�f�[�^���擾�ł����ꍇ
			if (opening_lecture_data != null) {
				form_component_ids = new String[opening_lecture_data.length]; // �t�H�[���\���v�fID�̗v�f�����J�u�u�`�f�[�^�̗v�f���ɐݒ�
				form_component_outlines = new String[form_component_ids.length]; // �t�H�[���\���v�f���o���̗v�f�����t�H�[���\���v�f���o���̗v�f���ɐݒ�
				String[][] lecture_data = databaseBean.getRowsData("lecture"); // �u�`�f�[�^���i�[

				// �t�H�[���\���v�f�f�[�^�̎擾
				for (int i = 0; i < form_component_ids.length; i++) {
					form_component_ids[i] = opening_lecture_data[i][0]; // �t�H�[���\���v�fID�̎擾

					// �t�H�[���\���v�f���o���̎擾
					for (int j = 0; j < lecture_data.length; j++) {
						// ���݃A�N�Z�X���Ă���J�u�u�`�f�[�^�����u�`ID�����݃A�N�Z�X���Ă���u�`�f�[�^�̍u�`ID�Ɠ������ꍇ
						if (opening_lecture_data[i][1].equals(lecture_data[j][0])) {
							form_component_outlines[i] = lecture_data[j][1]; // �t�H�[���\���v�f���o���̎擾
							break; // ���[�v���甲����
						}
					}
				}
			}
		}

		// ID��null�ł���ꍇ
		if (form_component_ids == null) {
			return null; // null��Ԃ�
		// null�łȂ��ꍇ
		} else {
			String[][] lecture_affiliation_number_form_component_data = new String[2][form_component_ids.length]; // �u�`�����ԍ��̃t�H�[���̍\���v�f�f�[�^���i�[

			// �u�`�����ԍ��̃t�H�[���̍\���v�f�f�[�^�̎擾
			for (int i = 0; i < form_component_ids.length; i++) {
				lecture_affiliation_number_form_component_data[0][i] = form_component_ids[i];
				lecture_affiliation_number_form_component_data[1][i] = form_component_outlines[i];
			}

			return lecture_affiliation_number_form_component_data; // �u�`�����ԍ��̃t�H�[���̍\���v�f�f�[�^�f�[�^��Ԃ�
		}
	}

	/**
	 * �w�肳�ꂽ�e�[�u�����̃t�H�[���̍\���v�f����Ԃ�
	 * @param table_name �e�[�u����
	 * @return �t�H�[���̍\���v�f��
	 * @exception SQLException
	 */
	public String[] getFormComponentNames(String table_name) throws SQLException {
		Vector id_component_names = new Vector(); // ID�̍\���v�f�����i�[
		Vector vector_form_component_names = new Vector(); // �t�H�[���̍\���v�f�����i�[
		String data_affiliation_number_name = getDataAffiliationNumberName(table_name); // �w�肳�ꂽ�e�[�u���̃f�[�^�����ԍ��̖��O���i�[
		String[] table_column_names = databaseBean.getColumnNames(table_name); // �w�肳�ꂽ�e�[�u���̗񖼂��i�[

		// �w�肳�ꂽ�e�[�u�������f�[�^�����ԍ��̂Ȃ��e�[�u���ł���ꍇ
		if (data_affiliation_number_name == null) {
			vector_form_component_names.addElement(table_column_names[0]); // ID���̕t��
		// �f�[�^�����ԍ��̂���e�[�u���ł���ꍇ
		} else {
			vector_form_component_names.addElement(data_affiliation_number_name); // �f�[�^�����ԍ����̕t��
			vector_form_component_names.addElement(table_name + "_number"); // �f�[�^�ԍ����̕t��
		}

		// �t�H�[���̍\���v�f���́AID�̍\���v�f�����ȊO�̎擾
		for (int i = 1; i < table_column_names.length; i++) {
			vector_form_component_names.addElement(table_column_names[i]);
		}

		// �w�肳�ꂽ�e�[�u�����u�`�e�[�u���ł���ꍇ
		if (table_name.equals("lecture")) {
			vector_form_component_names.addElement("is_registered_as_another_data"); // �t�H�[���̍\���v�f�̕t��
		}

		String[] form_component_names = new String[vector_form_component_names.size()]; // �t�H�[���̍\���v�f�����i�[

		// �t�H�[���̍\���v�f���̎擾
		for (int i = 0; i < form_component_names.length; i++) {
			form_component_names[i] = (String) vector_form_component_names.elementAt(i);
		}

		return form_component_names; // �t�H�[���̍\���v�f����Ԃ�
	}

	/**
	 * �w�肳�ꂽ�t�H�[���̍\���v�f���E�v����񂩂�t�H�[���f�[�^���쐬���A�����Ԃ�
	 * @param form_component_names �t�H�[���̍\���v�f��
	 * @param request �v�����
	 * @return �t�H�[���f�[�^
	 */
	public String[] getFormData(String[] form_component_names, HttpServletRequest request) {
		String[] form_data = new String[form_component_names.length]; // �t�H�[���f�[�^���i�[
		
		// �t�H�[���f�[�^�̎擾
		for (int i = 0; i < form_data.length; i++) {
			form_data[i] = request.getParameter(form_component_names[i]);
		}

		return form_data; // �t�H�[���f�[�^��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�J�u�u�`ID�ɊY������u�`�𗚏C�o�^�����w���̊w�Дԍ���Ԃ�
	 * @param opening_lecture_id �J�u�u�`ID
	 * @return �w�Дԍ�
	 * @exception SQLException
	 */
	public String[] getStudentIDs(String opening_lecture_id) throws SQLException {
		String[] student_ids = null; // �w�Дԍ����i�[
		String[][] registered_opening_lecture_data = databaseBean.getRowsData("registered_opening_lecture"); // �o�^�ς݊J�u�u�`�f�[�^���i�[

		// �o�^�ς݊J�u�u�`�f�[�^���擾�ł����ꍇ
		if (registered_opening_lecture_data != null) {
			int semester_number = getSemesterNumber(); // �w���ԍ����i�[
			Vector vector_student_ids = new Vector(); // �w�Дԍ����i�[

			// �w�Дԍ��̎擾
			for (int i = 0; i < registered_opening_lecture_data.length; i++) {
				// ���݃A�N�Z�X���Ă���o�^�ς݊J�u�u�`���w�肳�ꂽ�J�u�u�`�Ɠ������A���݂̊w���ɓo�^���ꂽ���̂ł���ꍇ
				if (registered_opening_lecture_data[i][1].equals(opening_lecture_id) && Integer.parseInt(registered_opening_lecture_data[i][0].substring(5, 6)) == semester_number) {
					vector_student_ids.addElement(registered_opening_lecture_data[i][0].substring(0, 5)); // �w�Дԍ��̕t��
				}
			}

			// �w�Дԍ����擾�ł����ꍇ
			if (!vector_student_ids.isEmpty()) {
				student_ids = new String[vector_student_ids.size()]; // �w�Дԍ������w�Дԍ��̗v�f���ɐݒ�

				// �w�Дԍ��̎擾
				for (int i = 0; i < student_ids.length; i++) {
					student_ids[i] = vector_student_ids.elementAt(i).toString();
				}
			}
		}

		return student_ids; // �w�Дԍ���Ԃ�
	}

	/**
	 * �w�肳�ꂽ�J�n�l�E�I���l����z����쐬���A�����Ԃ�
	 * @param start_value �J�n�l
	 * @param end_value �I���l
	 * @return �z��
	 */
	public String[] getArray(int start_value, int end_value) {
		return getArray(start_value, end_value, 1);
	}

	/**
	 * �w�肳�ꂽ�J�n�l�E�I���l�E�Ԋu����z����쐬���A�����Ԃ�
	 * @param start_value �J�n�l
	 * @param end_value �I���l
	 * @param interval �Ԋu
	 * @return �z��
	 */
	public String[] getArray(int start_value, int end_value, int interval) {
		boolean is_value_reversed = false; // �l������ւ���ꂽ���ǂ������i�[

		// �w�肳�ꂽ�J�n�l���I���l�����傫���ꍇ
		if (start_value > end_value) {
			// �l�̓���ւ�
			int temporary_memory_area = start_value;
			start_value = end_value;
			end_value = temporary_memory_area;

			is_value_reversed = true; // �l������ւ���ꂽ���Ƃ̐ݒ�
		}

		Vector vector_array = new Vector(); // �z����i�[

		// �v�f�̎擾
		for (int i = start_value; i <= end_value; i += interval) {
			vector_array.addElement("" + i);
		}

		String[] array = new String[vector_array.size()]; // �z����i�[

		// �v�f�̎擾
		for (int i = 0; i < array.length; i++) {
			int array_index = i; // �z��̃C���f�b�N�X���i�[

			// �l������ւ����Ă���ꍇ
			if (is_value_reversed) {
				array_index = array.length - (i + 1); // �z��̃C���f�b�N�X�̕ύX
			}

			array[array_index] = (String) vector_array.elementAt(i); // �v�f�̎擾
		}

		return array; // �z���Ԃ�
	}
}
