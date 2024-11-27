package coffeemachinepgm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CoffeeMaker {
    private Map<String, Recipe> recipeBook = new HashMap<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private final int MAX_RECIPES = 3;

    public CoffeeMaker() {
        // Initialize inventory
        inventory.put("coffee", 0);
        inventory.put("milk", 0);
        inventory.put("sugar", 0);
        inventory.put("chocolate", 0);
    }

    // Start the coffee maker and wait for user input
    public void waitForInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Coffee Maker Menu ---");
            System.out.println("1) Add Recipe");
            System.out.println("2) Delete Recipe");
            System.out.println("3) Edit Recipe");
            System.out.println("4) Add Inventory");
            System.out.println("5) Check Inventory");
            System.out.println("6) Purchase Beverage");
            System.out.println("7) Exit");
            System.out.print("Choose an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        addRecipe(scanner);
                        break;
                    case 2:
                        deleteRecipe(scanner);
                        break;
                    case 3:
                        editRecipe(scanner);
                        break;
                    case 4:
                        addInventory(scanner);
                        break;
                    case 5:
                        checkInventory();
                        break;
                    case 6:
                        purchaseBeverage(scanner);
                        break;
                    case 7:
                        System.out.println("Exiting Coffee Maker. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Add a new recipe
    private void addRecipe(Scanner scanner) {
        if (recipeBook.size() >= MAX_RECIPES) {
            System.out.println("Cannot add more recipes. Max limit reached.");
            return;
        }
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();
        if (recipeBook.containsKey(name)) {
            System.out.println("Recipe name already exists.");
            return;
        }

        System.out.print("Enter price: ");
        int price = validatePositiveInput(scanner);
        System.out.print("Enter units of coffee: ");
        int coffee = validatePositiveInput(scanner);
        System.out.print("Enter units of milk: ");
        int milk = validatePositiveInput(scanner);
        System.out.print("Enter units of sugar: ");
        int sugar = validatePositiveInput(scanner);
        System.out.print("Enter units of chocolate: ");
        int chocolate = validatePositiveInput(scanner);

        recipeBook.put(name, new Recipe(name, price, coffee, milk, sugar, chocolate));
        System.out.println("Recipe added successfully.");
    }

    // Delete a recipe
    private void deleteRecipe(Scanner scanner) {
        if (recipeBook.isEmpty()) {
            System.out.println("No recipes to delete.");
            return;
        }
        displayRecipes();
        System.out.print("Select a recipe to delete by number: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (choice < 1 || choice > recipeBook.size()) {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        String recipeNameToDelete = (String) recipeBook.keySet().toArray()[choice - 1];
        recipeBook.remove(recipeNameToDelete);
        System.out.println("Recipe " + recipeNameToDelete + " deleted.");
    }

    // Edit a recipe
    private void editRecipe(Scanner scanner) {
        if (recipeBook.isEmpty()) {
            System.out.println("No recipes to edit.");
            return;
        }
        displayRecipes();
        System.out.print("Select a recipe to edit by number: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (choice < 1 || choice > recipeBook.size()) {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        String recipeNameToEdit = (String) recipeBook.keySet().toArray()[choice - 1];
        Recipe recipe = recipeBook.get(recipeNameToEdit);

        System.out.println("Editing recipe: " + recipeNameToEdit);
        System.out.print("Enter new price: ");
        int price = validatePositiveInput(scanner);
        System.out.print("Enter new units of coffee: ");
        int coffee = validatePositiveInput(scanner);
        System.out.print("Enter new units of milk: ");
        int milk = validatePositiveInput(scanner);
        System.out.print("Enter new units of sugar: ");
        int sugar = validatePositiveInput(scanner);
        System.out.print("Enter new units of chocolate: ");
        int chocolate = validatePositiveInput(scanner);

        recipe.setPrice(price);
        recipe.setCoffee(coffee);
        recipe.setMilk(milk);
        recipe.setSugar(sugar);
        recipe.setChocolate(chocolate);
        System.out.println("Recipe updated.");
    }

    // Add inventory
    private void addInventory(Scanner scanner) {
        System.out.print("Enter units of coffee to add: ");
        int coffee = validatePositiveInput(scanner);
        System.out.print("Enter units of milk to add: ");
        int milk = validatePositiveInput(scanner);
        System.out.print("Enter units of sugar to add: ");
        int sugar = validatePositiveInput(scanner);
        System.out.print("Enter units of chocolate to add: ");
        int chocolate = validatePositiveInput(scanner);

        inventory.put("coffee", inventory.get("coffee") + coffee);
        inventory.put("milk", inventory.get("milk") + milk);
        inventory.put("sugar", inventory.get("sugar") + sugar);
        inventory.put("chocolate", inventory.get("chocolate") + chocolate);
        System.out.println("Inventory updated.");
    }

    // Check inventory
    private void checkInventory() {
        System.out.println("Inventory:");
        inventory.forEach((ingredient, amount) ->
            System.out.println(ingredient + ": " + amount + " units")
        );
    }

    // Purchase a beverage
    private void purchaseBeverage(Scanner scanner) {
        if (recipeBook.isEmpty()) {
            System.out.println("No recipes available.");
            return;
        }
        displayRecipes();
        System.out.print("Select a beverage to purchase by number: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (choice < 1 || choice > recipeBook.size()) {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        String selectedRecipeName = (String) recipeBook.keySet().toArray()[choice - 1];
        Recipe selectedRecipe = recipeBook.get(selectedRecipeName);

        System.out.print("Enter money to insert: ");
        int money = validatePositiveInput(scanner);

        if (money < selectedRecipe.getPrice()) {
            System.out.println("Not enough money. Your money will be returned.");
            return;
        }

        if (inventory.get("coffee") < selectedRecipe.getCoffee() ||
            inventory.get("milk") < selectedRecipe.getMilk() ||
            inventory.get("sugar") < selectedRecipe.getSugar() ||
            inventory.get("chocolate") < selectedRecipe.getChocolate()) {
            System.out.println("Not enough ingredients. Your money will be returned.");
            return;
        }

        inventory.put("coffee", inventory.get("coffee") - selectedRecipe.getCoffee());
        inventory.put("milk", inventory.get("milk") - selectedRecipe.getMilk());
        inventory.put("sugar", inventory.get("sugar") - selectedRecipe.getSugar());
        inventory.put("chocolate", inventory.get("chocolate") - selectedRecipe.getChocolate());

        int change = money - selectedRecipe.getPrice();
        System.out.println("Beverage dispensed. Change: " + change);
    }

    // Display available recipes
    private void displayRecipes() {
        int index = 1;
        for (String recipeName : recipeBook.keySet()) {
            System.out.println(index + ") " + recipeName);
            index++;
        }
    }

    // Validate positive integer input
    private int validatePositiveInput(Scanner scanner) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= 0) {
                    return input;
                }
                System.out.print("Invalid input. Enter a positive number: ");
            } catch (Exception e) {
                System.out.print("Invalid input. Enter a positive number: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Recipe class
    private static class Recipe {
        private String name;
        private int price;
        private int coffee;
        private int milk;
        private int sugar;
        private int chocolate;

        public Recipe(String name, int price, int coffee, int milk, int sugar, int chocolate) {
            this.name = name;
            this.price = price;
            this.coffee = coffee;
            this.milk = milk;
            this.sugar = sugar;
            this.chocolate = chocolate;
        }

        public int getPrice() { return price; }
        public int getCoffee() { return coffee; }
        public int getMilk() { return milk; }
        public int getSugar() { return sugar; }
        public int getChocolate() { return chocolate; }

        public void setPrice(int price) { this.price = price; }
        public void setCoffee(int coffee) { this.coffee = coffee; }
        public void setMilk(int milk) { this.milk = milk; }
        public void setSugar(int sugar) { this.sugar = sugar; }
        public void setChocolate(int chocolate) { this.chocolate = chocolate; }
    }

    // Main method
    public static void main(String[] args) {
        CoffeeMaker coffeeMaker = new CoffeeMaker();
        coffeeMaker.waitForInput();
    }
}
