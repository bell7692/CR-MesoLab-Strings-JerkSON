package io.zipcoder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrganizedItemData {
//take list of items and organize data in the way we need them of item name, price and count of prices
    private String itemName;
    private HashMap<Double, Integer> priceAndOccurrence = new HashMap<Double, Integer>();


    public OrganizedItemData(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void addPrice(double price) {
        if(priceAndOccurrence.containsKey(price)) {
            int count = priceAndOccurrence.get(price) + 1;
            priceAndOccurrence.put(price, count);
        } else {
            priceAndOccurrence.put(price, 1);
        }

    }

    private int getTotalCount() {
        int count = 0;
        for(Integer value : priceAndOccurrence.values()) {
            count+=value;
        }
        return count;
    }
//
//    public Iterable<Map.Entry<Double, Integer>> getPriceAndOccurrences() {
//        return priceAndOccurrence.entrySet();
//    }

    public String getFormattedData() {
        StringBuilder sb = new StringBuilder();
        sb.append("name:\t" + itemName);
        sb.append("\t\tseen: " + getTotalCount());
        if (getTotalCount() > 1) {
            sb.append(" times\n");
        } else {
            sb.append(" time\n");
        }
        sb.append("=============\t\t=============\n");
        int count = priceAndOccurrence.entrySet().size();
        for(Map.Entry<Double, Integer> entry: priceAndOccurrence.entrySet()) {
            if (count > 0) {
                sb.append("Price:\t" + entry.getKey());
                sb.append("\t\tseen: " + entry.getValue());
                if (entry.getValue() > 1) {
                    sb.append(" times\n");
                }
                else {
                    sb.append(" time\n");
                }
                if(count > 0) {
                    sb.append("-------------\t\t-------------\n");
                    count--;
                }
            }
        }
        sb.append("\n");
        return sb.toString();
    }



}

