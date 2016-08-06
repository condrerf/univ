<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.sql.SQLException" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納
	String table_name = request.getParameter("table_name"); // 指定されたテーブル名を格納
	String edit_type = request.getParameter("edit_type"); // 指定された編集の種類を格納
	String[] form_component_names = utility_bean.getFormComponentNames(table_name); // フォームの構成要素名を格納
	String[] form_data = utility_bean.getFormData(form_component_names, request); // フォームデータを格納
	String[] table_column_names = database_bean.getColumnNames(table_name); // 指定されたテーブルの列名を格納
	String[] register_data = new String[table_column_names.length]; // 登録データを格納
	String data_affiliation_number = utility_bean.getDataAffiliationNumber(table_name, request); // データ所属番号を格納
	int register_data_index = 0; // 登録データのインデックスを格納

	// フォームデータのチェックと登録データへの格納
	for (int i = 0; i < form_data.length; i++) {
		// 学生データの新規登録で、現在アクセスしているフォームデータの要素がパスワードである場合
		if (table_name.equals("student") && edit_type.equals("new") && form_component_names[i].equals("password")) {
			register_data[register_data_index] = form_data[i + 1]; // 生年月日を設定
			register_data_index++; // 登録データのインデックスの加算
			continue; // 次の要素に処理を移す
		}

		// 現在アクセスしているフォームデータがlectureテーブルの同種類・同名データ存在時に別データとして登録するかどうかの設定である場合
		if (table_name.equals("lecture") && i == (form_data.length - 1)) {
			break; // ループから抜ける
		}

		// 現在アクセスしているフォームデータの要素がnull、又は講義テーブルの講義番号要素にアクセスしていて、かつ同種類・同名データ存在時に別データとして登録すると設定されている場合
		if (form_data[i] == null || form_data[i].equals("null") || (table_name.equals("lecture") && edit_type.equals("edit") && i == 1 && form_data[form_data.length - 1] != null)) {
			// 登録データのインデックスがID欄、又はID欄の次に設定されている場合
			if (register_data_index <= 1) {
				int data_number; // データ番号を格納
				int data_number_position; // データ番号の位置を格納

				// 指定されたテーブル名がstudentである場合
				if (table_name.equals("student")) {
					data_number = Integer.parseInt(register_data[0].substring(2)) * 100; // 学科所属番号の100倍の値をデータ番号の初期値に設定
					register_data[0] = register_data[0].substring(0, 2); // 学生所属番号に含まれる学科所属番号のカット
					data_number_position = 2; // 2をデータ番号の位置に設定
				// それ以外のテーブル名である場合
				} else {
					data_number = -1; // -1をデータ番号の初期値に設定

					// 指定されたテーブル名がcourseである場合
					if (table_name.equals("course")) {
						register_data[0] = ""; // 空白をIDの要素に設定
						register_data_index++; // 登録データのインデックスの加算
						data_number_position = 0; // 0をデータ番号の位置に設定
					// lectureである場合
					} else if (table_name.equals("lecture")) {
						// 同種類のデータを登録する場合
						if (edit_type.equals("edit") && form_data[form_data.length - 1] != null) {
							edit_type = "new"; // 編集の種類の変更
							register_data[0] += form_data[1].substring(0, 2); // IDに講義種類番号を付加
						}

						data_number_position = register_data[0].length(); // IDの要素の桁数をデータ番号の位置に設定
					// それ以外のテーブル名である場合
					} else {
						data_number_position = data_affiliation_number.length(); // データ所属番号の桁数をデータ番号の位置に設定
					}
				}

				int data_number_length; // データ番号の桁数を格納

				// 指定されたテーブル名がlectureである場合
				if (table_name.equals("lecture")) {
					data_number_length = 2; // データ番号の桁数に2を設定
				// それ以外のテーブル名である場合
				} else {
					data_number_length = database_bean.getIDLength(table_name) - data_number_position; // IDの桁数とデータ所属番号の桁数の差を設定
				}

				String[] registered_data_ids = database_bean.getColumnDataArray(table_name, 0, data_affiliation_number); // 指定されたテーブルの指定されたデータ所属番号に登録されているデータのIDを格納

				// 同じ所属のデータが既に登録されている場合
				if (registered_data_ids != null) {
					int start_registered_data_ids_index = 0; // 登録済みデータのIDの開始インデックスを格納

					// 同種類の講義データを登録する場合
					if (table_name.equals("lecture") && form_data[form_data.length - 1] != null) {
						// 登録するデータと同種類の登録済みデータのIDが格納されているインデックスの取得
						while (!register_data[0].equals(registered_data_ids[start_registered_data_ids_index].substring(0, data_number_position))) {
							start_registered_data_ids_index++;
						}
					}

					// 有効なデータ番号の取得
					for (int j = start_registered_data_ids_index; j < registered_data_ids.length; j++) {
						int present_data_number = Integer.parseInt(registered_data_ids[j].substring(data_number_position, data_number_position + data_number_length)); // 現在のデータ番号を格納

						// 現在のデータ番号が記憶しているデータ番号よりも1だけ多い場合
						if (present_data_number == data_number + 1) {
							data_number = present_data_number; // 現在のデータ番号を記憶
						// 1だけ多いのではない、又は(講義データで)番号が等しくて同種類のデータを登録する場合
						} else if (present_data_number != data_number || form_data[form_data.length - 1] != null) {
							break; // ループを抜ける
						}
					}
				}

				data_number++; // データ番号の加算
				int data_number_length_subtract = data_number_length - new String("" + data_number).length(); // 指定されたデータ番号の桁数と取得したデータ番号の桁数の差を格納

				// 指定されたデータ番号の桁数と取得したデータ番号の桁数の差が0でない場合
				if (data_number_length_subtract != 0) {
					// 差の数だけIDに"0"を付加
					for (int j = 0; j < data_number_length_subtract; j++) {
						register_data[0] += "0";
					}
				}

				register_data[0] += data_number; // 加算したデータ種類番号をIDに付加

				// 指定されたテーブル名がlectureで、IDの要素の桁数が5である場合
				if (table_name.equals("lecture") && register_data[0].length() == 5) {
					register_data[0] += "00"; // IDの付加
				}
			// ID欄の次の次以降に設定されている場合
			} else {
				register_data_index++; // 登録データのインデックスの加算
			}
		// 空白である場合
		} else if (form_data[i].equals("")) {
			application.getRequestDispatcher(utility_bean.getErrorPageURI("no_character_inputed_error", utility_bean.getJapaneseWord(form_component_names[i]))).forward(request, response); // エラーページへの転送
		// 上記の条件を満たさない場合
		} else {
			// 現在アクセスしているフォーム構成要素データ名が現在アクセスしているテーブルの列名と等しい、又は登録データのIDの要素がnullである場合
			if (form_component_names[i].equals(table_column_names[register_data_index]) || register_data[0] == null) {
				// 現在アクセスしているフォーム構成要素データ名が現在アクセスしているテーブルの列名と等しい場合
				if (form_component_names[i].equals(table_column_names[register_data_index])) {
					register_data[register_data_index] = form_data[i]; // フォームデータの要素を登録データの要素に設定
				// 等しくない場合
				} else {
					register_data[0] = form_data[i]; // フォームデータの要素を登録データのIDの要素に設定
				}

				register_data_index++; // 登録データのインデックスの加算
			// 上記の条件を満たさない場合
			} else {
				register_data[0] += form_data[i]; // フォームデータの要素の付加
			}
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
	String[] table_row_data = database_bean.getRowData(table_name, register_data[0]); // 指定されたテーブルの行データを格納
	StringBuffer register_content = new StringBuffer(); // 登録内容を格納

	// 登録情報の取得
	for (int i = 0; i < table_column_names.length; i++) {
		// 現在アクセスしているテーブルの列名のデータがnullでない場合
		if (register_data[i] != null) {
			if (i > 0) {
				register_content.append("\t\t\t");
			}
			register_content.append("<input type=\"hidden\" name=\"" + table_column_names[i] + "\" value=\"" + register_data[i] + "\" />\n");
		}
	}
	register_content.append("\n");
	register_content.append("\t\t\t");

	// テーブルの行データがnullでない場合(データの編集を行っている場合)
	if(table_row_data != null) {
		// 編集内容の取得
		register_content.append("<table border=\"0\" cellpadding=\"0\" cellspacing\"0\">\n");
		register_content.append("\t\t\t\t<tr>\n");
		register_content.append("\t\t\t\t\t<th>編集前</th>\n");
		register_content.append("\t\t\t\t\t<td></td>\n");
		register_content.append("\t\t\t\t\t<th>編集後</th>\n");
		register_content.append("\t\t\t\t</tr>\n");
		register_content.append("\t\t\t\t<tr valign=\"center\">\n");
		register_content.append("\t\t\t\t\t<td>\n");
		register_content.append("\t\t\t\t\t\t" + html_bean.getDataEditTable(table_column_names, table_row_data, 6));
		register_content.append("\t\t\t\t\t</td>\n");
		register_content.append("\t\t\t\t\t<td>→</td>\n");
		register_content.append("\t\t\t\t\t<td>\n");
		register_content.append("\t\t\t\t\t\t" + html_bean.getDataEditTable(table_column_names, register_data, 6));
		register_content.append("\t\t\t\t\t</td>\n");
		register_content.append("\t\t\t\t</tr>\n");
		register_content.append("\t\t\t</table>\n");
	// nullである場合(新規登録である場合)
	} else {
		register_content.append(html_bean.getDataEditTable(table_column_names, register_data, 3)); // 登録内容の取得
	}
%>
<%= html_bean.getHeader("登録内容の確認") %>
		登録する<%= utility_bean.getJapaneseWord(table_name) %>データの内容を確認し、問題がなければ[登録]ボタンをクリックして下さい。<br />

		<form method="POST" action="./regist.jsp">
			<input type="hidden" name="table_name" value="<%= table_name %>" />
			<input type="hidden" name="edit_type" value="<%= edit_type %>" />
			<%= register_content %>
			<input type="submit" value="登録" />
		</form>

		<a href="<%= request.getHeader("referer") %>">戻る</a><br />

		<%= html_bean.getFooter() %>
