// パッケージの宣言
package bean;

// Beanの動作に必要なパッケージのインポート
import java.io.Serializable;

// メソッド内の処理で利用するパッケージのインポート
import bean.*;
import java.sql.SQLException;

/**
 * HTMLに関する処理を提供するBean
 * @author Ryo Fukushima
 */
public class HTMLBean implements Serializable {
	/** DatabaseBeanを格納 */
	private DatabaseBean databaseBean = null;

	/** UtilityBeanを格納 */
	private UtilityBean utilityBean = null;

	/** コンストラクタ */
	public HTMLBean() {
	}

	/**
	 * DatabaseBeanを設定する
	 * @param database_bean DatabaseBean
	 */
	public void setDatabaseBean(DatabaseBean database_bean) {
		databaseBean = database_bean;
	}

	/**
	 * UtilityBeanを設定する
	 * @param utility_bean UtilityBean
	 */
	public void setUtilityBean(UtilityBean utility_bean) {
		utilityBean = utility_bean;
	}

	/**
	 * ヘッダを返す
	 * @param title タイトル
	 * @return ヘッダ
	 */
	public synchronized String getHeader(String title) {
		StringBuffer header = new StringBuffer(); // ヘッダを格納

		// ヘッダの取得
		header.append("<html>\n");
		header.append("\t<head>\n");
		header.append("\t\t<title>" + title + " <履修登録システム></title>\n");
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

		return header.toString(); // ヘッダを返す
	}

	/**
	 * フッタを返す
	 * @return フッタ
	 */
	public String getFooter() {
		StringBuffer footer = new StringBuffer(); // フッタを格納

		// フッタの取得
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

		return footer.toString(); // フッタを返す
	}

	/**
	 * 指定されたアカウントのログインフォームを返す
	 * @param account_name アカウント名
	 * @return ログインフォーム
	 * @exception SQLException
	 */
	public synchronized String getLoginForm(String account_name) throws SQLException {
		String id_name = null; // ID名を格納
		String main_page_url = null; // メインページのURLを格納

		// 指定されたアカウント名が学生である場合
		if(account_name.equals("student")) {
			id_name = "学籍番号"; // "学籍番号"をID名に設定
			main_page_url = utilityBean.getMainPageURL(); // メインページのURLの取得
		// 指定されたアカウント名が管理者である場合
		} else if(account_name.equals("administrator")) {
			id_name = "管理者ID"; // "管理者ID"をID名に設定
			main_page_url = utilityBean.getAdminMainPageURL(); // 管理用メインページのURLの取得
		}

		StringBuffer login_form = new StringBuffer(); // ログインフォームを格納

		// ログインフォームの取得
		login_form.append("<form method=\"POST\" action=\"" + main_page_url + "\">\n");
		login_form.append("\t\t\t" + id_name + "とパスワードを入力し、[送信]ボタンをクリックして下さい。<br>\n");
		login_form.append("\t\t\t<table border=\"0\">\n");
		login_form.append("\t\t\t\t<tr>\n");
		login_form.append("\t\t\t\t\t<th align=\"right\">"+ id_name + "</th>\n");
		login_form.append("\t\t\t\t\t<td><input type=\"text\" name=\"" + account_name + "_id\" size=\"10\" /></td>\n");
		login_form.append("\t\t\t\t</tr>\n");
		login_form.append("\t\t\t\t<tr>\n");
		login_form.append("\t\t\t\t\t<th align=\"right\">パスワード</th>\n");
		login_form.append("\t\t\t\t\t<td><input type=\"password\" name=\"password\" size=\"10\" /></td>\n");
		login_form.append("\t\t\t\t</tr>\n");
		login_form.append("\t\t\t\t<tr>\n");
		login_form.append("\t\t\t\t\t<td colspan=\"2\">\n");
		login_form.append("\t\t\t\t\t\t<input type=\"submit\" value=\"送信\" />\n");
		login_form.append("\t\t\t\t\t\t<input type=\"reset\" value=\"取消\" />\n");
		login_form.append("\t\t\t\t\t</td>\n");
		login_form.append("\t\t\t\t</tr>\n");
		login_form.append("\t\t\t</table>\n");
		login_form.append("\t\t</form>\n");

		return login_form.toString(); // ログインフォームを返す
	}

	/**
	 * 指定された種類から開講講義一覧表を作成し、それを返す
	 * @param column_data 列データ
	 * @param button_name ボタン名
	 * @return 開講講義一覧表
	 * @exception SQLException
	 */
	public synchronized String getOpeningLectureList(String[][] column_data, String button_name) throws SQLException {
		final int TABLE_WIDTH = 1140; // テーブル幅を格納
		final int CLASS_OUTLINE_WIDTH = 10; // 時限見出し幅を格納
		final int COLUMN_WIDTH = 225; // 列幅を格納
		StringBuffer opening_lecture_list = new StringBuffer(); // 開講講義一覧表を格納

		// 開講講義一覧表の取得
		opening_lecture_list.append("<center>\n");
		opening_lecture_list.append("\t\t\t\t<table border=\"1\" width=\"" + TABLE_WIDTH + "\" cellpadding=\"0\" cellspacing=\"0\">\n");
		opening_lecture_list.append("\t\t\t\t\t<tr>\n");
		opening_lecture_list.append("\t\t\t\t\t\t<th width=\"" + CLASS_OUTLINE_WIDTH + "\"></th>\n");
		String[] day_names = {"月", "火", "水", "木", "金"}; // 曜日名を格納
		for (int i = 0; i < day_names.length; i++) {
			opening_lecture_list.append("\t\t\t\t\t\t<th width=\"" + COLUMN_WIDTH + "\">" + day_names[i] + "曜日</th>\n");
		}
		opening_lecture_list.append("\t\t\t\t\t</tr>\n");
		for (int i = 0; i < column_data[0].length; i++) {
			opening_lecture_list.append("\t\t\t\t\t<tr>\n");
			opening_lecture_list.append("\t\t\t\t\t\t<th>" + (i + 1) + "時限目</th>\n");

			// 列データの表示
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
		opening_lecture_list.append("\t\t\t\t\t\t<th width=\"" + CLASS_OUTLINE_WIDTH + "\">集中講義</th>\n");
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

		return opening_lecture_list.toString(); // 開講講義一覧表を返す
	}

	/**
	 * 指定された処理の種類・テーブル名・IDからデータ一覧表を作成し、それを返す
	 * @param processing_type 処理の種類
	 * @param table_name テーブル名
	 * @param ids ID
	 * @param button_name ボタンの名前
	 * @return データ一覧表
	 * @exception SQLException
	 */
	public String getDataList(String processing_type, String table_name, String[] ids, String button_name) throws SQLException {
		String[] table_column_names = databaseBean.getColumnNames(table_name); // テーブルの列名を格納
		String first_column_outline = utilityBean.getJapaneseWord(table_column_names[1]); // 1列目の見出しを格納
		String destination_page_url = null; // 宛て先ページのURLを格納
		StringBuffer data_list = new StringBuffer(); // データ一覧表を格納

		// 指定された処理の種類が"delete"である場合
		if (processing_type.equals("delete")) {
			destination_page_url = "./delete.jsp"; // 宛て先ページのURLの設定
		// "delete"以外の種類である場合
		} else {
			data_list.append(first_column_outline + "をクリックすると、該当する");

			// 指定された処理の種類が"record"である場合
			if (processing_type.equals("record")) {
				data_list.append("講義の成績編集ページに移動します。<br />\n");
			// "edit"である場合
			} else {
				destination_page_url = "delete/confirm.jsp"; // 宛て先ページのURLの設定
				String japanese_table_name = utilityBean.getJapaneseWord(table_name); // テーブル名の日本語名を格納

				data_list.append(japanese_table_name + "データの編集ページに移動します。<br />\n");
				data_list.append("\t\t削除チェックボックスにチェックを入れて[" + button_name + "]ボタンをクリックすると、指定された" + japanese_table_name + "データが削除されます。<br />\n");
			}

			data_list.append("\t\t<br />\n");
		}

		int tab_count; // タブ数を格納

		// 宛て先ページのURLが設定されていない場合
		if (destination_page_url == null) {
			tab_count = 2; // 2をタブ数に設定
		// 設定されている場合
		} else {
			tab_count = 3; // 3をタブ数に設定

			// 指定された処理の種類が"edit"である場合
			if (processing_type.equals("edit")) {
				data_list.append("\t\t");
			}
			data_list.append("<form method=\"POST\" action=\"" + destination_page_url + "\">\n");
			data_list.append("\t\t\t<input type=\"hidden\" name=\"table_name\" value=\"" + table_name + "\" />\n");
		}

		int outline_output_count; // 見出しの出力回数を格納

		// ID数が2以上である場合
		if (ids.length > 1) {
			outline_output_count = 2; // 見出しの出力回数を2に設定
		// 1である場合
		} else {
			outline_output_count = 1; // 見出しの出力回数を1に設定
		}

		String second_column_outline = utilityBean.getJapaneseWord(table_column_names[0]); // 2行目の見出しを格納

		// データ一覧表の取得
		data_list.append(getTab(tab_count) + "<table border=\"1\" bgcolor=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\">\n");
		data_list.append(getTab(tab_count + 1) + "<tr height=\"20\">\n");
		for (int i = 0; i < outline_output_count; i++) {
			data_list.append(getTab(tab_count + 2) + "<th>" + first_column_outline + "</th>\n");
			data_list.append(getTab(tab_count + 2) + "<th>" + second_column_outline + "</th>\n");

			// 指定された処理の種類が"edit"である場合
			if (processing_type.equals("edit")) {
				data_list.append("\t\t\t\t\t<th>削除</th>\n");
			}
		}
		data_list.append("\t\t\t\t</tr>\n");
		int id_index = 0; // IDのインデックスを格納
		while (id_index < ids.length) {
			int data_output_count; // データの出力回数を格納

			// IDのインデックスがID数より2以上小さい場合
			if (id_index <= (ids.length - 2)) {
				data_output_count = 2; // データの出力回数を2に設定
			// 1以下である場合
			} else {
				data_output_count = 1; // データの出力回数を1に設定
			}

			// 行の取得
			data_list.append(getTab(tab_count + 1) + "<tr height=\"25\">\n");
			for (int i = 0; i < data_output_count; i++) {
				String[] row_data = databaseBean.getRowData(table_name, ids[id_index]); // 行データを格納
				String first_column_data = row_data[1]; // 1列目のデータを格納

				// 指定されたテーブル名に"opening_lecture"が含まれている場合
				if (table_name.indexOf("opening_lecture") != -1) {
					String first_column_data_table_name = null; // 1列目のデータのテーブル名を格納

					// 指定されたテーブル名がopening_lectureである場合
					if (table_name.equals("opening_lecture")) {
						first_column_data_table_name = "lecture"; // lectureを1列目のデータのテーブル名に設定
					// opening_lecture_lecturerである場合
					} else {
						first_column_data_table_name = "lecturer"; // lecturerを1列目のデータのテーブル名に設定
					}

					first_column_data += "(" + databaseBean.getColumnData(first_column_data_table_name, 1, row_data[1]) + ")"; // データの付加
				}

				// 指定された処理の種類が"delete"である場合
				if (processing_type.equals("delete")) {
					// リンクなしの要素の取得
					data_list.append("\t\t\t\t\t<input type=\"hidden\" name=\"id\" value=\"" + ids[id_index] + "\" />\n");
					data_list.append("\t\t\t\t\t<td>" + first_column_data + "</td>\n");
				// "delete"以外の種類である場合
				} else {
					String edit_page_url = null; // 編集ページのURLを格納

					// 指定された処理の種類が"record"である場合
					if (processing_type.equals("record")) {
						edit_page_url = utilityBean.getRecordEditPageURL(ids[id_index]);
					// "edit"である場合
					} else {
						edit_page_url = utilityBean.getDataEditPageURL(table_name, ids[id_index], null);
					}

					data_list.append(getTab(tab_count + 2) + "<td><a href=\"" + edit_page_url + "\">" + first_column_data + "</a></td>\n"); // リンク付きの要素の取得
				}

				data_list.append(getTab(tab_count + 2) + "<td>" + ids[id_index] + "</td>\n");

				// 指定された処理の種類が"edit"である場合
				if (processing_type.equals("edit")) {
					data_list.append("\t\t\t\t\t<td align=\"center\"><input type=\"checkbox\" name=\"id\" value=\"" + ids[id_index] + "\" /></td>\n"); // 削除チェックボックスの取得
				}

				id_index++; // IDのインデックスの加算
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

		return data_list.toString(); // データ一覧表を返す
	}

	/**
	 * 指定されたフォーム構成要素データのデータ編集テーブルを返す
	 * @param form_component_names フォーム構成要素名
	 * @param form_component_contents フォーム構成要素の内容
	 * @param tab_count タブ数
	 * @return データ編集テーブル
	 */
	public String getDataEditTable(String[] form_component_names, String[] form_component_contents, int tab_count) {
		StringBuffer data_edit_table = new StringBuffer(); // データ編集テーブルを格納

		// データ編集テーブルの取得
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

		return data_edit_table.toString(); // データ編集テーブルを返す
	}

	/**
	 * 指定された種類・学籍番号・点数から開講講義の点数リストを作成し、それを返す
	 * @param type 種類
	 * @param student_ids 学籍番号
	 * @param scores 点数
	 * @return 開講講義の点数リスト
	 * @exception SQLException
	 */
	public String getOpeningLectureScoreList(String type, String[] student_ids, String[] scores) throws SQLException {
		String[] from_100_to_0_array = utilityBean.getArray(100, 0); // 100から0の配列を格納
		String[] score_select_box_ids = new String[from_100_to_0_array.length + 1]; // 点数選択欄のIDを格納
		String[] score_select_box_outlines = new String[score_select_box_ids.length]; // 点数選択欄の見出しを格納

		// 点数選択欄データの1つ目の要素の設定
		score_select_box_ids[0] = "";
		score_select_box_outlines[0] = "(未登録)";

		// 点数選択欄データの2つ目以降の要素の設定
		for (int i = 1; i < score_select_box_ids.length; i++) {
			int from_100_to_0_array_index = i - 1; // 100から0の配列のインデックスを格納

			// 点数選択欄データの要素の設定
			score_select_box_ids[i] = from_100_to_0_array[from_100_to_0_array_index];
			score_select_box_outlines[i] = from_100_to_0_array[from_100_to_0_array_index];
		}

		final String[] SCORE_COLOR_NAMES = {"red", "purple", "green", "blue"}; // 点数の色の種類を格納
		StringBuffer opening_lecture_score_list = new StringBuffer(); // 開講講義の点数リストを格納

		// 開講講義の点数リストの取得
		opening_lecture_score_list.append("<table border=\"1\" bgcolor=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\">\n");
		opening_lecture_score_list.append("\t\t\t\t<tr height=\"20\">\n");
		opening_lecture_score_list.append("\t\t\t\t\t<th>学籍番号</th>\n");
		opening_lecture_score_list.append("\t\t\t\t\t<th width=\"100\">氏名</th>\n");
		opening_lecture_score_list.append("\t\t\t\t\t<th width=\"80\">点数</th>\n");
		opening_lecture_score_list.append("\t\t\t\t</tr>\n");
		for (int i = 0; i < student_ids.length; i++) {
			opening_lecture_score_list.append("\t\t\t\t<tr height=\"25\">\n");
			opening_lecture_score_list.append("\t\t\t\t\t<td>" + student_ids[i] + "</td>\n");
			opening_lecture_score_list.append("\t\t\t\t\t<td>" + databaseBean.getColumnData("student", 1, student_ids[i]) + "</td>\n");
			opening_lecture_score_list.append("\t\t\t\t\t<td align=\"center\">");

			// 指定された種類が"edit"である場合
			if (type.equals("edit")) {
				opening_lecture_score_list.append(getSelectBox(student_ids[i], scores[i], score_select_box_ids, score_select_box_outlines, 5)); // 点数の選択欄の付加
			// "edit"でない場合
			} else {
				int grade = (Integer.parseInt(scores[i]) - 50) / 10; // 等級を格納

				// 取得した等級が0未満である場合
				if (grade < 0) {
					grade = 0; // 0を等級に設定
				// 3を超えている場合
				} else if (grade > 3) {
					grade = 3; // 3を等級に設定
				}

				// 取得した等級が0以外である場合
				if (grade != 0) {
					opening_lecture_score_list.append("<input type=\"hidden\" name=\"" + student_ids[i] + "\" value=\"" + scores[i] + "\" />"); // hidden構成要素の付加
				}

				opening_lecture_score_list.append("<font color=\"" + SCORE_COLOR_NAMES[grade] + "\">" + scores[i] + "(" + utilityBean.getEvaluation(scores[i]) + ")</font>"); // 点数の付加
			}

			opening_lecture_score_list.append("</td>\n");
			opening_lecture_score_list.append("\t\t\t\t</tr>\n");
		}
		opening_lecture_score_list.append("\t\t\t</table>\n");

		return opening_lecture_score_list.toString(); // 開講講義の点数リストを返す
	}

	/**
	 * 指定されたデータからセレクトボックスを作成し、それを返す
	 * @param name 名前
	 * @param id ID
	 * @param option_ids オプションのID
	 * @param tab_count タブ数
	 * @return セレクトボックス
	 */
	public String getSelectBox(String name, String id, String[] list_ids, int tab_count) {
		return getSelectBox(name, id, list_ids, null, true, tab_count);
	}

	/**
	 * 指定されたデータからセレクトボックスを作成し、それを返す
	 * @param name 名前
	 * @param id ID
	 * @param option_ids オプションのID
	 * @param option_outlines オプションの見出し
	 * @param tab_count タブ数
	 * @return セレクトボックス
	 */
	public synchronized String getSelectBox(String name, String id, String[] list_ids, String[] list_outlines, int tab_count) {
		return getSelectBox(name, id, list_ids, list_outlines, false, tab_count);
	}

	/**
	 * 指定されたデータからセレクトボックスを作成し、それを返す
	 * @param name 名前
	 * @param id ID
	 * @param option_ids オプションのID
	 * @param option_outlines オプションの見出し
	 * @param is_id_displayed IDを表示するかどうかの設定
	 * @param tab_count タブ数
	 * @return セレクトボックス
	 */
	public synchronized String getSelectBox(String name, String id, String[] option_ids, String[] option_outlines, boolean is_id_displayed, int tab_count) {
		StringBuffer select_box = new StringBuffer(); // セレクトボックスを格納

		// セレクトボックスの取得
		select_box.append("<select name=\"" + name + "\">\n");
		if (option_ids != null) {
			for (int i = 0; i < option_ids.length; i++) {
				select_box.append(getTab(tab_count + 1) + "<option value=\"" + option_ids[i] + "\"");

				// 指定されたIDがnullではなく、現在のオプションIDの要素と等しい場合
				if (id != null && id.equals(option_ids[i])) {
					select_box.append(" selected"); // "selected"の付加
				}

				select_box.append(">");

				// IDを表示するように設定されている場合
				if (is_id_displayed) {
					select_box.append(option_ids[i]);
				}

				// オプションの見出しがnullではない場合
				if (option_outlines != null) {
					// IDを表示するように設定されている場合
					if (is_id_displayed) {
						select_box.append("(" + option_outlines[i] + ")"); // 括弧をつけた見出しの取得
					// 設定されていない場合
					} else {
						select_box.append(option_outlines[i]); // 見出しの取得
					}
				}

				select_box.append("</option>\n");
			}
		}
		select_box.append(getTab(tab_count) + "</select>");

		return select_box.toString(); // セレクトボックスを返す
	}

	/**
	 * 指定された数のタブキーを返す
	 * @param tab_count タブの数
	 * @return タブキー
	 */
	public String getTab(int tab_count) {
		StringBuffer tab = new StringBuffer(); // タブキーを格納

		// 指定された数のタブキーの付加
		for(int i = 0; i < tab_count; i++) {
			tab.append("\t");
		}

		return tab.toString(); // タブキーを返す
	}
}
