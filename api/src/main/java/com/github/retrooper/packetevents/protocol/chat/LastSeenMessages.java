package com.github.retrooper.packetevents.protocol.chat;

import org.jetbrains.annotations.Nullable;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LastSeenMessages {
    public static final LastSeenMessages EMPTY = new LastSeenMessages(new ArrayList<>());

    private final List<Entry> entries;

    public LastSeenMessages(List<Entry> entries) {
        this.entries = entries;
    }

    public void updateHash(DataOutput output) throws IOException {
        for (Entry entry : this.entries) {
            UUID uuid = entry.getUUID();
            byte[] lastVerifier = entry.getLastVerifier();
            output.writeByte(70);
            output.writeLong(uuid.getMostSignificantBits());
            output.writeLong(uuid.getLeastSignificantBits());
             output.write(lastVerifier);
        }
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public static class Entry {
        private final UUID uuid;
        private final byte[] signature;

        public Entry(UUID uuid, byte[] lastVerifier) {
            this.uuid = uuid;
            this.signature = lastVerifier;
        }

        public UUID getUUID() {
            return uuid;
        }

        public byte[] getLastVerifier() {
            return signature;
        }
    }

    public static class Update {
        private final LastSeenMessages lastSeenMessages;
        private final @Nullable Entry lastReceived;

        public Update(LastSeenMessages lastSeenMessages, @Nullable Entry lastReceived) {
            this.lastSeenMessages = lastSeenMessages;
            this.lastReceived = lastReceived;
        }

        public LastSeenMessages getLastSeenMessages() {
            return lastSeenMessages;
        }

        public @Nullable Entry getLastReceived() {
            return lastReceived;
        }
    }
}
