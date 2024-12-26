package com.hhplus.lecture.infra.lecture;

import com.hhplus.lecture.domain.lecture.Lecture;
import com.hhplus.lecture.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureJpaRepository.findById(id);
    }

    @Override
    public List<Lecture> findAllAvailableLectures(LocalDateTime now) {
        return lectureJpaRepository.findAllAvailableLectures(now);
    }

    @Override
    public List<Lecture> findAvailableLecturesOnDate(LocalDate date) {
        return lectureJpaRepository.findAvailableLecturesOnDate(date);
    }

    @Override
    public List<Lecture> findAllByUserId(Long userId) {
        return lectureJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Lecture> findByIdForUpdate(Long id) {
        return lectureJpaRepository.findByIdForUpdate(id);
    }

    @Override
    public void save(Lecture lecture) {
        lectureJpaRepository.save(lecture);
    }
}
