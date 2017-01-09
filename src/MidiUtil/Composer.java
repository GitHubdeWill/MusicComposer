package MidiUtil;

import java.util.Random;

public class Composer
{

	final static int C = 60;
	public static final int[] 	
				CHROMATIC_SCALE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
				
				IONIAN_SCALE = {0, 2, 4, 5, 7, 9, 11},
				DORIAN_SCALE = {0, 2, 3, 5, 7, 9, 10},
				PHRYGIAN_SCALE = {0, 1, 3, 5, 7, 8, 10},
				LYDIAN_SCALE = {0, 2, 4, 6, 7, 9, 11},
				MIXOLYDIAN_SCALE = {0, 2, 4, 5, 7, 9, 10},
				AEOLION_SCALE = {0, 2, 3, 5, 7, 8, 10},
				LOCRIAN_SCALE = {0, 1, 3, 5, 6, 8, 10},
				
				HARMONIC_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 11},
				MELODIC_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 9, 10, 11},
				NATURAL_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 10},
				DIATONIC_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 10},
				
				PENTATONIC_SCALE = {0, 2, 4, 7, 9},
				BLUES_SCALE = {0, 2, 3, 4, 5, 7, 9, 10, 11},
				TURKISH_SCALE = {0, 1, 3, 5, 7, 10, 11},
				INDIAN_SCALE = {0, 1, 1, 4, 5, 8, 10}
	;
	
	public final static int[][] CHORDS= {
			{0,4,7}, //MAJ
			{0,3,7}, //MIN
			{0,4,7,11}, //MAJ7
			{0,4,7,10}, //7
			{0,3,7,10}, //MIN7
			{0,4,6,10}, //7-5
			{0,4,8}, //AUG
			{0,4,8,10}, //AUG7
			{0,3,6}, //DIM
			{0,3,6,9}, //DIM7
			{0,4,7,9}, //6
			{0,2,7,12}, //SUS2
			{0,5,7,12}, //SUS4
	};
	
	public final static int[][] MAJOR_SCALES = {
			{0, 2, 4, 7, 9, 11},
			{},
			{0, 2, 3, 5, 7, 10},
			{},
			{0, 3, 5, 7, 10},
			{0, 2, 4, 6, 7, 9, 11},
			{},
			{0, 2, 4, 7, 9, 10},
			{},
			{0, 2, 3, 5, 7, 10},
			{},
			{0, 3, 5, 6, 8, 10},
	};
	
	//Note,Chord,Inversion
	public final static int[][] SIMPLE_PROGRESSION = {
			{0,9,4,9,2,5,5,7,0,9,2,11,4,0,5,7},//Root Note
			{0,4,4,4,4,2,0,3,2,3,4,3 ,4,3,2,3},//Chord
			{0,9,4,9,2,5,5,7,0,9,2,11,4,0,5,7}//Scale Type
//			{0,9,5,7},
//			{0,1,0,0},
//			{0,9,5,7}
	};
	

	public final static int[] SIMPLE_RYTHMN = {1,0,1,0,1,1,1,0,1,0,1,1,0,1,1,0};
	
	public final static int[] SIMPLE_DRYTHMN = {1,0,2,0,3,0,2,1,1,0,2,0,3,0,2,2};
	
	//0-11, 0 - 12; 1 - 4;
	public static int[] getChordInversion (int note, int chord, int inversion) {
		if (inversion > CHORDS[chord].length) return null;
		int[] ret = new int[CHORDS[chord].length];
		int index = 0;
		for (int i = inversion - 1; i < CHORDS[chord].length; i++) {
			ret[index++] = CHORDS[chord][i]+note;
		}
		for (int i = 0; i < inversion - 1; i++) {
			ret[index++] = CHORDS[chord][i]+note;
		}
		return ret;
	}
	
	public static int getTransNote (int scaleType, int note) {
//		return getRanItemFromArray(MAJOR_SCALES[scaleType]);
		for (int i = 0; i < MAJOR_SCALES[scaleType].length; i++) {
			if (MAJOR_SCALES[scaleType][i] == note) return MAJOR_SCALES[scaleType][(Math.abs(i+getRanPN())%MAJOR_SCALES[scaleType].length)];
		}
		return getRanItemFromArray(MAJOR_SCALES[scaleType]);
	}
	
	public static int getRanPN() {
	    Random random = new Random();
	    if(random.nextBoolean()) return 1;
	    else return -1;
	}
	
	public static int getRanItemFromArray(int[] array) {
	    int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}
	
	static void compose(MidiFileBuilder mBuilder,
			int[][] progression, int[] rythmn, int[] drythmn, 
			int ticks, int loopTimes, int unitLength) {
		// C major
		if (rythmn.length != unitLength || drythmn.length != unitLength) throw new IllegalArgumentException();
		Progression prog = new Progression();
		for (int i = 0; i < progression[0].length; i++) {
			prog.addChord(progression[0][i],progression[1][i],progression[2][i]);
		}
		int realTime = 0;
		
		for (int r = 0; r < loopTimes; r++){
			//For each root Notes

			int note = 0;
			for (int i = 0; i < prog.getRootNotes().size(); i++){
				int time = 0;
				for (int c : Composer.getChordInversion(prog.getRootNote(i),//RootNote
						prog.getChordType(i),//Chord Type
						Composer.getRanItemFromArray(new int[]{1,2,3})//Inversion
						)) {
					//Add each notes in the chord
					for (long t = 0; t < ticks*unitLength; t +=ticks){
						if (drythmn[(int)(t/ticks)] != 0 && 
								drythmn[(int)(t/ticks)] != 2)mBuilder.addNote(new Note(C+c-12, (int)(realTime + t), ticks),5);
					}
					note = c;
				}
				mBuilder.addNote(new Note(36, 9, 100, realTime, ticks*unitLength),9);
				time+=ticks; realTime+=ticks;
				
				//Drums
				while (time < ticks * unitLength){
					switch (drythmn[time/ticks]) {
					case 2:
						mBuilder.addNote(new Note(42, 9, 100, realTime, ticks),9);
						break;
					case 1:
						mBuilder.addNote(new Note(36, 9, 100, realTime, ticks),9);
						break;
					case 3:
						mBuilder.addNote(new Note(38, 9, 100, realTime, ticks),9);
						break;
					}
					
					if (time == ticks * unitLength / 2)
						mBuilder.addNote(//Chord notes
								new Note(C+Composer.getRanItemFromArray(Composer.getChordInversion(prog.getRootNote(i), 
										prog.getChordType(i), Composer.getRanItemFromArray(new int[]{1,2,3}))) , realTime, ticks),0);
					else if (//rythmn[time/ticks] == 1
							new Random().nextBoolean()) {
						note = Composer.getTransNote(prog.getScaleType(i), note);
						mBuilder.addNote(new Note(C+prog.getRootNote(i)//Add offset
							+(note)//get Note from scale type
							, realTime, ticks),0);
					}
					
					realTime+=12;
					time+=12;
				}
			}
		}
	}
}
