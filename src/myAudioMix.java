import java.io.*;
import java.util.*;

import javax.sound.sampled.*;

import gnu.getopt.Getopt;


public class myAudioMix{

	File mixMusic(int backgroundMusic){
		
		String				strOutputFilename		="�ϳ�.wav";
		AudioFormat		audioFormat				=null;
		List					audioInputStreamList =new ArrayList();
		File					soundFile;
		
		
		for (int i = 0; i < 2; i++){
			
			if (i==0) {
				soundFile = new File("����.wav");
			}else {
				if (backgroundMusic==1) {
					soundFile = new File("������Ů��������.wav");
					System.out.println("��ȡ����");
				}
				else {
					soundFile = new File("˦�и�.wav");
				}
			}
			System.out.println("������");
			
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

		audioInputStream = new MixingAudioInputStream(audioFormat, audioInputStreamList);

		File	outputFile = new File(strOutputFilename);
		try{
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);
			System.out.println("д��");
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