package uz.pdp.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.hospital.entity.enums.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;
}