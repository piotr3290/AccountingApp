CREATE TABLE accounts_types
(
    id     INT         NOT NULL AUTO_INCREMENT,
    name   VARCHAR(64) NOT NULL,
    number INT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE accounts
(
    id              INT NOT NULL AUTO_INCREMENT,
    account_type_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_type_id) REFERENCES accounts_types (id)
);

CREATE TABLE cooperatives
(
    id             INT          NOT NULL AUTO_INCREMENT,
    account_id     INT          NOT NULL,
    name           VARCHAR(255) NOT NULL,
    nip            CHAR(10),
    regon          VARCHAR(14),
    city           VARCHAR(64),
    street         VARCHAR(255),
    house_number   VARCHAR(8),
    premise_number VARCHAR(8),
    postal_code    CHAR(6) CHECK ( postal_code REGEXP '[0-9]{2}-[0-9]{3}'),
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);

CREATE TABLE opening_balances
(
    id         INT     NOT NULL AUTO_INCREMENT,
    account_id INT     NOT NULL,
    balance    DOUBLE  NOT NULL,
    year       TINYINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);

CREATE TABLE buildings
(
    id             INT          NOT NULL AUTO_INCREMENT,
    cooperative_id INT          NOT NULL,
    city           VARCHAR(64)  NOT NULL,
    street         VARCHAR(255) NOT NULL,
    house_number   VARCHAR(8)   NOT NULL,
    postal_code    CHAR(6)      NOT NULL CHECK ( postal_code REGEXP '[0-9]{2}-[0-9]{3}'),
    PRIMARY KEY (id),
    FOREIGN KEY (cooperative_id) REFERENCES cooperatives (id)
);

CREATE TABLE premises_types
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE premises
(
    id               INT        NOT NULL AUTO_INCREMENT,
    building_id      INT        NOT NULL,
    premises_type_id INT        NOT NULL,
    premises_number  VARCHAR(8) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (building_id) REFERENCES buildings (id),
    FOREIGN KEY (premises_type_id) REFERENCES premises_types (id)
);

CREATE TABLE areas
(
    id          INT    NOT NULL AUTO_INCREMENT,
    premises_id INT    NOT NULL,
    start_date  DATE,
    end_date    DATE,
    value       DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (premises_id) REFERENCES premises (id)
);

CREATE TABLE people_numbers
(
    id          INT NOT NULL AUTO_INCREMENT,
    premises_id INT NOT NULL,
    start_date  DATE,
    end_date    DATE,
    number      INT NOT NULL CHECK ( number >= 0 ),
    PRIMARY KEY (id),
    FOREIGN KEY (premises_id) REFERENCES premises (id)
);

CREATE TABLE advances_types
(
    id               INT         NOT NULL AUTO_INCREMENT,
    name             VARCHAR(64) NOT NULL,
    calculation_type INT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE consumptions
(
    id              INT    NOT NULL AUTO_INCREMENT,
    advance_type_id INT    NOT NULL,
    premises_id     INT    NOT NULL,
    start_date      DATE,
    end_date        DATE,
    value           DOUBLE NOT NULL CHECK ( value >= 0 ),
    PRIMARY KEY (id),
    FOREIGN KEY (premises_id) REFERENCES premises (id),
    FOREIGN KEY (advance_type_id) REFERENCES advances_types (id)
);

CREATE TABLE contractors
(
    id         INT         NOT NULL AUTO_INCREMENT,
    name       VARCHAR(64) NOT NULL,
    nip        CHAR(10),
    account_id INT         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);

CREATE TABLE landlords
(
    id              INT NOT NULL AUTO_INCREMENT,
    account_id      INT NOT NULL,
    first_name      VARCHAR(64),
    middle_name     VARCHAR(64),
    last_name       VARCHAR(64),
    city            VARCHAR(64),
    street          VARCHAR(255),
    house_number    VARCHAR(8),
    premises_number VARCHAR(8),
    postal_code     CHAR(6) CHECK ( postal_code REGEXP '[0-9]{2}-[0-9]{3}'),
    pesel           CHAR(11) CHECK ( pesel REGEXP '[0-9]{11}'),
    phone_number    VARCHAR(15),
    email_address   VARCHAR(255) CHECK ( email_address REGEXP '%@%'),
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);

CREATE TABLE premises_landlords
(
    id          INT NOT NULL AUTO_INCREMENT,
    premises_id INT NOT NULL,
    landlord_id INT NOT NULL,
    start_date  DATE,
    end_date    DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (premises_id) REFERENCES premises (id),
    FOREIGN KEY (landlord_id) REFERENCES landlords (id)
);

CREATE TABLE invoices_types
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE invoices
(
    id               INT    NOT NULL AUTO_INCREMENT,
    invoice_type_id  INT    NOT NULL,
    cooperative_id   INT    NOT NULL,
    contractor_id    INT    NOT NULL,
    amount           DOUBLE NOT NULL,
    realization_date DATE   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (invoice_type_id) REFERENCES invoices_types (id),
    FOREIGN KEY (cooperative_id) REFERENCES cooperatives (id),
    FOREIGN KEY (contractor_id) REFERENCES contractors (id)
);

CREATE TABLE landlords_payments
(
    id               INT  NOT NULL AUTO_INCREMENT,
    realization_date DATE NOT NULL,
    cooperative_id   INT  NOT NULL,
    landlord_id      INT  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cooperative_id) REFERENCES cooperatives (id),
    FOREIGN KEY (landlord_id) REFERENCES landlords (id)
);

CREATE TABLE contractors_payments
(
    id               INT  NOT NULL AUTO_INCREMENT,
    realization_date DATE NOT NULL,
    cooperative_id   INT  NOT NULL,
    contractor_id    INT  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cooperative_id) REFERENCES cooperatives (id),
    FOREIGN KEY (contractor_id) REFERENCES contractors (id)
);

CREATE TABLE advances
(
    id               INT               NOT NULL AUTO_INCREMENT,
    realization_date DATE              NOT NULL,
    cooperative_id   INT               NOT NULL,
    advance_type_id  INT               NOT NULL,
    landlord_id      INT               NOT NULL,
    month            TINYINT UNSIGNED  NOT NULL CHECK ( month BETWEEN 1 AND 12),
    year             SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cooperative_id) REFERENCES cooperatives (id),
    FOREIGN KEY (landlord_id) REFERENCES landlords (id),
    FOREIGN KEY (advance_type_id) REFERENCES advances_types (id)
);

CREATE TABLE rates
(
    id              INT NOT NULL AUTO_INCREMENT,
    start_date      DATE,
    end_date        DATE,
    cooperative_id  INT,
    building_id     INT,
    premises_id     INT,
    advance_type_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cooperative_id) REFERENCES cooperatives (id),
    FOREIGN KEY (building_id) REFERENCES buildings (id),
    FOREIGN KEY (premises_id) REFERENCES premises (id),
    FOREIGN KEY (advance_type_id) REFERENCES advances_types (id)
);