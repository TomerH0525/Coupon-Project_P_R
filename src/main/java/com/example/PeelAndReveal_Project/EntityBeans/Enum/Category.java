package com.example.PeelAndReveal_Project.EntityBeans.Enum;

public enum Category {
    FOOD(0),VACATION(1),ELECTRONICS(2),CARS(3),BABY(4),HOUSE(5),PHONE(6);

    private Category(int i) {

    }

    public static Category getCategoryByNumber(int number) {
        for (Category enumMember : Category.values()) {
            if (enumMember.ordinal() == number) {
                return enumMember;
            }
        }
        return null;
    }
}
