package se.modu;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LootTable {

	private Scanner in;
	private static Random rand;
	private boolean showRolls;
	private int hoardClass;

	public LootTable() {
		in = new Scanner(System.in);
		rand = new Random();

		showRolls = true;
	}

	private void setup() {
		// Select which hoard class to use
		hoardClass = -1;
		while (hoardClass < 1 || hoardClass > 22) {
			System.out.println("Hoard class: ");
			try {
				hoardClass = in.nextInt();
			} catch (Exception e) {
				in.nextLine();
				hoardClass = -1;
			}
		}
	}

	public int rollDie(int noOfDie, int noOfSides){
		return rollDie(noOfDie, noOfSides, 0);
	}
	
	public int rollDie(int noOfDie, int noOfSides, int modifier) {
		int sum = 0;
		if(showRolls){
			System.out.println("Rolling "+noOfDie+"d"+noOfSides+ (modifier != 0 ? ("+"+modifier) : ""));
			System.out.print("Dice rolls: ");
		}
		for (int i = 0; i < noOfDie; i++) {
			int roll = rand.nextInt(noOfSides)+1;
			if(showRolls){
				System.out.print(roll + " ");
			}
			sum += roll;
		}
		if(showRolls && modifier != 0){
			System.out.print("+"+modifier);
		}
		if(showRolls){
			System.out.println();
		}
		return sum+modifier;
	}

	private boolean percentageRoll(int chance){
		// Always based on 100%
		int percentage = rand.nextInt(100)+1;
		if(showRolls){
			System.out.println("Required for success: "+chance + ". Got: "+percentage);
		}
		if(percentage <= chance){
			return true;
		}
		return false;
	}
	
	private int rollDiePercentage(int noOfDie, int noOfSides, int modifier, int chance, String type){
		if(showRolls){
			System.out.print("Attempting to roll for "+type+". ");
		}
		if(percentageRoll(chance)){
			return rollDie(noOfDie,noOfSides,modifier);
		}
		return 0;
	}
	
	private ArrayList<String> rollDieArtifactPercentage(int noOfDie, int noOfSides, int modifier, int chance, String type){
		// Not sure how well this one will work out, we'll see
		
		ArrayList<String> artifacts = new ArrayList<String>();
		if(showRolls){
			System.out.print("Attempting to roll for artifacts. ");
		}
		if(percentageRoll(chance)){
			artifacts.add(rollDie(noOfDie,noOfSides,modifier)+" "+type);
		}
		return artifacts;
	}
	
	private ArrayList<String> rollArtifactPercentage(int noOfArtifacts, int chance, String type){
		// Not sure how well this one will work out, we'll see
		
		ArrayList<String> artifacts = new ArrayList<String>();
		if(showRolls){
			System.out.print("Attempting to roll for artifacts. ");
		}
		if(percentageRoll(chance)){
			artifacts.add(noOfArtifacts+" "+type);
		}
		return artifacts;
	}
	
	
	public void roll() {
		setup();
		int copper = 0;
		int silver = 0;
		int gold = 0;
		ArrayList<String> artifacts = new ArrayList<String>();
		if(hoardClass == 1){
			copper = rollDie(4,6);
		} else if (hoardClass == 2){
			silver = rollDie(2,8,1);
		} else if (hoardClass == 3){
			gold = rollDie(1,6);
		} else if (hoardClass == 4){
			gold = rollDie(1,8);
		} else if (hoardClass == 5){
			gold = rollDie(1,6)*5;
		} else if (hoardClass == 6){
			copper = rollDiePercentage(1,100,0,70,"copper");
			silver = rollDiePercentage(1,100,0,5,"silver");
			gold = rollDiePercentage(1,100,0,5,"gold");
			artifacts = rollArtifactPercentage(1, 3, "any");
		} else if (hoardClass == 7){
			silver = rollDiePercentage(1,100,0,25,"silver");
			gold = rollDiePercentage(1,100,0,10,"gold");
			artifacts = rollArtifactPercentage(1, 7, "any");
		} else if (hoardClass == 8){
			artifacts = rollDieArtifactPercentage(1,4,0,45,"gizmos");
		} else if (hoardClass == 9){
			artifacts = rollDieArtifactPercentage(2,4,0,45,"drugs, chemicals and medical devices");
		} else if (hoardClass == 10){
			gold = rollDiePercentage(2,10,0,70,"gold")*1000;
		} else if (hoardClass == 11){
			gold = rollDiePercentage(2,4,0,40,"gold")*1000;
		} else if (hoardClass == 12){
			silver = rollDiePercentage(1,8,0,25,"silver")*1000;
			gold = rollDiePercentage(1,4,0,15,"gold")*1000;
		} else if (hoardClass == 13){
			copper = rollDiePercentage(1,6,0,25,"copper")*1000;
			silver = rollDiePercentage(1,4,0,25,"silver")*1000;
		} else if (hoardClass == 14){
			
		} else if (hoardClass == 15){
			
		} else if (hoardClass == 16){
			
		} else if (hoardClass == 17){
			
		} else if (hoardClass == 18){
			
		} else if (hoardClass == 19){
			
		} else if (hoardClass == 20){
			
		} else if (hoardClass == 21){
			
		} else if (hoardClass == 22){
			
		}
		System.out.print("You receive: ");
		if(gold != 0){
			System.out.print(gold + " gold ");
		}
		if(silver != 0){
			System.out.print(silver+" silver ");
		}
		if(copper != 0){
			System.out.print(copper+" copper ");
		}
		if(artifacts.size() != 0){
			for(String str : artifacts){
				System.out.print(str+" ");
			}
		}
		if(artifacts.size() == 0 && gold == 0 && silver == 0 && copper == 0){
			System.out.println("Nothing.");
		}
		
		// Get it to ask "Again?" to rerun
			
	}

	public static void main(String[] args) {
		(new LootTable()).roll();
	}

}
