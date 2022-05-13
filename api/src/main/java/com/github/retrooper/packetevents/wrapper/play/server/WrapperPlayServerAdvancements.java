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
import com.github.retrooper.packetevents.protocol.advancement.Advancement;
import com.github.retrooper.packetevents.protocol.advancement.Advancement.DisplayData;
import com.github.retrooper.packetevents.protocol.advancement.Advancement.DisplayData.FrameType;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Mostly from MCProtocolLib
public class WrapperPlayServerAdvancements extends PacketWrapper<WrapperPlayServerAdvancements> {
  private static final int FLAG_HAS_BACKGROUND_TEXTURE = 0x01;
  private static final int FLAG_SHOW_TOAST = 0x02;
  private static final int FLAG_HIDDEN = 0x04;

  private boolean reset;
  private @NotNull Advancement[] advancements;
  private @NotNull String[] removedAdvancements;
  private @NotNull Map<String, Map<String, Long>> progress;

  public WrapperPlayServerAdvancements(PacketSendEvent event) {
    super(event);
  }

  public WrapperPlayServerAdvancements(boolean reset, @NotNull Advancement[] advancements, @NotNull String[] removedAdvancements, @NotNull Map<String, Map<String, Long>> progress) {
    super(PacketType.Play.Server.ADVANCEMENTS);
    this.reset = reset;
    this.advancements = advancements;
    this.removedAdvancements = removedAdvancements;
    this.progress = progress;
  }

  @Override
  public void read() {
    this.reset = this.readBoolean();

    this.advancements = new Advancement[readVarInt()];
    for (int i = 0; i < this.advancements.length; i++) {
      final String id = readString();
      final String parentId = readBoolean() ? readString() : null;
      DisplayData displayData = null;
      if (readBoolean()) {
        final Component title = readComponent();
        final Component description = readComponent();
        final ItemStack icon = readItemStack();
        final FrameType frameType = FrameType.VALUES[readVarInt()];

        final int flags = readInt();
        final boolean hasBackgroundTexture = (flags & FLAG_HAS_BACKGROUND_TEXTURE) != 0;
        final boolean showToast = (flags & FLAG_SHOW_TOAST) != 0;
        final boolean hidden = (flags & FLAG_HIDDEN) != 0;

        final String backgroundTexture = hasBackgroundTexture ? readString() : null;
        final float posX = readFloat();
        final float posY = readFloat();

        displayData = new DisplayData(title, description, icon, frameType, showToast, hidden, posX, posY, backgroundTexture);
      }

      final List<String> criteria = new ArrayList<>();
      final int criteriaCount = readVarInt();
      for (int j = 0; j < criteriaCount; j++) {
        criteria.add(readString());
      }
      
      final List<List<String>> requirements = new ArrayList<>();
      final int requirementCount = readVarInt();
      for (int j = 0; j < requirementCount; j++) {
        final List<String> requirement = new ArrayList<>();
        final int componentCount = readVarInt();
        for (int k = 0; k < componentCount; k++) {
          requirement.add(readString());
        }
        requirements.add(requirement);
      }

      this.advancements[i] = new Advancement(id, criteria, requirements, parentId, displayData);
    }

    this.removedAdvancements = new String[readVarInt()];
    for (int i = 0; i < this.removedAdvancements.length; i++) {
      this.removedAdvancements[i] = readString();
    }

    this.progress = new HashMap<>();
    final int progressCount = readVarInt();
    for (int i = 0; i < progressCount; i++) {
      final String advancementId = readString();
      final Map<String, Long> advancementProgress = new HashMap<>();
      final int criteriaCount = readVarInt();
      for (int j = 0; j < criteriaCount; j++) {
        final String criteriaId = readString();
        final long achievedData = readBoolean() ? readLong() : -1;
        advancementProgress.put(criteriaId, achievedData);
      }
      this.progress.put(advancementId, advancementProgress);
    }
  }

  @Override
  public void write() {
    writeBoolean(this.reset);

    writeVarInt(this.advancements.length);
    for (final Advancement advancement : this.advancements) {
      writeString(advancement.getId());
      if (advancement.getParentId() != null) {
        writeBoolean(true);
        writeString(advancement.getParentId());
      } else {
        writeBoolean(false);
      }

      final DisplayData displayData = advancement.getDisplayData();
      if (displayData != null) {
        writeBoolean(true);
        writeComponent(displayData.getTitle());
        writeComponent(displayData.getDescription());
        writeItemStack(displayData.getIcon());
        writeVarInt(displayData.getFrameType().ordinal());
        final String backgroundTexture = displayData.getBackgroundTexture();

        int flags = 0;
        if (backgroundTexture != null) {
          flags |= FLAG_HAS_BACKGROUND_TEXTURE;
        }
        if (displayData.isShowToast()) {
          flags |= FLAG_SHOW_TOAST;
        }
        if (displayData.isHidden()) {
          flags |= FLAG_HIDDEN;
        }
        writeVarInt(flags);

        if (backgroundTexture != null) {
          writeString(backgroundTexture);
        }

        writeFloat(displayData.getPosX());
        writeFloat(displayData.getPosY());
      } else {
        writeBoolean(false);
      }

      writeVarInt(advancement.getCriteria().size());
      for (final String criteria : advancement.getCriteria()) {
        writeString(criteria);
      }

      writeVarInt(advancement.getRequirements().size());
      for (final List<String> requirement : advancement.getRequirements()) {
        writeVarInt(requirement.size());
        for (final String criterion : requirement) {
          writeString(criterion);
        }
      }
    }

    writeVarInt(this.removedAdvancements.length);
    for (final String removedAdvancement : this.removedAdvancements) {
      writeString(removedAdvancement);
    }

    writeVarInt(this.progress.size());
    for (final Map.Entry<String, Map<String, Long>> advancement : this.progress.entrySet()) {
      writeString(advancement.getKey());
      final Map<String, Long> advancementProgress = advancement.getValue();
      writeVarInt(advancementProgress.size());
      for (final Map.Entry<String, Long> criterion : advancementProgress.entrySet()) {
        writeString(criterion.getKey());
        if (criterion.getValue() != -1) {
          writeBoolean(true);
          writeLong(criterion.getValue());
        } else {
          writeBoolean(false);
        }
      }
    }
  }

  @Override
  public void copy(WrapperPlayServerAdvancements wrapper) {
    this.reset = wrapper.reset;
    this.advancements = wrapper.advancements;
    this.removedAdvancements = wrapper.removedAdvancements;
    this.progress = wrapper.progress;
  }

  @Override
  public String toString() {
    return "WrapperPlayServerAdvancements{" +
            "reset=" + reset +
            ", advancements=" + Arrays.toString(advancements) +
            ", removedAdvancements=" + Arrays.toString(removedAdvancements) +
            ", progress=" + progress +
            '}';
  }

  public Map<String, Long> getProgress(@NotNull final String advancementId) {
    return this.progress.get(advancementId);
  }

  public long getAchievedDate(@NotNull final String advancementId, @NotNull final String criterionId) {
    final Map<String, Long> progress = this.getProgress(advancementId);
    if (progress == null || !progress.containsKey(criterionId)) {
      return -1;
    }
    return progress.get(criterionId);
  }

  public boolean isReset() {
    return reset;
  }

  public void setReset(boolean reset) {
    this.reset = reset;
  }

  public Advancement[] getAdvancements() {
    return advancements;
  }

  public void setAdvancements(Advancement[] advancements) {
    this.advancements = advancements;
  }

  public String[] getRemovedAdvancements() {
    return removedAdvancements;
  }

  public void setRemovedAdvancements(String[] removedAdvancements) {
    this.removedAdvancements = removedAdvancements;
  }

  public @NotNull Map<String, Map<String, Long>> getProgress() {
    return progress;
  }

  public void setProgress(@NotNull Map<String, Map<String, Long>> progress) {
    this.progress = progress;
  }
}
