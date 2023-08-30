package net.example.postaltrackingservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.example.postaltrackingservice.constant.Constant;
import net.example.postaltrackingservice.model.enums.EventType;
import net.example.postaltrackingservice.model.enums.PostalItemStatus;
import net.example.postaltrackingservice.model.enums.PostalItemType;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostalItemHistoryDto {
    private Long id;
    private PostalItemType type;
    private PostalItemStatus status;
    private List<EventHistory> eventHistory;


    @Getter
    @AllArgsConstructor
    public static class EventHistory {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE_FORMAT)
        private OffsetDateTime eventDate;
        private String postOfficeCode;
        private String postOfficeName;
        private EventType event;
    }

}
