// �O�o�[�W�����Ƃ̑���_: 1�̌o�H�������Ă��I�������A�o�H�������߂�悤�ɕύX

// �R�m(�i�C�g)�̏���
public class Knight {
	int size; // �Ղ̑傫��
	int count; // ���݂̎��s������
	int startX, startY; // ���s�J�n����x���W��y���W
	int[] xMoveList = {1, 2, 2, 1, -1, -2, -2, -1}; // x���W��8�����ړ����X�g
	int[] yMoveList = {-2, -1, 1, 2, 2, 1, -1, -2}; // y���W��8�����ړ����X�g
	int[][] board; // �����������i�[
	int[][] sCount; // �e���W�̏��𐬌���

	// �Ղ̏�����
	Knight(int s) {
		// �Ղ̑傫�����i�[����
		size = s;

		// �ՊO�ւ̈ړ����s���l�����ď㉺���E��2�����߂ɐݒ肷��
		board = new int[s + 4][s + 4];

		// �ՊO��1�A����ȊO��0�ɏ�����
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

		// ���𐬌��񐔂��i�[����z���0�ɏ�����
		sCount = new int[s][s];
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s + 0; j++) {
				sCount[i][j] = 0;
			}
		}
	}

	// �e���W���Ƃ̏���O�̏�����
	void init(int x, int y) {
		count = 0;
		startX = x;
		startY = y;
	}

	// ��������
	void tour(int x, int y) {
		// �ړ��\����W���ՊO�A�܂��͍��܂łɎg�p���Ă�����߂�
		if (board[x][y] != 0) {
			return;
		}

		// ���ݍ��W�Ɍ��݃J�E���g���i�[
		board[x][y] = ++count;

		// �S���W�����������ꍇ
		if (count == Math.pow(size, 2)) {
			// ���𐬌��o�H���𑝂₷
			sCount[startX][startY]++;

/*			// �����_�̏��𐬌��o�H���ƔՂ̏������ʂ��o��(�f�o�b�O�p)
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
			// 8�����ւ̈ړ������s
			for (int i = 0; i < 8; i++) {
				move(x + xMoveList[i], y + yMoveList[i]);
			}
		}

		// ���ݍ��W����ǂ̕����ւ��ړ��ł��Ȃ������ꍇ
		board[x][y] = 0;
		count--;
	}

	// ���ʂ̕\��
	void display() {
		int sTotal = 0; // �����񐔂̍��v���i�[

		// ���o���̕\������
		System.out.print("\n");
		for(int i = 0; i < size - 4; i++) {
			System.out.print("--");
		}
		System.out.print(size + " * " + size + "�̏����̐����o�H��");
		for(int i = 0; i < size - 4; i++) {
			System.out.print("--");
		}
		System.out.print("\n");

		// ���ʂ̕\��
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				// �����񐔂ɉ����ĕ\���𒲐�����
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
		System.out.println("(���v: " + sTotal + "��)");
	}

	// ���C�����\�b�h
	public static void main(String[] args) {
		int size;

		// �Ղ̑傫���̐ݒ�
		if(args.length > 0 && Integer.parseInt(args[0]) > 0) {
			size = Integer.parseInt(args[0]);
		}
		else {
			size = 5;
		}

		// �C���X�^���X�̐���
		Knight k = new Knight(size);

		// ��������
		System.out.print(size + " * " + size + "�̏�����");
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				k.init(i, j);
				System.out.println("(" + i + ", " + j + ")����_�ɂ��čs���Ă��܂�...");
				k.tour(i + 2, j + 2);
			}
		}

		// ���ʂ̕\��
		k.display();
	}
}