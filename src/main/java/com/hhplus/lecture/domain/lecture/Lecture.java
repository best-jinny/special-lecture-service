package com.hhplus.lecture.domain.lecture;

import com.hhplus.lecture.common.exceptions.CapacityExceededException;
import com.hhplus.lecture.common.exceptions.DuplicateException;
import com.hhplus.lecture.domain.enrollment.Enrollment;
import com.hhplus.lecture.domain.user.UserAccount;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String lecturerName;

    @Column
    private LocalDate date;

    @Column
    private String startTime;

    @Column
    private String endTime;

    @Column
    private String venue;

    @Column
    private int capacity;

    @Column
    private int currentEnrollmentsCount = 0;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @Builder
    public Lecture(String title, String description, String lecturerName, LocalDate date, String startTime, String endTime, String venue, int capacity) {
        this.title = title;
        this.description = description;
        this.lecturerName = lecturerName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.capacity = capacity;
        this.currentEnrollmentsCount = 0;
    }

    // 정원 초과 여부 확인
    public boolean isFull() {
        return this.capacity <= this.currentEnrollmentsCount;
    }

    // 중복 신청 여부 확인
    public boolean isAlreadyEnrolled(UserAccount user) {
        return enrollments.stream().anyMatch(enrollment -> enrollment.isSameUser(user));
    }

    public Enrollment enroll(UserAccount user) {
        if(isFull()) {
            throw new CapacityExceededException("선착순 마감 됐습니다");
        }

        if(isAlreadyEnrolled(user)) {
            throw new DuplicateException("이미 수강 신청했습니다");
        }

        Enrollment enrollment = new Enrollment(user, this);
        this.enrollments.add(enrollment);
        this.currentEnrollmentsCount++;

        return enrollment;
    }


}
