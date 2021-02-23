package com.duzzi.sdk.utils;

public final class ArraysUtil {

    public static <T> int length(final T[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(final float[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(final int[] array) {
        return array == null ? 0 : array.length;
    }

    public static boolean isEmpty(final int[] array) {
        return length(array) == 0;
    }

    public static boolean contains(final int[] array, int element) {
        final int length = length(array);
        for (int i = 0; i < length; i++) {
            if (array[i] == element) {
                return true;
            }
        }
        return false;
    }

    public static int length(final byte[] array) {
        return array == null ? 0 : array.length;
    }

    public static boolean isEmpty(final byte[] array) {
        return length(array) == 0;
    }

    public static String get(final String[] array, int index) {
        return get(array, index, null);
    }

    public static int get(final int[] array, int index) {
        return get(array, index, 0);
    }

    public static float get(final float[] array, int index) {
        return get(array, index, 0);
    }

    public static float get(final float[] array, int index, float defaultValue) {
        return index >= 0 && index < length(array) ? array[index] : defaultValue;
    }

    public static int get(final int[] array, int index, int defaultValue) {
        return index >= 0 && index < length(array) ? array[index] : defaultValue;
    }

    public static <T> T get(final T[] array, int index, T defaultValue) {
        return index >= 0 && index < length(array) ? array[index] : defaultValue;
    }

    public static <T> T get(final T[] array, int index) {
        return get(array, index, null);
    }

    public static <T> void set(final T[] array, int index, T value) {
        if (index >= 0 && index < length(array)) {
            array[index] = value;
        };
    }

    public static void set(final float[] array, int index, float value) {
        if (index >= 0 && index < length(array)) {
            array[index] = value;
        };
    }
}