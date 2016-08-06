// パッケージの宣言
package filter;

// フィルタの動作に必要なパッケージのインポート
import java.io.IOException;
import javax.servlet.*;

// メソッド内の処理で利用するパッケージのインポート
import bean.*;
import java.beans.Beans;
import javax.servlet.http.*;

/**
 * アクセスチェックを行うフィルタ
 * @author Ryo Fukushima
 */
public class AccessCheckFilter implements Filter {
	/** フィルタの設定情報を格納 */
	private FilterConfig config = null;

	/**
	 * フィルタ開始時の処理
	 * @param config フィルタの設定情報
	 */
	public void init(FilterConfig config) {
		this.config = config; // フィルタの設定情報の取得
	}

	/** フィルタ終了時の処理 */
	public void destroy() {
		config = null; // フィルタの設定情報の破棄
	}

	/*
	 * フィルタの要求に対する処理
	 * (ServletRequestとServletResponseのキャスティング)
	 * @param request 要求情報
	 * @param response 応答情報
	 * @param chain フィルタの連結情報
	 * @exception IOException
	 * @exception ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	/**
	 * フィルタの要求に対する処理
	 * @param request 要求情報
	 * @param response 応答情報
	 * @param chain フィルタの連結情報
	 * @exception IOException
	 * @exception ServletException
	 */
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletContext application = config.getServletContext(); // アプリケーション情報を格納
		HTMLBean html_bean = (HTMLBean) application.getAttribute("html_bean"); // HTMLBeanを格納
		UtilityBean utility_bean = (UtilityBean) application.getAttribute("utility_bean"); // UtilityBeanを格納

		// UtilityBeanが取得できなかった場合
		if (utility_bean == null) {
			try {
				// 各Beanの生成
				DatabaseBean database_bean = (DatabaseBean) Beans.instantiate(this.getClass().getClassLoader(), "bean.DatabaseBean");
				html_bean = (HTMLBean) Beans.instantiate(this.getClass().getClassLoader(), "bean.HTMLBean");
				utility_bean = (UtilityBean) Beans.instantiate(this.getClass().getClassLoader(), "bean.UtilityBean");

				// 各Beanの設定
				database_bean.setUtilityBean(utility_bean);
				html_bean.setDatabaseBean(database_bean);
				html_bean.setUtilityBean(utility_bean);
				utility_bean.setDatabaseBean(database_bean);

				utility_bean.setApplicationURL(request); // アプリケーションのURLの設定

				// 各Beanの共有化
				application.setAttribute("database_bean", database_bean);
				application.setAttribute("html_bean", html_bean);
				application.setAttribute("utility_bean", utility_bean);
			} catch (ClassNotFoundException e) {
			}
		}

		String requested_page_url = request.getRequestURL().toString(); // 要求されたページのURLを格納

		// 要求されたページがログインページ以外のページである場合
		if (!requested_page_url.equals(utility_bean.getLoginPageURL()) && !requested_page_url.equals(utility_bean.getAdminLoginPageURL())) {
			String referer = request.getHeader("referer"); // 参照元ページのURLを格納

			// 参照元ページのURL情報がない、又はシステム外からのアクセスである場合
			if (referer == null || referer.indexOf(utility_bean.getApplicationURL()) == -1) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("access_error")).forward(request, response); // エラーページへの転送
			}
		}

		// 要求されたページが管理ディレクトリに属していない場合
		if (requested_page_url.indexOf(utility_bean.getAdminDirectoryURL()) == -1) {
			// 履修登録が行えないように設定されている場合
			if (!utility_bean.isRegisterable()) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("registration_denied_error")).forward(request, response); // エラーページへの転送
			}

			// メンテナンス中である場合
			if (utility_bean.isMaintained()) {
				application.getRequestDispatcher(utility_bean.getErrorPageURI("maintenance_error")).forward(request, response); // エラーページへの転送
			}
		}

		request.setCharacterEncoding("Shift_JIS"); // 文字コードの設定
		chain.doFilter(request, response); // 要求されたファイルの実行
	}
}
