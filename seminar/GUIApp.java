package guiapp;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GUIApp extends Frame implements ActionListener {
	// インスタンス変数の宣言
	MenuBar mb;
	Menu menu1;
	MenuItem menu1a, menu1b, menu1c;
	Panel pn1, pn2, pn3;
        Label la;
        TextField tf;
	TextArea ta;
	Button lButton, sButton;

	// コンストラクタ
	GUIApp(String title) {
		// タイトルの設定
		super(title);

		// メニューバーの設定
		mb = new MenuBar();
		setMenuBar(mb);

		menu1 = new Menu("File");
		mb.add(menu1);
		menu1a = new MenuItem("Load");
		menu1a.addActionListener(this);
		menu1.add(menu1a);
		menu1b = new MenuItem("Save");
		menu1b.addActionListener(this);
		menu1.add(menu1b);
		menu1c = new MenuItem("Exit");
		menu1c.addActionListener(this);
		menu1.add(menu1c);

		// ステータス表示部の設定
		pn1 = new Panel();
                la = new Label("Status: ");
		pn1.add(la);
		tf = new TextField("Start", 30);
		pn1.add(tf);
       		add(pn1, "North");

		// ファイル内容表示部の設定
		pn2 = new Panel();
		ta = new TextArea("TextArea\n", 16, 60);
		pn2.add(ta);
                add(pn2, "Center");

		// ボタン表示部の設定
		pn3 = new Panel();
		lButton = new Button("Load");
                lButton.addActionListener(this);
		pn3.add(lButton);
                sButton = new Button("Save");
                sButton.addActionListener(this);
                pn3.add(sButton);
                add(pn3, "South");

		// アプリケーションの終了要求取得時の終了処理
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
                        	dispose();
				System.exit(0);
			}
		});
	}

	// アクションイベントの処理
	public void actionPerformed(ActionEvent ae) {
		// Loadが選択された場合
                if(ae.getSource() == lButton || ae.getSource() == menu1a) {
                        FileDialog fd = new FileDialog(this, "Load File", FileDialog.LOAD);
                        fd.show();

			if(fd.getFile() != null) {
				// ファイル読み込みが要求された場合
	                        try {
					// ファイルの読み込み
	                                String s;
	                                BufferedReader in = new BufferedReader(new FileReader(fd.getDirectory() + fd.getFile()));
	                                ta.setText("");
        	                        while((s = in.readLine()) != null) {
	                                        ta.append(s + "\n");
	                                }
	                                in.close();

					// 保存完了時の表示
	                                setTitle("GUI Application - " + fd.getFile() + ": Loaded");
	                                tf.setText(fd.getFile() + ": Loaded");
	                        } catch(Exception e) {
					// 例外発生時の表示
	                                tf.setText(e.toString());
	                        }
			} else {
				// 読み込み中断時の表示
                                setTitle("GUI Application - Load Canceled");
				tf.setText("Load Canceled");
			}
		// Saveが選択された場合
                } else if(ae.getSource() == sButton || ae.getSource() == menu1b) {
                        FileDialog fd = new FileDialog(this, "Save File", FileDialog.SAVE);
                        fd.show();

			if(fd.getFile() != null) {
				// ファイル保存が要求された場合
	                        try {
					// ファイルの書き込み
	                                BufferedWriter out = new BufferedWriter(new FileWriter(fd.getDirectory() + fd.getFile()));
	                                out.write(ta.getText());
	                                out.close();

					// 保存完了時の表示
	                                setTitle("GUI Application - " + fd.getFile() + ": Saved");
	                                tf.setText(fd.getFile() + ": Saved");
	                        } catch(Exception e) {
					// 例外発生時の表示
	                                tf.setText(e.toString());
	                        }
			} else {
				// ファイル保存中断時の表示
                                setTitle("GUI Application - Save Canceled");
				tf.setText("Save Canceled");
			}
		// Exitが選択された場合
                } else if(ae.getSource() == menu1c) {
			// ウィンドウを閉じる
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
	}

        public void paint(Graphics g) {
        	pack();
        }

	// メインメソッド
	public static void main(String[] args) {
		// フレームのタイトルとサイズを指定して表示
		GUIApp f = new GUIApp("GUI Application");
                f.pack();
		f.show();
	}
}
