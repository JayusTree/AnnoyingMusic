import java.io.*;
import java.util.*;

import javax.sound.sampled.*;

import gnu.getopt.Getopt;


public class myAudioMix{

	File mixMusic(int backgroundMusic){
		
		String				strOutputFilename		="合成.wav";
		AudioFormat		audioFormat				=null;
		List					audioInputStreamList =new ArrayList();
		File					soundFile;
		
		
		for (int i = 0; i < 2; i++){
			
			if (i==0) {
				soundFile = new File("过渡.wav");
			}else {
				if (backgroundMusic==1) {
					soundFile = new File("机巧少女不会受伤.wav");
					System.out.println("读取机巧");
				}
				else {
					soundFile = new File("甩葱歌.wav");
				}
			}
			System.out.println("混音中");
			
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

		audioInputStream = new MixingAudioInputStream(audioFormat, audioInputStreamList);

		File	outputFile = new File(strOutputFilename);
		try{
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);
			System.out.println("写了");
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