<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.util.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String student_id = request.getParameter("student_id"); // 入力された学籍番号を格納
	String referer_url = request.getHeader("referer"); // 参照元のURLを格納
	String login_page_url = utility_bean.getLoginPageURL(); // ログインページのURLを格納

	// ログインページからのアクセスである場合
	if (referer_url.equals(login_page_url)) {
		student_id = utility_bean.getFullDigitAlteredStudentID(student_id); // 学籍番号を半角に変換
		utility_bean.checkStudentID(student_id, request, response); // 学籍番号のチェック
	}

	String[] student_data = database_bean.getRowData("student", student_id); // 学生データを格納

	// ログインページからのアクセスである場合
	if (referer_url.equals(login_page_url)) {
		// 学生データが取得できなかった場合
		if (student_data == null) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("not_registered_error", "学籍番号")).forward(request, response); // エラーページへの転送
		}

		// パスワードが仮登録状態のままである場合
		if(student_data[2].equals(student_data[3])) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("password_not_changed_error")).forward(request, response); // エラーページへの転送
		}

		String password = request.getParameter("password"); //パスワードを格納

		// パスワードが登録内容と一致しない場合
		if(!password.equals(student_data[2])) {
			// パスワードが入力されていない場合
			if(password.equals("")) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", "パスワード")).forward(request, response); // エラーページへの転送
			// 入力されている場合
			} else {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("not_accorded_error", "パスワード")).forward(request, response); // エラーページへの転送
			}
		}
	}

	String[][] mastered_lecture_data = database_bean.getRowsData("mastered_lecture", student_id); // 履修済み講義データを格納
	int student_affiliation_id = utility_bean.getStudentAffiliationID(student_id); // 学生所属IDを格納
	Vector[] mastered_lecture_data_indexes = null; // 履修済み講義データのインデックスを格納
	int affiliation_year = utility_bean.getAffiliationYear(student_id); // 在籍年数を格納
	int entered_year = utility_bean.getEnteredYear(student_id); // 入学した年を格納
	int[] got_credits = new int[2]; // 取得した単位を格納
	boolean is_seminar_i_mastered = false; // 演習第一を履修しているかどうかを格納
	boolean is_thesis_lead_mastered = false; // 論文指導を履修しているかどうかを格納
	boolean[] is_old_document_decipherment_1_mastered = new boolean[2]; // 古文書解読1を履修しているかどうかを格納

	// 履修済み講義データが取得できている場合
	if (mastered_lecture_data != null) {
		// 昼間主である場合
		if (student_affiliation_id != -6) {
			mastered_lecture_data_indexes = new Vector[affiliation_year]; // 在籍年数を履修済み講義データのインデックスの要素数に設定

			// 履修済み講義データのインデックスを格納するオブジェクトのインスタンスの生成
			for (int i = 0; i < mastered_lecture_data_indexes.length; i++) {
				mastered_lecture_data_indexes[i] = new Vector();
			}
		}

		// 演習第一を履修しているかどうかのチェックと、履修済み講義データのインデックスの取得
		for (int i = 0; i < mastered_lecture_data.length; i++) {
			int got_credits_index = Integer.parseInt(mastered_lecture_data[i][3]); // 取得した単位のインデックスを格納

			// 取得した単位のインデックスが経済学部のものである場合
			if (got_credits_index < got_credits.length) {
				got_credits[got_credits_index] += Integer.parseInt(database_bean.getColumnData("lecture", 2, mastered_lecture_data[i][0].substring(5))); // 現在アクセスしている履修済み講義データが属するクラスに単位を加算
			}

			String lecture_type_id = mastered_lecture_data[i][0].substring(5, 10); // 講義所属IDを格納

			// 現在アクセスしている履修済み講義データが演習第一のものである場合
			if (lecture_type_id.equals("06000")) {
				is_seminar_i_mastered = true; // 演習第一を履修していると設定
			}

			// 夜間主である場合
			if (student_affiliation_id == 6) {
				// 演習第一を履修していると設定されている場合
				if (is_seminar_i_mastered) {
					break; // ループから抜ける
				}

				// 現在アクセスしている履修済み講義データが論文指導のものである場合
				if (lecture_type_id.equals("06002")) {
					is_thesis_lead_mastered = true; // 論文指導を履修していると設定
				}
			// 昼間主である場合
			} else {
				// 現在アクセスしている履修済み講義データが学部共通科目に所属している場合
				if (lecture_type_id.substring(0, 1).equals("1")) {
					// 古文書解読A1のものである場合
					if (lecture_type_id.equals("10061")) {
						is_old_document_decipherment_1_mastered[0] = true; // 古文書解読A1を履修していると設定
					// 古文書解読B1のものである場合
					} else if (lecture_type_id.equals("10063")) {
						is_old_document_decipherment_1_mastered[1] = true; // 古文書解読B1を履修していると設定
					}
				}

				mastered_lecture_data_indexes[Integer.parseInt(mastered_lecture_data[i][2]) - entered_year].addElement("" + i); // 現在アクセスしている履修済み講義データのインデックスの設定
			}
		}
	}

	int present_semester_number = utility_bean.getSemesterNumber(); // 現在の学期番号を格納



	present_semester_number = 1; // テスト用の設定(開発終了後は削除)



	String[][] registered_opening_lecture_data = database_bean.getRowsData("registered_opening_lecture", student_id); // 登録済み開講講義データを格納
	String[][] registered_opening_lecture_ids = new String[6][7]; // 登録済み開講講義IDを格納

	// 登録済み開講講義データが取得できた場合
	if (registered_opening_lecture_data != null) {
		// 2000年までに入学した昼間主の演習第一を履修していない学生で、現在が秋学期である場合
		if (entered_year <= 2000 && student_affiliation_id != 6 && !is_seminar_i_mastered && present_semester_number == 2) {
			int spring_semester_registered_opening_lecture_credit = 0; // 春学期に登録した開講講義の単位を格納

			// 春学期に登録した開講講義の単位の取得
			for (int i = 0; i < registered_opening_lecture_data.length; i++) {
				// 現在アクセスしている登録済み開講講義が春学期に登録されたものである場合
				if (registered_opening_lecture_data[i][0].substring(5, 6).equals("1")) {
					spring_semester_registered_opening_lecture_credit += Integer.parseInt(database_bean.getColumnData("lecture", 2, database_bean.getColumnData("opening_lecture", 1, registered_opening_lecture_data[i][1]))); // 春学期に登録した開講講義の単位の加算
				}
			}

			// 春学期に登録した開講講義の単位数の合計が56に達している場合
			if (spring_semester_registered_opening_lecture_credit == 56) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("credit_error")).forward(request, response); // エラーページへの転送
			}
		}

		// 登録済み開講講義IDの取得
		for (int i = 0; i < registered_opening_lecture_data.length; i++) {
			int semester_number = Integer.parseInt(registered_opening_lecture_data[i][1].substring(0, 1)); // 学期番号を格納

			// 現在アクセスしている登録済み開講講義データが参照している開講講義が通年、又は現在の学期のものである場合
			if (semester_number == 0 || semester_number == present_semester_number) {
				registered_opening_lecture_ids[Integer.parseInt(registered_opening_lecture_data[i][1].substring(1, 2))][Integer.parseInt(registered_opening_lecture_data[i][1].substring(2, 3))] = registered_opening_lecture_data[i][1]; // 現在アクセスしている登録済み開講講義IDを設定
			}
		}
	}

	int year; // 学年を格納
	boolean is_sports_science_mastered = false; // スポーツ科学を履修しているかどうかを格納
	boolean[][] is_foreign_language_i_mastered = new boolean[database_bean.getColumnDataArray("lecture_affiliation", 0, "2").length][is_old_document_decipherment_1_mastered.length]; // 外国語Iを履修しているかどうかを格納

	// 夜間主である場合
	if (student_affiliation_id == 6) {
		// 在籍年数が4を超えている場合
		if (affiliation_year > 4) {
			year = 4; // 学年を4に設定
		// 超えていない場合
		} else {
			year = affiliation_year; // 在籍年数を学年に設定
		}
	// 昼間主である場合
	} else {
		// 在籍年数が1である場合
		if (affiliation_year == 1) {
			year = 1; // 学年を1に設定
		// 1でない場合
		} else {
			year = 2; // 学年を2に設定
		}

		// 履修済み講義データが取得できていて、学年が2以上に設定されている場合
		if (mastered_lecture_data != null && year >= 2) {
			int[] credits = new int[2]; // 単位を格納
			boolean is_basic_seminar_mastered = false; // 基礎演習を履修しているかどうかを格納
			int[] got_english_i_credits = new int[2]; // 取得した英語Iの単位を格納
			int got_english_ii_credit = 0; // 取得した英語IIの単位を格納
			int got_second_foreign_language_credit = 0; // 取得した第2外国語の単位を格納

			// 履修状況の取得と学年の設定
			for (int i = 0; i < mastered_lecture_data_indexes.length; i++) {
				for (int j = 0; j < mastered_lecture_data_indexes[i].size(); j++) {
					String lecture_id = mastered_lecture_data[Integer.parseInt(mastered_lecture_data_indexes[i].elementAt(j).toString())][0].substring(5); // 講義IDを格納
					String lecture_type_id = lecture_id.substring(0, 5); // 講義所属IDを格納

					// 現在アクセスしている履修済み講義データが基礎演習のものである場合
					if (lecture_type_id.equals("06003")) {
						is_basic_seminar_mastered = true; // 基礎演習を履修していると設定
					// スポーツ科学のものである場合
					} else if (lecture_type_id.equals("10052")) {
						is_sports_science_mastered = true; // スポーツ科学を履修していると設定
					// 基礎演習とスポーツ科学以外の講義である場合
					} else {
						int course_id = Integer.parseInt(lecture_id.substring(0, 1)); // 科目IDを格納
						int credit = Integer.parseInt(database_bean.getColumnData("lecture", 2, lecture_id)); // 単位を格納

						// 専門科目、又は学部共通科目の講義である場合
						if (course_id <= 1) {
							credits[course_id] += credit; // 単位の加算
						// 外国語の講義である場合
						} else if (course_id == 2) {
							int lecture_affiliation_number = Integer.parseInt(lecture_type_id.substring(1, 2)); // 講義所属番号を格納

							// 英語のものである場合
							if (lecture_affiliation_number == 0) {
								// 英語IIのものである場合
								if (lecture_type_id.equals("20004") || lecture_type_id.equals("20005")) {
									got_english_ii_credit += credit; // 英語IIの単位の加算
								// 英語Iaのものである場合
								} else if (lecture_type_id.equals("20000") || lecture_type_id.equals("20001") || lecture_type_id.equals("20007") || lecture_type_id.equals("20008")) {
									got_english_i_credits[0] += credit; // 英語Iaの単位の加算
								// 英語Ibのものである場合
								} else if (lecture_type_id.equals("20002") || lecture_type_id.equals("20003") || lecture_type_id.equals("20009") || lecture_type_id.equals("20010")) {
									got_english_i_credits[1] += credit; // 英語Ibの単位の加算
								}
							// 英語以外の外国語である場合
							} else {
								got_second_foreign_language_credit += credit; // 第2外国語の単位の加算
								int lecture_type_number = Integer.parseInt(lecture_type_id.substring(3)); // 講義種類番号を格納

								// Ia、又はIbである場合
								if (lecture_type_number < is_foreign_language_i_mastered[lecture_affiliation_number].length) {
									is_foreign_language_i_mastered[lecture_affiliation_number][lecture_type_number] = true; // 現在アクセスしている種類の外国語Ia、又はIbを履修していると設定
								}
							}
						}
					}
				}

				// 学年が2に設定され、在籍年数が3以上である場合
				if (year == 2 && affiliation_year >= 3) {
					// 3回生への進級条件を満たしている場合
					if (is_basic_seminar_mastered && is_sports_science_mastered && ((got_english_i_credits[0] + got_english_i_credits[1]) + got_english_ii_credit) >= 4 && got_second_foreign_language_credit >= 4 && (credits[0] + credits[1]) >= 36) {
						// 現在の要素番号が最終のものである場合
						if (i == (mastered_lecture_data_indexes.length - 1)) {
							year = 3; // 学年を3に設定
						// 最終のものでない場合
						} else {
							year = 4; // 学年を4に設定
						}
					}
				}
			}

			// 英語Iを履修しているかどうかの設定
			for (int i = 0; i < got_english_i_credits.length; i++) {
				// 現在アクセスしている種類の英語Iの取得単位が2に達している場合
				if (got_english_i_credits[i] == 2) {
					is_foreign_language_i_mastered[0][i] = true; // 現在アクセスしている種類の英語Iを履修していると設定
				}
			}
		}
	}

	Vector[][] opening_lecture_data_indexes = new Vector[registered_opening_lecture_ids.length][registered_opening_lecture_ids[0].length]; // 開講講義データのインデックスを格納
	Vector[][] lecture_data_indexes = new Vector[registered_opening_lecture_ids.length][registered_opening_lecture_ids[0].length]; // 講義データのインデックスを格納

	// Vector要素の初期化
	for (int i = 0; i < opening_lecture_data_indexes.length; i++) {
		for (int j = 0; j < opening_lecture_data_indexes[i].length; j++) {
			opening_lecture_data_indexes[i][j] = new Vector();
			lecture_data_indexes[i][j] = new Vector();
		}
	}

	String[][] lecture_data = database_bean.getRowsData("lecture"); // 講義データを格納
	String[][] opening_lecture_data = database_bean.getRowsData("opening_lecture"); // 開講講義データを格納
	int present_year = Calendar.getInstance().get(Calendar.YEAR); // 現在の年を格納
	int master_year_number_column_index = (student_affiliation_id / 6) + 3; // 履修年次番号欄のインデックスを格納
	final int[][] MASTERABLE_YEAR_RANGES = {{1, 1}, {1, 2}, {1, 4}, {2, 2}, {2, 4}, {3, 3}, {3, 4}, {4, 4}}; // 履修可能年次の範囲を格納

	// 選択可能な開講講義に関係する開講講義データと講義データのインデックスの取得
	for (int i = 0; i < opening_lecture_data.length; i++) {
		int semester_number = Integer.parseInt(opening_lecture_data[i][0].substring(0, 1)); // 学期番号を格納
		int day_number = Integer.parseInt(opening_lecture_data[i][0].substring(1, 2)); // 曜日番号を格納
		int class_number = Integer.parseInt(opening_lecture_data[i][0].substring(2, 3)); // クラス番号を格納

		// 現在が春学期である場合
		if (present_semester_number == 1) {
			// 現在アクセスしている開講講義が秋学期のものである場合
			if (semester_number == 2) {
				break; // ループから抜ける
			}
		// 秋学期である場合
		} else {
			// 現在アクセスしている開講講義が通年のものである場合
			if (semester_number == 0) {
				// その講義を春学期に登録していない場合
				if (registered_opening_lecture_ids[day_number][class_number] == null || !registered_opening_lecture_ids[day_number][class_number].equals(opening_lecture_data[i][0])) {
					continue; // 次の要素に処理を移す
				}
			// 春学期のものである場合
			} else if (semester_number == 1) {
				continue; // 次の要素に処理を移す
			// 秋学期のもので、開講される日時に通年の講義を春学期に登録している場合
			} else if (registered_opening_lecture_ids[day_number][class_number] != null && registered_opening_lecture_ids[day_number][class_number].substring(0, 1).equals("0")) {
				continue; // 次の要素に処理を移す
			}
		}

		// 現在アクセスしている開講講義が昼間に開講されるものである場合
		if (class_number <= 4) {
			// 指定された学籍番号の学生が夜間主で、昼間に開講した講義を30単位取得している場合
			if (student_affiliation_id == 6 && got_credits[0] == 30) {
				continue; // 次の要素に処理を移す
			}
		// 夜間に開講されるものである場合
		} else {
			// 指定された学籍番号の学生が昼間主で、夜間に開講した講義を30単位取得している場合
			if (student_affiliation_id != 6 && got_credits[1] == 30) {
				continue; // 次の要素に処理を移す
			}
		}

		boolean is_mastered = false; // 履修済みであるかどうかを格納

		// 履修済み講義データが取得できている場合
		if (mastered_lecture_data != null) {
			String lecture_type_id = opening_lecture_data[i][1].substring(0, 5); // 講義種類IDを格納

			// 現在アクセスしている開講講義IDに該当する講義が履修済みであるかどうかのチェック
			for (int j = 0; j < mastered_lecture_data.length; j++) {
				// 現在アクセスしている開講講義データが持つ講義種類IDが履修済み講義データの履修済み講義IDの講義種類ID部分と等しい場合
				if (lecture_type_id.equals(mastered_lecture_data[j][0].substring(5, 10))) {
					is_mastered = true; // 履修済みであることの設定
					break; // ループから抜ける
				}
			}

			// 現在アクセスしている開講講義IDに該当する講義が履修済みである場合
			if (is_mastered) {
				continue; // 次の要素に処理を移す
			}
		}

		int lecture_data_index = 0; // 講義データのインデックスを格納

		// 講義データのインデックスの取得
		for (int j = 0; j < lecture_data.length; j++) {
			// 現在アクセスしている開講講義データの持つ講義IDが現在アクセスしている講義IDと等しい場合
			if (opening_lecture_data[i][1].equals(lecture_data[j][0])) {
				lecture_data_index = j; // 現在の要素番号をインデックスに設定
				break; // ループから抜ける
			}
		}

		int masterable_year_number = Integer.parseInt(lecture_data[lecture_data_index][master_year_number_column_index + 2]); // 履修可能年次番号を格納

		// 現在アクセスしている講義データの履修可能年次が履修不可、又は学年が履修可能年次の範囲に入っていない場合
		if (masterable_year_number == 8 || year < MASTERABLE_YEAR_RANGES[masterable_year_number][0] || MASTERABLE_YEAR_RANGES[masterable_year_number][1] < year) {
			continue; // 次の要素に処理を移す
		}

		int[] masterable_years = {present_year - 11, present_year}; // 履修可能年を格納

		// 履修可能年の取得
		for (int j = 7; j < 9; j++) {
			if (lecture_data[lecture_data_index][j] != null) {
				masterable_years[j - 7] = Integer.parseInt(lecture_data[lecture_data_index][j]);
			}
		}

		// 入学年が現在アクセスしている講義の履修可能年の範囲内に入っていない場合
		if (entered_year < masterable_years[0] || masterable_years[1] < entered_year) {
			continue; // 次の要素に処理を移す
		}

		// 指定された学籍番号の学生が昼間主で、現在アクセスしている講義に対し、指定された学籍番号の学生が所属している学科が履修不可に設定されている場合
		if (student_affiliation_id != 6 && lecture_data[lecture_data_index][student_affiliation_id + 9] != null) {
			continue; // 次の要素に処理を移す
		}

		String lecture_affiliation_number = lecture_data[lecture_data_index][0].substring(0, 3); // 講義所属番号を格納
		int lecture_type_number = Integer.parseInt(lecture_data[lecture_data_index][0].substring(3, 5)); // 講義種類番号を格納
		String lecture_division_number = "" + Integer.parseInt(lecture_data[lecture_data_index][0].substring(5)); // 講義区分番号を格納

		// 現在アクセスしている講義が専門のその他に属している場合
		if (lecture_affiliation_number.equals("060")) {
			// 演習第一、演習第二、又は基礎演習(大学入門セミナー)である場合
			if (lecture_type_number <= 3 && lecture_type_number != 2) {
				int lecturer_column_index; // 教官の列のインデックスを格納

				// 基礎演習である場合
				if (lecture_type_number == 3) {
					lecturer_column_index = 5; // 5を教官の列のインデックスに設定
				// 演習第一、又は演習第二である場合
				} else {
					// 演習第一で、論文指導を履修している場合
					if (lecture_type_number == 0) {
						if (is_thesis_lead_mastered) {
							continue; // 次の要素に処理を移す
						}
					// 演習第二で、演習第一を履修していない場合
					} else if (!is_seminar_i_mastered) {
						continue; // 次の要素に処理を移す
					}

					lecturer_column_index = 14; // 14を教官の列のインデックスに設定
				}

				// 現在アクセスしている講義の教官が指定された学籍番号の学生の教官でない場合
				if (!database_bean.getColumnDataArray("opening_lecture_lecturer", 1, opening_lecture_data[i][0])[0].equals(student_data[lecturer_column_index])) {
					continue; // 次の要素に処理を移す
				}
			// 情報リテラシーである場合
			} else if (lecture_type_number == 4) {
				// 昼間に開講されるもので、指定された学生が所属しているクラスではない場合
				if (student_affiliation_id != 6 && lecture_data[lecture_data_index][1].indexOf(student_data[6]) == -1) {
					continue; // 次の要素に処理を移す
				}
			// 外国文献研究で、指定された学籍番号の学生の学年が2で現在が春学期である場合
			} else if (lecture_type_number == 5 && (year == 2 && present_semester_number == 1)) {
				continue; // 次の要素に処理を移す
			}
		// 学部共通に属している場合
		} else if (lecture_affiliation_number.equals("100")) {
			// 現在アクセスしている講義が健康と生活である場合
			if (lecture_type_number == 73) {
				// 指定された学生が所属しているクラスではない場合
				if(lecture_data[lecture_data_index][1].indexOf(student_data[7]) == -1) {
					continue; // 次の要素に処理を移す
				}
			// スポーツ科学である場合
			} else if (lecture_type_number == 52) {
				// 昼間に開講されるもので、全員参加のクラスでも指定された学生が所属しているクラスでもない場合
				if (student_affiliation_id != 6 && !lecture_division_number.equals("0") && lecture_data[lecture_data_index][1].indexOf(student_data[8]) == -1) {
					continue; // 次の要素に処理を移す
				}
			// 語学演習である場合
			} else if (lecture_type_number <= 56 && lecture_type_number >= 60) {
				// 指定された学籍番号の学生の学年が2で現在が春学期である場合
				if (year == 2 && present_semester_number == 1) {
					continue; // 次の要素に処理を移す
				}

				int language_number = lecture_type_number - 56; // 語学番号を格納

				// 現在アクセスしている語学演習に関係する外国語Iを履修しているかどうかのチェック
				for (int j = 0; j < is_foreign_language_i_mastered[language_number].length; j++) {
					// 現在アクセスしている種類の外国語Iを履修していない場合
					if (!is_foreign_language_i_mastered[language_number][j]) {
						continue; // 次の要素に処理を移す
					}
				}
			// 古文書解読A2である場合
			} else if (lecture_type_number == 62) {
				// 古文書解読A1を履修していない場合
				if (!is_old_document_decipherment_1_mastered[0]) {
					continue; // 次の要素に処理を移す
				}
			// 古文書解読B2である場合
			} else if (lecture_type_number == 64) {
				// 古文書解読B1を履修していない場合
				if (!is_old_document_decipherment_1_mastered[1]) {
					continue; // 次の要素に処理を移す
				}
			// 日本事情で、指定された学生が日本人である場合
			} else if ((lecture_type_number >= 65 && lecture_type_number <= 68) && student_data[4].equals("0")) {
				continue; // 次の要素に処理を移す
			}
		// 外国語に属している場合
		} else if (lecture_data[lecture_data_index][0].substring(0, 1).equals("2")) {
			// 現在アクセスしている講義が日本語で、指定された学籍番号の学生が日本人である場合
			if (lecture_affiliation_number.equals("260") && student_data[4].equals("0")) {
				continue; // 次の要素に処理を移す
			}

			// 現在アクセスしている講義が昼間に開講されるものである場合
			if (student_affiliation_id != 6) {
				// 現在アクセスしている講義が英語である場合
				if (lecture_affiliation_number.equals("200")) {
					// 現在アクセスしている講義が大学英語入門、英語Ia、英会話基礎、英語Ib、英語IIh、又は英語IIaである場合
					if (lecture_type_number >= 4 && lecture_type_number <= 10 && lecture_type_number != 6) {
						int class_column_index; // クラスの列のインデックスを格納

						// 現在アクセスしている講義が英語IIhである場合
						if (lecture_type_number == 4) {
							class_column_index = 10; // 10をクラスの列のインデックスに設定
						// 英語IIaである場合
						} else if (lecture_type_number == 5) {
							class_column_index = 11; // 11をクラスの列のインデックスに設定
						// 大学英語入門、英語Ia、英会話基礎、又は英語Ibである場合
						} else {
							// 大学英語入門、又は英語Iaである場合
							if (lecture_type_number == 7 || lecture_type_number == 8) {
								// 英語Ia(新旧)を履修している場合
								if (is_foreign_language_i_mastered[0][0]) {
									continue; // 次の要素に処理を移す
								}
							// 英会話基礎、又は英語Ibで、英語Ib(新旧)を履修している場合
							} else if (is_foreign_language_i_mastered[0][1]) {
								continue; // 次の要素に処理を移す
							}

							class_column_index = 9; // 9をクラスの列のインデックスに設定
						}

						// 現在アクセスしている講義が英語II、又は英語Iで在籍年数が1であり、指定された学生が現在アクセスしている講義の所属しているクラスではない場合
						if ((lecture_type_number <= 5 || affiliation_year == 1) && lecture_data[lecture_data_index][1].indexOf(student_data[class_column_index]) == -1) {
							continue; // 次の要素に処理を移す
						}
					}
				// 日本語と英語以外の外国語である場合
				} else {
					// 現在アクセスしている講義が指定された学籍番号の学生が選択した言語でない場合
					if (!lecture_data[lecture_data_index][0].substring(1, 2).equals(student_data[12])) {
						// 指定された学籍番号の学生の在籍年数が1、又は現在アクセスしている講義が再履修クラスである場合
						if(affiliation_year == 1 || lecture_data[lecture_data_index][1].indexOf("再") != -1) {
							continue; // 次の要素に処理を移す
						}
					// 選択した言語である場合
					} else {
						// 現在アクセスしている講義が指定された学生が所属しているクラスでも再履修クラスでもない場合
						if (lecture_data[lecture_data_index][1].indexOf(student_data[13]) == -1 && lecture_data[lecture_data_index][1].indexOf("再") == -1) {
							continue; // 次の要素に処理を移す
						}

						// 現在アクセスしている講義が再履修クラスである場合
						if (lecture_data[lecture_data_index][1].indexOf("再") != -1) {
							// 中国語Ia・b(再)である場合
							if (lecture_affiliation_number.equals("240") && lecture_type_number == 4) {
								// IaとIbを共に履修している場合
								if (is_foreign_language_i_mastered[4][0] && is_foreign_language_i_mastered[4][1]) {
									continue; // 次の要素に処理を移す
								}
							// 中国語Ia・b(再)以外の再履修クラスで、在籍年数が2以下である場合
							} else if (affiliation_year <= 2) {
								continue; // 次の要素に処理を移す
							}
						}
					}
				}
			}
		}

		// インデックスの付加
		opening_lecture_data_indexes[day_number][class_number].addElement("" + i);
		lecture_data_indexes[day_number][class_number].addElement("" + lecture_data_index);
	}

	// 学生所属IDが夜間主のものでない場合
	if (student_affiliation_id != 6) {
		// 夜間の講義データの中に昼間の講義データと同じ種類のデータが含まれているかどうかをチェックし、含まれている場合は削除
		for (int i = 0; i < 5; i++) {
			for (int j = 5; j < registered_opening_lecture_ids[i].length; j++) {
				for (int k = 0; k < opening_lecture_data_indexes[i][j].size(); k++) {
					String lecture_type_id = lecture_data[Integer.parseInt(lecture_data_indexes[i][j].elementAt(k).toString())][0].substring(0, 5); // 講義種類IDを格納

					same_lecture_data_check: for (int l = 0; l < 5; l++) {
						for (int m = 0; m < 5; m++) {
							for (int n = 0; n < opening_lecture_data_indexes[l][m].size(); n++) {
								// 現在アクセスしている夜間の講義データと同じ種類の講義データが昼間の講義データの中に含まれていた場合
								if(lecture_data[Integer.parseInt(lecture_data_indexes[l][m].elementAt(n).toString())][0].substring(0, 5).equals(lecture_type_id)) {
									// 現在アクセスしているデータのインデックスの削除
									opening_lecture_data_indexes[i][j].removeElementAt(k);
									lecture_data_indexes[i][j].removeElementAt(k);

									k--; // 現在アクセスしているデータの要素番号の減算
									break same_lecture_data_check; // ループから抜ける
								}
							}
						}
					}
				}
			}
		}
	}

	// 必要開講講義IDが設定されている開講講義データに対してその開講講義IDのデータを検索し、存在しない場合は削除
	for (int i = 0; i < registered_opening_lecture_ids.length; i++) {
		for (int j = 0; j < registered_opening_lecture_ids[i].length; j++) {
			for (int k = 0; k < opening_lecture_data_indexes[i][j].size(); k++) {
				int opening_lecture_data_index = Integer.parseInt(opening_lecture_data_indexes[i][j].elementAt(k).toString()); // 開講講義データのインデックスを格納

				// 必要開講講義IDが設定されていない場合
				if (opening_lecture_data[opening_lecture_data_index][2] == null) {
					continue; // 次の要素に処理を移す
				}

				int day_number = Integer.parseInt(opening_lecture_data[opening_lecture_data_index][2].substring(1, 2)); // 曜日番号を格納
				int class_number = Integer.parseInt(opening_lecture_data[opening_lecture_data_index][2].substring(2, 3)); // クラス番号を格納
				boolean is_necessary_opening_lecture_not_found = true; // 必要開講講義が見つからなかったかどうかを格納

				// 必要開講講義に当たる開講講義IDが取得した開講講義ID内に存在するかどうかのチェック
				for (int l = 0; l < opening_lecture_data_indexes[day_number][class_number].size(); l++) {
					// 現在アクセスしている開講講義IDが指定された必要開講講義IDと等しい場合
					if (opening_lecture_data[Integer.parseInt(opening_lecture_data_indexes[day_number][class_number].elementAt(l).toString())][0].equals(opening_lecture_data[opening_lecture_data_index][2])) {
						is_necessary_opening_lecture_not_found = false; // 必要開講講義が見つかったと設定
						break; // ループから抜ける
					}
				}

				// 必要開講講義が見つからなかった場合
				if (is_necessary_opening_lecture_not_found) {
					// 現在アクセスしているデータのインデックスの削除
					opening_lecture_data_indexes[i][j].removeElementAt(k);
					lecture_data_indexes[i][j].removeElementAt(k);

					k--; // 現在アクセスしているデータの要素番号の減算
				}
			}
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	String[][] column_data = new String[registered_opening_lecture_ids.length][registered_opening_lecture_ids[0].length]; // 列データを格納

	// 列データの取得
	for (int i = 0; i < column_data.length; i++) {
		for (int j = 0; j < column_data[i].length; j++) {
			String column_data_name = "opening_lecture_id_" + i + j; // 列データの名前を格納

			// 現在が秋学期で、現在アクセスしている日時に開講講義が登録されていて、その講義が通年のものである場合
			if (present_semester_number == 2 && registered_opening_lecture_ids[i][j] != null && registered_opening_lecture_ids[i][j].substring(0, 1).equals("0")) {
				StringBuffer string_buffer_column_data = new StringBuffer(); // 列データを格納

				// 列データの取得
				string_buffer_column_data.append("<input type=\"hidden\" name=\"" + column_data_name + "\" value=\"" + registered_opening_lecture_ids[i][j] + "\" />\n");
				string_buffer_column_data.append("\t\t\t\t\t\t\t\t<b>" + lecture_data[Integer.parseInt(lecture_data_indexes[i][j].firstElement().toString())][1] + "</b>");

				column_data[i][j] = string_buffer_column_data.toString(); // 列データの設定
			// 上記の条件を満たさない場合
			} else {
				// 選択欄データの要素数の設定
				String[] select_box_ids = new String[opening_lecture_data_indexes[i][j].size() + 1]; // 選択欄のIDを格納
				String[] select_box_outlines = new String[select_box_ids.length]; // 選択欄の見出しを格納

				select_box_outlines[0] = "(選択なし)　　　　　　　　　　　　　　　　"; // "選択なし"欄の設定
				int select_box_index = 1; // 選択欄のインデックスを格納

				// 使われていない開講講義データが残っている間繰り返し
				while (opening_lecture_data_indexes[i][j].size() > 0) {
					int minimum_master_year_number_index = 0; // 最も小さい履修年次番号のインデックスを格納
					int minimum_master_year_number = Integer.parseInt(lecture_data[Integer.parseInt(lecture_data_indexes[i][j].elementAt(minimum_master_year_number_index).toString())][master_year_number_column_index]); // 最も小さい履修年次番号を格納

					// 最も小さい履修年次番号のインデックスの取得
					for (int l = 1; l < opening_lecture_data_indexes[i][j].size(); l++) {
						int master_year_number = Integer.parseInt(lecture_data[Integer.parseInt(lecture_data_indexes[i][j].elementAt(l).toString())][master_year_number_column_index]); // 履修年次番号を格納

						// 現在アクセスしている要素番号の講義データが持つ履修年次番号が最も小さい履修年次番号のインデックスの講義データのものよりも小さい場合
						if (master_year_number < minimum_master_year_number) {
							minimum_master_year_number_index = l; // 現在の要素番号を最も小さい履修年次番号のインデックスに設定
							minimum_master_year_number = master_year_number; // 現在の履修年次番号を最も小さい履修年次番号に設定
						}
					}

					// 選択欄データの設定
					select_box_ids[select_box_index] = opening_lecture_data[Integer.parseInt(opening_lecture_data_indexes[i][j].elementAt(minimum_master_year_number_index).toString())][0];
					select_box_outlines[select_box_index] = lecture_data[Integer.parseInt(lecture_data_indexes[i][j].elementAt(minimum_master_year_number_index).toString())][1];

					select_box_index++; // 選択欄のインデックスの加算

					// 最も小さい履修講義番号に該当するデータの削除
					opening_lecture_data_indexes[i][j].removeElementAt(minimum_master_year_number_index);
					lecture_data_indexes[i][j].removeElementAt(minimum_master_year_number_index);
				}

				column_data[i][j] = html_bean.getSelectBox(column_data_name, registered_opening_lecture_ids[i][j], select_box_ids, select_box_outlines, 8); // 列データの設定
			}
		}
	}

	String[] semester_types = {"春", "秋"}; // 学期の種類を格納
%>
<%= html_bean.getHeader("履修講義の選択(" + semester_types[present_semester_number - 1] + "学期)") %>
		(学籍番号: <b><%= student_id %></b> 氏名: <b><%= student_data[1] %></b>)<br />
		<br />

		<a href="./report.jsp?student_id=<%= student_id %>">これまでの履修状況</a><br />
		<br />

		履修登録したい講義を選択し、終了しましたら下の[送信]ボタンをクリックして下さい。<br />
		(履修済みなどの条件から受講できない講義は選択欄から除外されています)<br />
		<br />

		<form method="POST" action="./confirm.jsp">
			<input type="hidden" name="student_id" value="<%= student_id %>" />
			<%= html_bean.getOpeningLectureList(column_data, "送信") %>
		</form>
		<a href="<%= utility_bean.getLogoutPageURL() %>">ログアウト</a><br />

		<%= html_bean.getFooter() %>
