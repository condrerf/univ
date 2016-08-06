package alpha_gui;

import java.io.*;
import java.util.*;

// 覆面算
public class Alphametic {
	int count = 0; // values[]のどの配列要素まで格納したかをカウント
	int computeCount = 0; // 計算回数をカウント
	int charsSum = 0; // 入力された文字列に使われた文字の種類数を格納
	int[] values; // 各文字に対応する値を0～9の内で異なるように格納
	int[] vPositions; // 入力された文字列の各文字に対応する値の格納位置(values[]内)を格納
	int[] solutions; // 各文字列の値を格納
	char sign; // '+', '-', '*', '/'のいずれかを格納
        String[] strs; // 計算式として指定された文字列を格納
	boolean isUsed[] = new boolean[10]; // 0～9の各値が使われているかどうかを判断
	boolean isEnd = false; // 終了した(解が求められた)かどうか

	// コンストラクタ
	public Alphametic(Vector v_strs, char sign) {
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
	public void computeSolution(int x) {
		// 計算回数を加算
		computeCount++;

                // 値を格納してカウントを増やし、xが使われたことをセットする
		values[count++] = x;
		isUsed[x] = true;

                // 現在のカウントが最終である場合
		if(count == charsSum) {
                        // 計算式が一致した場合
                        if(isMatched()) {
				// 計算が終了したことをセットする
				isEnd = true;
				return;
                        } else {
				// カウントを戻してxを使用できるようにセットし、次の値に移す
                                count--;
				isUsed[x] = false;
                                return;
                        }
		}

		// 0から9の値を順に引数にして試行する
                for(int i = 0; i < 10; i++) {
			// 解が求められた場合
			if(isEnd) {
				// 前に戻す
				return;
			// iの値が使用可能な場合
			} else if(canUse(i)) {
				// iを引数にして試行する
		        	computeSolution(i);
	                }
		}

                // カウントを戻し、xを使用できるようにセットする
                count--;
		isUsed[x] = false;
	}

	// 計算式が一致するかどうかを返す
	private boolean isMatched() {
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

	// xの値を使用してよいか返す
	private boolean canUse(int x) {
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

	// 解が求められたかどうかを返す
	public boolean isEnd() {
		return isEnd;
	}

	// 答えの値を返す
	public int[] getSolutions() {
		return solutions;
	}

	// 解が求められたかどうかを返す
	public int getComputeCount() {
		return computeCount;
	}
}