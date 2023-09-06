package org.example.SerializableUTF8;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Location  implements Serializable {
    private final int locationID;
    private final String description;
    private final Map<String, Integer> exits;
    private long serialVersionUID = 1L;
    private transient Scanner scanner = new Scanner(System.in);

    public Location(int locationID, String description, Map<String, Integer> exits){
        this.locationID = locationID;
        this.description = description;

        if (exits != null){
            this.exits = new LinkedHashMap<String, Integer>(exits);
        } else {
            this.exits = new LinkedHashMap<String, Integer>();
        }

        this.exits.put("Q", 0);
        this.exits.put("B", 33);
    }

    public void addExitManually(String direction1, int location1){

        exits.put(direction1, location1);
    }

    
    public void addExitOnConsole(String direction, int location){
        boolean loopAgain = true;
        while(loopAgain){
            System.out.println("Enter new direction");
            String newDirection = scanner.nextLine();

            System.out.println("Enter new location integer");
            int newLocation = scanner.nextInt();

            if (exits.containsKey(newDirection)){
                System.out.println("Exit already at this location.");
            }

            if (!exits.containsKey(newDirection)){
                exits.put(newDirection, newLocation);
                System.out.println(newDirection + " new Direction and new Location " + newLocation + " added");
            }
           


            System.out.println("Do you want to add any more Exits? y/n?");
            String continueAdding = scanner.nextLine().toLowerCase();

            if (continueAdding.equals("y") ){
                continue;
            } else {
                break;
            }

        }
       
    }


    public int getLocationId(){
        return locationID;
    }

    public String getDescription(){
        return description;
    }

    public Map<String, Integer> getExits(){
        return new LinkedHashMap<String, Integer>(exits);
    }

    protected void addExit(String direction, int location) {
        exits.put(direction, location);
    }
    
}
