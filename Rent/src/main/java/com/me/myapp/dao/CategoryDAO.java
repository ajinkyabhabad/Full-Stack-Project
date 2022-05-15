package com.me.myapp.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.me.myapp.controller.MainController;
import com.me.myapp.pojo.Rent;
import com.me.myapp.pojo.Owner;
import com.me.myapp.pojo.Category;


public class CategoryDAO extends DAO{

	private static final Logger logger = LoggerFactory.getLogger(CategoryDAO.class);
	public Category addCategory(String cttyp, String transportation, String availability,Owner owner) throws Exception{
		Category v=null;
	    try {
	    	v = new Category();
	        v.setLocation(cttyp);
	        v.setOwner(owner);
	        v.setRooms(availability);
	        v.setTransportation(transportation);
	        begin();
	        getSession().save(v);
	        commit();
	       
	    } catch (HibernateException e) {
	        e.printStackTrace();
	        rollback();
	        throw e;
	    } finally {
	        close();
	    }
	    return v;
	}
	
	
	
	
	public List<Category> getCategory(){
		List<Category> v = new ArrayList<Category>();
        try {
            begin();
            //Query q = getSession().createQuery("from Message where userName= :username");
            Query q = getSession().createQuery("from Category");
            
            v = (List<Category>) q.list();
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
	
	
	
	
	public Category getCategorybyId(int categoryID){
		Category v = null;
        try {
            begin();
            //Query q = getSession().createQuery("from Message where userName= :username");
            Query q = getSession().createQuery("from Category where id= :categoryID");
            
            q.setInteger("categoryID",categoryID);
            v = (Category) q.uniqueResult();
            commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        return v;		
	}
	 
	
	
	public Category update(Category v) throws Exception{
	    try {
	    	
	        begin();
	        getSession().update(v);
	        commit();
	       
	    } catch (HibernateException e) {
	        e.printStackTrace();
	        rollback();
	        throw e;
	    } finally {
	        close();
	    }
	    return v;
	}
	
	public int deleteCategory(Category ctgr) throws Exception{
    	int ret = 0;
    	 try {
             begin();
            
//             Set<Venue> venues = getVenues(owner.getEmail());
//             logger.info("Size Vaibhav"+venues.size());
// 			
// 			venues.add(v);	
// 			owner.setVenues(venues);
//             v.setOwner(owner);
             
             getSession().delete(ctgr);
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
	
	
	public List<Category> getCategorybyOwner(Owner owner){
		List<Category> v = null;
		
        try {
            begin();
            //Query q = getSession().createQuery("from Message where userName= :username");
            Query q = getSession().createQuery("from Category where owner= :own");
            
            q.setEntity("own",owner);
            v = (List<Category>) q.list();
            commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        return v;		
	}
	
}
