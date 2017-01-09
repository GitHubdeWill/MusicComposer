package MidiUtil;

import java.util.*;

public class Launcher
{	
	
	final static int[] C_MAJOR = {1,5,8};
	final static int[] C_MAJOR7 = {1,5,8,12};
	final static int[] D_MINOR = {-2,3,6};
	final static int[] D_MINOR7 = {1,3,6,10};
	final static int[] E_MINOR = {5,8,12};
	final static int[] E_MAJOR = {5,9,12};
	final static int[] F_MINOR = {1,6,9};
	final static int[] F_MAJOR = {1,6,10};
	final static int[] G_MAJOR = {-4,0,3};
	final static int[] G_7 = {-4,3,6,12};
	final static int[] A_MINOR = {-2,1,5};
	
	
	public static void main(String[] args)
	{
		String file = "D:\\Drive\\temp.mid";
		String[] info = {file};
		ArrayList<Note> notes = new ArrayList<>();
		notes = addNotes(notes);
		CreateSequence.create(file, notes);
		MidiFileInfo.readInfo(info);
		SimpleMidiPlayer.play(file);
	}
	
	private static int[] getRealOffSet(int[] chord){
		int[] rChord = new int[chord.length];
		for (int i = 0; i < chord.length; i++){
			rChord[i] = chord[i]-1;
		}
		return rChord;
	}
	
	public static int getRanPN() {
	    Random random = new Random();
	    if(random.nextBoolean()) return 1;
	    else return -1;
	}
	
	public static int getRandomNote(int[] array) {
	    int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}

	
	private static int getTransNote(int i) {
		if ((i - 4)%12==0)
			if (getRanPN()>0) {
				return i+1;
			} else {
				return i-2;
			}
		
		if ((i - 5)%12==0) 
			if (getRanPN()>0) {
				return i+2;
			} else {
				return i-1;
			}
		if ((i + 1)%12==0) 
			if (getRanPN()>0) {
				return i+1;
			} else {
				return i-2;
			}
		if (i % 12 == 0) 
			if (getRanPN()>0) {
				return i+2;
			} else {
				return i+4;
			}
		if (i % 8 == 0) 
			if (getRanPN()>0) {
				return i+1;
			} else {
				return i-3;
			}
		return i + getRanPN()*2;
	}
	
	private static ArrayList<Note> addNotes(ArrayList<Note> notes) {
		// C major
		int realTime = 1;
		int[][] progression = {C_MAJOR, A_MINOR, F_MAJOR, G_MAJOR};
		int[][] progression2 = {C_MAJOR, E_MINOR, D_MINOR, G_MAJOR};
		int[][] progression3 = {C_MAJOR7, F_MINOR, D_MINOR7, G_7};
		for (int r = 0; r < 10; r++){
			for (int[] iss : progression){
				int time = 1;
				for (int i : getRealOffSet(iss)) {
					notes.add(new Note(60+i, realTime, 8));
				}
				int note = getRandomNote(getRealOffSet(iss));
				time++; realTime++;
				while (time < 5){
					note = getTransNote(note);
					notes.add(new Note(60+note, realTime++, 1));
					time++;
				}
				note = getRandomNote(getRealOffSet(iss));
				while (time < 9){
					note = getTransNote(note);
					notes.add(new Note(60+note, realTime++, 1));
					time++;
				}
			}
			for (int[] iss : progression2){
				int time = 1;
				for (int i : getRealOffSet(iss)) {
					notes.add(new Note(60+i, realTime, 8));
				}
				int note = getRandomNote(getRealOffSet(iss));
				time++; realTime++;
				while (time < 5){
					note = getTransNote(note);
					notes.add(new Note(60+note, realTime++, 1));
					time++;
				}
				note = getRandomNote(getRealOffSet(iss));
				while (time < 9){
					note = getTransNote(note);
					notes.add(new Note(60+note, realTime++, 1));
					time++;
				}
			}
			for (int[] iss : progression3){
				int time = 1;
				for (int i : getRealOffSet(iss)) {
					notes.add(new Note(60+i, realTime, 8));
				}
				int note = getRandomNote(getRealOffSet(iss));
				time++; realTime++;
				while (time < 5){
					note = getTransNote(note);
					notes.add(new Note(60+note, realTime++, 1));
					time++;
				}
				note = getRandomNote(getRealOffSet(iss));
				while (time < 9){
					note = getTransNote(note);
					notes.add(new Note(60+note, realTime++, 1));
					time++;
				}
			}
		}
		return notes;
	}
}
