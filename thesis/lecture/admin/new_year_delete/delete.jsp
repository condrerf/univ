<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String[] student_ids = database_bean.getColumnDataArray("student", 0); // 学籍番号を格納

	// 学籍番号が取得できた場合
	if (student_ids != null) {
		// 各学籍番号の学生が削除条件を満たしているかどうかをチェックし、満たしている場合は削除
		student_check: for (int i = 0; i < student_ids.length; i++) {
			int affiliation_year = utility_bean.getAffiliationYear(student_ids[i]); // 在籍年数を格納

			// 現在アクセスしている学籍番号の学生の在籍年数が4未満である場合
			if (affiliation_year < 4) {
				continue; // 次の要素に処理を移す
			// 12を超えている場合
			} else if (affiliation_year > 12) {
				database_bean.delete("student", student_ids[i]); // データの削除
				continue; // 次の要素に処理を移す
			}

			String[][] mastered_lecture_data = database_bean.getRowsData("mastered_lecture", student_ids[i]); // 履修済み講義データを格納

			// 履修済みデータが取得できなかった場合
			if (mastered_lecture_data == null) {
				continue; // 次の要素に処理を移す
			}

			int student_affiliation_id = utility_bean.getStudentAffiliationID(student_ids[i]); // 学生所属IDを格納
			int[] got_credits = new int[4]; // 取得した単位を格納
			boolean is_seminar_mastered = false; // 演習(基礎、又は第二)を履修しているかどうかを格納
			int affiliated_subject_mastered_lecture_count = 0; // 所属学科の履修講義数を格納
			int required_subject_mastered_count = 0; // 必修科目の履修数を格納
			int[] elective_required_subject_mastered_count = new int[3]; // 選択必修科目の履修数を格納

			// 履修状況の取得
			for (int j = 0; j < mastered_lecture_data.length; j++) {
				String lecture_id = mastered_lecture_data[j][0].substring(5); // 講義IDを格納
				int course_id = Integer.parseInt(lecture_id.substring(0, 1)); // 科目IDを格納
				got_credits[course_id] += Integer.parseInt(database_bean.getColumnData("lecture", 2, lecture_id)); // 現在アクセスしている履修済み講義データが属する科目に単位を加算

				// 現在アクセスしている履修済み講義が専門科目のものではない場合
				if (course_id != 0) {
					continue; // 次の要素に処理を移す
				}

				int lecture_affiliation_number = Integer.parseInt(lecture_id.substring(1, 2)); // 講義所属番号を格納
				int lecture_type_number = Integer.parseInt(lecture_id.substring(3, 5)); // 講義種類番号を格納

				// 現在アクセスしている履修済み講義と現在アクセスしている学籍番号の学生が所属している学科が同じでない場合
				if (lecture_affiliation_number != student_affiliation_id) {
					// 講義が専門のその他に属していない場合
					if (lecture_affiliation_number != 6) {
						continue; // 次の要素に処理を移す
					}

					// 演習第二である場合
					if (lecture_type_number == 1) {
						// 現在アクセスしている学籍番号の学生が昼間主である場合
						if (student_affiliation_id != 6) {
							is_seminar_mastered = true; // 演習を履修していると設定
						}
					// 基礎演習である場合
					} else if (lecture_type_number == 3) {
						// 現在アクセスしている学籍番号の学生が夜間主である場合
						if (student_affiliation_id == 6) {
							is_seminar_mastered = true; // 演習を履修していると設定
						}
					}

					continue; // 次の要素に処理を移す
				}

				affiliated_subject_mastered_lecture_count++; // 所属学科の履修講義数の加算
				String chair_number = lecture_id.substring(2, 3); // 講座番号を格納

				// 現在アクセスしている履修済み講義が経済学科のものである場合
				if (lecture_affiliation_number == 0) {
					// 講座が基礎理論である場合
					if (chair_number.equals("0")) {
						// マクロ経済学概論、ミクロ経済学概論、又は政治経済学概論である場合
						if (lecture_type_number == 1 || lecture_type_number == 2 || lecture_type_number == 9) {
							required_subject_mastered_count++; // 必修科目の履修数の加算
						// マクロ経済学、ミクロ経済学、又は政治経済学Iである場合
						} else if (lecture_type_number == 3 || lecture_type_number == 5 || lecture_type_number == 10) {
							elective_required_subject_mastered_count[0]++; // 1種類目の選択必修科目の履修数を加算
						// 経済学の基礎ツール、又は経済学史である場合
						} else if (lecture_type_number == 0 || lecture_type_number == 17) {
							elective_required_subject_mastered_count[1]++; // 2種類目の選択必修科目の履修数を加算
						}
					// 応用経済論である場合
					} else if (chair_number.equals("1")) {
						// 統計学I、又は財政学総論Iである場合
						if (lecture_type_number == 0 || lecture_type_number == 13) {
							elective_required_subject_mastered_count[1]++; // 2種類目の選択必修科目の履修数を加算
						}
					// 政策経済論である場合
					} else if (chair_number.equals("2")) {
						// 経済政策I、又は社会政策Iである場合
						if (lecture_type_number == 0 || lecture_type_number == 16) {
							elective_required_subject_mastered_count[1]++; // 2種類目の選択必修科目の履修数を加算
						}
					// 比較経済論である場合
					} else if (chair_number.equals("3")) {
						// 経済史概論、又は国際経済論Iである場合
						if (lecture_type_number == 0 || lecture_type_number == 9) {
							elective_required_subject_mastered_count[1]++; // 2種類目の選択必修科目の履修数を加算
						}
					// 基礎文献研究である場合
					} else {
						elective_required_subject_mastered_count[1]++; // 2種類目の選択必修科目の履修数を加算
					}
				// ファイナンス学科のものである場合
				} else if (lecture_affiliation_number == 1) {
					// 講座がファイナンス計画である場合
					if (chair_number.equals("0")) {
						// ファイナンス概論I、又はIIである場合
						if (lecture_type_number < 2) {
							required_subject_mastered_count++; // 必修科目の履修数の加算
						// 上記以外の講義である場合
						} else {
							elective_required_subject_mastered_count[0]++; // 1種類目の選択必修科目の履修数を加算
						}
					// ファイナンス市場である場合
					} else if (chair_number.equals("1")) {
						elective_required_subject_mastered_count[1]++; // 2種類目の選択必修科目の履修数を加算
					// ファイナンス・システムである場合
					} else if (chair_number.equals("2")) {
						elective_required_subject_mastered_count[2]++; // 3種類目の選択必修科目の履修数を加算
					}
				// 会計情報学科のもので、講座が財務会計であり、講義が簿記I、又は簿記IIである場合
				} else if (lecture_affiliation_number == 3 && chair_number.equals("0") && lecture_type_number <= 1) {
					required_subject_mastered_count++; // 必修科目の履修数の加算
				}
			}

			int[] necessary_credits = utility_bean.getNecessaryCredits(student_ids[i]); // 必要な単位を格納

			// 卒業条件を満たしているかどうかのチェック
			for (int j = 0; j < got_credits.length; j++) {
				// 現在アクセスしている科目に属する取得単位が必要な単位に達していない場合
				if (got_credits[j] < necessary_credits[j]) {
					continue student_check; // 次の学生要素に処理を移す
				}
			}

			// 演習を履修していない場合
			if (!is_seminar_mastered) {
				continue; // 次の要素に処理を移す
			}

			// 所属学科の履修講義数が10未満である場合
			if (affiliated_subject_mastered_lecture_count < 10) {
				continue; // 次の要素に処理を移す
			}

			// 現在アクセスしている学籍番号の学生が経済学科に所属している場合
			if (student_affiliation_id == 0) {
				// 必修科目・選択必修科目の条件を満たしていない場合
				if (required_subject_mastered_count < 3 || elective_required_subject_mastered_count[0] < 2 || elective_required_subject_mastered_count[1] < 5) {
					continue; // 次の要素に処理を移す
				}
			// ファイナンス学科である場合
			} else if (student_affiliation_id == 1) {
				// 必修科目・選択必修科目の条件を満たしていない場合
				if (required_subject_mastered_count < 2 || elective_required_subject_mastered_count[0] < 2 || elective_required_subject_mastered_count[1] < 2 || elective_required_subject_mastered_count[2] < 2) {
					continue; // 次の要素に処理を移す
				}
			// 会計情報学科で、必修科目の条件を満たしていない場合
			} else if (student_affiliation_id == 3 && required_subject_mastered_count < 2) {
				continue; // 次の要素に処理を移す
			}

			database_bean.delete("student", student_ids[i]); // データの削除
		}
	}

	String[] registered_opening_lecture_ids = database_bean.getColumnDataArray("registered_opening_lecture", 0); // 登録済み開講講義IDを格納

	// 登録済み開講講義IDが取得できた場合
	if (registered_opening_lecture_ids != null) {
		// 各登録済み開講講義データの削除
		for (int i = 0; i < registered_opening_lecture_ids.length; i++) {
			database_bean.delete("registered_opening_lecture", registered_opening_lecture_ids[i]);
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("削除の完了") %>
		データの削除が完了しました。<br />
		<br />

		<a href="<%= utility_bean.getAdminMainPageURL() %>">処理の選択</a> <a href="<%= utility_bean.getAdminLogoutPageURL() %>">ログアウト</a><br />

		<%= html_bean.getFooter() %>
