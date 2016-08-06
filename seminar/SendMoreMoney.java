package sendmoremoney;

class SendMoreMoney {
	int send, more, money; // �z��Ɋi�[�����l���r�ł���`�ɂ��Ċi�[
	int count = 0; // �ǂ̔z��v�f�܂Ŋi�[���������J�E���g
	int pCount = 0; // �v�Z�񐔂��J�E���g
	int[] array = {0, 0, 0, 0, 0, 0, 0, 0}; // 0����9�̒l��v�f���ƂɈقȂ�悤�Ɋi�[

	// ���̌v�Z
	void compute(int x) {
		pCount++;

                // ���݂̃J�E���g��4���i�[�\��l��0�̏ꍇ
		if(count == 4 && x == 0) {
			// 0�͎g�p�ł��Ȃ����ߎ��̒l�Ɉڂ�
                        return;
                }

                // ���݂̒l�ƍ��܂Ŋi�[�����v�f�̒l���r����
		for(int i = 0; i < count; i++) {
                        // ���݂̒l�����܂ł̗v�f�ɑ��݂��Ă����ꍇ
			if(array[i] == x) {
				// �����l�͎g�p�ł��Ȃ����ߎ��̒l�Ɉڂ�
				return;
			}
                }

                // ���݂̒l��v�f�Ɋi�[���A�J�E���g�𑝂₷
		array[count++] = x;

/*                // �z��̊i�[�l���o��(�f�o�b�O�p)
		System.out.print("(");
		for(int i = 0; i < count; i++) {
			System.out.print(array[i] + ", ");
		}
		System.out.print(")\n");
*/
                // �Ō�̗v�f�ɒl���i�[�����ꍇ
		if(count == 8) {
                        // �l����v���邩�ǂ���
                        if(isMatched()) {
				// ���ʂ��o�͂��ďI��
                 		display();
                                System.exit(0);
                        } else {
				// �J�E���g��߂��Ď��̒l�Ɉڂ�
                                count--;
                                return;
                        }
		}

		// 0����9�̒l�����Ɉ����ɂ��Ď��s����
                for(int i = 0; i < 10; i++) {
        		compute(i);
                }

                // �J�E���g��߂�
                count--;
	}

	// �l����v���邩�ǂ������v�Z
	boolean isMatched() {
		// �Y������z��̗v�f����l�𒊏o���Čv�Z
		send = (array[0] * 1000) + (array[1] * 100) + (array[2] * 10) + array[3];
		more = (array[4] * 1000) + (array[5] * 100) + (array[6] * 10) + array[1];
		money = (array[4] * 10000) + (array[5] * 1000) + (array[2] * 100) + (array[1] * 10) + array[7];

		// �l����v���邩�ǂ���
		if(send + more == money) {
			// ��v�����ƕԂ�
			return true;
		} else {
			// ��v���Ȃ������ƕԂ�
			return false;
		}
	}

	// �v�Z���ʂ̕\��
	void display() {
		System.out.println("\n---�v�Z����---");
		System.out.println(" send:  " + send);
		System.out.println(" more: +" + more);
		System.out.println("       -----");
		System.out.println("money: " + money);
		System.out.println("(�v�Z��: " + pCount + "��)");
	}

	// ���C�����\�b�h
	public static void main(String[] args) {
		// 1����9�̒l�������ɂ��ĉ����v�Z����(0�͕��ʎZ�̃��[�����g��Ȃ�)
		SendMoreMoney s = new SendMoreMoney();
		System.out.println("<send + more = money>�̉����v�Z���Ă��܂�...");
                for(int i = 1; i < 10; i++) {
        		s.compute(i);
                }

                // �����v�Z�ł��Ȃ������ꍇ�̏o��
                System.out.println("�����v�Z���邱�Ƃ͂ł��܂���ł����B");
	}
}