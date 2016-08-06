<%@ page contentType = "text/html; charset=Shift_JIS" import = "bean.*" %>
<%
	String error_type = request.getParameter("error_type"); // �w�肳�ꂽ�G���[�̎�ނ��i�[
	String error_name = null; // �G���[�����i�[
	String error_message = null; // �G���[���b�Z�[�W���i�[
	String error_parameter_name = request.getParameter("error_parameter_name"); // �G���[�p�����[�^�����i�[
	String error_parameter_value = request.getParameter("error_parameter_value"); // �G���[�p�����[�^�l���i�[

	// �w�肳�ꂽ�G���[�̎�ނ��A�N�Z�X�G���[�ł���ꍇ
	if (error_type.equals("access_error")) {
		error_name = "�A�N�Z�X�G���["; // �G���[���̐ݒ�
		error_message = "�s���ȃA�N�Z�X�ł��B�����N�𗘗p���Đ������A�N�Z�X���ĉ������B"; // �G���[���b�Z�[�W�̐ݒ�
	// ���C�o�^���ۃG���[�ł���ꍇ
	} else if (error_type.equals("registration_denied_error")) {
		error_name = "���C�o�^���ۃG���["; // �G���[���̐ݒ�
		error_message = "���C�o�^���s���Ȃ��悤�ɐݒ肳��Ă���A�葱�����s�����Ƃ��ł��܂���B"; // �G���[���b�Z�[�W�̐ݒ�
	// �����e�i���X�G���[�ł���ꍇ
	} else if (error_type.equals("maintenance_error")) {
		error_name = "�����e�i���X�ɂ��G���["; // �G���[���̐ݒ�
		error_message = "���݂͊Ǘ��҂������e�i���X���s���Ă��邽�߁A�葱�����s���܂���B���΂炭��ɍēǂݍ��݂����s���ĉ������B"; // �G���[���b�Z�[�W�̐ݒ�
	// ���������̓G���[�ł���ꍇ
	} else if (error_type.equals("no_character_inputed_error")) {
		error_name = "���������̓G���["; // �G���[���̐ݒ�
		error_message = error_parameter_name + "�����͂���Ă��܂���B"; // �G���[���b�Z�[�W�̐ݒ�
	// �����G���[�ł���ꍇ
	} else if (error_type.equals("length_error")) {
		error_name = "�����G���["; // �G���[���̐ݒ�
		error_message = error_parameter_name + "���w�肳�ꂽ�����œ��͂���Ă��܂���B"; // �G���[���b�Z�[�W�̐ݒ�
	// ���������G���[�ł���ꍇ
	} else if (error_type.equals("character_included_error")) {
		error_name = "���������G���["; // �G���[���̐ݒ�
		error_message = error_parameter_name + "�ɐ����ȊO�̕������܂܂�Ă��܂��B"; // �G���[���b�Z�[�W�̐ݒ�
	// ���o�^�G���[�ł���ꍇ
	} else if (error_type.equals("not_registered_error")) {
		error_name = "���o�^�G���["; // �G���[���̐ݒ�
		error_message = "�w�肳�ꂽ" + error_parameter_name + "�ɊY������f�[�^�͓o�^����Ă��܂���B"; // �G���[���b�Z�[�W�̐ݒ�
	// �p�X���[�h���ύX�G���[�ł���ꍇ
	} else if (error_type.equals("password_not_changed_error")) {
		error_name = "�p�X���[�h���ύX�G���["; // �G���[���̐ݒ�
		error_message = "�p�X���[�h�����o�^��Ԃ̂܂܂ł��B�ύX�̎葱�����s���ĉ������B"; // �G���[���b�Z�[�W�̐ݒ�
	// �s��v�G���[�ł���ꍇ
	} else if (error_type.equals("not_accorded_error")) {
		error_name = "�s��v�G���["; // �G���[���̐ݒ�
		error_message = "�w�肳�ꂽ" + error_parameter_name + "���o�^���e�ƈ�v���܂���B"; // �G���[���b�Z�[�W�̐ݒ�
	// �P�ʃG���[�ł���ꍇ
	} else if (error_type.equals("credit_error")) {
		error_name = "�P�ʃG���["; // �G���[���̐ݒ�

		// �G���[�p�����[�^�����w�肳��Ă��Ȃ��ꍇ
		if (error_parameter_name == null) {
			error_message = "�t�w���ɓo�^���ꂽ�u�`�̒P�ʂ̍��v���A1�N�ɗ��C�\�ȒP�ʂ̍ő�l�ł���56�ɒB���Ă��܂��B���̒l�𒴂���o�^�͂ł��܂���B"; // �G���[���b�Z�[�W�̐ݒ�
		} else {
			error_message = error_parameter_name + "���A���C�\�ȒP�ʂ̍ő�l�𒴂��Ă��܂��B"; // �G���[���b�Z�[�W�̐ݒ�
		}
	// �ʃR�[�X�̒P�ʃG���[�ł���ꍇ
	} else if (error_type.equals("another_course_credit_error")) {
		error_name = "�ʃR�[�X�̒P�ʃG���["; // �G���[���̐ݒ�
		error_message = error_parameter_name + "���A�ʃR�[�X�̗��C�\�ȒP�ʂ̍ő�l�ł���30�𒴂��Ă��܂��B"; // �G���[���b�Z�[�W�̐ݒ�
	// ���N�����g�p�G���[�ł���ꍇ
	} else if (error_type.equals("birthday_used_error")) {
		error_name = "���N�����g�p�G���["; // �G���[���̐ݒ�
		error_message = "�p�X���[�h�ɐ��N�������g�p����Ă��܂��B�Z�L�����e�B�ォ�狖�ł��܂���B"; // �G���[���b�Z�[�W�̐ݒ�
	// �ʂ̊Ǘ��҂ɂ�郁���e�i���X�G���[�ł���ꍇ
	} else if (error_type.equals("another_administrator_maintaining_error")) {
		error_name = "�ʂ̊Ǘ��҂̃����e�i���X�ɂ��G���["; // �G���[���̐ݒ�
		error_message = "���݂͕ʂ̊Ǘ��҂������e�i���X���s���Ă��邽�߁A�葱�����s���܂���B���΂炭��ɍēǂݍ��݂����s���ĉ������B"; // �G���[���b�Z�[�W�̐ݒ�
	// �f�[�^���w��G���[�ł���ꍇ
	} else if (error_type.equals("no_data_specified_error")) {
		error_name = "�f�[�^���w��G���["; // �G���[���̐ݒ�
		error_message = "�폜����f�[�^���w�肳��Ă��܂���B"; // �G���[���b�Z�[�W�̐ݒ�
	// �폜�G���[�ł���ꍇ
	} else if (error_type.equals("delete_error")) {
		error_name = "�폜�G���["; // �G���[���̐ݒ�
		error_message = "�w�肳�ꂽ" + error_parameter_name + "�͑��̃e�[�u���Ŏg�p����Ă��邽�߂ɍ폜���s���܂���B"; // �G���[���b�Z�[�W�̐ݒ�
	}

	String error_content = null; // �G���[���e���i�[

	// �G���[�p�����[�^�l���w�肳��Ă��Ȃ��ꍇ
	if (error_parameter_value != null) {
		error_content = "(�G���[����������" + error_parameter_name + ": \"<b>" + error_parameter_value + "</b>\")<br />"; // �G���[���e�̐ݒ�
	// �w�肳��Ă���ꍇ
	} else {
		error_content = ""; // �G���[���e���󔒂ɐݒ�
	}

	String link = null; // �����N���i�[

	// �w�肳�ꂽ�G���[�̎�ނ��A�N�Z�X�G���[�ł���ꍇ
	if (error_type.equals("access_error")) {
		String link_url = null; // �����N��URL���i�[
		UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[

		// �v�����󂯂��y�[�W���Ǘ��f�B���N�g���ɑ����Ă���ꍇ
		if (request.getRequestURL().toString().indexOf(utility_bean.getAdminDirectoryURL()) != -1) {
			link_url = utility_bean.getAdminLoginPageURL(); // �Ǘ��p�̃��O�C���y�[�W�������N��URL�̐ݒ�
		// �����Ă��Ȃ��ꍇ
		} else {
			link_url = utility_bean.getLoginPageURL(); // ���O�C���y�[�W�������N��URL�̐ݒ�
		}

		link = "<a href=\"" + link_url + "\">���O�C���y�[�W</a><br />"; // �����N�̎擾
	// ���̑��̃G���[�ł���ꍇ
	} else {
		link = ""; // �����N���󔒂ɐݒ�
	}

	HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
%>
<%= html_bean.getHeader(error_name) %>
		<font color="red"><%= error_message %></font><br />
		<%= error_content %>
		<br />

		<%= link %>

		<%= html_bean.getFooter() %>
