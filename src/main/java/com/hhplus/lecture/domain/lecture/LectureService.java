package com.hhplus.lecture.domain.lecture;

import com.hhplus.lecture.domain.enrollment.Enrollment;
import com.hhplus.lecture.domain.enrollment.EnrollmentRepository;
import com.hhplus.lecture.domain.user.UserAccount;
import com.hhplus.lecture.interfaces.dto.LectureResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final EnrollmentRepository enrollmentRepository;

    /* 수강신청 */
    public void register(Long lectureId, UserAccount userAccount) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new EntityNotFoundException("Lecture is not found"));
        Enrollment enrollment = lecture.enroll(userAccount);
        enrollmentRepository.save(enrollment);
    }

    /* 신청 가능한 특강 목록 조회 (현재 기준 모든 강의 / 특정 날짜에 진행되는 강의) */
    public List<LectureResponse> findAvailableLectures(LocalDate date) {
        if (date == null) {
            // 현 시점에서 신청 가능한 모든 강의
            return lectureRepository.findAllAvailableLectures(LocalDateTime.now())
                    .stream()
                    .map(LectureResponse::fromEntity)
                    .toList();
        } else {
            // 특정 날짜 기준으로 강의 조회
            return lectureRepository.findAvailableLecturesOnDate(date)
                    .stream()
                    .map(LectureResponse::fromEntity)
                    .toList();
        }
    }

    /* 특정 유저가 신청한 특강 목록 조회 */
    public List<LectureResponse> findLecturesByUser(Long userId) {
        return lectureRepository.findAllByUserId(userId)
                .stream()
                .map(LectureResponse::fromEntity)
                .toList();
    }
}
