<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.*" %>
<%!
	/**
	 * 指定されたデータからtext構成要素を作成し、それを返す
	 * @param name 名前
	 * @param value 値
	 * @return text構成要素
	 */
	private String getTextComponent(String name, String value) {
		StringBuffer text_component = new StringBuffer(); // text構成要素を格納

		// text構成要素の取得
		text_component.append("<input type=\"text\" name=\"" + name + "\" ");
		if (value != null) {
			text_component.append("value=\"" + value + "\" "); // 値の付加
		}
		text_component.append("/>");

		return text_component.toString(); // text構成要素を返す
	}

	/**
	 * 指定されたデータからフォーム構成要素を作成し、それを返す
	 * @param type 種類
	 * @param name 名前
	 * @param id ID
	 * @param option_ids 選択欄のID
	 * @param option_outlines 選択欄の見出し
	 * @return フォーム構成要素
	 */
	private String getFormComponent(String type, String name, String id, String[] option_ids, String[] option_outlines) {
		StringBuffer form_component = new StringBuffer(); // フォーム構成要素を格納

		// フォーム構成要素の取得
		for (int i = 0; i < option_ids.length; i++) {
			// 表示する選択欄が2つ目以降のものである場合
			if (i > 0) {
				form_component.append("\t\t\t\t\t\t");
			}

			form_component.append("<input type=\"" + type + "\" name=\""+ name + "\" value=\"" + option_ids[i] + "\"");

			// 指定されたIDが現在の選択欄のIDと等しい、又は指定された種類がradioで表示する選択欄が1つ目のものでIDが指定されていない
			if ((id != null && id.equals(option_ids[i])) || (type.equals("radio") && i == 0 && id == null)) {
				form_component.append(" checked");
			}

			form_component.append(" />");

			// 指定された種類がradioである場合
			if (type.equals("radio")) {
				// 選択欄の見出しが指定されている場合
				if (option_outlines != null) {
					form_component.append(option_outlines[i]);
				// 指定されていない場合
				} else {
					form_component.append(option_ids[i]);
				}
			}

			form_component.append("\n");
		}

		return form_component.toString(); // フォーム構成要素を返す
	}
%>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String table_name = request.getParameter("table_name"); // 指定されたテーブル名を格納
	String edit_type = null; // 編集の種類を格納
	String id = request.getParameter("id"); // 指定されたIDを格納
	String data_affiliation_number = null; // データ所属番号を格納
	String[] form_component_names = utility_bean.getFormComponentNames(table_name); // フォームの構成要素名を格納
	String[] form_component_data = new String[form_component_names.length]; // フォームの構成要素データを格納

	// IDが指定されている場合
	if (id != null) {
		edit_type = "edit"; // "edit"を編集の種類に設定
		data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, id); // データ所属番号の取得
		String[] table_row_data = database_bean.getRowData(table_name, id); // テーブルの行データを格納
		String table_row_data_data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, table_row_data[0]); // 指定されたテーブル・行データのデータ所属番号を格納
		Vector vector_form_component_data = new Vector(); // フォームの構成要素データを格納

		// データ所属番号が取得できなかった場合
		if (table_row_data_data_affiliation_number == null) {
			vector_form_component_data.addElement(table_row_data[0]); // IDの要素の付加
		// 取得できた場合
		} else {
			vector_form_component_data.addElement(table_row_data_data_affiliation_number); // データ所属番号の要素の付加
			vector_form_component_data.addElement(table_row_data[0].substring(table_row_data_data_affiliation_number.length())); // データ番号の要素の付加
		}

		// ID以外の要素の付加
		for (int i = 1; i < table_row_data.length; i++) {
			vector_form_component_data.addElement(table_row_data[i]);
		}

		// フォームの構成要素データの取得
		for (int i = 0; i < vector_form_component_data.size(); i++) {
			form_component_data[i] = (String) vector_form_component_data.elementAt(i);
		}
	// 指定されていない場合
	} else {
		edit_type = "new"; // "new"を編集の種類に設定
		data_affiliation_number = request.getParameter("data_affiliation_number"); // 指定されたデータ所属番号の取得

		// データ所属番号が取得できた場合
		if (data_affiliation_number != null) {
			form_component_data[0] = data_affiliation_number; // データ所属番号をフォーム構成要素のデータ所属番号要素に設定
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	Vector vector_form_component_names = new Vector(); // フォームの構成要素名を格納
	Vector vector_form_component_contents = new Vector(); // フォームの構成要素内容を格納

	// 指定されたテーブルが科目テーブルである場合
	if (table_name.equals("course")) {
		// データの編集である場合
		if (edit_type.equals("edit")) {
			// 科目IDの構成要素の設定
			vector_form_component_names.addElement(form_component_names[0]);
			vector_form_component_contents.addElement("<input type=\"hidden\" name=\"" + form_component_names[0] + "\" value=\"" + form_component_data[0] + "\" />" + form_component_data[0] + "\n");
		}

		// 科目名の構成要素の設定
		vector_form_component_names.addElement(form_component_names[1]);
		vector_form_component_contents.addElement(getTextComponent(form_component_names[1], form_component_data[1]));
	// その他のテーブルである場合
	} else {
		// データ所属番号とデータ番号の構成要素の設定
		String[][] data_affiliation_number_form_component_data = utility_bean.getDataAffiliationNumberFormComponentData(table_name); // データ所属番号のフォーム構成要素データを格納
		vector_form_component_names.addElement(form_component_names[0]);
		if (edit_type.equals("new")) {
			vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[0], form_component_data[0], data_affiliation_number_form_component_data[0], data_affiliation_number_form_component_data[1], true, 6));
		} else {
			vector_form_component_contents.addElement("<input type=\"hidden\" name=\"" + form_component_names[0] + "\" value=\"" + form_component_data[0] + "\" />" + form_component_data[0] + "\n");
			vector_form_component_names.addElement(form_component_names[1]);
			vector_form_component_contents.addElement("<input type=\"hidden\" name=\"" + form_component_names[1] + "\" value=\"" + form_component_data[1] + "\" />" + form_component_data[1] + "\n");
		}

		// 指定されたテーブルが開講講義テーブルである場合
		if (table_name.equals("opening_lecture")) {
			// 講義IDの構成要素の設定
			String[][] lecture_data = database_bean.getRowsData("lecture"); // 講義データを格納
			String[] lecture_ids = null; // 講義IDを格納
			String[] lecture_names = null; // 講義名を格納
			if (lecture_data != null) {
				// 講義データの要素数の設定
				lecture_ids = new String[lecture_data.length];
				lecture_names = new String[lecture_data.length];

				// 講義データの取得
				for (int i = 0; i < lecture_data.length; i++) {
					lecture_ids[i] = lecture_data[i][0];
					lecture_names[i] = lecture_data[i][1];
				}
			}
			vector_form_component_names.addElement(form_component_names[2]);
			vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[2], form_component_data[2], lecture_ids, lecture_names, true, 6));

			// 必要開講講義IDの構成要素の設定
			String[][] opening_lecture_data = database_bean.getRowsData("opening_lecture"); // 開講講義データを格納
			String[] necessary_opening_lecture_ids = null; // 必要開講講義IDを格納
			if (opening_lecture_data == null) {
				necessary_opening_lecture_ids = new String[1]; // 1を必要開講講義IDの要素数に設定
			} else {
				necessary_opening_lecture_ids = new String[opening_lecture_data.length + 1]; // 講義ID数+1を必要開講講義IDの要素数に設定
			}
			String[] necessary_opening_lecture_names = new String[necessary_opening_lecture_ids.length]; // 必要開講講義名を格納
			necessary_opening_lecture_names[0] = "なし"; // 必要開講講義名の設定
			for (int i = 1; i < necessary_opening_lecture_ids.length; i++) {
				int opening_lecture_data_index = i - 1; // 開講講義データのインデックスを格納
				necessary_opening_lecture_ids[i] = opening_lecture_data[opening_lecture_data_index][0]; // 必要開講講義IDの取得

				// 必要開講講義名の取得
				for (int j = 0; j < lecture_data.length; j++) {
					// 現在アクセスしている開講講義データが持つ講義IDが現在アクセスしている講義データの講義IDと等しい場合
					if (opening_lecture_data[opening_lecture_data_index][1].equals(lecture_data[j][0])) {
						necessary_opening_lecture_names[i] = lecture_data[j][1]; // 必要開講講義名の取得
						break; // ループから抜ける
					}
				}
			}
			vector_form_component_names.addElement(form_component_names[3]);
			vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[3], form_component_data[3], necessary_opening_lecture_ids, necessary_opening_lecture_names, true, 6));
		// 開講講義担当教官テーブルである場合
		} else if (table_name.equals("opening_lecture_lecturer")) {
			// 教官名の構成要素の設定
			vector_form_component_names.addElement(form_component_names[2]);
			vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[2], form_component_data[2], database_bean.getColumnDataArray("lecturer", 0), database_bean.getColumnDataArray("lecturer", 1), true, 6));
		// その他のテーブルである場合
		} else {
			// 名前の構成要素の設定
			vector_form_component_names.addElement(form_component_names[2]);
			vector_form_component_contents.addElement(getTextComponent(form_component_names[2], form_component_data[2]));

			// 指定されたテーブルが学生テーブルである場合
			if (table_name.equals("student")) {
				// データの編集である場合
				if (edit_type.equals("edit")) {
					// パスワードの構成要素の設定
					vector_form_component_names.addElement(form_component_names[3]);
					vector_form_component_contents.addElement("<input type=\"hidden\" name=\"" + form_component_names[3] + "\" value=\"" + form_component_data[3] + "\" />" + form_component_data[3] + "\n");
				}

				// 生年月日の構成要素の設定
				int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // 月を格納
				int year = Calendar.getInstance().get(Calendar.YEAR); // 現在の年を格納
				int year_day = 365; // 1年の日数を格納
				if ((year % 4) == 0 && (year % 100) != 0 || (year % 400) == 0) {
					days[1] = 29; // 2月の日数を29日に設定
					year_day = 366; // 1年の日数を366に設定
				}
				String[] birthday_ids = new String[year_day]; // 誕生日のIDを格納
				String[] birthday_outlines = new String[year_day]; // 誕生日の見出しを格納
				int birthday_index = 0; // 誕生日のインデックスを格納

				for (int i = 1; i <= 12; i++) {
					String month = null; // 月を格納

					// 値が10未満である場合
					if (i < 10) {
						month = "0" + i; // 月の前に"0"を付けた値を設定
					// 10以上である場合
					} else {
						month = "" + i; // 月の値を設定
					}

					for (int j = 1; j <= days[i - 1]; j++) {
						String day = null; // 日を格納

						// 値が10未満である場合
						if (j < 10) {
							day = "0" + j; // // 日の前に"0"を付けた値を設定
						// 10以上である場合
						} else {
							day = "" + j; // 日の値を設定
						}

						birthday_ids[birthday_index] = month + day; // 誕生日のIDの取得
						birthday_outlines[birthday_index] = month + "月" + day + "日"; // 誕生日の見出しの取得
						birthday_index++; // インデックスの加算
					}
				}
				vector_form_component_names.addElement(form_component_names[4]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[4], form_component_data[4], birthday_ids, birthday_outlines, true, 6));

				// 国籍の構成要素の設定
				String[] nationality_ids = {"0", "1"}; // 国籍のIDを格納
				String[] nationality_names = {"日本", "外国"}; // 国籍名を格納
				vector_form_component_names.addElement(form_component_names[5]);
				vector_form_component_contents.addElement(getFormComponent("radio", form_component_names[5], form_component_data[5], nationality_ids, nationality_names));

				String[] special_lecture_affiliation_ids = database_bean.getColumnDataArray("lecture_affiliation", 0, "0"); // 専門の講義所属IDを格納
				Vector vector_lecturer_ids = new Vector(); // 教官IDを格納
				Vector vector_lecturer_names = new Vector(); // 教官名を格納

				// 教官データの取得
				for (int i = 0; i < special_lecture_affiliation_ids.length - 1; i++) {
					String[] lecturer_ids = database_bean.getColumnDataArray("lecturer", 0, special_lecture_affiliation_ids[i].substring(1)); // 教官IDを格納

					for (int j = 0; j < lecturer_ids.length; j++) {
						vector_lecturer_ids.addElement(lecturer_ids[j]);
						vector_lecturer_names.addElement(database_bean.getColumnData("lecturer", 1, lecturer_ids[j]));
					}
				}

				String[][] lecturer_data = new String[2][vector_lecturer_ids.size()]; // 教官データを格納

				// 教官データの取得
				for (int i = 0; i < lecturer_data[0].length; i++) {
					lecturer_data[0][i] = (String) vector_lecturer_ids.elementAt(i);
					lecturer_data[1][i] = (String) vector_lecturer_names.elementAt(i);
				}

				// 基礎演習教官IDの構成要素の設定
				vector_form_component_names.addElement(form_component_names[6]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[6], form_component_data[6], lecturer_data[0], lecturer_data[1], true, 6));

				String[] from_0_to_20_array = utility_bean.getArray(0, 20); // 0から19の番号の配列を格納
				String[] from_0_to_9_array = utility_bean.getArray(0, 9); // 0から9の番号の配列を格納

				// 情報リテラシークラスの構成要素の設定
				vector_form_component_names.addElement(form_component_names[7]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[7], form_component_data[7], from_0_to_20_array, 6));

				// 健康と生活クラスの構成要素の設定
				vector_form_component_names.addElement(form_component_names[8]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[8], form_component_data[8], from_0_to_9_array, 6));

				// スポーツ科学クラスの構成要素の設定
				vector_form_component_names.addElement(form_component_names[9]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[9], form_component_data[9], from_0_to_20_array, 6));

				// 英語Iクラスの構成要素の設定
				vector_form_component_names.addElement(form_component_names[10]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[10], form_component_data[10], from_0_to_20_array, 6));

				// 英語IIaクラスの構成要素の設定
				vector_form_component_names.addElement(form_component_names[11]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[11], form_component_data[11], from_0_to_20_array, 6));

				// 英語IIbクラスの構成要素の設定
				vector_form_component_names.addElement(form_component_names[12]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[12], form_component_data[12], from_0_to_20_array, 6));

				// 外国語IDの構成要素の設定
				String[][] foreign_language_data = database_bean.getRowsData("lecture_affiliation", "2"); // 外国語データを格納
				String[][] second_foreign_language_data = new String[2][foreign_language_data.length - 2]; // 第2外国語データを格納
				for (int i = 1; i < foreign_language_data.length - 1; i++) {
					int second_foreign_language_data_index = i - 1; // 第2外国語データのインデックスを格納

					// 第2外国語データの取得
					second_foreign_language_data[0][second_foreign_language_data_index] = foreign_language_data[i][0].substring(1);
					second_foreign_language_data[1][second_foreign_language_data_index] = foreign_language_data[i][1];
				}
				vector_form_component_names.addElement(form_component_names[13]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[13], form_component_data[13], second_foreign_language_data[0], second_foreign_language_data[1], true, 6));

				// 外国語クラスの構成要素の設定
				vector_form_component_names.addElement(form_component_names[14]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[14], form_component_data[14], from_0_to_9_array, 6));

				// ゼミ教官IDの構成要素の設定
				vector_form_component_names.addElement(form_component_names[15]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[15], form_component_data[15], lecturer_data[0], lecturer_data[1], true, 6));
			// 講義テーブルである場合
			} else if (table_name.equals("lecture")) {
				// 単位の構成要素の設定
				String[] credits = utility_bean.getArray(1, 4);
				vector_form_component_names.addElement(form_component_names[3]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[3], form_component_data[3], credits,  6));

				Vector vector_year_outlines = new Vector(); // 年次の見出しを格納

				// 年次の見出しの取得
				for (int i = 1; i <= 4; i++) {
					for(int j = i; j <= 4; j++) {
						String year_outline = "" + i; // 年次の見出しを格納

						// 2つの値が等しくない場合
						if (i != j) {
							if (j == 3) {
								continue; // 次の要素に処理を移す
							}

							year_outline += "～" + j; // もう一方の値の付加
						}

						vector_year_outlines.addElement(year_outline + "回生"); // 年次の見出しの付加
					}
				}
				vector_year_outlines.addElement("履修不可");

				String[] year_ids = new String[vector_year_outlines.size()]; // 年次のIDを格納
				String[] year_outlines = new String[year_ids.length]; // 年次の見出しを格納

				// 年次データの取得
				for (int i = 0; i < year_ids.length; i++) {
					year_ids[i] = "" + i;
					year_outlines[i] = (String) vector_year_outlines.elementAt(i);
				}

				// 目安年次(昼間主)の構成要素の設定
				vector_form_component_names.addElement(form_component_names[4]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[4], form_component_data[4], year_ids, year_outlines, true, 6));

				// 目安年次(夜間主)の構成要素の設定
				vector_form_component_names.addElement(form_component_names[5]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[5], form_component_data[5], year_ids, year_outlines, true, 6));

				// 昼間主受講可能年次の構成要素の設定
				vector_form_component_names.addElement(form_component_names[6]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[6], form_component_data[6], year_ids, year_outlines, true, 6));

				// 夜間主受講可能年次の構成要素の設定
				vector_form_component_names.addElement(form_component_names[7]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[7], form_component_data[7], year_ids, year_outlines, true, 6));

				// 学生所属年の取得
				int present_year = Calendar.getInstance().get(Calendar.YEAR); // 現在の年を格納
				String[] years = utility_bean.getArray(present_year - 11, present_year); // 年を格納
				String[] student_affiliation_years = new String[years.length + 1]; // 学生所属年を格納
				for (int i = 1; i < student_affiliation_years.length; i++) {
					student_affiliation_years[i] = years[i - 1];
				}

				// 履修可能年始端の構成要素の設定
				vector_form_component_names.addElement(form_component_names[8]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[8], form_component_data[8], student_affiliation_years, 6));

				// 履修可能年終端の構成要素の設定
				vector_form_component_names.addElement(form_component_names[9]);
				vector_form_component_contents.addElement(html_bean.getSelectBox(form_component_names[9], form_component_data[9], student_affiliation_years, 6));

				String[] string_true = {"1"}; // true(1)を格納

				// 経済学科が履修可能であるかどうかの構成要素の設定
				vector_form_component_names.addElement(form_component_names[10]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[10], form_component_data[10], string_true, null));

				// ファイナンス学科が履修可能であるかどうかの構成要素の設定
				vector_form_component_names.addElement(form_component_names[11]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[11], form_component_data[11], string_true, null));

				// 企業経営学科が履修可能であるかどうかの構成要素の設定
				vector_form_component_names.addElement(form_component_names[12]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[12], form_component_data[12], string_true, null));

				// 会計情報学科が履修可能であるかどうかの構成要素の設定
				vector_form_component_names.addElement(form_component_names[13]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[13], form_component_data[13], string_true, null));

				// 情報管理学科が履修可能であるかどうかの構成要素の設定
				vector_form_component_names.addElement(form_component_names[14]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[14], form_component_data[14], string_true, null));

				// 社会システム学科が履修可能であるかどうかの構成要素の設定
				vector_form_component_names.addElement(form_component_names[15]);
				vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[15], form_component_data[15], string_true, null));

				// データの編集である場合
				if (edit_type.equals("edit")) {
					// 同種類のデータが存在する場合に別の番号で登録されるかどうかの構成要素の設定
					vector_form_component_names.addElement(form_component_names[16]);
					vector_form_component_contents.addElement(getFormComponent("checkbox", form_component_names[16], form_component_data[16], string_true, null));
				}
			}
		}
	}

	form_component_names = new String[vector_form_component_names.size()]; // フォームの構成要素名の再初期化
	String[] form_component_contents = new String[form_component_names.length]; // フォームの構成要素内容を格納

	// フォームの構成要素データの取得
	for (int i = 0; i < form_component_names.length; i++) {
		form_component_names[i] = (String) vector_form_component_names.elementAt(i);
		form_component_contents[i] = (String) vector_form_component_contents.elementAt(i);
	}

	String japanese_table_name = utility_bean.getJapaneseWord(table_name); // 指定されたテーブル名の日本語名を格納
%>
<%= html_bean.getHeader(japanese_table_name + "データの" + utility_bean.getJapaneseWord(edit_type)) %>
		内容の編集を行い、[送信]ボタンをクリックして下さい。<br />
		<form method="POST" action="./confirm.jsp">
			<input type="hidden" name="table_name" value="<%= table_name %>" />
			<input type="hidden" name="edit_type" value="<%= edit_type %>" />
			<%= html_bean.getDataEditTable(form_component_names, form_component_contents, 3) %>
			<input type="submit" value="送信" />
			<input type="reset" value="取消" />
		</form>
		<a href="<%= utility_bean.getDataSelectPageURL(table_name, data_affiliation_number) %>"><%= japanese_table_name %>データの選択</a><br />

		<%= html_bean.getFooter() %>
