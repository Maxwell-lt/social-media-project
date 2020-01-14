drop database if exists socialmediaproject;
create database socialmediaproject;
use socialmediaproject;

create table users (
    `id` int not null primary key auto_increment,
    `username` varchar(50) unique not null,
    `password` varchar(60) not null, # BCrypt password field
    `email` varchar(254) not null,
    `adminPermissions` boolean not null default false,
    `moderatorPermissions` boolean not null default false,
    `publicLikes` boolean not null default true
);

create table posts (
    `id` int not null primary key auto_increment,
    `title` varchar(100) not null,
    `image` varchar(36), # Refers to an image named with a UUID, without the file extension
    `text` text,
    `user` int not null,
    foreign key (id) references users(id)
);

create table comments (
    `id` int not null primary key auto_increment,
    `text` varchar(5000) not null,
    `user` int not null,
    `post` int not null,
    foreign key (user) references users(id),
    foreign key (post) references posts(id)
);

# Likes purchased by the user
create table purchasedlikes (
    `id` int not null primary key auto_increment,
    `pricePaid` decimal(13, 4) not null, # GAAP compatible money field
    `likesBought` int not null,
    `user` int not null,
    foreign key (user) references users(id)
);

# Likes granted by making a popular post
create table grantedlikes (
    `id` int not null primary key auto_increment,
    `likesGranted` int not null,
    `post` int not null,
    `user` int not null,
    foreign key (post) references posts(id),
    foreign key (user) references users(id)
);

# Likes spent on posts
create table spentlikes (
    `id` int not null primary key auto_increment,
    `likesUsed` int not null,
    `post` int not null,
    `user` int not null,
    foreign key (post) references posts(id),
    foreign key (user) references users(id)
);