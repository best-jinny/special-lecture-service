package com.hhplus.lecture.domain.enrollment;

import java.util.List;

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);
    List<Enrollment> findAllByLectureId(Long lectureId);
    long countByUserAccountAndLecture(Long userId, Long lectureId);
}
