delete from book;
delete from review;
insert into book(id, title, version) values(0, 'Alice in Wonderland', 0);
insert into review(id, text, version) values(0, 'Hello, World!', 0);
commit;
