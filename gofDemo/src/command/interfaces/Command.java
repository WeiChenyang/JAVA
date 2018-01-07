package command.interfaces;

/**
 * @author Administrator
 * ÃüÁî½Ó¿Ú
 */
public interface Command {

	//Ö´ĞĞÃüÁî
	public void execute();
	//³·ÏúÃüÁî
	public void undo();
}