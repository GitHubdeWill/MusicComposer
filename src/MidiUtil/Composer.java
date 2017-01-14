package MidiUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class Composer
{

	final static int C = 60;
	public static final int[] 	
				CHROMATIC_SCALE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
				
				IONIAN_SCALE = {0, 2, 4, 7, 9, 11},
				DORIAN_SCALE = {0, 2, 3, 5, 7, 10},
				PHRYGIAN_SCALE = {0, 3, 5, 7, 10},
				LYDIAN_SCALE = {0, 2, 4, 6, 7, 9, 11},
				MIXOLYDIAN_SCALE = {0, 2, 4, 7, 9, 10},
				AEOLION_SCALE = {0, 2, 3, 5, 7, 10},
				LOCRIAN_SCALE = {0, 3, 5, 6, 8, 10},
				
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
	public final static int[][] SCALE_TYPE = {
			{0,3},//MAJ 0
			{1,5},//MIN 1
			{4,8,9},//Mix7 2
			{10},//AUG7 3
			{11},//DIM7 4
			{12},//HAFDIM7 5
			{13},//7 6
			{14},//MIX7SUS4 7
			{15},//AUGMAJ7 8
			{16},//MAJ6 9
			{17},//MIN6 10
			{7},//BLUES 11
	};
	public final static int[][] MAJOR_SCALES = {
			IONIAN_SCALE,
			DORIAN_SCALE,
			PHRYGIAN_SCALE,
			LYDIAN_SCALE,
			MIXOLYDIAN_SCALE,
			AEOLION_SCALE,
			LOCRIAN_SCALE,

			BLUES_SCALE,
			//Mix
			{0,1,3,4,6,7,9,10},
			{0,1,3,4,6,8,10},
			//AUG7
			{0,2,4,6,8,10},
			//DIM7
			{0,2,3,5,6,8,9,11},
			//HALFDIM7
			{0,1,3,5,6,8,10},
			//7
			{0,2,3,5,6,7,9,11},
			//G7sus4
			{0,2,5,7,9,10},
			//AUGMAJ7
			{0,2,4,6,8,9,11},
			//MAJ6
			IONIAN_SCALE,
			//MIN6
			{0,2,3,5,6,7,9,11},
			
			
			
	};
	
	
	public final static int[][] SIMPLE_BLUES_PROGRESSION = {
			{0,0,0,0,5,5,0,0,7,5,0,0},//Root Note
			{3,3,3,3,3,3,3,3,3,3,3,3},//Chord
			{12,12,12,12,12,12,12,12,12,12,12,12}//Scale Type
	};
	

	public final static int[] SIMPLE_RYTHMN = {1,0,1,0,1,1,1,0,1,0,1,1,0,1,1,0};
	
	public final static int[] SIMPLE_DRYTHMN = {1,0,2,0,3,0,2,1,1,0,2,0,3,0,2,2};
//	public final static int[] SIMPLE_DRYTHMN = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
	
	
	//0-11, 0 - 12; 0,1,2,3;
	public static int[] getChordInversion (int note, int chord, int inversion) {
		if (inversion > CHORDS[chord].length) return null;
		int[] ret = new int[CHORDS[chord].length];
		int index = 0;
		for (int i = inversion; i < CHORDS[chord].length; i++) {
			ret[index++] = CHORDS[chord][i]+note;
		}
		for (int i = 0; i < inversion; i++) {
			ret[index++] = CHORDS[chord][i]+note;
		}
		return ret;
	}
	
	public static int getTransNote (int [][] scale_table ,int scaleType, int note) {
//		return getRanItemFromArray(MAJOR_SCALES[scaleType]);
		int index = getRanItemFromArray(SCALE_TYPE[scaleType]);
		for (int i = 0; i < scale_table[index].length; i++) {
			if (scale_table[index][i] == note) 
				return scale_table[index][(Math.abs(i+getRanPN())%scale_table[index].length)];
		}
		return getRanItemFromArray(scale_table[index]);
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
	
	public static void composeChord(MidiFileBuilder mBuilder, long ticks, String fileName) {
		String	strSource = fileName;
		String	strFilename = null;
		MidiFileFormat	fileFormat = null;
		Sequence	sequence = null;
		Track[] tracks = null;
		try {//Read File
			File file = new File(strSource);
			fileFormat = MidiSystem.getMidiFileFormat(file);
			strFilename = file.getCanonicalPath();
			sequence = MidiSystem.getSequence(file);
			tracks = sequence.getTracks();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		if (fileFormat == null) {
			out("File Format Error");
			return;
		}
		
		ArrayList<HashMap<Long, Chord>> hashMs = new ArrayList<>();
		
		out("General--------------------------------------------------------------------");
		out("Source: " + strFilename);
		out("Midi File Type: " + fileFormat.getType());
		String	strResolutionType = null;
		if (fileFormat.getDivisionType() == Sequence.PPQ)
			strResolutionType = " ticks per beat";
		else strResolutionType = " ticks per frame";
		out("Resolution: " + fileFormat.getResolution() + strResolutionType);
		String	strFileLength = null;
		if (fileFormat.getByteLength() != MidiFileFormat.UNKNOWN_LENGTH)
			strFileLength = "" + fileFormat.getByteLength() + " bytes";
		else strFileLength = "unknown";
		out("Length: " + strFileLength);
		out("-Sequence------------------------------------------------------------------");
		out("-Length: "+sequence.getTickLength()+" Ticks");
		out("-Length in Seconds: "+sequence.getMicrosecondLength()/1000+" s");
		
		for (int track = 0; track < tracks.length; track++){
			hashMs.add(new HashMap<>());
			if (track == 9) continue;
//			out("**Track"+track+"-------------------------------------------------------------------");
			for (int j = 0; j < tracks[track].size(); j++) {
				MidiEvent miet= tracks[track].get(j);
//				out("---Midevt "+j+"-----------------------------------------------------------------");
//				out("---Ticks: "+miet.getTick());
//				out("---Message: ");
//				for (byte b : miet.getMessage().getMessage()){
//					out("----: "+b);
//				}
				int note = miet.getMessage().getMessage()[1];
//				out("---Note: "+note);
				if (miet.getMessage().getMessage().length>=3 && miet.getMessage().getMessage()[2] >0){
					hashMs.get(track).putIfAbsent(miet.getTick(), new Chord());
					hashMs.get(track).get(miet.getTick()).addNote(note);
				}
			}
//			out(hashMs.get(track).toString());
		}

		TreeMap<Long, Chord> map = new TreeMap<>();
		for (HashMap<Long, Chord> hm : hashMs) {
			for (Entry<Long, Chord> e : hm.entrySet()) {
				if (e.getValue().getBaseNotes().size()>=1) {
					map.put(e.getKey(), e.getValue());
				}
			}
		}
		SortedSet<Long> keys = new TreeSet<Long>(map.keySet());
		for (Long key : keys) { 
		   Chord value = map.get(key);
		   out(key+">>"+value.toString());
		   
		}
	}
	
	static void compose(MidiFileBuilder mBuilder,
			int[][] progression, int[][] scaleTable, int[] rythmn, int[] drythmn, 
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
						Composer.getRanItemFromArray(new int[]{0,1,2})//Inversion
						)) {
//					//Add each notes in the chord
					for (long t = 0; t < ticks*unitLength; t +=ticks){
						if (drythmn[(int)(t/ticks)] != 0 && 
								drythmn[(int)(t/ticks)] != 2)mBuilder.addNote(new Note(C+c-12, 5, 100, (int)(realTime + t), ticks),5);
					}
					note = c;
				}
				mBuilder.addNote(new Note(C+prog.getRootNote(i)-12, 2, 100, (realTime), ticks*8),2);
				mBuilder.addNote(new Note(C+prog.getRootNote(i)-12, 2, 100, (realTime+ticks*12), ticks*4),2);
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
										prog.getChordType(i), Composer.getRanItemFromArray(new int[]{1,2,3}))), 0, 100, realTime, ticks),0);
					else if (//rythmn[time/ticks] == 1
							new Random().nextBoolean()
//							true
							) {
						note = Composer.getTransNote(scaleTable, prog.getScaleType(i), note);
						mBuilder.addNote(new Note(C+prog.getRootNote(i)//Add offset
							+(note)//get Note from scale type
							, 0, 100-Math.abs(realTime%ticks)
							, realTime, ticks*2),0);
					}
					
					realTime+=12;
					time+=12;
				}
			}
			for (int c : Composer.getChordInversion(prog.getRootNote(0),//RootNote
					prog.getChordType(0),//Chord Type
					Composer.getRanItemFromArray(new int[]{0,1,2})//Inversion
					)) {
//				//Add each notes in the chord
					mBuilder.addNote(new Note(C+c-12, 5, 100, (int)(realTime), ticks*8),5);
			}		
		}
	}
	
	private static void out (String str) {
		System.out.println(str);
	}
}
