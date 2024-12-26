package com.hhplus.lecture.facade.lecture;

import com.hhplus.lecture.domain.lecture.LectureService;
import com.hhplus.lecture.domain.user.UserAccount;
import com.hhplus.lecture.domain.user.UserService;
import com.hhplus.lecture.interfaces.dto.LectureEnrollRequest;
import com.hhplus.lecture.interfaces.dto.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {
    private final LectureService lectureService;
    private final UserService userService;

    public void registerForLecture(LectureEnrollRequest lectureEnrollRequest) {
        UserAccount user = userService.validateUser(lectureEnrollRequest.getUserId());
        lectureService.register(lectureEnrollRequest.getLectureId(), user);
    }

    public List<LectureResponse> findAvailableLectures(LocalDate date) {
        return lectureService.findAvailableLectures(date);
    }

    public List<LectureResponse> findLecturesByUserId(Long userId) {
        return lectureService.findLecturesByUser(userId);
    }
}
