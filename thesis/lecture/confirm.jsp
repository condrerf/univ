<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.regex.Pattern, java.util.Vector" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String student_id = request.getParameter("student_id"); // 学籍番号を格納
	String[][] opening_lecture_ids = utility_bean.getOpeningLectureIDs(request); // 開講講義IDを格納
	boolean[][][] is_error_existed = new boolean[opening_lecture_ids.length][opening_lecture_ids[0].length][2]; // エラーが存在するかどうかを格納
	Vector not_selected_necessary_opening_lecture_ids = new Vector(); // 選択されていない必要開講講義IDを格納
	String[][] lecture_data = database_bean.getRowsData("lecture"); // 講義データを格納
	int entered_year = utility_bean.getEnteredYear(student_id); // 入学した年を格納
	int[][] lecture_data_indexes = new int[opening_lecture_ids.length][opening_lecture_ids[0].length]; // 講義データのインデックスを格納
	int[] selected_opening_lecture_credits = new int[2]; // 選択された開講講義の単位を格納

	// 選択された開講講義のチェック
	check_selected_opening_lecture: for (int i = 0; i < opening_lecture_ids.length; i++) {
		for (int j = 0; j < opening_lecture_ids[i].length; j++) {
			// 処理が集中講義の最終要素まで終えている場合
			if (i == 5 && j == 5) {
				break check_selected_opening_lecture; // ループから抜ける
			}

			// 現在アクセスしている日時に開講講義IDが選択されていない場合
			if (opening_lecture_ids[i][j].equals("null")) {
				continue; // 次の要素に処理を移す
			}

			String[] opening_lecture_data = database_bean.getRowData("opening_lecture", opening_lecture_ids[i][j]); // 開講講義講義データを格納

			// 現在アクセスしている開講講義に必要な開講講義があり、その開講講義が選択されていない場合
			if (opening_lecture_data[2] != null && !opening_lecture_data[2].equals(opening_lecture_ids[Integer.parseInt(opening_lecture_data[2].substring(1, 2))][Integer.parseInt(opening_lecture_data[2].substring(2, 3))])) {
				is_error_existed[i][j][0] = true; // 現在アクセスしている日時に必要開講講義のエラーが存在すると設定
				not_selected_necessary_opening_lecture_ids.addElement(opening_lecture_data[2]); // 選択されていない必要開講講義IDの付加
			}

			// 講義データのインデックスの取得
			for (int k = 0; k < lecture_data.length; k++) {
				// 現在アクセスしている講義が現在アクセスしている日時に選択された講義と等しい場合
				if (lecture_data[k][0].equals(opening_lecture_data[1])) {
					int credit = Integer.parseInt(lecture_data[k][2]); // 単位を格納

					// 2000年以降に入学した学生で、現在アクセスしている講義が通年のものである場合
					if (entered_year > 2000 && lecture_data[k][0].substring(0, 1).equals("0")) {
						credit /= 2; // 単位を半分に加工
					}

					selected_opening_lecture_credits[j / 5] += credit; // 現在アクセスしている日時の要素に選択された開講講義の単位を加算
					lecture_data_indexes[i][j] = k; // 現在の要素番号を現在アクセスしている日時の講義データのインデックスに設定
					break; // ループから抜ける
				}
			}
		}
	}

	String[][] mastered_lecture_data = database_bean.getRowsData("mastered_lecture", student_id); // 履修済み講義データを格納
	int another_course_number = (11 - utility_bean.getStudentAffiliationID(student_id)) / 6; // 別コースの番号を格納
	boolean is_seminar_i_mastered = false; // 演習第一を履修しているかどうかを格納
	int another_course_got_credit = 0; // 別コースの取得した単位を格納

	// 履修済み講義データが取得できた場合
	if (mastered_lecture_data != null) {
		// 取得した単位と演習第一を履修しているかどうかのチェック
		for (int i = 0; i < mastered_lecture_data.length; i++) {
			int lecture_affiliation_number = Integer.parseInt(mastered_lecture_data[i][3]); // 講義所属番号を格納

			// 現在アクセスしている履修済み講義データが経済学部のものである場合
			if (lecture_affiliation_number < selected_opening_lecture_credits.length) {
				// 現在アクセスしている履修済み講義データが演習第一のものである場合
				if (mastered_lecture_data[i][0].substring(5, 10).equals("06000")) {
					is_seminar_i_mastered = true; // 演習第一を履修していると設定
				}

				// 設定された別コースの番号と現在アクセスしている履修済み講義データの講義所属番号が等しい場合
				if (another_course_number == lecture_affiliation_number) {
					another_course_got_credit += Integer.parseInt(database_bean.getColumnData("lecture", 2, mastered_lecture_data[i][0].substring(5))); // 別コースの取得した単位の加算
				}
			}
		}
	}

	// 指定された学籍番号の学生が昼間主で、演習第一を履修していない場合
	if (another_course_number == 1 && !is_seminar_i_mastered) {
		int credit_sum = selected_opening_lecture_credits[0] + selected_opening_lecture_credits[1]; // 単位の合計を格納
		String error_parameter_name = "選択講義の単位の合計"; // エラーパラメータ名を格納

		// 2000年までに入学した学生である場合
		if (entered_year <= 2000) {
			int spring_semester_registered_opening_lecture_credit = 0; // 春学期に登録した開講講義の単位を格納

			// 現在が秋学期である場合
			if (utility_bean.getSemesterNumber() == 2) {
				String[][] registered_opening_lecture_data = database_bean.getRowsData("registered_opening_lecture", student_id); // 登録済み開講講義データを格納

				// 登録済み開講講義データが取得できた場合
				if (registered_opening_lecture_data != null) {
					// 春学期に登録した開講講義の単位と登録済み開講講義IDの取得
					for (int i = 0; i < registered_opening_lecture_data.length; i++) {
						// 現在アクセスしている登録した開講講義データが春学期に登録されたものである場合
						if (registered_opening_lecture_data[i][0].substring(5, 6).equals("1")) {
							spring_semester_registered_opening_lecture_credit += Integer.parseInt(database_bean.getColumnData("lecture", 2, database_bean.getColumnData("opening_lecture", 1, registered_opening_lecture_data[i][1])));
						}
					}

					credit_sum += spring_semester_registered_opening_lecture_credit; // 春学期に登録した開講講義の単位を単位の合計に加算
				}
			}

			// 単位の合計が56を超えている場合
			if (credit_sum > 56) {
				// 春学期に登録した開講講義の単位が0でない場合
				if (spring_semester_registered_opening_lecture_credit != 0) {
					error_parameter_name += "(春学期登録分を含む)"; // エラーパラメータ名の付加
				}

				application.getRequestDispatcher(utility_bean.getErrorPageURI("credit_error", error_parameter_name, credit_sum)).forward(request, response); // エラーページへの転送
			}
		// 2000年以降に入学した学生で、単位の合計が26を超えている場合
		} else if (credit_sum > 26) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("credit_error", error_parameter_name, credit_sum)).forward(request, response); // エラーページへの転送
		}
	}

	int another_course_credit_sum = selected_opening_lecture_credits[another_course_number] + another_course_got_credit; // 別コースの単位の合計を格納

	// 別コースの単位の合計が30を超えている場合
	if(another_course_credit_sum > 30) {
		StringBuffer error_parameter_name = new StringBuffer(); // エラーパラメータ名を格納

		// 別コースの番号が昼間のものに設定されている場合
		if (another_course_number == 0) {
			error_parameter_name.append("昼"); // "昼"をエラーパラメータ名に付加
		// 夜間のものに設定されている場合
		} else {
			error_parameter_name.append("夜"); // "夜"をエラーパラメータ名に付加
		}

		error_parameter_name.append("間主の講義の単位の合計"); // エラーパラメータ名の付加

		// 別コースの取得した単位がある場合
		if (another_course_got_credit != 0) {
			error_parameter_name.append("(過去履修分を含む)"); // エラーパラメータ名の付加
		}

		application.getRequestDispatcher(utility_bean.getErrorPageURI("another_course_credit_error", error_parameter_name.toString(), another_course_credit_sum)).forward(request, response); // エラーページへの転送
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	String[][] column_data = new String[opening_lecture_ids.length][opening_lecture_ids[0].length]; // 行データを格納
	int semester_number = utility_bean.getSemesterNumber(); // 学期番号を格納

	semester_number = 1; // テスト用の設定(開発終了後は削除)

	Vector error_existed_opening_lecture_ids = new Vector(); // エラーが存在する開講講義IDを格納
	int final_class_number = opening_lecture_ids[0].length - 1; // 最終時限番号を格納

	// 各列データの取得
	get_column_data: for (int i = 0; i < column_data.length; i++) {
		for (int j = 0; j < column_data[i].length; j++) {
			// 処理が集中講義の最終要素まで終えている場合
			if (i == 5 && j == 5) {
				break get_column_data; // ループから抜ける
			}

			StringBuffer string_buffer_column_data = new StringBuffer(); // 行データを格納
			String registered_opening_lecture_id = database_bean.getColumnData("registered_opening_lecture", 1, student_id + semester_number + i + j); // 登録開講講義IDを格納

			// 登録開講講義IDが取得でき、そのIDが現在アクセスしているIDと異なる場合
			if (registered_opening_lecture_id != null && !registered_opening_lecture_id.equals(opening_lecture_ids[i][j])) {
				// 以前の登録状況の付加
				string_buffer_column_data.append("<font color=\"gray\">" + database_bean.getColumnData("lecture", 1, database_bean.getColumnData("opening_lecture", 1, registered_opening_lecture_id)) + "<br />↓<br /></font>\n");
				string_buffer_column_data.append("\t\t\t\t\t\t\t\t");
			}

			// 現在アクセスしている日時に講義が選択されていない場合
			if (opening_lecture_ids[i][j].equals("null")) {
				string_buffer_column_data.append("<font color=\"gray\">(選択なし)</font>"); // 行データの設定
			// 選択されている場合
			} else {
				String present_lecture_type_id = lecture_data[lecture_data_indexes[i][j]][0].substring(0, 5); // 現在の講義所属IDを格納

				// 現在アクセスしている日時に同種類講義選択エラーが設定されていない場合
				if (!is_error_existed[i][j][1]) {
					int day_number = i; // 曜日番号を格納
					int class_number = j; // 時限番号を格納

					// アクセス位置が集中講義の最終要素になるまで繰り返し
					while (day_number <= 4 || class_number <= 3) {
						// 時限番号が最終時限番号と等しい場合
						if (class_number == final_class_number) {
							// 次の曜日の最初の時限への設定
							day_number++;
							class_number = 0;
						// 等しくない場合
						} else {
							class_number++; // 時限番号の加算
						}

						// 現在アクセスしている日時に講義が選択されており、比較元の日時に選択されている講義と同種類である場合
						if(!opening_lecture_ids[day_number][class_number].equals("null") && lecture_data[lecture_data_indexes[day_number][class_number]][0].substring(0, 5).equals(present_lecture_type_id)) {
							// 同種類講義選択エラーの設定
							is_error_existed[i][j][1] = true;
							is_error_existed[day_number][class_number][1] = true;
						}
					}
				}

				String lecture_name_font_color = null; // 講義名のフォントカラーを格納

				// 現在アクセスしている日時に必要講義未選択エラー、又は同種類講義選択エラーが設定されている場合
				if (is_error_existed[i][j][0] || is_error_existed[i][j][1]) {
					lecture_name_font_color = "red"; // 講義名のフォントカラーを赤に設定
					boolean is_same_type_lecture_added = false; // 同種類の講義が付加されているかどうかを格納

					// 同種類講義選択エラーが設定されている場合
					if (is_error_existed[i][j][1]) {
						// 同種類の講義が既にエラーが存在する開講講義IDのリストに付加されているかどうかのチェック
						for (int k = 0; k < error_existed_opening_lecture_ids.size(); k++) {
							String opening_lecture_id = error_existed_opening_lecture_ids.elementAt(k).toString(); // 開講講義IDを格納
							String lecture_type_id = lecture_data[lecture_data_indexes[Integer.parseInt(opening_lecture_id.substring(1, 2))][Integer.parseInt(opening_lecture_id.substring(2, 3))]][0].substring(0, 5); // 講義所属IDを格納

							// 同種類の講義が選択されている開講講義IDが付加されている場合
							if (lecture_type_id.equals(lecture_data[lecture_data_indexes[i][j]][0].substring(0, 5))) {
								is_same_type_lecture_added = true; // 同種類の講義が付加されていると設定
								break; // ループから抜ける
							}
						}
					}

					// 必要講義未選択エラーが存在すると設定されている、又は同種類の講義が付加されていない場合
					if (is_error_existed[i][j][0] || !is_same_type_lecture_added) {
						// 必要講義未選択エラーが存在すると設定されていて、同種類の講義が付加されている場合
						if (is_same_type_lecture_added) {
							// 同種類講義選択のエラーと演習第一と論文指導の同時申請エラーの設定の解除
							is_error_existed[i][j][1] = false;
						}

						error_existed_opening_lecture_ids.addElement(opening_lecture_ids[i][j]); // 現在アクセスしている開講講義IDをエラーが存在する開講講義IDに付加
					}
				// 上記の条件を満たさない場合
				} else {
					lecture_name_font_color = "black"; // 講義名のフォントカラーを黒に設定
				}

				// 登録データの付加
				string_buffer_column_data.append("<input type=\"hidden\" name=\"opening_lecture_id_" + i + j + "\" value=\"" + opening_lecture_ids[i][j] + "\" />\n");
				string_buffer_column_data.append("\t\t\t\t\t\t\t\t<font color=\"" + lecture_name_font_color + "\"><b>" + lecture_data[lecture_data_indexes[i][j]][1] + "</b></font>"); // 行データの付加
			}

			column_data[i][j] = string_buffer_column_data.toString(); // 行データの取得
		}
	}

	String title = null; // タイトルを格納
	StringBuffer message = new StringBuffer(); // メッセージを格納
	String button_name = null; // ボタンの名前を格納

	// エラーが存在する講義がある場合
	if (!error_existed_opening_lecture_ids.isEmpty()) {
		title = "選択内容のエラー"; // タイトルの設定

		// メッセージの付加
		message.append("<b>エラー内容:</b><br />\n");
		for (int i = 0; i < error_existed_opening_lecture_ids.size(); i++) {
			String opening_lecture_id = error_existed_opening_lecture_ids.elementAt(i).toString(); // 開講講義IDを格納
			int day_number = Integer.parseInt(opening_lecture_id.substring(1, 2)); // 曜日番号を格納
			int class_number = Integer.parseInt(opening_lecture_id.substring(2, 3)); // クラス番号を格納
			String lecture_name = lecture_data[lecture_data_indexes[day_number][class_number]][1]; // 講義名を格納

			// 各エラーの存在をチェックし、存在するエラーの内容を表示
			for (int j = 0; j < is_error_existed[day_number][class_number].length; j++) {
				// 現在アクセスしている開講講義に現在アクセスしている種類のエラーが存在している場合
				if (is_error_existed[day_number][class_number][j]) {
					String error_content = null; // エラー内容を格納

					// 存在しているエラーが必要講義未選択エラーである場合
					if (j == 0) {
						String necessary_opening_lecture_id = not_selected_necessary_opening_lecture_ids.firstElement().toString(); // 必要開講講義IDを格納
						not_selected_necessary_opening_lecture_ids.removeElementAt(0); // 利用した、選択されていない必要開講講義IDの削除
						int necessary_opening_lecture_day_number = Integer.parseInt(necessary_opening_lecture_id.substring(1, 2)); // 必要開講講義の曜日番号を格納
						final String[] DAY_OUTLINES = {"月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "集中講義"}; // 曜日の見出しを格納
						StringBuffer error_caused_position = new StringBuffer(DAY_OUTLINES[necessary_opening_lecture_day_number]); // エラーが発生した位置を格納

						// 必要開講講義が集中講義のものではない場合
						if (necessary_opening_lecture_day_number != 5) {
							error_caused_position.append("・" + (Integer.parseInt(necessary_opening_lecture_id.substring(2, 3)) + 1) + "時限目");
						}

						error_content = "の履修に必要な<font color=\"red\">" + database_bean.getColumnData("lecture", 1, database_bean.getColumnData("opening_lecture", 1, necessary_opening_lecture_id)) + "</font>(" + error_caused_position + "開講)が選択されていません"; // エラー内容の設定
					// 同種類講義選択エラーである場合
					} else {
						lecture_name = Pattern.compile("\\(").split(lecture_name)[0]; // 講義名の加工
						error_content = "を1学期に2つ以上履修することはできません"; // エラー内容の設定
					}

					message.append("\t\t・<font color=\"red\">" + lecture_name + "</font>" + error_content + "<br />\n"); // メッセージの付加
				}
			}
		}
		message.append("\t\t<br />\n");
		message.append("\t\t選択ページに戻り、訂正を行って下さい。");
	// ない場合
	} else {
		title = "登録内容の確認"; // タイトルの設定
		button_name = "登録"; // "登録"をボタンの名前に設定
		message.append("登録内容を確認し、問題がなければ下の[" + button_name + "]ボタンをクリックして下さい。<br />"); // メッセージの付加
	}
%>
<%= html_bean.getHeader(title) %>
		<%= message %>

		<form method="POST" action="./regist.jsp">
			<input type="hidden" name="student_id" value="<%= student_id %>" />
			<%= html_bean.getOpeningLectureList(column_data, button_name) %>
		</form>
		<a href="<%= utility_bean.getMainPageURL(student_id) %>">戻る</a><br />

		<%= html_bean.getFooter() %>
