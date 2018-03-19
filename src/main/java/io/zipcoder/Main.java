package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;


public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        System.out.println(output);;
        ErrorLogger errorData = new ErrorLogger();

        ItemParser itemParser = new ItemParser(errorData);

        ArrayList<Item> itemList = itemParser.findRawItemToArrayList(output);


        ItemOrganizer itemOrganizer = new ItemOrganizer();
        Iterable<OrganizedItemData> organizedItems = itemOrganizer.organizeItems(itemList);

        for(OrganizedItemData organizedItemData : organizedItems) {
            System.out.println(organizedItemData.getFormattedData());
        }
        String errorOutput= errorData.getFormattedError();
        System.out.println(errorOutput);

        }
        // TODO: parse the data in output into items, and display to console.




}
