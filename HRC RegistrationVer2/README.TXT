What type of information each file contains:

INPUT
SessionList: Session ID Number, Session type (0==slotA only,1==slotB only,2==slotA&B separately(diff students),3==slotA&B continuous 2 hours (same students)), Max Number of students that can be assigned for EACH Slot, Speaker name, Room number, Room Type
Responses: Student First Name, Student Last Name, Grade,Choice1,Choice2,Choice3,Choice4,Choice5
UnregisteredStudents: Grade, {Student Last Name, Student First Name}

OUTPUT
Assignment: Session ID Number, Number of students enrolled, List of their names
Schedule_ByGrade: Grade, Student name, Slot A Session, Slot B Session
Schedule_ByName: Student name, Grade, Slot A Session, Slot B Session