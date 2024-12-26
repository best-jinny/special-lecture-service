package com.hhplus.lecture.interfaces.dto;

import com.hhplus.lecture.domain.lecture.Lecture;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LectureResponse {

    private Long id;
    private String title;
    private String description;
    private String lecturerName;
    private LocalDate date;
    private String startTime;
    private String endTime;
    private String venue;
    private int capacity;
    private int currentEnrollmentCount;
    private boolean isAvailable;


    public LectureResponse(Long id, String title, String description, String lecturerName, LocalDate date, String startTime, String endTime, String venue, int capacity, int currentEnrollmentCount, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lecturerName = lecturerName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.capacity = capacity;
        this.currentEnrollmentCount = currentEnrollmentCount;
        this.isAvailable = isAvailable;
    }

    public static LectureResponse fromEntity(Lecture lecture) {
        boolean isAvailable = lecture.getCurrentEnrollmentsCount() < lecture.getCapacity();
        return new LectureResponse(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getDescription(),
                lecture.getLecturerName(),
                lecture.getDate(),
                lecture.getStartTime(),
                lecture.getEndTime(),
                lecture.getVenue(),
                lecture.getCapacity(),
                lecture.getCurrentEnrollmentsCount(),
                isAvailable
        );
    }
}
