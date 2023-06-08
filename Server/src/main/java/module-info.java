module kursachNumbaThree.Server.main {
    exports kur3.server;
    exports kur3.server.entity;
    exports kur3.server.service to spring.beans, kursachNumbaThree.Client.main;
    exports kur3.server.controller to spring.beans, spring.web;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires org.jsoup;
    requires spring.beans;
    requires spring.context;
    requires spring.web;
    requires spring.core;
    requires org.hibernate.orm.core;
    requires lombok;


    opens kur3.server;
    opens kur3.server.entity;
    opens kur3.server.service;
    exports kur3.server.repository;
}