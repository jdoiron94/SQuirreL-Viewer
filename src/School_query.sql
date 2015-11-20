select classroom, TeacherFN, TeacherLN from test.teacher where Subject = 'Mathematics';

select TeacherID from test.teacher where Classroom = 100;

select TeacherID, TeacherLN, Subject from test.teacher;

select 1Class, 2Class, 3Class, 4Class, 5Class, 6Class from test.stuschedule where StudentID = 12345;

select StudentID from test.student;

select StudentID from test.student where StudentCI = '3018675309';

select Classroom from test.teacher where TeacherID = 22123;
