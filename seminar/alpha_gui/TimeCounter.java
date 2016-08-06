package alpha_gui;

// 時間のカウント
public class TimeCounter extends Thread {
	private long interval;
	private float count;
	private boolean canRun = true;

	// コンストラクタ
	public TimeCounter(long interval) {
		// インターバルを設定する
		this.interval = interval;

		// 非アクティブ時の誤作動を防ぐため優先度を高くする
		setPriority(MAX_PRIORITY);
	}

	// 時間をintervalミリ秒ごとにカウントする
	public void run() {
		try {
			while(canRun) {
				Thread.sleep(interval);
				count += 1;
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// 時間経過を返す
	public float getCount() {
		return count / (1000 / interval);
	}

	public void stopRun() {
		canRun = false;
		setPriority(MIN_PRIORITY);
	}
}
