INSERT INTO Person(azure_id, firstname, lastname) VALUES ('WW1234561', 'Vassili-Groza', 'Gorb');
INSERT INTO Person(azure_id, firstname, lastname) VALUES ('WW192883', 'Nikita-Bird', 'Birjukovs');
INSERT INTO Person(azure_id, firstname, lastname) VALUES ('WW111111', 'Artur-Sex-Instruktor', 'Hurgada');

INSERT INTO Possessor(structural_unit, subdivision) VALUES (112, 110);
INSERT INTO Possessor(structural_unit, subdivision) VALUES (234, 229);
INSERT INTO Possessor(structural_unit, subdivision) VALUES (2354, 2334);
INSERT INTO Possessor(structural_unit) VALUES (2211);

INSERT INTO Classification (sub_class, main_class) VALUES ('VV_ARVUTIKOMPLEKT', 'VV');
INSERT INTO Classification (sub_class, main_class) VALUES ('PV_LAUD', 'PV');
INSERT INTO Classification (sub_class, main_class) VALUES ('PV_KAPP', 'PV');

INSERT INTO Asset (id, name, sub_class,user_id, possessor_id, expiration_date, building_abbreviature, room, price, residual_price, description)
VALUES ('AAKP00001', 'Klassikapp 1', 'PV_KAPP', 3, 4, '2021-05-31', 'U02', '103', 150.00, 150.00, 'Kapp on, rohkemat ei vaja');
INSERT INTO Comment (asset_id, text) VALUES ('AAKP00001', 'Hea kapp');
INSERT INTO Comment (asset_id, text) VALUES ('AAKP00001', 'Mulle ei meeldi');
INSERT INTO Comment (asset_id, text) VALUES ('AAKP00001', 'Internetti pole');

INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, price, residual_price)
VALUES ('AAKP00002', 'Klassikapp 2', 'PV_KAPP', 4, '2021-05-31', 'U02', '103', 130.00, 130.00);

INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, price, residual_price)
VALUES ('AAKP00003', 'Klassikapp 3', 'PV_KAPP', 4, '2021-05-31', 'U02', '103', 120.00, 120.00);

INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, price, residual_price)
VALUES ('AAKP00004', 'Klassikapp 4', 'PV_KAPP', 4, '2021-05-31', 'U02', '103', 100.00, 100.00);

INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, description)
VALUES ('LD01', 'Laud ilus', 'PV_LAUD', 3, '2024-09-01', 'U01', '228', 'Väga hea laud, meeldib kuidas see särab päikese käes...');



