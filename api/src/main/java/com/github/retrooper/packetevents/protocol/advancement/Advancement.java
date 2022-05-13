package com.github.retrooper.packetevents.protocol.advancement;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// Mostly from MCProtocolLib
public class Advancement {
  private final @NotNull String id;
  private final @NotNull List<String> criteria;
  private final @NotNull List<List<String>> requirements;
  private final @Nullable String parentId;
  private final @Nullable DisplayData displayData;

  public Advancement(@NotNull String id, @NotNull List<String> criteria, @NotNull List<List<String>> requirements) {
    this(id, criteria, requirements, null, null);
  }

  public Advancement(@NotNull String id, @NotNull List<String> criteria, @NotNull List<List<String>> requirements, @Nullable String parentId) {
    this(id, criteria, requirements, parentId, null);
  }

  public Advancement(@NotNull String id, @NotNull List<String> criteria, @NotNull List<List<String>> requirements, @Nullable DisplayData displayData) {
    this(id, criteria, requirements, null, displayData);
  }

  public Advancement(@NotNull String id, @NotNull List<String> criteria, @NotNull List<List<String>> requirements, @Nullable String parentId, @Nullable DisplayData displayData) {
    this.id = id;
    this.criteria = criteria;
    this.requirements = requirements;
    this.parentId = parentId;
    this.displayData = displayData;
  }

  public @NotNull String getId() {
    return id;
  }

  public @NotNull List<String> getCriteria() {
    return criteria;
  }

  public @NotNull List<List<String>> getRequirements() {
    return requirements;
  }

  public @Nullable String getParentId() {
    return parentId;
  }

  public @Nullable DisplayData getDisplayData() {
    return displayData;
  }

  public static class DisplayData {
    private final @NotNull Component title;
    private final @NotNull Component description;
    private final ItemStack icon;
    private final @NotNull FrameType frameType;
    private final boolean showToast;
    private final boolean hidden;
    private final float posX;
    private final float posY;
    private final @Nullable String backgroundTexture;

    public DisplayData(@NotNull Component title, @NotNull Component description, ItemStack icon, @NotNull FrameType frameType,
                       boolean showToast, boolean hidden, float posX, float posY) {
      this(title, description, icon, frameType, showToast, hidden, posX, posY, null);
    }

    public DisplayData(@NotNull Component title, @NotNull Component description, ItemStack icon, @NotNull FrameType frameType,
                       boolean showToast, boolean hidden, float posX, float posY, @Nullable String backgroundTexture) {
      this.title = title;
      this.description = description;
      this.icon = icon;
      this.frameType = frameType;
      this.showToast = showToast;
      this.hidden = hidden;
      this.posX = posX;
      this.posY = posY;
      this.backgroundTexture = backgroundTexture;
    }

    public @NotNull Component getTitle() {
      return title;
    }

    public @NotNull Component getDescription() {
      return description;
    }

    public ItemStack getIcon() {
      return icon;
    }

    public @NotNull FrameType getFrameType() {
      return frameType;
    }

    public boolean isShowToast() {
      return showToast;
    }

    public boolean isHidden() {
      return hidden;
    }

    public float getPosX() {
      return posX;
    }

    public float getPosY() {
      return posY;
    }

    public @Nullable String getBackgroundTexture() {
      return backgroundTexture;
    }

    public enum FrameType {
      TASK,
      CHALLENGE,
      GOAL;

      public static final FrameType[] VALUES = values();
    }
  }
}