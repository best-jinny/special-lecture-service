package com.hhplus.lecture.interfaces.controller.lecture;

import com.hhplus.lecture.facade.lecture.LectureFacade;
import com.hhplus.lecture.interfaces.dto.LectureEnrollRequest;
import com.hhplus.lecture.interfaces.dto.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureFacade lectureFacade;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody LectureEnrollRequest lectureEnrollRequest) {
        lectureFacade.registerForLecture(lectureEnrollRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<LectureResponse>> list(@RequestParam(required = false) LocalDate date) {
        List<LectureResponse> availableLectures = lectureFacade.findAvailableLectures(date);
        return ResponseEntity.ok(availableLectures);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LectureResponse>> listByUserId(@PathVariable Long userId) {
        List<LectureResponse> userLectures = lectureFacade.findLecturesByUserId(userId);
        return ResponseEntity.ok(userLectures);
    }
}
