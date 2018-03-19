package io.zipcoder;

import java.util.HashMap;

public class ItemOrganizer {

    public Iterable<OrganizedItemData> organizeItems(Iterable<Item> items) {
        HashMap<String, OrganizedItemData> organizedItemsMap = new HashMap<String, OrganizedItemData>();
        for (Item item : items) {
            if(organizedItemsMap.containsKey(item.getName())) {
                OrganizedItemData organizedItemData = organizedItemsMap.get(item.getName());
                organizedItemData.addPrice(item.getPrice());
            } else {
                OrganizedItemData organizedItemData = new OrganizedItemData(item.getName());
                organizedItemData.addPrice(item.getPrice());
                organizedItemsMap.put(item.getName(), organizedItemData);
            }
        }
        return organizedItemsMap.values();
    }
}
