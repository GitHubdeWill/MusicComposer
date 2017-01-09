package MidiUtil;

import java.util.ArrayList;

public class Progression
{
	ArrayList<Integer> rootNotes;
	ArrayList<Integer> chordTypes;
	ArrayList<Integer> scaleTypes;
	public Progression()
	{
		this.rootNotes = new ArrayList<>();
		this.chordTypes = new ArrayList<>();
		this.scaleTypes = new ArrayList<>();
	}
	public Progression(ArrayList<Integer> rootNotes, ArrayList<Integer> chordType, ArrayList<Integer> scaleType)
	{
		this.rootNotes = rootNotes;
		this.chordTypes = chordType;
		this.scaleTypes = scaleType;
	}
	public void addChord (int root, int chord, int scale) {
		this.rootNotes.add(root);
		this.chordTypes.add(chord);
		this.scaleTypes.add(scale);
	}
	public ArrayList<Integer> getRootNotes()
	{
		return rootNotes;
	}
	public ArrayList<Integer> getChordTypes()
	{
		return chordTypes;
	}
	public ArrayList<Integer> getScaleTypes()
	{
		return scaleTypes;
	}
	public int getRootNote(int index)
	{
		return rootNotes.get(index);
	}
	public int getChordType(int index)
	{
		return chordTypes.get(index);
	}
	public int getScaleType(int index)
	{
		return scaleTypes.get(index);
	}
}
