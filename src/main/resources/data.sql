insert into USER(id, name, priority) values(1, 'Ludovic CWI', 25);
insert into USER(id, name, priority) values(2, 'Stéphane', 10);
insert into USER(id, name, priority) values(3, 'Azzad', 15);
insert into USER(id, name, priority) values(4, 'Guillaume', 20);
insert into USER(id, name, priority) values(5, 'Olivier I.', 5);
insert into USER(id, name, priority) values(6, 'Olivier F.', 1);

insert into PARKING_PLACE (id, numero, user_id) values (1, 255, 6);
insert into PARKING_PLACE (id, numero, user_id) values (2, 256, 5);
insert into PARKING_PLACE (id, numero, user_id) values (3, 257, 2);

insert into VACATION(id, user_id, start_date, end_date) values(1, 6, parsedatetime('25/09/2018', 'dd/MM/yyyy'), parsedatetime('29/09/2018', 'dd/MM/yyyy'));