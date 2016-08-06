<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*, java.sql.*" %>
<jsp:useBean id="html_bean" class="bean.HTMLBean" scope="application" />
<jsp:useBean id="database_bean" class="bean.DatabaseBean" scope="application" />
<%!
	// �w�肳�ꂽ�������Ԃ̌��ʂ�Ԃ�
	private String getProcessingTimeResult(long[] processing_time) {
		StringBuffer html = new StringBuffer(); // html���i�[
		long average_processing_time = 0; // ���Ϗ������Ԃ��i�[

		// ���Ϗ������Ԃ̌v�Z
		for (int i = 0; i < processing_time.length; i++) {
			average_processing_time += processing_time[i];
		}
		average_processing_time /= processing_time.length;

		// ���ʂ̐ݒ�
		html.append(average_processing_time + "(");
		for (int i = 0; i < processing_time.length; i++) {
			html.append(processing_time[i]);
			if ((i + 1) == processing_time.length) {
				html.append(")");
			} else {
				html.append("�A");
			}
		}

		return html.toString(); // ���ʂ�Ԃ�
	}
%>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean���i�[
	String administrator_id = "admin"; // �Ǘ���ID���i�[
	long[][] processing_time = new long[3][10]; // �e�����̏������Ԃ��i�[
	long start_time = 0; // ���[�v�O�̎��Ԃ��i�[
	Connection connection = null; // �R�l�N�V�������i�[
	PreparedStatement prepared_statement = null; // ��͍ς�SQL���i�[

	// �e�����̏������Ԃ̌v�Z(0: Statement�A1: PreparedStatement(���[�J��)�A2: PreparedStatement(Bean))
	for (int i = 0; i < processing_time.length; i++) {
		// 10��̌v�Z
		for (int j = 0; j < processing_time[i].length; j++) {
			start_time = System.currentTimeMillis(); // ���݂̎��Ԃ̎擾

			// Statement�̏������Ԃ����߂Ă���ꍇ
			if (i == 0) {
				Class.forName("org.gjt.mm.mysql.Driver"); // MySQL�p��JDBC�h���C�o�̃��[�h
				connection = DriverManager.getConnection("jdbc:mysql://localhost/lecture?user=admin&password=admin&useUnicode=true&characterEncoding=Shift_JIS"); // �R�l�N�V�����̊m��
			// PreparedStatement(���[�J��)�̏������Ԃ����߂Ă���ꍇ
			} else if (i == 1) {
				Class.forName("org.gjt.mm.mysql.Driver"); // MySQL�p��JDBC�h���C�o�̃��[�h
				connection = DriverManager.getConnection("jdbc:mysql://localhost/lecture?user=admin&password=admin&useUnicode=true&characterEncoding=Shift_JIS"); // �R�l�N�V�����̊m��
				prepared_statement = connection.prepareStatement("select password from administrator where administrator_id=?"); // �w�肳�ꂽID�̊Ǘ��҃p�X���[�h��I�������͍ς�SQL�̐���
			// PreparedStatement(Bean)�̏������Ԃ����߂Ă���ꍇ
			} else if (i == 2) {
				prepared_statement = database_bean.getAdministratorPasswordSelectingPreparedStatement(); // �w�肳�ꂽID�̊Ǘ��҃p�X���[�h��I�������͍ς�SQL�̎擾
			}

			// 10000��̃��[�v
			for (int k = 0; k < 10000; k++) {
				// Statement�̏������Ԃ����߂Ă���ꍇ
				if (i == 0) {
					ResultSet result_set = connection.createStatement().executeQuery("select password from administrator where administrator_id=\"" + administrator_id + "\""); // Statement�ɂ��SQL�̎��s
				// PreparedStatement(���[�J��)�̏������Ԃ����߂Ă���ꍇ
				} else if (i == 1) {
					// PreparedStatement�ɂ��SQL�̎��s
					prepared_statement.setString(1, administrator_id);
					ResultSet result_set = prepared_statement.executeQuery();
				// PreparedStatement(Bean)�̏������Ԃ����߂Ă���ꍇ
				} else if (i == 2) {
					// Bean�𗘗p����PreparedStatement�ɂ��SQL�̎��s
					prepared_statement.setString(1, administrator_id);
					ResultSet result_set = prepared_statement.executeQuery();
				}
			}

			processing_time[i][j] = System.currentTimeMillis() - start_time; // �������Ԃ̎擾
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("���Ϗ������Ԃ̃e�X�g(10000��̃��[�v��10��)") %>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">Statement: </td>
				<td><%= getProcessingTimeResult(processing_time[0]) %></td>
			</tr>
			<tr>
				<td align="right">PreparedStatement(���[�J��): </td>
				<td><%= getProcessingTimeResult(processing_time[1]) %></td>
			</tr>
			<tr>
				<td align="right">PreparedStatement(Bean): </td>
				<td><%= getProcessingTimeResult(processing_time[2]) %></td>
			</tr>
		</table>
		(�P��: ms)<br />

		<%= html_bean.getFooter() %>
