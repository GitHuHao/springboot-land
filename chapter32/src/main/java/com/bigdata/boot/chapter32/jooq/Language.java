/*
 * This file is generated by jOOQ.
 */
package com.bigdata.boot.chapter32.jooq;


import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Language extends TableImpl<Record> {

    private static final long serialVersionUID = -1529482790;

    /**
     * The reference instance of <code>PUBLIC.LANGUAGE</code>
     */
    public static final Language LANGUAGE = new Language();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>PUBLIC.LANGUAGE.ID</code>.
     */
    public final TableField<Record, Integer> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.LANGUAGE.CD</code>.
     */
    public final TableField<Record, String> CD = createField(DSL.name("CD"), org.jooq.impl.SQLDataType.CHAR(2).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.LANGUAGE.DESCRIPTION</code>.
     */
    public final TableField<Record, String> DESCRIPTION = createField(DSL.name("DESCRIPTION"), org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * Create a <code>PUBLIC.LANGUAGE</code> table reference
     */
    public Language() {
        this(DSL.name("LANGUAGE"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.LANGUAGE</code> table reference
     */
    public Language(String alias) {
        this(DSL.name(alias), LANGUAGE);
    }

    /**
     * Create an aliased <code>PUBLIC.LANGUAGE</code> table reference
     */
    public Language(Name alias) {
        this(alias, LANGUAGE);
    }

    private Language(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Language(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Language(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, LANGUAGE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.CONSTRAINT_C;
    }

    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.CONSTRAINT_C);
    }

    @Override
    public Language as(String alias) {
        return new Language(DSL.name(alias), this);
    }

    @Override
    public Language as(Name alias) {
        return new Language(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Language rename(String name) {
        return new Language(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Language rename(Name name) {
        return new Language(name, null);
    }
}