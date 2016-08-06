<%@ page contentType = "text/html; charset=Shift_JIS" import = "java.io.*, java.sql.*" %>
<%!
	// 受信データのエンコード
	public String strEncode(String strVal) throws UnsupportedEncodingException {
		// 受信した文字列が空でない場合
		if(strVal != null) {
			return new String(strVal.getBytes("ISO-8859-1"), "JISAutoDetect"); // エンコードした文字列を返す
		// 空である場合
		} else {
			return null; // null値を返す
		}
	}
%>
<html>
	<head>
		<title>テーブルデータ表</title>
		<link rel="stylesheet" type="text/css" href="./style.css">
		<meta name="content-language" content="ja">
	</head>

	<body>

		<form method="POST" action="./table_shower.jsp">
			<font color="blue"><b>表示するテーブルの入力</b></font><br>
			テーブル名:<input type="text" name="table" size="10" />
			<input type="submit" value="表示" />
		</form>

<%
	// テーブル名のクエリ情報の取得
	String table = strEncode(request.getParameter("table"));

	// クエリ情報を受信していない場合
	if(table == null) {
		out.println("\t\t上の入力フォームに見たいテーブル名を入力して下さい。"); // 説明文の表示
	// クエリ情報を受信している場合
	} else {
		// データを表示するテーブル名の表示
%>
		<table border="0" width="400" cellspacing="0" cellpadding="0">
			<tr>
				<td width="15" bgcolor="orange"></td>
				<td><font size="3" color="blue"><b>テーブル"<%= table %>"のデータ</b></font></td>
			</tr>
			<tr>
				<td colspan="2" bgcolor="orange" height="2"></td>
			</tr>
		</table>
		<br>
<%
		// テーブルのデータの表示
		try {
			Class.forName("org.gjt.mm.mysql.Driver"); // MySQL用のJDBCドライバのロード
			Connection db = DriverManager.getConnection("jdbc:mysql://localhost/lecture?user=admin&password=admin&useUnicode=true&characterEncoding=Shift_JIS"); 	// データベースへのコネクションの確立
			db.setReadOnly(true); //データベースを読み取り専用に設定
			Statement sttSql = db.createStatement(); // SQL実行用オブジェクトの生成
			ResultSet rs = sttSql.executeQuery("select * from " + table); // 指定されたテーブルのデータを取得するSQLの実行
			ResultSetMetaData rsMeta = rs.getMetaData(); // 結果セットのメタ情報の取得

			// フィールド名の表示
			out.println("\t\t<table border=\"0\">");
			out.println("\t\t\t<tr style=\"background: #00ccff;\">");
			for(int i = 0; i < rsMeta.getColumnCount(); i++) {
				out.println("\t\t\t\t<th>" + rsMeta.getColumnName(i + 1) + "</th>");
			}
			out.println("\t\t\t</tr>");

			// フィールドデータの表示
			while(rs.next()) {
				// 各フィールドのデータの表示
				out.println("\t\t\t<tr style=\"background: #ffffcc;\">");
				for(int i = 0; i < rsMeta.getColumnCount(); i++) {
					// 現在の対象フィールドデータの取得
					String data = rs.getString(i + 1);

					// 取得したデータが100文字を超えている場合
					if(data.length() > 100) {
						data = data.substring(0, 100) + "..."; // 先頭から100文字分だけを利用する
					}

					out.println("\t\t\t\t<td>" + data + "</td>"); // データの表示
				}
				out.println("\t\t\t</tr>");
			}
			out.println("\t\t</table>");

			// SQLのクローズ
			rs.close();

			// データベースへのコネクションのクローズ
			db.close();
		} catch(Exception e) {
			// エラー発生時のメッセージの表示
			out.println("\t\t<font color=\"red\"><b>エラー: " + e + "</b></font><br>");
			out.println("\t\tテーブルデータの読み込みに失敗しました。指定されたテーブルが存在するかどうか確認して下さい。<br>");
		}
	}
%>
	</body>
</html>
