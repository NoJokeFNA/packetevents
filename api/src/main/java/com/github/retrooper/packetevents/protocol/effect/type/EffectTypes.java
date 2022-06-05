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
        final EffectType effectType = new EffectType() {
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

        EFFECT_TYPE_MAPPINGS.put(effectType.getName().getKey(), effectType);
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

    public static final EffectType CLICK2 = define("bow_fire");
    public static final EffectType CLICK1 = define("bow_fire");
    public static final EffectType BOW_FIRE = define("bow_fire");
    public static final EffectType DOOR_TOGGLE = define("door_toggle");
    public static final EffectType EXTINGUISH = define("extinguish");
    public static final EffectType RECORD_PLAY = define("record_play");
    public static final EffectType GHAST_SHRIEK = define("ghast_shriek");
    public static final EffectType GHAST_SHOOT = define("ghast_shoot");
    public static final EffectType BALZE_SHOOT = define("blaze_shoot");
    public static final EffectType ZOMBIE_CHEW_WOODEN_DOOR = define("zombie_chew_wooden_door");
    public static final EffectType ZOMBIE_CHEW_IRON_DOOR = define("zombie_chew_iron_door");
    public static final EffectType ZOMBIE_DESTROY_DOOR = define("zombie_destroy_door");
    public static final EffectType SMOKE = define("smoke");
    public static final EffectType STEP_SOUND = define("step_sound");
    public static final EffectType POTION_BREAK = define("potion_break");
    public static final EffectType ENDER_SIGNAL = define("ender_signal");
    public static final EffectType MOBSPAWNER_FLAMES = define("mobspawner_flames");

    // 1.12
    public static final EffectType IRON_DOOR_TOGGLE = define("iron_door_toggle");
    public static final EffectType TRAPDOOR_TOGGLE = define("trapdoor_toggle");
    public static final EffectType IRON_TRAPDOOR_TOGGLE = define("iron_trapdoor_toggle");
    public static final EffectType FENCE_GATE_TOGGLE = define("fence_gate_toggle");
    public static final EffectType DOOR_CLOSE = define("door_close");
    public static final EffectType IRON_DOOR_CLOSE = define("iron_door_close");
    public static final EffectType TRAPDOOR_CLOSE = define("trapdoor_close");
    public static final EffectType IRON_TRAPDOOR_CLOSE = define("iron_trapdoor_close");
    public static final EffectType FENCE_GATE_CLOSE = define("fence_gate_close");
    public static final EffectType BREWING_STAND_BREW = define("brewing_stand_brew");
    public static final EffectType CHORUS_FLOWER_GROW = define("chorus_flower_grow");
    public static final EffectType CHORUS_FLOWER_DEATH = define("chorus_flower_death");
    public static final EffectType PORTAL_TRAVEL = define("portal_travel");
    public static final EffectType ENDEREYE_LAUNCH = define("endereye_launch");
    public static final EffectType FIREWORK_SHOOT = define("firework_shoot");
    public static final EffectType VILLAGER_PLANT_GROW = define("villager_plant_grow");
    public static final EffectType DRAGON_BREATH = define("dragon_breath");
    public static final EffectType ANVIL_BREAK = define("anvil_break");
    public static final EffectType ANVIL_USE = define("anvil_use");
    public static final EffectType ANVIL_LAND = define("anvil_land");
    public static final EffectType ENDERDRAGON_SHOOT = define("enderdragon_shoot");
    public static final EffectType WITHER_BREAK_BLOCK = define("wither_break_block");
    public static final EffectType WITHER_SHOOT = define("wither_shoot");
    public static final EffectType ZOMBIE_INFECT_ = define("zombie_infect");
    public static final EffectType ZOMBIE_CONVERTED_VILLAGER = define("zombie_converted_villager");
    public static final EffectType BAT_TAKEOFF = define("bat_takeoff");
    public static final EffectType END_GATEWAY_SPAWN = define("end_gateway_spawn");
    public static final EffectType ENDERDRAGON_GROWL = define("enderdragon_growl");

    public static Collection<EffectType> values = EFFECT_TYPE_MAPPINGS.values();
}
