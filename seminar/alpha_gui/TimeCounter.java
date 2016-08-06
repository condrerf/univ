package alpha_gui;

// ���Ԃ̃J�E���g
public class TimeCounter extends Thread {
	private long interval;
	private float count;
	private boolean canRun = true;

	// �R���X�g���N�^
	public TimeCounter(long interval) {
		// �C���^�[�o����ݒ肷��
		this.interval = interval;

		// ��A�N�e�B�u���̌�쓮��h�����ߗD��x����������
		setPriority(MAX_PRIORITY);
	}

	// ���Ԃ�interval�~���b���ƂɃJ�E���g����
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

	// ���Ԍo�߂�Ԃ�
	public float getCount() {
		return count / (1000 / interval);
	}

	public void stopRun() {
		canRun = false;
		setPriority(MIN_PRIORITY);
	}
}
