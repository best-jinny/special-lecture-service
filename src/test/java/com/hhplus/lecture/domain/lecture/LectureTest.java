package com.hhplus.lecture.domain.lecture;

import com.hhplus.lecture.common.exceptions.CapacityExceededException;
import com.hhplus.lecture.common.exceptions.DuplicateException;
import com.hhplus.lecture.domain.enrollment.Enrollment;
import com.hhplus.lecture.domain.user.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LectureTest {

    private Lecture lecture;
    private UserAccount user;

    @BeforeEach
    public void setUp() {
        user = UserAccount.builder()
                .name("김학생")
                .email("kim@naver.com")
                .build();

        lecture = Lecture.builder()
                .title("면접특강")
                .capacity(30)
                .build();
    }

    @Test
    public void 서른명이_수강신청이_완료된_상황에서_정원_초과_여부를_확인하면_true가_반환된다() {
        // given
        createEnrollments(lecture, 30);

        // when
        boolean result = lecture.isFull();

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void 동일_유저가_같은_강의에_중복_신청시_해당_유저의_신청_여부를_확인하면_true가_반환된다() {
        // given
        Enrollment firstEnrollment = Enrollment.enroll(user, lecture);
        lecture.getEnrollments().add(firstEnrollment);

        Enrollment secondEnrollment = Enrollment.enroll(user, lecture);
        lecture.getEnrollments().add(secondEnrollment);

        // when
        boolean result = lecture.isAlreadyEnrolled(user);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void 정상적으로_수강신청이_완료되면_수강신청_객체가_반환된다() {
        // when
        Enrollment enrollment = lecture.enroll(user);

        // then
        assertThat(enrollment).isNotNull();
        assertThat(lecture.getCurrentEnrollmentsCount()).isEqualTo(1);
        assertThat(lecture.getEnrollments()).contains(enrollment);
    }

    @Test
    public void 수강신청_인원_초과시_CapacityExceededException이_발생한다() {
        // given
        createEnrollments(lecture, 30);

        // when
        Exception exception = assertThrows(CapacityExceededException.class, () -> lecture.enroll(user));

        // then
        assertThat(exception).isInstanceOf(CapacityExceededException.class)
                .hasMessage("선착순 마감 됐습니다");
    }

    @Test
    public void 동일한_유저_아이디로_동일한_특강에_수강신청하면_DuplicateException이_발생한다() {
        // given
        lecture.enroll(user);

        // when
        Exception exception = assertThrows(DuplicateException.class, () -> lecture.enroll(user));

        // then
        assertThat(exception).isInstanceOf(DuplicateException.class)
                .hasMessage("이미 수강 신청했습니다");
    }

    private void createEnrollments(Lecture lecture, int count) {
        for (int i = 1; i <= count; i++) {
            UserAccount user = UserAccount.builder()
                    .name("유저" + i)
                    .email("user" + i + "@test.com")
                    .build();

            lecture.enroll(user);
        }
    }
}
