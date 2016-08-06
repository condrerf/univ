package sendmoremoney;

class SendMoreMoney {
	int send, more, money; // 配列に格納した値を比較できる形にして格納
	int count = 0; // どの配列要素まで格納したかをカウント
	int pCount = 0; // 計算回数をカウント
	int[] array = {0, 0, 0, 0, 0, 0, 0, 0}; // 0から9の値を要素ごとに異なるように格納

	// 解の計算
	void compute(int x) {
		pCount++;

                // 現在のカウントが4かつ格納予定値が0の場合
		if(count == 4 && x == 0) {
			// 0は使用できないため次の値に移す
                        return;
                }

                // 現在の値と今まで格納した要素の値を比較する
		for(int i = 0; i < count; i++) {
                        // 現在の値が今までの要素に存在していた場合
			if(array[i] == x) {
				// 同じ値は使用できないため次の値に移す
				return;
			}
                }

                // 現在の値を要素に格納し、カウントを増やす
		array[count++] = x;

/*                // 配列の格納値を出力(デバッグ用)
		System.out.print("(");
		for(int i = 0; i < count; i++) {
			System.out.print(array[i] + ", ");
		}
		System.out.print(")\n");
*/
                // 最後の要素に値を格納した場合
		if(count == 8) {
                        // 値が一致するかどうか
                        if(isMatched()) {
				// 結果を出力して終了
                 		display();
                                System.exit(0);
                        } else {
				// カウントを戻して次の値に移す
                                count--;
                                return;
                        }
		}

		// 0から9の値を順に引数にして試行する
                for(int i = 0; i < 10; i++) {
        		compute(i);
                }

                // カウントを戻す
                count--;
	}

	// 値が一致するかどうかを計算
	boolean isMatched() {
		// 該当する配列の要素から値を抽出して計算
		send = (array[0] * 1000) + (array[1] * 100) + (array[2] * 10) + array[3];
		more = (array[4] * 1000) + (array[5] * 100) + (array[6] * 10) + array[1];
		money = (array[4] * 10000) + (array[5] * 1000) + (array[2] * 100) + (array[1] * 10) + array[7];

		// 値が一致するかどうか
		if(send + more == money) {
			// 一致したと返す
			return true;
		} else {
			// 一致しなかったと返す
			return false;
		}
	}

	// 計算結果の表示
	void display() {
		System.out.println("\n---計算結果---");
		System.out.println(" send:  " + send);
		System.out.println(" more: +" + more);
		System.out.println("       -----");
		System.out.println("money: " + money);
		System.out.println("(計算回数: " + pCount + "回)");
	}

	// メインメソッド
	public static void main(String[] args) {
		// 1から9の値を引数にして解を計算する(0は覆面算のルールより使わない)
		SendMoreMoney s = new SendMoreMoney();
		System.out.println("<send + more = money>の解を計算しています...");
                for(int i = 1; i < 10; i++) {
        		s.compute(i);
                }

                // 解が計算できなかった場合の出力
                System.out.println("解を計算することはできませんでした。");
	}
}