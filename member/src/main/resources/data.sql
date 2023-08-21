insert into member (created_date, last_modified_date, age, email, password, name, nickname, profile_image, sex, temperature, local_id)
values (now(), now(), 25, 'aaa@naver.com', '$2a$10$50S6QGa1nvtqO8QJfBfwaOXqm.UVNTE/ljolYa.L1TuapxsO8Awju', 'aaa', 'AAA', null, 'm', 36.5, 1),
       (now(), now(), 31, 'bbb@naver.com', '$2a$10$50S6QGa1nvtqO8QJfBfwaOXqm.UVNTE/ljolYa.L1TuapxsO8Awju', 'bbb', 'BBB', null, 'm', 36.5, 2),
       (now(), now(), 22, 'ccc@naver.com', '$2a$10$50S6QGa1nvtqO8QJfBfwaOXqm.UVNTE/ljolYa.L1TuapxsO8Awju', 'ccc', 'CCC', null, 'w', 40.5, 3),
       (now(), now(), 23, 'ddd@naver.com', '$2a$10$50S6QGa1nvtqO8QJfBfwaOXqm.UVNTE/ljolYa.L1TuapxsO8Awju', 'ddd', 'DDD', null, 'm', 50.5, 950),
       (now(), now(), 28, 'eee@naver.com', '$2a$10$50S6QGa1nvtqO8QJfBfwaOXqm.UVNTE/ljolYa.L1TuapxsO8Awju', 'eee', 'EEE', null, 'w', 60.0, 4),
       (now(), now(), 21, 'test@naver.com', '$2a$10$50S6QGa1nvtqO8QJfBfwaOXqm.UVNTE/ljolYa.L1TuapxsO8Awju', 'test', 'test', null, 'w', 38.0, 300);