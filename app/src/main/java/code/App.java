/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package code;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws IOException, CsvValidationException {

        //double epsilon = 0.01;
        int fakeEpsilon = 8000;

        //Importing dataset
        InputStreamReader streamReader = new InputStreamReader(App.class.getClassLoader().getResourceAsStream("listings.csv"));
        CSVReader reader = new CSVReader(streamReader);
        String[] nextLine;
        List<String> stringArr = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            stringArr.add(nextLine[6]);
        }

        //Type conversion methods
        HashMap<String, Integer> stringToInt = stringToInt(stringArr);
        List<Integer> listToInt = listToInt(stringArr, stringToInt);

        //Heavy hitters implementation
        NewHeavyHitters hh = new NewHeavyHitters(fakeEpsilon);
        for(int item : listToInt){
            hh.update(item,1);
        }

        List<Integer> hhList = new ArrayList<>();
        hh.query(hhList);

        //Postprocessing
        List<String> listToString = listToString(stringToInt, hhList);

        //Print out
        for(String item : listToString){
            System.out.println(item);
        }



















        /*
        // ------------Count-Min Sketch------------

        // Creating a hashmap and then keySet just to only get unique values for displaying (just for running through a unique array)
        Map<String, Integer> stringToIntMap = new HashMap<>();
        for(String elem : stringArr){
            Integer mapIndex = 0;
            stringToIntMap.put(elem, mapIndex);
        }
        Set<String> keySet = stringToIntMap.keySet();


        // Add item frequency to the count-min sketch data structure
        CountMinSketch countMinSketch = new CountMinSketch(20, 15, 1000003);
        for (String neighborhood : stringArr) {
            countMinSketch.add(neighborhood, 1);
        }

        //Calculating actual frequency
        Map<String, Long> actualFrequency = stringArr.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        int completeNumber = 0;
        //Printing Estimated frequencies and Actual frequencies
        for(String neighborhood : keySet){
            System.out.println(" " + neighborhood + "-- Estimated Frequency: " + countMinSketch.estimateCount(neighborhood)
            + " --ACTUAL FREQUENCY: " + actualFrequency.get(neighborhood));

            completeNumber += actualFrequency.get(neighborhood);
        }

        System.out.println("------");
        System.out.println("Complete number of neighborhoods -> " + completeNumber);

        //---------------Heavy Hitters -----------------------

        ArrayList<String> heavyHittersList = new ArrayList<>();
        HeavyHittersCountMin heavyHitters = new HeavyHittersCountMin(5000, countMinSketch, heavyHittersList);
        for(String neighborhood : keySet){
            heavyHitters.update(neighborhood);
        }

        //Show list of heavy hitters
        heavyHittersList = heavyHitters.query();
        System.out.println("-- List of heavy hitters -- ");
        for(String elem : heavyHittersList){
            System.out.println(elem);
        }

        /*---------------Bloom filter-------------------*/

        /*
        BloomFilter bloomFilter = new BloomFilter(120, 5);
        //Adding elements to bloom filter
        for(String neighborhood : stringArr){
            bloomFilter.add(neighborhood);
        }

        System.out.println(bloomFilter.contains("Islington"));
*/
    }

    public static List<Integer> listToInt(List<String> stringArr, HashMap<String, Integer> stringToInt){
        ArrayList<Integer> output = new ArrayList<>();
        for(String s : stringArr){
            output.add(stringToInt.get(s));
        }
        return output;
    }


    public static HashMap<String, Integer> stringToInt(List<String> stringArr){
        int index = 0;
        HashMap<String, Integer> hashMap = new HashMap<>();
        for(String s : stringArr){
            if(!hashMap.containsKey(s)){
                hashMap.put(s, index);
                index++;
            }
        }
        return hashMap;
        }


    public static List<String> listToString(HashMap<String, Integer> stringToInt, List<Integer> listToInt){
        ArrayList<String> output = new ArrayList<>();
        HashMap<Integer,String> tempHashMap = new HashMap<>();
        for(String s : stringToInt.keySet()){
            tempHashMap.put(stringToInt.get(s), s);
        }

        for(int i : listToInt){
            output.add(tempHashMap.get(i));
        }

        return output;
    }

    }
