// �O�o�[�W�����Ƃ̑���_: �S�p�Ɣ��p�̈Ⴂ�ɂ��o�͌��ʂ̈Ⴂ�̒���

import java.io.*;
import java.util.*;

// ���Ԃ̃J�E���g
class TimeCounter extends Thread {
	private static long interval;
	private static float count = 0;

	// �R���X�g���N�^
	TimeCounter(long interval) {
		// �C���^�[�o����ݒ肷��
		this.interval = interval;

		// ��A�N�e�B�u���̌�쓮��h�����ߗD��x����������
		setPriority(MAX_PRIORITY);
	}

	// ���Ԃ�interval�~���b���ƂɃJ�E���g����
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

	// ���Ԍo�߂�Ԃ�
	static float getCount() {
		return count / (1000 / interval);
	}
}

// ���ʎZ
class Alphametic {
	int count = 0; // values[]�̂ǂ̔z��v�f�܂Ŋi�[���������J�E���g
	int computeCount = 0; // �v�Z�񐔂��J�E���g
	int charsSum = 0; // ���͂��ꂽ������Ɏg��ꂽ�����̎�ސ����i�[
	int[] values; // �e�����ɑΉ�����l��0�`9�̓��ňقȂ�悤�Ɋi�[
	int[] vPositions; // ���͂��ꂽ������̊e�����ɑΉ�����l�̊i�[�ʒu(values[]��)���i�[
	int[] solutions; // �e������̒l���i�[
	char sign; // '+', '-', '*', '/'�̂����ꂩ���i�[
        String[] strs; // �v�Z���Ƃ��Ďw�肳�ꂽ��������i�[
	boolean[] isUsed = new boolean[10]; // 0�`9�̊e�l���g���Ă��邩�ǂ����𔻒f

	// �R���X�g���N�^
	Alphametic(Vector v_strs, char sign) {
		// �v�Z�L�����i�[
		this.sign = sign;

		// �e������̒l���i�[�z��𕶎��񐔂Ő錾
		solutions = new int[v_strs.size()];

		// �v�Z����l�ƂȂ镶����𕶎��񐔂Ő錾���A�e��������i�[����
		this.strs = new String[v_strs.size()];
		v_strs.copyInto(strs);

		// �v�Z����l�ƂȂ镶������������Ċi�[����
		StringBuffer strs_all = new StringBuffer();
		for(int i = 0; i < strs.length; i++) {
			strs_all.append(strs[i]);
		}

		// ���͂��ꂽ������̍��v�������Ŕz���錾
		vPositions = new int[strs_all.length()];

		// ������Ɏg���Ă��镶���̎�ސ��Ɗe�����̒l�̊i�[�ʒu���i�[
		for(int i = 0; i < strs_all.length(); i++) {
			// i�����ڂƓ�������������܂łɑ��݂��Ă������ǂ����`�F�b�N
			boolean is_collided = false;
			int position = charsSum;
			for(int j = 0; j < i; j++) {
				// j�����ڂ̕�����i�����ڂƓ����ꍇ
				if(strs_all.charAt(i) == strs_all.charAt(j)) {
					// �Փ˂��Z�b�g���A�Փˈʒu���i�[����
					is_collided = true;
					position = vPositions[j];
				}
			}

			// i�Ԗڂ̕����̒l���i�[����ʒu���i�[
			vPositions[i] = position;

			// �Փ˂��Ȃ������ꍇ
			if(!(is_collided)) {
				// �����̎�ސ��̃J�E���g�𑝂₷
				charsSum++;
			}
		}

		 // �����̎�ސ��Ŕz���錾
		values = new int[charsSum];
	}

	// ���̌v�Z
	void computeSolution(int x) {
		// �v�Z�񐔂����Z
		computeCount++;

                // �l���i�[���ăJ�E���g�𑝂₵�Ax���g��ꂽ���Ƃ��Z�b�g����
		values[count++] = x;
		isUsed[x] = true;

                // ���݂̃J�E���g���ŏI�ł���ꍇ
		if(count == charsSum) {
                        // �v�Z������v�����ꍇ
                        if(isMatched()) {
				// ���ʂ��o�͂��ďI��
                 		display();
                                System.exit(0);
                        } else {
				// �J�E���g��߂���x���g�p�ł���悤�ɃZ�b�g���A���̒l�Ɉڂ�
                                count--;
				isUsed[x] = false;
                                return;
                        }
		}

		// 0����9�̒l�����Ɉ����ɂ��Ď��s����
                for(int i = 0; i < 10; i++) {
			// i�̒l���g�p�\�ȏꍇ
			if(canUse(i)) {
				// i�������ɂ��Ď��s����
		        	computeSolution(i);
	                }
		}

                // �J�E���g��߂��Ax���g�p�ł���悤�ɃZ�b�g����
                count--;
		isUsed[x] = false;
	}

	// �v�Z������v���邩�ǂ�����Ԃ�
	boolean isMatched() {
		int solution = 0; // �v�Z���̍��v���i�[

		// �e������̒l�Ƃ������狁�߂��錋�ʂ��v�Z
		int count = 0; // ������̔�r�ʒu
		for(int i = 0; i < strs.length; i++) {
			// ������̒l���v�Z����
			solutions[i] = 0; // ������̒l���i�[
			for(int j = strs[i].length() - 1; j > -1; j--) {
				solutions[i] += values[vPositions[count++]] * Math.pow(10, j);
			}

			// 1�Ԗڂ̕�����̒l���v�Z�����ꍇ
			if(i == 0) {
				// 1�Ԗڂ̕�����̒l���i�[
				solution = solutions[i];
			// �v�Z���������񂪏I�[(����)�ȊO�̏ꍇ
			} else if(i < (strs.length - 1)) {
				// �w�肳�ꂽ�v�Z�L���ɂ���ď�����ς���
				switch (sign) {
					// ���Z����ꍇ
					case '+':
						solution += solutions[i];
						break;
					// ���Z����ꍇ
					case '-':
						solution -= solutions[i];
						break;
					// �ώZ����ꍇ
					case '*':
						solution *= solutions[i];
						break;
					// ���Z����ꍇ
					case '/':
						solution /= solutions[i];
						break;
				}
			}
		}

		// �v�Z���̉������̒l�ƂȂ镶����̒l�ƈ�v����ꍇ
		if(solution == solutions[strs.length - 1]) {
			// ��v�����ƕԂ�
			return true;
		} else {
			// ��v���Ȃ������ƕԂ�
			return false;
		}
	}

	// �v�Z���ʂ̕\��
	void display() {
		// �ł�����������̕������ƍł��������l�̌��������߂�
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

		// ���o���̏o��
		System.out.println("");
		for(int i = 0; i < ((max_char_length + max_num_length) - 8) / 2 + 1; i++) {
			System.out.print("-");
		}
		System.out.print("�v�Z����");
		for(int i = 0; i < ((max_char_length + max_num_length) - 8) / 2 + 1; i++) {
			System.out.print("-");
		}
		System.out.println("");

		// �v�Z����l�ƂȂ���������Ƃ��̒l���o��
		for(int i = 0; i < strs.length - 1; i++) {
			for(int j = 0; j < max_char_length - strs[i].getBytes().length; j++) {
				System.out.print(" ");
			}
			// ���̗v�f�����������̍ŏI�v�f�̎�
			if(i == strs.length - 2) {
				// �L����t������
				System.out.print(strs[i] + ":" + sign);
			} else {
				System.out.print(strs[i] + ": ");
			}
			for(int j = 0; j < max_num_length - strs[i].length(); j++) {
				System.out.print(" ");
			}
			System.out.println(solutions[i]);
		}

		// ��؂���̏o��
		for(int i = 0; i < max_char_length + 1; i++) {
			System.out.print(" ");
		}
		for(int i = 0; i < max_num_length + 1; i++) {
			System.out.print("-");
		}
		System.out.println("");

		// ���̒l�ƂȂ���������ƒl�̏o��
		for(int i = 0; i < max_char_length - strs[strs.length - 1].getBytes().length; i++) {
			System.out.print(" ");
		}
		System.out.println(strs[strs.length - 1] + ": " + solutions[strs.length - 1]);

		// �v�Z�񐔂ƌv�Z���Ԃ̏o��
		System.out.println("(�v�Z��: " + computeCount + "��, �v�Z����: " + TimeCounter.getCount() + "�b)");
	}

	// x�̒l���g�p���Ă悢���Ԃ�
	boolean canUse(int x) {
		// x�̒l�����Ɏg�p����Ă���ꍇ
		if(isUsed[x]) {
			// �g�p�s�\�ƕԂ�
			return false;
		} else {
			// x�̒l��0�̏ꍇ
			if(x == 0) {
				int check = 0; // �e������̎n�[�����ʒu���i�[
				for(int j = 0; j < strs.length - 1; j++) {
					// j + 2�Ԗڂ̕�����̎n�[�����ʒu���i�[����
					check += strs[j].length();

					// check�Ԗڂ̕����Ɋi�[����l�̈ʒu�����݂̃J�E���g�Ɠ����ł���ꍇ
					if(vPositions[check] == count) {
						// �g�p�s�\�ƕԂ�
						return false;
					}
				}
			}
		}

		// �g�p�\�ƕԂ�
		return true;
	}

	// ���C�����\�b�h
	public static void main(String[] args) {
		char sign = ' '; // '+', '-', '*', '/'�̂����ꂩ���i�[
		Vector v_strs = new Vector(); // ���͂��ꂽ��������i�[

		// ������̓���
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n*****���ʎZ�̌v�Z*****\n");
		try {
			// �v�Z����l�ƂȂ镶����̓���
			System.out.println("<�v�Z����l�ƂȂ镶����̓���>");
			System.out.println("(���̃X�e�b�v�ɐi�ގ���\"!\"�Ɠ��͂��ĉ�����)");
			int count = 0;
			while(true) {
				System.out.print((count + 1) + "�Ԗڂ̕��������͂��ĉ�����:");
				String str = in.readLine();

				// ��̕��������͂��ꂽ�ꍇ
				if(str.equals("")) {
					System.out.println("\tError: ��̕����͎󂯕t�����܂���B");
				// "!"�����͂��ꂽ�ꍇ
				} else if(str.equals("!")) {
					// ����܂łɓ��͂��ꂽ������1�ȉ��̏ꍇ
					if(count < 2) {
						System.out.println("\tError: 2�ȏ�̕����񂪓��͂���Ă��Ȃ��ƌv�Z���o���܂���B");
					} else {
						// ���[�v�𔲂���
						break;
					}
				// ��L�ȊO�̏ꍇ
				} else {
					// ���͂��ꂽ�������z��Ɋi�[����
					v_strs.addElement(str);
					count++;
				}
			}

			// �v�Z�L�������
			System.out.println("\n<�v�Z�L���̓���>");
			while(sign == ' ') {
				System.out.print("\"+\", \"-\", \"*\", \"/\"�̂����ꂩ����͂��ĉ�����:");
				String str = in.readLine();

				// ��̕��������͂��ꂽ�ꍇ
				if(str.equals("")) {
					System.out.println("\tError: ��̕����͎󂯕t�����܂���B");
				// ���͂��ꂽ�f�[�^��1�����ł���ꍇ
				} else if(str.length() == 1) {
					// ���͂��ꂽ�����𔻒f����
					char ch = str.charAt(0); // ���͂��ꂽ�������i�[
					switch (ch) {
						// '+', '-', '*', '/'�̂����ꂩ�̏ꍇ
						case '+':
						case '-':
						case '*':
						case '/':
							// �L�����i�[����
							sign = ch;
							break;
						// ��L�ȊO�̏ꍇ
						default:
							System.out.println("\tError: ���͂��ꂽ�����͎󂯕t�����܂���B");
					}
				// ���͂��ꂽ�f�[�^��2�����ȏ�ł���ꍇ
				} else {
					System.out.println("\tError: 1�����̋L������͂��Ă��������B");
				}
			}

			// ���̒l�ƂȂ镶��������
			System.out.println("\n<���̒l�ƂȂ镶����̓���>");
			while(true) {
				System.out.print("�v�Z���̉��ƂȂ镶�������͂��ĉ�����:");
				String str = in.readLine();

				// ��̕��������͂��ꂽ�ꍇ
				if(str.equals("")) {
					System.out.println("\tError: ��̕����͎󂯕t�����܂���B");
				} else {
					// ���͂��ꂽ�������z��Ɋi�[����
					v_strs.addElement(str);
					break;
				}
			}

			// ���̓X�g���[�������
			in.close();
		// ��O������
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// �C���X�^���X�𐶐�
		Alphametic alpha = new Alphametic(v_strs, sign);

		// �v�Z�̊J�n���o��
		System.out.println("\n<���̌v�Z>");
		System.out.print("\"");
		for(int i = 0; i < v_strs.size() - 1; i++) {
			System.out.print(v_strs.elementAt(i));
			if(i < v_strs.size() - 2) {
				System.out.print(" " + sign + " ");
			}
		}
		System.out.println(" = " + v_strs.elementAt(v_strs.size() - 1) + "\"�̉����v�Z���Ă��܂�...");

		// �^�C���J�E���^�̃X�^�[�g
		new TimeCounter(10).start();

		// 1����9�̒l�������ɂ��ĉ����v�Z����(0�͕��ʎZ�̃��[�����g��Ȃ�)
		for(int i = 1; i < 10; i++) {
			alpha.computeSolution(i);
		}

                // �����v�Z�ł��Ȃ������ꍇ�̏o��
                System.out.println("�����v�Z���邱�Ƃ͂ł��܂���ł����B");
		System.exit(1);
	}
}