package MidiUtil;

import MidiUtil.Composer;

public class Driver
{

	public static void main(String[] args)
	{
		long l = System.currentTimeMillis();
		String file = "D:\\Drive\\temp2.mid";
		MidiFileBuilder mBuilder = new MidiFileBuilder(48, file);
		Composer.compose(mBuilder, Composer.SIMPLE_PROGRESSION, Composer.SIMPLE_RYTHMN, Composer.SIMPLE_DRYTHMN, 12,5,16);
		mBuilder.writeToFile();
		MidiFileAnalysis.readFile(file);
		SimpleMidiPlayer.play(file);
		System.out.println(System.currentTimeMillis() - l);
	}
}
