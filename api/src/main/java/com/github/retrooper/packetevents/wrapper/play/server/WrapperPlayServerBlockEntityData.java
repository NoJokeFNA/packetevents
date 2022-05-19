/*
 * This file is part of packetevents - https://github.com/retrooper/packetevents
 * Copyright (C) 2021 retrooper and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.retrooper.packetevents.wrapper.play.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Optional;
import java.util.OptionalInt;

public class WrapperPlayServerBlockEntityData extends PacketWrapper<WrapperPlayServerBlockEntityData> {
    private Vector3i position;
    private Optional<Action> action;
    private OptionalInt type;
    private Optional<byte[]> nbtData;
    private Optional<NBTCompound> nbtCompound;

    public WrapperPlayServerBlockEntityData(PacketSendEvent event) {
        super(event);
    }

    public WrapperPlayServerBlockEntityData(Vector3i position, OptionalInt type, Optional<NBTCompound> nbtCompound) {
        super(PacketType.Play.Server.BLOCK_ENTITY_DATA);
        this.position = position;
        this.type = type;
        this.nbtCompound = nbtCompound;
    }

    @Override
    public void read() {
        this.action = Optional.empty();
        this.type = OptionalInt.empty();
        this.nbtData = Optional.empty();
        this.nbtCompound = Optional.empty();
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.position = new Vector3i(readLong());
        } else {
            int x = readInt();
            int y = readShort();
            int z = readInt();
            this.position = new Vector3i(x, y, z);
        }
        if (serverVersion.isOlderThanOrEquals(ServerVersion.V_1_8_8)) {
            this.action = Optional.of(Action.VALUES[readUnsignedByte()]);
            this.nbtData = Optional.of(readByteArray());
        } else {
            this.type = OptionalInt.of(readVarInt());
            this.nbtCompound = Optional.of(readNBT());
        }
    }

    @Override
    public void write() {
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            long positionVector = this.position.getSerializedPosition();
            writeLong(positionVector);
        } else {
            writeInt(this.position.x);
            writeShort(this.position.y);
            writeInt(this.position.z);
        }
        if (serverVersion.isOlderThanOrEquals(ServerVersion.V_1_8_8)) {
            writeByte(this.action.get().ordinal());
            writeByteArray(this.nbtData.get());
        } else {
            writeVarInt(this.type.getAsInt());
            writeNBT(this.nbtCompound.get());
        }
    }

    @Override
    public void copy(WrapperPlayServerBlockEntityData wrapper) {
        this.position = wrapper.position;
        this.type = wrapper.type;
        this.nbtCompound = wrapper.nbtCompound;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerBlockEntityData{" +
                "position=" + position +
                ", action=" + action +
                ", type=" + type +
                ", nbtData=" + nbtData +
                ", nbtCompound=" + nbtCompound +
                '}';
    }

    public enum Action {
        MOB_DISPLAYED,
        COMMAND_BLOCK_TEXT,
        ROTATION_AND_SKIN_OF_MOB_HEAD,
        TYPE_OF_FLOWER;

        public static final Action VALUES[] = values();
    }
}
