package com.noname.mrch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.Array;
import com.noname.mrch.GUI.Gui;
import com.noname.mrch.gameobject.Clue;
import com.noname.mrch.gameobject.GameActor;
import com.noname.mrch.gameobject.GameCharacter;
import com.noname.mrch.gameobject.Item;
import com.noname.mrch.gameobject.NoteBook;
import com.noname.mrch.gameobject.Room;
import com.noname.mrch.helper.AssetLoader;

import java.util.Iterator;

/**
 * Initialises and holds all the game objects
 */

public class GameWorld {
    public static GameActor touchedActor = null;

    private NoteBook notebook;

    private Room currentRoom;
    private Gui gui;

    private CharacterManager characterManager;
    private ItemManager itemManager;
    private ClueManager clueManager;
    private RoomManager roomManager;

    public GameWorld(AssetLoader assetLoader) {
        characterManager = new CharacterManager(assetLoader);
        itemManager = new ItemManager(assetLoader, characterManager);
        clueManager = new ClueManager(assetLoader, characterManager);
        roomManager = new RoomManager(assetLoader);

        Array<Item> itemArray = itemManager.getItemArray();
        Array<GameCharacter> characterArray = characterManager.getCharacterArray();
        Array<Room> roomArray = roomManager.getRoomArray();

        // clue distribution
        roomManager.getLockedRoom().addClue(clueManager.getMotiveClue());

        // item distribution
        itemArray.add(itemManager.getKey());
        for (int i = 0; i < characterManager.getCharacterArray().size; i++) {
            //assign every item but the first one to every character
            //the first item is to be found in a room
            characterManager.getCharacterArray().get(i).addItem(itemManager.getItemArray().get(i+1));

            //create links between items
            itemManager.getItemArray().get(i).setReturnItem(itemManager.getItemArray().get(i+1));
        }
        itemArray.removeValue(itemManager.getKey(), false);
        roomArray.random().addItem(itemArray.get(0));


        // character distribution
        characterArray.shuffle();
        int cnt = 0;
        Array.ArrayIterator<GameCharacter> iterator = new Array.ArrayIterator<>(characterArray);
        while(iterator.hasNext()){
            int roomIndex = cnt % (Room.ROOM_COUNT-1);
            if (!roomArray.get(roomIndex).isLocked()){
                roomArray.get(roomIndex).addCharacter(iterator.next());
            }
            cnt++;
        }


        System.out.println("Character list: " + characterManager.getCharacterArray());
        System.out.println("Murderer: " + characterManager.getMurderer());
        System.out.println("Victim: " + characterManager.getVictim());
        System.out.println("Item list: " + itemManager.getItemArray());
        System.out.println("MOTIVE Clue: " + clueManager.getMotiveClue());
        System.out.println("WEAPON Clue: " + clueManager.getWeaponClue());
        System.out.println("APPEARANCE Clues: " + clueManager.getAppearanceClue());
        System.out.println("Room list: " + roomManager.getRoomArray());


        notebook = new NoteBook();

        currentRoom = roomManager.getRoomArray().first();
    }

    public void setGui(Gui gui){
        this.gui = gui;
    }

    public void update(float delta){
        // input check
        if (touchedActor != null) {
            Array actorArray = currentRoom.getCurrentStage().getActors();
            for (int i = 0; i < actorArray.size; i++) {
                GameActor actor = (GameActor) actorArray.get(i);
                if (actor.isTouched()) {
                    actor.setTouched(false);
                    handleInput(actor);
                }
            }
        }
    }

    private void handleInput(GameActor actor) {
        if (actor instanceof Clue) {
            Clue clue = (Clue) actor;
            notebook.addClue(clue);
            currentRoom.removeClue(clue);

            gui.displayInfo(actor, clue.getDescription());

            System.out.println("Clue clicked");

        } else if (actor instanceof Item) {
            Item item = (Item) actor;
            notebook.addItem(item);
            currentRoom.removeItem(item);

            gui.displayInfo(item, item.getDescription());

            System.out.println("Item clicked");

        } else if (actor instanceof GameCharacter) {
            gui.showInteractionUI();

            System.out.println("Character clicked");

        } else{
            System.out.println("Background clicked");
        }
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

    public void setCurrentRoom(Room targetRoom){
        currentRoom.setLocked(false);
        currentRoom.setDefault(true);

        currentRoom = targetRoom;
        Gdx.input.setInputProcessor(new InputMultiplexer(gui.getStage(), currentRoom.getCurrentStage()));
    }

    /**
     * Move to the target room
     * @param id room id
     * @return true if successful, false if unsuccessful
     */

    public boolean moveRoom(int id){
        Room targetRoom = roomManager.getRoom(id);
        if (!targetRoom.isLocked()){
            setCurrentRoom(targetRoom);
            return true;
        }
        // if the room is locked and the player had found the key
        else if (targetRoom.isLocked() && notebook.contains(itemManager.getKey())){
            setCurrentRoom(targetRoom);
            return true;
        }
        else{
            return false;
        }
    }

    public void dispose(){
        roomManager.dispose();
    }
}
