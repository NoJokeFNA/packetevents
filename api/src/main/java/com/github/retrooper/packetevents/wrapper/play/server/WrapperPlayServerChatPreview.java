package com.github.retrooper.packetevents.wrapper.play.server;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import net.kyori.adventure.text.Component;

public class WrapperPlayServerChatPreview extends PacketWrapper<WrapperPlayServerChatPreview> {
    private int queryID;
    private Component message;

    public WrapperPlayServerChatPreview(PacketReceiveEvent event) {
        super(event);
    }

    public WrapperPlayServerChatPreview(int queryID, Component message) {
        super(PacketType.Play.Server.CHAT_PREVIEW_PACKET);
        this.queryID = queryID;
        this.message = message;
    }

    @Override
    public void read() {
        queryID = readInt();
        if (readBoolean()) {
            message = readComponent();
        }
    }

    @Override
    public void write() {
        writeInt(queryID);
        if (message != null) {
            writeComponent(message);
        }
    }

    @Override
    public void copy(WrapperPlayServerChatPreview wrapper) {
        queryID = wrapper.queryID;
        message = wrapper.message;
    }

    public int getQueryID() {
        return queryID;
    }

    public void setQueryID(int queryID) {
        this.queryID = queryID;
    }

    public Component getMessage() {
        return message;
    }

    public void setMessage(Component message) {
        this.message = message;
    }
}
