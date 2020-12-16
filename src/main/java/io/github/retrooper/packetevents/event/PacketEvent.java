/*
 * MIT License
 *
 * Copyright (c) 2020 retrooper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.retrooper.packetevents.event;


import io.github.retrooper.packetevents.event.eventtypes.CallableEvent;

/**
 * An event in both of PacketEvents' event systems.
 * @author retrooper
 * @since 1.2.6
 */
public abstract class PacketEvent implements CallableEvent {
    private long timestamp = System.currentTimeMillis();

    /**
     * Timestamp of when the PacketEvent was created.
     * Basically timestamp of the packet.
     * @return Packet timestamp in milliseconds.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Setter for the timestamp.
     * @param timestamp Packet timestamp in milliseconds.
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void callPacketEvent(PacketListenerDynamic listener) {
        listener.onPacketEvent(this);
    }
}