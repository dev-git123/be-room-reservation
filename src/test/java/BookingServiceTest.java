import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.models.Booking;
import com.reservation.models.BookingDTO;
import com.reservation.models.Employee;
import com.reservation.models.Room;
import com.reservation.repostiories.BookingRepository;
import com.reservation.services.BookingService;
import com.reservation.services.EmployeeService;
import com.reservation.services.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {
    private Logger logger =  LogManager.getLogger(BookingServiceTest.class);

    @Mock
    BookingRepository bookingRepository;
    @Mock
    RoomService roomService;
    @Mock
    ObjectMapper objectMapper;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    BookingService bookingService;
    
    @Before
    public void init_mocks(){
        //If we want to define the attribute values from JUnit Test
        ReflectionTestUtils.setField(bookingService, "varName", "value");
    }


    @Test
    public void whenBook_withNotAvailabeRoom_thenSuccess() throws Exception {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartDateTime("2021-03-02T11:23:32");
        bookingDTO.setEndDateTime("2021-03-02T12:23:32");
        bookingDTO.setRoomId(UUID.fromString("294433c4-77b8-4d43-8131-5de7e115a749"));
        bookingDTO.setEmployeeNo("e-123");

        Room room1 = new Room();
        room1.setId(UUID.fromString("294433c4-77b8-4d43-8131-5de7e115a749"));
        room1.setName("Mocha");
        room1.setBuildingNo("Building A");
        room1.setCapacity(8);
        room1.setLevel("level 1");

        Booking booking = new Booking();
        booking.setId(UUID.fromString("304433c4-77b8-4d43-8131-5de7e115b111"));
        booking.setStartDateTime(LocalDateTime.parse("2021-03-02T11:23:32"));
        booking.setEndDateTime(LocalDateTime.parse("2021-03-02T12:23:32"));
        booking.setRoom(new Room());
        booking.setEmployee(new Employee());

        when(roomService.findAvailableRooms(any(LocalDateTime.class),
                any(LocalDateTime.class))).thenReturn(Arrays.asList(room1));
        when(employeeService.findByEmployeeNo(anyString())).thenReturn(new Employee());
        when(roomService.findById(any(UUID.class))).thenReturn(new Room());
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.doBooking(bookingDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void whenBook_withNotAvailabeRoom_thenFail() throws Exception {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartDateTime("2021-03-02T11:23:32");
        bookingDTO.setEndDateTime("2021-03-02T12:23:32");
        bookingDTO.setRoomId(UUID.fromString("294433c4-77b8-4d43-8131-5de7e115a749"));
        bookingDTO.setEmployeeNo("e-123");

        Room room1 = new Room();
        room1.setId(UUID.fromString("294433c4-77b8-4d43-8131-5de7e115a749"));
        room1.setName("Mocha");
        room1.setBuildingNo("Building A");
        room1.setCapacity(8);
        room1.setLevel("level 1");

        Booking booking = new Booking();
        booking.setId(UUID.fromString("304433c4-77b8-4d43-8131-5de7e115b111"));
        booking.setStartDateTime(LocalDateTime.parse("2021-03-02T11:23:32"));
        booking.setEndDateTime(LocalDateTime.parse("2021-03-02T12:23:32"));
        booking.setRoom(new Room());
        booking.setEmployee(new Employee());

        when(roomService.findAvailableRooms(any(LocalDateTime.class),
                any(LocalDateTime.class))).thenReturn(new ArrayList());

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Booking result = bookingService.doBooking(bookingDTO);
            }
        });
    }
}
