package MidiUtil;

import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.*;

public class MidiFileBuilder
{
	File file;
	Sequence sequence;
	Track[] tracks = new Track[16];
	
	public MidiFileBuilder (int ticks, String file) {
		this.file = new File(file);
		try
		{
			sequence = new Sequence(Sequence.PPQ, ticks);//Ticks per beat
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		for (int i = 0; i < 16; i++){
			tracks[i] = sequence.createTrack();
		}
		init();
	}
	
	private void init () {
		try {
		//****  General MIDI sysex -- turn on General MIDI sound set  ****
		byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
		SysexMessage sm = new SysexMessage();
		sm.setMessage(b, 6);
		MidiEvent me = new MidiEvent(sm,(long)0);
		tracks[0].add(me);
		
		//****  set omni on  ****
		ShortMessage mm = new ShortMessage();
//		mm.setMessage(0xB0, 0x7D,0x00);
//		me = new MidiEvent(mm,(long)0);
//		tracks[0].add(me);

//		****  set poly on  ****
		mm = new ShortMessage();
		mm.setMessage(0xB0, 0x7F,0x00);
		me = new MidiEvent(mm,(long)0);
		tracks[0].add(me);
		
		mm = new ShortMessage();
	    mm.setMessage(ShortMessage.PROGRAM_CHANGE, 0x00, 0x00);
	    me = new MidiEvent(mm,(long)0);
	    tracks[0].add(me);
	    
	    mm = new ShortMessage();
	    mm.setMessage(0xC0, 0x02, 0x27, 0x00);
	    me = new MidiEvent(mm,(long)0);
	    tracks[0].add(me);
	    
	    mm = new ShortMessage();
	    mm.setMessage(0xC0, 0x05, 0x19, 0x00);
	    me = new MidiEvent(mm,(long)0);
	    tracks[0].add(me);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void writeToFile() {
		try {
			MidiSystem.write(sequence,1,file);
		} catch(Exception e) {
			System.out.println("Exception caught " + e.toString());
		} //catch
	}
		
	public void addNote(Note n, int track){
			tracks[track].add(createNoteEvent(ShortMessage.NOTE_ON, n.note, n.getVelocity(), n.getStart_p(), n.getChannel()));
			tracks[track].add(createNoteEvent(ShortMessage.NOTE_OFF, n.note, 0, n.getEnd_p(), n.getChannel()));
	}
	
	public void addNote(ArrayList<Note> notes, int track){
		for (Note n : notes) {
			tracks[track].add(createNoteEvent(ShortMessage.NOTE_ON, n.note, n.getVelocity(), n.getStart_p(), n.getChannel()));
			tracks[track].add(createNoteEvent(ShortMessage.NOTE_OFF, n.note, 0, n.getEnd_p(), n.getChannel()));
		}
	}
	
	private static MidiEvent createNoteEvent(int nCommand, int nKey, int nVelocity, long lTick, int channel)
	{
		ShortMessage message = new ShortMessage();
		try {
		message.setMessage(nCommand, channel, nKey, nVelocity);
		} catch (InvalidMidiDataException e) {
		e.printStackTrace();
		System.exit(1);
		}
		MidiEvent event = new MidiEvent(message,
				  lTick);
		return event;
	}
}
