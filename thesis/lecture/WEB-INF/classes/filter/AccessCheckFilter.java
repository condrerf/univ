// �p�b�P�[�W�̐錾
package filter;

// �t�B���^�̓���ɕK�v�ȃp�b�P�[�W�̃C���|�[�g
import java.io.IOException;
import javax.servlet.*;

// ���\�b�h���̏����ŗ��p����p�b�P�[�W�̃C���|�[�g
import bean.*;
import java.beans.Beans;
import javax.servlet.http.*;

/**
 * �A�N�Z�X�`�F�b�N���s���t�B���^
 * @author Ryo Fukushima
 */
public class AccessCheckFilter implements Filter {
	/** �t�B���^�̐ݒ�����i�[ */
	private FilterConfig config = null;

	/**
	 * �t�B���^�J�n���̏���
	 * @param config �t�B���^�̐ݒ���
	 */
	public void init(FilterConfig config) {
		this.config = config; // �t�B���^�̐ݒ���̎擾
	}

	/** �t�B���^�I�����̏��� */
	public void destroy() {
		config = null; // �t�B���^�̐ݒ���̔j��
	}

	/*
	 * �t�B���^�̗v���ɑ΂��鏈��
	 * (ServletRequest��ServletResponse�̃L���X�e�B���O)
	 * @param request �v�����
	 * @param response �������
	 * @param chain �t�B���^�̘A�����
	 * @exception IOException
	 * @exception ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	/**
	 * �t�B���^�̗v���ɑ΂��鏈��
	 * @param request �v�����
	 * @param response �������
	 * @param chain �t�B���^�̘A�����
	 * @exception IOException
	 * @exception ServletException
	 */
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletContext application = config.getServletContext(); // �A�v���P�[�V���������i�[
		HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBean���i�[
		UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBean���i�[

		// UtilityBean���擾�ł��Ȃ������ꍇ
		if (utility_bean == null) {
			try {
				// �eBean�̐���
				DatabaseBean database_bean = (DatabaseBean) Beans.instantiate(this.getClass().getClassLoader(), "bean.DatabaseBean");
				html_bean = (HTMLBean) Beans.instantiate(this.getClass().getClassLoader(), "bean.HTMLBean");
				utility_bean = (UtilityBean) Beans.instantiate(this.getClass().getClassLoader(), "bean.UtilityBean");

				// �eBean�̐ݒ�
				database_bean.setUtilityBean(utility_bean);
				html_bean.setDatabaseBean(database_bean);
				html_bean.setUtilityBean(utility_bean);
				utility_bean.setDatabaseBean(database_bean);

				utility_bean.setApplicationURL(request); // �A�v���P�[�V������URL�̐ݒ�

				// �eBean�̋��L��
				application.setAttribute("database_bean", database_bean);
				application.setAttribute("html_bean", html_bean);
				application.setAttribute("utility_bean", utility_bean);
			} catch (ClassNotFoundException e) {
			}
		}

		String requested_page_url = request.getRequestURL().toString(); // �v�����ꂽ�y�[�W��URL���i�[

		// �v�����ꂽ�y�[�W�����O�C���y�[�W�ȊO�̃y�[�W�ł���ꍇ
		if (!requested_page_url.equals(utility_bean.getLoginPageURL()) && !requested_page_url.equals(utility_bean.getAdminLoginPageURL())) {
			String referer = request.getHeader("referer"); // �Q�ƌ��y�[�W��URL���i�[

			// �Q�ƌ��y�[�W��URL��񂪂Ȃ��A���̓V�X�e���O����̃A�N�Z�X�ł���ꍇ
			if (referer == null || referer.indexOf(utility_bean.getApplicationURL()) == -1) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("access_error")).forward(request, response); // �G���[�y�[�W�ւ̓]��
			}
		}

		// �v�����ꂽ�y�[�W���Ǘ��f�B���N�g���ɑ����Ă��Ȃ��ꍇ
		if (requested_page_url.indexOf(utility_bean.getAdminDirectoryURL()) == -1) {
			// ���C�o�^���s���Ȃ��悤�ɐݒ肳��Ă���ꍇ
			if (!utility_bean.isRegisterable()) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("registration_denied_error")).forward(request, response); // �G���[�y�[�W�ւ̓]��
			}

			// �����e�i���X���ł���ꍇ
			if (utility_bean.isMaintained()) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("maintenance_error")).forward(request, response); // �G���[�y�[�W�ւ̓]��
			}
		}

		request.setCharacterEncoding("Shift_JIS"); // �����R�[�h�̐ݒ�
		chain.doFilter(request, response); // �v�����ꂽ�t�@�C���̎��s
	}
}
