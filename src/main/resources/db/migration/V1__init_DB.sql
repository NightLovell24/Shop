--USERS
create sequence user_seq start with 1 increment by 1;

create table users
(
    id        bigint  not null,
    archive   boolean not null,
    email     varchar(255),
    name      varchar(255),
    password  varchar(255),
    phone     varchar(255),
    role      varchar(255),
    bucket_id bigint,
    primary key (id)
);

--PRODUCTS
create sequence product_seq start with 1 increment by 1;
create table products
(
    id    bigint not null,
    price numeric(38, 2),
    title varchar(255),
    primary key (id)
);

--ORDERS
create sequence order_seq start with 1 increment by 1;
create table orders
(
    id      bigint not null,
    address varchar(255),
    created timestamp(6),
    status  varchar(255),
    sum     numeric(38, 2),
    updated timestamp(6),
    user_id bigint,
    primary key (id)
);

alter table if exists orders
    add constraint orders_fk_users foreign key (user_id) references users;

--ORDER_DETAILS
create sequence order_details_seq start with 1 increment by 1;
create table order_details
(
    id         bigint not null,
    amount     numeric(38, 2),
    price      numeric(38, 2),
    order_id   bigint,
    product_id bigint,
    primary key (id)
);

--CATEGORY
create sequence category_seq start with 1 increment by 1;
create table categories
(
    id    bigint not null,
    title varchar(255),
    primary key (id)
);

--BUCKETS
create sequence bucket_seq start with 1 increment by 1;

create table buckets
(
    id      bigint not null,
    user_id bigint,
    primary key (id)
);


--BUCKETS AND PRODUCTS
create table buckets_products
(
    bucket_id  bigint not null,
    product_id bigint not null
);

--ORDERS AND DETAILS
create table orders_details
(
    order_id   bigint not null,
    details_id bigint not null
);

--PRODUCTS AND CATEGORIES
create table products_categories
(
    product_id  bigint not null,
    category_id bigint not null
);

alter table if exists products_categories
    add constraint products_categories_fk_products foreign key (product_id) references products;

alter table if exists products_categories
    add constraint products_categories_fk_categories foreign key (category_id) references categories;

alter table if exists orders_details
    add constraint orders_details_unique unique (details_id);

alter table if exists orders_details
    add constraint orders_details_fk_orders foreign key (order_id) references orders;

alter table if exists orders_details
    add constraint orders_details_fk_details foreign key (details_id) references order_details;

alter table if exists buckets_products
    add constraint buckets_products_fk_products foreign key (product_id) references products;

alter table if exists buckets_products
    add constraint buckets_products_fk_buckets foreign key (bucket_id) references buckets;

-- LINK BETWEEN BUCKETS AND USERS
alter table if exists buckets
    add constraint buckets_fk_users foreign key (user_id) references users;

alter table if exists order_details
    add constraint order_details_fk_orders foreign key (order_id) references orders;
alter table if exists order_details
    add constraint order_details_fk_products foreign key (product_id) references products;

--LINK BETWEEN USERS AND BUCKETS
alter table if exists users
    add constraint users_fk_buckets foreign key (bucket_id) references buckets;


