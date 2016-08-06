// �p�b�P�[�W�̐錾
package bean;

// Bean�̓���ɕK�v�ȃp�b�P�[�W�̃C���|�[�g
import java.io.Serializable;

// ���\�b�h���̏����ŗ��p����p�b�P�[�W�̃C���|�[�g
import java.sql.*;
import java.util.Calendar;

/**
 * �f�[�^�x�[�X�Ɋւ��鏈����񋟂���Bean
 * @author Ryo Fukushima
 */
public class DatabaseBean implements Serializable {
	/** UtilityBean���i�[ */
	private UtilityBean utilityBean = null;

	/** �f�[�^�x�[�X�ւ̃R�l�N�V�������i�[ */
	private Connection connection = null;

	/** �S�Ă̊Ǘ��҃f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allAdministratorDataSelectPreparedStatement = null;

	/** �Ǘ��҃f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement administratorDataSelectPreparedStatement = null;

	/** �S�ẲȖڃf�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allCourseDataSelectPreparedStatement = null;

	/** �Ȗڃf�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement courseDataSelectPreparedStatement = null;

	/** �Ȗڃf�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement courseDataInsertPreparedStatement = null;

	/** �Ȗڃf�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement courseDataUpdatePreparedStatement = null;

	/** �Ȗڃf�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement courseDataDeletePreparedStatement = null;

	/** �S�Ă̍u�`�����f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allLectureAffiliationDataSelectPreparedStatement = null;

	/** �w�肳�ꂽ�f�[�^�����ԍ��̍u�`�����f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement lectureAffiliationDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** �u�`�����f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement lectureAffiliationDataSelectPreparedStatement = null;

	/** �u�`�����f�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement lectureAffiliationDataInsertPreparedStatement = null;

	/** �u�`�����f�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement lectureAffiliationDataUpdatePreparedStatement = null;

	/** �u�`�����f�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement lectureAffiliationDataDeletePreparedStatement = null;

	/** �S�Ă̍u���f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allChairDataSelectPreparedStatement = null;

	/** �w�肳�ꂽ�f�[�^�����ԍ��̍u���f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement chairDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** �u���f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement chairDataSelectPreparedStatement = null;

	/** �u���f�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement chairDataInsertPreparedStatement = null;

	/** �u���f�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement chairDataUpdatePreparedStatement = null;

	/** �u���f�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement chairDataDeletePreparedStatement = null;

	/** �S�Ă̊w���f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allStudentDataSelectPreparedStatement = null;

	/** �w�肳�ꂽ�f�[�^�����ԍ��̊w���f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement studentDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** �w���f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement studentDataSelectPreparedStatement = null;

	/** �w���f�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement studentDataInsertPreparedStatement = null;

	/** �w���f�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement studentDataUpdatePreparedStatement = null;

	/** �w���f�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement studentDataDeletePreparedStatement = null;

	/** �S�Ă̋����f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allLecturerDataSelectPreparedStatement = null;

	/** �w�肳�ꂽ�f�[�^�����ԍ��̋����f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement lecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** �����f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement lecturerDataSelectPreparedStatement = null;

	/** �����f�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement lecturerDataInsertPreparedStatement = null;

	/** �����f�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement lecturerDataUpdatePreparedStatement = null;

	/** �����f�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement lecturerDataDeletePreparedStatement = null;

	/** �S�Ă̍u�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allLectureDataSelectPreparedStatement = null;

	/** �w�肳�ꂽ�f�[�^�����ԍ��̍u�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement lectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** �u�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement lectureDataSelectPreparedStatement = null;

	/** �u�`�f�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement lectureDataInsertPreparedStatement = null;

	/** �u�`�f�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement lectureDataUpdatePreparedStatement = null;

	/** �u�`�f�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement lectureDataDeletePreparedStatement = null;

	/** �S�Ă̊J�u�u�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allOpeningLectureDataSelectPreparedStatement = null;

	/** �w�肳�ꂽ�f�[�^�����ԍ��̊J�u�u�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement openingLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** �J�u�u�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement openingLectureDataSelectPreparedStatement = null;

	/** �J�u�u�`�f�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement openingLectureDataInsertPreparedStatement = null;

	/** �J�u�u�`�f�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement openingLectureDataUpdatePreparedStatement = null;

	/** �J�u�u�`�f�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement openingLectureDataDeletePreparedStatement = null;

	/** �S�Ă̊J�u�u�`�S�������f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allOpeningLectureLecturerDataSelectPreparedStatement = null;

	/** �w�肳�ꂽ�f�[�^�����ԍ��̊J�u�u�`�S�������f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement openingLectureLecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** �J�u�u�`�S�������f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement openingLectureLecturerDataSelectPreparedStatement = null;

	/** �J�u�u�`�S�������f�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement openingLectureLecturerDataInsertPreparedStatement = null;

	/** �J�u�u�`�S�������f�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement openingLectureLecturerDataUpdatePreparedStatement = null;

	/** �J�u�u�`�S�������f�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement openingLectureLecturerDataDeletePreparedStatement = null;

	/** �S�Ă̓o�^�ς݊J�u�u�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allRegisteredOpeningLectureDataSelectPreparedStatement = null;

	/** �w�肳�ꂽ�f�[�^�����ԍ��̓o�^�ς݊J�u�u�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement registeredOpeningLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** �o�^�ς݊J�u�u�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement registeredOpeningLectureDataSelectPreparedStatement = null;

	/** �o�^�ς݊J�u�u�`�f�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement registeredOpeningLectureDataInsertPreparedStatement = null;

	/** �o�^�ς݊J�u�u�`�f�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement registeredOpeningLectureDataUpdatePreparedStatement = null;

	/** �o�^�ς݊J�u�u�`�f�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement registeredOpeningLectureDataDeletePreparedStatement = null;

	/** �S�Ă̗��C�ςݍu�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement allMasteredLectureDataSelectPreparedStatement = null;

	/** ���C�ςݍu�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement masteredLectureDataSelectPreparedStatement = null;

	/** �w�肳�ꂽ�f�[�^�����ԍ��̗��C�ςݍu�`�f�[�^��I�������͍ς�SQL���i�[ */
	private PreparedStatement masteredLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** ���C�ςݍu�`�f�[�^��}�������͍ς�SQL���i�[ */
	private PreparedStatement masteredLectureDataInsertPreparedStatement = null;

	/** ���C�ςݍu�`�f�[�^���X�V�����͍ς�SQL���i�[ */
	private PreparedStatement masteredLectureDataUpdatePreparedStatement = null;

	/** ���C�ςݍu�`�f�[�^���폜�����͍ς�SQL���i�[ */
	private PreparedStatement masteredLectureDataDeletePreparedStatement = null;

	/**
	 * �R���X�g���N�^
	 * @exception ClassNotFoundException
	 * @exception SQLException
	 */
	public DatabaseBean() throws ClassNotFoundException, SQLException {
		Class.forName("org.gjt.mm.mysql.Driver"); // MySQL�p��JDBC�h���C�o�̃��[�h
		connection = DriverManager.getConnection("jdbc:mysql://localhost/lecture?user=admin&password=admin&useUnicode=true&characterEncoding=Shift_JIS"); // �R�l�N�V�����̊m��
		connection.setReadOnly(true); // �f�[�^�x�[�X��ǂݎ���p�ɐݒ�

		// �Ǘ��҃f�[�^�Ɋւ����͍ς�SQL�̐���
		allAdministratorDataSelectPreparedStatement = connection.prepareStatement("select * from administrator");
		administratorDataSelectPreparedStatement = connection.prepareStatement("select * from administrator where administrator_id=?");

		// �Ȗڃf�[�^�Ɋւ����͍ς�SQL�̐���
		allCourseDataSelectPreparedStatement = connection.prepareStatement("select * from course order by course_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		courseDataSelectPreparedStatement = connection.prepareStatement("select * from course where course_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		courseDataInsertPreparedStatement = connection.prepareStatement("insert into course values(?, ?)");
		courseDataUpdatePreparedStatement = connection.prepareStatement("update course set course_id=?, course_name=? where course_id=?");
		courseDataDeletePreparedStatement = connection.prepareStatement("delete from course where course_id=?");

		// �u�`�����f�[�^�Ɋւ����͍ς�SQL�̐���
		allLectureAffiliationDataSelectPreparedStatement = connection.prepareStatement("select * from lecture_affiliation order by lecture_affiliation_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureAffiliationDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from lecture_affiliation where lecture_affiliation_id like \"?_\" order by lecture_affiliation_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureAffiliationDataSelectPreparedStatement = connection.prepareStatement("select * from lecture_affiliation where lecture_affiliation_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureAffiliationDataInsertPreparedStatement = connection.prepareStatement("insert into lecture_affiliation values(?, ?)");
		lectureAffiliationDataUpdatePreparedStatement = connection.prepareStatement("update lecture_affiliation set lecture_affiliation_id=?, lecture_affiliation_name=? where lecture_affiliation_id=?");
		lectureAffiliationDataDeletePreparedStatement = connection.prepareStatement("delete from lecture_affiliation where lecture_affiliation_id=?");

		// �u���f�[�^�Ɋւ����͍ς�SQL�̐���
		allChairDataSelectPreparedStatement = connection.prepareStatement("select * from chair order by chair_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		chairDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from chair where chair_id like \"?_\" order by chair_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		chairDataSelectPreparedStatement = connection.prepareStatement("select * from chair where chair_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		chairDataInsertPreparedStatement = connection.prepareStatement("insert into chair values(?, ?)");
		chairDataUpdatePreparedStatement = connection.prepareStatement("update chair set chair_id=?, chair_name=? where chair_id=?");
		chairDataDeletePreparedStatement = connection.prepareStatement("delete from chair where chair_id=?");

		// �w���f�[�^�Ɋւ����͍ς�SQL�̐���
		allStudentDataSelectPreparedStatement = connection.prepareStatement("select * from student order by student_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		studentDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from student where student_id between ? and ? order by student_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		studentDataSelectPreparedStatement = connection.prepareStatement("select * from student where student_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		studentDataInsertPreparedStatement = connection.prepareStatement("insert into student values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		studentDataUpdatePreparedStatement = connection.prepareStatement("update student set student_id=?, student_name=?, password=?, birthday=?, nationality_number=?, basic_seminar_lecturer_id=?, infomation_literacy_class=?, health_and_life_class=?, sports_science_i_class=?, english_i_class=?, english_ii_h_class=?, english_ii_a_class=?, second_foreign_language_id=?, second_foreign_language_class=?, seminar_lecturer_id=? where student_id=?");
		studentDataDeletePreparedStatement = connection.prepareStatement("delete from student where student_id=?");

		// �����f�[�^�Ɋւ����͍ς�SQL�̐���
		allLecturerDataSelectPreparedStatement = connection.prepareStatement("select * from lecturer order by lecturer_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from lecturer where lecturer_id like \"?___\" order by lecturer_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lecturerDataSelectPreparedStatement = connection.prepareStatement("select * from lecturer where lecturer_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lecturerDataInsertPreparedStatement = connection.prepareStatement("insert into lecturer values(?, ?)");
		lecturerDataUpdatePreparedStatement = connection.prepareStatement("update lecturer set lecturer_id=?, lecturer_name=? where lecturer_id=?");
		lecturerDataDeletePreparedStatement = connection.prepareStatement("delete from lecturer where lecturer_id=?");

		// �u�`�f�[�^�Ɋւ����͍ς�SQL�̐���
		allLectureDataSelectPreparedStatement = connection.prepareStatement("select * from lecture order by lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from lecture where lecture_id like \"???____\" order by lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureDataSelectPreparedStatement = connection.prepareStatement("select * from lecture where lecture_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureDataInsertPreparedStatement = connection.prepareStatement("insert into lecture values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		lectureDataUpdatePreparedStatement = connection.prepareStatement("update lecture set lecture_id=?, lecture_name=?, credit=?, day_master_year_number=?, night_master_year_number=?, day_masterable_year_number=?, night_masterable_year_number=?, start_masterable_year=?, end_masterable_year=?, is_economy_subject_unmasterable=?, is_finance_subject_unmasterable=?, is_enterprise_management_subject_unmasterable=?, is_accounts_information_subject_unmasterable=?, is_information_management_subject_unmasterable=?, is_society_system_subject_unmasterable=? where lecture_id=?");
		lectureDataDeletePreparedStatement = connection.prepareStatement("delete from lecture where lecture_id=?");

		// �J�u�u�`�f�[�^�Ɋւ����͍ς�SQL�̐���
		allOpeningLectureDataSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture order by opening_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture where opening_lecture_id like \"???__\" order by opening_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureDataSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture where opening_lecture_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureDataInsertPreparedStatement = connection.prepareStatement("insert into opening_lecture values(?, ?, ?)");
		openingLectureDataUpdatePreparedStatement = connection.prepareStatement("update opening_lecture set opening_lecture_id=?, lecture_id=?, necessary_opening_lecture_id=? where opening_lecture_id=?");
		openingLectureDataDeletePreparedStatement = connection.prepareStatement("delete from opening_lecture where opening_lecture_id=?");

		// �J�u�u�`�S�������f�[�^�Ɋւ����͍ς�SQL�̐���
		allOpeningLectureLecturerDataSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture_lecturer order by opening_lecture_lecturer_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureLecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture_lecturer where opening_lecture_lecturer_id like \"?????__\" order by opening_lecture_lecturer_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureLecturerDataSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture_lecturer where opening_lecture_lecturer_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureLecturerDataInsertPreparedStatement = connection.prepareStatement("insert into opening_lecture_lecturer values(?, ?)");
		openingLectureLecturerDataUpdatePreparedStatement = connection.prepareStatement("update opening_lecture_lecturer set opening_lecture_lecturer_id=?, lecturer_id=? where opening_lecture_lecturer_id=?");
		openingLectureLecturerDataDeletePreparedStatement = connection.prepareStatement("delete from opening_lecture_lecturer where opening_lecture_lecturer_id=?");

		// �o�^�ς݊J�u�u�`�f�[�^�Ɋւ����͍ς�SQL�̐���
		allRegisteredOpeningLectureDataSelectPreparedStatement = connection.prepareStatement("select * from registered_opening_lecture order by registered_opening_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		registeredOpeningLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from registered_opening_lecture where registered_opening_lecture_id like \"?????___\" order by registered_opening_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		registeredOpeningLectureDataSelectPreparedStatement = connection.prepareStatement("select * from registered_opening_lecture where registered_opening_lecture_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		registeredOpeningLectureDataInsertPreparedStatement = connection.prepareStatement("insert into registered_opening_lecture values(?, ?)");
		registeredOpeningLectureDataUpdatePreparedStatement = connection.prepareStatement("update registered_opening_lecture set registered_opening_lecture_id=?, opening_lecture_id=? where registered_opening_lecture_id=?");
		registeredOpeningLectureDataDeletePreparedStatement = connection.prepareStatement("delete from registered_opening_lecture where registered_opening_lecture_id=?");

		// ���C�ςݍu�`�f�[�^�Ɋւ����͍ς�SQL�̐���
		allMasteredLectureDataSelectPreparedStatement = connection.prepareStatement("select * from mastered_lecture order by mastered_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		masteredLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from mastered_lecture where mastered_lecture_id like \"?????_______\" order by mastered_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		masteredLectureDataSelectPreparedStatement = connection.prepareStatement("select * from mastered_lecture where mastered_lecture_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		masteredLectureDataInsertPreparedStatement = connection.prepareStatement("insert into mastered_lecture values(?, ?, ?, ?)");
		masteredLectureDataUpdatePreparedStatement = connection.prepareStatement("update mastered_lecture set mastered_lecture_id=?, score=?, mastered_year=?, lecture_affiliation_number=? where mastered_lecture_id=?");
		masteredLectureDataDeletePreparedStatement = connection.prepareStatement("delete from mastered_lecture where mastered_lecture_id=?");
	}

	/**
	 * UtilityBean��ݒ肷��
	 * @param utility_bean UtilityBean
	 */
	public void setUtilityBean(UtilityBean utility_bean) {
		utilityBean = utility_bean;
	}

	/**
	 * �w�肳�ꂽ��͍ς�SQL�����s����
	 * @param prepared_statement ��͍ς�SQL
	 * @exception SQLException
	 */
	private synchronized void executeUpdate(PreparedStatement prepared_statement) throws SQLException {
		connection.setReadOnly(false); // �f�[�^�x�[�X��ǂݎ���p�������
		prepared_statement.executeUpdate(); // ��͍ς�SQL�̎��s
		connection.setReadOnly(true); // �f�[�^�x�[�X��ǂݎ���p�ɐݒ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u���Ɏw�肳�ꂽ�s�f�[�^��}������
	 * @param table_name �e�[�u����
	 * @param row_data �s�f�[�^
	 * @exception SQLException
	 */
	public synchronized void insert(String table_name, String[] row_data) throws SQLException {
		PreparedStatement prepared_statement = null; // ��͍ς�SQL���i�[

		// �w�肳�ꂽ�e�[�u�����Ȗڃe�[�u���ł���ꍇ
		if (table_name.equals("course")) {
			prepared_statement = courseDataInsertPreparedStatement; // �Ȗڃf�[�^��}�������͍ς�SQL��ݒ�
		// �u�`�����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture_affiliation")) {
			prepared_statement = lectureAffiliationDataInsertPreparedStatement; // �u�`�����f�[�^��}�������͍ς�SQL��ݒ�
		// �u���e�[�u���ł���ꍇ
		} else if (table_name.equals("chair")) {
			prepared_statement = chairDataInsertPreparedStatement; // �u���f�[�^��}�������͍ς�SQL��ݒ�
		// �w���e�[�u���ł���ꍇ
		} else if (table_name.equals("student")) {
			prepared_statement = studentDataInsertPreparedStatement; // �w���f�[�^��}�������͍ς�SQL��ݒ�
		// �����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecturer")) {
			prepared_statement = lecturerDataInsertPreparedStatement; // �����f�[�^��}�������͍ς�SQL��ݒ�
		// �u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture")) {
			prepared_statement = lectureDataInsertPreparedStatement; // �u�`�f�[�^��}�������͍ς�SQL��ݒ�
		// �J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = openingLectureDataInsertPreparedStatement; // �J�u�u�`�f�[�^��}�������͍ς�SQL��ݒ�
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataInsertPreparedStatement; // �J�u�u�`�S�������f�[�^��}�������͍ς�SQL��ݒ�
		// �o�^�ς݊J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataInsertPreparedStatement; // �o�^�ς݊J�u�u�`�f�[�^��}�������͍ς�SQL��ݒ�
		// ���C�ςݍu�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataInsertPreparedStatement; // ���C�ςݍu�`�f�[�^��}�������͍ς�SQL��ݒ�
		}

		// �s�f�[�^�̐ݒ�
		for (int i = 0; i < row_data.length; i++) {
			prepared_statement.setString(i + 1, row_data[i]);
		}

		executeUpdate(prepared_statement); // �s�f�[�^�̑}��
	}

	/**
	 * �w�肳�ꂽ�e�[�u���EID�Ɏw�肳�ꂽ�s�f�[�^���X�V����
	 * @param table_name �e�[�u����
	 * @param id ID
	 * @param row_data �s�f�[�^
	 * @exception SQLException
	 */
	public synchronized void update(String table_name, String id, String[] row_data) throws SQLException {
		PreparedStatement prepared_statement = null; // ��͍ς�SQL���i�[

		// �w�肳�ꂽ�e�[�u�����Ȗڃe�[�u���ł���ꍇ
		if (table_name.equals("course")) {
			prepared_statement = courseDataUpdatePreparedStatement; // �Ȗڃf�[�^���X�V�����͍ς�SQL��ݒ�
		// �u�`�����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture_affiliation")) {
			prepared_statement = lectureAffiliationDataUpdatePreparedStatement; // �u�`�����f�[�^���X�V�����͍ς�SQL��ݒ�
		// �u���e�[�u���ł���ꍇ
		} else if (table_name.equals("chair")) {
			prepared_statement = chairDataUpdatePreparedStatement; // �u���f�[�^���X�V�����͍ς�SQL��ݒ�
		// �w���e�[�u���ł���ꍇ
		} else if (table_name.equals("student")) {
			prepared_statement = studentDataUpdatePreparedStatement; // �w���f�[�^���X�V�����͍ς�SQL��ݒ�
		// �����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecturer")) {
			prepared_statement = lecturerDataUpdatePreparedStatement; // �����f�[�^���X�V�����͍ς�SQL��ݒ�
		// �u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture")) {
			prepared_statement = lectureDataUpdatePreparedStatement; // �u�`�f�[�^���X�V�����͍ς�SQL��ݒ�
		// �J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = openingLectureDataUpdatePreparedStatement; // �J�u�u�`�f�[�^���X�V�����͍ς�SQL��ݒ�
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataUpdatePreparedStatement; // �J�u�u�`�S�������f�[�^���X�V�����͍ς�SQL��ݒ�
		// �o�^�ς݊J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataUpdatePreparedStatement; // �o�^�ς݊J�u�u�`�f�[�^���X�V�����͍ς�SQL��ݒ�
		// ���C�ςݍu�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataUpdatePreparedStatement; // ���C�ςݍu�`�f�[�^���X�V�����͍ς�SQL��ݒ�
		}

		// �s�f�[�^�̐ݒ�
		for (int i = 0; i < row_data.length; i++) {
			prepared_statement.setString(i + 1, row_data[i]);
		}
		prepared_statement.setString(row_data.length + 1, id);

		executeUpdate(prepared_statement); // �s�f�[�^�̍X�V
	}

	/**
	 * �w�肳�ꂽ�e�[�u���EID�̍s���폜����
	 * @param table_name �e�[�u����
	 * @param id ID
	 * @exception SQLException
	 */
	public synchronized void delete(String table_name, String id) throws SQLException {
		PreparedStatement prepared_statement = null; // ��͍ς�SQL���i�[

		// �w�肳�ꂽ�e�[�u�����Ȗڃe�[�u���A�u�`�����e�[�u���A���͍u���e�[�u���ł���ꍇ
		if (table_name.equals("course") || table_name.equals("lecture_affiliation") || table_name.equals("chair")) {
			String[] lecture_ids = null; // �u�`ID���i�[

			// �w�肳�ꂽ�e�[�u�������Ȗڃe�[�u���ł���ꍇ
			if (table_name.equals("course")) {
				String[] lecture_affiliation_ids = getColumnDataArray("lecture_affiliation", 0, id); // �u�`����ID���i�[

				// �u�`����ID���擾�ł����ꍇ
				if (lecture_affiliation_ids != null) {
					// �e�f�[�^�̍폜
					for (int i = 0; i < lecture_affiliation_ids.length; i++) {
						delete("lecture_affiliation", lecture_affiliation_ids[i]);
					}
				// �擾�ł��Ȃ������ꍇ
				} else {
					lecture_ids = getColumnDataArray("lecture", 0, id + "00"); // �u�`ID�̎擾
				}

				prepared_statement = courseDataDeletePreparedStatement; // �Ȗڃf�[�^���폜�����͍ς�SQL�̎擾
			// �u�`�����e�[�u���ł���ꍇ
			} else if (table_name.equals("lecture_affiliation")) {
				// �w�肳�ꂽID�����Ȗڂ̂��̂ł���ꍇ
				if (id.substring(0, 1).equals("0")) {
					String lecture_affiliation_number = id.substring(1, 2); // �u�`�����ԍ����i�[
					int int_lecture_affiliation_number = Integer.parseInt(lecture_affiliation_number); // �u�`�����ԍ����i�[

					// �u�`�����ԍ����w�������ԍ��̗v�f�������������ꍇ
					if (int_lecture_affiliation_number < utilityBean.STUDENT_AFFILIATION_NUMBERS.length) {
						int year = Calendar.getInstance().get(Calendar.YEAR); // �N���i�[
						String[] years = utilityBean.getArray(year, year - 11); // �N���i�[

						// �e�N�̎w�肳�ꂽ�u�`�����ɏ�������w���f�[�^�����݂��邩�ǂ������`�F�b�N���A���݂���ꍇ�͍폜
						for (int i = 0; i < years.length; i++) {
							String[] student_ids = getColumnDataArray("student", 0, years[i].substring(2) + utilityBean.STUDENT_AFFILIATION_NUMBERS[int_lecture_affiliation_number]); // �w�Дԍ����i�[

							// �w�Дԍ����擾�ł����ꍇ
							if (student_ids != null) {
								// �e�w���f�[�^�̍폜
								for (int j = 0; j < student_ids.length; j++) {
									delete("student", student_ids[j]);
								}
							}
						}
					}

					String[] check_table_names = {"chair", "lecturer"}; // �`�F�b�N����e�[�u�������i�[

					// �`�F�b�N����e�[�u���ɍ폜����f�[�^�����݂���ꍇ�̍폜����
					for (int i = 0; i < check_table_names.length; i++) {
						String[] delete_data_ids = getColumnDataArray(check_table_names[i], 0, lecture_affiliation_number); // �폜����f�[�^��ID���i�[

						// �폜����f�[�^��ID���擾�ł����ꍇ
						if (delete_data_ids != null) {
							// �e�f�[�^�̍폜
							for (int j = 0; j < delete_data_ids.length; j++) {
								delete(check_table_names[i], delete_data_ids[j]);
							}
						}
					}
				// ���Ȗڂ̂��̂łȂ��ꍇ
				} else {
					lecture_ids = getColumnDataArray("lecture", 0, id + "0"); // �u�`ID�̎擾
				}

				prepared_statement = lectureAffiliationDataDeletePreparedStatement; // �u�`�����f�[�^���폜�����͍ς�SQL�̎擾
			// �u���e�[�u���ł���ꍇ
			} else if (table_name.equals("chair")) {
				lecture_ids = getColumnDataArray("lecture", 0, "0" + id); // �u�`ID�̎擾
				prepared_statement = chairDataDeletePreparedStatement; // �u���f�[�^���폜�����͍ς�SQL�̎擾
			}

			// �u�`ID���擾�ł����ꍇ
			if (lecture_ids != null) {
				// �e�u�`�f�[�^�̍폜
				for (int i = 0; i < lecture_ids.length; i++) {
					delete("lecture", lecture_ids[i]);
				}
			}
		// �w���e�[�u���ł���ꍇ
		} else if (table_name.equals("student")) {
			String[] check_table_names = {"registered_opening_lecture", "mastered_lecture"}; // �`�F�b�N����e�[�u�������i�[

			// �`�F�b�N����e�[�u���ɍ폜����f�[�^�����݂���ꍇ�̍폜����
			for (int i = 0; i < check_table_names.length; i++) {
				String[] delete_data_ids = getColumnDataArray(check_table_names[i], 0, id); // �폜����f�[�^��ID���i�[

				// �폜����f�[�^��ID���擾�ł����ꍇ
				if (delete_data_ids != null) {
					// �e�f�[�^�̍폜
					for (int j = 0; j < delete_data_ids.length; j++) {
						delete(check_table_names[i], delete_data_ids[j]);
					}
				}
			}

			prepared_statement = studentDataDeletePreparedStatement; // �w���f�[�^���폜�����͍ς�SQL�̎擾
		// �����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecturer")) {
			String[][] opening_lecture_lecturer_data = getRowsData("opening_lecture_lecturer"); // �J�u�u�`�S�������f�[�^���i�[

			// �J�u�u�`�S�������f�[�^���擾�ł����ꍇ
			if (opening_lecture_lecturer_data != null) {
				// �w�肳�ꂽ����ID�ɊY������J�u�u�`�S�������f�[�^�̍폜
				for (int i = 0; i < opening_lecture_lecturer_data.length; i++) {
					// ���݃A�N�Z�X���Ă���J�u�u�`�����f�[�^�̋���ID���폜���鋳���f�[�^�̋���ID�Ɠ������ꍇ
					if (opening_lecture_lecturer_data[i][1].equals(id)) {
						delete("opening_lecture_lecturer", opening_lecture_lecturer_data[i][0]); // �f�[�^�̍폜
					}
				}
			}

			prepared_statement = lecturerDataDeletePreparedStatement; // �����f�[�^���폜�����͍ς�SQL�̎擾
		// �u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture")) {
			String[][] opening_lecture_data = getRowsData("opening_lecture"); // �J�u�u�`�f�[�^���i�[

			// �J�u�u�`�f�[�^���擾�ł����ꍇ
			if (opening_lecture_data != null) {
				// �w�肳�ꂽ�u�`ID�ɊY������J�u�u�`�f�[�^�̍폜
				for (int i = 0; i < opening_lecture_data.length; i++) {
					// ���݃A�N�Z�X���Ă���J�u�u�`�f�[�^�����u�`ID���w�肳�ꂽ�u�`ID�Ɠ������ꍇ
					if (opening_lecture_data[i][1].equals(id)) {
						delete("opening_lecture", opening_lecture_data[i][0]); // �f�[�^�̍폜
					}
				}
			}

			prepared_statement = lectureDataDeletePreparedStatement; // �u�`�f�[�^���폜�����͍ς�SQL�̎擾
		// �J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture")) {
			String[] opening_lecture_lecturer_ids = getColumnDataArray("opening_lecture_lecturer", 0, id); // �J�u�u�`�S������ID���i�[

			// �J�u�u�`�S������ID���擾�ł����ꍇ
			if (opening_lecture_lecturer_ids != null) {
				// �e�J�u�u�`�S������ID�̍폜
				for (int i = 0; i < opening_lecture_lecturer_ids.length; i++) {
					delete("opening_lecture_lecturer", opening_lecture_lecturer_ids[i]);
				}
			}

			String[][] opening_lecture_data = getRowsData("opening_lecture"); // �J�u�u�`�f�[�^���i�[

			// �J�u�u�`�f�[�^���擾�ł����ꍇ
			if (opening_lecture_data != null) {
				// �w�肳�ꂽ�J�u�u�`���K�v�ȊJ�u�u�`�Ɛݒ肳��Ă���J�u�u�`�f�[�^�̍X�V
				for (int i = 0; i < opening_lecture_data.length; i++) {
					// ���݃A�N�Z�X���Ă���J�u�u�`�f�[�^�ɐݒ肳��Ă���K�v�J�u�u�`ID���w�肳�ꂽID�Ɠ������ꍇ
					if (opening_lecture_data[i][2] != null && opening_lecture_data[i][2].equals(id)) {
						opening_lecture_data[i][2] = null; // �K�v�J�u�u�`ID��null�ɐݒ�
						update("opening_lecture", opening_lecture_data[i][0], opening_lecture_data[i]); // �f�[�^�̍X�V
					}
				}
			}

			prepared_statement = openingLectureDataDeletePreparedStatement; // �J�u�u�`�f�[�^���폜�����͍ς�SQL�̎擾
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataDeletePreparedStatement; // �J�u�u�`�S�������f�[�^���폜�����͍ς�SQL�̎擾
		// �o�^�ς݊J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataDeletePreparedStatement; // �o�^�ς݊J�u�u�`�f�[�^���폜�����͍ς�SQL�̎擾
		// ���C�ςݍu�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataDeletePreparedStatement; // ���C�ςݍu�`�f�[�^���폜�����͍ς�SQL�̎擾
		}

		prepared_statement.setString(1, id); // �폜���s���s��ID�̐ݒ�
		executeUpdate(prepared_statement); // �s�̍폜
	}

	/**
	 * �w�肳�ꂽ�e�[�u���̑S�s�̌��ʏW����Ԃ�
	 * @param table_name �e�[�u����
	 * @return ���ʏW��
	 * @exception SQLException
	 */
	private synchronized ResultSet getAllRowsResultSet(String table_name) throws SQLException {
		PreparedStatement prepared_statement = null; // ��͍ς�SQL���i�[

		// �w�肳�ꂽ�e�[�u�����Ǘ��҃e�[�u���ł���ꍇ
		if (table_name.equals("administrator")) {
			prepared_statement = allAdministratorDataSelectPreparedStatement; // �S�Ă̊Ǘ��҃f�[�^��I�������͍ς�SQL�̎擾
		// �Ȗڃe�[�u���ł���ꍇ
		} else if (table_name.equals("course")) {
			prepared_statement = allCourseDataSelectPreparedStatement; // �S�ẲȖڃf�[�^��I�������͍ς�SQL�̎擾
		// �u�`�����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture_affiliation")) {
			prepared_statement = allLectureAffiliationDataSelectPreparedStatement; // �S�Ă̍u�`�����f�[�^��I�������͍ς�SQL�̎擾
		// �u���e�[�u���ł���ꍇ
		} else if (table_name.equals("chair")) {
			prepared_statement = allChairDataSelectPreparedStatement; // �S�Ă̍u���f�[�^��I�������͍ς�SQL�̎擾
		// �w���e�[�u���ł���ꍇ
		} else if (table_name.equals("student")) {
			prepared_statement = allStudentDataSelectPreparedStatement; // �S�Ă̊w���f�[�^��I�������͍ς�SQL�̎擾
		// �����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecturer")) {
			prepared_statement = allLecturerDataSelectPreparedStatement; // �S�Ă̋����f�[�^��I�������͍ς�SQL�̎擾
		// �u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture")) {
			prepared_statement = allLectureDataSelectPreparedStatement; // �S�Ă̍u�`�f�[�^��I�������͍ς�SQL�̎擾
		// �J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = allOpeningLectureDataSelectPreparedStatement; // �S�Ă̊J�u�u�`�f�[�^��I�������͍ς�SQL�̎擾
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = allOpeningLectureLecturerDataSelectPreparedStatement; // �S�Ă̊J�u�u�`�S�������f�[�^��I�������͍ς�SQL�̎擾
		// �o�^�ς݊J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = allRegisteredOpeningLectureDataSelectPreparedStatement; // �S�Ă̓o�^�ς݊J�u�u�`�f�[�^��I�������͍ς�SQL�̎擾
		// ���C�ςݍu�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = allMasteredLectureDataSelectPreparedStatement; // �S�Ă̗��C�ςݍu�`�f�[�^��I�������͍ς�SQL�̎擾
		}

		return prepared_statement.executeQuery(); // ��͍ς�SQL�̌��ʏW����Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u���E�f�[�^�����ԍ��̍s�̌��ʏW����Ԃ�
	 * @param table_name �e�[�u����
	 * @param data_affiliation_number �f�[�^�����ԍ�
	 * @return ���ʏW��
	 * @exception SQLException
	 */
	private synchronized ResultSet getRowsResultSet(String table_name, String data_affiliation_number) throws SQLException {
		PreparedStatement prepared_statement = null; // ��͍ς�SQL���i�[

		// �w�肳�ꂽ�e�[�u�����u�`�����e�[�u���ł���ꍇ
		if (table_name.equals("lecture_affiliation")) {
			prepared_statement = lectureAffiliationDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // �w�肳�ꂽ�f�[�^�����ԍ��̍u�`�����f�[�^��I�������͍ς�SQL�̎擾
		// �u���e�[�u���ł���ꍇ
		} else if (table_name.equals("chair")) {
			prepared_statement = chairDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // �w�肳�ꂽ�f�[�^�����ԍ��̍u���f�[�^��I�������͍ς�SQL�̎擾
		// �w���e�[�u���ł���ꍇ
		} else if (table_name.equals("student")) {
			prepared_statement = studentDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // �w�肳�ꂽ�f�[�^�����ԍ��̊w���f�[�^��I�������͍ς�SQL�̎擾
		// �����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecturer")) {
			prepared_statement = lecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // �w�肳�ꂽ�f�[�^�����ԍ��̋����f�[�^��I�������͍ς�SQL�̎擾
		// �u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture")) {
			prepared_statement = lectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // �w�肳�ꂽ�f�[�^�����ԍ��̍u�`�f�[�^��I�������͍ς�SQL�̎擾
		// �J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = openingLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // �w�肳�ꂽ�f�[�^�����ԍ��̊J�u�u�`�f�[�^��I�������͍ς�SQL�̎擾
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // �w�肳�ꂽ�f�[�^�����ԍ��̊J�u�u�`�S�������f�[�^��I�������͍ς�SQL�̎擾
		// �o�^�ς݊J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // �w�肳�ꂽ�f�[�^�����ԍ��̓o�^�ς݊J�u�u�`�f�[�^��I�������͍ς�SQL�̎擾
		// ���C�ςݍu�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // �w�肳�ꂽ�f�[�^�����ԍ��̗��C�ςݍu�`�f�[�^��I�������͍ς�SQL�̎擾
		}

		// �w�肳�ꂽ�e�[�u�����w���e�[�u���ł���ꍇ
		if (table_name.equals("student")) {
			String year = data_affiliation_number.substring(0, 2); // �N���i�[
			int subject_affiliation_number = Integer.parseInt(data_affiliation_number.substring(2)); // �w�ȏ����ԍ����i�[
			int end_subject_affiliation_number; // �w�ȏ����ԍ��̏I�[���i�[

			// �w�ȏ����ԍ��ɂ�镪��
			switch (subject_affiliation_number) {
				// �w�肳�ꂽ�w�ȏ����ԍ����o�ϊw�Ȃ̂��̂ł���ꍇ
				case 1:
					end_subject_affiliation_number = 3; // �w�ȏ����ԍ��̏I�[��3��ݒ�
					break; // �u���b�N���甲����
				// ��ƌo�c�w�Ȃ̂��̂ł���ꍇ
				case 5:
					end_subject_affiliation_number = 6; // �w�ȏ����ԍ��̏I�[��6��ݒ�
					break; // �u���b�N���甲����
				// ����ȊO�̂��̂ł���ꍇ
				default:
					end_subject_affiliation_number = subject_affiliation_number; // �w�ȏ����ԍ��̏I�[�Ɋw�ȏ����ԍ���ݒ�
					break; // �u���b�N���甲����
			}

			// �I��͈͂̐ݒ�
			prepared_statement.setString(1, year + subject_affiliation_number + "00");
			prepared_statement.setString(2, year + end_subject_affiliation_number + "99");
		// ���̑��̃e�[�u�����ł���ꍇ
		} else {
			// �f�[�^�����ԍ��̐ݒ�
			for (int i = 0; i < data_affiliation_number.length(); i++) {
				prepared_statement.setInt(i + 1, Integer.parseInt(data_affiliation_number.substring(i, i + 1)));
			}
		}

		return prepared_statement.executeQuery(); // ��͍ς�SQL�̌��ʏW����Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u���EID�̍s�̌��ʏW����Ԃ�
	 * @param table_name �e�[�u����
	 * @param id ID
	 * @return ���ʏW��
	 * @exception SQLException
	 */
	private synchronized ResultSet getRowResultSet(String table_name, String id) throws SQLException {
		PreparedStatement prepared_statement = null; // ��͍ς�SQL���i�[

		// �w�肳�ꂽ�e�[�u�����Ǘ��҃e�[�u���ł���ꍇ
		if (table_name.equals("administrator")) {
			prepared_statement = administratorDataSelectPreparedStatement; // �Ǘ��҃f�[�^��I�������͍ς�SQL�̎擾
		// �Ȗڃe�[�u���ł���ꍇ
		} else if (table_name.equals("course")) {
			prepared_statement = courseDataSelectPreparedStatement; // �Ȗڃf�[�^��I�������͍ς�SQL�̎擾
		// �u�`�����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture_affiliation")) {
			prepared_statement = lectureAffiliationDataSelectPreparedStatement; // �u�`�����f�[�^��I�������͍ς�SQL�̎擾
		// �u���e�[�u���ł���ꍇ
		} else if (table_name.equals("chair")) {
			prepared_statement = chairDataSelectPreparedStatement; // �u�`�����f�[�^��I�������͍ς�SQL�̎擾
		// �w���e�[�u���ł���ꍇ
		} else if (table_name.equals("student")) {
			prepared_statement = studentDataSelectPreparedStatement; // �w���f�[�^��I�������͍ς�SQL�̎擾
		// �����e�[�u���ł���ꍇ
		} else if (table_name.equals("lecturer")) {
			prepared_statement = lecturerDataSelectPreparedStatement; // �����f�[�^��I�������͍ς�SQL�̎擾
		// �u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("lecture")) {
			prepared_statement = lectureDataSelectPreparedStatement; // �u�`�f�[�^��I�������͍ς�SQL�̎擾
		// �J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = openingLectureDataSelectPreparedStatement; // �J�u�u�`�f�[�^��I�������͍ς�SQL�̎擾
		// �J�u�u�`�S�������e�[�u���ł���ꍇ
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataSelectPreparedStatement; // �J�u�u�`�S�������f�[�^��I�������͍ς�SQL�̎擾
		// �o�^�ς݊J�u�u�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataSelectPreparedStatement; // �o�^�ς݊J�u�u�`�f�[�^��I�������͍ς�SQL�̎擾
		// ���C�ςݍu�`�e�[�u���ł���ꍇ
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataSelectPreparedStatement; // ���C�ςݍu�`�f�[�^��I�������͍ς�SQL�̎擾
		}

		prepared_statement.setString(1, id); // ID�̐ݒ�
		ResultSet result_set = prepared_statement.executeQuery(); // ���ʏW�����i�[
		return result_set; // ���ʏW����Ԃ�
	}

	/**
	 * �w�肳�ꂽ���ʏW���̍s����Ԃ�
	 * @param result_set ���ʏW��
	 * @return �s��
	 * @exception SQLException
	 */
	private synchronized int getRowCount(ResultSet result_set) throws SQLException {
		result_set.last(); // ���ʏW���̃|�C���^���Ō�̗v�f�Ɉړ�
		int row_count = result_set.getRow(); // �s�����i�[
		result_set.first(); // ���ʏW���̃|�C���^���ŏ��̗v�f�Ɉړ�
		return row_count; // �s����Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u���̍s�f�[�^��Ԃ�
	 * @param table_name �e�[�u����
	 * @return �s�f�[�^
	 * @exception SQLException
	 */
	public synchronized String[][] getRowsData(String table_name) throws SQLException {
		return getRowsData(table_name, null);
	}

	/**
	 * �w�肳�ꂽ�e�[�u���E�f�[�^�����ԍ��̍s�f�[�^��Ԃ�
	 * @param table_name �e�[�u����
	 * @param data_affiliation_number �f�[�^�����ԍ�
	 * @return �s�f�[�^
	 * @exception SQLException
	 */
	public synchronized String[][] getRowsData(String table_name, String data_affiliation_number) throws SQLException {
		ResultSet result_set = null; // ��͍ς�SQL�̌��ʏW�����i�[

		// �f�[�^�����ԍ����w�肳��Ă��Ȃ��ꍇ
		if (data_affiliation_number == null) {
			result_set = getAllRowsResultSet(table_name); // �w�肳�ꂽ�e�[�u���S�s�̌��ʏW���̎擾
		// �w�肳��Ă���ꍇ
		} else {
			result_set = getRowsResultSet(table_name, data_affiliation_number); // �w�肳�ꂽ�e�[�u���E�f�[�^�����ԍ��ɊY������s�̌��ʏW���̎擾
		}

		String[][] rows_data = null; // �s�f�[�^���i�[

		// �f�[�^���擾�ł����ꍇ
		if (result_set.next()) {
			rows_data = new String[getRowCount(result_set)][result_set.getMetaData().getColumnCount()]; // �s�f�[�^�̗v�f���̐ݒ�

			// �s�f�[�^�̎擾
			for (int i = 0; i < rows_data.length; i++) {
				for (int j = 0; j < rows_data[i].length; j++) {
					rows_data[i][j] = result_set.getString(j + 1);
				}

				result_set.next(); // ���ʏW���̃|�C���^�̈ړ�
			}
		}

		result_set.close(); // ��͍ς�SQL�̌��ʏW���̃N���[�Y
		return rows_data; // �s�f�[�^��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u���EID�̍s�f�[�^��Ԃ�
	 * @param table_name �e�[�u����
	 * @param id ID
	 * @return �s�f�[�^
	 * @exception SQLException
	 */
	public synchronized String[] getRowData(String table_name, String id) throws SQLException {
		ResultSet result_set = getRowResultSet(table_name, id); // ���ʏW�����i�[
		String[] row_data = null; // �s�f�[�^���i�[

		// ���ʏW�����擾�ł����ꍇ
		if (result_set.next()) {
			row_data = new String[result_set.getMetaData().getColumnCount()]; // �s�f�[�^���i�[

			// �s�f�[�^�̎擾
			for (int i = 0; i < row_data.length; i++) {
				row_data[i] = result_set.getString(i + 1);
			}
		}

		result_set.close(); // ��͍ς�SQL�̌��ʏW���̃N���[�Y
		return row_data; // �s�f�[�^��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u���E��ԍ��̗�f�[�^�z���Ԃ�
	 * @param table_name �e�[�u����
	 * @param column_number ��ԍ�
	 * @return ��f�[�^�z��
	 * @exception SQLException
	 */
	public synchronized String[] getColumnDataArray(String table_name, int column_number) throws SQLException {
		return getColumnDataArray(table_name, "" + column_number, null);
	}

	/**
	 * �w�肳�ꂽ�e�[�u���E��ԍ��E�f�[�^�����ԍ��̗�f�[�^�z���Ԃ�
	 * @param table_name �e�[�u����
	 * @param column_number ��ԍ�
	 * @param data_affiliation_number �f�[�^�����ԍ�
	 * @return ��f�[�^�z��
	 * @exception SQLException
	 */
	public synchronized String[] getColumnDataArray(String table_name, int column_number, String data_affiliation_number) throws SQLException {
		return getColumnDataArray(table_name, "" + column_number, data_affiliation_number);
	}

	/**
	 * �w�肳�ꂽ�e�[�u���E�񖼂̗�f�[�^�z���Ԃ�
	 * @param table_name �e�[�u����
	 * @param column_name ��
	 * @return ��f�[�^�z��
	 * @exception SQLException
	 */
	public synchronized String[] getColumnDataArray(String table_name, String column_name) throws SQLException {
		return getColumnDataArray(table_name, column_name, null);
	}

	/**
	 * �w�肳�ꂽ�e�[�u���E�񖼁E�f�[�^�����ԍ��̗�f�[�^�z���Ԃ�
	 * @param table_name �e�[�u����
	 * @param column_name ��
	 * @param data_affiliation_number �f�[�^�����ԍ�
	 * @return ��f�[�^�z��
	 * @exception SQLException
	 */
	public synchronized String[] getColumnDataArray(String table_name, String column_name, String data_affiliation_number) throws SQLException {
		ResultSet result_set = null; // ��͍ς�SQL�̌��ʏW�����i�[

		// �f�[�^�����ԍ����w�肳��Ă��Ȃ��ꍇ
		if (data_affiliation_number == null) {
			result_set = getAllRowsResultSet(table_name); // �w�肳�ꂽ�e�[�u���S�s�̌��ʏW���̎擾
		// �w�肳��Ă���ꍇ
		} else {
			result_set = getRowsResultSet(table_name, data_affiliation_number); // �w�肳�ꂽ�e�[�u���E�f�[�^�����ԍ��ɊY������s�̌��ʏW���̎擾
		}

		String[] column_data_array = null; // ��f�[�^�z����i�[

		// ���ʏW�����擾�ł����ꍇ
		if (result_set.next()) {
			column_data_array = new String[getRowCount(result_set)]; // ���ʏW���̍s�����f�[�^�z��̗v�f���ɐݒ�

			// ��f�[�^�z��̗v�f�̎擾
			for (int i = 0; i < column_data_array.length; i++) {
				// �w�肳�ꂽ�񖼂������ł���ꍇ
				if (!utilityBean.isCharacterIncluded(column_name)) {
					column_data_array[i] = result_set.getString(Integer.parseInt(column_name) + 1);
				// �����łȂ��ꍇ
				} else {
					column_data_array[i] = result_set.getString(column_name);
				}

				result_set.next(); // ���ʏW���̃|�C���^�̈ړ�
			}
		}

		result_set.close(); // ��͍ς�SQL�̌��ʏW���̃N���[�Y
		return column_data_array; // ��f�[�^�z���Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u���E��ԍ��EID�̗�f�[�^��Ԃ�
	 * @param table_name �e�[�u����
	 * @param column_number ��ԍ�
	 * @param id ID
	 * @return ��f�[�^
	 * @exception SQLException
	 */
	public synchronized String getColumnData(String table_name, int column_number, String id) throws SQLException {
		return getColumnData(table_name, "" + column_number, id);
	}

	/**
	 * �w�肳�ꂽ�e�[�u���E�񖼁EID�̗�f�[�^��Ԃ�
	 * @param table_name �e�[�u����
	 * @param column_name ��
	 * @param id ID
	 * @return ��f�[�^
	 * @exception SQLException
	 */
	public synchronized String getColumnData(String table_name, String column_name, String id) throws SQLException {
		ResultSet result_set = getRowResultSet(table_name, id); // ���ʏW�����i�[
		String column_data = null; // ��f�[�^���i�[

		// ���ʏW�����擾�ł����ꍇ
		if (result_set.next()) {
			// �w�肳�ꂽ�񖼂������ł���ꍇ
			if (!utilityBean.isCharacterIncluded(column_name)) {
				column_data = result_set.getString(Integer.parseInt(column_name) + 1); // ��f�[�^�̎擾
			// �����łȂ��ꍇ
			} else {
				column_data = result_set.getString(column_name); // ��f�[�^�̎擾
			}
		}

		result_set.close(); // ���ʏW���̃N���[�Y
		return column_data; // ��f�[�^��Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u���̗񖼂�Ԃ�
	 * @param table_name �e�[�u����
	 * @return ��
	 * @exception SQLException
	 */
	public String[] getColumnNames(String table_name) throws SQLException {
		ResultSet result_set = getAllRowsResultSet(table_name); // ���ʏW�����i�[
		ResultSetMetaData result_set_meta_data = result_set.getMetaData(); // ���ʏW���̃��^�f�[�^���i�[
		result_set.close(); // ���ʏW���̃N���[�Y
		String[] column_names = new String[result_set_meta_data.getColumnCount()]; // �񖼂��i�[

		// �񖼂̎擾
		for(int i = 0; i < column_names.length; i++) {
			column_names[i] = result_set_meta_data.getColumnName(i + 1);
		}

		return column_names; // �񖼂�Ԃ�
	}

	/**
	 * �w�肳�ꂽ�e�[�u����ID�̌�����Ԃ�
	 * @param table_name �e�[�u����
	 * @return ID�̌���
	 * @exception SQLException
	 */
	public int getIDLength(String table_name) throws SQLException {
		ResultSet result_set = connection.getMetaData().getColumns("lecture", "%", "%", table_name + "_id"); // ID�̗���̎擾
		result_set.first(); // �ŏ��̗v�f�ւ̈ړ�
		int id_length = Integer.parseInt(result_set.getString("CHAR_OCTET_LENGTH")); // ID�̌����̎擾
		result_set.close(); // ID�̗���̃N���[�Y
		return id_length; // ID�̌�����Ԃ�
	}
}
