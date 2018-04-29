CREATE database if not exists Tasks;

use Tasks;

CREATE TABLE task (id INT AUTO_INCREMENT,
                        label VARCHAR(50) NOT NULL,
                        create_date DATE NOT NULL,
                        due_date DATE,
                        status INT DEFAULT 0,
                        PRIMARY KEY (id) );

CREATE TABLE tag (name VARCHAR(25),
                    PRIMARY KEY (name) );

CREATE TABLE tagged_task (task_id int,
                        tag_name varchar(25),
                        FOREIGN KEY (task_id) REFERENCES task(id),
                        FOREIGN KEY (tag_name) REFERENCES tag(name),
                        PRIMARY KEY (task_id, tag_name));

--DATA
insert into task (id, label, create_date, due_date, status) values ('1','Finish DB Project' , NOW(), null, '0');
insert ignore into tag  (name) values('DBHW');
insert into tagged_task (task_id, tag_name) values (1, 'DBHW');

insert into task (id, label, create_date, due_date, status) values ('2','Finish Algorithms Test' , NOW(), null, '0');
insert ignore into tag  (name) values('CSHW');
insert into tagged_task (task_id, tag_name) values (2, 'CSHW');

insert into task (id, label, create_date, due_date, status) values ('3','Sleep' , NOW(), null, '0');

insert into task (id, label, create_date, due_date, status) values ('4','Wakeup' , NOW(), null, '2');

insert into task (id, label, create_date, due_date, status) values ('5','' , NOW(), null, '0');
insert ignore into tag  (name) values('DBHW');
insert into tagged_task (task_id, tag_name) values (1, 'DBHW');