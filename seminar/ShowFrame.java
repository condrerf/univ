import java.awt.*;
import java.awt.event.*;

public class ShowFrame extends Frame {
	// コンストラクタ
	ShowFrame(String title) {
		// タイトルの設定
		super(title);

		// アプリケーションの終了要求取得時の終了処理
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	// メインメソッド
	public static void main(String[] args) {
		// フレームのタイトルとサイズを指定して表示
		ShowFrame f = new ShowFrame("Frame");
		f.setSize(480, 360);
		f.show();
	}
}
