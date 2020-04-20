package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;
    private Action state;

    private enum Action {
        STANDBY, BUYING, FILLING_WATER, FILLING_MILK, FILLING_BEANS, FILLING_CUPS, OFF
    }

    private enum Coffee {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        private final int water;
        private final int milk;
        private final int beans;
        private final int cost;

        Coffee(int water, int milk, int beans, int cost) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cost = cost;
        }
    }

    private CoffeeMachine() {
        this.water = 400;
        this.milk = 540;
        this.beans = 120;
        this.cups = 9;
        this.money = 550;
        this.state = Action.STANDBY;
    }

    public void handleInput(String input) {
        switch(this.state) {
            case STANDBY:
                handleStandby(input);
                break;
            case BUYING:
                handleBuying(input);
                break;
            case FILLING_WATER:
                this.water = this.water + Integer.valueOf(input);
                this.state = Action.FILLING_MILK;
                break;
            case FILLING_MILK:
                this.milk = this.milk + Integer.valueOf(input);
                this.state = Action.FILLING_BEANS;
                break;
            case FILLING_BEANS:
                this.beans = this.beans + Integer.valueOf(input);
                this.state = Action.FILLING_CUPS;
                break;
            case FILLING_CUPS:
                this.cups = this.cups + Integer.valueOf(input);
                this.state = Action.STANDBY;
                break;
            default:
                break;
        }
    }

    private void handleStandby(String input) {
        switch (input) {
            case "buy":
                this.state = Action.BUYING;
                break;
            case "fill":
                this.state = Action.FILLING_WATER;
                break;
            case "take":
                System.out.println("I gave you $" + this.money);
                this.money = 0;
                break;
            case "remaining":
                this.printInfo();
                break;
            case "exit":
                this.state = Action.OFF;
                break;
        }
    }

    private void handleBuying(String input) {
        switch (input) {
            case "1":
                tryBuying(Coffee.ESPRESSO);
                break;
            case "2":
                tryBuying(Coffee.LATTE);
                break;
            case "3":
                tryBuying(Coffee.CAPPUCCINO);
                break;
            default:
                this.state = Action.STANDBY;
                break;
        }
    }

    private void tryBuying(Coffee COFFEE) {
        if (this.water < COFFEE.water) {
            System.out.println("Sorry, not enough water!");
        } else if (this.milk < COFFEE.milk) {
            System.out.println("Sorry, not enough milk!");
        } else if (this.beans < COFFEE.beans) {
            System.out.println("Sorry, not enough beans!");
        } else if (this.cups < 1) {
            System.out.println("Sorry, not enough cups!");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            this.water = this.water - COFFEE.water;
            this.milk = this.milk - COFFEE.milk;
            this.beans = this.beans - COFFEE.beans;
            this.cups = this.cups - 1;
            this.money = this.money + COFFEE.cost;
        }
        this.state = Action.STANDBY;
    }

    private void showMessage() {
        switch (state) {
            case STANDBY:
                System.out.println("Write Action (buy, fill, take, remaining, exit):");
                break;
            case BUYING:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                break;
            case FILLING_WATER:
                System.out.println("Write how many ml of water do you want to add:");
                break;
            case FILLING_MILK:
                System.out.println("Write how many ml of milk do you want to add:");
                break;
            case FILLING_BEANS:
                System.out.println("Write how many grams of coffee beans do you want to add:");
                break;
            case FILLING_CUPS:
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;
        }
    }

    private void printInfo() {
        System.out.println("The coffee machine has:");
        System.out.println(this.water + " of water");
        System.out.println(this.milk + " of milk");
        System.out.println(this.beans + " of coffee beans");
        System.out.println(this.cups + " of disposable cups");
        System.out.println(this.money + " of money");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        while (coffeeMachine.state != Action.OFF) {
            coffeeMachine.showMessage();
            String input = scanner.nextLine();
            coffeeMachine.handleInput(input);
        }
    }
}