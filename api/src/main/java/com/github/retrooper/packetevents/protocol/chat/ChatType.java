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

package com.github.retrooper.packetevents.protocol.chat;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//TODO Convert ChatType into a registry like we did with PotionTypes and other things.
public enum ChatType {
    CHAT(0),

    @Deprecated
    SYSTEM(-1),
    @Deprecated
    GAME_INFO(-1),

    SAY_COMMAND(1),

    @Deprecated
    MSG_COMMAND(-1),

    MSG_COMMAND_INCOMING(2),
    MSG_COMMAND_OUTGOING(3),

    @Deprecated
    TEAM_MSG_COMMAND(-1),

    TEAM_MSG_COMMAND_INCOMING(4),
    TEAM_MSG_COMMAND_OUTGOING(5),
    EMOTE_COMMAND(6),

    @Deprecated
    TELLRAW_COMMAND(-1);

    private final byte modernId;

    ChatType(int modernId) {
        this.modernId = (byte) modernId;
    }

    private static final ChatType[] VALUES;
    private static final Map<Byte, ChatType> MODERN_CHAT_TYPE_MAP;

    static {
        VALUES = values();
        MODERN_CHAT_TYPE_MAP = new HashMap<>();

        Arrays.stream(VALUES)
                .filter(chatType -> chatType.modernId != -1)
                .forEach(chatType -> MODERN_CHAT_TYPE_MAP.put(chatType.modernId, chatType));
    }

    public int getId(ServerVersion version) {
        if (version.isNewerThanOrEquals(ServerVersion.V_1_19_1)) {
            return modernId;
        }
        return ordinal();
    }

    @Nullable
    public static ChatType getById(ServerVersion version, int id) {
        if (version.isNewerThanOrEquals(ServerVersion.V_1_19_1)) {
            return MODERN_CHAT_TYPE_MAP.get((byte) id);
        } else {
            return VALUES[id];
        }
    }
}
