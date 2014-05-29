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
			if(showRolls){
				System.out.print("Attempting to roll for copper. ");
			}
			if(percentageRoll(70)){
				copper = rollDie(1,100);
			}
			if(showRolls){
				System.out.print("Attempting to roll for silver. ");
			}
			if(percentageRoll(5)){
				silver = rollDie(1,100);
			}
			if(showRolls){
				System.out.print("Attempting to roll for gold. ");
			}
			if(percentageRoll(5)){
				gold = rollDie(1,100);
			}
			if(showRolls){
				System.out.print("Attempting to roll for artifacts. ");
			}
			if(percentageRoll(3)){
				artifacts.add("1 any");
			}
		} else if (hoardClass == 7){
			if(showRolls){
				System.out.print("Attempting to roll for silver. ");
			}
			if(percentageRoll(25)){
				silver = rollDie(1,100);
			}
			if(showRolls){
				System.out.print("Attempting to roll for gold. ");
			}
			if(percentageRoll(10)){
				gold = rollDie(1,100);
			}
			if(showRolls){
				System.out.print("Attempting to roll for artifacts. ");
			}
			if(percentageRoll(7)){
				artifacts.add("1 any");
			}
		} else if (hoardClass == 8){
			
		} else if (hoardClass == 9){
			
		} else if (hoardClass == 10){
			
		} else if (hoardClass == 11){
			
		} else if (hoardClass == 12){
			
		} else if (hoardClass == 13){
			
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
			
	}

	public static void main(String[] args) {
		(new LootTable()).roll();
	}

}
