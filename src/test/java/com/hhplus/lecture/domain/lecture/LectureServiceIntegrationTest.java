package com.hhplus.lecture.domain.lecture;

import com.hhplus.lecture.domain.enrollment.EnrollmentRepository;
import com.hhplus.lecture.domain.user.UserAccount;
import com.hhplus.lecture.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Sql(value = "/sql/lecture-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
public class LectureServiceIntegrationTest {

    @Autowired
    private LectureService lectureService;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    void 정원이_30명인_강의에_동시에_40명이_수강_신청을_시도하면_30명만_성공한다() throws InterruptedException {
        int userCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(userCount);
        CountDownLatch latch = new CountDownLatch(userCount);

        Lecture lecture = lectureRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("lecture not found"));

        for (long i = 1; i <= userCount; i++) {
            final Long userId = i;
            executorService.submit(() -> {
                try {
                    UserAccount userAccount = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
                    lectureService.register(lecture.getId(), userAccount);

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertThat(lecture.getCurrentEnrollmentsCount()).isEqualTo(30);
        assertThat(enrollmentRepository.findAllByLectureId(lecture.getId())).size().isEqualTo(30);
    }


    @Test
    void 동일한_유저_정보로_같은_특강을_5번_신청하면_1번만_성공한다() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Lecture lecture = lectureRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("lecture not found"));

        UserAccount user = userRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("User not found"));

        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> { try { lectureService.register(lecture.getId(), user); // 동시 요청
            } catch (EntityNotFoundException e) { // 예외 처리
                e.printStackTrace();
            } finally {
                latch.countDown();
            } });
        }

        latch.await();

        // 최종적으로 수강 신청된 데이터가 1개인지 확인
        long enrollmentCount = enrollmentRepository.countByUserAccountAndLecture(user.getId(), lecture.getId());
        assertThat(enrollmentCount).isEqualTo(1);
    }


}
