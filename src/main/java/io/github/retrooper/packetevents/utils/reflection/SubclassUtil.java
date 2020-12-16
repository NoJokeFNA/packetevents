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

package io.github.retrooper.packetevents.utils.reflection;

import java.lang.annotation.Annotation;

public class SubclassUtil {
    public static Class<?> getSubClass(Class<?> cls, String name) {
        for (Class<?> subClass : cls.getDeclaredClasses()) {
            if (ClassUtil.getClassSimpleName(subClass).equals(name)) {
                return subClass;
            }
        }
        return null;
    }

    public static Class<?> getSubClass(Class<?> cls, int index) {
        int currentIndex = 0;
        for (Class<?> subClass : cls.getDeclaredClasses()) {
            if (index == currentIndex++) {
                return subClass;
            }
        }
        return null;
    }

    public static Class<?> getSubClass(Class<?> cls, Annotation annotation, int index) {
        int currentIndex = 0;
        for (Class<?> subClass : cls.getDeclaredClasses()) {
            if (subClass.isAnnotationPresent(annotation.getClass())) {
                if (index == currentIndex++) {
                    return subClass;
                }
            }
        }
        return null;
    }
}
