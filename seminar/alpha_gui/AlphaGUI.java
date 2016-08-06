package alpha_gui;
import alpha_gui.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

// GUIによる覆面算の計算
public class AlphaGUI extends Frame implements ActionListener, ItemListener {
	Label lStatus, lExpression; // 状態表示欄と計算式表示欄のオブジェクトを格納
	TextField computeField, solutionField; // 各入力欄のオブジェクトを格納
	Button addtion, setting, start, initialize; // 各ボタンのオブジェクトを格納
	Panel pResult2, pResultOutput; // 計算結果の表示欄を構成するパネルのオブジェクトを格納
	char sign; // 計算記号を格納
	String solutionStr; // 解となる文字列を格納
	Vector vStrs = new Vector(); // 入力された文字列を格納
	TextArea outputArea; // 解の出力先
	int solutions[]; // 各文字列に相当する値を格納
	String strs[];

	// コンストラクタ
	AlphaGUI(String title) {
		// タイトルの設定
		super(title);

		// 上側の表示部分を設定する
		setLayout(new BorderLayout());
		Panel p_top = new Panel();
		p_top.setLayout(new GridLayout(2, 1));
		p_top.setBackground(new Color(250, 250, 250));
		add(p_top, "North");

		// 状態の表示欄表示
		Panel p_status = new Panel();
		p_status.setLayout(new BorderLayout());
		lStatus = new Label("");
		p_status.add(lStatus, "West");
		p_top.add(p_status);

		// 計算式の表示欄表示
		Panel p_expression = new Panel();
		p_expression.setLayout(new BorderLayout());
		lExpression = new Label("");
		p_expression.add(lExpression, "West");
		p_top.add(p_expression);

		// 下側の表示部分を設定する
		Panel p_bottom = new Panel();
		p_bottom.setLayout(new GridLayout(1, 2));
		add(p_bottom, "Center");

		// 下側の左表示部分を設定する
		Panel p_left = new Panel();
		p_left.setLayout(new GridLayout(6, 1));
		p_left.setBackground(new Color(230, 230, 230));
		p_bottom.add(p_left);

		// 計算する値となる文字列の入力欄表示
		Panel p_compute = new Panel();
		p_compute.setLayout(new BorderLayout());
		Label l_compute = new Label("<計算する値となる文字列>");
		p_compute.add(l_compute, "West");
		p_left.add(p_compute);

		Panel p_compute2 = new Panel();
		computeField = new TextField("", 10);
                computeField.addActionListener(this);
		p_compute2.add(computeField);
		addtion = new Button("追加");
                addtion.addActionListener(this);
		p_compute2.add(addtion);
		p_left.add(p_compute2);

		// 計算記号("+", "-", "*", "/")の選択欄表示
		Panel p_sign = new Panel();
		p_sign.setLayout(new BorderLayout());
		Label l_sign = new Label("<計算記号>");
		p_sign.add(l_sign, "West");
		p_left.add(p_sign);

		Panel p_sign2 = new Panel();
		CheckboxGroup cbg = new CheckboxGroup();
		Checkbox add = new Checkbox("+", cbg, true);
		add.addItemListener(this);
		p_sign2.add(add);
		Checkbox subtract = new Checkbox("-", cbg, false);
		subtract.addItemListener(this);
		p_sign2.add(subtract);
		Checkbox multiply = new Checkbox("*", cbg, false);
		multiply.addItemListener(this);
		p_sign2.add(multiply);
		Checkbox divide = new Checkbox("/", cbg, false);
		divide.addItemListener(this);
		p_sign2.add(divide);
		p_left.add(p_sign2);

		// 計算する値となる文字列の入力欄表示
		Panel p_solution = new Panel();
		p_solution.setLayout(new BorderLayout());
		Label l_solution = new Label("<解の値となる文字列>");
		p_solution.add(l_solution, "West");
		p_left.add(p_solution);

		Panel p_solution2 = new Panel();
		solutionField = new TextField("", 10);
                solutionField.addActionListener(this);
		p_solution2.add(solutionField);
		setting = new Button("セット");
		setting.addActionListener(this);
		p_solution2.add(setting);
		p_left.add(p_solution2);

		// 計算開始ボタンと初期化ボタンの表示
		Panel p_setting = new Panel();
		start = new Button("計算開始");
		start.addActionListener(this);
		p_setting.add(start);
		initialize = new Button("初期化");
		initialize.addActionListener(this);
		p_setting.add(initialize);
		p_left.add(p_setting);

		// 下側の右表示部分を設定する
		Panel p_right = new Panel();
		p_right.setLayout(new BorderLayout());
		p_bottom.add(p_right);

		// 計算結果の見出しの表示
		Panel p_result = new Panel();
		p_result.setLayout(new BorderLayout());
		Label l_result = new Label("<計算結果>");
		p_result.add(l_result, "West");
		p_right.add(p_result, "North");

		pResult2 = new Panel();
		pResult2.setLayout(new BorderLayout());
		outputArea = new TextArea("", 10, 10);
		pResult2.add(outputArea, "Center");
/*		pResultOutput = new Panel();
		pResultOutput.setLayout(new GridLayout(1, 2));
		pResult2.add(pResultOutput, "Center");
*/		p_right.add(pResult2, "Center");

		// アプリケーションの終了要求取得時の終了処理
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
				System.exit(0);
			}
		});

		// 要素の初期化
		init();
	}

	// 要素の初期化
	void init() {
		sign = '+';
		vStrs.removeAllElements();
		solutionStr = "";
		lStatus.setText("状態:");
		lExpression.setText("計算式:");
		outputArea.setText("");

/*		// 計算結果表示欄の初期化
		pResultOutput = new Panel();
		pResultOutput.setLayout(new GridLayout(1, 2));
*/	}

	// アクションイベントの処理
	public void actionPerformed(ActionEvent ae) {
		lStatus.setText("状態:");
		// 計算開始ボタンが押された場合
		if(ae.getSource() == start) {
			// 計算する値となる文字列が1文字以下か解となる文字列が入力されていない場合
			if(vStrs.size() < 2 || solutionStr.equals("")) {
				// 計算を開始しない
				lStatus.setText("状態: 計算が行える状態になっていません。");
				repaint();
				return;
			}

			// 解となる文字列をベクトルに追加する
			vStrs.addElement(solutionStr);

/*			// 解の表示欄を表示する
			Panel p_output_left = new Panel();
			p_output_left.setLayout(new GridLayout(vStrs.size() + 1, 1));
			pResultOutput.add(p_output_left);

			Panel[] p_output_char = new Panel[vStrs.size()];
			Label[] l_output_char = new Label[vStrs.size()];
			for(int i = 0; i < vStrs.size(); i++) {
				p_output_char[i] = new Panel();
				p_output_char[i].setLayout(new BorderLayout());
				l_output_char[i] = new Label((String)vStrs.elementAt(i) + ": ");
				p_output_char[i].add(l_output_char[i], "East");
				p_output_left.add(p_output_char[i]);
			}
			l_output_char[vStrs.size() - 1].setText((String)vStrs.elementAt(vStrs.size() - 1) + ": ");

			Panel p_output_right = new Panel();
			p_output_right.setLayout(new GridLayout(vStrs.size() + 1, 1));
			pResultOutput.add(p_output_right);

			Panel[] p_output_num = new Panel[vStrs.size()];
			Label[] l_output_num = new Label[vStrs.size()];
			for(int i = 0; i < vStrs.size(); i++) {
				p_output_num[i] = new Panel();
				p_output_num[i].setLayout(new BorderLayout());
				l_output_num[i] = new Label();
				p_output_num[i].add(l_output_num[i], "East");
				p_output_right.add(p_output_num[i]);
			}
*/
			// 覆面算を計算するインスタンスの生成
			Alphametic alpha = new Alphametic(vStrs, sign);

			// タイムカウンタのスタート
			TimeCounter t = new TimeCounter(10);
			t.start();

			// 1から9の値を引数にして解を計算する(0は覆面算のルールより使わない)
			lStatus.setText("状態: 解を求めています...");
			outputArea.setText("");
			repaint();
			for(int i = 1; i < 10; i++) {
				// 計算を試行する
				alpha.computeSolution(i);

				// 解が求められた場合
				if(alpha.isEnd()) {
					// 解を出力する
					lStatus.setText("状態: 計算完了 (計算回数: " + alpha.getComputeCount() + "回, 計算時間: " + t.getCount() + "秒)");

					solutions = alpha.getSolutions();
					display();
/*					for(int j = 0; j < vStrs.size() - 1; j++) {
						l_output_num[j].setText(solutions[j]);
					}
					l_output_num[vStrs.size()].setText(solutions[vStrs.size()];
*/
					t.stopRun();
					vStrs.removeElementAt(vStrs.size() - 1);
					repaint();
					return;
				}
			}

			// 解が見つからなかったことを通知する
			lStatus.setText("状態: 解は見つかりませんでした。");
			t.stopRun();
			vStrs.removeElementAt(vStrs.size() - 1);
			repaint();
			return;
		// 初期化ボタンが押された場合
		} else if(ae.getSource() == initialize) {
			// 要素の初期化
			init();
			lStatus.setText("状態: 初期化されました。");
			repaint();
			return;
		// 計算する値となる文字列入力欄でエンターキーが押されたか追加ボタンが押された場合
		} else if( ae.getSource() == computeField || ae.getSource() == addtion) {
			// 計算する値となる文字列が空白だった場合
			if(computeField.getText().equals("")) {
				lStatus.setText("状態: 空の文字は受け付けられません。");
			} else {
				// 要素を追加する
				vStrs.addElement(computeField.getText());
				computeField.setText("");
			}
		// 解の文字列入力欄でエンターキーが押されたかセットボタンが押された場合
		} else if(ae.getSource() == solutionField || ae.getSource() == setting) {
			// 解となる文字列が空白だった場合
			if(solutionField.getText().equals("")) {
				lStatus.setText("状態: 空の文字は受け付けられません。");
			} else {
				// 解となる文字列を格納する
				solutionStr = solutionField.getText();
				solutionField.setText("");
			}
		}

		// 計算式の表示を更新する
		updateExpression();
		repaint();
	}

	// 選択された計算記号を格納する
	public void itemStateChanged(ItemEvent ie) {
		Checkbox cb = (Checkbox)ie.getItemSelectable();
		sign = cb.getLabel().charAt(0);

		// 計算式の表示を更新する
		updateExpression();
	}

	// 計算式の表示を更新する
	void updateExpression() {
		lExpression.setText("計算式: ");

		// 計算する値となる文字列が1つ以上格納されている場合
		if(vStrs.size() >= 1) {

			// 最初の文字列要素を表示する
			lExpression.setText(lExpression.getText() + (String)vStrs.elementAt(0));

			// 2つ目以降の文字列要素を表示する
			for(int i = 1; i < vStrs.size(); i++) {
				lExpression.setText(lExpression.getText() + sign + vStrs.elementAt(i));
			}

			// 解となる文字列が格納されている場合
			if(!(solutionStr.equals(""))) {
				// 解となる文字列を表示する
				lExpression.setText(lExpression.getText() + " = " + solutionStr);
			}
		}
	}

	public void paint(Graphics g) {
		pack();
	}

	void display() {
		strs = new String[vStrs.size()];
		vStrs.copyInto(strs);

		// 最も長い文字列の文字数と最も長い数値の桁数を求める
		int max_char_length = strs[0].getBytes().length;
		int max_num_length = strs[0].length();
		for(int i = 1; i < strs.length; i++) {
			if(max_char_length < strs[i].getBytes().length) {
				max_char_length = strs[i].getBytes().length;
			}

			if(max_num_length < strs[i].length()) {
				max_num_length = strs[i].length();
			}
		}

		// 計算する値となった文字列とその値を出力
		for(int i = 0; i < strs.length - 1; i++) {
			for(int j = 0; j < max_char_length - strs[i].getBytes().length; j++) {
				outputArea.append(" ");
			}
			// 解の要素を除いた内の最終要素の時
			if(i == strs.length - 2) {
				// 記号を付加する
				outputArea.append(strs[i] + ":" + sign);
			} else {
				outputArea.append(strs[i] + ": ");
			}
			for(int j = 0; j < max_num_length - strs[i].length(); j++) {
				outputArea.append(" ");
			}
			outputArea.append(solutions[i] + "\n");
		}

		// 区切り線の出力
		for(int i = 0; i < max_char_length + 1; i++) {
			outputArea.append(" ");
		}
		for(int i = 0; i < max_num_length + 1; i++) {
			outputArea.append("-");
		}
		outputArea.append("\n");

		// 解の値となった文字列と値の出力
		for(int i = 0; i < max_char_length - strs[strs.length - 1].getBytes().length; i++) {
			outputArea.append(" ");
		}
		outputArea.append(strs[strs.length - 1] + ": " + solutions[strs.length - 1] + "\n");
	}

	// メインメソッド
	public static void main(String[] args) {
		// フレームのタイトルとサイズを指定して表示
		AlphaGUI alpha_gui = new AlphaGUI("覆面算の計算");
//		alpha_gui.setSize(480, 360);
		alpha_gui.pack();
		alpha_gui.show();
	}
}