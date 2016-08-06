<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.sql.*" %>
<jsp:useBean id="html_bean" class="bean.HTMLBean" scope="application" />
<jsp:useBean id="database_bean" class="bean.DatabaseBean" scope="application" />
<%!
	// 指定された処理時間の結果を返す
	private String getProcessingTimeResult(long[] processing_time) {
		StringBuffer html = new StringBuffer(); // htmlを格納
		long average_processing_time = 0; // 平均処理時間を格納

		// 平均処理時間の計算
		for (int i = 0; i < processing_time.length; i++) {
			average_processing_time += processing_time[i];
		}
		average_processing_time /= processing_time.length;

		// 結果の設定
		html.append(average_processing_time + "(");
		for (int i = 0; i < processing_time.length; i++) {
			html.append(processing_time[i]);
			if ((i + 1) == processing_time.length) {
				html.append(")");
			} else {
				html.append("、");
			}
		}

		return html.toString(); // 結果を返す
	}
%>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBeanを格納
	String administrator_id = "admin"; // 管理者IDを格納
	long[][] processing_time = new long[3][10]; // 各処理の処理時間を格納
	long start_time = 0; // ループ前の時間を格納
	Connection connection = null; // コネクションを格納
	PreparedStatement prepared_statement = null; // 解析済みSQLを格納

	// 各処理の処理時間の計算(0: Statement、1: PreparedStatement(ローカル)、2: PreparedStatement(Bean))
	for (int i = 0; i < processing_time.length; i++) {
		// 10回の計算
		for (int j = 0; j < processing_time[i].length; j++) {
			start_time = System.currentTimeMillis(); // 現在の時間の取得

			// Statementの処理時間を求めている場合
			if (i == 0) {
				Class.forName("org.gjt.mm.mysql.Driver"); // MySQL用のJDBCドライバのロード
				connection = DriverManager.getConnection("jdbc:mysql://localhost/lecture?user=admin&password=admin&useUnicode=true&characterEncoding=Shift_JIS"); // コネクションの確立
			// PreparedStatement(ローカル)の処理時間を求めている場合
			} else if (i == 1) {
				Class.forName("org.gjt.mm.mysql.Driver"); // MySQL用のJDBCドライバのロード
				connection = DriverManager.getConnection("jdbc:mysql://localhost/lecture?user=admin&password=admin&useUnicode=true&characterEncoding=Shift_JIS"); // コネクションの確立
				prepared_statement = connection.prepareStatement("select password from administrator where administrator_id=?"); // 指定されたIDの管理者パスワードを選択する解析済みSQLの生成
			// PreparedStatement(Bean)の処理時間を求めている場合
			} else if (i == 2) {
				prepared_statement = database_bean.getAdministratorPasswordSelectingPreparedStatement(); // 指定されたIDの管理者パスワードを選択する解析済みSQLの取得
			}

			// 10000回のループ
			for (int k = 0; k < 10000; k++) {
				// Statementの処理時間を求めている場合
				if (i == 0) {
					ResultSet result_set = connection.createStatement().executeQuery("select password from administrator where administrator_id=\"" + administrator_id + "\""); // StatementによるSQLの実行
				// PreparedStatement(ローカル)の処理時間を求めている場合
				} else if (i == 1) {
					// PreparedStatementによるSQLの実行
					prepared_statement.setString(1, administrator_id);
					ResultSet result_set = prepared_statement.executeQuery();
				// PreparedStatement(Bean)の処理時間を求めている場合
				} else if (i == 2) {
					// Beanを利用したPreparedStatementによるSQLの実行
					prepared_statement.setString(1, administrator_id);
					ResultSet result_set = prepared_statement.executeQuery();
				}
			}

			processing_time[i][j] = System.currentTimeMillis() - start_time; // 処理時間の取得
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
%>
<%= html_bean.getHeader("平均処理時間のテスト(10000回のループを10回)") %>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">Statement: </td>
				<td><%= getProcessingTimeResult(processing_time[0]) %></td>
			</tr>
			<tr>
				<td align="right">PreparedStatement(ローカル): </td>
				<td><%= getProcessingTimeResult(processing_time[1]) %></td>
			</tr>
			<tr>
				<td align="right">PreparedStatement(Bean): </td>
				<td><%= getProcessingTimeResult(processing_time[2]) %></td>
			</tr>
		</table>
		(単位: ms)<br />

		<%= html_bean.getFooter() %>
