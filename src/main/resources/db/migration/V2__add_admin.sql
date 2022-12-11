INSERT INTO users(id, archive, email, name, password, phone, role, bucket_id)
VALUES (1, false, 'admin@gmail.com', 'admin', '1488', '+380952284613', 'ADMIN', null);

ALTER SEQUENCE user_seq RESTART WITH 2;