package net.example.postaltrackingservice.service;


import net.example.postaltrackingservice.model.dto.PostalItemHistoryDto;

import java.util.Optional;

public interface ReportService {

    Optional<PostalItemHistoryDto> getPostalItemHistory(long postalItemID);

}
