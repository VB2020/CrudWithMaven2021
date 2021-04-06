package controller;


import IoUtils.RegionIO;
import model.Region;
import repository.impl.RegionRepositoryImpl;
import view.ForConsole;
import view.RegionView;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RegionController {

    public static void startM() throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        try {
            do {
                RegionView.show();
                String response = sc.next();
                switch (response) {
                    case "1":
                        create();
                        break;
                    case "2":
                        edit();
                        break;
                    case "3":
                        delete();
                        break;
                    case "4":
                        showAll();
                        break;
                    case "5":
                        isExit = true;
                        MainController.startM();
                        break;
                    case "6":
                        isExit = true;
                        break;
                    default:
                        System.out.println(ForConsole.ERROR_INPUT.getMessage());
                        break;
                }
            } while (!isExit);
            sc.close();
        }
        catch (NumberFormatException nfe){
            System.out.println("It's not a number !!!");
        }
    }

    public static void create() throws Exception {
        Scanner sc = new Scanner(System.in);
        RegionRepositoryImpl lR = new RegionRepositoryImpl();
        RegionView.create();
        String name = sc.next();
        int demonId = RegionIO.getMaxId(lR.getAll());
        if (demonId != 0) {
            if (lR.getById(demonId).getName().equals(RegionView.deleted)) {
                lR.save(new Region(demonId + 1, name));
                lR.deleteById(demonId);
            }
            else {
                lR.save(new Region(demonId + 1, name));
            }
        }
        else {
            lR.save(new Region(demonId + 1, name));
        }
    }

    public static void edit() throws Exception {
        Scanner sc = new Scanner(System.in);
        RegionRepositoryImpl lR = new RegionRepositoryImpl();
        boolean isExit = false;
        List<Region> Regions = lR.getAll();
        RegionView.showRegionsList(Regions);
        try {
            do {
                RegionView.editId();
                try {
                    int id = sc.nextInt();
                    int maxId = RegionIO.getMaxId(Regions);
                    if (id > 0 && id <= maxId) {
                        Region Region = lR.getById(id);
                        if (!Region.getName().equals(RegionView.deleted)) {
                            RegionView.editName();
                            String name = sc.next();
                            Region.setName(name);
                            lR.update(Region);
                            isExit = true;
                        }
                        else {
                            System.out.println("Id not exist !!!!");
                        }
                    }
                } catch (NullPointerException e) {
                    System.out.println("Id not exist !!!!");
                }
            }while (!isExit);
        } catch (InputMismatchException nfe) {
            System.out.println("It's not a number !!!");
        }
    }

    public static void delete() throws Exception {
        Scanner sc = new Scanner(System.in);
        RegionRepositoryImpl lR = new RegionRepositoryImpl();
        boolean isExit = false;
        List<Region> Regions = lR.getAll();
        RegionView.showRegionsList(Regions);
        try {
            do {
                RegionView.editId();
                try{
                    int id = sc.nextInt();
                    int maxId = RegionIO.getMaxId(Regions);
                    Region Region = lR.getById(id);
                    if (id > 0 && id <= maxId && !Region.getName().equals(RegionView.deleted)) {
                        lR.deleteById(id);
                        isExit = true;
                    }
                    else {
                        System.out.println("Id not exist !!!!");
                    }
                } catch (NullPointerException e) {
                    System.out.println("Id not exist !!!!");
                }
            }while (!isExit);
        }
        catch (InputMismatchException nfe){
            System.out.println("It's not a number !!!");
        }
    }

    public static void showAll() throws FileNotFoundException {
        RegionRepositoryImpl lR = new RegionRepositoryImpl();
        RegionView.showRegionsList(lR.getAll());
    }

}
