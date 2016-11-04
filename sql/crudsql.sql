-- *******users*******
-- insert
insert INTO USERS VALUES(user_seq.nextval , '이하민', 'lhm8446@gmail.com', '1234', 'male');

-- delete
delete from users where no = 2;

--select 
select no,name from users where email = 'lhm8446@gmail.com' and password = '1234';
select * from users;
select * from guestbook;
select no,name,email,password,gender from users where no = 2;

--update 
update users set name = '이하민',password = '1234',gender = 'male' where no = 2;

commit;

-- ******* guestbook ******

insert into guestbook values(guestbook_SEQ.nextval,'이하민','안녕','1234',sysdate);
select * from guestbook;

delete from guestbook where no=2;

-- *******board*******

delete from board;

select * from board;


-- view
select no, title, content, hit,group_no, order_no,depth from board where no = 2; 

update board set hit = hit + 1 where no = 2;          -- 조회수 늘리기 


--list


select count(*) from board;     -- total page count 

select * from (select no, title, hit, reg_date, depth, name, users_no, rownum as rn
				from( select a.no,a.title,a.hit,to_char(a.reg_date,'yyyy-mm-dd hh:mi:ss') as reg_date, a.depth, b.name , a.users_no	  
					    from board a, users b
					   where a.users_no = b.no
-- and title like '%kwd%' or content like '%kwd%'
					order by GROUP_NO desc, order_no asc))

where (1-1)*5+1 <= rn and rn <= 1*5;

select * from guestbook where no = 65;
-- 새로운 글
insert into board values(board_seq.nextval, '안녕', '안녕', sysdate, 0, nvl((select max(group_no) from board),0)+1 , 1, 0 ,2);

insert into board values(board_seq.nextval, '점심 뭐', '냉무', sysdate, 0, nvl((select max(group_no) from board),0)+1 , 1, 0 ,2);

insert into board values(board_seq.nextval, '배고프다 그만', '냉무', sysdate, 0, nvl((select max(group_no) from board),0)+1 , 1, 0 ,2);

-- 답글
update board set order_no = order_no +1 where group_no = 2 and order_no > 1;  --부모 글 순서

insert into board values(board_seq.nextval, '짬뽕싫어', '냉무', sysdate, 0, 2, -- 부모글의 그룹--  2, -- 부모글 순서 +1--  1, -- 부모글 깊이 +1-	   2);


