package com.hhplus.lecture.domain.enrollment;

import com.hhplus.lecture.domain.lecture.Lecture;
import com.hhplus.lecture.domain.user.UserAccount;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints={ @UniqueConstraint(
                        name="uniqueKey",
                        columnNames={"user_id", "lecture_id"})})
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @JoinColumn(name = "lecture_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lecture lecture;

    @Column(nullable = false)
    private EnrollmentStatus status;

    @Builder
    public Enrollment(UserAccount userAccount, Lecture lecture) {
        this.userAccount = userAccount;
        this.lecture = lecture;
        this.status = EnrollmentStatus.ENROLLED;
    }

    public static Enrollment enroll(UserAccount userAccount, Lecture lecture) {
        return new Enrollment(userAccount, lecture);
    }

    public boolean isSameUser(UserAccount user) {
        return this.userAccount.equals(user);
    }


}
