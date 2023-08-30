package net.example.postaltrackingservice.handler;

import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.exception.PostOfficeNotFoundException;
import net.example.postaltrackingservice.exception.PostalItemNotFoundException;
import net.example.postaltrackingservice.repository.PostOfficeRepository;
import net.example.postaltrackingservice.repository.PostalItemRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EntityPresenceHandler {

    private final PostOfficeRepository postOfficeRepository;
    private final PostalItemRepository postalItemRepository;

    @Transactional(readOnly = true)
    public void checkPostOfficeByIndex(String index) {
        postOfficeRepository.findById(index)
                .orElseThrow(() -> new PostOfficeNotFoundException(
                        String.format("Не найдено почтовое отделение по индексу '%s'", index)));
    }

    @Transactional(readOnly = true)
    public void checkPostalItemByID(Long postalItemID) {
        postalItemRepository.findById(postalItemID)
                .orElseThrow(() -> new PostalItemNotFoundException(
                        String.format("Не найдено почтовое отправление по идентификатору '%s'", postalItemID)));
    }

}
