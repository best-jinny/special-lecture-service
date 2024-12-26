package com.hhplus.lecture.domain.lecture;

import com.hhplus.lecture.domain.enrollment.Enrollment;
import com.hhplus.lecture.domain.enrollment.EnrollmentRepository;
import com.hhplus.lecture.domain.user.UserAccount;
import com.hhplus.lecture.interfaces.dto.LectureResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private LectureService lectureService;

    @Test
    void 신청가능_강의와_유효한_사용자_계정으로_수강신청에_성공한다() {
        // given
        UserAccount user = UserAccount.builder()
                .name("김학생")
                .email("kim@nave.com")
                .build();

        Lecture lecture = Lecture.builder()
                .title("면접특강")
                .capacity(30)
                .build();

        when(lectureRepository.findById(1L)).thenReturn(Optional.of(lecture));

        // when
        lectureService.register(1L, user);

        // then
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
        assertThat(lecture.getCurrentEnrollmentsCount()).isEqualTo(1);
    }

    @Test
    void 날짜_지정_없이_특강을_조회하면_신청_가능한_모든_특강이_조회된다() {
        // given
        Lecture lecture = Lecture.builder()
                .title("면접특강")
                .capacity(30)
                .build();

        when(lectureRepository.findAllAvailableLectures(any(LocalDateTime.class))).thenReturn(Collections.singletonList(lecture));

        // when
        List<LectureResponse> lectures = lectureService.findAvailableLectures(null);

        // then
        assertThat(lectures).isNotEmpty();
        assertThat(lectures.get(0).getTitle()).isEqualTo("면접특강");
    }

    @Test
    void 날짜를_지정해서_특강을_조회하면_해당_날짜의_신청_가능한_모든_특강이_조회된다() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 28);
        Lecture lecture1 = Lecture.builder()
                .title("면접특강")
                .date(date)
                .capacity(30)
                .build();

        Lecture lecture2 = Lecture.builder()
                .title("이력서특강")
                .date(date)
                .capacity(30)
                .build();

        List<Lecture> lectures = Arrays.asList(lecture1, lecture2);
        when(lectureRepository.findAvailableLecturesOnDate(date)).thenReturn(lectures);

        // when
        List<LectureResponse> lectureResponses = lectureService.findAvailableLectures(date);

        // then
        assertThat(lectureResponses.size()).isEqualTo(2);
        assertThat(lectureResponses.get(0).getTitle()).isEqualTo("면접특강");
        assertThat(lectureResponses.get(1).getTitle()).isEqualTo("이력서특강");
    }

    @Test
    void userId로_해당_유저가_신청한_특강_목록을_조회하면_강의명_강사_강의시간_정보가_포함된_리스트가_반환된다() {
        // given
        Long userId = 1L;
        Lecture lecture1 = Lecture.builder()
                .title("면접특강")
                .lecturerName("하헌우")
                .date(LocalDate.of(2024, 12, 28))
                .startTime("10:00")
                .capacity(30)
                .build();

        Lecture lecture2 = Lecture.builder()
                .title("이력서특강")
                .lecturerName("허재")
                .date(LocalDate.of(2024, 12, 28))
                .startTime("15:00")
                .capacity(30)
                .build();

        List<Lecture> lectures = Arrays.asList(lecture1, lecture2);
        when(lectureRepository.findAllByUserId(userId)).thenReturn(lectures);

        // when
        List<LectureResponse> lectureResponses = lectureService.findLecturesByUser(userId);

        // then
        assertThat(lectureResponses.size()).isEqualTo(2);
        assertThat(lectures.get(0).getTitle()).isEqualTo("면접특강");
        assertThat(lectures.get(0).getLecturerName()).isEqualTo("하헌우");
        assertThat(lectures.get(0).getDate()).isEqualTo(LocalDate.of(2024, 12, 28));
        assertThat(lectures.get(0).getStartTime()).isEqualTo("10:00");
        assertThat(lectures.get(1).getTitle()).isEqualTo("이력서특강");
        assertThat(lectures.get(1).getLecturerName()).isEqualTo("허재");
        assertThat(lectures.get(1).getDate()).isEqualTo(LocalDate.of(2024, 12, 28));
        assertThat(lectures.get(1).getStartTime()).isEqualTo("15:00");
    }
}
