create table accounts_types
(
    id     int auto_increment
        constraint `PRIMARY`
        primary key,
    name   varchar(64) not null,
    number int         not null
);

create table accounts
(
    id              int auto_increment
        constraint `PRIMARY`
        primary key,
    account_type_id int not null,
    constraint accounts_ibfk_1
        foreign key (account_type_id) references accounts_types (id)
);

create index account_type_id
    on accounts (account_type_id);

create table areas_types
(
    id   int auto_increment
        constraint `PRIMARY`
        primary key,
    name varchar(64) not null
);

create table charges_types
(
    id     int auto_increment
        constraint `PRIMARY`
        primary key,
    name   varchar(64) not null,
    number int         not null
);

create table contractors
(
    id         int auto_increment
        constraint `PRIMARY`
        primary key,
    name       varchar(64) not null,
    nip        char(10)    null,
    account_id int         not null,
    constraint contractors_ibfk_1
        foreign key (account_id) references accounts (id)
);

create index account_id
    on contractors (account_id);

create table cooperatives
(
    id             int auto_increment
        constraint `PRIMARY`
        primary key,
    account_id     int          not null,
    name           varchar(255) not null,
    nip            char(10)     null,
    regon          varchar(14)  null,
    city           varchar(64)  null,
    street         varchar(255) null,
    house_number   varchar(8)   null,
    premise_number varchar(8)   null,
    postal_code    char(6)      null,
    constraint cooperatives_ibfk_1
        foreign key (account_id) references accounts (id)
);

create table buildings
(
    id             int auto_increment
        constraint `PRIMARY`
        primary key,
    cooperative_id int          not null,
    city           varchar(64)  not null,
    street         varchar(255) not null,
    house_number   varchar(8)   not null,
    postal_code    char(6)      not null,
    constraint buildings_ibfk_1
        foreign key (cooperative_id) references cooperatives (id)
);

create index cooperative_id
    on buildings (cooperative_id);

create table contractors_payments
(
    id               int auto_increment
        constraint `PRIMARY`
        primary key,
    realization_date date   not null,
    cooperative_id   int    not null,
    contractor_id    int    not null,
    value            double not null,
    constraint contractors_payments_ibfk_1
        foreign key (cooperative_id) references cooperatives (id),
    constraint contractors_payments_ibfk_2
        foreign key (contractor_id) references contractors (id)
);

create index contractor_id
    on contractors_payments (contractor_id);

create index cooperative_id
    on contractors_payments (cooperative_id);

create index account_id
    on cooperatives (account_id);

create table flyway_schema_history
(
    installed_rank int                                 not null
        constraint `PRIMARY`
        primary key,
    version        varchar(50)                         null,
    description    varchar(200)                        not null,
    type           varchar(20)                         not null,
    script         varchar(1000)                       not null,
    checksum       int                                 null,
    installed_by   varchar(100)                        not null,
    installed_on   timestamp default CURRENT_TIMESTAMP not null,
    execution_time int                                 not null,
    success        tinyint(1)                          not null
);

create index flyway_schema_history_s_idx
    on flyway_schema_history (success);

create table hibernate_sequence
(
    next_val bigint null
);

create table invoices_types
(
    id     int auto_increment
        constraint `PRIMARY`
        primary key,
    name   varchar(64) not null,
    number int         not null
);

create table invoices
(
    id               int auto_increment
        constraint `PRIMARY`
        primary key,
    invoice_type_id  int    not null,
    cooperative_id   int    not null,
    contractor_id    int    not null,
    value            double not null,
    realization_date date   not null,
    constraint invoices_ibfk_1
        foreign key (invoice_type_id) references invoices_types (id),
    constraint invoices_ibfk_2
        foreign key (cooperative_id) references cooperatives (id),
    constraint invoices_ibfk_3
        foreign key (contractor_id) references contractors (id)
);

create index contractor_id
    on invoices (contractor_id);

create index cooperative_id
    on invoices (cooperative_id);

create index invoice_type_id
    on invoices (invoice_type_id);

create table landlords
(
    id              int auto_increment
        constraint `PRIMARY`
        primary key,
    account_id      int          not null,
    first_name      varchar(64)  null,
    middle_name     varchar(64)  null,
    last_name       varchar(64)  null,
    city            varchar(64)  null,
    street          varchar(255) null,
    house_number    varchar(8)   null,
    premises_number varchar(8)   null,
    postal_code     char(6)      null,
    phone_number    varchar(15)  null,
    email_address   varchar(255) null,
    constraint landlords_ibfk_1
        foreign key (account_id) references accounts (id)
);

create index account_id
    on landlords (account_id);

create table opening_balances
(
    id             int auto_increment
        constraint `PRIMARY`
        primary key,
    account_id     int      not null,
    balance        double   not null,
    year           smallint not null,
    cooperative_id int      not null,
    constraint opening_balances_ibfk_1
        foreign key (account_id) references accounts (id),
    constraint opening_balances_ibfk_2
        foreign key (cooperative_id) references cooperatives (id)
);

create index account_id
    on opening_balances (account_id);

create index cooperative_id
    on opening_balances (cooperative_id);

create table premises_types
(
    id   int auto_increment
        constraint `PRIMARY`
        primary key,
    name varchar(64) not null
);

create table charges_elements
(
    id                     int auto_increment
        constraint `PRIMARY`
        primary key,
    cooperative_id         int        not null,
    start_date             date       null,
    end_date               date       null,
    charge_type_id         int        not null,
    premises_type_id       int        null,
    area_type_id           int        null,
    multiply_people_number tinyint(1) null,
    multiply_consumption   tinyint(1) null,
    constraint charges_elements_ibfk_1
        foreign key (charge_type_id) references charges_types (id),
    constraint charges_elements_ibfk_2
        foreign key (cooperative_id) references cooperatives (id),
    constraint charges_elements_ibfk_3
        foreign key (premises_type_id) references premises_types (id),
    constraint charges_elements_ibfk_4
        foreign key (area_type_id) references areas_types (id)
);

create index area_type_id
    on charges_elements (area_type_id);

create index charge_type_id
    on charges_elements (charge_type_id);

create index cooperative_id
    on charges_elements (cooperative_id);

create index premises_type_id
    on charges_elements (premises_type_id);

create table premises
(
    id               int auto_increment
        constraint `PRIMARY`
        primary key,
    building_id      int        not null,
    premises_type_id int        not null,
    premises_number  varchar(8) not null,
    constraint premises_ibfk_1
        foreign key (building_id) references buildings (id),
    constraint premises_ibfk_2
        foreign key (premises_type_id) references premises_types (id)
);

create table advances
(
    id               int auto_increment
        constraint `PRIMARY`
        primary key,
    realization_date date              not null,
    charge_type_id   int               not null,
    landlord_id      int               not null,
    month            tinyint unsigned  not null,
    year             smallint unsigned not null,
    premises_id      int               not null,
    amount           double            not null,
    constraint advances_ibfk_1
        foreign key (premises_id) references premises (id),
    constraint advances_ibfk_2
        foreign key (landlord_id) references landlords (id),
    constraint advances_ibfk_3
        foreign key (charge_type_id) references charges_types (id)
);

create index advance_type_id
    on advances (charge_type_id);

create index landlord_id
    on advances (landlord_id);

create index premises_id
    on advances (premises_id);

create table areas
(
    id           int auto_increment
        constraint `PRIMARY`
        primary key,
    premises_id  int    not null,
    start_date   date   null,
    end_date     date   null,
    value        double not null,
    area_type_id int    not null,
    constraint areas_ibfk_1
        foreign key (premises_id) references premises (id),
    constraint areas_ibfk_2
        foreign key (area_type_id) references areas_types (id)
);

create index premises_id
    on areas (premises_id);

create table consumptions
(
    id             int auto_increment
        constraint `PRIMARY`
        primary key,
    charge_type_id int    not null,
    premises_id    int    not null,
    start_date     date   null,
    end_date       date   null,
    value          double not null,
    constraint consumptions_ibfk_1
        foreign key (premises_id) references premises (id),
    constraint consumptions_ibfk_2
        foreign key (charge_type_id) references charges_types (id)
);

create index advance_type_id
    on consumptions (charge_type_id);

create index premises_id
    on consumptions (premises_id);

create table landlords_payments
(
    id               int auto_increment
        constraint `PRIMARY`
        primary key,
    realization_date date   not null,
    premises_id      int    not null,
    landlord_id      int    not null,
    value            double not null,
    constraint landlords_payments_ibfk_1
        foreign key (premises_id) references premises (id),
    constraint landlords_payments_ibfk_2
        foreign key (landlord_id) references landlords (id)
);

create index landlord_id
    on landlords_payments (landlord_id);

create table people_numbers
(
    id          int auto_increment
        constraint `PRIMARY`
        primary key,
    premises_id int  not null,
    start_date  date null,
    end_date    date null,
    number      int  not null,
    constraint people_numbers_ibfk_1
        foreign key (premises_id) references premises (id)
);

create index premises_id
    on people_numbers (premises_id);

create index building_id
    on premises (building_id);

create index premises_type_id
    on premises (premises_type_id);

create table premises_landlords
(
    id          int auto_increment
        constraint `PRIMARY`
        primary key,
    premises_id int  not null,
    landlord_id int  not null,
    start_date  date null,
    end_date    date null,
    constraint premises_landlords_ibfk_1
        foreign key (premises_id) references premises (id),
    constraint premises_landlords_ibfk_2
        foreign key (landlord_id) references landlords (id)
);

create index landlord_id
    on premises_landlords (landlord_id);

create index premises_id
    on premises_landlords (premises_id);

create table rates
(
    id             int auto_increment
        constraint `PRIMARY`
        primary key,
    start_date     date   null,
    end_date       date   null,
    cooperative_id int    null,
    building_id    int    null,
    premises_id    int    null,
    charge_type_id int    not null,
    value          double not null,
    constraint rates_ibfk_1
        foreign key (cooperative_id) references cooperatives (id),
    constraint rates_ibfk_2
        foreign key (building_id) references buildings (id),
    constraint rates_ibfk_3
        foreign key (premises_id) references premises (id),
    constraint rates_ibfk_4
        foreign key (charge_type_id) references charges_types (id)
);

create index advance_type_id
    on rates (charge_type_id);

create index building_id
    on rates (building_id);

create index cooperative_id
    on rates (cooperative_id);

create index premises_id
    on rates (premises_id);

create table users
(
    id       int auto_increment
        constraint `PRIMARY`
        primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    constraint login
        unique (username)
);


