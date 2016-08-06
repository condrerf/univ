package guiapp;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GUIApp extends Frame implements ActionListener {
	// �C���X�^���X�ϐ��̐錾
	MenuBar mb;
	Menu menu1;
	MenuItem menu1a, menu1b, menu1c;
	Panel pn1, pn2, pn3;
        Label la;
        TextField tf;
	TextArea ta;
	Button lButton, sButton;

	// �R���X�g���N�^
	GUIApp(String title) {
		// �^�C�g���̐ݒ�
		super(title);

		// ���j���[�o�[�̐ݒ�
		mb = new MenuBar();
		setMenuBar(mb);

		menu1 = new Menu("File");
		mb.add(menu1);
		menu1a = new MenuItem("Load");
		menu1a.addActionListener(this);
		menu1.add(menu1a);
		menu1b = new MenuItem("Save");
		menu1b.addActionListener(this);
		menu1.add(menu1b);
		menu1c = new MenuItem("Exit");
		menu1c.addActionListener(this);
		menu1.add(menu1c);

		// �X�e�[�^�X�\�����̐ݒ�
		pn1 = new Panel();
                la = new Label("Status: ");
		pn1.add(la);
		tf = new TextField("Start", 30);
		pn1.add(tf);
       		add(pn1, "North");

		// �t�@�C�����e�\�����̐ݒ�
		pn2 = new Panel();
		ta = new TextArea("TextArea\n", 16, 60);
		pn2.add(ta);
                add(pn2, "Center");

		// �{�^���\�����̐ݒ�
		pn3 = new Panel();
		lButton = new Button("Load");
                lButton.addActionListener(this);
		pn3.add(lButton);
                sButton = new Button("Save");
                sButton.addActionListener(this);
                pn3.add(sButton);
                add(pn3, "South");

		// �A�v���P�[�V�����̏I���v���擾���̏I������
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
                        	dispose();
				System.exit(0);
			}
		});
	}

	// �A�N�V�����C�x���g�̏���
	public void actionPerformed(ActionEvent ae) {
		// Load���I�����ꂽ�ꍇ
                if(ae.getSource() == lButton || ae.getSource() == menu1a) {
                        FileDialog fd = new FileDialog(this, "Load File", FileDialog.LOAD);
                        fd.show();

			if(fd.getFile() != null) {
				// �t�@�C���ǂݍ��݂��v�����ꂽ�ꍇ
	                        try {
					// �t�@�C���̓ǂݍ���
	                                String s;
	                                BufferedReader in = new BufferedReader(new FileReader(fd.getDirectory() + fd.getFile()));
	                                ta.setText("");
        	                        while((s = in.readLine()) != null) {
	                                        ta.append(s + "\n");
	                                }
	                                in.close();

					// �ۑ��������̕\��
	                                setTitle("GUI Application - " + fd.getFile() + ": Loaded");
	                                tf.setText(fd.getFile() + ": Loaded");
	                        } catch(Exception e) {
					// ��O�������̕\��
	                                tf.setText(e.toString());
	                        }
			} else {
				// �ǂݍ��ݒ��f���̕\��
                                setTitle("GUI Application - Load Canceled");
				tf.setText("Load Canceled");
			}
		// Save���I�����ꂽ�ꍇ
                } else if(ae.getSource() == sButton || ae.getSource() == menu1b) {
                        FileDialog fd = new FileDialog(this, "Save File", FileDialog.SAVE);
                        fd.show();

			if(fd.getFile() != null) {
				// �t�@�C���ۑ����v�����ꂽ�ꍇ
	                        try {
					// �t�@�C���̏�������
	                                BufferedWriter out = new BufferedWriter(new FileWriter(fd.getDirectory() + fd.getFile()));
	                                out.write(ta.getText());
	                                out.close();

					// �ۑ��������̕\��
	                                setTitle("GUI Application - " + fd.getFile() + ": Saved");
	                                tf.setText(fd.getFile() + ": Saved");
	                        } catch(Exception e) {
					// ��O�������̕\��
	                                tf.setText(e.toString());
	                        }
			} else {
				// �t�@�C���ۑ����f���̕\��
                                setTitle("GUI Application - Save Canceled");
				tf.setText("Save Canceled");
			}
		// Exit���I�����ꂽ�ꍇ
                } else if(ae.getSource() == menu1c) {
			// �E�B���h�E�����
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
	}

        public void paint(Graphics g) {
        	pack();
        }

	// ���C�����\�b�h
	public static void main(String[] args) {
		// �t���[���̃^�C�g���ƃT�C�Y���w�肵�ĕ\��
		GUIApp f = new GUIApp("GUI Application");
                f.pack();
		f.show();
	}
}
