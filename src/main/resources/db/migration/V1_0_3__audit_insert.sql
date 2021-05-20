INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (10, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (11, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (12, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (13, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (14, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (15, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (16, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (17, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (18, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (19, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (20, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (21, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (22, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (23, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (24, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (25, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (26, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (27, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (28, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (29, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (30, extract(epoch from now()) * 1000, null);
INSERT INTO Revision_info(revision_id, rev_timestamp, person_id) VALUES (31, extract(epoch from now()) * 1000, null);


INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class,user_id, possessor_id, expiration_date, building_abbreviature, room, price, residual_price, description)
VALUES (10, 'AAKP00001', 0, 'Klassikapp 1', 'PV_KAPP', 3, 4, '2021-05-31', 'U02', '103', 150.00, 150.00, 'Kapp on, rohkemat ei vaja');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, price, residual_price)
VALUES (11, 'AAKP00002', 0, 'Klassikapp 2', 'PV_KAPP', 4, '2021-05-31', 'U02', '103', 130.00, 130.00);

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, price, residual_price)
VALUES (12, 'AAKP00003', 0, 'Klassikapp 3', 'PV_KAPP', 4, '2021-05-31', 'U02', '103', 120.00, 120.00);

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, price, residual_price)
VALUES (13, 'AAKP00004', 0, 'Klassikapp 4', 'PV_KAPP', 4, '2021-05-31', 'U02', '103', 100.00, 100.00);

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, description)
VALUES (14, 'LD01', 0, 'Laud ilus', 'PV_LAUD', 3, '2024-09-01', 'U01', '228', 'Väga hea laud, meeldib kuidas see särab päikese käes...');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, description)
VALUES (15, 'LD02', 0, 'Laud ilus', 'PV_LAUD', 3, '2022-10-13', 'U01', '228', 'Любовь может изменить человека до неузнаваемости.');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, possessor_id, expiration_date, building_abbreviature, room, description)
VALUES (16, 'LD03', 0, 'Laud ilus', 'PV_LAUD', 3, '2024-01-05', 'U01', '228', 'Надо жить, надо любить, надо верить.');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, building_abbreviature, room)
VALUES (17, 'AK1132', 0, 'Arvutikomplekt Vasja', 'VV_ARVUTIKOMPLEKT', 1, 1, 'SOC', '203');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, building_abbreviature)
VALUES (18, 'AK1133', 0, 'Arvutikomplekt Nikita', 'VV_ARVUTIKOMPLEKT', 2, 2, 'U06');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, building_abbreviature, room)
VALUES (19, 'AK1134', 0, 'Arvutikomplekt Artur', 'VV_ARVUTIKOMPLEKT', 3, 2, 'SOC', '320');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature, room)
VALUES (20, 'AK1120', 0, 'Arvutihiir HP 2210', 'VV_ARVUTIKOMPLEKT', 1, 1, 15.00, 15.00, 'SOC', '203');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature)
VALUES (21, 'AK1121', 0, 'Arvutihiir HP 2210', 'VV_ARVUTIKOMPLEKT', 2, 2, 15.00, 15.00, 'U06');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature, room, description)
VALUES (22, 'AK1122', 0, 'Arvutihiir HP 3333', 'VV_ARVUTIKOMPLEKT', 3, 2, 15.00, 15.00, 'SOC', '320', 'The mouse need to be replaced for a new one.');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature, room)
VALUES (23, 'AK1123', 0, 'Arvutiklaviatuur HP 2210', 'VV_ARVUTIKOMPLEKT', 1, 1, 30.00, 30.00, 'SOC', '203');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature)
VALUES (24, 'AK1124', 0, 'Arvutiklaviatuur HP 2210', 'VV_ARVUTIKOMPLEKT', 2, 2, 30.00, 30.00, 'U06');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature, room)
VALUES (25, 'AK1125', 0, 'Arvutiklaviatuur HP 2210', 'VV_ARVUTIKOMPLEKT', 3, 2, 30.00, 30.00, 'SOC', '203');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature, room)
VALUES (26, 'AK1126', 0, 'Arvutimonitor LG 27F19ERR18', 'VV_ARVUTIKOMPLEKT', 1, 1, 69.99, 50.00, 'SOC', '203');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature)
VALUES (27, 'AK1127', 0, 'Arvutimonitor HP 2210', 'VV_ARVUTIKOMPLEKT', 2, 2, 55.00, 55.00, 'U06');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature, room)
VALUES (28, 'AK1128', 0, 'Arvutimonitor Samsung 24T43SSR9', 'VV_ARVUTIKOMPLEKT', 3, 2, 100.00, 100.00, 'SOC', '320');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature, room)
VALUES (29, 'AK1129', 0, 'Lenovo V50s SFF 11EF0039PB', 'VV_ARVUTIKOMPLEKT', 1, 1, 823.39, 400.00, 'SOC', '203');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature)
VALUES (30, 'AK1130', 0, 'Lenovo V50s SFF 11EF0039PB', 'VV_ARVUTIKOMPLEKT', 2, 2, 823.39, 400.00, 'U06');

INSERT INTO Asset_audit (revision_id, id, revision_type, name, sub_class, user_id, possessor_id, price, residual_price, building_abbreviature, room)
VALUES (31, 'AK1131', 0, 'Lenovo V50s SFF 11EF0039PB', 'VV_ARVUTIKOMPLEKT', 3, 2, 823.39, 200.00, 'SOC', '320');
