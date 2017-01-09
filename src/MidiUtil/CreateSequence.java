package MidiUtil;
/*
 *	CreateSequence.java
*
*	This file is part of jsresources.org
*/

/*
* Copyright (c) 2000 by Matthias Pfisterer
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
* - Redistributions of source code must retain the above copyright notice,
*   this list of conditions and the following disclaimer.
* - Redistributions in binary form must reproduce the above copyright
*   notice, this list of conditions and the following disclaimer in the
*   documentation and/or other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
* FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
* COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
* HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
* STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
* OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/*
|<---            this code is formatted to fit into 80 columns             --->|
*/

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.*;


/**	<titleabbrev>CreateSequence</titleabbrev>
	<title>Creating a Sequence</title>

	<formalpara><title>Purpose</title>
	<para>
	Shows how to construct a Sequence object with a Track and MidiEvents
	in memory and save it to a Standard MIDI File (SMF).
	</para></formalpara>

	<formalpara><title>Usage</title>
	<para>
	<cmdsynopsis>
	<command>java CreateSequence</command>
	<arg choice="plain"><replaceable class="parameter">midi_file</replaceable></arg>
	</cmdsynopsis>
	</para>
	</formalpara>

	<formalpara><title>Parameters</title>
	<variablelist>
	<varlistentry>
	<term><option><replaceable class="parameter">midi_file</replaceable></option></term>
	<listitem><para>the name of the file to save to as a Standard Midi File.</para></listitem>
	</varlistentry>
	</variablelist>
	</formalpara>

	<formalpara><title>Bugs, limitations</title>
	<para>None
	</para>
	</formalpara>

	<formalpara><title>Source code</title>
	<para>
	<ulink url="CreateSequence.java.html">CreateSequence.java</ulink>
	</para>
	</formalpara>
	  
*/
public class CreateSequence
{
	/*	This velocity is used for all notes.
	 */
	private static final int	VELOCITY = 64;


	public static void create(String arg0, ArrayList<Note> notes)
	{
		File outputFile = new File(arg0);
		Sequence	sequence = null;
		try
		{
			sequence = new Sequence(Sequence.PPQ, 1);
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		/* Track objects cannot be created by invoking their constructor
		   directly. Instead, the Sequence object does the job. So we
		   obtain the Track there. This links the Track to the Sequence
		   automatically.
		*/
		
		Track	track = sequence.createTrack();
		
		

		try {
			byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
			SysexMessage sm = new SysexMessage();
			sm.setMessage(b, 6);
			MidiEvent me = new MidiEvent(sm,(long)0);
			track.add(me);

			MetaMessage mt = new MetaMessage();
	        byte[] bt = {0x02, (byte)0x00, 0x00};
			mt.setMessage(0x51 ,bt, 3);
			me = new MidiEvent(mt,(long)0);
			track.add(me);
			
			
			
			ShortMessage mm = new ShortMessage();
			mm.setMessage(0xC0, 0, 0);
			me = new MidiEvent(mm,(long)0);
			track.add(me);
			mm = new ShortMessage();
			mm.setMessage(0xC9, 118, 0);
			me = new MidiEvent(mm,(long)0);
			track.add(me);
			

			//****  set poly on  ****
					mm = new ShortMessage();
					mm.setMessage(0xB0, 0x7F,0x00);
					me = new MidiEvent(mm,(long)0);
					track.add(me);
			

			mm = new ShortMessage();
			for (int i = 1; i < 8*4*3*10; i+=8){
				mm.setMessage(0x99,36,127);
				me = new MidiEvent(mm,(long)i);
				track.add(me);
//				mm.setMessage(0x89,35,40);
//				me = new MidiEvent(mm,(long)i+12);
//				track.add(me);
				mm = new ShortMessage();
				
				mm.setMessage(0x99,42,100);
				me = new MidiEvent(mm,(long)i+2);
				track.add(me);
				
				mm = new ShortMessage();
				mm.setMessage(0x99,38,100);
				me = new MidiEvent(mm,(long)i+4);
				track.add(me);
				
				mm = new ShortMessage();
				mm.setMessage(0x99,42,100);
				me = new MidiEvent(mm,(long)i+6);
				track.add(me);

				mm = new ShortMessage();
//				mm.setMessage(0x89,42,100);
//				me = new MidiEvent(mm,(long)i+3);
//				track.add(me);
//				mm.setMessage(0x89,38,100);
//				me = new MidiEvent(mm,(long)i+5);
//				track.add(me);
//				mm.setMessage(0x89,42,100);
//				me = new MidiEvent(mm,(long)i+7);
//				track.add(me);
			}
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		

		
		for (Note n : notes) {
			track.add(createNoteOnOffEvent(n.getNote(), n.getStart_p(), 1));
			track.add(createNoteOnOffEvent(n.getNote(), n.getEnd_p(), 0));
		}


		/* Now we just save the Sequence to the file we specified.
		   The '0' (second parameter) means saving as SMF type 0.
		   Since we have only one Track, this is actually the only option
		   (type 1 is for multiple tracks).
		*/
		try
		{
			MidiSystem.write(sequence, 0, outputFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}



	private static MidiEvent createNoteOnOffEvent(int nKey, long lTick, int type)
	{
		if (type == 1)
			return createNoteEvent(ShortMessage.NOTE_ON,
								   nKey,
								   VELOCITY,
								   lTick);
		else if (type == 0)
			return createNoteEvent(ShortMessage.NOTE_OFF,
								   nKey,
								   0,
								   lTick);
		return null;
	}

	private static MidiEvent createNoteEvent(int nCommand,
											 int nKey,
											 int nVelocity,
											 long lTick)
	{
		ShortMessage	message = new ShortMessage();
		try
		{
			message.setMessage(nCommand,
							   0,	// always on channel 1
							   nKey,
							   nVelocity);
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		MidiEvent	event = new MidiEvent(message,
										  lTick);
		return event;
	}


	private static void out(String strMessage)
	{
		System.out.println(strMessage);
	}
}



/*** CreateSequence.java ***/