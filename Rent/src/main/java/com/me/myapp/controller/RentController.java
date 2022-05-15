package com.me.myapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.myapp.dao.RentDAO;
import com.me.myapp.dao.OwnerDAO;
import com.me.myapp.dao.UserDAO;
import com.me.myapp.dao.CategoryDAO;
import com.me.myapp.pojo.Rent;
import com.me.myapp.pojo.Owner;
import com.me.myapp.pojo.User;
import com.me.myapp.pojo.Category;

import javassist.Loader.Simple;

@Controller
public class RentController {

private static final Logger logger = LoggerFactory.getLogger(RentController.class);

	@RequestMapping(value = "/createitems", method = RequestMethod.POST)
	public ModelAndView register(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView mv = null;
		
		String itmid = request.getParameter("itemId");
		
		int vid = Integer.parseInt(itmid); 
		CategoryDAO ed = new CategoryDAO();
		Category venue = ed.getCategorybyId(vid);
		
		mv = new ModelAndView("createitems", "venue",venue);
		
		return mv;
	}


	@RequestMapping(value = "/additemdetails", method = RequestMethod.POST)
	public ModelAndView addevent(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		HttpSession session = request.getSession();
		User usersess = (User)session.getAttribute("sessionUser");
		
		
		
		
		ModelAndView mv = null;
		Date eventdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat tf = new SimpleDateFormat("yyyy-mm-dd");
		String eventname = request.getParameter("eventname");
		String details = request.getParameter("details");
		System.out.println("in rent contoller"+request.getParameter("capacity"));
		int capacity = Integer.parseInt(request.getParameter("capacity"));
		
//		String capacity = request.getParameter("capacity");
		
		try {
			eventdate = sdf.parse(request.getParameter("eventdate"));
			System.out.println("Date from request"+eventdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        java.sql.Date sqlDate = new java.sql.Date(eventdate.getTime());
        System.out.println("Date after conversion"+sqlDate);
		String venueid = request.getParameter("venueid");
		int vid = Integer.parseInt(venueid);
		
		CategoryDAO vd = new CategoryDAO();
		Category venue = vd.getCategorybyId(vid);
		
		
		Set<Date> bookeddates = venue.getBookedDates();
		boolean flag = true;
		for(Date d:bookeddates) {
			
			String dt = tf.format(d);
			System.out.println("BookedDate "+d+" Event Date "+eventdate);
			if(dt.equals(tf.format(eventdate))) {
				flag = false;
				break;
			}
		}
		
		
		if(flag) {
		RentDAO ed = new RentDAO();
		Rent event = null;
		List<Rent> events = new ArrayList<Rent>();
		try {
			event = ed.addEvent(eventname,details, eventdate, capacity, venue, usersess);
			usersess.addCreatedEvent(event);
			
			venue.addBookedDate(eventdate);
			vd.update(venue);
			events = ed.getEvents(usersess);
			mv = new ModelAndView("usercreateditems", "events",events);
			model.addAttribute("success", "Item Created Successfully");
			//EmailController.sendEmail("theadda2020@gmail.com", "vaibhavdhoke1@gmail.com", "Event Added Successfully by "
//			+usersess.getName()+" at "+venue.getLocation()+"and Event Name is "+eventname+" , Event Date is "+event.getEvent_date(), "Event Added");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mv = new ModelAndView("usercreateditems", "events",events);
			model.addAttribute("msg", "Item can not be added at this time");
		}
		}else {
		
		
			mv = new ModelAndView("createitem","booked", "Item is not available at this date");
			//model.addAttribute("Message", "Event Created Successfully");
		}
		return mv;
	}
	
	@RequestMapping(value = "/usercreateditems", method = RequestMethod.GET)
	public ModelAndView usercreatedevents(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView mv = null;
		
		
		
		HttpSession session = request.getSession();
		User usersess = (User)session.getAttribute("sessionUser");
		RentDAO ed = new RentDAO();
		
		List<Rent> events = new ArrayList<Rent>();
		events = ed.getEvents(usersess);
		mv = new ModelAndView("usercreateditems", "events",events);
		
		return mv;
	}
	
	
	@RequestMapping(value = "/viewallitems", method = RequestMethod.GET)
	public ModelAndView viewallevents(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView mv = null;
		
		HttpSession session = request.getSession();
		User usersess = (User)session.getAttribute("sessionUser");
		RentDAO ed = new RentDAO();
		
		List<Rent> events = new ArrayList<Rent>();
		events = ed.getAllEvents();
		
		List<Rent> sevents = new ArrayList<Rent>(); 
		
		for(Rent e:events) {
			System.out.println("status "+e.getStatus());
			if(e.getStatus().equalsIgnoreCase("Scheduled")) {
				sevents.add(e);
			}
		}
		mv = new ModelAndView("allitems", "events",sevents);
		
		return mv;
	}
	
	
	@RequestMapping(value = "/registeritem", method = RequestMethod.POST)
	public ModelAndView registerforevent(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView mv = null;
		
		HttpSession session = request.getSession();
		User usersess = (User)session.getAttribute("sessionUser");
		
		
		
		int eventid = Integer.parseInt(request.getParameter("eventid"));
		UserDAO ud = new UserDAO();
		RentDAO ed = new RentDAO();
		
		
		
		
		Rent event = ed.getEventbyid(eventid);
		Set<Rent> events = new HashSet<Rent>();
		events = usersess.getEvents_registered();
		System.out.println("Value of If statement ="+events.contains(event));
		boolean flag = false;
		for(Rent e:events) {
			if(e.getId()==eventid) {
				flag = true;
			}
		}
		if(flag) {
			mv = new ModelAndView("registereditems", "events",events);
			model.addAttribute("registered", "Item is already registered");
		}else {
			event.setSeats_available(event.getSeats_available()-1);
			
			usersess.addRegisteredEvent(event);
			event.addUser(usersess);
		
		try {
			ud.update(usersess);
			ed.updateEvent(event);
			System.out.println("Size of registered set"+usersess.getEvents_registered().size());
			
			events = usersess.getEvents_registered();
		  	mv = new ModelAndView("registereditems", "events",events);
		  	model.addAttribute("registered", "Item registered successfully");
//		  	EmailController.sendEmail("theadda2020@gmail.com", "vaibhavdhoke1@gmail.com", "Event Registered Successfully by "
//		  	+usersess.getName()+" for "+event.getEventname()+" at "+event.getLocation(), "Event Registered");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			events = usersess.getEvents_registered();
			e.printStackTrace();
			mv = new ModelAndView("registereditems", "events",events);
			model.addAttribute("registered", "Item is already registered");
		}
		
		}
//		ed.updateEvent(event);
		
		
		
		return mv;
	}
	
	@RequestMapping(value = "/userregistereditems", method = RequestMethod.GET)
	public ModelAndView userregisteredevents(Locale locale, Model model,HttpServletRequest request,RentDAO event) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView mv = null;
		
		HttpSession session = request.getSession();
		User usersess = (User)session.getAttribute("sessionUser");
		
		
		//Set<Event> events = new HashSet<Event>();
		List<Rent> v = new ArrayList<Rent>();
		//events = usersess.getEvents_registered();
		v =event.getEvents(usersess);
		//List<Event> events = ed.getEvents(usersess);
		
		
		mv = new ModelAndView("registereditems", "events",v);
		
		return mv;
	}
	
	
	
	@RequestMapping(value = "/updateitem", method = RequestMethod.POST)
	public ModelAndView updateevent(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView mv = null;
		
		int eid = Integer.parseInt(request.getParameter("eventid"));
		
		RentDAO ed = new RentDAO();
		Rent event = ed.getEventbyid(eid);
		
		SimpleDateFormat tf = new SimpleDateFormat("MM/dd/yyyy");
		String d = tf.format(event.getEvent_date());
		mv = new ModelAndView("updateitem", "event",event);
		model.addAttribute("evdate", d);
		
		return mv;
	}
	
	
	@RequestMapping(value = "/updateitemdetails", method = RequestMethod.POST )
	public ModelAndView updateeventdetails(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView mv = null;
		
		HttpSession session = request.getSession();
		User usersess = (User)session.getAttribute("sessionUser");
		String action = request.getParameter("submit");
		int eid = Integer.parseInt(request.getParameter("eventid"));
		RentDAO ed = new RentDAO();
		CategoryDAO vd = new CategoryDAO();
		
		if(action.equalsIgnoreCase("Update")) {
			
			try {
			System.out.println("Before Param");
			String ename = request.getParameter("eventname");
			String details = request.getParameter("details");
			int capacity = Integer.parseInt(request.getParameter("capacity"));
			System.out.println("After capacity");
			String status = request.getParameter("status");
			System.out.println("After status");
			
			
			System.out.println("After Param");
			Date eventdate = null;
			Date oldeventdate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			eventdate = sdf.parse(request.getParameter("eventdate"));
			oldeventdate = sdf.parse(request.getParameter("oldeventdate"));
			java.sql.Date sqlDate = new java.sql.Date(eventdate.getTime());
			System.out.println("After Date");
			
			Rent event = ed.getEventbyid(eid);
			event.setEventname(ename);
			event.setDetails(details);
			event.setCapacity(capacity);
			event.setEvent_date(eventdate);
			event.setStatus(status);
			Category venue = vd.getCategorybyId(event.getVenue().getId());
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(oldeventdate); 
			c.add(Calendar.DATE, 1);
			oldeventdate = c.getTime();
			
			if(venue.getBookedDates().contains(eventdate)) {
				mv = new ModelAndView("updateitem", "event",event);
				model.addAttribute("booked", "Item is not available at this date");
				
			}else {
					venue.removeBookedDate(oldeventdate);
					venue.addBookedDate(eventdate);
					System.out.println("Before Update");
					ed.updateEvent(event);
					System.out.println("After Event Update");
					vd.update(venue);
					System.out.println("After Venue Update");
					
					mv = new ModelAndView("updateitem", "event",event);
					model.addAttribute("msg", "Event Updated Successfully");
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mv = new ModelAndView("usercreateditems", "msg", "Item can not be updated at this time");
			}
			
		}else if(action.equalsIgnoreCase("Delete")) {
			
			try {
			Rent event = ed.getEventbyid(eid);
			Category venue = vd.getCategorybyId(event.getVenue().getId());
			Date oldeventdate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			
			oldeventdate = sdf.parse(request.getParameter("oldeventdate"));
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(oldeventdate); 
			c.add(Calendar.DATE, 1);
			oldeventdate = c.getTime();
			System.out.println("Old DAte "+oldeventdate);
			venue.removeBookedDate(oldeventdate);
			vd.update(venue);
			Set<User> users = event.getUsers();
			
//			for(User u:users) {
//				u.removeRegisteredEvent(event);
//			}
//			
//			usersess.removeCreatedEvent(event);
				
				ed.deleteEvent(event);
				mv = new ModelAndView("usercreateditems");
				model.addAttribute("msg", "Item deleted successfully");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mv = new ModelAndView("usercreateditems", "msg", "Item can not be deleted at this time");
			}
			
		}
		return mv;
	}
	
	
	
	
	@RequestMapping(value = "/deregister", method = RequestMethod.POST)
	public ModelAndView deregister(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView mv = null;
		
		HttpSession session = request.getSession();
		User usersess = (User)session.getAttribute("sessionUser");
		
		
		int eventid = Integer.parseInt(request.getParameter("eventid"));
		UserDAO ud = new UserDAO();
		RentDAO ed = new RentDAO();
		
		Rent event = ed.getEventbyid(eventid);
		
		event.setSeats_available(event.getSeats_available()+1);
		usersess.removeRegisteredEvent(event);
		//event.removeUser(usersess);
		
		
		try {
//			ed.updateEvent(event);
			ud.update(usersess);
			
			System.out.println("After all update");
			mv = new ModelAndView("registereditems","events",usersess.getEvents_registered());
			model.addAttribute("msg", "Item Successfully Deregistered");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("inside catch");
			mv = new ModelAndView("registereditems","events",usersess.getEvents_registered());
			model.addAttribute("msg", "Something went wrong");
		}
		
		
		
		return mv;
	}
	
	
}
