<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[
	String[] select_box_ids = {"true", "false"}; // �Z���N�g�{�b�N�X��ID���i�[
	String[] select_box_outlines = {"�s����", "�s���Ȃ�"}; // �Z���N�g�{�b�N�X�̌��o�����i�[
	int select_box_index; // �I�𗓂̃C���f�b�N�X���i�[

	// ���C�o�^���\�ł���Ɛݒ肳��Ă���ꍇ
	if (utility_bean.isRegisterable()) {
		select_box_index = 0; // 0��I�𗓂̃C���f�b�N�X�ɐݒ�
	// �ݒ肳��Ă��Ȃ��ꍇ
	} else {
		select_box_index = 1; // 1��I�𗓂̃C���f�b�N�X�ɐݒ�
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader("�ۂ̑I��") %>
		���C�o�^���s����悤�ɂ��邩�ǂ�����I�����A[�ݒ�]�{�^�����N���b�N���ĉ������B<br />
		<br />
		(���݂�<b><%= select_box_outlines[select_box_index] %></b>�悤�ɐݒ肳��Ă��܂�)<br />
		<br />

		<form method="POST" action="./regist.jsp">
			�J�u�u�`��<%= html_bean.getSelectBox("is_registerable", select_box_ids[select_box_index], select_box_ids, select_box_outlines, 3) %>�悤�ɂ���
			<input type="submit" value="�ݒ�" />
		</form>

		<a href="<%= utility_bean.getAdminMainPageURL() %>">�����̑I��</a><br />

		<%= html_bean.getFooter() %>
