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
    private static final Map<String, IEffect> EFFECT_TYPE_MAPPINGS;
    private static final Map<Byte, Map<Integer, IEffect>> EFFECT_TYPE_ID_MAPPINGS;
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

    public static IEffect define(@NotNull final String key) {
        final TypesBuilderData data = TYPES_BUILDER.define(key);
        final IEffect IEffect = new IEffect() {
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
                if (obj instanceof IEffect) {
                    return getName() == ((IEffect) obj).getName();
                }
                return false;
            }
        };

        EFFECT_TYPE_MAPPINGS.put(IEffect.getName().getKey(), IEffect);
        for (ClientVersion version : TYPES_BUILDER.getVersions()) {
            final int index = TYPES_BUILDER.getDataIndex(version);
            final Map<Integer, IEffect> typeIdMap = EFFECT_TYPE_ID_MAPPINGS.computeIfAbsent((byte) index, k -> new HashMap<>());
            typeIdMap.put(IEffect.getId(version), IEffect);
        }
        return IEffect;
    }

    @Nullable
    public static IEffect getByName(@NotNull final String name) {
        return EFFECT_TYPE_MAPPINGS.get(name);
    }

    @Nullable
    public static IEffect getById(@NotNull final ClientVersion version, final int id) {
        final int index = TYPES_BUILDER.getDataIndex(version);
        final Map<Integer, IEffect> typeIdMap = EFFECT_TYPE_ID_MAPPINGS.get((byte) index);
        return typeIdMap.get(id);
    }

    public static final IEffect CLICK2 = define("bow_fire");
    public static final IEffect CLICK1 = define("bow_fire");
    public static final IEffect BOW_FIRE = define("bow_fire");
    public static final IEffect DOOR_TOGGLE = define("door_toggle");
    public static final IEffect EXTINGUISH = define("extinguish");
    public static final IEffect RECORD_PLAY = define("record_play");
    public static final IEffect GHAST_SHRIEK = define("ghast_shriek");
    public static final IEffect GHAST_SHOOT = define("ghast_shoot");
    public static final IEffect BALZE_SHOOT = define("blaze_shoot");
    public static final IEffect ZOMBIE_CHEW_WOODEN_DOOR = define("zombie_chew_wooden_door");
    public static final IEffect ZOMBIE_CHEW_IRON_DOOR = define("zombie_chew_iron_door");
    public static final IEffect ZOMBIE_DESTROY_DOOR = define("zombie_destroy_door");
    public static final IEffect SMOKE = define("smoke");
    public static final IEffect STEP_SOUND = define("step_sound");
    public static final IEffect POTION_BREAK = define("potion_break");
    public static final IEffect ENDER_SIGNAL = define("ender_signal");
    public static final IEffect MOBSPAWNER_FLAMES = define("mobspawner_flames");

    // 1.12
    public static final IEffect IRON_DOOR_TOGGLE = define("iron_door_toggle");
    public static final IEffect TRAPDOOR_TOGGLE = define("trapdoor_toggle");
    public static final IEffect IRON_TRAPDOOR_TOGGLE = define("iron_trapdoor_toggle");
    public static final IEffect FENCE_GATE_TOGGLE = define("fence_gate_toggle");
    public static final IEffect DOOR_CLOSE = define("door_close");
    public static final IEffect IRON_DOOR_CLOSE = define("iron_door_close");
    public static final IEffect TRAPDOOR_CLOSE = define("trapdoor_close");
    public static final IEffect IRON_TRAPDOOR_CLOSE = define("iron_trapdoor_close");
    public static final IEffect FENCE_GATE_CLOSE = define("fence_gate_close");
    public static final IEffect BREWING_STAND_BREW = define("brewing_stand_brew");
    public static final IEffect CHORUS_FLOWER_GROW = define("chorus_flower_grow");
    public static final IEffect CHORUS_FLOWER_DEATH = define("chorus_flower_death");
    public static final IEffect PORTAL_TRAVEL = define("portal_travel");
    public static final IEffect ENDEREYE_LAUNCH = define("endereye_launch");
    public static final IEffect FIREWORK_SHOOT = define("firework_shoot");
    public static final IEffect VILLAGER_PLANT_GROW = define("villager_plant_grow");
    public static final IEffect DRAGON_BREATH = define("dragon_breath");
    public static final IEffect ANVIL_BREAK = define("anvil_break");
    public static final IEffect ANVIL_USE = define("anvil_use");
    public static final IEffect ANVIL_LAND = define("anvil_land");
    public static final IEffect ENDERDRAGON_SHOOT = define("enderdragon_shoot");
    public static final IEffect WITHER_BREAK_BLOCK = define("wither_break_block");
    public static final IEffect WITHER_SHOOT = define("wither_shoot");
    public static final IEffect ZOMBIE_INFECT_ = define("zombie_infect");
    public static final IEffect ZOMBIE_CONVERTED_VILLAGER = define("zombie_converted_villager");
    public static final IEffect BAT_TAKEOFF = define("bat_takeoff");
    public static final IEffect END_GATEWAY_SPAWN = define("end_gateway_spawn");
    public static final IEffect ENDERDRAGON_GROWL = define("enderdragon_growl");

    public static Collection<IEffect> values = EFFECT_TYPE_MAPPINGS.values();
}
