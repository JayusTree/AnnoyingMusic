import java.io.*;
import java.util.*;

import javax.sound.sampled.*;

import gnu.getopt.Getopt;


public class myAudioConcat{

	File ConcatMusic(int[] CommandArray){
		
		String				strOutputFilename		="过渡.wav";
		boolean			startDone					=false;
		boolean			timeToEnd					=false;
		AudioFormat		audioFormat				=null;
		List					audioInputStreamList =new ArrayList();
		ArrayList<File>	myMusicList				=new ArrayList<File>();
		File					soundFile;
		String[]				musicName				={"报纸编辑学.wav","报纸发行营销导论.wav","传播学概论.wav","当代广播电视概论.wav",
			"电视受众与文化研究.wav","电影和电视制作.wav","广播电视范文评析.wav","基础英语.wav","计算机基础.wav",
			"马克思主义基本原理.wav","毛邓三概论.wav","美国政治基础.wav","深度报道范文评析.wav","思想道德与法律基础.wav","通讯范文评析.wav","外国新闻传播史.wav",
			"新闻评论范文评析.wav","新闻评论学教程.wav","应用文写作.wav","中国古代文学史.wav","中国新闻传播史.wav"};
		
		for (int i = 0; i < musicName.length; i++) {
			soundFile = new File(musicName[i]);
			myMusicList.add(soundFile);
		}
		
		
		for (int i = 0; i < CommandArray.length+2; i++){
			
			if (i==(CommandArray.length+1)) {
				timeToEnd = true;
			}
			
			if (!startDone) {
				soundFile = new File("开头.wav");
				startDone = true;
			}else if (timeToEnd) {
				soundFile = new File("结尾.wav");
			}else if ((CommandArray[i-1]==1)&&startDone) {
				soundFile = myMusicList.get(i-1);
			}else {
				continue;
			}
			System.out.println("第"+(i+1)+"部分正在合成");
			
			//得到输入的音频流
			AudioInputStream	audioInputStream = null;
			try{
				audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			}
			catch (Exception e){
				
				//万一出了异常，我们就输出异常信息并退出程序
				e.printStackTrace();
				System.exit(1);
			}
			AudioFormat	format = audioInputStream.getFormat();
			
			//如果音频格式为无，则获得音频流的格式
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