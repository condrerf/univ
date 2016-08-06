// パッケージの宣言
package bean;

// Beanの動作に必要なパッケージのインポート
import java.io.Serializable;

// メソッド内の処理で利用するパッケージのインポート
import java.sql.*;
import java.util.Calendar;

/**
 * データベースに関する処理を提供するBean
 * @author Ryo Fukushima
 */
public class DatabaseBean implements Serializable {
	/** UtilityBeanを格納 */
	private UtilityBean utilityBean = null;

	/** データベースへのコネクションを格納 */
	private Connection connection = null;

	/** 全ての管理者データを選択する解析済みSQLを格納 */
	private PreparedStatement allAdministratorDataSelectPreparedStatement = null;

	/** 管理者データを選択する解析済みSQLを格納 */
	private PreparedStatement administratorDataSelectPreparedStatement = null;

	/** 全ての科目データを選択する解析済みSQLを格納 */
	private PreparedStatement allCourseDataSelectPreparedStatement = null;

	/** 科目データを選択する解析済みSQLを格納 */
	private PreparedStatement courseDataSelectPreparedStatement = null;

	/** 科目データを挿入する解析済みSQLを格納 */
	private PreparedStatement courseDataInsertPreparedStatement = null;

	/** 科目データを更新する解析済みSQLを格納 */
	private PreparedStatement courseDataUpdatePreparedStatement = null;

	/** 科目データを削除する解析済みSQLを格納 */
	private PreparedStatement courseDataDeletePreparedStatement = null;

	/** 全ての講義所属データを選択する解析済みSQLを格納 */
	private PreparedStatement allLectureAffiliationDataSelectPreparedStatement = null;

	/** 指定されたデータ所属番号の講義所属データを選択する解析済みSQLを格納 */
	private PreparedStatement lectureAffiliationDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** 講義所属データを選択する解析済みSQLを格納 */
	private PreparedStatement lectureAffiliationDataSelectPreparedStatement = null;

	/** 講義所属データを挿入する解析済みSQLを格納 */
	private PreparedStatement lectureAffiliationDataInsertPreparedStatement = null;

	/** 講義所属データを更新する解析済みSQLを格納 */
	private PreparedStatement lectureAffiliationDataUpdatePreparedStatement = null;

	/** 講義所属データを削除する解析済みSQLを格納 */
	private PreparedStatement lectureAffiliationDataDeletePreparedStatement = null;

	/** 全ての講座データを選択する解析済みSQLを格納 */
	private PreparedStatement allChairDataSelectPreparedStatement = null;

	/** 指定されたデータ所属番号の講座データを選択する解析済みSQLを格納 */
	private PreparedStatement chairDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** 講座データを選択する解析済みSQLを格納 */
	private PreparedStatement chairDataSelectPreparedStatement = null;

	/** 講座データを挿入する解析済みSQLを格納 */
	private PreparedStatement chairDataInsertPreparedStatement = null;

	/** 講座データを更新する解析済みSQLを格納 */
	private PreparedStatement chairDataUpdatePreparedStatement = null;

	/** 講座データを削除する解析済みSQLを格納 */
	private PreparedStatement chairDataDeletePreparedStatement = null;

	/** 全ての学生データを選択する解析済みSQLを格納 */
	private PreparedStatement allStudentDataSelectPreparedStatement = null;

	/** 指定されたデータ所属番号の学生データを選択する解析済みSQLを格納 */
	private PreparedStatement studentDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** 学生データを選択する解析済みSQLを格納 */
	private PreparedStatement studentDataSelectPreparedStatement = null;

	/** 学生データを挿入する解析済みSQLを格納 */
	private PreparedStatement studentDataInsertPreparedStatement = null;

	/** 学生データを更新する解析済みSQLを格納 */
	private PreparedStatement studentDataUpdatePreparedStatement = null;

	/** 学生データを削除する解析済みSQLを格納 */
	private PreparedStatement studentDataDeletePreparedStatement = null;

	/** 全ての教官データを選択する解析済みSQLを格納 */
	private PreparedStatement allLecturerDataSelectPreparedStatement = null;

	/** 指定されたデータ所属番号の教官データを選択する解析済みSQLを格納 */
	private PreparedStatement lecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** 教官データを選択する解析済みSQLを格納 */
	private PreparedStatement lecturerDataSelectPreparedStatement = null;

	/** 教官データを挿入する解析済みSQLを格納 */
	private PreparedStatement lecturerDataInsertPreparedStatement = null;

	/** 教官データを更新する解析済みSQLを格納 */
	private PreparedStatement lecturerDataUpdatePreparedStatement = null;

	/** 教官データを削除する解析済みSQLを格納 */
	private PreparedStatement lecturerDataDeletePreparedStatement = null;

	/** 全ての講義データを選択する解析済みSQLを格納 */
	private PreparedStatement allLectureDataSelectPreparedStatement = null;

	/** 指定されたデータ所属番号の講義データを選択する解析済みSQLを格納 */
	private PreparedStatement lectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** 講義データを選択する解析済みSQLを格納 */
	private PreparedStatement lectureDataSelectPreparedStatement = null;

	/** 講義データを挿入する解析済みSQLを格納 */
	private PreparedStatement lectureDataInsertPreparedStatement = null;

	/** 講義データを更新する解析済みSQLを格納 */
	private PreparedStatement lectureDataUpdatePreparedStatement = null;

	/** 講義データを削除する解析済みSQLを格納 */
	private PreparedStatement lectureDataDeletePreparedStatement = null;

	/** 全ての開講講義データを選択する解析済みSQLを格納 */
	private PreparedStatement allOpeningLectureDataSelectPreparedStatement = null;

	/** 指定されたデータ所属番号の開講講義データを選択する解析済みSQLを格納 */
	private PreparedStatement openingLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** 開講講義データを選択する解析済みSQLを格納 */
	private PreparedStatement openingLectureDataSelectPreparedStatement = null;

	/** 開講講義データを挿入する解析済みSQLを格納 */
	private PreparedStatement openingLectureDataInsertPreparedStatement = null;

	/** 開講講義データを更新する解析済みSQLを格納 */
	private PreparedStatement openingLectureDataUpdatePreparedStatement = null;

	/** 開講講義データを削除する解析済みSQLを格納 */
	private PreparedStatement openingLectureDataDeletePreparedStatement = null;

	/** 全ての開講講義担当教官データを選択する解析済みSQLを格納 */
	private PreparedStatement allOpeningLectureLecturerDataSelectPreparedStatement = null;

	/** 指定されたデータ所属番号の開講講義担当教官データを選択する解析済みSQLを格納 */
	private PreparedStatement openingLectureLecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** 開講講義担当教官データを選択する解析済みSQLを格納 */
	private PreparedStatement openingLectureLecturerDataSelectPreparedStatement = null;

	/** 開講講義担当教官データを挿入する解析済みSQLを格納 */
	private PreparedStatement openingLectureLecturerDataInsertPreparedStatement = null;

	/** 開講講義担当教官データを更新する解析済みSQLを格納 */
	private PreparedStatement openingLectureLecturerDataUpdatePreparedStatement = null;

	/** 開講講義担当教官データを削除する解析済みSQLを格納 */
	private PreparedStatement openingLectureLecturerDataDeletePreparedStatement = null;

	/** 全ての登録済み開講講義データを選択する解析済みSQLを格納 */
	private PreparedStatement allRegisteredOpeningLectureDataSelectPreparedStatement = null;

	/** 指定されたデータ所属番号の登録済み開講講義データを選択する解析済みSQLを格納 */
	private PreparedStatement registeredOpeningLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** 登録済み開講講義データを選択する解析済みSQLを格納 */
	private PreparedStatement registeredOpeningLectureDataSelectPreparedStatement = null;

	/** 登録済み開講講義データを挿入する解析済みSQLを格納 */
	private PreparedStatement registeredOpeningLectureDataInsertPreparedStatement = null;

	/** 登録済み開講講義データを更新する解析済みSQLを格納 */
	private PreparedStatement registeredOpeningLectureDataUpdatePreparedStatement = null;

	/** 登録済み開講講義データを削除する解析済みSQLを格納 */
	private PreparedStatement registeredOpeningLectureDataDeletePreparedStatement = null;

	/** 全ての履修済み講義データを選択する解析済みSQLを格納 */
	private PreparedStatement allMasteredLectureDataSelectPreparedStatement = null;

	/** 履修済み講義データを選択する解析済みSQLを格納 */
	private PreparedStatement masteredLectureDataSelectPreparedStatement = null;

	/** 指定されたデータ所属番号の履修済み講義データを選択する解析済みSQLを格納 */
	private PreparedStatement masteredLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = null;

	/** 履修済み講義データを挿入する解析済みSQLを格納 */
	private PreparedStatement masteredLectureDataInsertPreparedStatement = null;

	/** 履修済み講義データを更新する解析済みSQLを格納 */
	private PreparedStatement masteredLectureDataUpdatePreparedStatement = null;

	/** 履修済み講義データを削除する解析済みSQLを格納 */
	private PreparedStatement masteredLectureDataDeletePreparedStatement = null;

	/**
	 * コンストラクタ
	 * @exception ClassNotFoundException
	 * @exception SQLException
	 */
	public DatabaseBean() throws ClassNotFoundException, SQLException {
		Class.forName("org.gjt.mm.mysql.Driver"); // MySQL用のJDBCドライバのロード
		connection = DriverManager.getConnection("jdbc:mysql://localhost/lecture?user=admin&password=admin&useUnicode=true&characterEncoding=Shift_JIS"); // コネクションの確立
		connection.setReadOnly(true); // データベースを読み取り専用に設定

		// 管理者データに関する解析済みSQLの生成
		allAdministratorDataSelectPreparedStatement = connection.prepareStatement("select * from administrator");
		administratorDataSelectPreparedStatement = connection.prepareStatement("select * from administrator where administrator_id=?");

		// 科目データに関する解析済みSQLの生成
		allCourseDataSelectPreparedStatement = connection.prepareStatement("select * from course order by course_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		courseDataSelectPreparedStatement = connection.prepareStatement("select * from course where course_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		courseDataInsertPreparedStatement = connection.prepareStatement("insert into course values(?, ?)");
		courseDataUpdatePreparedStatement = connection.prepareStatement("update course set course_id=?, course_name=? where course_id=?");
		courseDataDeletePreparedStatement = connection.prepareStatement("delete from course where course_id=?");

		// 講義所属データに関する解析済みSQLの生成
		allLectureAffiliationDataSelectPreparedStatement = connection.prepareStatement("select * from lecture_affiliation order by lecture_affiliation_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureAffiliationDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from lecture_affiliation where lecture_affiliation_id like \"?_\" order by lecture_affiliation_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureAffiliationDataSelectPreparedStatement = connection.prepareStatement("select * from lecture_affiliation where lecture_affiliation_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureAffiliationDataInsertPreparedStatement = connection.prepareStatement("insert into lecture_affiliation values(?, ?)");
		lectureAffiliationDataUpdatePreparedStatement = connection.prepareStatement("update lecture_affiliation set lecture_affiliation_id=?, lecture_affiliation_name=? where lecture_affiliation_id=?");
		lectureAffiliationDataDeletePreparedStatement = connection.prepareStatement("delete from lecture_affiliation where lecture_affiliation_id=?");

		// 講座データに関する解析済みSQLの生成
		allChairDataSelectPreparedStatement = connection.prepareStatement("select * from chair order by chair_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		chairDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from chair where chair_id like \"?_\" order by chair_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		chairDataSelectPreparedStatement = connection.prepareStatement("select * from chair where chair_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		chairDataInsertPreparedStatement = connection.prepareStatement("insert into chair values(?, ?)");
		chairDataUpdatePreparedStatement = connection.prepareStatement("update chair set chair_id=?, chair_name=? where chair_id=?");
		chairDataDeletePreparedStatement = connection.prepareStatement("delete from chair where chair_id=?");

		// 学生データに関する解析済みSQLの生成
		allStudentDataSelectPreparedStatement = connection.prepareStatement("select * from student order by student_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		studentDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from student where student_id between ? and ? order by student_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		studentDataSelectPreparedStatement = connection.prepareStatement("select * from student where student_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		studentDataInsertPreparedStatement = connection.prepareStatement("insert into student values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		studentDataUpdatePreparedStatement = connection.prepareStatement("update student set student_id=?, student_name=?, password=?, birthday=?, nationality_number=?, basic_seminar_lecturer_id=?, infomation_literacy_class=?, health_and_life_class=?, sports_science_i_class=?, english_i_class=?, english_ii_h_class=?, english_ii_a_class=?, second_foreign_language_id=?, second_foreign_language_class=?, seminar_lecturer_id=? where student_id=?");
		studentDataDeletePreparedStatement = connection.prepareStatement("delete from student where student_id=?");

		// 教官データに関する解析済みSQLの生成
		allLecturerDataSelectPreparedStatement = connection.prepareStatement("select * from lecturer order by lecturer_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from lecturer where lecturer_id like \"?___\" order by lecturer_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lecturerDataSelectPreparedStatement = connection.prepareStatement("select * from lecturer where lecturer_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lecturerDataInsertPreparedStatement = connection.prepareStatement("insert into lecturer values(?, ?)");
		lecturerDataUpdatePreparedStatement = connection.prepareStatement("update lecturer set lecturer_id=?, lecturer_name=? where lecturer_id=?");
		lecturerDataDeletePreparedStatement = connection.prepareStatement("delete from lecturer where lecturer_id=?");

		// 講義データに関する解析済みSQLの生成
		allLectureDataSelectPreparedStatement = connection.prepareStatement("select * from lecture order by lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from lecture where lecture_id like \"???____\" order by lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureDataSelectPreparedStatement = connection.prepareStatement("select * from lecture where lecture_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		lectureDataInsertPreparedStatement = connection.prepareStatement("insert into lecture values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		lectureDataUpdatePreparedStatement = connection.prepareStatement("update lecture set lecture_id=?, lecture_name=?, credit=?, day_master_year_number=?, night_master_year_number=?, day_masterable_year_number=?, night_masterable_year_number=?, start_masterable_year=?, end_masterable_year=?, is_economy_subject_unmasterable=?, is_finance_subject_unmasterable=?, is_enterprise_management_subject_unmasterable=?, is_accounts_information_subject_unmasterable=?, is_information_management_subject_unmasterable=?, is_society_system_subject_unmasterable=? where lecture_id=?");
		lectureDataDeletePreparedStatement = connection.prepareStatement("delete from lecture where lecture_id=?");

		// 開講講義データに関する解析済みSQLの生成
		allOpeningLectureDataSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture order by opening_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture where opening_lecture_id like \"???__\" order by opening_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureDataSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture where opening_lecture_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureDataInsertPreparedStatement = connection.prepareStatement("insert into opening_lecture values(?, ?, ?)");
		openingLectureDataUpdatePreparedStatement = connection.prepareStatement("update opening_lecture set opening_lecture_id=?, lecture_id=?, necessary_opening_lecture_id=? where opening_lecture_id=?");
		openingLectureDataDeletePreparedStatement = connection.prepareStatement("delete from opening_lecture where opening_lecture_id=?");

		// 開講講義担当教官データに関する解析済みSQLの生成
		allOpeningLectureLecturerDataSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture_lecturer order by opening_lecture_lecturer_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureLecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture_lecturer where opening_lecture_lecturer_id like \"?????__\" order by opening_lecture_lecturer_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureLecturerDataSelectPreparedStatement = connection.prepareStatement("select * from opening_lecture_lecturer where opening_lecture_lecturer_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		openingLectureLecturerDataInsertPreparedStatement = connection.prepareStatement("insert into opening_lecture_lecturer values(?, ?)");
		openingLectureLecturerDataUpdatePreparedStatement = connection.prepareStatement("update opening_lecture_lecturer set opening_lecture_lecturer_id=?, lecturer_id=? where opening_lecture_lecturer_id=?");
		openingLectureLecturerDataDeletePreparedStatement = connection.prepareStatement("delete from opening_lecture_lecturer where opening_lecture_lecturer_id=?");

		// 登録済み開講講義データに関する解析済みSQLの生成
		allRegisteredOpeningLectureDataSelectPreparedStatement = connection.prepareStatement("select * from registered_opening_lecture order by registered_opening_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		registeredOpeningLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from registered_opening_lecture where registered_opening_lecture_id like \"?????___\" order by registered_opening_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		registeredOpeningLectureDataSelectPreparedStatement = connection.prepareStatement("select * from registered_opening_lecture where registered_opening_lecture_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		registeredOpeningLectureDataInsertPreparedStatement = connection.prepareStatement("insert into registered_opening_lecture values(?, ?)");
		registeredOpeningLectureDataUpdatePreparedStatement = connection.prepareStatement("update registered_opening_lecture set registered_opening_lecture_id=?, opening_lecture_id=? where registered_opening_lecture_id=?");
		registeredOpeningLectureDataDeletePreparedStatement = connection.prepareStatement("delete from registered_opening_lecture where registered_opening_lecture_id=?");

		// 履修済み講義データに関する解析済みSQLの生成
		allMasteredLectureDataSelectPreparedStatement = connection.prepareStatement("select * from mastered_lecture order by mastered_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		masteredLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement = connection.prepareStatement("select * from mastered_lecture where mastered_lecture_id like \"?????_______\" order by mastered_lecture_id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		masteredLectureDataSelectPreparedStatement = connection.prepareStatement("select * from mastered_lecture where mastered_lecture_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		masteredLectureDataInsertPreparedStatement = connection.prepareStatement("insert into mastered_lecture values(?, ?, ?, ?)");
		masteredLectureDataUpdatePreparedStatement = connection.prepareStatement("update mastered_lecture set mastered_lecture_id=?, score=?, mastered_year=?, lecture_affiliation_number=? where mastered_lecture_id=?");
		masteredLectureDataDeletePreparedStatement = connection.prepareStatement("delete from mastered_lecture where mastered_lecture_id=?");
	}

	/**
	 * UtilityBeanを設定する
	 * @param utility_bean UtilityBean
	 */
	public void setUtilityBean(UtilityBean utility_bean) {
		utilityBean = utility_bean;
	}

	/**
	 * 指定された解析済みSQLを実行する
	 * @param prepared_statement 解析済みSQL
	 * @exception SQLException
	 */
	private synchronized void executeUpdate(PreparedStatement prepared_statement) throws SQLException {
		connection.setReadOnly(false); // データベースを読み取り専用から解除
		prepared_statement.executeUpdate(); // 解析済みSQLの実行
		connection.setReadOnly(true); // データベースを読み取り専用に設定
	}

	/**
	 * 指定されたテーブルに指定された行データを挿入する
	 * @param table_name テーブル名
	 * @param row_data 行データ
	 * @exception SQLException
	 */
	public synchronized void insert(String table_name, String[] row_data) throws SQLException {
		PreparedStatement prepared_statement = null; // 解析済みSQLを格納

		// 指定されたテーブルが科目テーブルである場合
		if (table_name.equals("course")) {
			prepared_statement = courseDataInsertPreparedStatement; // 科目データを挿入する解析済みSQLを設定
		// 講義所属テーブルである場合
		} else if (table_name.equals("lecture_affiliation")) {
			prepared_statement = lectureAffiliationDataInsertPreparedStatement; // 講義所属データを挿入する解析済みSQLを設定
		// 講座テーブルである場合
		} else if (table_name.equals("chair")) {
			prepared_statement = chairDataInsertPreparedStatement; // 講座データを挿入する解析済みSQLを設定
		// 学生テーブルである場合
		} else if (table_name.equals("student")) {
			prepared_statement = studentDataInsertPreparedStatement; // 学生データを挿入する解析済みSQLを設定
		// 教官テーブルである場合
		} else if (table_name.equals("lecturer")) {
			prepared_statement = lecturerDataInsertPreparedStatement; // 教官データを挿入する解析済みSQLを設定
		// 講義テーブルである場合
		} else if (table_name.equals("lecture")) {
			prepared_statement = lectureDataInsertPreparedStatement; // 講義データを挿入する解析済みSQLを設定
		// 開講講義テーブルである場合
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = openingLectureDataInsertPreparedStatement; // 開講講義データを挿入する解析済みSQLを設定
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataInsertPreparedStatement; // 開講講義担当教官データを挿入する解析済みSQLを設定
		// 登録済み開講講義テーブルである場合
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataInsertPreparedStatement; // 登録済み開講講義データを挿入する解析済みSQLを設定
		// 履修済み講義テーブルである場合
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataInsertPreparedStatement; // 履修済み講義データを挿入する解析済みSQLを設定
		}

		// 行データの設定
		for (int i = 0; i < row_data.length; i++) {
			prepared_statement.setString(i + 1, row_data[i]);
		}

		executeUpdate(prepared_statement); // 行データの挿入
	}

	/**
	 * 指定されたテーブル・IDに指定された行データを更新する
	 * @param table_name テーブル名
	 * @param id ID
	 * @param row_data 行データ
	 * @exception SQLException
	 */
	public synchronized void update(String table_name, String id, String[] row_data) throws SQLException {
		PreparedStatement prepared_statement = null; // 解析済みSQLを格納

		// 指定されたテーブルが科目テーブルである場合
		if (table_name.equals("course")) {
			prepared_statement = courseDataUpdatePreparedStatement; // 科目データを更新する解析済みSQLを設定
		// 講義所属テーブルである場合
		} else if (table_name.equals("lecture_affiliation")) {
			prepared_statement = lectureAffiliationDataUpdatePreparedStatement; // 講義所属データを更新する解析済みSQLを設定
		// 講座テーブルである場合
		} else if (table_name.equals("chair")) {
			prepared_statement = chairDataUpdatePreparedStatement; // 講座データを更新する解析済みSQLを設定
		// 学生テーブルである場合
		} else if (table_name.equals("student")) {
			prepared_statement = studentDataUpdatePreparedStatement; // 学生データを更新する解析済みSQLを設定
		// 教官テーブルである場合
		} else if (table_name.equals("lecturer")) {
			prepared_statement = lecturerDataUpdatePreparedStatement; // 教官データを更新する解析済みSQLを設定
		// 講義テーブルである場合
		} else if (table_name.equals("lecture")) {
			prepared_statement = lectureDataUpdatePreparedStatement; // 講義データを更新する解析済みSQLを設定
		// 開講講義テーブルである場合
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = openingLectureDataUpdatePreparedStatement; // 開講講義データを更新する解析済みSQLを設定
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataUpdatePreparedStatement; // 開講講義担当教官データを更新する解析済みSQLを設定
		// 登録済み開講講義テーブルである場合
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataUpdatePreparedStatement; // 登録済み開講講義データを更新する解析済みSQLを設定
		// 履修済み講義テーブルである場合
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataUpdatePreparedStatement; // 履修済み講義データを更新する解析済みSQLを設定
		}

		// 行データの設定
		for (int i = 0; i < row_data.length; i++) {
			prepared_statement.setString(i + 1, row_data[i]);
		}
		prepared_statement.setString(row_data.length + 1, id);

		executeUpdate(prepared_statement); // 行データの更新
	}

	/**
	 * 指定されたテーブル・IDの行を削除する
	 * @param table_name テーブル名
	 * @param id ID
	 * @exception SQLException
	 */
	public synchronized void delete(String table_name, String id) throws SQLException {
		PreparedStatement prepared_statement = null; // 解析済みSQLを格納

		// 指定されたテーブルが科目テーブル、講義所属テーブル、又は講座テーブルである場合
		if (table_name.equals("course") || table_name.equals("lecture_affiliation") || table_name.equals("chair")) {
			String[] lecture_ids = null; // 講義IDを格納

			// 指定されたテーブル名が科目テーブルである場合
			if (table_name.equals("course")) {
				String[] lecture_affiliation_ids = getColumnDataArray("lecture_affiliation", 0, id); // 講義所属IDを格納

				// 講義所属IDが取得できた場合
				if (lecture_affiliation_ids != null) {
					// 各データの削除
					for (int i = 0; i < lecture_affiliation_ids.length; i++) {
						delete("lecture_affiliation", lecture_affiliation_ids[i]);
					}
				// 取得できなかった場合
				} else {
					lecture_ids = getColumnDataArray("lecture", 0, id + "00"); // 講義IDの取得
				}

				prepared_statement = courseDataDeletePreparedStatement; // 科目データを削除する解析済みSQLの取得
			// 講義所属テーブルである場合
			} else if (table_name.equals("lecture_affiliation")) {
				// 指定されたIDが専門科目のものである場合
				if (id.substring(0, 1).equals("0")) {
					String lecture_affiliation_number = id.substring(1, 2); // 講義所属番号を格納
					int int_lecture_affiliation_number = Integer.parseInt(lecture_affiliation_number); // 講義所属番号を格納

					// 講義所属番号が学生所属番号の要素数よりも小さい場合
					if (int_lecture_affiliation_number < utilityBean.STUDENT_AFFILIATION_NUMBERS.length) {
						int year = Calendar.getInstance().get(Calendar.YEAR); // 年を格納
						String[] years = utilityBean.getArray(year, year - 11); // 年を格納

						// 各年の指定された講義所属に所属する学生データが存在するかどうかをチェックし、存在する場合は削除
						for (int i = 0; i < years.length; i++) {
							String[] student_ids = getColumnDataArray("student", 0, years[i].substring(2) + utilityBean.STUDENT_AFFILIATION_NUMBERS[int_lecture_affiliation_number]); // 学籍番号を格納

							// 学籍番号が取得できた場合
							if (student_ids != null) {
								// 各学生データの削除
								for (int j = 0; j < student_ids.length; j++) {
									delete("student", student_ids[j]);
								}
							}
						}
					}

					String[] check_table_names = {"chair", "lecturer"}; // チェックするテーブル名を格納

					// チェックするテーブルに削除するデータが存在する場合の削除処理
					for (int i = 0; i < check_table_names.length; i++) {
						String[] delete_data_ids = getColumnDataArray(check_table_names[i], 0, lecture_affiliation_number); // 削除するデータのIDを格納

						// 削除するデータのIDが取得できた場合
						if (delete_data_ids != null) {
							// 各データの削除
							for (int j = 0; j < delete_data_ids.length; j++) {
								delete(check_table_names[i], delete_data_ids[j]);
							}
						}
					}
				// 専門科目のものでない場合
				} else {
					lecture_ids = getColumnDataArray("lecture", 0, id + "0"); // 講義IDの取得
				}

				prepared_statement = lectureAffiliationDataDeletePreparedStatement; // 講義所属データを削除する解析済みSQLの取得
			// 講座テーブルである場合
			} else if (table_name.equals("chair")) {
				lecture_ids = getColumnDataArray("lecture", 0, "0" + id); // 講義IDの取得
				prepared_statement = chairDataDeletePreparedStatement; // 講座データを削除する解析済みSQLの取得
			}

			// 講義IDが取得できた場合
			if (lecture_ids != null) {
				// 各講義データの削除
				for (int i = 0; i < lecture_ids.length; i++) {
					delete("lecture", lecture_ids[i]);
				}
			}
		// 学生テーブルである場合
		} else if (table_name.equals("student")) {
			String[] check_table_names = {"registered_opening_lecture", "mastered_lecture"}; // チェックするテーブル名を格納

			// チェックするテーブルに削除するデータが存在する場合の削除処理
			for (int i = 0; i < check_table_names.length; i++) {
				String[] delete_data_ids = getColumnDataArray(check_table_names[i], 0, id); // 削除するデータのIDを格納

				// 削除するデータのIDが取得できた場合
				if (delete_data_ids != null) {
					// 各データの削除
					for (int j = 0; j < delete_data_ids.length; j++) {
						delete(check_table_names[i], delete_data_ids[j]);
					}
				}
			}

			prepared_statement = studentDataDeletePreparedStatement; // 学生データを削除する解析済みSQLの取得
		// 教官テーブルである場合
		} else if (table_name.equals("lecturer")) {
			String[][] opening_lecture_lecturer_data = getRowsData("opening_lecture_lecturer"); // 開講講義担当教官データを格納

			// 開講講義担当教官データが取得できた場合
			if (opening_lecture_lecturer_data != null) {
				// 指定された教官IDに該当する開講講義担当教官データの削除
				for (int i = 0; i < opening_lecture_lecturer_data.length; i++) {
					// 現在アクセスしている開講講義教官データの教官IDが削除する教官データの教官IDと等しい場合
					if (opening_lecture_lecturer_data[i][1].equals(id)) {
						delete("opening_lecture_lecturer", opening_lecture_lecturer_data[i][0]); // データの削除
					}
				}
			}

			prepared_statement = lecturerDataDeletePreparedStatement; // 教官データを削除する解析済みSQLの取得
		// 講義テーブルである場合
		} else if (table_name.equals("lecture")) {
			String[][] opening_lecture_data = getRowsData("opening_lecture"); // 開講講義データを格納

			// 開講講義データが取得できた場合
			if (opening_lecture_data != null) {
				// 指定された講義IDに該当する開講講義データの削除
				for (int i = 0; i < opening_lecture_data.length; i++) {
					// 現在アクセスしている開講講義データが持つ講義IDが指定された講義IDと等しい場合
					if (opening_lecture_data[i][1].equals(id)) {
						delete("opening_lecture", opening_lecture_data[i][0]); // データの削除
					}
				}
			}

			prepared_statement = lectureDataDeletePreparedStatement; // 講義データを削除する解析済みSQLの取得
		// 開講講義テーブルである場合
		} else if (table_name.equals("opening_lecture")) {
			String[] opening_lecture_lecturer_ids = getColumnDataArray("opening_lecture_lecturer", 0, id); // 開講講義担当教官IDを格納

			// 開講講義担当教官IDが取得できた場合
			if (opening_lecture_lecturer_ids != null) {
				// 各開講講義担当教官IDの削除
				for (int i = 0; i < opening_lecture_lecturer_ids.length; i++) {
					delete("opening_lecture_lecturer", opening_lecture_lecturer_ids[i]);
				}
			}

			String[][] opening_lecture_data = getRowsData("opening_lecture"); // 開講講義データを格納

			// 開講講義データが取得できた場合
			if (opening_lecture_data != null) {
				// 指定された開講講義が必要な開講講義と設定されている開講講義データの更新
				for (int i = 0; i < opening_lecture_data.length; i++) {
					// 現在アクセスしている開講講義データに設定されている必要開講講義IDが指定されたIDと等しい場合
					if (opening_lecture_data[i][2] != null && opening_lecture_data[i][2].equals(id)) {
						opening_lecture_data[i][2] = null; // 必要開講講義IDをnullに設定
						update("opening_lecture", opening_lecture_data[i][0], opening_lecture_data[i]); // データの更新
					}
				}
			}

			prepared_statement = openingLectureDataDeletePreparedStatement; // 開講講義データを削除する解析済みSQLの取得
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataDeletePreparedStatement; // 開講講義担当教官データを削除する解析済みSQLの取得
		// 登録済み開講講義テーブルである場合
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataDeletePreparedStatement; // 登録済み開講講義データを削除する解析済みSQLの取得
		// 履修済み講義テーブルである場合
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataDeletePreparedStatement; // 履修済み講義データを削除する解析済みSQLの取得
		}

		prepared_statement.setString(1, id); // 削除を行う行のIDの設定
		executeUpdate(prepared_statement); // 行の削除
	}

	/**
	 * 指定されたテーブルの全行の結果集合を返す
	 * @param table_name テーブル名
	 * @return 結果集合
	 * @exception SQLException
	 */
	private synchronized ResultSet getAllRowsResultSet(String table_name) throws SQLException {
		PreparedStatement prepared_statement = null; // 解析済みSQLを格納

		// 指定されたテーブルが管理者テーブルである場合
		if (table_name.equals("administrator")) {
			prepared_statement = allAdministratorDataSelectPreparedStatement; // 全ての管理者データを選択する解析済みSQLの取得
		// 科目テーブルである場合
		} else if (table_name.equals("course")) {
			prepared_statement = allCourseDataSelectPreparedStatement; // 全ての科目データを選択する解析済みSQLの取得
		// 講義所属テーブルである場合
		} else if (table_name.equals("lecture_affiliation")) {
			prepared_statement = allLectureAffiliationDataSelectPreparedStatement; // 全ての講義所属データを選択する解析済みSQLの取得
		// 講座テーブルである場合
		} else if (table_name.equals("chair")) {
			prepared_statement = allChairDataSelectPreparedStatement; // 全ての講座データを選択する解析済みSQLの取得
		// 学生テーブルである場合
		} else if (table_name.equals("student")) {
			prepared_statement = allStudentDataSelectPreparedStatement; // 全ての学生データを選択する解析済みSQLの取得
		// 教官テーブルである場合
		} else if (table_name.equals("lecturer")) {
			prepared_statement = allLecturerDataSelectPreparedStatement; // 全ての教官データを選択する解析済みSQLの取得
		// 講義テーブルである場合
		} else if (table_name.equals("lecture")) {
			prepared_statement = allLectureDataSelectPreparedStatement; // 全ての講義データを選択する解析済みSQLの取得
		// 開講講義テーブルである場合
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = allOpeningLectureDataSelectPreparedStatement; // 全ての開講講義データを選択する解析済みSQLの取得
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = allOpeningLectureLecturerDataSelectPreparedStatement; // 全ての開講講義担当教官データを選択する解析済みSQLの取得
		// 登録済み開講講義テーブルである場合
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = allRegisteredOpeningLectureDataSelectPreparedStatement; // 全ての登録済み開講講義データを選択する解析済みSQLの取得
		// 履修済み講義テーブルである場合
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = allMasteredLectureDataSelectPreparedStatement; // 全ての履修済み講義データを選択する解析済みSQLの取得
		}

		return prepared_statement.executeQuery(); // 解析済みSQLの結果集合を返す
	}

	/**
	 * 指定されたテーブル・データ所属番号の行の結果集合を返す
	 * @param table_name テーブル名
	 * @param data_affiliation_number データ所属番号
	 * @return 結果集合
	 * @exception SQLException
	 */
	private synchronized ResultSet getRowsResultSet(String table_name, String data_affiliation_number) throws SQLException {
		PreparedStatement prepared_statement = null; // 解析済みSQLを格納

		// 指定されたテーブルが講義所属テーブルである場合
		if (table_name.equals("lecture_affiliation")) {
			prepared_statement = lectureAffiliationDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // 指定されたデータ所属番号の講義所属データを選択する解析済みSQLの取得
		// 講座テーブルである場合
		} else if (table_name.equals("chair")) {
			prepared_statement = chairDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // 指定されたデータ所属番号の講座データを選択する解析済みSQLの取得
		// 学生テーブルである場合
		} else if (table_name.equals("student")) {
			prepared_statement = studentDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // 指定されたデータ所属番号の学生データを選択する解析済みSQLの取得
		// 教官テーブルである場合
		} else if (table_name.equals("lecturer")) {
			prepared_statement = lecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // 指定されたデータ所属番号の教官データを選択する解析済みSQLの取得
		// 講義テーブルである場合
		} else if (table_name.equals("lecture")) {
			prepared_statement = lectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // 指定されたデータ所属番号の講義データを選択する解析済みSQLの取得
		// 開講講義テーブルである場合
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = openingLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // 指定されたデータ所属番号の開講講義データを選択する解析済みSQLの取得
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // 指定されたデータ所属番号の開講講義担当教官データを選択する解析済みSQLの取得
		// 登録済み開講講義テーブルである場合
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // 指定されたデータ所属番号の登録済み開講講義データを選択する解析済みSQLの取得
		// 履修済み講義テーブルである場合
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataOfSpecifiedDataAffiliationNumberSelectPreparedStatement; // 指定されたデータ所属番号の履修済み講義データを選択する解析済みSQLの取得
		}

		// 指定されたテーブルが学生テーブルである場合
		if (table_name.equals("student")) {
			String year = data_affiliation_number.substring(0, 2); // 年を格納
			int subject_affiliation_number = Integer.parseInt(data_affiliation_number.substring(2)); // 学科所属番号を格納
			int end_subject_affiliation_number; // 学科所属番号の終端を格納

			// 学科所属番号による分岐
			switch (subject_affiliation_number) {
				// 指定された学科所属番号が経済学科のものである場合
				case 1:
					end_subject_affiliation_number = 3; // 学科所属番号の終端に3を設定
					break; // ブロックから抜ける
				// 企業経営学科のものである場合
				case 5:
					end_subject_affiliation_number = 6; // 学科所属番号の終端に6を設定
					break; // ブロックから抜ける
				// それ以外のものである場合
				default:
					end_subject_affiliation_number = subject_affiliation_number; // 学科所属番号の終端に学科所属番号を設定
					break; // ブロックから抜ける
			}

			// 選択範囲の設定
			prepared_statement.setString(1, year + subject_affiliation_number + "00");
			prepared_statement.setString(2, year + end_subject_affiliation_number + "99");
		// その他のテーブル名である場合
		} else {
			// データ所属番号の設定
			for (int i = 0; i < data_affiliation_number.length(); i++) {
				prepared_statement.setInt(i + 1, Integer.parseInt(data_affiliation_number.substring(i, i + 1)));
			}
		}

		return prepared_statement.executeQuery(); // 解析済みSQLの結果集合を返す
	}

	/**
	 * 指定されたテーブル・IDの行の結果集合を返す
	 * @param table_name テーブル名
	 * @param id ID
	 * @return 結果集合
	 * @exception SQLException
	 */
	private synchronized ResultSet getRowResultSet(String table_name, String id) throws SQLException {
		PreparedStatement prepared_statement = null; // 解析済みSQLを格納

		// 指定されたテーブルが管理者テーブルである場合
		if (table_name.equals("administrator")) {
			prepared_statement = administratorDataSelectPreparedStatement; // 管理者データを選択する解析済みSQLの取得
		// 科目テーブルである場合
		} else if (table_name.equals("course")) {
			prepared_statement = courseDataSelectPreparedStatement; // 科目データを選択する解析済みSQLの取得
		// 講義所属テーブルである場合
		} else if (table_name.equals("lecture_affiliation")) {
			prepared_statement = lectureAffiliationDataSelectPreparedStatement; // 講義所属データを選択する解析済みSQLの取得
		// 講座テーブルである場合
		} else if (table_name.equals("chair")) {
			prepared_statement = chairDataSelectPreparedStatement; // 講義所属データを選択する解析済みSQLの取得
		// 学生テーブルである場合
		} else if (table_name.equals("student")) {
			prepared_statement = studentDataSelectPreparedStatement; // 学生データを選択する解析済みSQLの取得
		// 教官テーブルである場合
		} else if (table_name.equals("lecturer")) {
			prepared_statement = lecturerDataSelectPreparedStatement; // 教官データを選択する解析済みSQLの取得
		// 講義テーブルである場合
		} else if (table_name.equals("lecture")) {
			prepared_statement = lectureDataSelectPreparedStatement; // 講義データを選択する解析済みSQLの取得
		// 開講講義テーブルである場合
		} else if (table_name.equals("opening_lecture")) {
			prepared_statement = openingLectureDataSelectPreparedStatement; // 開講講義データを選択する解析済みSQLの取得
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			prepared_statement = openingLectureLecturerDataSelectPreparedStatement; // 開講講義担当教官データを選択する解析済みSQLの取得
		// 登録済み開講講義テーブルである場合
		} else if (table_name.equals("registered_opening_lecture")) {
			prepared_statement = registeredOpeningLectureDataSelectPreparedStatement; // 登録済み開講講義データを選択する解析済みSQLの取得
		// 履修済み講義テーブルである場合
		} else if (table_name.equals("mastered_lecture")) {
			prepared_statement = masteredLectureDataSelectPreparedStatement; // 履修済み講義データを選択する解析済みSQLの取得
		}

		prepared_statement.setString(1, id); // IDの設定
		ResultSet result_set = prepared_statement.executeQuery(); // 結果集合を格納
		return result_set; // 結果集合を返す
	}

	/**
	 * 指定された結果集合の行数を返す
	 * @param result_set 結果集合
	 * @return 行数
	 * @exception SQLException
	 */
	private synchronized int getRowCount(ResultSet result_set) throws SQLException {
		result_set.last(); // 結果集合のポインタを最後の要素に移動
		int row_count = result_set.getRow(); // 行数を格納
		result_set.first(); // 結果集合のポインタを最初の要素に移動
		return row_count; // 行数を返す
	}

	/**
	 * 指定されたテーブルの行データを返す
	 * @param table_name テーブル名
	 * @return 行データ
	 * @exception SQLException
	 */
	public synchronized String[][] getRowsData(String table_name) throws SQLException {
		return getRowsData(table_name, null);
	}

	/**
	 * 指定されたテーブル・データ所属番号の行データを返す
	 * @param table_name テーブル名
	 * @param data_affiliation_number データ所属番号
	 * @return 行データ
	 * @exception SQLException
	 */
	public synchronized String[][] getRowsData(String table_name, String data_affiliation_number) throws SQLException {
		ResultSet result_set = null; // 解析済みSQLの結果集合を格納

		// データ所属番号が指定されていない場合
		if (data_affiliation_number == null) {
			result_set = getAllRowsResultSet(table_name); // 指定されたテーブル全行の結果集合の取得
		// 指定されている場合
		} else {
			result_set = getRowsResultSet(table_name, data_affiliation_number); // 指定されたテーブル・データ所属番号に該当する行の結果集合の取得
		}

		String[][] rows_data = null; // 行データを格納

		// データが取得できた場合
		if (result_set.next()) {
			rows_data = new String[getRowCount(result_set)][result_set.getMetaData().getColumnCount()]; // 行データの要素数の設定

			// 行データの取得
			for (int i = 0; i < rows_data.length; i++) {
				for (int j = 0; j < rows_data[i].length; j++) {
					rows_data[i][j] = result_set.getString(j + 1);
				}

				result_set.next(); // 結果集合のポインタの移動
			}
		}

		result_set.close(); // 解析済みSQLの結果集合のクローズ
		return rows_data; // 行データを返す
	}

	/**
	 * 指定されたテーブル・IDの行データを返す
	 * @param table_name テーブル名
	 * @param id ID
	 * @return 行データ
	 * @exception SQLException
	 */
	public synchronized String[] getRowData(String table_name, String id) throws SQLException {
		ResultSet result_set = getRowResultSet(table_name, id); // 結果集合を格納
		String[] row_data = null; // 行データを格納

		// 結果集合が取得できた場合
		if (result_set.next()) {
			row_data = new String[result_set.getMetaData().getColumnCount()]; // 行データを格納

			// 行データの取得
			for (int i = 0; i < row_data.length; i++) {
				row_data[i] = result_set.getString(i + 1);
			}
		}

		result_set.close(); // 解析済みSQLの結果集合のクローズ
		return row_data; // 行データを返す
	}

	/**
	 * 指定されたテーブル・列番号の列データ配列を返す
	 * @param table_name テーブル名
	 * @param column_number 列番号
	 * @return 列データ配列
	 * @exception SQLException
	 */
	public synchronized String[] getColumnDataArray(String table_name, int column_number) throws SQLException {
		return getColumnDataArray(table_name, "" + column_number, null);
	}

	/**
	 * 指定されたテーブル・列番号・データ所属番号の列データ配列を返す
	 * @param table_name テーブル名
	 * @param column_number 列番号
	 * @param data_affiliation_number データ所属番号
	 * @return 列データ配列
	 * @exception SQLException
	 */
	public synchronized String[] getColumnDataArray(String table_name, int column_number, String data_affiliation_number) throws SQLException {
		return getColumnDataArray(table_name, "" + column_number, data_affiliation_number);
	}

	/**
	 * 指定されたテーブル・列名の列データ配列を返す
	 * @param table_name テーブル名
	 * @param column_name 列名
	 * @return 列データ配列
	 * @exception SQLException
	 */
	public synchronized String[] getColumnDataArray(String table_name, String column_name) throws SQLException {
		return getColumnDataArray(table_name, column_name, null);
	}

	/**
	 * 指定されたテーブル・列名・データ所属番号の列データ配列を返す
	 * @param table_name テーブル名
	 * @param column_name 列名
	 * @param data_affiliation_number データ所属番号
	 * @return 列データ配列
	 * @exception SQLException
	 */
	public synchronized String[] getColumnDataArray(String table_name, String column_name, String data_affiliation_number) throws SQLException {
		ResultSet result_set = null; // 解析済みSQLの結果集合を格納

		// データ所属番号が指定されていない場合
		if (data_affiliation_number == null) {
			result_set = getAllRowsResultSet(table_name); // 指定されたテーブル全行の結果集合の取得
		// 指定されている場合
		} else {
			result_set = getRowsResultSet(table_name, data_affiliation_number); // 指定されたテーブル・データ所属番号に該当する行の結果集合の取得
		}

		String[] column_data_array = null; // 列データ配列を格納

		// 結果集合が取得できた場合
		if (result_set.next()) {
			column_data_array = new String[getRowCount(result_set)]; // 結果集合の行数を列データ配列の要素数に設定

			// 列データ配列の要素の取得
			for (int i = 0; i < column_data_array.length; i++) {
				// 指定された列名が数字である場合
				if (!utilityBean.isCharacterIncluded(column_name)) {
					column_data_array[i] = result_set.getString(Integer.parseInt(column_name) + 1);
				// 数字でない場合
				} else {
					column_data_array[i] = result_set.getString(column_name);
				}

				result_set.next(); // 結果集合のポインタの移動
			}
		}

		result_set.close(); // 解析済みSQLの結果集合のクローズ
		return column_data_array; // 列データ配列を返す
	}

	/**
	 * 指定されたテーブル・列番号・IDの列データを返す
	 * @param table_name テーブル名
	 * @param column_number 列番号
	 * @param id ID
	 * @return 列データ
	 * @exception SQLException
	 */
	public synchronized String getColumnData(String table_name, int column_number, String id) throws SQLException {
		return getColumnData(table_name, "" + column_number, id);
	}

	/**
	 * 指定されたテーブル・列名・IDの列データを返す
	 * @param table_name テーブル名
	 * @param column_name 列名
	 * @param id ID
	 * @return 列データ
	 * @exception SQLException
	 */
	public synchronized String getColumnData(String table_name, String column_name, String id) throws SQLException {
		ResultSet result_set = getRowResultSet(table_name, id); // 結果集合を格納
		String column_data = null; // 列データを格納

		// 結果集合が取得できた場合
		if (result_set.next()) {
			// 指定された列名が数字である場合
			if (!utilityBean.isCharacterIncluded(column_name)) {
				column_data = result_set.getString(Integer.parseInt(column_name) + 1); // 列データの取得
			// 数字でない場合
			} else {
				column_data = result_set.getString(column_name); // 列データの取得
			}
		}

		result_set.close(); // 結果集合のクローズ
		return column_data; // 列データを返す
	}

	/**
	 * 指定されたテーブルの列名を返す
	 * @param table_name テーブル名
	 * @return 列名
	 * @exception SQLException
	 */
	public String[] getColumnNames(String table_name) throws SQLException {
		ResultSet result_set = getAllRowsResultSet(table_name); // 結果集合を格納
		ResultSetMetaData result_set_meta_data = result_set.getMetaData(); // 結果集合のメタデータを格納
		result_set.close(); // 結果集合のクローズ
		String[] column_names = new String[result_set_meta_data.getColumnCount()]; // 列名を格納

		// 列名の取得
		for(int i = 0; i < column_names.length; i++) {
			column_names[i] = result_set_meta_data.getColumnName(i + 1);
		}

		return column_names; // 列名を返す
	}

	/**
	 * 指定されたテーブルのIDの桁数を返す
	 * @param table_name テーブル名
	 * @return IDの桁数
	 * @exception SQLException
	 */
	public int getIDLength(String table_name) throws SQLException {
		ResultSet result_set = connection.getMetaData().getColumns("lecture", "%", "%", table_name + "_id"); // IDの列情報の取得
		result_set.first(); // 最初の要素への移動
		int id_length = Integer.parseInt(result_set.getString("CHAR_OCTET_LENGTH")); // IDの桁数の取得
		result_set.close(); // IDの列情報のクローズ
		return id_length; // IDの桁数を返す
	}
}
