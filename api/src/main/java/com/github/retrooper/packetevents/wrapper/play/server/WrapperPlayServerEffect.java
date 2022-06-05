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
import com.github.retrooper.packetevents.protocol.effect.type.EffectType;
import com.github.retrooper.packetevents.protocol.effect.type.EffectTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.Direction;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayServerEffect extends PacketWrapper<WrapperPlayServerEffect> {
    private int effectId;
    private @Nullable Direction direction;
    private @Nullable EffectType effectType;
    private Vector3i position;
    private int data;
    private boolean disableRelativeVolume;

    public WrapperPlayServerEffect(PacketSendEvent event) {
        super(event);
    }

    public WrapperPlayServerEffect(int effectId, @Nullable EffectType effectType, @Nullable Direction direction, Vector3i position, int data, boolean disableRelativeVolume) {
        super(PacketType.Play.Server.EFFECT);
        this.effectId = effectId;
        this.direction = direction;
        this.effectType = effectType;
        this.position = position;
        this.data = data;
        this.disableRelativeVolume = disableRelativeVolume;
    }

    @Override
    public void read() {
        this.effectId = readInt();
        if (this.effectId == 2000) {
            System.out.println("HI!");
            this.direction = Direction.getByHorizontalIndex(readUnsignedByte());
            System.out.println(" -> " + direction.name() + " -> " + direction.getHorizontalIndex());
        }

        System.out.println(this.effectId);
        this.effectType = EffectTypes.getById(serverVersion.toClientVersion(), this.effectId);
        if (this.getEffectType().isPresent()) {
            System.out.println(this.effectType.getName() + " -> " + this.effectType.getId(serverVersion.toClientVersion()));
        }
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.position = new Vector3i(readLong());
        } else {
            int x = readInt();
            int y = readShort();
            int z = readInt();
            this.position = new Vector3i(x, y, z);
        }
        this.data = readInt();
        this.disableRelativeVolume = readBoolean();
    }

    @Override
    public void write() {
        writeInt(this.effectId);
        if (this.effectId == 2001) {
            writeByte(this.direction.getHorizontalIndex());
        }
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            long positionVector = this.position.getSerializedPosition();
            writeLong(positionVector);
        } else {
            writeInt(this.position.x);
            writeShort(this.position.y);
            writeInt(this.position.z);
        }
        writeInt(this.data);
        writeBoolean(this.disableRelativeVolume);
    }

    @Override
    public void copy(WrapperPlayServerEffect wrapper) {
        this.effectType = wrapper.effectType;
        this.position = wrapper.position;
        this.data = wrapper.data;
        this.disableRelativeVolume = wrapper.disableRelativeVolume;
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public Optional<Direction> getDirection() {
        return Optional.ofNullable(direction);
    }

    public void setDirection(@Nullable Direction direction) {
        this.direction = direction;
    }

    public Optional<EffectType> getEffectType() {
        return Optional.ofNullable(effectType);
    }

    public void setEffectType(@Nullable EffectType effectType) {
        this.effectType = effectType;
    }

    public Vector3i getPosition() {
        return position;
    }

    public void setPosition(Vector3i position) {
        this.position = position;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public boolean isDisableRelativeVolume() {
        return disableRelativeVolume;
    }

    public void setDisableRelativeVolume(boolean disableRelativeVolume) {
        this.disableRelativeVolume = disableRelativeVolume;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerEffect{" +
                "effectId=" + effectId +
                ", position=" + position.getSerializedPosition() +
                ", data=" + data +
                ", disableRelativeVolume=" + disableRelativeVolume +
                '}';
    }
}
