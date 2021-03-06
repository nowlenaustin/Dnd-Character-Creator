import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Desktop;

public class DndCharacterCreator {
	public static final String SRC = "./assets/DnD_5E_CharacterSheet - Form Fillable.pdf";
	public static String DEST = "./Character Sheets/";
	public static final String RACES = "./assets/races.txt";
	public static final String CLASSES = "./assets/classes.txt";
	public static Scanner userInput;
	public static dndInfo DndInfo; // contains info needed to create characters (ie, races, classes, etc)
    public static characterData character;
	
	
    public static void main(String[] args) throws IOException {
    	/** Starting point for DndCharacterCreator program */
    	
    	// setup for program
        setup();

        // Player Name
        getPlayerName();

        // Character Name
        getCharacterName();

        // Character Race
        getCharacterRace();
        
        // Character Class
        getCharacterClass();
        
        // Character Level
        getCharacterLevel();
        
        // Create PDF
        String pathOfPDF = createPDF();
        
        // Open PDF 
        openPDF(pathOfPDF);
        
        close();
    }
    
    public static void setup() throws IOException{
        /** sets up DndInfo and userInput variables */
        // create list of races from file
    	DndInfo = new dndInfo(RACES, CLASSES);
        // needed to read from console input
        userInput = new Scanner(System.in);
        // initialize characterData class
        character = new characterData();
    }
    
    public static void getCharacterClass(){
    	/** Prompts user for their desired class for their character and stores 
    	 * the value in the public variable character */
    	String characterClass;
        while(true){
        	// build string to print out all race options
        	StringBuilder sb = new StringBuilder();
        	sb.append("Classes to choose from: ");
        	String delimiter = ""; // used so not comma before first and no comma after last
        	for(String s : DndInfo.classes){
        		sb.append(delimiter);
        		delimiter = ", ";
        		sb.append(s);
        	}
        	//print all race options
        	System.out.println(sb.toString());
        	
        	System.out.print("Please enter your Character's class out of the listed options: ");
        	characterClass = userInput.nextLine();
        	if(DndInfo.classes.contains(characterClass)){
        		break;
        	}else{
        		System.out.println("\"" + characterClass + "\" is not a valid class. Please choose a valid class.");
        		System.out.println(""); // print empty line
        	}
        }

        character.characterClass = characterClass;

        System.out.println(""); // print empty line
    }

    public static void getCharacterLevel(){
        /** Prompts user for their desired level for their character and stores
         * the value in the public variable character */
    	// make sure level is a number
        String level;
        while(true){
        	System.out.print("Please enter your Character's Level: ");
        	level = userInput.nextLine();
		    if(_isInteger(level)){ // check if level is an integer
				// is an integer!
				break;
        	}else{
				// not an integer!
				System.out.println("Please type the level as a number and not as words.");
			}
        }

        // store new ClassLevel with class and level 
        character.level = level;

        System.out.println(""); // print empty line
    }
    
    public static void getCharacterName(){
    	/** Prompts user for their desired name for their character and stores 
    	 * the value in the public variable character */
        System.out.print("Please enter your Character's name: ");

        character.characterName = userInput.nextLine();

        System.out.println(""); // print empty line
    }
    
    public static void getCharacterRace(){
    	/** Prompts user for their desired race for their character and stores 
    	 * the value in the public variable character */
    	String race;
        while(true){
        	// build string to print out all race options
        	StringBuilder sb = new StringBuilder();
        	sb.append("Races to choose from: ");
        	for(String s : DndInfo.races){
        		sb.append(s);
        		sb.append(", ");
        	}
        	//print all race options
        	System.out.println(sb.toString());
        	
        	System.out.print("Please enter your Character's race out of the listed options: ");
        	race = userInput.nextLine();
        	if(DndInfo.races.contains(race)){
        		break;
        	}else{
        		System.out.println("\"" + race + "\" is not a valid race. Please input a valid race.");
        		System.out.println(""); // print empty line
        	}
        }

        character.race = race;
        System.out.println(""); // print empty line
    }
    
    public static void getPlayerName(){
    	/** Prompts user for the name they wish to be displayed on their character sheet and 
    	 * stores the value in the public variable character */
        System.out.print("Please enter your Name: ");
        character.playerName = userInput.nextLine();
        System.out.println(""); // print empty line
    }
    
    public static String createPDF() throws IOException{
    	/** Takes data stored in DndCharacterCreator.character and creates a PDF
    	 * character sheet using the stored values. The PDF is stored in the DndCharacterCreator.DEST 
    	 * and is named using the character's name */
    	
        // PDF file name based off Character Name
        DEST = DEST + character.characterName + ".pdf"; // sets DEST to final destination of PDF
        File file = new File(DEST);
        file.getParentFile().mkdirs(); // create directories for file if they do not exist
        
        // Create PDF
        System.out.println("Making your Character Sheet. Please wait. This shouldn't take long.");
        System.out.println(""); // print empty line

        try {
            character.createPDF(SRC, DEST);
            System.out.println("Your Character Sheet has been created! Thank you for using my program!");
        } catch(IOException io){
            System.out.println("Sorry something went wrong when creating your character sheet. Please contact creator of the software.");
        }

        
        return DEST;
    }
    
    public static void openPDF(String pathOfPDF) throws IOException{
        /** opens the created PDF in the default PDF viewer */
    	Desktop.getDesktop().open(new File(pathOfPDF));
    }
    
    public static void close() throws IOException{
    	/** Cleans up anything needing to be closed before program end */
    	System.out.println("Press ENTER to close.");
        System.in.read();
    	userInput.close();
    }
    
    private static boolean _isInteger(String s) {
    	/** Checks if contents of string is an integer */
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
