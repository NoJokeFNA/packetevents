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

package io.github.retrooper.packetevents.event.manager;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.PacketListener;
import io.github.retrooper.packetevents.event.annotation.PacketHandler;
import io.github.retrooper.packetevents.event.eventtypes.CancellableEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
class EventManagerLegacy {
    /**
     * Map storing all all legacy packet event listeners.
     * The key is an individual listener, the values are the key's event methods.
     */
    private final Map<PacketListener, List<Method>> staticRegisteredMethods = new HashMap<>();

    /**
     * Call a PacketEvent with the legacy event manager.
     * This method processes the event on all legacy packet event listeners.
     * This method is actually called by {@link EventManagerDynamic#callEvent(PacketEvent)} as the
     * if you mix the two event systems, the dynamic listeners are always processed first and pass
     * their highest reached event priority that this legacy one has to beat/reach to be able to cancel
     * the event.
     * This downside of this system is the event listeners aren't executed in any particular order, just
     * in the order they were registered.
     * This system is also slower than the other event system.
     * @param event {@link PacketEvent}
     * @param eventPriority Priority the legacy listeners should beat to decide cancellation of the event.
     */
    @Deprecated
    public void callEvent(final PacketEvent event, byte eventPriority) {
        boolean isCancelled = false;
        if (event instanceof CancellableEvent) {
            isCancelled = ((CancellableEvent) event).isCancelled();
        }
        //STATIC LISTENERS
        for (final PacketListener listener : staticRegisteredMethods.keySet()) {
            List<Method> methods = staticRegisteredMethods.get(listener);

            for (Method method : methods) {
                Class<?> parameterType = method.getParameterTypes()[0];
                if (parameterType.equals(PacketEvent.class)
                        || parameterType.isInstance(event)) {

                    PacketHandler annotation = method.getAnnotation(PacketHandler.class);
                    try {
                        method.invoke(listener, event);
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                    if (event instanceof CancellableEvent) {
                        CancellableEvent ce = (CancellableEvent) event;
                        if (annotation.priority() >= eventPriority) {
                            eventPriority = annotation.priority();
                            isCancelled = ce.isCancelled();
                        }
                    }
                }
            }
        }
        if (event instanceof CancellableEvent) {
            CancellableEvent ce = (CancellableEvent) event;
            ce.setCancelled(isCancelled);
        }
    }
    /**
     * Register a legacy packet event listener.
     * Not recommended to use the deprecated event listener.
     * @param listener {@link PacketListener}
     */
    @Deprecated
    public void registerListener(final PacketListener listener) {
        final List<Method> methods = new ArrayList<>();
        for (final Method m : listener.getClass().getDeclaredMethods()) {
            if (!m.isAccessible()) {
                m.setAccessible(true);
            }
            if (m.isAnnotationPresent(PacketHandler.class)
                    && m.getParameterTypes().length == 1) {
                methods.add(m);
            }
        }

        if (!methods.isEmpty()) {
            staticRegisteredMethods.put(listener, methods);
        }
    }

    /**
     * Register multiple legacy packet event listeners with one method.
     * Not recommended to use the deprecated event listener.
     * @param listeners {@link PacketListener}
     */
    @Deprecated
    public void registerListeners(final PacketListener... listeners) {
        for (final PacketListener listener : listeners) {
            registerListener(listener);
        }
    }

    /**
     * Unregister a legacy packet event listener.
     * Not recommended to use the deprecated event listener.
     * @param listener {@link PacketListener}
     */
    @Deprecated
    public void unregisterListener(final PacketListener listener) {
        staticRegisteredMethods.remove(listener);
    }

    /**
     * Unregister multiple legacy packet event listeners with one method.
     * Not recommended to use the deprecated event listener.
     * @param listeners {@link PacketListener}
     */
    @Deprecated
    public void unregisterListeners(final PacketListener... listeners) {
        for (final PacketListener listener : listeners) {
            unregisterListener(listener);
        }
    }

    /**
     * Unregister all legacy packet event listeners.
     */
    @Deprecated
    public void unregisterAllListeners() {
        staticRegisteredMethods.clear();
    }
}
