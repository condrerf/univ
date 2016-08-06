package alpha_gui;

import java.io.*;
import java.util.*;

// ���ʎZ
public class Alphametic {
	int count = 0; // values[]�̂ǂ̔z��v�f�܂Ŋi�[���������J�E���g
	int computeCount = 0; // �v�Z�񐔂��J�E���g
	int charsSum = 0; // ���͂��ꂽ������Ɏg��ꂽ�����̎�ސ����i�[
	int[] values; // �e�����ɑΉ�����l��0�`9�̓��ňقȂ�悤�Ɋi�[
	int[] vPositions; // ���͂��ꂽ������̊e�����ɑΉ�����l�̊i�[�ʒu(values[]��)���i�[
	int[] solutions; // �e������̒l���i�[
	char sign; // '+', '-', '*', '/'�̂����ꂩ���i�[
        String[] strs; // �v�Z���Ƃ��Ďw�肳�ꂽ��������i�[
	boolean isUsed[] = new boolean[10]; // 0�`9�̊e�l���g���Ă��邩�ǂ����𔻒f
	boolean isEnd = false; // �I������(�������߂�ꂽ)���ǂ���

	// �R���X�g���N�^
	public Alphametic(Vector v_strs, char sign) {
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
	public void computeSolution(int x) {
		// �v�Z�񐔂����Z
		computeCount++;

                // �l���i�[���ăJ�E���g�𑝂₵�Ax���g��ꂽ���Ƃ��Z�b�g����
		values[count++] = x;
		isUsed[x] = true;

                // ���݂̃J�E���g���ŏI�ł���ꍇ
		if(count == charsSum) {
                        // �v�Z������v�����ꍇ
                        if(isMatched()) {
				// �v�Z���I���������Ƃ��Z�b�g����
				isEnd = true;
				return;
                        } else {
				// �J�E���g��߂���x���g�p�ł���悤�ɃZ�b�g���A���̒l�Ɉڂ�
                                count--;
				isUsed[x] = false;
                                return;
                        }
		}

		// 0����9�̒l�����Ɉ����ɂ��Ď��s����
                for(int i = 0; i < 10; i++) {
			// �������߂�ꂽ�ꍇ
			if(isEnd) {
				// �O�ɖ߂�
				return;
			// i�̒l���g�p�\�ȏꍇ
			} else if(canUse(i)) {
				// i�������ɂ��Ď��s����
		        	computeSolution(i);
	                }
		}

                // �J�E���g��߂��Ax���g�p�ł���悤�ɃZ�b�g����
                count--;
		isUsed[x] = false;
	}

	// �v�Z������v���邩�ǂ�����Ԃ�
	private boolean isMatched() {
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

	// x�̒l���g�p���Ă悢���Ԃ�
	private boolean canUse(int x) {
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

	// �������߂�ꂽ���ǂ�����Ԃ�
	public boolean isEnd() {
		return isEnd;
	}

	// �����̒l��Ԃ�
	public int[] getSolutions() {
		return solutions;
	}

	// �������߂�ꂽ���ǂ�����Ԃ�
	public int getComputeCount() {
		return computeCount;
	}
}