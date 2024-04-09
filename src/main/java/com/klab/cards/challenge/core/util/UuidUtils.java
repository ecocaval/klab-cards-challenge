package com.klab.cards.challenge.core.util;

import java.util.UUID;

public class UuidUtils {

    public static boolean stringIsAValidUuid(String uuidString) {
        try {
            UUID.fromString(uuidString);
            return true;
        } catch (IllegalArgumentException ignore) {
            return false;
        }
    }
}
