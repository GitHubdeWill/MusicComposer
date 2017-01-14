package MidiUtil;

import java.util.Random;

import MidiUtil.Composer;

public class Driver
{

	public static void main(String[] args)
	{
		long l = System.currentTimeMillis();
		String file = "temp.mid";
		if (args.length >= 1) file = args[0];
		MidiFileBuilder mBuilder = new MidiFileBuilder(48, file);
		int[][] progression_2 = {
				{0,9,4,9,2,5,5,7,0,9,2,11,4,0,5,7},//Root Note
				{0,4,4,4,4,2,0,3,2,3,4,3 ,4,3,2,3},//Chord
				{0,1,1,1,1,0,0,2,0,6,1,5 ,1,6,0,2}//Scale Type
//				{0,9,5,7},
//				{0,1,0,0},
//				{0,9,5,7}
		};

		int[][] progression_5 = {
				{0,5,11,4,9,2,7 ,7},//Root Note
				{2,2,9 ,4,4,4,12,3},//Chord
				{0,0,4 ,1,1,1,7 ,2}//Scale Type
		};
		int[][] progression_4 = {
				{0,7,2,9,4,11,5,5},//Root Note
				{2,3,4,4,4,9 ,3,2},//Chord
				{0,2,1,1,1,4 ,6,0}//Scale Type
		};
		int[][] progression_3 = {
				{0,9,5,2,0,4,7 ,7},//Root Note
				{0,4,2,4,0,4,12,3},//Chord
				{0,1,0,1,0,1,7 ,2}//Scale Type
		};
		
		int[][][] progs = new int[][][]{
			progression_2, 
			progression_3, 
			progression_4,
			progression_5,
		};
		if (args.length >= 2){
			ImageReader ir = new ImageReader(args[1]);
			int[][] pp = ir.marchThroughImage();
			progs = new int[][][] {
				pp,
			};
		}

		Composer.compose(mBuilder, progs[Composer.getRanItemFromArray(new int[]{0})], Composer.MAJOR_SCALES, Composer.SIMPLE_RYTHMN, Composer.SIMPLE_DRYTHMN, 12,10,16);
		mBuilder.writeToFile();
		MidiFileAnalysis.readFile(file);
		SimpleMidiPlayer.play(file);
		System.out.println(System.currentTimeMillis() - l);
	}
}
