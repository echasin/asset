package com.innvo.json;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author ali
 */
public class StringJsonUserType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return String.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {

        if( x== null){

            return y== null;
        }

        return x.equals( y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {

        return x.hashCode();
    }
   

    @Override
    public Object deepCopy(Object value) throws HibernateException {

        return value;
    }

 
    @Override
    public boolean isMutable() {
        return true;
    }


    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (String)this.deepCopy( value);
    }

  
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy( cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		
	}
}