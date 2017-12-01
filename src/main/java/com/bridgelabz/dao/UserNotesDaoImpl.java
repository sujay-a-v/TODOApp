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

import com.bridgelabz.model.Collaborator;
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
		}
		catch (Exception e) {
			if(transaction!=null)
				transaction.rollback();
			
			e.printStackTrace();
		}
		finally {
			session.close();
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
		finally {
			session.close();
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

	@Override
	public void addCollaborator(Collaborator collaborate) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try
		{
			session.save(collaborate);
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
	public List<User> getUserList(int noteId) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		try
		{
			Criteria criteria=session.createCriteria(Collaborator.class);
			criteria.add(Restrictions.eq("noteId", noteId));
			List<User> userList=criteria.list();
			return userList;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Notes> getCollaboratedNotes(int userId) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		try
		{
			Criteria criteria=session.createCriteria(Collaborator.class);
			criteria.add(Restrictions.eq("userId", userId));
			List<Notes> noteList=criteria.list();
			return noteList;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return null;
	}

	@Override
	public int removeUser(int userId, int noteId) {
		Session session=sessionFactory.openSession();
		Transaction transaction=null;
		try
		{
			transaction=session.beginTransaction();
			String sql="delete Collaborator where userId=:userId and noteId=:noteId";
			Query query=session.createQuery(sql);
			query.setParameter("userId", userId);
			query.setParameter("noteId", noteId);
			int status=query.executeUpdate();
			transaction.commit();
			return status;
		}
		catch (Exception e) {
			if(transaction!=null)
				transaction.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return 0;
	}

}
