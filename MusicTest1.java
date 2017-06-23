import javax.sound.midi.*;
public class MusicTest1 {
    pubic void play() {
	Sequencer sequencer = MidiSystem.getSequencer();

	System.out.println("We got a sequencer");
    } // close play

    public static void main (String[] args) {
	MusicTest1 mt = new MusicTest1();
	mt.play;
    }// close main
}// close class
