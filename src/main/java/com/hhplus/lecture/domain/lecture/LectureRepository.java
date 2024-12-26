package com.hhplus.lecture.domain.lecture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureRepository {
    Optional<Lecture> findById(Long id);

    List<Lecture> findAllAvailableLectures(LocalDateTime now);
    List<Lecture> findAvailableLecturesOnDate(LocalDate date);

    List<Lecture> findAllByUserId(Long userId);

    Optional<Lecture> findByIdForUpdate(Long id);

    void save(Lecture lecture);
}
