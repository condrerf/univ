<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String student_id = request.getParameter("student_id"); // 指定された学籍番号を格納
	String[][] mastered_lecture_data = database_bean.getRowsData("mastered_lecture", student_id); // 履修済み講義データを格納
	StringBuffer mastered_lecture_condition = new StringBuffer(); // 履修済み講義の状況を格納
	int[] lecture_sums = new int[5]; // 講義の合計を格納
	int[] credit_sums = new int[5]; // 単位の合計を格納

	// 履修済み講義データが取得できなかった場合
	if (mastered_lecture_data == null) {
		mastered_lecture_condition.append("　履修済みの講義はありません<br />\n"); // メッセージの付加
	// 取得できた場合
	} else {
		StringBuffer[] mastered_lecture_tables = new StringBuffer[2]; // 履修済み講義テーブルを格納
		int last_mastered_lecture_data_index = (mastered_lecture_data.length - 1); // 履修済み講義データの終端のインデックスを格納
		int[] last_mastered_lecture_data_indexes = {last_mastered_lecture_data_index / 2, last_mastered_lecture_data_index}; // 履修済み講義データの終端のインデックスを格納
		int mastered_lecture_data_index = 0; // 履修済み講義データのインデックスを格納

		// 開講講義テーブルの取得
		for (int i = 0; i < mastered_lecture_tables.length; i++) {
			mastered_lecture_tables[i] = new StringBuffer(); // 現在アクセスしている開講講義テーブルの初期化

			// 履修済み講義データのインデックスが現在アクセスしている履修済み講義データの終端のインデックスを越えていない場合
			if (mastered_lecture_data_index <= last_mastered_lecture_data_indexes[i]) {
				// 見出しの取得
				mastered_lecture_tables[i].append("\t\t\t\t\t<table border=\"1\" bgcolor=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\">\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t<tr>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th width=\"200\">講義名</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th>単位</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th width=\"50\" colspan=\"2\">評価</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th>年度</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t</tr>\n");

				// 履修状況の取得
				while (mastered_lecture_data_index <= last_mastered_lecture_data_indexes[i]) {
					String lecture_id = mastered_lecture_data[mastered_lecture_data_index][0].substring(5); // 履修済み講義IDに含まれる講義IDを格納
					String score = null; // 点数を格納

					// 履修済み講義データの点数欄がnullである場合
					if (mastered_lecture_data[mastered_lecture_data_index][1] == null) {
						score = "　"; // 点数を空白に設定
					// nullでない場合
					} else {
						score = mastered_lecture_data[mastered_lecture_data_index][1]; // 点数の取得
					}

					// 履修状況の取得
					mastered_lecture_tables[i].append("\t\t\t\t\t\t<tr height=\"20\">\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td>" + database_bean.getColumnData("lecture", 1, lecture_id) + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"right\">" + database_bean.getColumnData("lecture", 2, lecture_id) + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"right\">" + score + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"center\">" + utility_bean.getEvaluation(mastered_lecture_data[mastered_lecture_data_index][1]) + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"right\">" + mastered_lecture_data[mastered_lecture_data_index][2] + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t</tr>\n");

					int course_id = Integer.parseInt(mastered_lecture_data[mastered_lecture_data_index][0].substring(5, 6)); // 科目IDを格納
					int credit = Integer.parseInt(database_bean.getColumnData("lecture", 2, mastered_lecture_data[mastered_lecture_data_index][0].substring(5))); // 単位数を格納

					// 講義数と単位の加算
					lecture_sums[course_id]++;
					credit_sums[course_id] += credit;
					lecture_sums[4]++;
					credit_sums[4] += credit;

					mastered_lecture_data_index++; // 履修済み講義データのインデックスの加算
				}
			}
			mastered_lecture_tables[i].append("\t\t\t\t\t</table>\n");
		}

		// 履修済み講義の状況の取得
		mastered_lecture_condition.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		mastered_lecture_condition.append("\t\t\t<tr valign=\"top\">\n");
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append(mastered_lecture_tables[0]);
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append("\t\t\t\t<td width=\"20\">　</td>\n");
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append(mastered_lecture_tables[1]);
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append("\t\t\t</tr>\n");
		mastered_lecture_condition.append("\t\t</table>");
	}

	int[] necessary_credits = utility_bean.getNecessaryCredits(student_id); // 必要な単位を格納
	String[] outlines = {"専門科目", null, "外国語科目", "査定外の単位", "卒業所要単位"}; // 見出しを格納

	// 指定された学生が入学した年が2002年以降である場合
	if (utility_bean.getEnteredYear(student_id) >= 2002) {
		outlines[1] = "全学共通科目"; // "全学"を共通科目の名前に設定
	// 2002年以前である場合
	} else {
		outlines[1] = "学部共通科目"; // "学部共通科目"を共通科目の名前に設定
	}

	StringBuffer got_credit_condition = new StringBuffer(); // 取得した単位の状況を格納

	// 取得した単位の状況の取得
	for (int i = 0; i < outlines.length; i++) {
		// 2つ目以降のデータである場合
		if (i > 0) {
			got_credit_condition.append("\t\t\t");
		}

		got_credit_condition.append("<tr height=\"20\">\n");
		got_credit_condition.append("\t\t\t\t<th align=\"left\">" + outlines[i] + "</td>\n");
		got_credit_condition.append("\t\t\t\t<td>　</td>\n");
		got_credit_condition.append("\t\t\t\t<td align=\"right\">" + necessary_credits[i] + "</td>\n");
		got_credit_condition.append("\t\t\t\t<td align=\"right\">" + lecture_sums[i] + "</td>\n");
		got_credit_condition.append("\t\t\t\t<td align=\"right\">" + credit_sums[i] + "</td>\n");
		got_credit_condition.append("\t\t\t</tr>");

		// 最終要素以外のデータである場合
		if (i < outlines.length - 1) {
			got_credit_condition.append("\n");
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("これまでの履修状況") %>
		(講義の履修状況)<br />
		<%= mastered_lecture_condition %>
		<br />

		(単位の取得状況)<br />
		<table border="1" bgcolor="#ffffff" cellpadding="0" cellspacing="0">
			<tr>
				<th rowspan="2">分野系列</th>
				<th colspan="2">卒業要件</th>
				<th colspan="2">修得済</th>
			</tr>
			<tr>
				<th>科目</th>
				<th>単位</th>
				<th>科目</th>
				<th>単位</th>
			</tr>
			<%= got_credit_condition %>
		</table>
		<br />

		<a href="<%= utility_bean.getMainPageURL(student_id) %>">履修講義の選択</a><br />

		<%= html_bean.getFooter() %>
