package org.example.SerializableUTF8;

import java.io.*;
import java.util.*;
// startByte = (n-1) * 30

// The first four bytes will contain the number of locations (bytes 0-3)
// the next four bytes will contain the start offset of the locations section (bytes 4-7)
// the next section of the file will contain the index (the index is 1692 bytes long. It will start at byte 8 and end at byte 1699
// the final section of the file will contain the location records (the data). It will start at byte 1700
public class Locations  implements Map<Integer, Location> {


    private static Map<Integer, Location> locations = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {


        ///////////////////////////////////////////////////
        // //   //  step 3
        ///////////////////////////////////////////////////////
        try (ObjectOutputStream localFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("locationsObjectStream.dat")))) {
            for (Location location : locations.values()) {
                localFile.writeObject(location);
            }
        }


    }

///////////////////////////////////////////////////////////////////////////////
     // step 4 or step 1 if you already have serialized .dat file
///////////////////////////////////////////////////////////////////////////////

    static {

//
        try (ObjectInputStream localFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream("locationsObjectStream.dat"))))
        {
            boolean eof = false;

            while (!eof){
                try {
                    Location location = (Location) localFile.readObject();
                    System.out.println("Read location ID = " + location.getLocationId() + " : " + location.getDescription());
                    System.out.println("Found " + location.getExits().size() + " exits");
                    System.out.println("\n" + location.getExits());
                    locations.put(location.getLocationId(), location);
                } catch (EOFException e){
                    eof = true;
                }
            }
        } catch (InvalidClassException e ) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException io){
            System.out.println("IO Exception" + io.getMessage());
        } catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException " + e.getMessage());
        }
}




//////////////////////////////////////////////////////////////////////////////////
//    //   // You need to read the text file first... capture the objects , then serialized them to file
//////////////////////////////////////////////////////////////////////////////////
//    //  //step 1.
//////////////////////////////////////////////////////////////////////////////////

    static {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("locations.txt")))) {
            scanner.useDelimiter(","); // our data is being separated by a comma
            while (scanner.hasNextLine()) {
                int loc = scanner.nextInt();
                scanner.skip(scanner.delimiter());
                String description = scanner.nextLine();
                System.out.println("Imported loc: " + loc + ": " + description);
                Map<String, Integer> tempExit = new HashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


////////////////////////////////////////
        //  // Now read the Exits
        //   // step 2
        ////////////////////////////////////////////////////

        try (BufferedReader directionsFile = new BufferedReader(new FileReader("directions.txt"))) {
            String input;

            while ((input = directionsFile.readLine()) != null) {
                String[] data = input.split(",");
                int loc = Integer.parseInt(data[0]);
                String direction = data[1];
                int destination = Integer.parseInt(data[2]);
                System.out.println(loc + ": " + direction + ": " + destination);
                Location location = locations.get(loc);
                location.addExit(direction, destination);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/////////////////////////////////////////////////////////////////////////



    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer integer, Location location) {
        return locations.put(integer, location);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> map) {

    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
