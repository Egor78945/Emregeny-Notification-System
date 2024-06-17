package org.database_service.app.services;

import lombok.RequiredArgsConstructor;
import org.database_service.app.model.entities.PhoneNumber;
import org.database_service.app.repositories.PhoneNumberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {
    private final PhoneNumberRepository phoneNumberRepository;

    public List<PhoneNumber> getByUserId(Long id) {
        return phoneNumberRepository.findAllByUserId(id);
    }
}
