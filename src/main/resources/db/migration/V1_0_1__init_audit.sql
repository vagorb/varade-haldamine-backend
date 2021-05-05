CREATE TABLE IF NOT EXISTS Revision_info (
    revision_id SERIAL PRIMARY KEY ,
    rev_timestamp BIGINT NOT NULL,
    person_id VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS Comment_audit (
   revision_id INTEGER NOT NULL,
   id SERIAL,
   revision_type SMALLINT NOT NULL,
   asset_id VARCHAR(20) REFERENCES Asset(id) ON DELETE CASCADE ON UPDATE CASCADE,
   text VARCHAR(255) NOT NULL,
   created_at TIMESTAMP DEFAULT NOW(),
   PRIMARY KEY (revision_id, asset_id),
   CONSTRAINT idfk_asset_revinfo_rev_id FOREIGN KEY (revision_id) REFERENCES Revision_info (revision_id)
);

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.modified_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE IF NOT EXISTS Asset_audit (
    revision_id INTEGER NOT NULL,
    id VARCHAR(20) NOT NULL,
    revision_type SMALLINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    sub_class VARCHAR(30) NOT NULL REFERENCES Classification(sub_class) ON DELETE RESTRICT ON UPDATE CASCADE,
    active BOOLEAN DEFAULT TRUE,
    user_id INT DEFAULT NULL REFERENCES Person(id) ON DELETE SET NULL ON UPDATE CASCADE,
    possessor_id INT NOT NULL REFERENCES Possessor(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    expiration_date DATE,
    delicate_condition BOOLEAN NOT NULL DEFAULT FALSE,
    checked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_at TIMESTAMP DEFAULT NOW(),
    price NUMERIC(12, 2),
    residual_price NUMERIC(12, 2),
    purchase_date TIMESTAMP,
    building_abbreviature VARCHAR(10) NOT NULL,
    room VARCHAR(10),
    description VARCHAR(255),
    PRIMARY KEY (revision_id, id),
    CONSTRAINT idfk_asset_revinfo_rev_id FOREIGN KEY (revision_id) REFERENCES Revision_info (revision_id)
);

CREATE TRIGGER set_timestamp
    BEFORE UPDATE ON Asset_audit
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TABLE IF NOT EXISTS Classification_audit (
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT NOT NULL,
    sub_class VARCHAR(30),
    main_class VARCHAR(20) NOT NULL,
    PRIMARY KEY (revision_id, sub_class),
    CONSTRAINT idfk_asset_revinfo_rev_id FOREIGN KEY (revision_id) REFERENCES Revision_info (revision_id)
);

CREATE TABLE IF NOT EXISTS Person_audit (
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT NOT NULL,
    id SERIAL,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    PRIMARY KEY (revision_id, id),
    CONSTRAINT idfk_asset_revinfo_rev_id FOREIGN KEY (revision_id) REFERENCES Revision_info (revision_id)
);

CREATE TABLE IF NOT EXISTS Possessor_audit (
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT NOT NULL,
    id SERIAL,
    structural_unit SMALLINT,
    subdivision SMALLINT,
    PRIMARY KEY (revision_id, id),
    CONSTRAINT idfk_asset_revinfo_rev_id FOREIGN KEY (revision_id) REFERENCES Revision_info (revision_id)
);

create sequence HIBERNATE_SEQUENCE
    minvalue 100000
    maxvalue 9999999999999999
    start with 100060
    increment by 1
    cache 20;
