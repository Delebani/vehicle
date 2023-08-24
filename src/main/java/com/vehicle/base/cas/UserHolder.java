package com.vehicle.base.cas;


import com.alibaba.ttl.TransmittableThreadLocal;

public class UserHolder {
    UserHolder() {
    }

    private static TransmittableThreadLocal<CurrentUser> USER = new TransmittableThreadLocal<>();

    public static <T extends CurrentUser> void put(T t) {
        USER.set(t);
    }

    public static <T extends CurrentUser> T get() {
        return (T) USER.get();
    }

    public static void remove() {
        USER.remove();
    }
}
