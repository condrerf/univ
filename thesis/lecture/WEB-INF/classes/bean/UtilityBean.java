// パッケージの宣言
package bean;

// Beanの動作に必要なパッケージのインポート
import java.io.Serializable;

// メソッド内の処理で利用するパッケージのインポート
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * データベース及びHTMLに関係しない処理を提供するBean
 * @author Ryo Fukushima
 */
public class UtilityBean implements Serializable {
	/** 学生所属番号を格納 */
	public final int[] STUDENT_AFFILIATION_NUMBERS = {1, 4, 5, 7, 8, 9, 0};

	/** ログインページ名を格納 */
	private final String LOGIN_PAGE_NAME = "index.jsp";

	/** メインページ名を格納 */
	private final String MAIN_PAGE_NAME = "main.jsp";

	/** ログアウトページ名を格納 */
	private final String LOGOUT_PAGE_NAME = "logout.jsp";

	/** DatabaseBeanを格納 */
	private DatabaseBean databaseBean = null;

	/** メンテナンスを行っている管理者IDを格納 */
	private String maintainingAdministratorID = null;

	/** 履修登録が行えるかどうかを格納 */
	private boolean isRegisterable = false;

	/** アプリケーションのURLを格納 */
	private String applicationURL = null;

	/** コンストラクタ */
	public UtilityBean() {
	}

	/**
	 * DatabaseBeanを設定する
	 * @param database_bean DatabaseBean
	 */
	public void setDatabaseBean(DatabaseBean database_bean) {
		databaseBean = database_bean;
	}

	/**
	 * メンテナンスを行っている管理者IDを設定する
	 * @param maintaining_administrator_id 管理者ID
	 */
	public void setMaintainingAdministratorID(String maintaining_administrator_id) {
		maintainingAdministratorID = maintaining_administrator_id;
	}

	/**
	 * メンテナンスを行っている管理者IDを返す
	 * @return 管理者ID
	 */
	public String getMaintainingAdministratorID() {
		return maintainingAdministratorID;
	}

	/** メンテナンスを行っている管理者IDを解放する */
	public void releaseMaintainingAdministratorID() {
		maintainingAdministratorID = null;
	}

	/**
	 * メンテナンス中であるかどうかを返す
	 * @return true又はfalse
	 */
	public boolean isMaintained() {
		return (maintainingAdministratorID != null ? true : false);
	}

	/**
	 * 履修登録が行えるかどうかを設定する
	 * @return true又はfalse
	 */
	public void setIsRegisterable(boolean is_registerable) {
		isRegisterable = is_registerable;
	}

	/**
	 * 履修登録が行えるかどうかを返す
	 * @return true又はfalse
	 */
	public boolean isRegisterable() {
		return isRegisterable;
	}

	/**
	 * アプリケーションのURLを設定する
	 * @param request 要求情報
	 */
	public void setApplicationURL(HttpServletRequest request) {
		applicationURL = Pattern.compile("/").split(request.getProtocol())[0].toLowerCase() + "://" + request.getServerName() + request.getContextPath() + "/";
	}

	/**
	 * アプリケーションのURLを返す
	 * @return アプリケーションのURL
	 */
	public String getApplicationURL() {
		return applicationURL;
	}

	/**
	 * ログインページのURLを返す
	 * @return ログインページのURL
	 */
	public String getLoginPageURL() {
		return applicationURL + LOGIN_PAGE_NAME;
	}

	/**
	 * メインページのURLを返す
	 * @return メインページのURL
	 */
	public String getMainPageURL() {
		return applicationURL + MAIN_PAGE_NAME;
	}

	/**
	 * メインページのURLを返す
	 * @param student_id 学籍番号
	 * @return メインページのURL
	 */
	public synchronized String getMainPageURL(String student_id) {
		return getMainPageURL() + "?student_id=" + student_id;
	}

	/**
	 * ログアウトページのURLを返す
	 * @return ログアウトページのURL
	 */
	public String getLogoutPageURL() {
		return applicationURL + LOGOUT_PAGE_NAME;
	}

	/**
	 * パスワード変更手続きページのURLを返す
	 * @return パスワード変更手続きページのURL
	 */
	public String getPasswordChangeProcedurePageURL() {
		return applicationURL + "password/index.jsp";
	}

	/**
	 * 管理ディレクトリのURLを返す
	 * @return 管理ディレクトリのURL
	 */
	public String getAdminDirectoryURL() {
		return getApplicationURL() + "admin/";
	}

	/**
	 * 管理用ログインページのURLを返す
	 * @return 管理用ログインページのURL
	 */
	public String getAdminLoginPageURL() {
		return getAdminDirectoryURL() + LOGIN_PAGE_NAME;
	}

	/**
	 * 管理用メインページのURLを返す
	 * @return 管理用メインページのURL
	 */
	public String getAdminMainPageURL() {
		return getAdminDirectoryURL() + MAIN_PAGE_NAME;
	}

	/**
	 * 管理用ログアウトページのURLを返す
	 * @return 管理用ログアウトページのURL
	 */
	public String getAdminLogoutPageURL() {
		return getAdminDirectoryURL() + LOGOUT_PAGE_NAME;
	}

	/**
	 * テーブル選択ページのURLを返す
	 * @return テーブル選択ページのURL
	 */
	public String getTableSelectPageURL() {
		return getAdminDirectoryURL() + "table_select.jsp";
	}

	/**
	 * データ選択ページのURLを返す
	 * @return データ選択ページのURL
	 */
	public String getDataSelectPageURL() {
		return getAdminDirectoryURL() + "data_select.jsp";
	}

	/**
	 * 指定されたテーブル名のデータ選択ページのURLを返す
	 * @param table_name テーブル名
	 * @return データ選択ページのURL
	 * @exception SQLException
	 */
	public String getDataSelectPageURL(String table_name) throws SQLException {
		return getDataSelectPageURL() + "?table_name=" + table_name;
	}

	/**
	 * 指定されたテーブル名・データ所属番号からデータ選択ページのURLを作成し、それを返す
	 * @param table_name テーブル名
	 * @param data_affiliation_number データ所属番号
	 * @return データ選択ページのURL
	 * @exception SQLException
	 */
	public String getDataSelectPageURL(String table_name, String data_affiliation_number) throws SQLException {
		StringBuffer admin_data_select_page_url = new StringBuffer(getDataSelectPageURL(table_name)); // データ選択ページのURLを格納

		// 指定されたデータ所属番号がnullでなく、指定されたデータ所属番号のデータが存在する場合
		if (data_affiliation_number != null && databaseBean.getRowsData(table_name, data_affiliation_number) != null) {
			admin_data_select_page_url.append("&" + getDataAffiliationNumberName(table_name) + "=" + data_affiliation_number); // パラメータの付加
		}

		return admin_data_select_page_url.toString(); // データ選択ページのURLを返す
	}

	/**
	 * 指定された開講講義IDの講義と同じ日時に開講される開講講義データの選択ページへのURLを返す
	 * @param opening_lecture_id 開講講義ID
	 * @return 開講講義データ選択ページのURL
	 */
	public String getOpeningLectureDataSelectPageURL(String opening_lecture_id) {
		return getDataSelectPageURL() + "?processing_type=record&" + getDataAffiliationNumberName("opening_lecture") + "=" + getDataAffiliationNumber("opening_lecture", opening_lecture_id);
	}

	/**
	 * 指定されたテーブル名・ID・データ所属番号から管理データ編集ページのURLを作成し、それを返す
	 * @param table_name テーブル名
	 * @param id ID
	 * @param data_affiliation_number データ所属番号
	 * @return 管理データ編集ページのURL
	 */
	public String getDataEditPageURL(String table_name, String id, String data_affiliation_number) {
		StringBuffer admin_data_edit_page_url = new StringBuffer(getAdminDirectoryURL() + "edit/index.jsp?table_name=" + table_name); // 管理データ編集ページのURLを格納

		// IDが指定されている場合
		if (id != null) {
			admin_data_edit_page_url.append("&id=" + id); // IDの付加
		// データ所属番号が指定されている場合
		} else if (data_affiliation_number != null) {
			admin_data_edit_page_url.append("&data_affiliation_number=" + data_affiliation_number); // データ所属番号の付加
		}

		return admin_data_edit_page_url.toString(); // 管理データ編集ページのURLを返す
	}

	/**
	 * 成績編集ページのURLを返す
	 * @param id ID
	 * @return 成績編集ページのURL
	 */
	public String getRecordEditPageURL(String id) {
		return getAdminDirectoryURL() + "record/index.jsp?id=" + id;
	}

	/**
	 * 指定された種類のエラーページのURIを返す
	 * @param error_type エラーの種類
	 * @return エラーページのURI
	 * @exception UnsupportedEncodingException
	 */
	public synchronized String getErrorPageURI(String error_type) throws UnsupportedEncodingException {
		return getErrorPageURI(error_type, null, null); // パラメータをnullに指定した実行結果を返す
	}

	/**
	 * 指定された種類のエラーページのURIを返す
	 * @param error_type エラーの種類
	 * @param error_parameter_name エラーパラメータの名前
	 * @return エラーページのURI
	 * @exception UnsupportedEncodingException
	 */
	public synchronized String getErrorPageURI(String error_type, String error_parameter_name) throws UnsupportedEncodingException {
		return getErrorPageURI(error_type, error_parameter_name, null); // パラメータ値をnullに指定した実行結果を返す
	}

	/**
	 * 指定された種類のエラーページのURIを返す
	 * @param error_type エラーの種類
	 * @param error_parameter_name エラーパラメータの名前
	 * @param error_parameter_value エラーパラメータの値
	 * @return エラーページのURI
	 * @exception UnsupportedEncodingException
	 */
	public synchronized String getErrorPageURI(String error_type, String error_parameter_name, int error_parameter_value) throws UnsupportedEncodingException {
		return getErrorPageURI(error_type, error_parameter_name, "" + error_parameter_value); // パラメータ値を文字列型に変更した実行結果を返す
	}

	/**
	 * 指定された種類のエラーページのURIを返す
	 * @param error_type エラーの種類
	 * @param error_error_parameter_name エラーパラメータの名前
	 * @param error_parameter_value エラーパラメータの値
	 * @return エラーページのURI
	 * @exception UnsupportedEncodingException
	 */
	public synchronized String getErrorPageURI(String error_type, String error_parameter_name, String error_parameter_value) throws UnsupportedEncodingException {
		StringBuffer error_page_url = new StringBuffer("/error.jsp?error_type=" + error_type); // エラーページのURLを格納

		// パラメータ名が設定されている場合
		if (error_parameter_name != null) {
			error_page_url.append("&error_parameter_name=" + URLEncoder.encode(error_parameter_name, "Shift_JIS")); // パラメータ名の付加
		}

		// パラメータ値が設定されている場合
		if (error_parameter_value != null) {
			error_page_url.append("&error_parameter_value=" + URLEncoder.encode(error_parameter_value, "Shift_JIS")); // パラメータ値の付加
		}

		return error_page_url.toString(); // エラーページのURLを返す
	}

	/**
	 * 指定された学籍番号に含まれる全角数字を半角に変換して返す
	 * @param student_id 学籍番号
	 * @return 半角数字
	 */
	public synchronized String getFullDigitAlteredStudentID(String student_id) {
		final String[] FULL_DIGITS = {"０", "１", "２", "３", "４", "５", "６", "７", "８", "９"}; // 全角数字を格納

		// 全角数字を半角に変換
		for (int i = 0; i < FULL_DIGITS.length; i++) {
			student_id = Pattern.compile(FULL_DIGITS[i]).matcher(student_id).replaceAll("" + i);
		}

		return student_id; // 学籍番号を返す
	}

	/**
	 * 指定された数字に文字が含まれているかどうかを返す
	 * @param number 数字
	 * @return true又はfalse
	 */
	public synchronized boolean isCharacterIncluded(String number) {
		// 数字を1文字ずつチェック
		for (int i = 0; i < number.length(); i++) {
			// 現在チェックしている文字が数字でない場合
			if(!Character.isDigit(number.charAt(i))) {
				return true; // 含まれていると返す
			}
		}

		return false; // 含まれていないと返す
	}

	/**
	 * 指定された学籍番号をチェックし、エラーがある場合は転送する
	 * @param student_id 学籍番号
	 * @param request 要求情報
	 * @param response 応答情報
	 * @exception IOException
	 * @exception ServletException
	 * @exception UnsupportedEncodingException
	 */
	public synchronized void checkStudentID(String student_id, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, UnsupportedEncodingException {
		ServletContext application = request.getSession().getServletContext(); // アプリケーション情報を格納

		// 学籍番号が入力されていない場合
		if (student_id.equals("")) {
			application.getRequestDispatcher(getErrorPageURI("no_character_inputed_error", "学籍番号")).forward(request, response); // エラーページへの転送
		}

		// 学籍番号が5桁で入力されていない場合
		if (student_id.length() != 5) {
			application.getRequestDispatcher(getErrorPageURI("length_error", "学籍番号", student_id)).forward(request, response); // エラーページへの転送
		}

		// 学籍番号に文字が含まれている場合
		if(isCharacterIncluded(student_id)) {
			application.getRequestDispatcher(getErrorPageURI("character_included_error", "学籍番号", student_id)).forward(request, response); // エラーページへの転送
		}
	}

	/**
	 * 指定された学籍番号の学生が入学した年を返す
	 * @param student_id 学籍番号
	 * @return 入学した年
	 */
	public synchronized int getEnteredYear(String student_id) {
		int present_year = Calendar.getInstance().get(Calendar.YEAR); // 現在の年を格納
		int entered_year = Integer.parseInt(((present_year - (present_year % 100)) / 100) + student_id.substring(0, 2)); // 指定された学籍番号の学生が入学したと思われる年(現在の年の上2桁+学籍番号の左2桁)を格納

		// 入学したと思われる年の値が現在の年よりも多い場合
		if (entered_year > present_year) {
			entered_year -= 100; // 100年減らす
		}

		return entered_year; // 入学した年を返す
	}

	/**
	 * 指定された学籍番号の学生の在籍年数を返す
	 * @param student_id 学籍番号
	 * @return 在籍年数
	 */
	public synchronized int getAffiliationYear(String student_id) {
		int affiliation_year = Calendar.getInstance().get(Calendar.YEAR) - getEnteredYear(student_id); // 在籍年数を返す
		return (Calendar.getInstance().get(Calendar.MONTH) < 3 ? affiliation_year : affiliation_year + 1); // 現在の月が3月までであればそのままの値、そうでなければ+1の値を返す
	}

	/**
	 * 指定された学籍番号に含まれる学生所属番号から学生所属IDを返す
	 * @param student_id 学籍番号
	 * @return 学生所属ID
	 */
	public synchronized int getStudentAffiliationID(String student_id) {
		// 学生所属番号による分岐
		switch (Integer.parseInt(student_id.substring(2, 3))) {
			// 指定された学生所属番号が経済学科のものである場合
			case 1:
			case 2:
			case 3:
				return 0; // 0を返す
			// ファイナンス学科のものである場合
			case 4:
				return 1; // 1を返す
			// 企業経営学科のものである場合
			case 5:
			case 6:
				return 2; // 2を返す
			// 会計情報学科のものである場合
			case 7:
				return 3; // 3を返す
			// 情報管理学科のものである場合
			case 8:
				return 4; // 4を返す
			// 社会システム学科のものである場合
			case 9:
				return 5; // 5を返す
			// 夜間主のものである場合
			default:
				return 6; // 6を返す
		}
	}

	/**
	 * 学期番号を返す
	 * @return 学期番号
	 */
	public int getSemesterNumber() {
		int month = Calendar.getInstance().get(Calendar.MONTH); // 月を格納
		return (3 <= month && month <= 8 ? 1 : 2); // 4月から9月である場合は1、それ以外の場合は2を返す
	}

	/**
	 * 指定された要求情報から開講講義IDを取得し、それを返す
	 * @param request 要求情報
	 * @return 開講講義ID
	 */
	public synchronized String[][] getOpeningLectureIDs(HttpServletRequest request) {
		String[][] opening_lecture_ids = new String[6][7]; // 開講講義IDを格納

		// 開講講義IDの取得
		for (int i = 0; i < opening_lecture_ids.length; i++) {
			for (int j = 0; j < opening_lecture_ids[i].length; j++) {
				opening_lecture_ids[i][j] = request.getParameter("opening_lecture_id_" + i + j);
			}
		}

		return opening_lecture_ids; // 開講講義IDを返す
	}

	/**
	 * 指定された点数の評価を返す
	 * @param score 点数
	 * @return 評価
	 */
	public synchronized String getEvaluation(String score) {
		// 点数が指定されていない場合
		if (score == null) {
			return "認定"; // "認定"を返す
		// 指定されている場合
		} else {
			int grade = (Integer.parseInt(score) - 50) / 10; // 等級を格納

			// 等級が0である場合
			if (grade <= 0) {
				return "不可"; // "不可"を返す
			// 1である場合
			} else if (grade == 1) {
				return "可"; // "可"を返す
			// 2である場合
			} else if (grade == 2) {
				return "良"; // "良"を返す
			// 3以上である場合
			} else {
				return "優"; // "優"を返す
			}
		}
	}

	/**
	 * 指定された学籍番号の学生が必要な単位を返す
	 * @param student_id 学籍番号
	 * @return 必要な単位
	 */
	public synchronized int[] getNecessaryCredits(String student_id) {
		int[] necessary_credits = {92, 0, 0, 0, 124}; // 必要な単位を格納

		// 指定された学生が夜間主である場合
		if (getStudentAffiliationID(student_id) == 6) {
			necessary_credits[1] = 24; // 共通科目の必要な単位数を24に設定
			necessary_credits[2] = 8; // 外国語科目の必要な単位数を8に設定
		// 昼間主である場合
		} else {
			necessary_credits[1] = 20; // 共通科目の必要な単位数を20に設定
			necessary_credits[2] = 12; // 外国語科目の必要な単位数を12に設定
		}

		return necessary_credits; // 必要な単位を返す
	}

	/**
	 * 指定された英単語を日本語に置き換え、それを返す
	 * @param english_word 英単語
	 * @return 日本語に置き換えられた英単語
	 */
	public synchronized String getJapaneseWord(String english_word) {
		String[][] replace_words = {{"_", ""}, {"studentid", "学籍番号"}, {"id", "ID"}, {"new", "新規登録"}, {"credit", "単位"}, {"edit", "編集"}, {"course", "科目"}, {"name", "名"}, {"lecturer", "教官"}, {"openinglecture", "開講講義"}, {"lecture", "講義"}, {"affiliation", "所属"}, {"number", "番号"}, {"chair", "講座"}, {"student", "学生"}, {"subject", "学科"}, {"password", "パスワード"}, {"birthday", "誕生日"}, {"nationality", "国籍"}, {"basicseminar", "基礎演習"}, {"infomationliteracy", "情報リテラシー"}, {"class", "クラス"}, {"healthandlife", "健康と生活"}, {"sportsscience", "スポーツ科学"}, {"english", "英語"}, {"second", "第2"}, {"foreignlanguage", "外国語"}, {"seminar", "ゼミ"}, {"day", "昼間主"}, {"night", "夜間主"}, {"masteryear", "履修年次"}, {"masterableyear", "履修可能年"}, {"registeredasanotherdata", "別データとして登録"}, {"start", "始端"}, {"end", "終端"}, {"economy", "経済"}, {"finance", "ファイナンス"}, {"enterprisemanagement", "企業経営"}, {"accounts", "会計"}, {"information", "情報"}, {"management", "管理"}, {"societysystem", "社会システム"}, {"unmasterable", "を履修不可にする"}, {"is", ""}, {"necessary", "必要"}, {"i", "I"}}; // 置き換える言葉を格納

		// 各語の置き換え
		for (int i = 0; i < replace_words.length; i++) {
			english_word = Pattern.compile(replace_words[i][0]).matcher(english_word).replaceAll(replace_words[i][1]);
		}

		return english_word; // 日本語に置き換えられた英単語
	}

	/**
	 * 設定されたテーブル名のデータ所属番号の名前を返す
	 * @param table_name テーブル名
	 * @return データ所属番号の名前
	 */
	public String getDataAffiliationNumberName(String table_name) {
		String data_affiliation_number_name = null; // データ所属番号の名前を格納

		// 指定されたテーブル名が講義所属テーブルである場合
		if (table_name.equals("lecture_affiliation")) {
			data_affiliation_number_name = "course_id"; // "course_id"をデータ所属番号の名前に設定
		// 講座テーブルである場合
		} else if (table_name.equals("chair")) {
			data_affiliation_number_name = "subject_id"; // "subject_id"をデータ所属番号の名前に設定
		// 学生テーブルである場合
		} else if (table_name.equals("student")) {
			data_affiliation_number_name = "student_affiliation_number"; // "student_affiliation_number"をデータ所属番号の名前に設定
		// 教官テーブルである場合
		} else if (table_name.equals("lecturer")) {
			data_affiliation_number_name = "lecturer_affiliation_number"; // "lecturer_affiliation_number"をデータ所属番号の名前に設定
		// lectureである場合
		} else if (table_name.equals("lecture")) {
			data_affiliation_number_name = "lecture_affiliation_number"; // "lecture_affiliation_number"をデータ所属番号の名前に設定
		// 開講講義テーブルである場合
		} else if (table_name.equals("opening_lecture")) {
			data_affiliation_number_name = "opening_lecture_affiliation_number"; // "opening_lecture_affiliation_number"をデータ所属番号の名前に設定
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			data_affiliation_number_name = "opening_lecture_id"; // "opening_lecture_id"をデータ所属番号の名前に設定
		}

		return data_affiliation_number_name; // データ所属番号の名前を返す
	}

	/**
	 * 指定されたテーブル名に応じて指定されたIDに含まれるデータ所属番号を返す
	 * @param table_name テーブル名
	 * @param id ID
	 * @return データ所属番号
	 */
	public String getDataAffiliationNumber(String table_name, String id) {
		String data_affiliation_number = null; // データ所属番号を格納

		// 指定されたテーブルが講義所属テーブルである場合
		if (table_name.equals("lecture_affiliation")) {
			data_affiliation_number = id.substring(0, 1); // 指定されたIDの1文字目をデータ所属番号に設定
		// 講座テーブルである場合
		} else if (table_name.equals("chair")) {
			data_affiliation_number = id.substring(0, 1); // 指定されたIDの1文字目をデータ所属番号に設定
		// 講義テーブルである場合
		} else if (table_name.equals("lecture")) {
			data_affiliation_number = id.substring(0, 3); // 指定されたIDの1文字目から3文字目までをデータ所属番号に設定
		// 教官テーブルである場合
		} else if (table_name.equals("lecturer")) {
			data_affiliation_number = id.substring(0, 1); // 指定されたIDの1文字目をデータ所属番号に設定
		// 開講講義テーブルである場合
		} else if (table_name.equals("opening_lecture")) {
			data_affiliation_number = id.substring(0, 3); // 指定されたIDの1文字目から3文字目までをデータ所属番号に設定
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			data_affiliation_number = id.substring(0, 5); // 指定されたIDの1文字目から5文字目までをデータ所属番号に設定
		// 学生テーブルである場合
		} else if (table_name.equals("student")) {
			data_affiliation_number = id.substring(0, 3); // 指定されたIDの1文字目から3文字目までをデータ所属番号に設定
		}

		return data_affiliation_number; // データ所属番号を返す
	}

	/**
	 * 指定されたテーブル名・要求情報からデータ所属番号を取得し、それを返す
	 * @param table_name テーブル名
	 * @param request 要求情報
	 * @return データ所属番号
	 */
	public String getDataAffiliationNumber(String table_name, HttpServletRequest request) {
		String data_affiliation_number_name = getDataAffiliationNumberName(table_name); // データ所属番号名を格納
		return (data_affiliation_number_name != null ? request.getParameter(data_affiliation_number_name) : null); // データ所属番号名が取得できた場合はデータ所属番号、取得できなかった場合はnullを返す
	}

	/**
	 * 指定されたテーブルのデータ所属番号のフォーム構成要素データを返す
	 * @param table_name テーブル名
	 * @return データ所属番号のフォーム構成要素データ
	 * @exception SQLException
	 */
	public String[][] getDataAffiliationNumberFormComponentData(String table_name) throws SQLException {
		String[] form_component_ids = null; // フォーム構成要素のIDを格納
		String[] form_component_outlines = null; // フォーム構成要素の見出しを格納

		// 指定されたテーブルが講義所属テーブルである場合
		if (table_name.equals("lecture_affiliation")) {
			String[][] course_data = databaseBean.getRowsData("course"); // 科目データを格納

			// 科目データが取得できた場合
			if (course_data != null) {
				// フォーム構成要素データの要素数の設定
				form_component_ids = new String[course_data.length];
				form_component_outlines = new String[course_data.length];

				// フォーム構成要素データの取得
				for (int i = 0; i < course_data.length; i++) {
					form_component_ids[i] = course_data[i][0];
					form_component_outlines[i] = course_data[i][1];
				}
			}
		// 講座テーブル、又は学生テーブルである場合
		} else if (table_name.equals("chair") || table_name.equals("student")) {
			String[][] lecture_affiliation_data = databaseBean.getRowsData("lecture_affiliation", "0"); // 講義所属データを格納

			// 講義所属データが取得できた場合
			if (lecture_affiliation_data != null) {
				String[][] subject_data = new String[2][lecture_affiliation_data.length - 1]; // 学科データを格納

				// 学科データの取得
				for (int i = 0; i < subject_data[0].length; i++) {
					subject_data[0][i] = lecture_affiliation_data[i][0].substring(1);
					subject_data[1][i] = lecture_affiliation_data[i][1];
				}

				// 指定されたテーブル名が講座テーブルである場合
				if (table_name.equals("chair")) {
					form_component_ids = new String[subject_data[0].length]; // フォーム構成要素IDの要素数の設定
					form_component_outlines = new String[form_component_ids.length]; // フォーム構成要素見出しの要素数の設定

					// 構成要素データの取得
					for (int i = 0; i < form_component_ids.length; i++) {
						form_component_ids[i] = subject_data[0][i];
						form_component_outlines[i] = subject_data[1][i];
					}
				// 学生テーブルである場合
				} else {
					String[][] student_affiliation_data = new String[2][subject_data[0].length + 1]; // 学生所属データを格納

					// 学生所属データの取得
					for (int i = 0; i < subject_data[0].length; i++) {
						student_affiliation_data[0][i] = subject_data[0][i];
						student_affiliation_data[1][i] = subject_data[1][i];
					}
					student_affiliation_data[0][subject_data[0].length] = "6";
					student_affiliation_data[1][subject_data[1].length] = "夜間主";

					int year = Calendar.getInstance().get(Calendar.YEAR); // 年を格納
					String[] years = getArray(year, year - 11); // 年を格納
					int component_count = years.length * student_affiliation_data[0].length; // 構成要素数を格納
					form_component_ids = new String[component_count]; // IDの要素数の設定
					form_component_outlines = new String[component_count]; // 見出しの要素数の設定

					// 構成要素データの取得
					for (int i = 0; i < years.length; i++) {
						String student_affiliation_year = years[i].substring(2); // 学生の所属年を格納

						for (int j = 0; j < STUDENT_AFFILIATION_NUMBERS.length; j++) {
							int index = (i * STUDENT_AFFILIATION_NUMBERS.length) + j; // インデックスを格納
							form_component_ids[index] = student_affiliation_year + STUDENT_AFFILIATION_NUMBERS[Integer.parseInt(student_affiliation_data[0][j])]; // IDの取得
							form_component_outlines[index] = years[i] + "年・" + student_affiliation_data[1][j]; // 見出しの取得
						}
					}
				}
			}
		// 教官テーブルである場合
		} else if (table_name.equals("lecturer")) {
			String[][] lecture_affiliation_data = databaseBean.getRowsData("lecture_affiliation", "0"); // 講義所属データを格納

			// 講義所属データが取得できた場合
			if (lecture_affiliation_data != null) {
				// フォーム構成要素データの要素数の設定
				form_component_ids = new String[lecture_affiliation_data.length];
				form_component_outlines = new String[lecture_affiliation_data.length];

				// フォーム構成要素データの取得IDの取得
				for (int i = 0;i < lecture_affiliation_data.length; i++) {
					form_component_ids[i] = lecture_affiliation_data[i][0].substring(1);
					form_component_outlines[i] = lecture_affiliation_data[i][1];
				}
			}
		// 講義テーブルである場合
		} else if (table_name.equals("lecture")) {
			String[][] course_data = databaseBean.getRowsData("course"); // 科目データを格納

			// 科目データが取得できた場合
			if (course_data != null) {
				Vector vector_form_component_ids = new Vector(); // フォーム構成要素のIDを格納
				Vector vector_form_component_outlines = new Vector(); // フォーム構成要素の見出しを格納

				// 構成要素データの取得
				for (int i = 0; i < course_data.length; i++) {
					String[][] lecture_affiliation_data = databaseBean.getRowsData("lecture_affiliation", course_data[i][0]); // 講義所属データを格納

					// 講義所属データが取得できなかった場合
					if (lecture_affiliation_data == null) {
						// 科目データの構成要素データへの付加
						vector_form_component_ids.addElement(course_data[i][0] + "00");
						vector_form_component_outlines.addElement(course_data[i][1]);
					// 取得できた場合
					} else {
						int lecture_affiliation_number_length = getDataAffiliationNumber("lecture_affiliation", lecture_affiliation_data[0][0]).length(); // 講義所属番号の桁数を格納

						// 講義所属データの付加
						for (int j = 0; j < lecture_affiliation_data.length; j++) {
							String outline = course_data[i][1] + "・" + lecture_affiliation_data[j][1]; // 見出しを格納
							String[][] chair_data = null; // 講座データを格納

							// 現在アクセスしている科目データが専門データである場合
							if (course_data[i][0].equals("0")) {
								chair_data = databaseBean.getRowsData("chair", lecture_affiliation_data[j][0].substring(lecture_affiliation_number_length)); // 講座データの取得
							}

							// 講座データがnullである場合
							if (chair_data == null) {
								// 科目データと講義所属データの構成要素データへの付加
								vector_form_component_ids.addElement(lecture_affiliation_data[j][0] + "0");
								vector_form_component_outlines.addElement(outline);
							// nullでない場合
							} else {
								int chair_affiliation_number_length = getDataAffiliationNumber("chair", chair_data[0][0]).length(); // 講座所属番号の桁数を格納

								// 科目データと講義所属データ、講座データの構成要素データへの付加
								for (int k = 0; k < chair_data.length; k++) {
									vector_form_component_ids.addElement(lecture_affiliation_data[j][0] + chair_data[k][0].substring(chair_affiliation_number_length));
									vector_form_component_outlines.addElement(outline + "・" + chair_data[k][1]);
								}
							}
						}
					}
				}

				form_component_ids = new String[vector_form_component_ids.size()]; // IDの要素数の設定
				form_component_outlines = new String[form_component_ids.length]; // 見出しの要素数の設定

				// 構成要素データの取得
				for (int i = 0; i < form_component_ids.length; i++) {
					form_component_ids[i] = (String) vector_form_component_ids.elementAt(i);
					form_component_outlines[i] = (String) vector_form_component_outlines.elementAt(i);
				}
			}
		// 開講講義テーブルである場合
		} else if (table_name.equals("opening_lecture")) {
			final String[] SEMESTER_NAMES = {"通年", "春学期", "秋学期"}; // 学期名を格納
			final String[] DAY_NAMES = {"月", "火", "水", "木", "金"}; // 曜日名を格納
			final int CLASS_COUNT = 7; // 時限数を格納
			final int CONCENTRATION_LECTURE_COUNT = 5; // 集中講義数を格納
			form_component_ids = new String[(SEMESTER_NAMES.length * DAY_NAMES.length * CLASS_COUNT) + ((SEMESTER_NAMES.length - 1) * CONCENTRATION_LECTURE_COUNT)]; // フォーム構成要素IDの要素数の設定
			form_component_outlines = new String[form_component_ids.length]; // フォーム構成要素名の要素数の設定
			int form_component_data_index = 0; // フォーム構成要素データのインデックスを格納

			// フォーム構成要素データの取得
			for (int i = 0; i < SEMESTER_NAMES.length; i++) {
				// 通常講義データの取得
				for (int j = 0; j < DAY_NAMES.length; j++) {
					for (int k = 0; k < CLASS_COUNT; k++) {
						form_component_ids[form_component_data_index] = i + "" + j + "" + k; // フォーム構成要素IDの設定
						form_component_outlines[form_component_data_index] = SEMESTER_NAMES[i] + "・" + DAY_NAMES[j] + "曜日・" + (k + 1) + "時限目"; // フォーム構成要素見出しの設定
						form_component_data_index++; // フォーム構成要素データのインデックスの加算
					}
				}

				// 現在アクセスしている学期要素が通年以外である場合
				if (i > 0) {
					// 集中講義データの取得
					for (int j = 0; j < CONCENTRATION_LECTURE_COUNT; j++) {
						form_component_ids[form_component_data_index] = i + "" + DAY_NAMES.length + "" + j; // フォーム構成要素IDの設定
						form_component_outlines[form_component_data_index] = SEMESTER_NAMES[i] + "・" + "集中講義(" + (j + 1) + ")"; // フォーム構成要素見出しの設定
						form_component_data_index++; // フォーム構成要素データのインデックスの加算
					}
				}
			}
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			String[][] opening_lecture_data = databaseBean.getRowsData("opening_lecture"); // 開講講義データを格納

			// 開講講義データが取得できた場合
			if (opening_lecture_data != null) {
				form_component_ids = new String[opening_lecture_data.length]; // フォーム構成要素IDの要素数を開講講義データの要素数に設定
				form_component_outlines = new String[form_component_ids.length]; // フォーム構成要素見出しの要素数をフォーム構成要素見出しの要素数に設定
				String[][] lecture_data = databaseBean.getRowsData("lecture"); // 講義データを格納

				// フォーム構成要素データの取得
				for (int i = 0; i < form_component_ids.length; i++) {
					form_component_ids[i] = opening_lecture_data[i][0]; // フォーム構成要素IDの取得

					// フォーム構成要素見出しの取得
					for (int j = 0; j < lecture_data.length; j++) {
						// 現在アクセスしている開講講義データが持つ講義IDが現在アクセスしている講義データの講義IDと等しい場合
						if (opening_lecture_data[i][1].equals(lecture_data[j][0])) {
							form_component_outlines[i] = lecture_data[j][1]; // フォーム構成要素見出しの取得
							break; // ループから抜ける
						}
					}
				}
			}
		}

		// IDがnullである場合
		if (form_component_ids == null) {
			return null; // nullを返す
		// nullでない場合
		} else {
			String[][] lecture_affiliation_number_form_component_data = new String[2][form_component_ids.length]; // 講義所属番号のフォームの構成要素データを格納

			// 講義所属番号のフォームの構成要素データの取得
			for (int i = 0; i < form_component_ids.length; i++) {
				lecture_affiliation_number_form_component_data[0][i] = form_component_ids[i];
				lecture_affiliation_number_form_component_data[1][i] = form_component_outlines[i];
			}

			return lecture_affiliation_number_form_component_data; // 講義所属番号のフォームの構成要素データデータを返す
		}
	}

	/**
	 * 指定されたテーブル名のフォームの構成要素名を返す
	 * @param table_name テーブル名
	 * @return フォームの構成要素名
	 * @exception SQLException
	 */
	public String[] getFormComponentNames(String table_name) throws SQLException {
		Vector id_component_names = new Vector(); // IDの構成要素名を格納
		Vector vector_form_component_names = new Vector(); // フォームの構成要素名を格納
		String data_affiliation_number_name = getDataAffiliationNumberName(table_name); // 指定されたテーブルのデータ所属番号の名前を格納
		String[] table_column_names = databaseBean.getColumnNames(table_name); // 指定されたテーブルの列名を格納

		// 指定されたテーブル名がデータ所属番号のないテーブルである場合
		if (data_affiliation_number_name == null) {
			vector_form_component_names.addElement(table_column_names[0]); // ID名の付加
		// データ所属番号のあるテーブルである場合
		} else {
			vector_form_component_names.addElement(data_affiliation_number_name); // データ所属番号名の付加
			vector_form_component_names.addElement(table_name + "_number"); // データ番号名の付加
		}

		// フォームの構成要素内の、IDの構成要素部分以外の取得
		for (int i = 1; i < table_column_names.length; i++) {
			vector_form_component_names.addElement(table_column_names[i]);
		}

		// 指定されたテーブルが講義テーブルである場合
		if (table_name.equals("lecture")) {
			vector_form_component_names.addElement("is_registered_as_another_data"); // フォームの構成要素の付加
		}

		String[] form_component_names = new String[vector_form_component_names.size()]; // フォームの構成要素名を格納

		// フォームの構成要素名の取得
		for (int i = 0; i < form_component_names.length; i++) {
			form_component_names[i] = (String) vector_form_component_names.elementAt(i);
		}

		return form_component_names; // フォームの構成要素名を返す
	}

	/**
	 * 指定されたフォームの構成要素名・要求情報からフォームデータを作成し、それを返す
	 * @param form_component_names フォームの構成要素名
	 * @param request 要求情報
	 * @return フォームデータ
	 */
	public String[] getFormData(String[] form_component_names, HttpServletRequest request) {
		String[] form_data = new String[form_component_names.length]; // フォームデータを格納
		
		// フォームデータの取得
		for (int i = 0; i < form_data.length; i++) {
			form_data[i] = request.getParameter(form_component_names[i]);
		}

		return form_data; // フォームデータを返す
	}

	/**
	 * 指定された開講講義IDに該当する講義を履修登録した学生の学籍番号を返す
	 * @param opening_lecture_id 開講講義ID
	 * @return 学籍番号
	 * @exception SQLException
	 */
	public String[] getStudentIDs(String opening_lecture_id) throws SQLException {
		String[] student_ids = null; // 学籍番号を格納
		String[][] registered_opening_lecture_data = databaseBean.getRowsData("registered_opening_lecture"); // 登録済み開講講義データを格納

		// 登録済み開講講義データが取得できた場合
		if (registered_opening_lecture_data != null) {
			int semester_number = getSemesterNumber(); // 学期番号を格納
			Vector vector_student_ids = new Vector(); // 学籍番号を格納

			// 学籍番号の取得
			for (int i = 0; i < registered_opening_lecture_data.length; i++) {
				// 現在アクセスしている登録済み開講講義が指定された開講講義と等しく、現在の学期に登録されたものである場合
				if (registered_opening_lecture_data[i][1].equals(opening_lecture_id) && Integer.parseInt(registered_opening_lecture_data[i][0].substring(5, 6)) == semester_number) {
					vector_student_ids.addElement(registered_opening_lecture_data[i][0].substring(0, 5)); // 学籍番号の付加
				}
			}

			// 学籍番号が取得できた場合
			if (!vector_student_ids.isEmpty()) {
				student_ids = new String[vector_student_ids.size()]; // 学籍番号数を学籍番号の要素数に設定

				// 学籍番号の取得
				for (int i = 0; i < student_ids.length; i++) {
					student_ids[i] = vector_student_ids.elementAt(i).toString();
				}
			}
		}

		return student_ids; // 学籍番号を返す
	}

	/**
	 * 指定された開始値・終了値から配列を作成し、それを返す
	 * @param start_value 開始値
	 * @param end_value 終了値
	 * @return 配列
	 */
	public String[] getArray(int start_value, int end_value) {
		return getArray(start_value, end_value, 1);
	}

	/**
	 * 指定された開始値・終了値・間隔から配列を作成し、それを返す
	 * @param start_value 開始値
	 * @param end_value 終了値
	 * @param interval 間隔
	 * @return 配列
	 */
	public String[] getArray(int start_value, int end_value, int interval) {
		boolean is_value_reversed = false; // 値が入れ替えられたかどうかを格納

		// 指定された開始値が終了値よりも大きい場合
		if (start_value > end_value) {
			// 値の入れ替え
			int temporary_memory_area = start_value;
			start_value = end_value;
			end_value = temporary_memory_area;

			is_value_reversed = true; // 値が入れ替えられたことの設定
		}

		Vector vector_array = new Vector(); // 配列を格納

		// 要素の取得
		for (int i = start_value; i <= end_value; i += interval) {
			vector_array.addElement("" + i);
		}

		String[] array = new String[vector_array.size()]; // 配列を格納

		// 要素の取得
		for (int i = 0; i < array.length; i++) {
			int array_index = i; // 配列のインデックスを格納

			// 値が入れ替えられている場合
			if (is_value_reversed) {
				array_index = array.length - (i + 1); // 配列のインデックスの変更
			}

			array[array_index] = (String) vector_array.elementAt(i); // 要素の取得
		}

		return array; // 配列を返す
	}
}
