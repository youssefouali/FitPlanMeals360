package com.example.mealplanner.enums;

public enum FoodType {

    VEGETABLE("Vegetable"),
    FRUIT("Fruit"),
    GRAINS_BEANS_NUTS("Grains, Beans, Nuts"),
    MEAT_POULTRY("Meat, Poultry"),
    FISH_SEAFOOD("Fish, Sea food"),
    DAIRY("Dairy product"),
    OTHER("Other");

    public final String label;


    private FoodType(String label) {
        this.label = label;
    }

    public String getValue() {
        return this.name();
    }

    public String getLabel() {
        return label;
    }
}
