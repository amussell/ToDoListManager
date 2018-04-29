CREATE TABLE task (id INT AUTO_INCREMENT,
                        label VARCHAR(50) NOT NULL,
                        create_date DATE NOT NULL,
                        due_date DATE,
                        status INT DEFAULT 0
                        PRIMARY KEY (id) );

CREATE TABLE tag (name VARCHAR(25),
                    PRIMARY KEY (name) );

CREATE TABLE task_tag (task_id FOREIGN KEY,
                        tag_name FOREIGN KEY
                        PRIMARY KEY (task_id, tag_name));