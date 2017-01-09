package MidiUtil;

import java.io.File;
import java.util.Random;

public class AnalysisDriver
{

	public static void main(String[] args)
	{
		String dirName = "D:\\Midis\\Midi\\www.midiworld.com\\download\\";
		File dir = new File(dirName);
		File[] files = dir.listFiles();

		Random rand = new Random();

//		String file = files[rand.nextInt(files.length)].getAbsolutePath();
		String file = dirName + "Valtsu_-_188s.mid";
		String targetFile = dirName + "test.mid";
//		MidiFileAnalysis.readFile(file);
		MidiFileAnalysis.transformFile(file, targetFile);
		SimpleMidiPlayer.play(targetFile);
	}
}
