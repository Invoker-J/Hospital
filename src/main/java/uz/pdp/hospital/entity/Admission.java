package uz.pdp.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.hospital.entity.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "admission")
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private LocalDateTime admissionDateTime;
    private LocalDateTime arrivedDateTime;
    private String description;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @OneToMany
    private List<Procedure> procedures;

    @ManyToOne
    private Administrator administrator;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Integer getSumOfProcedure(){
        return this.procedures.stream()
                .mapToInt(Procedure::getPrice)// This step converts Integer to int
                .sum();
    }

    public boolean isArrivedOnTime() {
        if (arrivedDateTime == null || admissionDateTime == null) {
            return false;
        }
        return !arrivedDateTime.isAfter(admissionDateTime);
    }

}