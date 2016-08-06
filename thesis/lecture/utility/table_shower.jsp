<%@ page contentType = "text/html; charset=Shift_JIS" import = "java.io.*, java.sql.*" %>
<%!
	// ��M�f�[�^�̃G���R�[�h
	public String strEncode(String strVal) throws UnsupportedEncodingException {
		// ��M���������񂪋�łȂ��ꍇ
		if(strVal != null) {
			return new String(strVal.getBytes("ISO-8859-1"), "JISAutoDetect"); // �G���R�[�h�����������Ԃ�
		// ��ł���ꍇ
		} else {
			return null; // null�l��Ԃ�
		}
	}
%>
<html>
	<head>
		<title>�e�[�u���f�[�^�\</title>
		<link rel="stylesheet" type="text/css" href="./style.css">
		<meta name="content-language" content="ja">
	</head>

	<body>

		<form method="POST" action="./table_shower.jsp">
			<font color="blue"><b>�\������e�[�u���̓���</b></font><br>
			�e�[�u����:<input type="text" name="table" size="10" />
			<input type="submit" value="�\��" />
		</form>

<%
	// �e�[�u�����̃N�G�����̎擾
	String table = strEncode(request.getParameter("table"));

	// �N�G��������M���Ă��Ȃ��ꍇ
	if(table == null) {
		out.println("\t\t��̓��̓t�H�[���Ɍ������e�[�u��������͂��ĉ������B"); // �������̕\��
	// �N�G��������M���Ă���ꍇ
	} else {
		// �f�[�^��\������e�[�u�����̕\��
%>
		<table border="0" width="400" cellspacing="0" cellpadding="0">
			<tr>
				<td width="15" bgcolor="orange"></td>
				<td><font size="3" color="blue"><b>�e�[�u��"<%= table %>"�̃f�[�^</b></font></td>
			</tr>
			<tr>
				<td colspan="2" bgcolor="orange" height="2"></td>
			</tr>
		</table>
		<br>
<%
		// �e�[�u���̃f�[�^�̕\��
		try {
			Class.forName("org.gjt.mm.mysql.Driver"); // MySQL�p��JDBC�h���C�o�̃��[�h
			Connection db = DriverManager.getConnection("jdbc:mysql://localhost/lecture?user=admin&password=admin&useUnicode=true&characterEncoding=Shift_JIS"); 	// �f�[�^�x�[�X�ւ̃R�l�N�V�����̊m��
			db.setReadOnly(true); //�f�[�^�x�[�X��ǂݎ���p�ɐݒ�
			Statement sttSql = db.createStatement(); // SQL���s�p�I�u�W�F�N�g�̐���
			ResultSet rs = sttSql.executeQuery("select * from " + table); // �w�肳�ꂽ�e�[�u���̃f�[�^���擾����SQL�̎��s
			ResultSetMetaData rsMeta = rs.getMetaData(); // ���ʃZ�b�g�̃��^���̎擾

			// �t�B�[���h���̕\��
			out.println("\t\t<table border=\"0\">");
			out.println("\t\t\t<tr style=\"background: #00ccff;\">");
			for(int i = 0; i < rsMeta.getColumnCount(); i++) {
				out.println("\t\t\t\t<th>" + rsMeta.getColumnName(i + 1) + "</th>");
			}
			out.println("\t\t\t</tr>");

			// �t�B�[���h�f�[�^�̕\��
			while(rs.next()) {
				// �e�t�B�[���h�̃f�[�^�̕\��
				out.println("\t\t\t<tr style=\"background: #ffffcc;\">");
				for(int i = 0; i < rsMeta.getColumnCount(); i++) {
					// ���݂̑Ώۃt�B�[���h�f�[�^�̎擾
					String data = rs.getString(i + 1);

					// �擾�����f�[�^��100�����𒴂��Ă���ꍇ
					if(data.length() > 100) {
						data = data.substring(0, 100) + "..."; // �擪����100�����������𗘗p����
					}

					out.println("\t\t\t\t<td>" + data + "</td>"); // �f�[�^�̕\��
				}
				out.println("\t\t\t</tr>");
			}
			out.println("\t\t</table>");

			// SQL�̃N���[�Y
			rs.close();

			// �f�[�^�x�[�X�ւ̃R�l�N�V�����̃N���[�Y
			db.close();
		} catch(Exception e) {
			// �G���[�������̃��b�Z�[�W�̕\��
			out.println("\t\t<font color=\"red\"><b>�G���[: " + e + "</b></font><br>");
			out.println("\t\t�e�[�u���f�[�^�̓ǂݍ��݂Ɏ��s���܂����B�w�肳�ꂽ�e�[�u�������݂��邩�ǂ����m�F���ĉ������B<br>");
		}
	}
%>
	</body>
</html>
