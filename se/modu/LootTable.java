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

	public int rollDie(int noOfDie, int noOfSides) {
		return rollDie(noOfDie, noOfSides, 0);
	}

	public int rollDie(int noOfDie, int noOfSides, int modifier) {
		int sum = 0;
		if (showRolls) {
			System.out.println("Rolling " + noOfDie + "d" + noOfSides
					+ (modifier != 0 ? ("+" + modifier) : ""));
			System.out.print("Dice rolls: ");
		}
		for (int i = 0; i < noOfDie; i++) {
			int roll = rand.nextInt(noOfSides) + 1;
			if (showRolls) {
				System.out.print(roll + " ");
			}
			sum += roll;
		}
		if (showRolls && modifier != 0) {
			System.out.print("+" + modifier);
		}
		if (showRolls) {
			System.out.println();
		}
		return sum + modifier;
	}

	private boolean percentageRoll(int chance) {
		// Always based on 100%
		int percentage = rand.nextInt(100) + 1;
		if (showRolls) {
			System.out.println("Required for success: " + chance + ". Got: " + percentage);
		}
		if (percentage <= chance) {
			return true;
		}
		return false;
	}

	private int rollDiePercentage(int noOfDie, int noOfSides, int modifier, int chance, String type) {
		if (showRolls) {
			System.out.print("Attempting to roll for " + type + ". ");
		}
		if (percentageRoll(chance)) {
			return rollDie(noOfDie, noOfSides, modifier);
		}
		return 0;
	}

	private ArrayList<String> rollDieArtifactPercentage(int noOfDie, int noOfSides, int modifier, int chance,
			String type) {
		// Not sure how well this one will work out, we'll see

		ArrayList<String> artifacts = new ArrayList<String>();
		if (showRolls) {
			System.out.print("Attempting to roll for artifacts. ");
		}
		if (percentageRoll(chance)) {
			artifacts.add(rollDie(noOfDie, noOfSides, modifier) + " " + type);
		}
		return artifacts;
	}

	private ArrayList<String> rollArtifactPercentage(int noOfArtifacts, int chance, String type) {
		// Not sure how well this one will work out, we'll see

		ArrayList<String> artifacts = new ArrayList<String>();
		if (showRolls) {
			System.out.print("Attempting to roll for artifacts. ");
		}
		if (percentageRoll(chance)) {
			artifacts.add(noOfArtifacts + " " + type);
		}
		return artifacts;
	}

	public void roll() {
		setup();
		int copper = 0;
		int silver = 0;
		int gold = 0;
		ArrayList<String> artifacts = new ArrayList<String>();
		if (hoardClass == 1) {
			copper = rollDie(4, 6);
		} else if (hoardClass == 2) {
			silver = rollDie(2, 8, 1);
		} else if (hoardClass == 3) {
			gold = rollDie(1, 6);
		} else if (hoardClass == 4) {
			gold = rollDie(1, 8);
		} else if (hoardClass == 5) {
			gold = rollDie(1, 6) * 5;
		} else if (hoardClass == 6) {
			copper = rollDiePercentage(1, 100, 0, 70, "copper");
			silver = rollDiePercentage(1, 100, 0, 5, "silver");
			gold = rollDiePercentage(1, 100, 0, 5, "gold");
			artifacts = rollArtifactPercentage(1, 3, "any");
		} else if (hoardClass == 7) {
			silver = rollDiePercentage(1, 100, 0, 25, "silver");
			gold = rollDiePercentage(1, 100, 0, 10, "gold");
			artifacts = rollArtifactPercentage(1, 7, "any");
		} else if (hoardClass == 8) {
			artifacts = rollDieArtifactPercentage(1, 4, 0, 45, "gizmos");
		} else if (hoardClass == 9) {
			artifacts = rollDieArtifactPercentage(2, 4, 0, 45, "drugs, chemicals and medical devices");
		} else if (hoardClass == 10) {
			gold = rollDiePercentage(2, 10, 0, 70, "gold") * 1000;
		} else if (hoardClass == 11) {
			gold = rollDiePercentage(2, 4, 0, 40, "gold") * 1000;
		} else if (hoardClass == 12) {
			silver = rollDiePercentage(1, 8, 0, 25, "silver") * 1000;
			gold = rollDiePercentage(1, 4, 0, 15, "gold") * 1000;
		} else if (hoardClass == 13) {
			copper = rollDiePercentage(1, 6, 0, 25, "copper") * 1000;
			silver = rollDiePercentage(1, 4, 0, 25, "silver") * 1000;
		} else if (hoardClass == 14) {
			gold = rollDiePercentage(2, 4, 0, 30, "gold") * 5 * 1000;
			artifacts = rollArtifactPercentage(3, 30, "any 1");
			artifacts.add("3 foodstuffs and junk");
		} else if (hoardClass == 15) {

		} else if (hoardClass == 16) {

		} else if (hoardClass == 17) {

		} else if (hoardClass == 18) {

		} else if (hoardClass == 19) {

		} else if (hoardClass == 20) {

		} else if (hoardClass == 21) {

		} else if (hoardClass == 22) {

		}
		System.out.print("You receive: ");
		if (gold != 0) {
			System.out.print(gold + " gold ");
		}
		if (silver != 0) {
			System.out.print(silver + " silver ");
		}
		if (copper != 0) {
			System.out.print(copper + " copper ");
		}
		if (artifacts.size() != 0) {
			for (String str : artifacts) {
				System.out.print(str + " ");
			}
		}
		if (artifacts.size() == 0 && gold == 0 && silver == 0 && copper == 0) {
			System.out.println("Nothing.");
		}

		// Get it to ask "Again?" to rerun

	}

	private String randomArtifactType() {
		int roll = rand.nextInt(100) + 1;
		if (roll >= 1 && roll <= 5) {
			return primitiveFirearms();
		} else if (roll >= 6 && roll <= 17) {
			return advancedMeleeWeapons();
		} else if (roll >= 18 && roll <= 25) {
			return advancedPistols();
		} else if (roll >= 26 && roll <= 33) {
			return advancedRifles();
		} else if (roll >= 34 && roll <= 41) {
			return advancedArmor();
		} else if (roll >= 42 && roll <= 50) {
			return powerSources();
		} else if (roll >= 51 && roll <= 59) {
			return grenades();
		} else if (roll >= 60 && roll <= 68) {
			return bombsAndMissiles();
		} else if (roll >= 69 && roll <= 79) {
			return gizmos();
		} else if (roll >= 80 && roll <= 90) {
			return drugsAndChems();
		} else {
			return foodstuffsAndJunk();
		}
	}

	public String primitiveFirearms() {
		return "foo";
	}

	public String advancedMeleeWeapons() {
		return "foo";
	}

	public String advancedPistols() {
		return "foo";
	}

	public String advancedRifles() {
		return "foo";
	}

	public String advancedArmor() {
		return "foo";
	}

	public String powerSources() {
		return "foo";
	}

	public String grenades() {
		return "foo";
	}

	public String bombsAndMissiles() {
		return "foo";
	}

	public String gizmos() {
		int roll = rand.nextInt(100) + 1;
		if (roll >= 1 && roll <= 5) {
			return "Advanced Breathing Apparatus";
		} else if (roll >= 6 && roll <= 9) {
			return "Autograpnel";
		} else if (roll >= 10 && roll <= 13) {
			return "Boron Solution Spray";
		} else if (roll >= 14 && roll <= 18) {
			return "Cigarette Lighter";
		} else if (roll >= 19 && roll <= 23) {
			return "Clones";
		} else if (roll >= 24 && roll <= 27) {
			return "Communicator";
		} else if (roll >= 28 && roll <= 31) {
			return "Electronically Responsive Notation Instrument (Ernie)";
		} else if (roll >= 32 && roll <= 35) {
			return "Firestarter Cube";
		} else if (roll >= 36 && roll <= 38) {
			return "Flashlight";
		} else if (roll >= 39 && roll <= 42) {
			return "Force Screen Belt";
		} else if (roll >= 43 && roll <= 48) {
			return "Gas Mask";
		} else if (roll >= 49 && roll <= 53) {
			return "Gas Mask Filter";
		} else if (roll >= 54 && roll <= 57) {
			return "Chemical Sensor";
		} else if (roll >= 58 && roll <= 61) {
			return "Geiger Counter";
		} else if (roll >= 62 && roll <= 66) {
			return "Rad Tab";
		} else if (roll >= 67 && roll <= 71) {
			return "Infra-Red Goggles";
		} else if (roll >= 72 && roll <= 76) {
			return "Motion Detector";
		} else if (roll >= 77 && roll <= 81) {
			return "Optic Scanner";
		} else if (roll >= 82 && roll <= 86) {
			return "Portable Detection Radar";
		} else if (roll >= 87 && roll <= 91) {
			return "Power Fist";
		} else if (roll >= 92 && roll <= 95) {
			return "UV Sterilizer";
		} else if (roll >= 96 && roll <= 98) {
			return "Water Purifier";
		} else {
			return "X-Ray Goggles";
		}

	}

	public String drugsAndChems() {
		int roll = rand.nextInt(100) + 1;
		if (roll >= 1 && roll <= 6) {
			return "Antitox";
		} else if (roll >= 7 && roll <= 12) {
			return "Filter-Dose";
		} else if (roll >= 13 && roll <= 18) {
			return "Hercurin";
		} else if (roll >= 19 && roll <= 25) {
			return "K-O Shot";
		} else if (roll >= 26 && roll <= 32) {
			return "Medi-Spray I";
		} else if (roll >= 33 && roll <= 40) {
			return "Medi-Spray II";
		} else if (roll >= 41 && roll <= 46) {
			return "Proton Energy Pill";
		} else if (roll >= 47 && roll <= 52) {
			return "Rad-Purge Shot";
		} else if (roll >= 53 && roll <= 58) {
			return "Stimshot A";
		} else if (roll >= 59 && roll <= 63) {
			return "Stimshot B";
		} else if (roll >= 64 && roll <= 69) {
			return "Supergen";
		} else if (roll >= 70 && roll <= 75) {
			return "Truth Serum";
		} else if (roll >= 76 && roll <= 83) {
			return "Diagnostic Scanner";
		} else if (roll >= 84 && roll <= 91) {
			return "Healing Pack";
		} else if (roll >= 92 && roll <= 98) {
			return "Ready Syringe";
		} else {
			return "Regeneration Tank";
		}
	}

	public String foodstuffsAndJunk() {
		int roll = rand.nextInt(100) + 1;
		if (roll >= 1 && roll <= 10) {
			return "Canned Foods";
		} else if (roll >= 11 && roll <= 17) {
			return "Dehydrated Pills";
		} else if (roll >= 18 && roll <= 24) {
			return "Goo Tube";
		} else if (roll >= 25 && roll <= 30) {
			return "Salt Pills";
		} else if (roll >= 31 && roll <= 38) {
			return "Soup Mixes";
		} else if (roll >= 39 && roll <= 45) {
			return "Synthihol";
		} else if (roll >= 46 && roll <= 51) {
			return "Hologram Projector";
		} else if (roll >= 52 && roll <= 56) {
			return "Identity Card";
		} else if (roll >= 57 && roll <= 67) {
			return "Ion Bonding Tape";
		} else if (roll >= 68 && roll <= 76) {
			return "Light Rod";
		} else if (roll >= 77 && roll <= 82) {
			return "Light Stick";
		} else if (roll >= 83 && roll <= 87) {
			return "Magnesium Firestarter";
		} else if (roll >= 88 && roll <= 94) {
			return "Portable Stove";
		} else {
			return "Survival Kit";
		}
	}

	public static void main(String[] args) {
		(new LootTable()).roll();
	}

}
