package uz.pdp.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hospital.entity.Room;

public interface RoomRepo extends JpaRepository<Room, Integer> {
}