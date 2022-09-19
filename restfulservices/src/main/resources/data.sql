
create table Employee (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    emp_role varchar(50) default NULL
);
 
 create table if not exists USER (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) NOT NULL,
    user_name varchar(50) NOT NULL,
    password varchar(250) default NULL
);

create table if not exists ROLE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) NOT NULL
);

create table if not exists USER_ROLES (
    user_id INT,
    role_id INT,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES USER(id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES ROLE(id)
);


INSERT INTO Employee (first_name, last_name, emp_role)
 VALUES ('P','Ram', 'Senior Manager'),
 ('P','Ram', 'Team Lead'),
 ('P','Prasad', 'Developer'),
 ('K','Kollu', 'QA'),
 ('S','Pranav', 'Manual Tester'),
 ('C','Cherry', 'Manager');

INSERT INTO USER (name, password, user_name) 
VALUES('admin', '$2a$10$fDJAzi6F1/YBW0ZcbimDneru7daQKsGwxCgRgkGfgAKukEuv4zpA.', 'admin');

INSERT INTO ROLE (name) VALUES('SUPER ADMIN');

INSERT INTO USER_ROLES(user_id, role_id) (select u.id as user_id,r.id as role_id from USER u INNER JOIN ROLE r ON u.id = r.id);



