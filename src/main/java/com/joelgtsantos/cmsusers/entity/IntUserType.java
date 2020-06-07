/**
 * 
 */
package com.joelgtsantos.cmsusers.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGInterval;

/**
 * @author Joel Santos
 *
 * cms-users-admin
 * 2018
 */
public class IntUserType implements UserType {
    private static final int[] SQL_TYPES = { Types.OTHER };

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return Integer.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }
    

	@Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor arg2, Object owner)
            throws HibernateException, SQLException {
        String interval = rs.getString(names[0]);
        if (rs.wasNull() || interval == null) {
            return null;
        }
        PGInterval pgInterval = new PGInterval(interval);
        Date epoch = new Date(0l);
        pgInterval.add(epoch);
        return Integer.valueOf((int)epoch.getTime() / 1000);
    }

    public static String getInterval(int value){
        return new PGInterval(0, 0, 0, 0, 0, value).getValue();
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor arg3)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            //this http://postgresql.1045698.n5.nabble.com/Inserting-Information-in-PostgreSQL-interval-td2175203.html#a2175205
            st.setObject(index, getInterval(((Integer) value).intValue()), Types.OTHER);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }



}