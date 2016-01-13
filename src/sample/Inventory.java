package sample;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Inventory {

    private int maxSize;
    private TreeMap<String,List<Item>> itemList;

    public Inventory(){
        itemList = new TreeMap<String,List<Item>>();
    }

    /*
        add an item into the invotory
        @param item to add
     */
    public void addItem(Item item){
        // Si l'objet est déjà présent dans l'inventaire
        if(itemList.containsKey(item.getName())){
            List<Item> l = itemList.get(item.getName());
            l.add(item);
        }else{
            List<Item> l = new ArrayList<>();
            l.add(item);
            itemList.put(item.name,l);
        }
    }

    public void deleteItem(Item item){
        List<Item> l = itemList.get(item.getName());
        l.remove(item);
    }

    /*
        returns the quantity of an item included in the inventory
    public int getQuantity(Item item){
        return itemList.get(item.getName()).size();
    }

}
