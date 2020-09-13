create table if not exists sale_order (
    id varchar(20) not null,
    create_time datetime not null,
    customer_id varchar(20) not null,
    total_price decimal(12, 2) not null default 0,
    total_payment decimal(12, 2) not null default 0,
    status tinyint not null default 0,
    version int,
    primary key (id)
);

create table if not exists product (
    id varchar(20) not null,
    name varchar(200) not null,
    price decimal(12,2) not null default 0,
    primary key (id)
);

create table if not exists order_item (
    id bigint auto_increment,
    order_id varchar(20) not null,
    prod_id varchar(20) not null,
    amount decimal(12,2) not null default 0,
    sub_total decimal(12,2) not null default 0,
    primary key (id)
);

create table if not exists customer (
    id varchar(20) not null,
    name varchar(40) not null,
    primary key (id)
);


--LOAN
--        this.id = id;
--         this.customer = customer;
--         this.interestRate = interestRate;
--         this.repaymentType = repaymentType;
--         this.maturityDate = maturityDate;
--         this.commitment = commitment;
--         this.createdAt = createdAt;
--         this.status = status;

create table if not exists contract (
    id varchar(20) not null,
    create_time datetime not null,
    customer_id varchar(20) not null,
    customer_name varchar(100) not null,
    customer_id_number varchar(18) not null,
    customer_phone varchar(40) not null,
    interest_rate decimal(12, 2) not null default 0,
    commitment decimal(12, 2) not null default 0,
    repayment_type varchar(20) not null,
    maturity_date datetime not null,
    status varchar(40) not null,
    version int,
    primary key (id)
);
