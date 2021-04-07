import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.Font;
import java.util.Timer;

import java.awt.*;
import java.awt.event.*;

public class SimulationFrame extends JFrame {
	 
	 private static final long serialVersionUID =1L;
	 private JPanel panel,panel2;
	 private int WIDTH= 600,HEIGHT=600;
	 private JTextField textField,textField1,textField2,textField3,textField4,textField5;
	 private int counter;
	    //nrclienti,nrcase,timp,
	 int secunde=0;
	 Timer myTimer=new Timer();
	 TimerTask task=new TimerTask(){
		 public void run(){
			 secunde++;
			 System.out.println(secunde);
		 }
	 
};
public void start(){
	//myTimer.scheduleAtFixedRate(task,1000,1000);
}
	 
	 public SimulationFrame(){
		 this.setTitle("Simulator cozi");
		 panel=new JPanel();
		 panel.setLayout(new FlowLayout());
		 this.add(panel);
		 this.setSize(WIDTH, HEIGHT);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setLocationRelativeTo(null);
		 //this.setLayout(new BorderLayout());
		 this.setVisible(true);
		
	 }
	 
	 public void displayData(Client[][] clients, long[] waitingTimes, int crtTime){
		 //System.out.println(clients[0].length);
		 //remove all previous elements from panel and revalidate
		 panel.removeAll();
		 panel.revalidate();
		 //panel.setSize(WIDTH, HEIGHT);
		 //add a JScrollPane with a JList containing tasks
		 //JPanel queuePanel = new JPanel();
		 //queuePanel.setSize(new Dimension(1000, 20));
		 for(int i=0; i<clients.length; i++){
			 String s = "[" + waitingTimes[i] + "]";
			 //System.out.println(s);
			 for(int j=0; j<clients[i].length; j++){
				 //queuePanel.add(new JLabel(" " + clients[0][j].getProcessingTime()));
				 s = s+" C("  + clients[i][j].getProcessingTime() + ")";
				 //System.out.println(clients[i][j]);
			 }
			 JTextField tf = new JTextField(s);
			 //tf.setPreferredSize(new Dimension(1000, 20));
			 JScrollPane sp = new JScrollPane(tf);
			 sp.setPreferredSize(new Dimension(WIDTH-20, 50));
			 sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			 //add scrollPane to panel
			 
			 panel.add(sp);
		 }
		 panel.add(new JLabel(String.valueOf(crtTime)));
		 //repaint and revalidate panel
		 panel.repaint();
		 panel.revalidate();
		 
	 }

}
