package net.example.postaltrackingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.example.postaltrackingservice.model.dto.PostalItemHistoryDto;
import net.example.postaltrackingservice.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController implements AdminController {

    private final ReportService reportService;

    @PreAuthorize("hasAnyAuthority('POST_WORKER', 'ADMIN', 'USER')")
    @GetMapping("/history/{postalItemID}")
    @Operation(summary = "Статус и история движений почтового отправления.")
    public ResponseEntity<?> getPostalItemHistory(@PathVariable("postalItemID") long id) {
        log.info("Request [GET] /report/history/{}", id);
        Optional<PostalItemHistoryDto> historyDto = reportService.getPostalItemHistory(id);

        if (historyDto.isPresent()) {
            return ResponseEntity.ok(historyDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
