select classroom, TeacherFN, TeacherLN from teacher where Subject = 'Mathematics';
select TeacherID from teacher where Classroom = 100;
select TeacherID, TeacherLN, Subject from teacher;
select 1Class, 2Class, 3Class, 4Class, 5Class, 6Class from stuschedule where StudentID = 12345;
select StudentID from student;
select StudentID from student where StudentCI = '3015555555';
select Classroom from teacher where TeacherID = 22123;