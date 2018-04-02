//package com.github.soonboylena.myflow.persistentneo4j.dao;
//
//import org.neo4j.driver.v1.Driver;
//import org.neo4j.driver.v1.Session;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//
//@Component
//public class TransactDao implements AutoCloseable {
//
//    @Autowired
//    private Driver driver;
//
//    public void execute(Collection<CQLTemplate> templates) {
//
//        CusTransactionWork work = new CusTransactionWork(templates);
//        try (Session session = driver.session()) {
//            Object o = session.writeTransaction(work);
//        }
//    }
//
//    @Override
//    public void close() throws Exception {
//        driver.close();
//    }
//}
