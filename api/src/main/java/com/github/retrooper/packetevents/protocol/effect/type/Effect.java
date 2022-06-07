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

public class Effect {
    private static final Map<String, Effects> EFFECT_TYPE_MAPPINGS;
    private static final Map<Byte, Map<Integer, Effects>> EFFECT_TYPE_ID_MAPPINGS;
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

    public static Effects define(@NotNull final String key) {
        final TypesBuilderData data = TYPES_BUILDER.define(key);
        final Effects effect = new Effects() {
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
                if (obj instanceof Effects) {
                    return getName() == ((Effects) obj).getName();
                }
                return false;
            }
        };

        EFFECT_TYPE_MAPPINGS.put(effect.getName().getKey(), effect);
        for (ClientVersion version : TYPES_BUILDER.getVersions()) {
            final int index = TYPES_BUILDER.getDataIndex(version);
            final Map<Integer, Effects> typeIdMap = EFFECT_TYPE_ID_MAPPINGS.computeIfAbsent((byte) index, k -> new HashMap<>());
            typeIdMap.put(effect.getId(version), effect);
        }
        return effect;
    }

    @Nullable
    public static Effects getByName(@NotNull final String name) {
        return EFFECT_TYPE_MAPPINGS.get(name);
    }

    @Nullable
    public static Effects getById(@NotNull final ClientVersion version, final int id) {
        final int index = TYPES_BUILDER.getDataIndex(version);
        final Map<Integer, Effects> typeIdMap = EFFECT_TYPE_ID_MAPPINGS.get((byte) index);
        return typeIdMap.get(id);
    }

    public static final Effects CLICK2 = define("bow_fire");
    public static final Effects CLICK1 = define("bow_fire");
    public static final Effects BOW_FIRE = define("bow_fire");
    public static final Effects DOOR_TOGGLE = define("door_toggle");
    public static final Effects EXTINGUISH = define("extinguish");
    public static final Effects RECORD_PLAY = define("record_play");
    public static final Effects GHAST_SHRIEK = define("ghast_shriek");
    public static final Effects GHAST_SHOOT = define("ghast_shoot");
    public static final Effects BALZE_SHOOT = define("blaze_shoot");
    public static final Effects ZOMBIE_CHEW_WOODEN_DOOR = define("zombie_chew_wooden_door");
    public static final Effects ZOMBIE_CHEW_IRON_DOOR = define("zombie_chew_iron_door");
    public static final Effects ZOMBIE_DESTROY_DOOR = define("zombie_destroy_door");
    public static final Effects SMOKE = define("smoke");
    public static final Effects STEP_SOUND = define("step_sound");
    public static final Effects POTION_BREAK = define("potion_break");
    public static final Effects ENDER_SIGNAL = define("ender_signal");
    public static final Effects MOBSPAWNER_FLAMES = define("mobspawner_flames");

    // 1.12
    public static final Effects IRON_DOOR_TOGGLE = define("iron_door_toggle");
    public static final Effects TRAPDOOR_TOGGLE = define("trapdoor_toggle");
    public static final Effects IRON_TRAPDOOR_TOGGLE = define("iron_trapdoor_toggle");
    public static final Effects FENCE_GATE_TOGGLE = define("fence_gate_toggle");
    public static final Effects DOOR_CLOSE = define("door_close");
    public static final Effects IRON_DOOR_CLOSE = define("iron_door_close");
    public static final Effects TRAPDOOR_CLOSE = define("trapdoor_close");
    public static final Effects IRON_TRAPDOOR_CLOSE = define("iron_trapdoor_close");
    public static final Effects FENCE_GATE_CLOSE = define("fence_gate_close");
    public static final Effects BREWING_STAND_BREW = define("brewing_stand_brew");
    public static final Effects CHORUS_FLOWER_GROW = define("chorus_flower_grow");
    public static final Effects CHORUS_FLOWER_DEATH = define("chorus_flower_death");
    public static final Effects PORTAL_TRAVEL = define("portal_travel");
    public static final Effects ENDEREYE_LAUNCH = define("endereye_launch");
    public static final Effects FIREWORK_SHOOT = define("firework_shoot");
    public static final Effects VILLAGER_PLANT_GROW = define("villager_plant_grow");
    public static final Effects DRAGON_BREATH = define("dragon_breath");
    public static final Effects ANVIL_BREAK = define("anvil_break");
    public static final Effects ANVIL_USE = define("anvil_use");
    public static final Effects ANVIL_LAND = define("anvil_land");
    public static final Effects ENDERDRAGON_SHOOT = define("enderdragon_shoot");
    public static final Effects WITHER_BREAK_BLOCK = define("wither_break_block");
    public static final Effects WITHER_SHOOT = define("wither_shoot");
    public static final Effects ZOMBIE_INFECT_ = define("zombie_infect");
    public static final Effects ZOMBIE_CONVERTED_VILLAGER = define("zombie_converted_villager");
    public static final Effects BAT_TAKEOFF = define("bat_takeoff");
    public static final Effects END_GATEWAY_SPAWN = define("end_gateway_spawn");
    public static final Effects ENDERDRAGON_GROWL = define("enderdragon_growl");

    public static Collection<Effects> values = EFFECT_TYPE_MAPPINGS.values();
}
