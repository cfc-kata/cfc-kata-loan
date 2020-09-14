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

------------------ Loan ---------------------
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
    version int not null,
    primary key (id)
);

create table if not exists loan (
    id varchar(20) not null,
    created_at datetime not null,
    contract_id varchar(20) not null,
    apply_amount decimal(12, 2) not null,
    total_month int not null,
    interest_rate decimal(12, 2) not null,
    withdraw_bank_account varchar(20) not null,
    repayment_bank_account varchar(20) not null,
    repayment_type varchar(20) not null,
    version int not null,
    primary key (id)
);

create table if not exists repayment_plan (
    loan_id varchar(20) not null,
    plan_no int not null,
    payable_date datetime not null,
    payable_amount decimal(12, 2) not null,
    payable_interest decimal(12, 2) not null,
    payable_capital decimal(12, 2) not null,
    status varchar(40) not null,
    primary key(loan_id, plan_no)
);
