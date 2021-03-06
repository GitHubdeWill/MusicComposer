package MidiUtil;

public class Note
{

	int note;
	int channel = 0;
	int velocity = 70;
	long start_p;
	long length;
	long end_p;
	public Note(int note, int channel, int velocity, int start_p, int length)
	{
		this.note = note;
		this.channel = channel;
		this.velocity = velocity;
		this.start_p = start_p;
		this.length = length;
		this.end_p = start_p + length;
	}
	
	public int getNote()
	{
		return note;
	}
	public void setNote(int note)
	{
		this.note = note;
	}
	public long getStart_p()
	{
		return start_p;
	}
	public void setStart_p(long start_p)
	{
		this.start_p = start_p;
	}
	public long getLength()
	{
		return length;
	}
	public void setLength(long length)
	{
		this.length = length;
	}
	public long getEnd_p()
	{
		return end_p;
	}
	public void setEnd_p(long end_p)
	{
		this.end_p = end_p;
	}
	public int getChannel()
	{
		return channel;
	}
	public void setChannel(int channel)
	{
		this.channel = channel;
	}
	public int getVelocity()
	{
		return velocity;
	}
	public void setVelocity(int velocity)
	{
		this.velocity = velocity;
	}
	
}
