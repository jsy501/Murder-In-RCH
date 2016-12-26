package com.noname.mrch.gameobject;

import com.badlogic.gdx.utils.Array;
import com.noname.mrch.gui.Gui;

/**
 * Holds items and clues found by the player
 */

public class NoteBook {
    private Array<Clue> clueList;
    private Array<Item> itemList;

    public NoteBook(){
        clueList = new Array<>();
        itemList = new Array<>();
//
//        Item testItem1 = new Item(1, "item1", "", false);
//        Item testItem2 = new Item(1, "item2", "", false);
//        Item testItem3 = new Item(1, "item3", "", false);
//        Item testItem4 = new Item(1, "item4", "", false);
//        Item testItem5 = new Item(1, "item5", "", false);
//        Item testItem6 = new Item(1, "item6", "", false);
//        Item testItem7 = new Item(1, "item7", "", false);
//        Item testItem8 = new Item(1, "item8", "", false);
//        Item testItem9 = new Item(1, "item9", "", false);
//        Item testItem10 = new Item(1, "item10", "", false);
//
//        itemList.addAll(testItem1, testItem2, testItem3, testItem4, testItem5, testItem6, testItem7, testItem8, testItem9, testItem10);
    }

    public void addClue(Clue clue){
        if (clueList.contains(clue, false)){
            throw new RuntimeException("Clue already in notebook");
        }
        clueList.add(clue);
    }

    public void addItem(Item item){
        if (itemList.contains(item, false)){
            throw new RuntimeException("Item already in notebook");
        }
        itemList.add(item);
    }

    public Clue getClue(int id) {
        for (int i =0; i < clueList.size; i++){
            if (clueList.get(i).getId() == id){
                return clueList.get(i);
            }
        }
        throw new RuntimeException("Clue not in notebook");
    }


    public Item getItem(int id){
        for (int i =0; i < itemList.size; i++){
            if (itemList.get(i).getId() == id){
                return itemList.get(i);
            }
        }
        throw new RuntimeException("Item not in notebook");
    }

    public void removeItem(Item item){
        itemList.removeValue(item, false);
    }

    public boolean contains(Item item) {
        return (itemList.contains(item, false));
    }

    public boolean contains(Clue clue) {
        return (clueList.contains(clue, false));
    }

    public Array<Clue> getClueList() {
        return clueList;
    }

    public Array<Item> getItemList() {
        return itemList;
    }
}
