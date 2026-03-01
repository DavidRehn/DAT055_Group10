-- Tagit detta från förra kursen, bra att ha med tanke på att vi kommer ändra på SQL koden rätt mycket och vill inte ha problem från
-- tidigare varianter

-- This script deletes everything in your database
-- this is the only part of the script you do not need to understand
\set QUIET true
SET client_min_messages TO WARNING; -- Less talk please.
-- This script deletes everything in your database
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO CURRENT_USER;
-- This line makes psql stop on the first error it encounters
-- You may want to remove this when running tests that are intended to fail
\set ON_ERROR_STOP ON
SET client_min_messages TO NOTICE; -- More talk
\set QUIET false


-- \ir is for include relative, it will run files in the same directory as this file
-- Note that these are not SQL statements but rather Postgres commands (no terminating semicolon). 
\ir SQL_template.sql
insert into Users values('A','2');
insert into Users values('B','3');
insert into Users values('C','4');
insert into Users values('D','5');
insert into Chats values('a');
insert into Chats values('b');
insert into Chats values('c');
insert into Messages values ('A', 'a', 'text', 'Hello');
insert into Messages(sender, message_id, chat, msg_type, image_url) values ('B', 'a', 'image','src/Images/test.jpg');
insert into Messages values ('A', 'a', 'text', 'Hello');
insert into Messages values ('A', 'a', 'text', 'Hello');
insert into Messages values ('A', 'a', 'text', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
insert into Messages(sender, message_id, chat, msg_type, image_url) values ('B', 'a', 'image','src/Images/test.jpg');

SELECT * From messages;