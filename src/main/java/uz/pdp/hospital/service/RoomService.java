package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hospital.entity.Room;
import uz.pdp.hospital.repo.RoomRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService implements BaseService<Room, Integer> {
    private final RoomRepo roomRepo;
    @Override
    public Optional<Room> findById(Integer id) {
        return roomRepo.findById(id);
    }

    @Override
    public Room save(Room room) {
        return roomRepo.save(room);
    }

    @Override
    public void delete(Integer integer) {
        roomRepo.deleteById(integer);
    }

    @Override
    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    @Override
    public Room update(Room room, Integer integer) {
        room.setId(integer);
        return roomRepo.save(room);
    }
}
