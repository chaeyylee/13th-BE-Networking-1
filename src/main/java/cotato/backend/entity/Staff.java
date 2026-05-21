package cotato.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "staff")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true, length = 11)
    private String phoneNumber;

    @Column(nullable = false)
    private String role; // 파트장/기획팀장/홍보팀장/부회장/회장/교육팀장

    public void update(String name, Integer age, String phoneNumber, String role) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Builder
    public Staff(String name, Integer age, String phoneNumber, String role) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
