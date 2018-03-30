package com.github.soonboylena.myflow.persistentneo4j.dao;

import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import java.util.Arrays;
import java.util.Collection;

import static org.neo4j.driver.v1.Values.parameters;


public class CusTransactionWork implements TransactionWork {

    private Collection<CQLTemplate> templates;

    public CusTransactionWork(CQLTemplate... templates) {
        this(Arrays.asList(templates));
    }

    public CusTransactionWork(Collection<CQLTemplate> templates) {
        this.templates = templates;
    }

    @Override
    public Object execute(Transaction tx) {
        for (CQLTemplate cqlTemplate : templates) {
            StatementResult run = tx.run(cqlTemplate.build(), parameters(cqlTemplate.keysAndValues()));
        }
        return null;
    }
}
