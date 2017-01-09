package MidiUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class MidiFileAnalysis
{
	public static void readFile (String fileName) {
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
	
	public void transformFile (String fileName, String targetFileName) {
		
	}
	
	private static void out (String str) {
		System.out.println(str);
	}
}
