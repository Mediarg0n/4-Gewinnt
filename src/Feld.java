
public class Feld {
	private char chip;
	
	public char getChip() {
		return chip;
	}
	public boolean setChar(char c) {
		if(chip==' ')
			chip = c;
		else 
			return false;
		return true;
	}
}
