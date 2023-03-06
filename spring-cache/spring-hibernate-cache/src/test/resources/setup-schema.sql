SET REFERENTIAL_INTEGRITY FALSE;

truncate table employee;
truncate table department;

SET REFERENTIAL_INTEGRITY TRUE;

select next value for department_seq;
insert into department (name, id, executives) values ('engineering', 1, false);

select next value for department_seq;
insert into department (name, id, executives) values ('sales', 2, false);

select next value for employee_seq;
insert into employee (department_id, name, id) values (1, 'John Wick', 1);

select next value for employee_seq;
insert into employee (department_id, name, id) values (1, 'Jane Doe', 2);

select next value for employee_seq;
insert into employee (department_id, name, id) values (2, 'Jack Sparrow', 3);

commit;