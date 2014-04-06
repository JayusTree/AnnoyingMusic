import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.*;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class StartDrive {

	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList;
	ArrayList<JRadioButton> checkboxListForR;
	JFrame theFrame;
	JRadioButton r1;
	JRadioButton r2;
	
	String[] Subject = {"����Ŀ����ֽ�༭ѧ","����Ŀ����ֽ����Ӫ������","����Ŀ������ѧ����","����Ŀ�������㲥���Ӹ���",
			"����Ŀ�������������Ļ��о�","����Ŀ����Ӱ�͵�������","����Ŀ���㲥���ӷ�������","����Ŀ������Ӣ��","����Ŀ�����������",
			"����Ŀ�����˼�������ԭ��","����Ŀ��ë��������","����Ŀ���������λ���","����Ŀ����ȱ�����������","����Ŀ��˼������뷨�ɻ���",
			"����Ŀ��ͨѶ��������","����Ŀ��������Ŵ���ʷ","����Ŀ���������۷�������","����Ŀ����������ѧ�̳�","����Ŀ��Ӧ����д��",
			"����Ŀ���й��Ŵ���ѧʷ","����Ŀ���й����Ŵ���ʷ"};
	String[] song = {"���������֡�˦�и�","���������֡�������Ů��������"};
	int SubN = Subject.length;
	int[] CheR =new int[SubN];
	
	public static void main(String[] args) {
		new StartDrive().BuildGUI();
	}
	
	void BuildGUI(){
		JFrame theFrame = new JFrame("���������Ӻϳ���1.1�Ŀư�");
 		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				//GUI��ӡ����Ͻǹرչ��ܡ�
 		JPanel background = new JPanel(new BorderLayout());						//����������Բ���
 		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//GUI�趨����ϰ������ʱ�Ŀհױ�Ե
 		
 		//��1����������б�
 		Box buttonBox = new Box(BoxLayout.Y_AXIS);		//GUI�����ܰ�ť�����б�
 		JButton Start = new JButton("�ϳɲ�����");			//GUI��ӡ����Ű�ť��
 		Start.addActionListener(new MyStartListener());
 		buttonBox.add(Start);
 		background.add(BorderLayout.EAST,buttonBox);	//���Ű�ť���ڡ��ҷ���
 		
 		
 		//��2���������˵���б�
 		Box nameBox = new Box(BoxLayout.Y_AXIS);		//GUI��������󷽡�����˵����
 		for(int i = 0;i<SubN; i++){
 			nameBox.add(new Label(Subject[i]));
 		}
 		for(int i = 0;i<2; i++){
 			nameBox.add(new Label(song[i]));
 		}
 		background.add(BorderLayout.WEST,nameBox);	//����˵�����ڡ��󷽡�
 		
 		//��3������м䲼������
 		GridLayout grid = new GridLayout(SubN+2,1);	//������״����
 		grid.setVgap(2);										//�����С
 		grid.setHgap(2);
 		mainPanel = new JPanel(grid);
 		background.add(BorderLayout.CENTER,mainPanel);
 		
 		//��4�����м䲼���У�ͨ��ѭ����Ӹ�ѡ��ť
 		checkboxList = new ArrayList<JCheckBox>();	//�������ظ�ѡ��ť��ArrayList 		
 		for(int i = 0;i < SubN; i++){
 			JCheckBox c = new JCheckBox();
 			c.setSelected(false);
 			checkboxList.add(c);
 			mainPanel.add(c);
 		}
 		r1 = new JRadioButton();
		r1.setSelected(false);
		mainPanel.add(r1);
		r1.addActionListener(new MyR1Listener());
		
		r2 = new JRadioButton();
		r2.setSelected(false);
		mainPanel.add(r2);
		r2.addActionListener(new MyR2Listener());
 		
 		theFrame.getContentPane().add(background);		//������������
 		theFrame.setBounds(50,50,300,300);
 		theFrame.pack();
 		theFrame.setVisible(true);

	}
	
	public class MyStartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//������������
			int[] checkArray = new int[checkboxList.size()];
			int backgroundMusic = 0;
			
			if (r1.isSelected()) {
				backgroundMusic = 0;
			}else {
				backgroundMusic = 1;
			}
			
			//ɾ�����ļ�
			File playMe;
			File playMeReal;
			File deleltFile = new File("�ϳ�.wav");
			if (deleltFile.exists()) {
				deleltFile.delete();
			}
			
			//�����Щ�����
			for (int i = 0; i < checkboxList.size(); i++) {
				JCheckBox jc = (JCheckBox) checkboxList.get(i);
				if (jc.isSelected()){
					checkArray[i]=1;
				}else {
					checkArray[i]=0;
				}
			}
			
			//�ϳ�����
			myAudioConcat audioConcatHelper = new myAudioConcat();
			playMe = audioConcatHelper.ConcatMusic(checkArray);
			
			myAudioMix audioMixHelper = new myAudioMix();
			playMeReal = audioMixHelper.mixMusic(backgroundMusic);
			
			
			//����
			try {
				FileInputStream myPlayInputStream = new FileInputStream(playMeReal);
				AudioStream myPlayAudioStream = new AudioStream(myPlayInputStream);
				AudioPlayer.player.start(myPlayAudioStream);
			} catch (Exception e) {
				System.out.println("������ɷ��");
				e.printStackTrace();
			}
		}

	}
	
	public class MyR1Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			r2.setSelected(false);
		}

	}
	
	public class MyR2Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			r1.setSelected(false);

		}

	}
	
}
