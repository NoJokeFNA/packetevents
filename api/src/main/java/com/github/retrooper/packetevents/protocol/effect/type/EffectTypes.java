package com.github.retrooper.packetevents.protocol.effect.type;

import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.TypesBuilder;
import com.github.retrooper.packetevents.util.TypesBuilderData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EffectTypes {
    private static final Map<String, EffectType> EFFECT_TYPE_MAPPINGS;
    private static final Map<Byte, Map<Integer, EffectType>> EFFECT_TYPE_ID_MAPPINGS;
    private static final TypesBuilder TYPES_BUILDER;

    static {
        EFFECT_TYPE_MAPPINGS = new HashMap<>();
        EFFECT_TYPE_ID_MAPPINGS = new HashMap<>();
        TYPES_BUILDER = new TypesBuilder("effect/effect_type_mappings",
                ClientVersion.V_1_8,
                ClientVersion.V_1_9,
                ClientVersion.V_1_10,
                ClientVersion.V_1_11,
                ClientVersion.V_1_12,
                ClientVersion.V_1_13,
                ClientVersion.V_1_14,
                ClientVersion.V_1_15,
                ClientVersion.V_1_16,
                ClientVersion.V_1_17,
                ClientVersion.V_1_18);
    }

    public static EffectType define(@NotNull final String key) {
        final TypesBuilderData data = TYPES_BUILDER.define(key);
        final ResourceLocation name = data.getName();
        final String[] split = name.toString().split(":");
        final String typeName = split[0];
        final int typeId = Integer.parseInt(split[1]);
        final EffectType effectType = new EffectType() {
            @Override
            public String getType() {
                return typeName;
            }

            @Override
            public int getTypeId() {
                return typeId;
            }

            @Override
            public ResourceLocation getName() {
                return data.getName();
            }

            @Override
            public int getId(ClientVersion version) {
                final int index = TYPES_BUILDER.getDataIndex(version);
                return data.getData()[index];
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof EffectType) {
                    return getName() == ((EffectType) obj).getName();
                }
                return false;
            }
        };

        EFFECT_TYPE_MAPPINGS.put(effectType.getName().toString().split(":")[0], effectType);
        for (ClientVersion version : TYPES_BUILDER.getVersions()) {
            final int index = TYPES_BUILDER.getDataIndex(version);
            final Map<Integer, EffectType> typeIdMap = EFFECT_TYPE_ID_MAPPINGS.computeIfAbsent((byte) index, k -> new HashMap<>());
            typeIdMap.put(effectType.getId(version), effectType);
        }
        return effectType;
    }

    @Nullable
    public static EffectType getByName(@NotNull final String name) {
        return EFFECT_TYPE_MAPPINGS.get(name);
    }

    @Nullable
    public static EffectType getById(@NotNull final ClientVersion version, final int id) {
        final int index = TYPES_BUILDER.getDataIndex(version);
        final Map<Integer, EffectType> typeIdMap = EFFECT_TYPE_ID_MAPPINGS.get((byte) index);
        return typeIdMap.get(id);
    }

    public static final EffectType BOW_FIRE = define("bow_fire");

    public static Collection<EffectType> values = EFFECT_TYPE_MAPPINGS.values();
}
