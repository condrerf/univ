package alpha_gui;
import alpha_gui.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

// GUI�ɂ�镢�ʎZ�̌v�Z
public class AlphaGUI extends Frame implements ActionListener, ItemListener {
	Label lStatus, lExpression; // ��ԕ\�����ƌv�Z���\�����̃I�u�W�F�N�g���i�[
	TextField computeField, solutionField; // �e���͗��̃I�u�W�F�N�g���i�[
	Button addtion, setting, start, initialize; // �e�{�^���̃I�u�W�F�N�g���i�[
	Panel pResult2, pResultOutput; // �v�Z���ʂ̕\�������\������p�l���̃I�u�W�F�N�g���i�[
	char sign; // �v�Z�L�����i�[
	String solutionStr; // ���ƂȂ镶������i�[
	Vector vStrs = new Vector(); // ���͂��ꂽ��������i�[
	TextArea outputArea; // ���̏o�͐�
	int solutions[]; // �e������ɑ�������l���i�[
	String strs[];

	// �R���X�g���N�^
	AlphaGUI(String title) {
		// �^�C�g���̐ݒ�
		super(title);

		// �㑤�̕\��������ݒ肷��
		setLayout(new BorderLayout());
		Panel p_top = new Panel();
		p_top.setLayout(new GridLayout(2, 1));
		p_top.setBackground(new Color(250, 250, 250));
		add(p_top, "North");

		// ��Ԃ̕\�����\��
		Panel p_status = new Panel();
		p_status.setLayout(new BorderLayout());
		lStatus = new Label("");
		p_status.add(lStatus, "West");
		p_top.add(p_status);

		// �v�Z���̕\�����\��
		Panel p_expression = new Panel();
		p_expression.setLayout(new BorderLayout());
		lExpression = new Label("");
		p_expression.add(lExpression, "West");
		p_top.add(p_expression);

		// �����̕\��������ݒ肷��
		Panel p_bottom = new Panel();
		p_bottom.setLayout(new GridLayout(1, 2));
		add(p_bottom, "Center");

		// �����̍��\��������ݒ肷��
		Panel p_left = new Panel();
		p_left.setLayout(new GridLayout(6, 1));
		p_left.setBackground(new Color(230, 230, 230));
		p_bottom.add(p_left);

		// �v�Z����l�ƂȂ镶����̓��͗��\��
		Panel p_compute = new Panel();
		p_compute.setLayout(new BorderLayout());
		Label l_compute = new Label("<�v�Z����l�ƂȂ镶����>");
		p_compute.add(l_compute, "West");
		p_left.add(p_compute);

		Panel p_compute2 = new Panel();
		computeField = new TextField("", 10);
                computeField.addActionListener(this);
		p_compute2.add(computeField);
		addtion = new Button("�ǉ�");
                addtion.addActionListener(this);
		p_compute2.add(addtion);
		p_left.add(p_compute2);

		// �v�Z�L��("+", "-", "*", "/")�̑I�𗓕\��
		Panel p_sign = new Panel();
		p_sign.setLayout(new BorderLayout());
		Label l_sign = new Label("<�v�Z�L��>");
		p_sign.add(l_sign, "West");
		p_left.add(p_sign);

		Panel p_sign2 = new Panel();
		CheckboxGroup cbg = new CheckboxGroup();
		Checkbox add = new Checkbox("+", cbg, true);
		add.addItemListener(this);
		p_sign2.add(add);
		Checkbox subtract = new Checkbox("-", cbg, false);
		subtract.addItemListener(this);
		p_sign2.add(subtract);
		Checkbox multiply = new Checkbox("*", cbg, false);
		multiply.addItemListener(this);
		p_sign2.add(multiply);
		Checkbox divide = new Checkbox("/", cbg, false);
		divide.addItemListener(this);
		p_sign2.add(divide);
		p_left.add(p_sign2);

		// �v�Z����l�ƂȂ镶����̓��͗��\��
		Panel p_solution = new Panel();
		p_solution.setLayout(new BorderLayout());
		Label l_solution = new Label("<���̒l�ƂȂ镶����>");
		p_solution.add(l_solution, "West");
		p_left.add(p_solution);

		Panel p_solution2 = new Panel();
		solutionField = new TextField("", 10);
                solutionField.addActionListener(this);
		p_solution2.add(solutionField);
		setting = new Button("�Z�b�g");
		setting.addActionListener(this);
		p_solution2.add(setting);
		p_left.add(p_solution2);

		// �v�Z�J�n�{�^���Ə������{�^���̕\��
		Panel p_setting = new Panel();
		start = new Button("�v�Z�J�n");
		start.addActionListener(this);
		p_setting.add(start);
		initialize = new Button("������");
		initialize.addActionListener(this);
		p_setting.add(initialize);
		p_left.add(p_setting);

		// �����̉E�\��������ݒ肷��
		Panel p_right = new Panel();
		p_right.setLayout(new BorderLayout());
		p_bottom.add(p_right);

		// �v�Z���ʂ̌��o���̕\��
		Panel p_result = new Panel();
		p_result.setLayout(new BorderLayout());
		Label l_result = new Label("<�v�Z����>");
		p_result.add(l_result, "West");
		p_right.add(p_result, "North");

		pResult2 = new Panel();
		pResult2.setLayout(new BorderLayout());
		outputArea = new TextArea("", 10, 10);
		pResult2.add(outputArea, "Center");
/*		pResultOutput = new Panel();
		pResultOutput.setLayout(new GridLayout(1, 2));
		pResult2.add(pResultOutput, "Center");
*/		p_right.add(pResult2, "Center");

		// �A�v���P�[�V�����̏I���v���擾���̏I������
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
				System.exit(0);
			}
		});

		// �v�f�̏�����
		init();
	}

	// �v�f�̏�����
	void init() {
		sign = '+';
		vStrs.removeAllElements();
		solutionStr = "";
		lStatus.setText("���:");
		lExpression.setText("�v�Z��:");
		outputArea.setText("");

/*		// �v�Z���ʕ\�����̏�����
		pResultOutput = new Panel();
		pResultOutput.setLayout(new GridLayout(1, 2));
*/	}

	// �A�N�V�����C�x���g�̏���
	public void actionPerformed(ActionEvent ae) {
		lStatus.setText("���:");
		// �v�Z�J�n�{�^���������ꂽ�ꍇ
		if(ae.getSource() == start) {
			// �v�Z����l�ƂȂ镶����1�����ȉ������ƂȂ镶���񂪓��͂���Ă��Ȃ��ꍇ
			if(vStrs.size() < 2 || solutionStr.equals("")) {
				// �v�Z���J�n���Ȃ�
				lStatus.setText("���: �v�Z���s�����ԂɂȂ��Ă��܂���B");
				repaint();
				return;
			}

			// ���ƂȂ镶������x�N�g���ɒǉ�����
			vStrs.addElement(solutionStr);

/*			// ���̕\������\������
			Panel p_output_left = new Panel();
			p_output_left.setLayout(new GridLayout(vStrs.size() + 1, 1));
			pResultOutput.add(p_output_left);

			Panel[] p_output_char = new Panel[vStrs.size()];
			Label[] l_output_char = new Label[vStrs.size()];
			for(int i = 0; i < vStrs.size(); i++) {
				p_output_char[i] = new Panel();
				p_output_char[i].setLayout(new BorderLayout());
				l_output_char[i] = new Label((String)vStrs.elementAt(i) + ": ");
				p_output_char[i].add(l_output_char[i], "East");
				p_output_left.add(p_output_char[i]);
			}
			l_output_char[vStrs.size() - 1].setText((String)vStrs.elementAt(vStrs.size() - 1) + ": ");

			Panel p_output_right = new Panel();
			p_output_right.setLayout(new GridLayout(vStrs.size() + 1, 1));
			pResultOutput.add(p_output_right);

			Panel[] p_output_num = new Panel[vStrs.size()];
			Label[] l_output_num = new Label[vStrs.size()];
			for(int i = 0; i < vStrs.size(); i++) {
				p_output_num[i] = new Panel();
				p_output_num[i].setLayout(new BorderLayout());
				l_output_num[i] = new Label();
				p_output_num[i].add(l_output_num[i], "East");
				p_output_right.add(p_output_num[i]);
			}
*/
			// ���ʎZ���v�Z����C���X�^���X�̐���
			Alphametic alpha = new Alphametic(vStrs, sign);

			// �^�C���J�E���^�̃X�^�[�g
			TimeCounter t = new TimeCounter(10);
			t.start();

			// 1����9�̒l�������ɂ��ĉ����v�Z����(0�͕��ʎZ�̃��[�����g��Ȃ�)
			lStatus.setText("���: �������߂Ă��܂�...");
			outputArea.setText("");
			repaint();
			for(int i = 1; i < 10; i++) {
				// �v�Z�����s����
				alpha.computeSolution(i);

				// �������߂�ꂽ�ꍇ
				if(alpha.isEnd()) {
					// �����o�͂���
					lStatus.setText("���: �v�Z���� (�v�Z��: " + alpha.getComputeCount() + "��, �v�Z����: " + t.getCount() + "�b)");

					solutions = alpha.getSolutions();
					display();
/*					for(int j = 0; j < vStrs.size() - 1; j++) {
						l_output_num[j].setText(solutions[j]);
					}
					l_output_num[vStrs.size()].setText(solutions[vStrs.size()];
*/
					t.stopRun();
					vStrs.removeElementAt(vStrs.size() - 1);
					repaint();
					return;
				}
			}

			// ����������Ȃ��������Ƃ�ʒm����
			lStatus.setText("���: ���͌�����܂���ł����B");
			t.stopRun();
			vStrs.removeElementAt(vStrs.size() - 1);
			repaint();
			return;
		// �������{�^���������ꂽ�ꍇ
		} else if(ae.getSource() == initialize) {
			// �v�f�̏�����
			init();
			lStatus.setText("���: ����������܂����B");
			repaint();
			return;
		// �v�Z����l�ƂȂ镶������͗��ŃG���^�[�L�[�������ꂽ���ǉ��{�^���������ꂽ�ꍇ
		} else if( ae.getSource() == computeField || ae.getSource() == addtion) {
			// �v�Z����l�ƂȂ镶���񂪋󔒂������ꍇ
			if(computeField.getText().equals("")) {
				lStatus.setText("���: ��̕����͎󂯕t�����܂���B");
			} else {
				// �v�f��ǉ�����
				vStrs.addElement(computeField.getText());
				computeField.setText("");
			}
		// ���̕�������͗��ŃG���^�[�L�[�������ꂽ���Z�b�g�{�^���������ꂽ�ꍇ
		} else if(ae.getSource() == solutionField || ae.getSource() == setting) {
			// ���ƂȂ镶���񂪋󔒂������ꍇ
			if(solutionField.getText().equals("")) {
				lStatus.setText("���: ��̕����͎󂯕t�����܂���B");
			} else {
				// ���ƂȂ镶������i�[����
				solutionStr = solutionField.getText();
				solutionField.setText("");
			}
		}

		// �v�Z���̕\�����X�V����
		updateExpression();
		repaint();
	}

	// �I�����ꂽ�v�Z�L�����i�[����
	public void itemStateChanged(ItemEvent ie) {
		Checkbox cb = (Checkbox)ie.getItemSelectable();
		sign = cb.getLabel().charAt(0);

		// �v�Z���̕\�����X�V����
		updateExpression();
	}

	// �v�Z���̕\�����X�V����
	void updateExpression() {
		lExpression.setText("�v�Z��: ");

		// �v�Z����l�ƂȂ镶����1�ȏ�i�[����Ă���ꍇ
		if(vStrs.size() >= 1) {

			// �ŏ��̕�����v�f��\������
			lExpression.setText(lExpression.getText() + (String)vStrs.elementAt(0));

			// 2�ڈȍ~�̕�����v�f��\������
			for(int i = 1; i < vStrs.size(); i++) {
				lExpression.setText(lExpression.getText() + sign + vStrs.elementAt(i));
			}

			// ���ƂȂ镶���񂪊i�[����Ă���ꍇ
			if(!(solutionStr.equals(""))) {
				// ���ƂȂ镶�����\������
				lExpression.setText(lExpression.getText() + " = " + solutionStr);
			}
		}
	}

	public void paint(Graphics g) {
		pack();
	}

	void display() {
		strs = new String[vStrs.size()];
		vStrs.copyInto(strs);

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

		// �v�Z����l�ƂȂ���������Ƃ��̒l���o��
		for(int i = 0; i < strs.length - 1; i++) {
			for(int j = 0; j < max_char_length - strs[i].getBytes().length; j++) {
				outputArea.append(" ");
			}
			// ���̗v�f�����������̍ŏI�v�f�̎�
			if(i == strs.length - 2) {
				// �L����t������
				outputArea.append(strs[i] + ":" + sign);
			} else {
				outputArea.append(strs[i] + ": ");
			}
			for(int j = 0; j < max_num_length - strs[i].length(); j++) {
				outputArea.append(" ");
			}
			outputArea.append(solutions[i] + "\n");
		}

		// ��؂���̏o��
		for(int i = 0; i < max_char_length + 1; i++) {
			outputArea.append(" ");
		}
		for(int i = 0; i < max_num_length + 1; i++) {
			outputArea.append("-");
		}
		outputArea.append("\n");

		// ���̒l�ƂȂ���������ƒl�̏o��
		for(int i = 0; i < max_char_length - strs[strs.length - 1].getBytes().length; i++) {
			outputArea.append(" ");
		}
		outputArea.append(strs[strs.length - 1] + ": " + solutions[strs.length - 1] + "\n");
	}

	// ���C�����\�b�h
	public static void main(String[] args) {
		// �t���[���̃^�C�g���ƃT�C�Y���w�肵�ĕ\��
		AlphaGUI alpha_gui = new AlphaGUI("���ʎZ�̌v�Z");
//		alpha_gui.setSize(480, 360);
		alpha_gui.pack();
		alpha_gui.show();
	}
}