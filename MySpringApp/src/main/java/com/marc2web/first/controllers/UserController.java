package com.marc2web.first.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.marc2web.first.dao.UserRepo;
import com.marc2web.first.model.User;

@Controller
@org.springframework.transaction.annotation.Transactional

public class UserController 
{
	@Autowired
	UserRepo userRepo;
	 @PersistenceContext
	  private EntityManager em;
	
	@RequestMapping(value= {"/","/home","/index"},method = RequestMethod.GET)
	public String goToHome() {
		
		return "index.jsp";
	}
	
	@RequestMapping(value= {"/CreateUser"},method=RequestMethod.GET)
	public String gotoRegister()
	{
		return "register.jsp";
	}
	
	@RequestMapping(value= {"/addUser"},method = RequestMethod.POST)
	public String goToRegister(User user) {
		userRepo.save(user);
		return "register.jsp";
	}
	
   @RequestMapping(value="/ShowUser", method=RequestMethod.GET)
   public ModelAndView showUser()
   {
	   Iterable<User> it=userRepo.findAll();
	   ModelAndView modelAndView= new ModelAndView("ShowUser.jsp");
	   modelAndView.addObject("users",it);
	   modelAndView.addObject("title","show users");

	   return modelAndView;	   
	      }
   
   @RequestMapping (value="delete/{id}/user",method=RequestMethod.GET)
   public String gotoDelete(@PathVariable ("id") Integer id)
      {
	   
	   try {
			userRepo.deleteById(id);
			return "redirect:/ShowUser";
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "redirect:/ShowUser";
			}
   }

	@RequestMapping (value="update/{id}/user/request",method=RequestMethod.GET)
	public ModelAndView gotoUpdate(@PathVariable("id") Integer id)
	{
		try {
		User user=em.find(User.class,id);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("/Update.jsp");
		modelAndView.addObject("user",user);
		return modelAndView;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ModelAndView("/Update.jsp");
		}
		
		
		
	}
	
	@RequestMapping (value="/UpdateUser", method=RequestMethod.POST)
	public String updateUser(User user)
	{

		User user1=em.find(User.class, user.getId());
		System.out.println(user.toString());
		System.out.println(user1.toString());
		if(user1.getId()==user.getId())
		{
			user1.setUname(user.getUname());
			user1.setPassword(user.getPassword());
			user1.setRole(user.getRole());
			
		}
		em.persist(user1);
		return "redirect:/ShowUser";
		
		
	}
	
	

}
