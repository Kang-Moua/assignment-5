/*
CSC422 Section 100
Teamwork Assignment
 */

import java.util.Random;
import java.util.ArrayList;

public class ZombieWar {

    public static void main(String[] args) {

        //generates random number of survivors
        Random rand = new Random();
        int randSurvivors = rand.nextInt(20) + 1; // +1 to prevent zero bound (1-10 survivors)
        int numSoldiers = rand.nextInt(randSurvivors);
        int numTeachers = rand.nextInt(randSurvivors - numSoldiers);
        int numChildren = randSurvivors - numSoldiers - numTeachers;

        //generates random number of zombies
        int numZombies = rand.nextInt(20) + 1;
        int numCommonInfected = rand.nextInt(numZombies);
        int numTanks = numZombies - numCommonInfected;

        //add survivors and zombies into 2 arraylists
        ArrayList<Survivor> survivors = generateSurvivors(numSoldiers, numTeachers, numChildren);
        ArrayList<Zombie> zombies = generateZombies(numCommonInfected, numTanks);

        //prints initial information about zombie war
        printInitialMessage(survivors, zombies, numSoldiers, numTeachers, numChildren, numCommonInfected, numTanks);

        //while loop to print and make both survivors and zombies attack each other
        while (!survivors.isEmpty() && !zombies.isEmpty()) {
            attackSequence(survivors, zombies);
        }

        //prints out survivor count
        printFinalSurvivorCount(survivors);
    }

    //method to generate and return Arraylist filled with survivors based on the random integers
    static ArrayList<Survivor> generateSurvivors(int numSoldiers, int numTeachers, int numChildren) {
        ArrayList<Survivor> survivors = new ArrayList<Survivor>();
        for (int x = 0; x < numSoldiers; x++) {
            survivors.add(new Soldier(x + 1));
        }
        for (int x = 0; x < numTeachers; x++) {
            survivors.add(new Teacher(x + 1));
        }
        for (int x = 0; x < numChildren; x++) {
            survivors.add(new Child(x + 1));
        }

        return survivors;
    }

    //method to generate and return Arraylist filled with zombies based on the random integers
    static ArrayList<Zombie> generateZombies(int numCommonInfected, int numTanks) {
        ArrayList<Zombie> zombies = new ArrayList<Zombie>();
        for (int x = 0; x < numCommonInfected; x++) {
            zombies.add(new CommonInfected(x + 1));
        }
        for (int x = 0; x < numTanks; x++) {
            zombies.add(new Tank(x + 1));
        }

        return zombies;
    }

    //method that makes each survivor attack each zombie then vice versa for the zombies and repeat
    static void attackSequence(ArrayList<Survivor> survivors, ArrayList<Zombie> zombies) {
        for (int j = 0; j < survivors.size(); j++) {
            for (int i = 0; i < zombies.size(); i++) {
                survivors.get(j).attack(zombies.get(i)); // Survivors attack zombies first
                if (zombies.get(i).isDead() == true) {
                    System.out.println(survivors.get(j).getName() + " killed " + zombies.get(i).getName());
                    zombies.remove(i);
                    i--;
                } // Check/set if zombie is dead by checking if health is less
                // than zero
            }
        }
        for (int j = 0; j < zombies.size(); j++) {
            for (int i = 0; i < survivors.size(); i++) {
                zombies.get(j).attack(survivors.get(i)); // Zombies attack survivors
                if (survivors.get(i).isDead()) {
                    System.out.println(zombies.get(j).getName() + " killed " + survivors.get(i).getName());
                    survivors.remove(i);
                    i--;
                } // Check/set if survivor is dead by checking if health is
                // less than zero
            }
        }
    }

    //method to print information about the number of surviors and zombies before the war
    static void printInitialMessage(ArrayList<Survivor> survivors, ArrayList<Zombie> zombies, int numSoldiers,
            int numTeachers, int numChildren, int numCommonInfected, int numTanks) {
        int numSurvivors = survivors.size();
        int numZombies = zombies.size();

        System.out.println("We have " + numSurvivors + " survivors trying to make it to safety. (" + numChildren
                + " children, " + numTeachers + " teachers, " + numSoldiers + " soldiers)");
        System.out.println("But there are " + numZombies + " zombies waiting for them. (" + numCommonInfected
                + " common infected, " + numTanks + " tanks)");
    }

    //method to print survivor count at the end of the war
    static void printFinalSurvivorCount(ArrayList<Survivor> survivors) {
        if (survivors.size() <= 0) {
            System.out.println("None of the survivors made it.");
        } else {
            System.out.println(
                    "It seems " + (survivors.size()) + " have made it to safety.");
        }
    }
}

abstract class Survivor {

    private int id;
    private int health;
    private int attack;
    String type;

    public Survivor(int id, int health, int attack, String type) {
        this.id = id;
        this.health = health;
        this.attack = attack;
        this.type = type;
    }

    public int getID() {
        return id;
    }

    public void setHealth(int attack) {
        this.health = health - attack;
    }

    public void attack(Zombie zombie) {
        zombie.setHealth(attack);
    }

    public boolean isDead() {
        if (health <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return type + " " + id;
    }
}

class Soldier extends Survivor {

    public Soldier(int id) {
        super(id, 100, 10, "Soldier");
    }
}

class Teacher extends Survivor {

    public Teacher(int id) {
        super(id, 50, 5, "Teacher");
    }
}

class Child extends Survivor {

    public Child(int id) {
        super(id, 20, 2, "Child");
    }
}

abstract class Zombie {

    int id;
    int health;
    int attack;
    String type;

    public Zombie(int id, int health, int attack, String type) {
        this.id = id;
        this.health = health;
        this.attack = attack;
        this.type = type;
    }

    public int getHealth() {
        return this.health;
    }

    public int getID() {
        return id;
    }

    public void setHealth(int attack) {
        this.health = health - attack;
    }

    int getAttack() {
        return attack;
    }

    void attack(Survivor survivor) {
        survivor.setHealth(attack);
    }

    public boolean isDead() {
        if (health <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return type + " " + id;
    }
}

class CommonInfected extends Zombie {

    public CommonInfected(int id) {
        super(id, 30, 5, "Common Infected");
    }
}

class Tank extends Zombie {

    public Tank(int id) {
        super(id, 150, 20, "Tank");
    }
}