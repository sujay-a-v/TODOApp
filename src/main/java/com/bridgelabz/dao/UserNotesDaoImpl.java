package com.bridgelabz.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.model.Notes;
import com.bridgelabz.model.User;

public class UserNotesDaoImpl implements UserNotesDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addUserNotes(Notes notes) {
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try
		{
			session.save(notes);
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
	public void dalateUserNotes(int id) {
		
		Session session=sessionFactory.openSession();
		Transaction transaction=null;
		try{
			transaction=session.beginTransaction();
			String sql="delete Notes where id =:id";
			Query query=session.createQuery(sql);
			query.setParameter("id", id);
			query.executeUpdate();
			transaction.commit();
			session.close();
		}
		catch (Exception e) {
			if(transaction!=null)
				transaction.rollback();
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void modifiedNotes(int id, Notes notes) {
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try
		{
			session.update(notes);
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
	public List<Notes> fetchAllNotes(User user) {
		System.out.println("inside Retrieve All");
		Session session=sessionFactory.openSession();
		try
		{
			Criteria criteria=session.createCriteria(Notes.class);
			criteria.add(Restrictions.eq("user", user));
			List<Notes> list=criteria.list();
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Notes fetchById(int id) {
		Session session=sessionFactory.openSession();
		try
		{
			Criteria criteria=session.createCriteria(Notes.class);
			Criterion criterion=Restrictions.eq("id", id);
			criteria.add(criterion);
			Notes notes=(Notes) criteria.uniqueResult();
			return notes;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
