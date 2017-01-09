package MidiUtil;

import java.util.ArrayList;
import java.util.Collections;

public class Chord
{
	public final static int[][] CHORDS= {
			{0,4,7}, //MAJ
			{0,3,7}, //MIN
			{0,4,7,11}, //MAJ7
			{0,4,7,10}, //7
			{0,3,7,10} //MIN7
	};
	public final static int[][] ADV_CHORDS= {	
			{0,4,6,10}, //7-5
			{0,4,8}, //AUG
			{0,4,8,10}, //AUG7
			{0,3,6}, //DIM
			{0,3,6,9}, //DIM7
			{0,4,7,9}, //6
			{0,3,7,9},//MIN6
			{0,2,4,7}, //SUS2
			{0,4,5,7}, //SUS4
			//Advanced
			{0,6,8},//AUG6
			{0,4,8,11},//AUGMAJ7
			{0,3,6,11},//DIMMAJ7
			{0,3,6,10},//HALFDIM7
			{0,3,4,7,10},//7+9
			{0,2,4,7,9},//6/9
			{0,2,3,7,9},//MIN6/9
			{0,2,3,7,10},//MIN9
			{0,2,4,7,10},//9
			{0,2,4,8,10},//9+5
			{0,2,4,6,10},//9-5
			{0,2,4,6,7,10},//AUG11
			{0,2,4,5,7,11},//11
			{0,2,3,5,7,10},//MIN11
			{0,4,6,8,11},//#7 11
			{0,2,4,6,7,9,11},//13
			{0,2,3,5,7,9,10},//MIN13
			
			{0,5,6,7},//DREAM
			{0,1,4,7,9},//ELEKTRA
			{0,4,6,7,11},//LYDIAN
			{0,1,3,5,6,10}, //MAGIC
			{0,2,4,6,9,10}, //MYSTIC
			
	};
	public static final String[] CHORD_NAMES = {
			"MAJ","MIN","MAJ7","7","MIN7"
	};
	public static final String[] ADV_CHORD_NAMES = {
			"7-5","AUG","AUG7","DIM","DIM7","6","MIN6","SUS2","SUS4",
			"AUG6","AUGMAJ7","DIMMAJ7","HALFDIM7","7/9","6/9","MIN6/9","MIN9","9","9+5","9-5","AUG11","11","MIN11","#7 11","13","MIN13",
			"DREAM","ELEKTRA","LYDIAN","MAGIC","MYSTIC"
	};

	public static final String[] MIDI_NAMES = {
			"C","#C/bD","D","#D/bE","E","F","#F/bG","G","#G/bA","A","#A/bB","B"
	};
	
	
	ArrayList<Integer> notes;
	ArrayList<Integer> baseNotes;

	public Chord()
	{
		this.notes = new ArrayList<>();
		this.baseNotes = new ArrayList<>();
	}
	
	public ArrayList<Integer> getNotes()
	{
		sort();
		return notes;
	}

	public ArrayList<Integer> getBaseNotes()
	{
		sort();
		return baseNotes;
	}

	public void addNote(int note)
	{
		if (!notes.contains(note))
			this.notes.add(note);
		if (!baseNotes.contains(note%12))baseNotes.add(note%12);
		sort();
	}
	
	private int chordFinder () {
		sort();
		if (baseNotes.size() <3) return -1;
//		System.out.println(baseChord.toString());
		int cNum = -1;
		for (int tran = 0; tran < 12; tran++){	
			for (int i = 0; i < CHORDS.length; i++) {
				ArrayList<Integer> curChord = new ArrayList<>();
				for (Integer e : CHORDS[i]) {
					curChord.add((e+tran)%12);
				}
							
				
				int found = 3;
				for (int j = 0; j < baseNotes.size(); j++) {
					if (!curChord.contains(baseNotes.get(j))) {
						found -= 1;
						break;
					}
				}
				for (int j = 0; j < curChord.size(); j++) {
					if (!baseNotes.contains(curChord.get(j))) {
						found -= 1;
						break;
					}
				}
				if (found==3) {
					cNum = i + tran*10;
					return cNum;
				} else if (found > 1) {
					cNum = i + tran*10;
				}
			}
		}
		
		return cNum;
	}
	
	private int advChordFinder () {
		sort();
		if (baseNotes.size() <3) return -1;
//		System.out.println(baseChord.toString());
		int cNum = -1;		
		for (int tran = 0; tran < 12; tran++){
			
			for (int i = 0; i < ADV_CHORDS.length; i++) {
				ArrayList<Integer> curChord = new ArrayList<>();
				for (Integer e : ADV_CHORDS[i]) {
					curChord.add((e+tran)%12);
				}
							
			
				
				int found = 3;
				for (int j = 0; j < baseNotes.size(); j++) {
					if (!curChord.contains(baseNotes.get(j))) {
						found -= 1;
						break;
					}
				}
				for (int j = 0; j < curChord.size(); j++) {
					if (!baseNotes.contains(curChord.get(j))) {
						found -= 1;
						break;
					}
				}
				if (found==3) {
					cNum = i + tran*10;
					return cNum;
				} else if (found > 1) {
					cNum = i + tran*10;
				}
			}
		}
		return cNum;
	}
	
	private void sort () {
		Collections.sort(notes);
		Collections.sort(baseNotes);
		
	}
	
	public String toString() {
		int chordS = chordFinder();
		int chordSA = advChordFinder();
		String ret = "";
		if (chordS>=0) 
			ret += "Chord "+
					MIDI_NAMES[(chordS/10)%12]+
//					getRoot()/12+
					" "+CHORD_NAMES[chordS%10];
		else if (chordSA>=0) 
			ret += "Chord "+
			MIDI_NAMES[(chordSA/10)%12]+
//			getRoot()/12+
			" "+ADV_CHORD_NAMES[chordSA%10];
		
		return notes.toString()+"->"+baseNotes.toString()+"->"+ret;
	}
}
