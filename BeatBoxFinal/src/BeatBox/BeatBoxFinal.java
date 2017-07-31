import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.event.*;

public class BeatBoxFinal {
  JFrame theFreame;
  JPanel mainPanel;
  JList incomingList;
  JTextField userMessage;
  ArrayList<JCheckBox> checkboxList;
  int nextNum;
  Vector<String> listVector = new Vector<String>();
  String userName;
  ObjectOutputStream out;
  ObjectInputStream in;
  HashMap<String, boolean[]> otherSeqMap = new HashMap<String, boolean[]>();

  Sequencer sequencer;
  Sequence sequence;
  Sequence mySequence = null;
  Track track;
  
  String[] insturmentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic snare", "Crash Cymbal",
      "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", 
      "High Agogo", "Open Hi Conga"};
  
  int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
  
  public static void main (String[] args) {
      new BeatBoxFinal().startUp(args[0]); // the args area for user name needs to be removed and replaced with a prompt 
      
  }
  // this is the method to improve to prompt for user name 
  // set up a gui text pop up that collects the name and saves to the variable
  public void startUp(String name) {
      userName = name;
      // add server ip = ip and collect ip from the user in the user name area as well
      // open connection to the server
      try {
          Socket sock  = new Socket("45.77.72.121", 4242);
          out = new ObjectOutputStream(sock.getOutputStream());
          in = new ObjectInputStream(sock.getInputStream());
          Thread remote = new Thread(new RemoteReader());
          remote.start();
      } catch (Exception ex) {
          System.out.println("couldn't connect - play alone");
          
      }
      setUpMidi();
      buildGUI();
      
  } // close startup
  
  public void buildGUI() {
      theFrame = new JFrame("BeatBox");
      BorderLayout layout = new BorderLayout();
      JPanel background = new JPanel(layout);
      background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
      checkboxList = new ArrayList<JCheckBox>();
      
      Box buttonBox = new Box(BoxLayout.Y_axIS);
      JButton start = new JButton("Start");
      start.addActionListener(new MyStartListener());
      buttonBox.add(start);
      
      JButton stop = new JButton ("Stop");
      stop.addActionListener(new MyStopListener());
      buttonBox.add(stop);
      
      JButton upTempo = new JButton("Tempo Up");
      upTempo.addActionListener(new MyUpTempoListener());
      buttonBox.add(upTempo);
      
      JButton downTempo = new JButton("Tempo Down");
      downTempo.addActionListener(new MyDownTempoListener());
      buttonBox.add(downTempo);
      
      JButton sendIt = new JButton("send it");
      sendIt.addActionListener(new MySendItListener());
      buttonBox.add(sendIt);
      
      userMessage = new JTextField();
      buttonBox.add(userMessage);
  }
  
  
  }


