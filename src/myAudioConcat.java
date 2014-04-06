import java.io.*;
import java.util.*;

import javax.sound.sampled.*;

import gnu.getopt.Getopt;


public class myAudioConcat{

	File ConcatMusic(int[] CommandArray){
		
		String				strOutputFilename		="����.wav";
		boolean			startDone					=false;
		boolean			timeToEnd					=false;
		AudioFormat		audioFormat				=null;
		List					audioInputStreamList =new ArrayList();
		ArrayList<File>	myMusicList				=new ArrayList<File>();
		File					soundFile;
		String[]				musicName				={"��ֽ�༭ѧ.wav","��ֽ����Ӫ������.wav","����ѧ����.wav","�����㲥���Ӹ���.wav",
			"�����������Ļ��о�.wav","��Ӱ�͵�������.wav","�㲥���ӷ�������.wav","����Ӣ��.wav","���������.wav",
			"���˼�������ԭ��.wav","ë��������.wav","�������λ���.wav","��ȱ�����������.wav","˼������뷨�ɻ���.wav","ͨѶ��������.wav","������Ŵ���ʷ.wav",
			"�������۷�������.wav","��������ѧ�̳�.wav","Ӧ����д��.wav","�й��Ŵ���ѧʷ.wav","�й����Ŵ���ʷ.wav"};
		
		for (int i = 0; i < musicName.length; i++) {
			soundFile = new File(musicName[i]);
			myMusicList.add(soundFile);
		}
		
		
		for (int i = 0; i < CommandArray.length+2; i++){
			
			if (i==(CommandArray.length+1)) {
				timeToEnd = true;
			}
			
			if (!startDone) {
				soundFile = new File("��ͷ.wav");
				startDone = true;
			}else if (timeToEnd) {
				soundFile = new File("��β.wav");
			}else if ((CommandArray[i-1]==1)&&startDone) {
				soundFile = myMusicList.get(i-1);
			}else {
				continue;
			}
			System.out.println("��"+(i+1)+"�������ںϳ�");
			
			//�õ��������Ƶ��
			AudioInputStream	audioInputStream = null;
			try{
				audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			}
			catch (Exception e){
				
				//��һ�����쳣�����Ǿ�����쳣��Ϣ���˳�����
				e.printStackTrace();
				System.exit(1);
			}
			AudioFormat	format = audioInputStream.getFormat();
			
			//�����Ƶ��ʽΪ�ޣ�������Ƶ���ĸ�ʽ
			if (audioFormat == null){
				audioFormat = format;
			}
			else if ( ! audioFormat.matches(format)){
				// TODO: try to convert
				out("AudioConcat.main(): WARNING: AudioFormats don't match");
				out("AudioConcat.main(): master format: " + audioFormat);
				out("AudioConcat.main(): this format: " + format);
			}
			audioInputStreamList.add(audioInputStream);
		}

		if (audioFormat == null){
			out("No input filenames!");
			printUsageAndExit();
		}
		AudioInputStream	audioInputStream = null;

		audioInputStream = new SequenceAudioInputStream(audioFormat, audioInputStreamList);

		File	outputFile = new File(strOutputFilename);
		try{
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);
		}
		catch (IOException e){
			e.printStackTrace();
		}

		return outputFile;
	}

	private static void printUsageAndExit(){
		out("AudioConcat: usage:");
		out("\tjava AudioConcat -h");
		out("\tjava AudioConcat [-D] -c|-m -o <outputfile> <inputfile> ...");
		System.exit(1);
	}

	private static void out(String strMessage){
		System.out.println(strMessage);
	}
}