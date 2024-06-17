package org.database_service.app.services.converters;

import org.database_service.app.model.entities.PhoneNumber;

import java.util.List;

public class PhoneNumberConverter {
    public static List<String> convertToString(List<PhoneNumber> phoneNumbers) {
        return phoneNumbers
                .stream()
                .map(n -> n.getNumber())
                .toList();
    }
}
