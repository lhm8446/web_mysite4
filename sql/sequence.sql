-- sequence 


drop sequence USER_SEQ;

create sequence user_seq
start WITH 1 
increment by 1
maxvalue 9999999999;

drop sequence guestbook_SEQ;

create sequence guestbook_seq
start WITH 1 
increment by 1
maxvalue 9999999999;


drop sequence board_seq;

create sequence board_seq
start WITH 1 
increment by 1
maxvalue 9999999999;


drop sequence gallery_seq;

create sequence gallery_seq
start WITH 1 
increment by 1
maxvalue 9999999999;
