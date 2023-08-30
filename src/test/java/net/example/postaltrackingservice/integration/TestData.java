package net.example.postaltrackingservice.integration;

import net.example.postaltrackingservice.model.dto.PostageEventReadDto;

import java.time.OffsetDateTime;
import java.util.List;

import static net.example.postaltrackingservice.model.enums.EventType.ARRIVAL;
import static net.example.postaltrackingservice.model.enums.EventType.DEPARTURE;

public class TestData {

    public static final Long POSTAL_ITEM_ID_1 = 1L;
    public static final Long POSTAL_ITEM_ID_2 = 2L;
    public static final Long POSTAL_ITEM_ID_3 = 3L;
    public static final Long POSTAL_ITEM_ID_4 = 4L;
    public static final Long POSTAL_ITEM_ID_5 = 5L;
    public static final Long POSTAL_ITEM_ID_NOT_FOUND = 20L;
    public static final String POST_OFFICE_1 = "1001001";
    public static final String POST_OFFICE_2 = "2002002";
    public static final String POST_OFFICE_NOT_FOUND = "1000000";
    public static final String POST_OFFICE_1_NAME = "Почтовое отделение 1001001";
    public static final Long ADDRESS_ID_1 = 1L;
    public static final String ADDRESS_1 = "Москва, ул. Рабочая, 20";
    public static final String ADDRESS_NEW = "Москва, ул. Бурмистрова, 1";

    public static final Long LAST_EVENT_ID = 9L;
    public static final int TOTAL_EVENTS = 4;
    public static final List<PostageEventReadDto> ALL_EVENTS = stringRepresentationOfAllEvents();

    private static List<PostageEventReadDto> stringRepresentationOfAllEvents() {
        return List.of(
                new PostageEventReadDto(4L, 4L, "2002002", "Почтовое отделение 2002002", OffsetDateTime.parse("2023-08-21T09:30:10.789068Z"), ARRIVAL),
                new PostageEventReadDto(6L, 4L, "2002002", "Почтовое отделение 2002002", OffsetDateTime.parse("2023-08-21T10:20:47.899777Z"), DEPARTURE),
                new PostageEventReadDto(7L, 4L, "3003003", "Почтовое отделение 3003003", OffsetDateTime.parse("2023-08-22T09:30:10.569069Z"), ARRIVAL),
                new PostageEventReadDto(9L, 4L, "3003003", "Почтовое отделение 3003003", OffsetDateTime.parse("2023-08-23T11:30:11.564388Z"), DEPARTURE));
    }

}
