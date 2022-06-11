package com.github.retrooper.packetevents.wrapper.play.server;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayServerServerData extends PacketWrapper<WrapperPlayServerServerData> {
    private @Nullable Component motd;
    private @Nullable String icon;
    private boolean previewsChat;

    public WrapperPlayServerServerData(PacketReceiveEvent event) {
        super(event);
    }

    public WrapperPlayServerServerData(@Nullable Component motd, @Nullable String icon, boolean previewsChat) {
        super(PacketType.Play.Server.SERVER_DATA);
        this.motd = motd;
        this.icon = icon;
        this.previewsChat = previewsChat;
    }

    @Override
    public void read() {
        if (readBoolean()) {
            motd = readComponent();
        }
        if (readBoolean()) {
            icon = readString();
        }
        previewsChat = readBoolean();
    }

    @Override
    public void write() {
        if (motd != null) {
            writeComponent(motd);
        }
        if (icon != null) {
            writeString(icon);
        }
        writeBoolean(previewsChat);
    }

    @Override
    public void copy(WrapperPlayServerServerData wrapper) {
        motd = wrapper.motd;
        icon = wrapper.icon;
        previewsChat = wrapper.previewsChat;
    }

    public Optional<Component> getMotd() {
        return Optional.ofNullable(motd);
    }

    public void setMotd(@Nullable Component motd) {
        this.motd = motd;
    }

    public Optional<String> getIcon() {
        return Optional.ofNullable(icon);
    }

    public void setIcon(@Nullable String icon) {
        this.icon = icon;
    }

    public boolean isPreviewsChat() {
        return previewsChat;
    }

    public void setPreviewsChat(boolean previewsChat) {
        this.previewsChat = previewsChat;
    }
}
