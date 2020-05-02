import java.awt.Dimension;

public class Spielbrett {
	private static final int SPALTEN = 7;
	private static final int ZEILEN = 6;
	private char[][] felder;
	
	public Spielbrett() {
		felder = new char[ZEILEN][SPALTEN];
		for(int i = 0; i<ZEILEN; i++)
			for(int j=0; j<SPALTEN; j++)
				felder[i][j]=' ';
	}
	public int getZeilen() {
		return ZEILEN;
	}
	public int getSpalten() {
		return SPALTEN;
	}
	public char[][] getFelder() {
		return felder;
	}
	
	public boolean setChip(int spalte, int player) {
		for(int i = ZEILEN-1; i>=0; i--)
			if(felder[i][spalte]==' ') {
				if(player==0)
					felder[i][spalte]= 'r';
				else 
					felder[i][spalte]= 'y';
				return true;
			}
		return false;
	}
}


