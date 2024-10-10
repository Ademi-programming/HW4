import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 240, 200, 450, 230, 230, 250};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 0, 0, 10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Witcher", "Thor"};
    public static int medicHeal = 100;
    public static int roundNumber;
    public static boolean isGolemAlive = true;
    public static boolean isLuckyAlive = true;
    public static boolean isWitcherAlive = true;
    public static boolean isThorAlive = true;
    public static boolean bossStunned = false;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        bossStunned = false;
        chooseBossDefence();
        if ( !bossStunned) {
            bossAttacks();
        }
        bossAttacks();
        heroesAttack();
        printStatistics();
        medicGivesHp();
        golemProtect();
        witcherRevive();
        thorStunner();
        luckyChance();


    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomInd = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomInd];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage *= coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void medicGivesHp() {
        if (heroesHealth[3] <= 0) {
            return;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i != 3 && heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                heroesHealth[i] += medicHeal;
                System.out.println("Медик вылечил: " + heroesAttackType + "На 50 хп");
                break;
            }
        }
    }

    public static void golemProtect() {
        if (!isGolemAlive) {
            return;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesAttackType[i] != "Golem") {
                heroesHealth[i] -= bossDamage / 5;
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
            }
        }
    }

    public static void luckyChance () {
        if (isLuckyAlive && bossDamage > 0) {
            Random random = new Random();
            boolean isLucky = random.nextBoolean();
            if (isLucky) {
                System.out.println("Лаки уклонился от ударов босса");
            }
        }
    }

    public static void witcherRevive () {
        if (isWitcherAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] == 0 && i != 3) {
                    heroesHealth[i] = 280;
                    isWitcherAlive = false;
                    System.out.println("Ведьма оживила" + heroesAttackType[i] + "Но пожертвовала собой");
                    break;
                }
            }
        }
    }

    public static void thorStunner () {
        if (isThorAlive && !bossStunned ) {
            Random random = new Random();
            boolean isStunned = random.nextBoolean();
            if (isStunned) {
                bossStunned = true;
                System.out.println("Босс был заглушен на 1 раунд");
            }
        }
    }


    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No Defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}