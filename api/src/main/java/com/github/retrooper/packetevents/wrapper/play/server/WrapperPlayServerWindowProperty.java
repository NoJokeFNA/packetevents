/*
 * This file is part of packetevents - https://github.com/retrooper/packetevents
 * Copyright (C) 2021-2022 retrooper and contributors
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
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerWindowProperty extends PacketWrapper<WrapperPlayServerWindowProperty> {

    private byte containerId;
    private int id;
    private int value;

    public WrapperPlayServerWindowProperty(PacketSendEvent event) {
        super(event);
    }

    public WrapperPlayServerWindowProperty(byte containerId, int id, int value) {
        super(PacketType.Play.Server.WINDOW_PROPERTY);
        this.containerId = containerId;
        this.id = id;
        this.value = value;
    }

    @Override
    public void read() {
        this.containerId = (byte) readUnsignedByte();
        this.id = readShort();
        this.value = readShort();
    }

    @Override
    public void copy(WrapperPlayServerWindowProperty wrapper) {
        this.containerId = wrapper.containerId;
        this.id = wrapper.id;
        this.value = wrapper.value;
    }

    @Override
    public void write() {
        writeByte(this.containerId);
        writeShort(this.id);
        writeShort(this.value);
    }

    public byte getContainerId() {
        return containerId;
    }

    public void setContainerId(byte containerId) {
        this.containerId = containerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
