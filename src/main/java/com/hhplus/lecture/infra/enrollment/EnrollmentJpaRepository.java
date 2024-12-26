package com.hhplus.lecture.infra.enrollment;

import com.hhplus.lecture.domain.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentJpaRepository extends JpaRepository<Enrollment, Long> {

}
