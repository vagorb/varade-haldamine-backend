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

INSERT INTO Asset (id, name, sub_class,user_id, possessor_id, expiration_date) VALUES ('AAKP00001', 'Klassikapp 1', 'PV_KAPP', 3, 4, '2021-05-31');
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AAKP00001', 'U02', '103');
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AAKP00001', 150.00, 150.00);
INSERT INTO Description (asset_id, text) VALUES ('AAKP00001', 'Kapp on, rohkemat ei vaja');
INSERT INTO Comment (asset_id, text) VALUES ('AAKP00001', 'Hea kapp');
INSERT INTO Comment (asset_id, text) VALUES ('AAKP00001', 'Mulle ei meeldi');
INSERT INTO Comment (asset_id, text) VALUES ('AAKP00001', 'Internetti pole');

INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date) VALUES ('AAKP00002', 'Klassikapp 2', 'PV_KAPP', 4, '2021-05-31');
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AAKP00002', 'U02', '103');
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AAKP00002', 130.00, 130.00);

INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date) VALUES ('AAKP00003', 'Klassikapp 3', 'PV_KAPP', 4, '2021-05-31');
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AAKP00003', 'U02', '103');
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AAKP00003', 120.00, 120.00);

INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date) VALUES ('AAKP00004', 'Klassikapp 4', 'PV_KAPP', 4, '2021-05-31');
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AAKP00004', 'U02', '103');
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AAKP00004', 100.00, 100.00);



INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date) VALUES ('LD01', 'Laud ilus', 'PV_LAUD', 3, '2024-09-01');
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('LD01', 'U01', '228');
INSERT INTO Description (asset_id, text) VALUES ('LD01', 'Väga hea laud, meeldib kuidas see särab päikese käes...');

INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date) VALUES ('LD02', 'Laud ilus', 'PV_LAUD', 3, '2022-10-13');
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('LD02', 'U01', '228');
INSERT INTO Description (asset_id, text) VALUES ('LD02', 'Любовь может изменить человека до неузнаваемости.');

INSERT INTO Asset (id, name, sub_class, possessor_id, expiration_date) VALUES ('LD03', 'Laud ilus', 'PV_LAUD', 3, '2021-01-05');
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('LD03', 'U01', '228');
INSERT INTO Description (asset_id, text) VALUES ('LD03', 'Надо жить, надо любить, надо верить.');


INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1132', 'Arvutikomplekt Vasja', 'VV_ARVUTIKOMPLEKT', 1, 1);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1132', 'SOC', '203');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1132', 'AK1132');

INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1133', 'Arvutikomplekt Nikita', 'VV_ARVUTIKOMPLEKT', 2, 2);
INSERT INTO Address (asset_id, building_abbreviature) VALUES ('AK1133', 'U06');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1133', 'AK1133');

INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1134', 'Arvutikomplekt Artur', 'VV_ARVUTIKOMPLEKT', 3, 2);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1134', 'SOC', '320');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1134', 'AK1134');



INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1120', 'Arvutihiir HP 2210', 'VV_ARVUTIKOMPLEKT', 1, 1);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1120', 15.00, 15.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1120', 'SOC', '203');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1120', 'AK1132');


INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1121', 'Arvutihiir HP 2210', 'VV_ARVUTIKOMPLEKT', 2, 2);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1121', 15.00, 15.00);
INSERT INTO Address (asset_id, building_abbreviature) VALUES ('AK1121', 'U06');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1121', 'AK1133');



INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1122', 'Arvutihiir HP 3333', 'VV_ARVUTIKOMPLEKT', 3, 2);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1122', 15.00, 15.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1122', 'SOC', '320');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1122', 'AK1134');
INSERT INTO Description (asset_id, text) VALUES ('AK1122', 'The mouse need to be replaced for a new one.');




INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1123', 'Arvutiklaviatuur HP 2210', 'VV_ARVUTIKOMPLEKT', 1, 1);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1123', 30.00, 30.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1123', 'SOC', '203');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1123', 'AK1132');


INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1124', 'Arvutiklaviatuur HP 2210', 'VV_ARVUTIKOMPLEKT', 2, 2);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1124', 30.00, 30.00);
INSERT INTO Address (asset_id, building_abbreviature) VALUES ('AK1124', 'U06');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1124', 'AK1133');


INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1125', 'Arvutiklaviatuur HP 2210', 'VV_ARVUTIKOMPLEKT', 3, 2);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1125', 30.00, 30.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1125', 'SOC', '320');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1125', 'AK1134');



INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1126', 'Arvutimonitor LG 27F19ERR18', 'VV_ARVUTIKOMPLEKT', 1, 1);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1126', 69.99, 50.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1126', 'SOC', '203');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1126', 'AK1132');

INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1127', 'Arvutimonitor HP 2210', 'VV_ARVUTIKOMPLEKT', 2, 2);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1127', 55.00, 55.00);
INSERT INTO Address (asset_id, building_abbreviature) VALUES ('AK1127', 'U06');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1127', 'AK1133');


INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1128', 'Arvutimonitor Samsung 24T43SSR9', 'VV_ARVUTIKOMPLEKT', 3, 2);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1128', 100.00, 100.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1128', 'SOC', '320');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1128', 'AK1134');



INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1129', 'Lenovo V50s SFF 11EF0039PB', 'VV_ARVUTIKOMPLEKT', 1, 1);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1129', 823.39, 400.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1129', 'SOC', '203');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1129', 'AK1132');


INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1130', 'Lenovo V50s SFF 11EF0039PB', 'VV_ARVUTIKOMPLEKT', 2, 2);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1130', 823.39, 400.00);
INSERT INTO Address (asset_id, building_abbreviature) VALUES ('AK1130', 'U06');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1130', 'AK1133');


INSERT INTO Asset (id, name, sub_class, user_id, possessor_id) VALUES ('AK1131', 'Lenovo V50s SFF 11EF0039PB', 'VV_ARVUTIKOMPLEKT', 3, 2);
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1131', 823.39, 200.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1131', 'SOC', '320');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1131', 'AK1134');




