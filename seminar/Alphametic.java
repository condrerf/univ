// 前バージョンとの相違点: 全角と半角の違いによる出力結果の違いの調整

import java.io.*;
import java.util.*;

// 時間のカウント
class TimeCounter extends Thread {
	private static long interval;
	private static float count = 0;

	// コンストラクタ
	TimeCounter(long interval) {
		// インターバルを設定する
		this.interval = interval;

		// 非アクティブ時の誤作動を防ぐため優先度を高くする
		setPriority(MAX_PRIORITY);
	}

	// 時間をintervalミリ秒ごとにカウントする
	public void run() {
		try {
			while(true) {
				Thread.sleep(interval);
				count += 1;
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// 時間経過を返す
	static float getCount() {
		return count / (1000 / interval);
	}
}

// 覆面算
class Alphametic {
	int count = 0; // values[]のどの配列要素まで格納したかをカウント
	int computeCount = 0; // 計算回数をカウント
	int charsSum = 0; // 入力された文字列に使われた文字の種類数を格納
	int[] values; // 各文字に対応する値を0～9の内で異なるように格納
	int[] vPositions; // 入力された文字列の各文字に対応する値の格納位置(values[]内)を格納
	int[] solutions; // 各文字列の値を格納
	char sign; // '+', '-', '*', '/'のいずれかを格納
        String[] strs; // 計算式として指定された文字列を格納
	boolean[] isUsed = new boolean[10]; // 0～9の各値が使われているかどうかを判断

	// コンストラクタ
	Alphametic(Vector v_strs, char sign) {
		// 計算記号を格納
		this.sign = sign;

		// 各文字列の値を格納配列を文字列数で宣言
		solutions = new int[v_strs.size()];

		// 計算する値となる文字列を文字列数で宣言し、各文字列を格納する
		this.strs = new String[v_strs.size()];
		v_strs.copyInto(strs);

		// 計算する値となる文字列を結合して格納する
		StringBuffer strs_all = new StringBuffer();
		for(int i = 0; i < strs.length; i++) {
			strs_all.append(strs[i]);
		}

		// 入力された文字列の合計文字数で配列を宣言
		vPositions = new int[strs_all.length()];

		// 文字列に使われている文字の種類数と各文字の値の格納位置を格納
		for(int i = 0; i < strs_all.length(); i++) {
			// i文字目と同じ文字がこれまでに存在していたかどうかチェック
			boolean is_collided = false;
			int position = charsSum;
			for(int j = 0; j < i; j++) {
				// j文字目の文字がi文字目と同じ場合
				if(strs_all.charAt(i) == strs_all.charAt(j)) {
					// 衝突をセットし、衝突位置を格納する
					is_collided = true;
					position = vPositions[j];
				}
			}

			// i番目の文字の値を格納する位置を格納
			vPositions[i] = position;

			// 衝突しなかった場合
			if(!(is_collided)) {
				// 文字の種類数のカウントを増やす
				charsSum++;
			}
		}

		 // 文字の種類数で配列を宣言
		values = new int[charsSum];
	}

	// 解の計算
	void computeSolution(int x) {
		// 計算回数を加算
		computeCount++;

                // 値を格納してカウントを増やし、xが使われたことをセットする
		values[count++] = x;
		isUsed[x] = true;

                // 現在のカウントが最終である場合
		if(count == charsSum) {
                        // 計算式が一致した場合
                        if(isMatched()) {
				// 結果を出力して終了
                 		display();
                                System.exit(0);
                        } else {
				// カウントを戻してxを使用できるようにセットし、次の値に移す
                                count--;
				isUsed[x] = false;
                                return;
                        }
		}

		// 0から9の値を順に引数にして試行する
                for(int i = 0; i < 10; i++) {
			// iの値が使用可能な場合
			if(canUse(i)) {
				// iを引数にして試行する
		        	computeSolution(i);
	                }
		}

                // カウントを戻し、xを使用できるようにセットする
                count--;
		isUsed[x] = false;
	}

	// 計算式が一致するかどうかを返す
	boolean isMatched() {
		int solution = 0; // 計算式の合計を格納

		// 各文字列の値とそこから求められる結果を計算
		int count = 0; // 文字列の比較位置
		for(int i = 0; i < strs.length; i++) {
			// 文字列の値を計算する
			solutions[i] = 0; // 文字列の値を格納
			for(int j = strs[i].length() - 1; j > -1; j--) {
				solutions[i] += values[vPositions[count++]] * Math.pow(10, j);
			}

			// 1番目の文字列の値を計算した場合
			if(i == 0) {
				// 1番目の文字列の値を格納
				solution = solutions[i];
			// 計算した文字列が終端(答え)以外の場合
			} else if(i < (strs.length - 1)) {
				// 指定された計算記号によって処理を変える
				switch (sign) {
					// 加算する場合
					case '+':
						solution += solutions[i];
						break;
					// 減算する場合
					case '-':
						solution -= solutions[i];
						break;
					// 積算する場合
					case '*':
						solution *= solutions[i];
						break;
					// 除算する場合
					case '/':
						solution /= solutions[i];
						break;
				}
			}
		}

		// 計算式の解が解の値となる文字列の値と一致する場合
		if(solution == solutions[strs.length - 1]) {
			// 一致したと返す
			return true;
		} else {
			// 一致しなかったと返す
			return false;
		}
	}

	// 計算結果の表示
	void display() {
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

		// 見出しの出力
		System.out.println("");
		for(int i = 0; i < ((max_char_length + max_num_length) - 8) / 2 + 1; i++) {
			System.out.print("-");
		}
		System.out.print("計算結果");
		for(int i = 0; i < ((max_char_length + max_num_length) - 8) / 2 + 1; i++) {
			System.out.print("-");
		}
		System.out.println("");

		// 計算する値となった文字列とその値を出力
		for(int i = 0; i < strs.length - 1; i++) {
			for(int j = 0; j < max_char_length - strs[i].getBytes().length; j++) {
				System.out.print(" ");
			}
			// 解の要素を除いた内の最終要素の時
			if(i == strs.length - 2) {
				// 記号を付加する
				System.out.print(strs[i] + ":" + sign);
			} else {
				System.out.print(strs[i] + ": ");
			}
			for(int j = 0; j < max_num_length - strs[i].length(); j++) {
				System.out.print(" ");
			}
			System.out.println(solutions[i]);
		}

		// 区切り線の出力
		for(int i = 0; i < max_char_length + 1; i++) {
			System.out.print(" ");
		}
		for(int i = 0; i < max_num_length + 1; i++) {
			System.out.print("-");
		}
		System.out.println("");

		// 解の値となった文字列と値の出力
		for(int i = 0; i < max_char_length - strs[strs.length - 1].getBytes().length; i++) {
			System.out.print(" ");
		}
		System.out.println(strs[strs.length - 1] + ": " + solutions[strs.length - 1]);

		// 計算回数と計算時間の出力
		System.out.println("(計算回数: " + computeCount + "回, 計算時間: " + TimeCounter.getCount() + "秒)");
	}

	// xの値を使用してよいか返す
	boolean canUse(int x) {
		// xの値が既に使用されている場合
		if(isUsed[x]) {
			// 使用不可能と返す
			return false;
		} else {
			// xの値が0の場合
			if(x == 0) {
				int check = 0; // 各文字列の始端文字位置を格納
				for(int j = 0; j < strs.length - 1; j++) {
					// j + 2番目の文字列の始端文字位置を格納する
					check += strs[j].length();

					// check番目の文字に格納する値の位置が現在のカウントと等価である場合
					if(vPositions[check] == count) {
						// 使用不可能と返す
						return false;
					}
				}
			}
		}

		// 使用可能と返す
		return true;
	}

	// メインメソッド
	public static void main(String[] args) {
		char sign = ' '; // '+', '-', '*', '/'のいずれかを格納
		Vector v_strs = new Vector(); // 入力された文字列を格納

		// 文字列の入力
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n*****覆面算の計算*****\n");
		try {
			// 計算する値となる文字列の入力
			System.out.println("<計算する値となる文字列の入力>");
			System.out.println("(次のステップに進む時は\"!\"と入力して下さい)");
			int count = 0;
			while(true) {
				System.out.print((count + 1) + "番目の文字列を入力して下さい:");
				String str = in.readLine();

				// 空の文字が入力された場合
				if(str.equals("")) {
					System.out.println("\tError: 空の文字は受け付けられません。");
				// "!"が入力された場合
				} else if(str.equals("!")) {
					// これまでに入力された文字列が1つ以下の場合
					if(count < 2) {
						System.out.println("\tError: 2つ以上の文字列が入力されていないと計算が出来ません。");
					} else {
						// ループを抜ける
						break;
					}
				// 上記以外の場合
				} else {
					// 入力された文字列を配列に格納する
					v_strs.addElement(str);
					count++;
				}
			}

			// 計算記号を入力
			System.out.println("\n<計算記号の入力>");
			while(sign == ' ') {
				System.out.print("\"+\", \"-\", \"*\", \"/\"のいずれかを入力して下さい:");
				String str = in.readLine();

				// 空の文字が入力された場合
				if(str.equals("")) {
					System.out.println("\tError: 空の文字は受け付けられません。");
				// 入力されたデータが1文字である場合
				} else if(str.length() == 1) {
					// 入力された文字を判断する
					char ch = str.charAt(0); // 入力された文字を格納
					switch (ch) {
						// '+', '-', '*', '/'のいずれかの場合
						case '+':
						case '-':
						case '*':
						case '/':
							// 記号を格納する
							sign = ch;
							break;
						// 上記以外の場合
						default:
							System.out.println("\tError: 入力された文字は受け付けられません。");
					}
				// 入力されたデータが2文字以上である場合
				} else {
					System.out.println("\tError: 1文字の記号を入力してください。");
				}
			}

			// 解の値となる文字列を入力
			System.out.println("\n<解の値となる文字列の入力>");
			while(true) {
				System.out.print("計算式の解となる文字列を入力して下さい:");
				String str = in.readLine();

				// 空の文字が入力された場合
				if(str.equals("")) {
					System.out.println("\tError: 空の文字は受け付けられません。");
				} else {
					// 入力された文字列を配列に格納する
					v_strs.addElement(str);
					break;
				}
			}

			// 入力ストリームを閉じる
			in.close();
		// 例外発生時
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// インスタンスを生成
		Alphametic alpha = new Alphametic(v_strs, sign);

		// 計算の開始を出力
		System.out.println("\n<解の計算>");
		System.out.print("\"");
		for(int i = 0; i < v_strs.size() - 1; i++) {
			System.out.print(v_strs.elementAt(i));
			if(i < v_strs.size() - 2) {
				System.out.print(" " + sign + " ");
			}
		}
		System.out.println(" = " + v_strs.elementAt(v_strs.size() - 1) + "\"の解を計算しています...");

		// タイムカウンタのスタート
		new TimeCounter(10).start();

		// 1から9の値を引数にして解を計算する(0は覆面算のルールより使わない)
		for(int i = 1; i < 10; i++) {
			alpha.computeSolution(i);
		}

                // 解が計算できなかった場合の出力
                System.out.println("解を計算することはできませんでした。");
		System.exit(1);
	}
}