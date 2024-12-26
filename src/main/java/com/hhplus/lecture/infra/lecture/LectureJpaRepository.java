package com.hhplus.lecture.infra.lecture;

import com.hhplus.lecture.domain.lecture.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
    // 현재 기준 이후 강의 조회
    @Query("SELECT l FROM Lecture l WHERE l.date > :now")
    List<Lecture> findAllAvailableLectures(@Param("now") LocalDateTime now);

    // 특정 날짜 기준으로 강의 조회
    @Query("SELECT l FROM Lecture l WHERE DATE(l.date) = :date")
    List<Lecture> findAvailableLecturesOnDate(@Param("date") LocalDate date);

    // 특정 유저가 신청한 강의 조회
    @Query("SELECT l FROM Lecture l JOIN l.enrollments e WHERE e.userAccount.id = :userId")
    List<Lecture> findAllByUserId(@Param("userId") Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l WHERE l.id = :lectureId")
    Optional<Lecture> findByIdForUpdate(@Param("lectureId") Long lectureId);
}
