import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener, KeyListener{
	private int count;
	private Spielbrett sBrett;
	private JButton[] buttons;
	private char winn=' ';
	
	// Konstruiert das Frame
	public GamePanel(Spielbrett sBrett) {
		this.sBrett = sBrett;
		count =0;
		this.setLayout(new BorderLayout());
		
		JPanel eingabePanel= new JPanel();

		eingabePanel.setLayout(new GridLayout(1,sBrett.getSpalten()));
		this.add(eingabePanel,BorderLayout.PAGE_START);
		buttons = new JButton[sBrett.getSpalten()];
		for(int i=1;i<=buttons.length;i++) {
			buttons[i-1] = new JButton(i+". Spalte");
			eingabePanel.add(buttons[i-1]);
			buttons[i-1].addActionListener(this);
		}
		this.repaint();
		
	}
	
	//Zeichnet die Componenten auf das Panel
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		char[][] f=sBrett.getFelder();
		
		g.setColor(Color.BLUE);
		g.fillRect(0, 20, f[0].length*100, f.length*100);
		
		for(int i=0;i<f.length;i++) {
			for(int j=0;j<f[i].length;j++) {
				switch(f[i][j]) {
				case ' ': g.setColor(Color.GRAY);break;
				case 'r': g.setColor(Color.RED);break;
				case 'y': g.setColor(Color.YELLOW); 
				}
				g.fillOval(j*100, 20+i*100, 100, 100);
			}
		}
		
		g.setColor(Color.RED);
		for(int i=0;i<f.length;i++) {
			for(int j=0;j<f[i].length;j++)
				if(f[i][j]=='r')
					g.fillOval(j*100, 20+i*100, 100, 100);
		}
		char w= isWinn();
		if(w!=' ') {
			if(w=='r')
				g.setColor(Color.RED);
			else if(w=='y')
				g.setColor(Color.YELLOW);
			g.setFont(new Font("SansSerif",Font.PLAIN,36));
			g.drawString("Gewonnen", 100, 100);
		}
		
		Color buttonColor;
		if(count%2==0) 
			buttonColor = Color.RED;
		else 
			buttonColor = Color.YELLOW;
		
		for(JButton x:buttons) {
			x.setBackground(buttonColor);
		}
		
	}
	
	//Gibt die Dimension für das Frame wieder
	public Dimension getDimension() {
		return new Dimension((sBrett.getSpalten())*103,sBrett.getZeilen()*110+20);
	}
	
	//Überprüft das Spielbrett darauf ob es einen Gewinner gibt
	private char isWinn() {
		char[][] felder = sBrett.getFelder();
		char w=isHorWinn(felder); 
		if(w!= ' ')
			return w;
		w = isVerWinn(felder);
		if(w!= ' ')
			return w;
		w = isDiaWinn(felder);
			return w;
	}
	//Überprüft ob es einen Horizentralen Gewinn gibt
	private char isHorWinn(char[][] felder) {
		for(int i=0;i<felder.length; i++) {
			int r=0;
			int y=0;
			for(int j=0; j<felder[i].length; j++) {
				if(felder[i][j]=='y') {
					y++;
					r=0;
					if(y==4)
						return 'y';
				}
				else if(felder[i][j]=='r') {
					r++;
					y=0;
					if(r==4)
						return 'r';
				}
				else {
					r=0;
					y=0;
				}
			}
		}
		return ' ';
	}
	//Überprüft ob es einen Vertikalen Gewinn gibt
	private char isVerWinn(char[][] felder) {
		for(int i=0;i<felder[0].length; i++) {
			int r=0;
			int y=0;
			for(int j=0; j<felder.length; j++) {
				if(felder[j][i]=='y') {
					y++;
					r=0;
					if(y==4)
						return 'y';
				}
				else if(felder[j][i]=='r') {
					r++;
					y=0;
					if(r==4)
						return 'r';
				}
				else {
					r=0;
					y=0;
				}
			}
		}
		return ' ';
	}
	//Überprpft ob es einen Diagonalen Gewinn gibt
	private char isDiaWinn(char[][] felder) {
		//von oben links nach unten rechts
		for(int i=-2; i<=3; i++) {
			int z = 0;
			int s = i;
			if (i<0) {
				z=-i;
				s=0;
			}

			int y=0;
			int r=0;
			do {
				if(felder[z][s]=='r') {
					r++;
					y=0;
					if(r==4)	
						return 'r';
				}
				else if(felder[z][s]=='y') {
					y++;
					r=0;
					if(y==4)	
						return 'y';
				}
				else {
					y=0;
					r=0;
				}
				
			
				z++;
				s++;
			}while(z<felder.length && s<felder[0].length);
		}
		//von Rechts oben nach links unten
		for(int i=-2; i<=3; i++) {
			int z,s;
			if (i<=0) {
				s = 0;
				z = felder.length-1+i;
			}
			else {
				s=0+i;
				z=felder.length-1;
			}

			int y=0;
			int r=0;
			do {
				if(felder[z][s]=='r') {
					r++;
					y=0;
					if(r==4)	
						return 'r';
				}
				else if(felder[z][s]=='y') {
					y++;
					r=0;
					if(y==4)	
						return 'y';
				}
				else {
					r=0;
					y=0;
				}
			
				z--;
				s++;
			}while(z>=0 && s<felder[0].length);
		}
		return ' ';
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("4 Gewinnt");
		GamePanel panel = new GamePanel(new Spielbrett());
		//frame.addKeyListener(panel);
		frame.add(panel);
		frame.setSize(panel.getDimension());;
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	//ActionPerfomer der JButtons
	public void actionPerformed(ActionEvent e) {
		int buttonAkt = -1;
		for(int i=0;i<buttons.length && buttonAkt==-1; i++) {
			if(e.getSource().equals(buttons[i]))
				buttonAkt = i;
		}
		sBrett.setChip(buttonAkt, count%2);
		count++;
		
		
		this.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_1: sBrett.setChip(0,count%2); count++; break;
		case KeyEvent.VK_2: sBrett.setChip(1,count%2); count++; break;
		case KeyEvent.VK_3: sBrett.setChip(2,count%2); count++; break;
		case KeyEvent.VK_4: sBrett.setChip(3,count%2); count++; break;
		case KeyEvent.VK_5: sBrett.setChip(4,count%2); count++; break;
		case KeyEvent.VK_6: sBrett.setChip(5,count%2); count++; break;
		case KeyEvent.VK_7: sBrett.setChip(6,count%2); count++; break;
		}
		System.out.println("t");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("2t");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("t2");
	}
}
