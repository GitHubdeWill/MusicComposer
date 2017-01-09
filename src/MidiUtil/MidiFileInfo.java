package MidiUtil;
/*
 *	MidiFileInfo.java
 *
 *	This file is part of jsresources.org
 */

/*
 * Copyright (c) 1999, 2000 by Matthias Pfisterer
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
import java.io.InputStream;
import java.io.IOException;

import java.net.URL;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;



public class MidiFileInfo {
	
	public static void readInfo(String[] args)
	{
		boolean	bCheckSequence = false;
		int	nCurrentArg = 0;
		while (nCurrentArg < args.length){
			bCheckSequence = true;
			nCurrentArg++;
		}
		String	strSource = args[nCurrentArg - 1];
		String	strFilename = null;
		MidiFileFormat	fileFormat = null;
		Sequence	sequence = null;
		try {
			File	file = new File(strSource);
			fileFormat = MidiSystem.getMidiFileFormat(file);
			strFilename = file.getCanonicalPath();
			if (bCheckSequence)
				sequence = MidiSystem.getSequence(file);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		/*
		 *	And now, we output the data.
		 */
		if (fileFormat == null)
		{
			out("Cannot determine format");
		}
		else
		{
			out("---------------------------------------------------------------------------");
			out("Source: " + strFilename);
			out("Midi File Type: " + fileFormat.getType());

			float	fDivisionType = fileFormat.getDivisionType();
			String	strDivisionType = null;
			if (fDivisionType == Sequence.PPQ)
			{
				strDivisionType = "PPQ";
			}
			else if (fDivisionType == Sequence.SMPTE_24)
			{
				strDivisionType = "SMPTE, 24 frames per second";
			}
			else if (fDivisionType == Sequence.SMPTE_25)
			{
				strDivisionType = "SMPTE, 25 frames per second";
			}
			else if (fDivisionType == Sequence.SMPTE_30DROP)
			{
				strDivisionType = "SMPTE, 29.97 frames per second";
			}
			else if (fDivisionType == Sequence.SMPTE_30)
			{
				strDivisionType = "SMPTE, 30 frames per second";
			}

			out("DivisionType: " + strDivisionType);

			String	strResolutionType = null;
			if (fileFormat.getDivisionType() == Sequence.PPQ)
			{
				strResolutionType = " ticks per beat";
			}
			else
			{
				strResolutionType = " ticks per frame";
			}
			out("Resolution: " + fileFormat.getResolution() + strResolutionType);

			String	strFileLength = null;
			if (fileFormat.getByteLength() != MidiFileFormat.UNKNOWN_LENGTH)
			{
				strFileLength = "" + fileFormat.getByteLength() + " bytes";
			}
			else
			{
				strFileLength = "unknown";
			}
			out("Length: " + strFileLength);

			String	strDuration = null;
			if (fileFormat.getMicrosecondLength() != MidiFileFormat.UNKNOWN_LENGTH)
			{
				strDuration = "" + fileFormat.getMicrosecondLength() + " microseconds)";
			}
			else
			{
				strDuration = "unknown";
			}
			out("Duration: " + strDuration);

			if (bCheckSequence)
			{
				out("[Sequence says:] Length: " + sequence.getTickLength() + " ticks (= " + sequence.getMicrosecondLength() + " us)\n");
			}
			out("---------------------------------------------------------------------------");
		}
		
	}


	private static void out(String strMessage)
	{
		System.out.println(strMessage);
	}
}



/*** MidiFileInfo.java ***/