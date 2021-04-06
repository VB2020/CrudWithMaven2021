package view;


import model.Region;

import java.util.Comparator;
import java.util.List;

public class RegionView {
    public static final String deleted = "This region was deleted";

    public static void show(){
            System.out.println(ForConsole.BORDER.getMessage());
            String mainMessage = "Choose an action with regions:\n" +
                    " 1. Create\n" +
                    " 2. Edit\n" +
                    " 3. Delete\n" +
                    " 4. Show regions\n" +
                    " 5. Main menu\n" +
                    " 6. Exit";
            System.out.println(mainMessage);
            System.out.println(ForConsole.BORDER.getMessage());

    }

    public static void create(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter region name: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void editId(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter region id: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void editName(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter new region name: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showRegionsList(List<Region> regions){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("regions list:");
        System.out.println(ForConsole.BORDER.getMessage());
        regions.stream().filter((region) -> !region.getName().equals(RegionView.deleted)).sorted(
                Comparator.comparing(Region::getId)).forEach(
                (region) -> System.out.println("Id: " + region.getId() + " | Name: " + region.getName())
        );
    }

}
