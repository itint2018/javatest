INSERT INTO users (enabled, first_name, last_name, login, password, session)
VALUES (true, 'Администратор', 'Администратор', 'admin', '827ccb0eea8a706c4c34a16891f84e7b', '');


INSERT INTO role (name)
VALUES ('ROLE_ADMIN');
INSERT INTO role (name)
VALUES ('ROLE_MANAGER');
INSERT INTO role (name)
VALUES ('ROLE_EMPLOYEE');

INSERT INTO user_role (user_id, role_id)
select users.id as user_id, role.id as role_id
from users
         join role
where users.login = 'admin'
  and role.name = 'ROLE_ADMIN';

INSERT INTO options_room (icon, name)
VALUES ('mdi-projector', 'Проектор');
INSERT INTO options_room (icon, name)
VALUES ('mdi-square-edit-outline', 'Маркерная доска');
INSERT INTO options_room (icon, name)
VALUES ('mdi-table-furniture', 'Интерактивный стол');
