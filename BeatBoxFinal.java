package BeatBox;



import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.event.*;

public class BeatBoxFinal {
  JFrame theFrame;
  JFrame frame;
  JPanel mainPanel;
  JList incomingList;
  JTextField userMessage;
  ArrayList<JCheckBox> checkboxList;
  int nextNum;
  Vector<String> listVector = new Vector<>();
  JTextField userName;
  String user;
  ObjectOutputStream out;
  ObjectInputStream in;
  HashMap<String, boolean[]> otherSeqsMap = new HashMap<>();

  Sequencer sequencer;
  Sequence sequence;
  Sequence mySequence = null;
  Track track;

  String[] insturmentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic snare", "Crash Cymbal",
      "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom",
      "High Agogo", "Open Hi Conga"};

  int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

  public static void main (String[] args) {
      new BeatBoxFinal().startUp(); // the args area for user name needs to be removed and replaced with a prompt

  }
  // this is the method to improve to prompt for user name
  // set up a gui text pop up that collects the name and saves to the variable
  public void startUp() {

      frame = new JFrame("Username");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      BorderLayout layout = new BorderLayout();
      JPanel background = new JPanel(layout);
      background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      userName = new JTextField(20);
      JLabel UsernameLabel = new JLabel("Enter Username");
      JButton enterServer = new JButton("Enter");
      enterServer.addActionListener(new usernameListener());
      frame.setBounds(300,300,300,100);
      frame.add(BorderLayout.CENTER, userName);
      frame.add(BorderLayout.NORTH, UsernameLabel);
      frame.add(BorderLayout.SOUTH, enterServer);
      frame.setVisible(true);

      // open connection to the server
      //try {
      //    Socket sock  = new Socket("45.77.72.121", 4242);
        //  out = new ObjectOutputStream(sock.getOutputStream());
        //  in = new ObjectInputStream(sock.getInputStream());
         // Thread remote = new Thread(new RemoteReader());
         // remote.start();
     // } catch (IOException ex) {
       //   System.out.println("couldn't connect - play alone");

     // }
     // setUpMidi();
     // frame.setVisible(false);
     // buildGUI();

  } // close startup
  class usernameListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
          user = userName.getText();
          if (user.length() < 1) {
              user = null;
          } else { //open connection to the server
              try {
          Socket sock  = new Socket("127.0.0.1", 4242);
          out = new ObjectOutputStream(sock.getOutputStream());
          in = new ObjectInputStream(sock.getInputStream());
          Thread remote = new Thread(new RemoteReader());
          remote.start();
      } catch (IOException ex) {
          System.out.println("couldn't connect - play alone");

      }
      setUpMidi();
      frame.setVisible(false);
      buildGUI();
          }
      }
  }

  public void buildGUI() {
      theFrame = new JFrame("BeatBox");
      theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      BorderLayout layout = new BorderLayout();
      JPanel background = new JPanel(layout);
      background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

      checkboxList = new ArrayList<JCheckBox>();

      Box buttonBox = new Box(BoxLayout.Y_AXIS);
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

      JButton sendIt = new JButton("send/save");
      sendIt.addActionListener(new MySendListener());
      buttonBox.add(sendIt);



      userMessage = new JTextField();
      buttonBox.add(userMessage);
      incomingList = new JList();
      incomingList.addListSelectionListener(new MyListSelectionListener());
      incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JScrollPane theList = new JScrollPane(incomingList);
      buttonBox.add(theList);
      incomingList.setListData(listVector); // no data to start with

      Box nameBox = new Box(BoxLayout.Y_AXIS);
      for (int i = 0; i < 16; i++) {
          nameBox.add(new Label(insturmentNames[i]));
      }

      background.add(BorderLayout.EAST, buttonBox);
      background.add(BorderLayout.WEST, nameBox);

      theFrame.getContentPane().add(background);
      GridLayout grid = new GridLayout(16,16);
      grid.setVgap(1);
      grid.setHgap(2);
      mainPanel = new JPanel(grid);
      background.add(BorderLayout.CENTER, mainPanel);
      theFrame.getRootPane().setDefaultButton(sendIt);

      for (int i = 0; i < 256; i++) {
          JCheckBox c = new JCheckBox();
          c.setSelected(false);
          checkboxList.add(c);
          mainPanel.add(c);
      } // end loop

      theFrame.setBounds(50,50,300,300);
      theFrame.pack();
      theFrame.setVisible(true);

  } // close build GUI

  public void setUpMidi() {
      try {
          sequencer = MidiSystem.getSequencer();
          sequencer.open();
          sequence = new Sequence(Sequence.PPQ,4);
          track = sequence.createTrack();
          sequencer.setTempoInBPM(120);
      } catch(InvalidMidiDataException | MidiUnavailableException e) {e.printStackTrace();}
  } // close set up MIDI

  public void buildTrackAndStart() {
      ArrayList<Integer> trackList = null; // holds the insturments
      sequence.deleteTrack(track);
      track = sequence.createTrack();

      for (int i = 0; i < 16; i++) {
          trackList = new ArrayList<Integer>();
          for (int j = 0; j < 16; j++) {
              JCheckBox jc = (JCheckBox) checkboxList.get(j + (16*i));
              if (jc.isSelected()) {
                  int key = instruments[i];
                  trackList.add(new Integer(key));

              } else {
                  trackList.add(null); // because slot should be empty in the track
              }
          } //close inner loop
          makeTracks(trackList);
      } // close outer loop
      track.add(makeEvent(192,9,1,0,15)); // so always 16 beats
      try {
          sequencer.setSequence(sequence);
          sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
          sequencer.start();
          sequencer.setTempoInBPM(120);

      } catch(Exception e) {e.printStackTrace();}
  } // close method

  public class MyStartListener implements ActionListener {
      public void actionPerformed(ActionEvent a) {
          buildTrackAndStart();
      } // close action performed
  } // close inner class

  public class MyStopListener implements ActionListener {
      public void actionPerformed(ActionEvent a) {
          sequencer.stop();
      } // close action performed
  } //close inner class

  public class MyUpTempoListener implements ActionListener {
      public void actionPerformed(ActionEvent a) {
          float tempoFactor = sequencer.getTempoFactor();
          sequencer.setTempoFactor((float) (tempoFactor * 1.03));
      } // close action performed
  } //close inner class

  public class MyDownTempoListener implements ActionListener {
      public void actionPerformed (ActionEvent a) {
          float tempoFactor = sequencer.getTempoFactor();
          sequencer.setTempoFactor((float) (tempoFactor * .97));
      }
  }

  public class MySendListener implements ActionListener {
      public void actionPerformed(ActionEvent a) {
          // make an arraylist of just the state of the checkboxes
          boolean[] checkboxState = new boolean[256];
          for (int i = 0; i < 256; i++) {
              JCheckBox check = (JCheckBox) checkboxList.get(i);
              if (check.isSelected()) {
                  checkboxState[i] = true;
              }
          } // close loop
          String messageToSend = null;
          try {
              out.writeObject(user + nextNum++ + ": " + userMessage.getText());
              out.writeObject(checkboxState);
          } catch(Exception ex) {
              System.out.println("Sorry, cannot send to server");
          }
          userMessage.setText("");
      } // close actionperformed
  } // close inner class

  public class MyListSelectionListener implements ListSelectionListener {
      public void valueChanged(ListSelectionEvent le ) {
          if (!le.getValueIsAdjusting()) {
              String selected = (String) incomingList.getSelectedValue();
              if (selected != null) {
                  // now change the mapped sequence
                  boolean[] selectedState = (boolean[]) otherSeqsMap.get(selected);
                  changeSequence(selectedState);
                  sequencer.stop();
                  buildTrackAndStart();
              }
          }
      } // close value changed
  } // close inner class
  public class RemoteReader implements Runnable {
      boolean[] checkboxState = null;
      String nameToShow = null;
      Object obj = null;
      public void run() {
          try {
              while((obj=in.readObject()) != null) {
                  System.out.println("Got from server");
                  System.out.println(obj.getClass());
                  String nameToShow = (String) obj;
                  checkboxState = (boolean[]) in.readObject();
                  otherSeqsMap.put(nameToShow, checkboxState);
                  listVector.add(nameToShow);
                  incomingList.setListData(listVector);
              } // close while

          } catch(Exception ex) {ex.printStackTrace();}
      }// close run
  } // close inner

  public class MyPlayMineListener implements ActionListener {
      public void actionPerformed(ActionEvent a) {
          if (mySequence != null) {
              sequence = mySequence; //restore to the original
          }
      } // close action performed
  } // close inner class

  public void changeSequence(boolean[] checkboxState) {
      for (int i = 0; i < 256; i++) {
          JCheckBox check = (JCheckBox) checkboxList.get(i);
          if (checkboxState[i]) {
              check.setSelected(true);
          } else {
              check.setSelected(false);
          }
      } // close loop
  } // close change sequence

  public void makeTracks(ArrayList list) {
      Iterator it = list.iterator();
      for (int i = 0; i < 16; i++) {
          Integer num = (Integer) it.next();
          if (num != null) {
              int numKey = num.intValue();
              track.add(makeEvent(144,9,numKey, 100, i));
              track.add(makeEvent(128,9,numKey,100, i + 1));
          }
      } // close loop
  } // close maketracks()

  public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
      MidiEvent event = null;
      try {
          ShortMessage a = new ShortMessage();
          a.setMessage(comd, chan, one, two);
          event = new MidiEvent(a, tick);
      } catch(Exception e) { }
      return event;
  } // close make event



  } // close class
