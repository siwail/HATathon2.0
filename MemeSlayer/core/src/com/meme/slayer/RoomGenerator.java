package com.meme.slayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RoomGenerator {

    public List<Room> generateRooms(int count) {
        List<Room> rooms = new ArrayList<>();
        Room previousRoom = null;

        /*for (int i = 0; i < count; i++) {
            Room currentRoom = new Room(previousRoom != null ? previousRoom.getSides() : null);
            currentRoom.generateExits();
            rooms.add(currentRoom);
            previousRoom = currentRoom;
        }
*/
        return rooms;
    }
}