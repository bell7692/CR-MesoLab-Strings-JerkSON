package io.zipcoder;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {


    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString) {
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }

    public ArrayList<String> parseRawDataIntoStringArray(String rawData) {
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawData);
        return response;
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem) {
        String stringPattern = "[@|^|*|%|!|;]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawItem);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException {

        String name = checkName(rawItem);
        Double price = Double.valueOf(checkPrice(rawItem));
        String type = checkType(rawItem);
        String expiration = checkExpiration(rawItem);

        return new Item(name, price, type, expiration);

    }

    public ArrayList<Item> findRawItemToArrayList(String rawData) throws ItemParseException {
        ArrayList<String> temp = parseRawDataIntoStringArray(rawData);
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        for (int i = 0; i < temp.size(); i++) {
            itemArrayList.add(parseStringIntoItem(temp.get(i)));
        }
        return itemArrayList;
    }


    public HashMap<String, Integer> findBasedOnName(String rawData, String whatToSearch) throws ItemParseException {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        ArrayList<Item> tempItems = findRawItemToArrayList(rawData);
        for (int i = 0; i < tempItems.size(); i++) {
            if (tempItems.get(i).getName().equalsIgnoreCase(whatToSearch)) {
                incrementValue(hashMap, String.valueOf(tempItems.get(i).getPrice()));
            }
        }
        return hashMap;
    }

    public void incrementValue(Map<String, Integer> map, String key) {
        Integer count = map.get(key);
        if (count == null) {
            count = 0;
        }
        map.put(key, count + 1);
    }

    public Integer getTotalCount (String rawData, String whatToSearch) throws ItemParseException {
        HashMap<String, Integer> hashMap = findBasedOnName(rawData,whatToSearch);
        Integer totalCount = 0;
        for (Map.Entry<String, Integer> entry: hashMap.entrySet()){
            totalCount += entry.getValue();
        }
        return totalCount;

    }

    public String printOut (String rawData) throws ItemParseException {
        ArrayList<Item> tempItems = findRawItemToArrayList(rawData);
        HashMap<String, Integer> milk = findBasedOnName(rawData, "milk");
        HashMap<String, Integer> bread = findBasedOnName(rawData, "bread");
        HashMap<String, Integer> cookies = findBasedOnName(rawData, "cookies");
        HashMap<String, Integer> apples = findBasedOnName(rawData, "apples");

        StringBuilder sb = new StringBuilder();
        sb.append("name:\tMilk\t\tseen: "+ )
//
//        name:    Milk 		 seen: 6 times
//                ============= 	 	 =============
//        Price: 	 3.23		 seen: 5 times
//                -------------		 -------------
//                Price:   1.23		 seen: 1 time
//
//        name:   Bread		 seen: 6 times
//                =============		 =============
//        Price:   1.23		 seen: 6 times
//                -------------		 -------------
//
//                name: Cookies     	 seen: 8 times
//                =============     	 =============
//        Price:   2.25        seen: 8 times
//                -------------        -------------
//
//                name:  Apples     	 seen: 4 times
//                =============     	 =============
//        Price:   0.25     	 seen: 2 times
//                -------------     	 -------------
//                Price:   0.23  	 	 seen: 2 times
//
//        Errors         	 	 seen: 4 times


        return rawData;
    }


    public static void main(String[] args) throws ItemParseException {
        String rawMultipleItems = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##"
                + "naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##"
                + "NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##"
                + "NAMe:BrEAD;price:1.25;type:Food;expiration:2/25/2016##"
                + "NAMe:chocolate;price:1.25;type:Food;expiration:2/25/2016##";
        ;
        ItemParser itemParser = new ItemParser();
        System.out.println(itemParser.findBasedOnName(rawMultipleItems, "bread"));
        System.out.println(itemParser.getTotalCount(rawMultipleItems, "bread"));

    }



    public String fixCookie(String input) {
        Pattern patternCookies = Pattern.compile("[Cc][Oo0][Oo0][Kk][Ii][Ee][Ss]");
        Matcher matcherCookies = patternCookies.matcher(input);
        return matcherCookies.replaceAll("cookies");
    }

    public String checkName(String input) throws ItemParseException {
        String newInput = fixCookie(input);
        Pattern patternName = Pattern.compile("([Nn]..[Ee]:)(\\w+)");
        Matcher matcherName = patternName.matcher(newInput);

        if (matcherName.find())
            return matcherName.group(2).toLowerCase();
        else throw new ItemParseException();
    }

    public String checkPrice(String input) throws ItemParseException {
        Pattern patternPrice = Pattern.compile("([Pp]...[Ee]:)(\\d\\.\\d{2})");
        Matcher matcherPrice = patternPrice.matcher(input);

        if (matcherPrice.find())
            return matcherPrice.group(2).toLowerCase();
        else throw new ItemParseException();
    }

    public String checkType(String input) throws ItemParseException {
        Pattern patternType = Pattern.compile("([Tt]..[Ee]:)(\\w+)");
        Matcher matcherType = patternType.matcher(input);

        if (matcherType.find())
            return matcherType.group(2).toLowerCase();
        else throw new ItemParseException();
    }

    public String checkExpiration(String input) throws ItemParseException {
        Pattern patternExpiration = Pattern.compile("([Ee]........[Nn]:)(\\d\\/\\d{2}\\/\\d{4})");
        Matcher matcherExpiration = patternExpiration.matcher(input);

        if (matcherExpiration.find())
            return matcherExpiration.group(2).toLowerCase();
        else throw new ItemParseException();
    }
}


