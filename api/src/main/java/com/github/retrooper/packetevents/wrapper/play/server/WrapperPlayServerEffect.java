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
import com.github.retrooper.packetevents.protocol.effect.type.Effect;
import com.github.retrooper.packetevents.protocol.effect.type.Effects;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.Direction;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.OptionalInt;

public class WrapperPlayServerEffect extends PacketWrapper<WrapperPlayServerEffect> {
    private int effectId;
    private @Nullable Effects effects;
    private Vector3i position;
    private int data;
    private boolean disableRelativeVolume;
    private @Nullable Direction direction;
    private int rgbColor;
    private int particlesToSpawn;
    private int blockTypeID;

    public WrapperPlayServerEffect(PacketSendEvent event) {
        super(event);
    }

    public WrapperPlayServerEffect(int effectId, @Nullable Effects effects, @Nullable Direction direction, Vector3i position, int data, boolean disableRelativeVolume) {
        super(PacketType.Play.Server.EFFECT);
        this.effectId = effectId;
        this.direction = direction;
        this.effects = effects;
        this.position = position;
        this.data = data;
        this.disableRelativeVolume = disableRelativeVolume;
    }

    @Override
    public void read() {
        this.effectId = readInt();
        this.effects = Effect.getById(serverVersion.toClientVersion(), this.effectId);
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
        extraData(true);
    }

    @Override
    public void write() {
        writeInt(this.effectId);
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
        extraData(false);
    }

    @Override
    public void copy(WrapperPlayServerEffect wrapper) {
        this.effects = wrapper.effects;
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

    public Optional<Effects> getEffects() {
        return Optional.ofNullable(effects);
    }

    public void setEffects(@Nullable Effects effects) {
        this.effects = effects;
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

    public Optional<Direction> getDirection() {
        return Optional.ofNullable(direction);
    }

    public void setDirection(@Nullable Direction direction) {
        this.direction = direction;
    }

    public OptionalInt getRgbColor() {
        return OptionalInt.of(rgbColor);
    }

    public void setRgbColor(int rgbColor) {
        this.rgbColor = rgbColor;
    }

    public OptionalInt getParticlesToSpawn() {
        return OptionalInt.of(particlesToSpawn);
    }

    public void setParticlesToSpawn(int particlesToSpawn) {
        this.particlesToSpawn = particlesToSpawn;
    }

    public OptionalInt getBlockTypeID() {
        return OptionalInt.of(blockTypeID);
    }

    public void setBlockTypeID(int blockTypeID) {
        this.blockTypeID = blockTypeID;
    }

    public Optional<WrappedBlockState> getBlockType() {
        return Optional.of(WrappedBlockState.getByGlobalId(serverVersion.toClientVersion(), blockTypeID));
    }

    protected void extraData(boolean read) {
        if (this.effects == Effect.SMOKE) {
            if (read) {
                this.direction = Direction.getByHorizontalIndex(readByte());
            } else {
                writeByte(this.direction.getHorizontalIndex());
            }
        }

        if (this.effects == Effect.INSTANT_POTION_BREAK || this.effects == Effect.POTION_BREAK) {
            if (read) {
                this.rgbColor = readInt();
            } else {
                writeInt(this.rgbColor);
            }
        }

        if (this.effects == Effect.BONE_MEAL_USE) {
            if (read) {
                this.particlesToSpawn = readInt();
            } else {
                writeInt(this.particlesToSpawn);
            }
        }

        if (this.effects == Effect.ANVIL_BREAK || this.effects == Effect.WITHER_BREAK_BLOCK) {
            if (read) {
                this.blockTypeID = readVarInt();
            } else {
                writeVarInt(this.blockTypeID);
            }
        }
    }
}
