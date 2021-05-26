import com.reservation.models.Booking;
import com.reservation.models.Room;
import com.reservation.repostiories.RoomRepository;
import com.reservation.services.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {
    private Logger logger =  LogManager.getLogger(RoomServiceTest.class);

    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    RoomService roomService;

    @Test
    public void findAvailableRoomsTest() {
        List<Room> list = new ArrayList<Room>();
        Room room1 = new Room();
        room1.setRoomNo("R01");
        room1.setId(UUID.fromString("294433c4-77b8-4d43-8131-5de7e115a749"));
        room1.setName("Mocha");
        room1.setBuildingNo("Building A");
        room1.setCapacity(8);
        room1.setLevel("level 1");

        Booking booking1 = new Booking();
        booking1.setStartDateTime(LocalDateTime.parse("2021-03-21T09:00:00"));
        booking1.setEndDateTime(LocalDateTime.parse("2021-03-21T10:00:00"));
        room1.setBookings(Arrays.asList(booking1));

        Room room2 = new Room();
        room2.setRoomNo("R02");
        room2.setId(UUID.fromString("294433c4-77b8-4d43-8131-5de7e115a749"));
        room2.setName("Cappuccino");
        room2.setBuildingNo("Building A");
        room2.setCapacity(15);
        room2.setLevel("level 2");
        room2.setBookings(new ArrayList<>());

        when(roomRepository.findAll()).thenReturn(Arrays.asList(room1, room2));

        logger.debug("findAvailableRooms Test Case 1...");
        List<Room> availableRooms_case1 = roomService.findAvailableRooms(LocalDateTime.parse("2021-03-21T07:30:00"),
                LocalDateTime.parse("2021-03-21T09:00:00"));
        assertEquals(2, availableRooms_case1.size());

        logger.debug("findAvailableRooms Test Case 2...");
        List<Room> availableRooms_case2 = roomService.findAvailableRooms(LocalDateTime.parse("2021-03-21T10:00:00"),
                LocalDateTime.parse("2021-03-21T12:30:00"));
        assertEquals(2, availableRooms_case2.size());

        logger.debug("findAvailableRooms Test Case 3...");
        List<Room> availableRooms_case3 = roomService.findAvailableRooms(LocalDateTime.parse("2021-03-21T09:15:00"),
                LocalDateTime.parse("2021-03-21T09:45:00"));
        assertEquals(1, availableRooms_case3.size());

        logger.debug("findAvailableRooms Test Case 4...");
        List<Room> availableRooms_case4 = roomService.findAvailableRooms(LocalDateTime.parse("2021-03-21T08:00:00"),
                LocalDateTime.parse("2021-03-21T09:01:00"));
        assertEquals(1, availableRooms_case4.size());

        logger.debug("findAvailableRooms Test Case 5...");
        List<Room> availableRooms_case5 = roomService.findAvailableRooms(LocalDateTime.parse("2021-03-21T09:01:00"),
                LocalDateTime.parse("2021-03-21T10:01:00"));
        assertEquals(1, availableRooms_case5.size());

        logger.debug("findAvailableRooms Test Case 6...");
        List<Room> availableRooms_case6 = roomService.findAvailableRooms(LocalDateTime.parse("2021-03-21T09:01:00"),
                LocalDateTime.parse("2021-03-21T12:01:00"));
        assertEquals(1, availableRooms_case6.size());

        logger.debug("findAvailableRooms Test Case 7...");
        List<Room> availableRooms_case7 = roomService.findAvailableRooms(LocalDateTime.parse("2021-03-21T08:01:00"),
                LocalDateTime.parse("2021-03-21T11:01:00"));
        assertEquals(1, availableRooms_case7.size());

        logger.debug("findAvailableRooms Test Case 8...");
        List<Room> availableRooms_case8 = roomService.findAvailableRooms(LocalDateTime.parse("2021-03-21T08:01:00"),
                LocalDateTime.parse("2021-03-21T11:01:00"));
        assertEquals(1, availableRooms_case8.size());

        logger.debug("findAvailableRooms Test Case 9...");
        List<Room> availableRooms_case9 = roomService.findAvailableRooms(LocalDateTime.parse("2021-03-22T08:01:00"),
                LocalDateTime.parse("2021-03-22T11:01:00"));
        assertEquals(2, availableRooms_case9.size());
    }

}
