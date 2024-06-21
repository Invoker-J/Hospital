package uz.pdp.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.pdp.hospital.entity.Procedure;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcedureRequest {
    private Integer patientId;
    private List<Procedure> procedures;
}

