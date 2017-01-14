package MidiUtil;

import java.util.Random;

import MidiUtil.Composer;

public class Driver
{

	public static void main(String[] args)
	{
		long l = System.currentTimeMillis();
		String file = args[0];
		MidiFileBuilder mBuilder = new MidiFileBuilder(48, file);

		int[][] progression_5 = {
				{0,5,11,4,9,2,7,7},//Root Note
				{2,2,9,4,4,4,3,3},//Chord
				{0,5,11,4,9,2,7,7}//Scale Type
		};
		int[][] progression_4 = {
				{0,7,2,9,4,11,5,5},//Root Note
				{2,3,4,4,4,9,2,2},//Chord
				{0,7,2,9,4,11,5,5}//Scale Type
		};
		int[][] progression_3 = {
				{0,9,5,2,0,4,7,7},//Root Note
				{0,4,2,4,0,4,3,3},//Chord
				{0,9,5,2,0,4,7,7}//Scale Type
		};
		ImageReader ir = new ImageReader("D:\\Drive\\eclipseWorkSpace\\MusicComposor\\test.jpg");
		int[][] pp = ir.marchThroughImage();
		
		int[][][] progs = new int[][][]{Composer.SIMPLE_PROGRESSION, progression_3, progression_4
			,progression_5};
		
		Composer.compose(mBuilder, progs[Composer.getRanItemFromArray(new int[]{0,1,2,3})], Composer.MAJOR_SCALES, Composer.SIMPLE_RYTHMN, Composer.SIMPLE_DRYTHMN, 12,5,16);
		mBuilder.writeToFile();
		MidiFileAnalysis.readFile(file);
		SimpleMidiPlayer.play(file);
		System.out.println(System.currentTimeMillis() - l);
	}
}
