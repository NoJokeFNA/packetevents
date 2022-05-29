package com.github.retrooper.packetevents.protocol.item.book;

public enum BookType {
    CRAFTING,
    FURNACE,
    BLAST_FURNACE,
    SMOKER;

    public static final BookType[] VALUES = values();

    public int getId() {
        return ordinal();
    }

    public static BookType getById(int id) {
        return VALUES[id];
    }
}
