INSERT INTO Person(azure_id, firstname, lastname) VALUES ('WW1234561', 'VASJA', 'GORB');
INSERT INTO Possessor(structural_unit, subdivision) VALUES (112, 110);
INSERT INTO Classification (sub_class, main_class) VALUES ('hiir', 'arvutikomponent');
INSERT INTO Asset (id, name, sub_class, user_id, possessor_id, expiration_date) VALUES ('AK1120', 'Arvutihiir HP 2210', 'hiir', 1, 1, '2008-11-11');
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1120', 15.00, 15.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1120', 'SOC', '203');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1120', 'AK1120');

INSERT INTO Person(azure_id, firstname, lastname) VALUES ('WW192883', 'Niki', 'Birj');
INSERT INTO Classification (sub_class, main_class) VALUES ('klaviatuur', 'arvutikomponent');
INSERT INTO Asset (id, name, sub_class, user_id, possessor_id, expiration_date) VALUES ('AK1121', 'Arvutiklaviatuur HP 2210', 'klaviatuur', 2, 1, '2040-11-11');
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1121', 30.00, 30.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1121', 'SOC', '203');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1121', 'AK1121');

INSERT INTO Person(azure_id, firstname, lastname) VALUES ('WW111111', 'Art', 'Hur');
INSERT INTO Classification (sub_class, main_class) VALUES ('monitor', 'arvutikomponent');
INSERT INTO Asset (id, name, sub_class, user_id, possessor_id, expiration_date) VALUES ('AK1122', 'Arvutimonitor HP 2210', 'monitor', 3, 1, '2030-11-11');
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1122', 55.00, 55.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1122', 'SOC', '203');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1122', 'AK1122');

INSERT INTO Asset (id, name, sub_class, user_id, possessor_id, expiration_date) VALUES ('AK1123', 'Arvutihiir HP 2210', 'hiir', 1, 1, '2100-11-11');
INSERT INTO Worth (asset_id, price, residual_price) VALUES ('AK1123', 15.00, 15.00);
INSERT INTO Address (asset_id, building_abbreviature, room) VALUES ('AK1123', 'SOC', '203');
INSERT INTO Kit_relation (component_asset_id, major_asset_id) VALUES ('AK1123', 'AK1123');
