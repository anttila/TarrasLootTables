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
		
		// TODO: Behaviour of stacks aren't consistant with HC 8 and 9 compared to the rest
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
			if(rand.nextInt(100)+1 <= 30){
				artifacts.add(1+" "+randomArtifactType());
				for(int i=0;i<3;i++){
					artifacts.add(1+" "+foodstuffsAndJunk());
				}
			}
		} else if (hoardClass == 15) {
			copper = rollDiePercentage(2,12,0,25,"copper")*1000;
			silver = rollDiePercentage(1,100,0,60,"silver")*1000;
			gold = rollDiePercentage(2,4,0,70,"gold")*10*1000;
			if(rand.nextInt(100)+1 <= 18){
				for(int i=0;i<4;i++){
					artifacts.add(1+" "+randomArtifactType());
				}
				artifacts.add(1+" "+drugsAndChems());
				artifacts.add(1+" "+gizmos());
			}
		} else if (hoardClass == 16) {
			gold = rollDiePercentage(1,8,0,60,"gold")*10*1000;
			if(rand.nextInt(100)+1 <= 30){
				for(int i=0;i<5;i++){
					artifacts.add(1+" "+randomArtifactType());
				}
				artifacts.add(1+" "+gizmos());
			}
		} else if (hoardClass == 17) {
			silver = rollDiePercentage(4,6,40,0,"silver")*1000;
			gold = rollDiePercentage(4,10,0,70,"gold")*1000;
			if(rand.nextInt(100)+1 <= 30){
				for(int i=0;i<3;i++){
					artifacts.add(1+" "+randomArtifactTypeNoWeapons());
				}
				artifacts.add(1+" "+drugsAndChems());
				artifacts.add(1+" "+gizmos());
			}
		} else if (hoardClass == 18) {
			copper = rollDiePercentage(2,6,0,7,"copper")*1000;
			silver = rollDiePercentage(1,10,0,35,"silver")*1000;
			gold = rollDiePercentage(2,6,0,50,"gold")*1000;
			if(rand.nextInt(100)+1 <= 30){
				for(int i=0;i<3;i++){
					artifacts.add(1+" "+randomArtifactType());
				}
				artifacts.add(1+" "+gizmos());
			}
		} else if (hoardClass == 19) {
			copper = rollDiePercentage(1,10,0,10,"copper")*1000;
			silver = rollDiePercentage(1,10,0,15,"silver")*1000;
			gold = rollDiePercentage(1,8,0,55,"gold")*1000;
			if(rand.nextInt(100)+1 <= 15){
				for(int i=0;i<2;i++){
					artifacts.add(1+" "+randomArtifactType());
				}
				artifacts.add(1+" "+drugsAndChems());
			}
		} else if (hoardClass == 20) {
			copper = rollDiePercentage(1,12,0,25,"copper")*1000;
			silver = rollDiePercentage(4,6,0,45,"silver")*1000;
			if(rand.nextInt(100)+1 <= 12){
				for(int i=0;i<2;i++){
					artifacts.add(1+" "+randomArtifactType());
				}
			}
		} else if (hoardClass == 21) {
			copper = rollDiePercentage(1,10,0,45,"copper")*1000;
			silver = rollDiePercentage(3,6,0,55,"silver")*1000;
			gold = rollDiePercentage(1,4,0,20,"gold")*1000;
			if(rand.nextInt(100)+1 <= 12){
				int roll = rand.nextInt(3);
				if(roll == 0){
					artifacts.add(1+" "+advancedArmor());
				} else if (roll == 1){
					artifacts.add(1+" "+advancedMeleeWeapons());
				} else if (roll == 2){
					roll = rand.nextInt(3);
					if(roll == 0){
						artifacts.add(1+" "+primitiveFirearms());
					} else if(roll == 0){
						artifacts.add(1+" "+advancedPistols());
					} else if(roll == 0){
						artifacts.add(1+" "+advancedRifles());
					}
				}
			}
		} else if (hoardClass == 22) {
			copper = rollDiePercentage(1,8,0,30,"copper")*1000;
			silver = rollDiePercentage(2,6,0,60,"silver")*1000;
			gold = rollDiePercentage(4,8,0,60,"gold")*1000;
			if(rand.nextInt(100)+1 <= 25){
				for(int i=0;i<3;i++){
					artifacts.add(1+" "+randomArtifactType());
				}
			}
		}
		System.out.print("You receive: ");
		if (gold != 0) {
			System.out.printf("%,d Gold Pieces \n", gold);
		}
		if (silver != 0) {
			System.out.printf("%,d Silver Pieces \n", silver);
		}
		if (copper != 0) {
			System.out.printf("%,d Copper Pieces \n", copper);
		}
		if (artifacts.size() != 0) {
			for (String str : artifacts) {
				System.out.println(str + " ");
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
	
	private String randomArtifactTypeNoWeapons() {
		int roll = rand.nextInt(100) + 1 + 33; // This is really ugly, but I feel lazy
		if (roll >= 34 && roll <= 41) {
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
		int roll = rand.nextInt(100)+1;
		if(roll >=1 && roll <=7){
			int ammo = rollDie(1, 6)*10;
			return "Ammunition: " +ammo + " Rounds of ammo.";
		}else if(roll >=8 && roll <=17){
			int ammo = rollDie(1, 10)+10;
			return "Pistol - Automatic and " +ammo +  " Pistol Bullets.";
		}else if(roll >=18 && roll <=30){
			int ammo = rollDie(1, 6)+6;
			return "Pistol - Revolver and " +ammo + " Revolver Bullets.";
		}else if(roll >=31 && roll <=36){
			int ammo = rollDie(5, 10);
			return "Sub Machinegun and " +ammo + " Sub Machinegun Bullets.";
		}else if(roll >=37 && roll <=41){
			int ammo = rollDie(3, 12);
			return "Rifle - Carbine and " +ammo + " Rifle Bullets.";
		}else if(roll >=42 && roll <=52){
			int ammo = rollDie(5, 10);
			return "Rifle - Automatic and " +ammo + " Rifle Bullets.";
		}else if(roll >=53 && roll <=59){
			int ammo = rollDie(3, 12);
			return "Rifle - Sport and " +ammo + " Rifle Bullets.";
		}else if(roll >=60 && roll <=69){
			int ammo = rollDie(1, 6)+5;
			return "Shotgun - Single and " +ammo + " Shotgun Shells.";
		}else if(roll >=70 && roll <=78){
			int ammo = rollDie(1, 10)+10;
			return "Shotgun - Automatic and " +ammo + " Shotgun Shells.";
		}else if(roll >=79 && roll <=81){
			int ammo = rollDie(1, 3)+1;
			return "Cannon and " +ammo + " Cannon Shells.";
		}else if(roll >=82 && roll <=90){
			int ammo = rollDie(1, 6);
			return "Grenade Launcher and " +ammo + " Launcher Grenades.";
		} else {
			int ammo = rollDie(1, 12)*10;
			return "Machinegun and " +ammo + " Machinegun Bullets.";
		}
	}

	public String advancedMeleeWeapons() {
		int roll = rand.nextInt(100)+1;
		if(roll >=1 && roll <=11){
			int ammo = rollDie(4, 10);
			return "Energy Baton with " +ammo + " Charges Remaining.";
		}else if(roll >=12 && roll <=23){
			int ammo = rollDie(4, 10);
			return "Shock Gloves with " +ammo + " Charges Remaining.";
		}else if(roll >=24 && roll <=35){
			int ammo = rollDie(4, 10);
			return "Shock-Field Glove with " +ammo + " Charges Remaining.";
		}else if(roll >=36 && roll <=47){
			int ammo = rollDie(4, 10);
			return "Stun Baton with " +ammo + " Charges Remaining.";
		}else if(roll >=48 && roll <=59){
			return "Vibro Dagger, " +((rollDie(1,100) <= 90) ? "Powered": "Unpowered" )+".";
		}else if(roll >=60 && roll <=71){
			return "Vibro Sword, " +((rollDie(1,100) <= 90) ? "Powered": "Unpowered" )+".";
		}else if(roll >=72 && roll <=81){
			return "Warp-Field Dagger, " +((rollDie(1,100) <= 90) ? "Powered": "Unpowered" )+".";
		}else if(roll >=82 && roll <=91){
			return "Warp-Field Mace, " +((rollDie(1,100) <= 90) ? "Powered": "Unpowered" )+".";
		} else {
			return "Warp-Field Sword, " +((rollDie(1,100) <= 90) ? "Powered": "Unpowered" )+".";
		}
	}

	public String advancedPistols() {
		int roll = rand.nextInt(100)+1;
		if(roll >=1 && roll <=15){
			int charge = rollDie(1, 12, 5);
			return "Gauss Machine Pistol with " +charge +" Charges Remaining.";
		}else if(roll >=16 && roll <=30){
			int charge = rollDie(1, 10, 5);
			return "Gauss Pistol Mk 1 with " +charge +" Charges Remaining.";
		}else if(roll >=31 && roll <=37){
			int charge = rollDie(1, 10, 5);
			return "Laser Pistol Mk 1 with " +charge +" Charges Remaining.";
		}else if(roll >=38 && roll <=54){
			int charge = rollDie(1, 10, 5);
			return "Laser Pistol Mk 2 with " +charge +" Charges Remaining.";
		}else if(roll >=55 && roll <=69){
			int charge = rollDie(1, 10, 5);
			return "Maser Pistol with " +charge +" Charges Remaining.";
		}else if(roll >=70 && roll <=84){
			int charge = rollDie(1, 10, 5);
			return "Plasma Pistol with " +charge +" Charges Remaining.";
		} else {
			int charge = rollDie(1, 10, 5);
			return "Stun Pistol with " +charge +" Charges Remaining.";
		}
	}

	public String advancedRifles() {
		int roll = rand.nextInt(100)+1;
		if(roll >=1 && roll <=5){
			int charge = rollDie(1, 10, 1);
			return "Blaster Rifle with " +charge + " Charges Remaining.";
		}else if(roll >=6 && roll <=16){
			int charge = rollDie(1, 10, 1);
			return "EMP Rifle with " +charge + " Charges Remaining.";
		}else if(roll >=17 && roll <=22){
			int charge = rollDie(1, 10, 1);
			return "Fusion Rifle with " +charge + " Charges Remaining.";
		}else if(roll >=23 && roll <=29){
			int charge = rollDie(1, 10, 1);
			return "Plasma Rifle with " +charge + " Charges Remaining.";
		}else if(roll >=30 && roll <=42){
			int charge = rollDie(2, 10, 5);
			return "Gauss Auto Rifle with " +charge + " Charges Remaining.";
		}else if(roll >=43 && roll <=57){
			int charge = rollDie(2, 12, 5);
			return "Gauss Rifle with " +charge + " Charges Remaining.";
		}else if(roll >=58 && roll <=68){
			int charge = rollDie(2, 10, 5);
			return "Laser Rifle with " +charge + " Charges Remaining.";
		}else if(roll >=69 && roll <=79){
			int charge = rollDie(2, 10, 5);
			return "Maser Rifle with " +charge + " Charges Remaining.";
		}else if(roll >=80 && roll <=84){
			int charge = rollDie(1, 10, 1);
			return "Radiation Rifle with " +charge + " Charges Remaining.";
		}else if(roll >=85 && roll <=92){
			int charge = rollDie(1, 10, 1);
			return "Stun Rifle with " +charge + " Charges Remaining.";
		} else {
			int charge = rollDie(1, 10, 1);
			return "X-Laser Rifle with " +charge + " Charges Remaining.";
		}
	}

	public String advancedArmor() {
		int roll = rand.nextInt(100)+1;
		if(roll >=1 && roll <=11){
			return "Ballistic Nylon Armor";
		}else if(roll >=12 && roll <=23){
			return "Metal Insert";
		}else if(roll >=24 && roll <=35){
			return "Plastic Plate";
		}else if(roll >=36 && roll <=47){
			return "Plastex";
		}else if(roll >=48 && roll <=59){
			return "Advanced Metal Armor";
		}else if(roll >=60 && roll <=70){
			return "LazAb Armor";
		}else if(roll >=71 && roll <=80){
			return "Environmental Armor";
		}else if(roll >=81 && roll <=85){
			return "Scout EMA";
		}else if(roll >=86 && roll <=90){
			return "Light EMA";
		}else if(roll >=91 && roll <=95){
			return "Medium EMA";
		} else {
			return "Heavy EMA";
		}
	}

	public String powerSources() {
		int roll = rand.nextInt(100)+1;
		if(roll >=1 && roll <=14){
			return "Power Cell";
		}else if(roll >=15 && roll <=29){
			return "Power Pack";
		}else if(roll >=30 && roll <=44){
			return "Power Clip";
		}else if(roll >=45 && roll <=59){
			return "Power Beltpack";
		}else if(roll >=60 && roll <=74){
			return "Power Backpack";
		}else if(roll >=75 && roll <=89){
			return "Minifusion Cell";
		}else if(roll >=90 && roll <=97){
			return "Plutonium Clip";
		}else if(roll >=98 && roll <=99){
			return "Radioactive Battery";
		} else {
			return "Universal Power Source";
		}
	}

	public String grenades() { //All grenades come in boxes of 1d4 grenades each
		int roll = rand.nextInt(100)+1;
		int nade = rollDie(1, 4);
		if(roll >=1 && roll <=6){
			return +nade + "x Blood Agent Grenade";
		}else if(roll >=7 && roll <=13){
			return +nade + "x Concussion Grenade";
		}else if(roll >=14 && roll <=19){
			return +nade + "x Dynamite";
		}else if(roll >=20 && roll <=25){
			return +nade + "x Energy Grenade";
		}else if(roll >=26 && roll <=31){
			return +nade + "x Frag Grenade";
		}else if(roll >=32 && roll <=37){
			return +nade + "x Inferno Grenade";
		}else if(roll >=38 && roll <=43){
			return +nade + "x Irritant Gas Grenade";
		}else if(roll >=44 && roll <=49){
			return +nade + "x Molotov Cocktail";
		}else if(roll >=50 && roll <=55){
			return +nade + "x Mutation Grenade";
		}else if(roll >=56 && roll <=61){
			return +nade + "x Nerve Gas Grenade";
		}else if(roll >=62 && roll <=67){
			return +nade + "x Photon Grenade - Lethal";
		}else if(roll >=68 && roll <=75){
			return +nade + "x Photon Grenade - Nonlethal";
		}else if(roll >=76 && roll <=82){
			return +nade + "x Plasma Grenade";
		}else if(roll >=83 && roll <=88){
			return +nade + "x Shock Grenade";
		} else {
			return +nade + "x Smoke Grenade";
		}
	}

	public String bombsAndMissiles() {
		int roll = rand.nextInt(100)+1;
		if(roll >=1 && roll <=8){
			return "Bio Toxin Bomb";
		}else if(roll >=9 && roll <=17){
			return "Concussion Bomb";
		}else if(roll >=18 && roll <=21){
			return "Light Anti-Tank Weapon";
		}else if(roll >=22 && roll <=31){
			return "Mutation Bomb";
		}else if(roll >=32 && roll <=41){
			return "Negation Bomb";
		}else if(roll >=42 && roll <=48){
			return "Plasma Bomb";
		}else if(roll >=49 && roll <=50){
			return "Radiation Bomb - Dirty";
		}else if(roll >=51 && roll <=59){
			return "Radiation Bomb";
		}else if(roll >=60 && roll <=69){
			return "Rocket Propelled Grenade Launcher";
		}else if(roll >=70 && roll <=81){
			int bomb = rollDie(1,4);
			return +bomb + "x Satchel A";
		}else if(roll >=82 && roll <=86){
			int bomb = rollDie(1,3);
			return +bomb + "x Satchel B";
		}else if(roll >=87 && roll <=90){
			int bomb = rollDie(1,2);
			return +bomb + "x Satchel C";
		}else if(roll >=91 && roll <=93){
			return "1x Satchel D";
		}else if(roll >=94 && roll <=95){
			int rocket = rollDie(2, 12, 1);
			return "Micro-Missile Launcher and a box containing " +rocket +" Micro-Missiles.";
		}else if(roll >=96 && roll <=97){
			int rocket = rollDie(2, 6, 1);
			return "Mini-Missile Launcher and a box containing " +rocket +" Mini-Missiles.";
		} else {
			int rocket = rollDie(1, 6, 1);
			return "Missile Launcher and a box containing " +rocket +" Missiles.";
		}
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
		//System.out.println((new LootTable()).advancedRifles());  //Testing tables
	}

}
