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

package com.github.retrooper.packetevents.wrapper.login.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.util.crypto.SignatureData;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperLoginClientLoginStart extends PacketWrapper<WrapperLoginClientLoginStart> {
    private String username;
    private Optional<SignatureData> signatureData = Optional.empty();
    public WrapperLoginClientLoginStart(PacketReceiveEvent event) {
        super(event);
    }

    public WrapperLoginClientLoginStart(ClientVersion clientVersion, String username) {
        super(PacketType.Login.Client.LOGIN_START.getId(), clientVersion);
        this.username = username;
    }

    public WrapperLoginClientLoginStart(ClientVersion clientVersion, String username, SignatureData signatureData) {
        super(PacketType.Login.Client.LOGIN_START.getId(), clientVersion);
        this.username = username;
        this.signatureData = Optional.of(signatureData);
    }

    @Override
    public void read() {
        this.username = readString(16);
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_19) && readBoolean()) {
            SignatureData data = new SignatureData(readLong(), readPublicKey(), readBytes(4096));
            this.signatureData = Optional.of(data);
        }
    }

    @Override
    public void copy(WrapperLoginClientLoginStart wrapper) {
        this.username = wrapper.username;
        this.signatureData = wrapper.signatureData;
    }

    @Override
    public void write() {
        writeString(username, 16);
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_19) && signatureData.isPresent()) {
            writeBoolean(true);
            SignatureData data = signatureData.get();
            writeLong(data.getTimestamp());
            writePublicKey(data.getPublicKey());
            writeBytes(data.getSignature());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Optional<SignatureData> getSignatureData() {
        return signatureData;
    }

    public void setSignatureData(@Nullable SignatureData signatureData) {
        this.signatureData = Optional.ofNullable(signatureData);
    }
}
