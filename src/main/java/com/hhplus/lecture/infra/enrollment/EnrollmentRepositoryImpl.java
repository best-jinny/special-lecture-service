package com.hhplus.lecture.infra.enrollment;

import com.hhplus.lecture.domain.enrollment.Enrollment;
import com.hhplus.lecture.domain.enrollment.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    private final EnrollmentJpaRepository enrollmentJpaRepository;

    @Override
    public Enrollment save(Enrollment enrollment) {
        return enrollmentJpaRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> findAllByLectureId(Long lectureId) {
        return enrollmentJpaRepository.findAllByLectureId(lectureId);
    }


    @Override
    public long countByUserAccountAndLecture(Long userId, Long lectureId) {
        return enrollmentJpaRepository.countByUserIdAndLectureId(userId, lectureId);
    }
}
