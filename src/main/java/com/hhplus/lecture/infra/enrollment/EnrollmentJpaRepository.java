package com.hhplus.lecture.infra.enrollment;

import com.hhplus.lecture.domain.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentJpaRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByLectureId(Long lectureId);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.userAccount.id = :userId AND e.lecture.id = :lectureId")
    long countByUserIdAndLectureId(@Param("userId") Long userId, @Param("lectureId") Long lectureId);
}
