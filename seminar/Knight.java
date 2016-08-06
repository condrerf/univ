// 前バージョンとの相違点: 1つの経路を見つけても終了せず、経路数を求めるように変更

// 騎士(ナイト)の巡歴
public class Knight {
	int size; // 盤の大きさ
	int count; // 現在の試行成功回数
	int startX, startY; // 試行開始時のx座標とy座標
	int[] xMoveList = {1, 2, 2, 1, -1, -2, -2, -1}; // x座標の8方向移動リスト
	int[] yMoveList = {-2, -1, 1, 2, 2, 1, -1, -2}; // y座標の8方向移動リスト
	int[][] board; // 巡歴順序を格納
	int[][] sCount; // 各座標の巡歴成功回数

	// 盤の初期化
	Knight(int s) {
		// 盤の大きさを格納する
		size = s;

		// 盤外への移動試行を考慮して上下左右に2ずつ多めに設定する
		board = new int[s + 4][s + 4];

		// 盤外を1、それ以外は0に初期化
		for(int i = 0; i < s + 4; i++) {
			for(int j = 0; j < s + 4; j++) {
				board[i][j] = 1;
			}
		}
		for (int i = 2; i < s + 2; i++) {
			for (int j = 2; j < s + 2; j++) {
				board[i][j] = 0;
			}
		}

		// 巡歴成功回数を格納する配列を0に初期化
		sCount = new int[s][s];
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s + 0; j++) {
				sCount[i][j] = 0;
			}
		}
	}

	// 各座標ごとの巡歴前の初期化
	void init(int x, int y) {
		count = 0;
		startX = x;
		startY = y;
	}

	// 巡歴処理
	void tour(int x, int y) {
		// 移動予定座標が盤外、または今までに使用していたら戻る
		if (board[x][y] != 0) {
			return;
		}

		// 現在座標に現在カウントを格納
		board[x][y] = ++count;

		// 全座標を巡歴した場合
		if (count == Math.pow(size, 2)) {
			// 巡歴成功経路数を増やす
			sCount[startX][startY]++;

/*			// 現時点の巡歴成功経路数と盤の巡歴結果を出力(デバッグ用)
			System.out.println("sCount++(" + sCount[startX][startY] + "): ");
			for(int i = 2; i < size + 2; i++) {
				for(int j = 2; j < size + 2; j++) {
					if(board[i][j] < 10) {
						System.out.print("[ " + board[i][j] + "]");
					} else {
						System.out.print("[" + board[i][j] + "]");
					}
				}
				System.out.println("");
			}
*/		} else {
			// 8方向への移動を試行
			for (int i = 0; i < 8; i++) {
				move(x + xMoveList[i], y + yMoveList[i]);
			}
		}

		// 現在座標からどの方向へも移動できなかった場合
		board[x][y] = 0;
		count--;
	}

	// 結果の表示
	void display() {
		int sTotal = 0; // 成功回数の合計を格納

		// 見出しの表示調整
		System.out.print("\n");
		for(int i = 0; i < size - 4; i++) {
			System.out.print("--");
		}
		System.out.print(size + " * " + size + "の巡歴の成功経路数");
		for(int i = 0; i < size - 4; i++) {
			System.out.print("--");
		}
		System.out.print("\n");

		// 結果の表示
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				// 成功回数に応じて表示を調整する
				if(sCount[i][j] < 10) {
					System.out.print("[  " + sCount[i][j] + "]");
				} else if(sCount[i][j] < 100) {
					System.out.print("[ " + sCount[i][j] + "]");
				} else {
					System.out.print("[" + sCount[i][j] + "]");
				}
				sTotal += sCount[i][j];
			}
			System.out.println("");
		}
		System.out.println("(合計: " + sTotal + "回)");
	}

	// メインメソッド
	public static void main(String[] args) {
		int size;

		// 盤の大きさの設定
		if(args.length > 0 && Integer.parseInt(args[0]) > 0) {
			size = Integer.parseInt(args[0]);
		}
		else {
			size = 5;
		}

		// インスタンスの生成
		Knight k = new Knight(size);

		// 巡歴処理
		System.out.print(size + " * " + size + "の巡歴を");
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				k.init(i, j);
				System.out.println("(" + i + ", " + j + ")を基点にして行っています...");
				k.tour(i + 2, j + 2);
			}
		}

		// 結果の表示
		k.display();
	}
}