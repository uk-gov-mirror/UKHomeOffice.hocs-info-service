package uk.gov.digital.ho.hocs.info.logging;

public enum LogEvent {

    UNIT_CREATED,
    UNIT_RENAMED,
    TEAM_CREATED,
    TEAM_ADDED_TO_UNIT,
    TEAM_RENAMED,
    TEAM_PERMISSIONS_UPDATED,
    USER_CREATED,
    USER_ADDED_TO_TEAM;

    public static final String EVENT = "event_id";
}