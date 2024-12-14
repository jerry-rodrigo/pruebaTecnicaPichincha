INSERT INTO app_user (username, password, role, name) VALUES ('admin', '$2a$10$1CoaZgW3BSnjXnmw3Lbbp.UZ3JTy/1o0/rKkk.8Ai6C4yb/nN5o9C', 'ROLE_ADMIN', 'Jerry');
INSERT INTO app_user (username, password, role, name) VALUES ('user', '$2a$10$Cph5gNCzM99eFiB5flkpJeSrxf0NfUbCH4SfEEf3m.CxFE5JPDgOi', 'ROLE_USER', 'Rodrigo');

INSERT INTO exchange_rate (id, currency_from, currency_to, rate) VALUES (1, 'USD', 'PEN', 3.70);
INSERT INTO exchange_rate (id, currency_from, currency_to, rate) VALUES (2, 'PEN', 'USD', 0.27);
