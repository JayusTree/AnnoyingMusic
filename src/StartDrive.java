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
	
	String[] Subject = {"【科目】报纸编辑学","【科目】报纸发行营销导论","【科目】传播学概论","【科目】当代广播电视概论",
			"【科目】电视受众与文化研究","【科目】电影和电视制作","【科目】广播电视范文评析","【科目】基础英语","【科目】计算机基础",
			"【科目】马克思主义基本原理","【科目】毛邓三概论","【科目】美国政治基础","【科目】深度报道范文评析","【科目】思想道德与法律基础",
			"【科目】通讯范文评析","【科目】外国新闻传播史","【科目】新闻评论范文评析","【科目】新闻评论学教程","【科目】应用文写作",
			"【科目】中国古代文学史","【科目】中国新闻传播史"};
	String[] song = {"【背景音乐】甩葱歌","【背景音乐】机巧少女不会受伤"};
	int SubN = Subject.length;
	int[] CheR =new int[SubN];
	
	public static void main(String[] args) {
		new StartDrive().BuildGUI();
	}
	
	void BuildGUI(){
		JFrame theFrame = new JFrame("烦死人闹钟合成器1.1文科版");
 		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				//GUI添加【右上角关闭功能】
 		JPanel background = new JPanel(new BorderLayout());						//主面板是线性布局
 		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//GUI设定面板上摆设组件时的空白边缘
 		
 		//【1】解决功能列表
 		Box buttonBox = new Box(BoxLayout.Y_AXIS);		//GUI【功能按钮】的列表
 		JButton Start = new JButton("合成并播放");			//GUI添加【播放按钮】
 		Start.addActionListener(new MyStartListener());
 		buttonBox.add(Start);
 		background.add(BorderLayout.EAST,buttonBox);	//播放按钮放在【右方】
 		
 		
 		//【2】解决功能说明列表
 		Box nameBox = new Box(BoxLayout.Y_AXIS);		//GUI添加两个左方【功能说明】
 		for(int i = 0;i<SubN; i++){
 			nameBox.add(new Label(Subject[i]));
 		}
 		for(int i = 0;i<2; i++){
 			nameBox.add(new Label(song[i]));
 		}
 		background.add(BorderLayout.WEST,nameBox);	//功能说明放在【左方】
 		
 		//【3】解决中间布局问题
 		GridLayout grid = new GridLayout(SubN+2,1);	//设置网状布局
 		grid.setVgap(2);										//网格大小
 		grid.setHgap(2);
 		mainPanel = new JPanel(grid);
 		background.add(BorderLayout.CENTER,mainPanel);
 		
 		//【4】在中间布局中，通过循环添加复选按钮
 		checkboxList = new ArrayList<JCheckBox>();	//用来储藏复选按钮的ArrayList 		
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
 		
 		theFrame.getContentPane().add(background);		//将背景加入框架
 		theFrame.setBounds(50,50,300,300);
 		theFrame.pack();
 		theFrame.setVisible(true);

	}
	
	public class MyStartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//生成命令数组
			int[] checkArray = new int[checkboxList.size()];
			int backgroundMusic = 0;
			
			if (r1.isSelected()) {
				backgroundMusic = 0;
			}else {
				backgroundMusic = 1;
			}
			
			//删除旧文件
			File playMe;
			File playMeReal;
			File deleltFile = new File("合成.wav");
			if (deleltFile.exists()) {
				deleltFile.delete();
			}
			
			//检查哪些被点击
			for (int i = 0; i < checkboxList.size(); i++) {
				JCheckBox jc = (JCheckBox) checkboxList.get(i);
				if (jc.isSelected()){
					checkArray[i]=1;
				}else {
					checkArray[i]=0;
				}
			}
			
			//合成音乐
			myAudioConcat audioConcatHelper = new myAudioConcat();
			playMe = audioConcatHelper.ConcatMusic(checkArray);
			
			myAudioMix audioMixHelper = new myAudioMix();
			playMeReal = audioMixHelper.mixMusic(backgroundMusic);
			
			
			//播放
			try {
				FileInputStream myPlayInputStream = new FileInputStream(playMeReal);
				AudioStream myPlayAudioStream = new AudioStream(myPlayInputStream);
				AudioPlayer.player.start(myPlayAudioStream);
			} catch (Exception e) {
				System.out.println("播不了煞笔");
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
