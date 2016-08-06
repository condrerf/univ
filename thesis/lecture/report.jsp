<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	DatabaseBean database_bean = (DatabaseBean) application.getAttribute("database_bean"); // DatabaseBean‚ğŠi”[
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean‚ğŠi”[
	String student_id = request.getParameter("student_id"); // w’è‚³‚ê‚½ŠwĞ”Ô†‚ğŠi”[
	String[][] mastered_lecture_data = database_bean.getRowsData("mastered_lecture", student_id); // —šCÏ‚İu‹`ƒf[ƒ^‚ğŠi”[
	StringBuffer mastered_lecture_condition = new StringBuffer(); // —šCÏ‚İu‹`‚Ìó‹µ‚ğŠi”[
	int[] lecture_sums = new int[5]; // u‹`‚Ì‡Œv‚ğŠi”[
	int[] credit_sums = new int[5]; // ’PˆÊ‚Ì‡Œv‚ğŠi”[

	// —šCÏ‚İu‹`ƒf[ƒ^‚ªæ“¾‚Å‚«‚È‚©‚Á‚½ê‡
	if (mastered_lecture_data == null) {
		mastered_lecture_condition.append("@—šCÏ‚İ‚Ìu‹`‚Í‚ ‚è‚Ü‚¹‚ñ<br />\n"); // ƒƒbƒZ[ƒW‚Ì•t‰Á
	// æ“¾‚Å‚«‚½ê‡
	} else {
		StringBuffer[] mastered_lecture_tables = new StringBuffer[2]; // —šCÏ‚İu‹`ƒe[ƒuƒ‹‚ğŠi”[
		int last_mastered_lecture_data_index = (mastered_lecture_data.length - 1); // —šCÏ‚İu‹`ƒf[ƒ^‚ÌI’[‚ÌƒCƒ“ƒfƒbƒNƒX‚ğŠi”[
		int[] last_mastered_lecture_data_indexes = {last_mastered_lecture_data_index / 2, last_mastered_lecture_data_index}; // —šCÏ‚İu‹`ƒf[ƒ^‚ÌI’[‚ÌƒCƒ“ƒfƒbƒNƒX‚ğŠi”[
		int mastered_lecture_data_index = 0; // —šCÏ‚İu‹`ƒf[ƒ^‚ÌƒCƒ“ƒfƒbƒNƒX‚ğŠi”[

		// ŠJuu‹`ƒe[ƒuƒ‹‚Ìæ“¾
		for (int i = 0; i < mastered_lecture_tables.length; i++) {
			mastered_lecture_tables[i] = new StringBuffer(); // Œ»İƒAƒNƒZƒX‚µ‚Ä‚¢‚éŠJuu‹`ƒe[ƒuƒ‹‚Ì‰Šú‰»

			// —šCÏ‚İu‹`ƒf[ƒ^‚ÌƒCƒ“ƒfƒbƒNƒX‚ªŒ»İƒAƒNƒZƒX‚µ‚Ä‚¢‚é—šCÏ‚İu‹`ƒf[ƒ^‚ÌI’[‚ÌƒCƒ“ƒfƒbƒNƒX‚ğ‰z‚¦‚Ä‚¢‚È‚¢ê‡
			if (mastered_lecture_data_index <= last_mastered_lecture_data_indexes[i]) {
				// Œ©o‚µ‚Ìæ“¾
				mastered_lecture_tables[i].append("\t\t\t\t\t<table border=\"1\" bgcolor=\"#ffffff\" cellpadding=\"0\" cellspacing=\"0\">\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t<tr>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th width=\"200\">u‹`–¼</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th>’PˆÊ</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th width=\"50\" colspan=\"2\">•]‰¿</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<th>”N“x</th>\n");
				mastered_lecture_tables[i].append("\t\t\t\t\t\t</tr>\n");

				// —šCó‹µ‚Ìæ“¾
				while (mastered_lecture_data_index <= last_mastered_lecture_data_indexes[i]) {
					String lecture_id = mastered_lecture_data[mastered_lecture_data_index][0].substring(5); // —šCÏ‚İu‹`ID‚ÉŠÜ‚Ü‚ê‚éu‹`ID‚ğŠi”[
					String score = null; // “_”‚ğŠi”[

					// —šCÏ‚İu‹`ƒf[ƒ^‚Ì“_”—“‚ªnull‚Å‚ ‚éê‡
					if (mastered_lecture_data[mastered_lecture_data_index][1] == null) {
						score = "@"; // “_”‚ğ‹ó”’‚Éİ’è
					// null‚Å‚È‚¢ê‡
					} else {
						score = mastered_lecture_data[mastered_lecture_data_index][1]; // “_”‚Ìæ“¾
					}

					// —šCó‹µ‚Ìæ“¾
					mastered_lecture_tables[i].append("\t\t\t\t\t\t<tr height=\"20\">\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td>" + database_bean.getColumnData("lecture", 1, lecture_id) + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"right\">" + database_bean.getColumnData("lecture", 2, lecture_id) + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"right\">" + score + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"center\">" + utility_bean.getEvaluation(mastered_lecture_data[mastered_lecture_data_index][1]) + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t\t<td align=\"right\">" + mastered_lecture_data[mastered_lecture_data_index][2] + "</td>\n");
					mastered_lecture_tables[i].append("\t\t\t\t\t\t</tr>\n");

					int course_id = Integer.parseInt(mastered_lecture_data[mastered_lecture_data_index][0].substring(5, 6)); // ‰È–ÚID‚ğŠi”[
					int credit = Integer.parseInt(database_bean.getColumnData("lecture", 2, mastered_lecture_data[mastered_lecture_data_index][0].substring(5))); // ’PˆÊ”‚ğŠi”[

					// u‹`”‚Æ’PˆÊ‚Ì‰ÁZ
					lecture_sums[course_id]++;
					credit_sums[course_id] += credit;
					lecture_sums[4]++;
					credit_sums[4] += credit;

					mastered_lecture_data_index++; // —šCÏ‚İu‹`ƒf[ƒ^‚ÌƒCƒ“ƒfƒbƒNƒX‚Ì‰ÁZ
				}
			}
			mastered_lecture_tables[i].append("\t\t\t\t\t</table>\n");
		}

		// —šCÏ‚İu‹`‚Ìó‹µ‚Ìæ“¾
		mastered_lecture_condition.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		mastered_lecture_condition.append("\t\t\t<tr valign=\"top\">\n");
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append(mastered_lecture_tables[0]);
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append("\t\t\t\t<td width=\"20\">@</td>\n");
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append(mastered_lecture_tables[1]);
		mastered_lecture_condition.append("\t\t\t\t<td>\n");
		mastered_lecture_condition.append("\t\t\t</tr>\n");
		mastered_lecture_condition.append("\t\t</table>");
	}

	int[] necessary_credits = utility_bean.getNecessaryCredits(student_id); // •K—v‚È’PˆÊ‚ğŠi”[
	String[] outlines = {"ê–å‰È–Ú", null, "ŠO‘Œê‰È–Ú", "¸’èŠO‚Ì’PˆÊ", "‘²‹ÆŠ—v’PˆÊ"}; // Œ©o‚µ‚ğŠi”[

	// w’è‚³‚ê‚½Šw¶‚ª“üŠw‚µ‚½”N‚ª2002”NˆÈ~‚Å‚ ‚éê‡
	if (utility_bean.getEnteredYear(student_id) >= 2002) {
		outlines[1] = "‘SŠw‹¤’Ê‰È–Ú"; // "‘SŠw"‚ğ‹¤’Ê‰È–Ú‚Ì–¼‘O‚Éİ’è
	// 2002”NˆÈ‘O‚Å‚ ‚éê‡
	} else {
		outlines[1] = "Šw•”‹¤’Ê‰È–Ú"; // "Šw•”‹¤’Ê‰È–Ú"‚ğ‹¤’Ê‰È–Ú‚Ì–¼‘O‚Éİ’è
	}

	StringBuffer got_credit_condition = new StringBuffer(); // æ“¾‚µ‚½’PˆÊ‚Ìó‹µ‚ğŠi”[

	// æ“¾‚µ‚½’PˆÊ‚Ìó‹µ‚Ìæ“¾
	for (int i = 0; i < outlines.length; i++) {
		// 2‚Â–ÚˆÈ~‚Ìƒf[ƒ^‚Å‚ ‚éê‡
		if (i > 0) {
			got_credit_condition.append("\t\t\t");
		}

		got_credit_condition.append("<tr height=\"20\">\n");
		got_credit_condition.append("\t\t\t\t<th align=\"left\">" + outlines[i] + "</td>\n");
		got_credit_condition.append("\t\t\t\t<td>@</td>\n");
		got_credit_condition.append("\t\t\t\t<td align=\"right\">" + necessary_credits[i] + "</td>\n");
		got_credit_condition.append("\t\t\t\t<td align=\"right\">" + lecture_sums[i] + "</td>\n");
		got_credit_condition.append("\t\t\t\t<td align=\"right\">" + credit_sums[i] + "</td>\n");
		got_credit_condition.append("\t\t\t</tr>");

		// ÅI—v‘fˆÈŠO‚Ìƒf[ƒ^‚Å‚ ‚éê‡
		if (i < outlines.length - 1) {
			got_credit_condition.append("\n");
		}
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean‚ğŠi”[
%>
<%= html_bean.getHeader("‚±‚ê‚Ü‚Å‚Ì—šCó‹µ") %>
		(u‹`‚Ì—šCó‹µ)<br />
		<%= mastered_lecture_condition %>
		<br />

		(’PˆÊ‚Ìæ“¾ó‹µ)<br />
		<table border="1" bgcolor="#ffffff" cellpadding="0" cellspacing="0">
			<tr>
				<th rowspan="2">•ª–ìŒn—ñ</th>
				<th colspan="2">‘²‹Æ—vŒ</th>
				<th colspan="2">C“¾Ï</th>
			</tr>
			<tr>
				<th>‰È–Ú</th>
				<th>’PˆÊ</th>
				<th>‰È–Ú</th>
				<th>’PˆÊ</th>
			</tr>
			<%= got_credit_condition %>
		</table>
		<br />

		<a href="<%= utility_bean.getMainPageURL(student_id) %>">—šCu‹`‚Ì‘I‘ğ</a><br />

		<%= html_bean.getFooter() %>
