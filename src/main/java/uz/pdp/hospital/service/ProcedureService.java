package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Procedure;
import uz.pdp.hospital.repo.ProcedureRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcedureService implements BaseService<Procedure, Integer> {
    private final ProcedureRepo procedureRepo;
    @Override
    public Optional<Procedure> findById(Integer id) {
        return procedureRepo.findById(id);
    }

    @Override
    public Procedure save(Procedure procedure) {
        return procedureRepo.save(procedure);
    }

    @Override
    public void delete(Integer integer) {
        procedureRepo.deleteById(integer);
    }

    @Override
    public List<Procedure> findAll() {
        return procedureRepo.findAll();
    }

    @Override
    public Procedure update(Procedure procedure, Integer integer) {
        procedure.setId(integer);
        return procedureRepo.save(procedure);
    }

    public void saveAll(List<Procedure> procedures) {
        procedureRepo.saveAll(procedures);
    }
}
