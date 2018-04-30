INSERT INTO task (id,label,create_date,due_date,status) VALUES (1,'PS 1',2018-04-01,2018-03-02,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (2,'PS 2',2018-04-01,2018-03-09,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (3,'PS 3',2018-04-01,2018-03-17,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (4,'HW 1',2018-04-01,2018-01-08,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (5,'HW 2',2018-04-01,2018-01-25,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (6,'HW 3',2018-04-01,2018-03-15,0);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (7,'HW 4',2018-04-01,2018-04-15,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (8,'HW 1',2018-04-01,2018-02-05,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (9,'HW 2',2018-04-01,2018-02-25,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (10,'HW 3',2018-04-01,2018-04-04,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (11,'DB EXAM 1',2018-04-01,2018-02-07,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (12,'DB EXAM 2',2018-04-01,2018-04-25,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (13,'QUIZ 1',2018-04-01,2019-02-15,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (14,'QUIZ 2',2018-04-01,2018-03-13,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (15,'QUIZ 3',2018-04-01,2018-04-20,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (16,'421 EXAM 1',2018-04-01,2018-02-02,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (17,'421 EXAM 2',2018-04-01,2018-03-15,1);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (18,'421 EXAM 3',2018-04-01,2018-04-25,1);
INSERT INTO task (id,label,create_date,status) VALUES (19,'PWM TESTING',2018-04-01,0);
INSERT INTO task (id,label,create_date,status) VALUES (20,'GAZEBO SIM SETUP',2018-04-01,0);
INSERT INTO task (id,label,create_date,status) VALUES (21,'BUDGET REQUEST',2018-04-01,0);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (22,'PAY NESSA',2018-04-01,2018-06-01,0);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (23,'PICK VENUE',2018-04-01,2018-07-10,0);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (24,'FIND OFFICIANT',2018-04-01,2018-06-01,0);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (25,'PLAN MEALS',2018-04-01,2018-06-05,0);
INSERT INTO task (id,label,create_date,due_date,status) VALUES (26,'GIVE THIS GROUP AN A!',2018-04-01,2018-04-30,0);

INSERT INTO tags (name) VALUES ('school');
INSERT INTO tags (name) VALUES ('aclub');
INSERT INTO tags (name) VALUES ('410');
INSERT INTO tags (name) VALUES ('421');
INSERT INTO tags (name) VALUES ('455');
INSERT INTO tags (name) VALUES ('wedding');

INSERT INTO tagged_task (task_id,tag_name) VALUES (1,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (2,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (3,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (4,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (5,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (6,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (7,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (8,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (9,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (10,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (11,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (12,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (13,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (14,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (15,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (16,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (17,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (18,'school');
INSERT INTO tagged_task (task_id,tag_name) VALUES (19,'aclub');
INSERT INTO tagged_task (task_id,tag_name) VALUES (20,'aclub');
INSERT INTO tagged_task (task_id,tag_name) VALUES (21,'aclub');
INSERT INTO tagged_task (task_id,tag_name) VALUES (22,'wedding');
INSERT INTO tagged_task (task_id,tag_name) VALUES (23,'wedding');
INSERT INTO tagged_task (task_id,tag_name) VALUES (24,'wedding');
INSERT INTO tagged_task (task_id,tag_name) VALUES (25,'wedding');
INSERT INTO tagged_task (task_id,tag_name) VALUES (26,'wedding');
INSERT INTO tagged_task (task_id,tag_name) VALUES (1,'421');
INSERT INTO tagged_task (task_id,tag_name) VALUES (2,'421');
INSERT INTO tagged_task (task_id,tag_name) VALUES (3,'421');
INSERT INTO tagged_task (task_id,tag_name) VALUES (4,'455');
INSERT INTO tagged_task (task_id,tag_name) VALUES (5,'455');
INSERT INTO tagged_task (task_id,tag_name) VALUES (6,'455');
INSERT INTO tagged_task (task_id,tag_name) VALUES (7,'455');
INSERT INTO tagged_task (task_id,tag_name) VALUES (8,'410');
INSERT INTO tagged_task (task_id,tag_name) VALUES (9,'410');
INSERT INTO tagged_task (task_id,tag_name) VALUES (10,'410');
INSERT INTO tagged_task (task_id,tag_name) VALUES (11,'410');
INSERT INTO tagged_task (task_id,tag_name) VALUES (12,'410');
INSERT INTO tagged_task (task_id,tag_name) VALUES (13,'455');
INSERT INTO tagged_task (task_id,tag_name) VALUES (14,'455');
INSERT INTO tagged_task (task_id,tag_name) VALUES (15,'455');
INSERT INTO tagged_task (task_id,tag_name) VALUES (16,'421');
INSERT INTO tagged_task (task_id,tag_name) VALUES (17,'421');
INSERT INTO tagged_task (task_id,tag_name) VALUES (18,'421');