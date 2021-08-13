INSERT INTO users (id, enabled, first_name, last_name, login, password, session)
VALUES (1, true, 'Администратор', 'Администратор', 'admin', '827ccb0eea8a706c4c34a16891f84e7b', '');


INSERT INTO role (id, name)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name)
VALUES (2, 'ROLE_MANAGER');
INSERT INTO role (id, name)
VALUES (3, 'ROLE_EMPLOYEE');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1);

INSERT INTO options_room (id, icon, name)
VALUES (1, 'mdi mdi-projector', 'Проектор');
INSERT INTO options_room (id, icon, name)
VALUES (2, 'mdi mdi-square-edit-outline', 'Маркерная доска');
INSERT INTO options_room (id, icon, name)
VALUES (3, 'mdi mdi-table-furniture', 'Интерактивный стол');
