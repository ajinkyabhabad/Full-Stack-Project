package com.me.myapp.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.me.myapp.controller.MainController;
import com.me.myapp.pojo.Rent;
import com.me.myapp.pojo.Owner;
import com.me.myapp.pojo.User;
import com.me.myapp.pojo.Category;

public class RentDAO extends DAO{
	private static final Logger logger = LoggerFactory.getLogger(RentDAO.class);
	
	public Rent addEvent(String rntname,String details, Date event_date,int capacity,Category venue,User organizer) throws Exception{
		Rent ev=null;
	    try {
	    	ev = new Rent();
	    	ev.setEventname(rntname);
	        ev.setLocation(venue.getLocation());
	        ev.setCapacity(capacity);
	        ev.setDetails(details);
	        ev.setEvent_date(event_date);
	        ev.setOrganizer(organizer);
	        ev.setSeats_available(capacity);
	        ev.setVenue(venue);
	        ev.setStatus("Scheduled");
	        begin();
	        getSession().save(ev);
	        commit();
	    } catch (HibernateException e) {
	        e.printStackTrace();
	        rollback();
	        throw e;
	    } finally {
	        close();
	    }
	    return ev;
	}
	
	
	public List<Rent> getEvents(User user){
		List<Rent> v = new ArrayList<Rent>();
        try {
            begin();
            //Query q = getSession().createQuery("from Message where userName= :username");
            Query q = getSession().createQuery("from Rent where organizer=:user");
            
            q.setEntity("user", user);
            v = (List<Rent>) q.list();
            logger.info("Vaibhav"+v.size());
            System.out.println("Size in sout"+v.size());
            commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        return v;		
	}
	
	
	public List<Rent> getAllEvents(){
		List<Rent> v = new ArrayList<Rent>();
        try {
            begin();
            //Query q = getSession().createQuery("from Message where userName= :username");
            Query q = getSession().createQuery("from Rent");
            
            v = (List<Rent>) q.list();
            logger.info("Vaibhav"+v.size());
            System.out.println("Size in sout"+v.size());
            commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        return v;		
	}
	
	
	
	public Rent getEventbyid(int eid){
		Rent v = new Rent();
        try {
            begin();
            //Query q = getSession().createQuery("from Message where userName= :username");
            Query q = getSession().createQuery("from Rent where id=:eid");
            
            q.setInteger("eid", eid);
            v = (Rent) q.uniqueResult();
            
            commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        return v;		
	}
	
	
	public int updateEvent(Rent event) throws Exception{
    	int ret = 0;
    	 try {
             begin();
            
//             Set<Venue> venues = getVenues(owner.getEmail());
//             logger.info("Size Vaibhav"+venues.size());
// 			
// 			venues.add(v);	
// 			owner.setVenues(venues);
//             v.setOwner(owner);
             
             getSession().update(event);
             System.out.println("updated from here");
             ret=1;
             commit();
         } catch (HibernateException e) {
        	 e.printStackTrace();
	         rollback();
	         throw e;
         }finally {
	            close();
	        }
    	 return ret;
    }
	
	public int deleteEvent(Rent event) throws Exception{
    	int ret = 0;
    	 try {
             begin();
            
//             Set<Venue> venues = getVenues(owner.getEmail());
//             logger.info("Size Vaibhav"+venues.size());
// 			
// 			venues.add(v);	
// 			owner.setVenues(venues);
//             v.setOwner(owner);
             
             getSession().delete(event);
             System.out.println("deleted from here");
             ret=1;
             commit();
         } catch (HibernateException e) {
        	 e.printStackTrace();
	         rollback();
	         throw e;
         }finally {
	            close();
	        }
    	 return ret;
    }
	
}
