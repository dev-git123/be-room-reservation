## API


Method | Attempt | Param | Description
--- |--- | --- | --- 
Get  |/meetingRoom/booking |  | return all bookings 
Get  |/meetingRoom/booking | @RequestParam(name="employeeNo") | return bookings that reserved by this employeeNo
Post  |/meetingRoom/booking | @RequestBody BookingDTO bookingDTO  | reserve meeting room
Delete  |/meetingRoom/booking/{bookingId}|  | cancel booking with bookingId
Get  |/employees|  | return all employee information
Get  |/rooms|  | return all room information
Get  |/rooms/{roomId}|  | return corresponding room information
Post  |/rooms | @RequestBody Room room  | add meeting room
Delete  |/rooms/{roomId}|  | delete room
Get  |/rooms/available| @RequestParam("from") , @RequestParam("to") | return available room list in-between Start Time and End Time


