package com.me.myapp.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.me.myapp.dao.CategoryDAO;
import com.me.myapp.pojo.Rent;
import com.me.myapp.pojo.Owner;
import com.me.myapp.pojo.Category;


@Controller
public class CategoryController {

	
private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
//	@RequestMapping(value = "/addvenue", method = RequestMethod.POST)
//	public ModelAndView addVenue(Locale locale, Model model,HttpServletRequest request) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		ModelAndView mv = new ModelAndView();
//		String addvenue = request.getParameter("addvenue");
//		String allvenues = request.getParameter("allvenues");
//		
//		HttpSession session = request.getSession();
//		Owner usersess = (Owner)session.getAttribute("sessionUser");
//		Set<Venue> venues = null;
//		
//		if(addvenue!=null) {
//			mv = new ModelAndView("AddVenue");
//		}else if(allvenues!=null) {
//			OwnerDAO od = new OwnerDAO();
//			try {
//				venues = usersess.getVenues();
//			}catch(Exception e) {
//				mv = new ModelAndView("OwnerDashboard", "error","Error while fecting venues");
//			}
//			logger.info("Size of Set be printing");
//			logger.info("Size of Set", venues.size());
//			mv = new ModelAndView("allvenues","venues",venues);
//		}
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate);
//		
//		return mv;
//	}
	
	
	
	@RequestMapping(value = "/addcategorydetails", method = RequestMethod.POST)
	public ModelAndView registerVenue(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		ModelAndView mv = null;
		String location = request.getParameter("cttype");
		String ctmax= request.getParameter("catmax");
		String transport = request.getParameter("transport");
		HttpSession session = request.getSession();
		Owner usersess = (Owner)session.getAttribute("sessionUser");
		CategoryDAO vd = new CategoryDAO();
		OwnerDAO od = new OwnerDAO();
		try{
			
			Category venue = vd.addCategory(location, transport, ctmax, usersess);
//			Venue venue = new Venue();
//			venue.setLocation(location);
//			venue.setTransportation(transport);
//			venue.setRooms(availability);
			
			od.update(usersess,venue);
			mv= new ModelAndView("OwnerDashboard", "error", "Category Successfully added");
//			EmailController.sendEmail("theadda2020@gmail.com", "vaibhavdhoke1@gmail.com", "Venue Added Successfully and Location is "
//					+venue.getLocation()+" and Accessibility is "+venue.getTransportation(), "Venue Added");
		}catch(Exception e) {
			mv = new ModelAndView("OwnerDashboard","error", "Error occured while adding Venue");
		}
		 
		
		return mv;
	}
	
	@RequestMapping(value = "/OwnerDashboard", method = RequestMethod.GET)
	public String ownerdashboard(Locale locale, Model model,HttpServletRequest request) {
		
		return "OwnerDashboard";
	}
	
	@RequestMapping(value = "/UserDashboard", method = RequestMethod.GET)
	public String userdashboard(Locale locale, Model model,HttpServletRequest request) {
		
		return "UserDashboard";
	}
	
	@RequestMapping(value = "/allcategory", method = RequestMethod.GET)
	public ModelAndView allvenuesforevents(Locale locale, Model model,HttpServletRequest request) {
		
		ModelAndView mv = null;
		
		
		CategoryDAO vd = new CategoryDAO();
		List<Category> v= new ArrayList<Category>();
		v = vd.getCategory();
		mv = new ModelAndView("venuesforevents", "venues",v);
		return mv;
	}
	
	@RequestMapping(value = "/ownercategories", method = RequestMethod.GET)
	public ModelAndView ownervenues(Locale locale, Model model,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Owner usersess = (Owner)session.getAttribute("sessionUser");
		List<Category> venues = new ArrayList<Category>();
		CategoryDAO vd = new CategoryDAO();
		OwnerDAO od = new OwnerDAO();
		try {
			venues = vd.getCategorybyOwner(usersess);
			System.out.println(venues.size());
			
		}catch(Exception e) {
			mv = new ModelAndView("OwnerDashboard", "error","Error while fecting venues");
		}
		logger.info("Size of Set be printing");
		logger.info("Size of Set", venues.size());
		mv = new ModelAndView("allcategory","category",venues);
		
		return mv;
	}
	
	
	@RequestMapping(value = "/addcategory", method = RequestMethod.GET)
	public ModelAndView addvenue(Locale locale, Model model,HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		mv = new ModelAndView("AddCategory");
		return mv;
	}
	
	
	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public ModelAndView updatevenue(Locale locale, Model model,HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Owner usersess = (Owner)session.getAttribute("sessionUser");
		int vid = Integer.parseInt(request.getParameter("categoryid"));
		
		CategoryDAO vd = new CategoryDAO();
		Category venue = vd.getCategorybyId(vid);
		
		
		
		mv = new ModelAndView("updatecategory","venue",venue);
		return mv;
	}
	
	@RequestMapping(value = "/updatecategorydetails", method = RequestMethod.POST)
	public ModelAndView updatevenuedetails(Locale locale, Model model,HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Owner usersess = (Owner)session.getAttribute("sessionUser");
		int vid = Integer.parseInt(request.getParameter("categoryid"));
		
		String vloc = request.getParameter("categorytype");
		String vroom = request.getParameter("vroom");
		String venuetrans = request.getParameter("venuetrans");
		CategoryDAO vd = new CategoryDAO();
		Category venue = vd.getCategorybyId(vid);
		RentDAO ed = new RentDAO();
		
		String action = request.getParameter("submit");
		if(action.equalsIgnoreCase("Update")) {
			venue.setLocation(vloc);
			venue.setRooms(vroom);
			venue.setTransportation(venuetrans);
			try {
				vd.update(venue);
//				mv = new ModelAndView("updatecategory","venue",venue);
				mv = new ModelAndView("allcategory","venue",venue);
				model.addAttribute("msg", "Category Updated Successfully");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mv = new ModelAndView("updatecategory","msg","Category can not be updated at this time");
			}
			
		}else if(action.equalsIgnoreCase("Delete")) {
			
			Set<Rent> events = venue.getEvents();
			for(Rent event:events) {
				try {
					ed.deleteEvent(event);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mv = new ModelAndView("updatecategory","msg","Something went wrong");
				}
			}
			try {
				vd.deleteCategory(venue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mv = new ModelAndView("updatecategory","msg","Something went wrong");
			}
			List<Category> venues= vd.getCategorybyOwner(usersess);
			mv = new ModelAndView("allcategory","venues",venues);
			model.addAttribute("msg","Category Deleted Successfully");
		}
		
		return mv;
	}
	
	
}
