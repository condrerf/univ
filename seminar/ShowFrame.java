import java.awt.*;
import java.awt.event.*;

public class ShowFrame extends Frame {
	// �R���X�g���N�^
	ShowFrame(String title) {
		// �^�C�g���̐ݒ�
		super(title);

		// �A�v���P�[�V�����̏I���v���擾���̏I������
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	// ���C�����\�b�h
	public static void main(String[] args) {
		// �t���[���̃^�C�g���ƃT�C�Y���w�肵�ĕ\��
		ShowFrame f = new ShowFrame("Frame");
		f.setSize(480, 360);
		f.show();
	}
}
