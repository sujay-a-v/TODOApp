package com.bridgelabz.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.bridgelabz.model.User;


@SuppressWarnings("deprecation")
public class UserDaoImplement implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	//***** Insert User details into database *****/
	@Override
	public void saveUser(User user) {	
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try
		{
			session.save(user);
			transaction.commit();
			session.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			session.close();
		}
	}

	@Override
	public User checkUser(String email, String password) {
		try{
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(User.class);
		Criterion email1=Restrictions.eq("userEmail", email);
		criteria.add(email1);
		User user=(User) criteria.uniqueResult();
		session.close();
			if(BCrypt.checkpw(password, user.getUserPassword()))
			{
				return user;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean isUserExit(String email) {
		Session session=sessionFactory.openSession();
		try
		{
			Criteria criteria=session.createCriteria(User.class);
			List list=criteria.list();
			for(Iterator itr= list.iterator(); itr.hasNext();)
			{
				User user=(User) itr.next();
				String email1=user.getUserEmail();
				if(email.equals(email1))
				{
					return true;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public User retrieveById(int id) {
		Session session=sessionFactory.openSession();
		try
		{
			Criteria criteria=session.createCriteria(User.class);
			Criterion id1=Restrictions.eq("id", id);
			criteria.add(id1);
			User user=(User) criteria.uniqueResult();
			session.close();
			return user;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void activateUser(int id, User user) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try
		{
			session.update(user);
			transaction.commit();
			session.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			session.close();
		}
		
	}

	@Override
	public User getByEmail(String email) {
		Session session=sessionFactory.openSession();
		try
		{
			Criteria criteria=session.createCriteria(User.class);
			Criterion email1=Restrictions.eq("userEmail", email);
			criteria.add(email1);
			User user=(User) criteria.uniqueResult();
			session.close();
			return user;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String passwordReset(User user) {
		Session session=sessionFactory.openSession();
		Transaction transaction=null;
		try
		{
			transaction=session.beginTransaction();
			String hql="update User set userPassword=:userPassword where id=:id";
			Query query=session.createQuery(hql);
			query.setParameter("userPassword",user.getUserPassword());
			query.setParameter("id", user.getId());
			query.executeUpdate();
			//session.update(user);
			transaction.commit();
			session.close();
			return "Password set";
		}
		catch (Exception e) {
			if(transaction!=null)
				transaction.rollback();
			e.printStackTrace();
		}
		return "Password not set";
	}
}
