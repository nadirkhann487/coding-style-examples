
package assignment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	
	
	DBClass db = new DBClass();// creates database instance
	
    @RequestMapping("/Login")// displays login page
    public String Login(Model model) {
       
        return "login";
    } 
    
    @RequestMapping("/LoginRequest")// executes login request check if user is present in database and creates a session
    public String LoginRequest(HttpServletRequest request,Model model,@RequestParam(value="email", required=false, defaultValue="-1") String email,@RequestParam(value="pwd", required=false, defaultValue="-1") String pwd) {
       
    	Users u = db.getUser(email, pwd);
    	
    	if(u==null)
    	{
    		 model.addAttribute("errorid", "Invalid email or password");
    		return "error";
    	}

    	 HttpSession se=request.getSession();		
			se.setAttribute("voter",db.getUser(email, pwd));
    	 Referendum uv = db.getReferendum();

    	 if(uv.getOpen() == 1)
    		 model.addAttribute("open", "Stop Referendum");
    	 else 
		model.addAttribute("open", "Start Referendum");
    	 
    	 model.addAttribute("referendum", uv);// add an attribute of referendums to be passed to voter page
        return "voterPage";
    }
    
    @RequestMapping("/RegistrationPage")// display registration page
    public String RegistrationPage(Model model) {
       
        return "registration";
    }
    
    @RequestMapping("/RegisterRequest")// check if bic is valid, if bic is associated with another user, if email is already present in the records or not and insert the user in the database
    public String RegisterRequest(HttpServletRequest request,Model model, @RequestParam(value="name", required=false, defaultValue="-1") String name,@RequestParam(value="email", required=false,       defaultValue="-1") String email,@RequestParam(value="pwd", required=false, defaultValue="-1") String pwd,@RequestParam(value="dob", required=false, defaultValue="-1") String dob,@RequestParam(value="bic", required=false, defaultValue="-1") String bic,@RequestParam(value="address", required=false, defaultValue="-1") String address) {
    
       boolean isExist = db.checkBIC(bic);// check bic
       
       if(!isExist)
       {
    	   model.addAttribute("errorid", "Invalid BIC");
    	   return "error";
       }
    	  
       isExist = db.existingUser(bic);// check existing bic
       if(!isExist)
       {
    	   model.addAttribute("errorid", "Another User already been registered with the same BIC");
    	   return "error";
       }
       
       isExist = db.associatedEmail(email);// check associated email
       if(!isExist)
       {
    	   model.addAttribute("errorid", "Another User has already used provided Email.");
    	   return "error";
       }
       
       boolean isInserted = db.insertVoter(name, dob, email, address, pwd, bic);// inserting into database
       if(isInserted)
       {
    	   HttpSession se=request.getSession();	// session	
			se.setAttribute("voter",db.getUser(email, pwd));// putting values into session
    	   Referendum u = db.getReferendum();

    	   if(u.getOpen() == 1)// if referendum is open or not
      		 model.addAttribute("open", "Stop Referendum");
      	 else 
		model.addAttribute("open", "Start Referendum");

    	   model.addAttribute("referendum", u);
    	   return "voterPage";
       }
        return "login";
    }
    
    @RequestMapping("/ReferendumStatus")// check if referendum status is open or closed
    public String ReferendumStatus(HttpServletRequest request,Model model, @RequestParam(value="REF_ID", required=false, defaultValue="-1") String refID, @RequestParam(value="status", required=false,   defaultValue="-1") String status) {
        	
    	Users u = (Users)request.getSession().getAttribute("voter");
    	Referendum r = db.getReferendum();

    	if(u.getType().equalsIgnoreCase("EC"))
    	{
    		int voteCount = db.getVotingPercentage(Integer.parseInt(refID));
    		
    		if(r.getOpen() == 1)
    		{
    			if(voteCount < 80)
    			{
    				 model.addAttribute("errorid", "Voting can only be closed if 80% of people has voted.");
    			  	   return "error";
    			}
    		}

    		boolean condition = db.changeReferendumStatus(Integer.parseInt(refID),Integer.parseInt(status));// change referendum status

    		if(condition)
    		{
    			int count = db.getCount(r.getID());
            		model.addAttribute("count", count);// adding attributes
            		model.addAttribute("referendum", r);
                	return "Result";// displays result page
            	
    		}
    		return "error";// displays error page
    		
    	}
    	 model.addAttribute("errorid", "Only Election Commision can start/stop Referendum.");
  	   return "error";
    } 

    @RequestMapping("/getReferendums")// get referendums and pass it to the view
    public String getReferendums(Model model) {
       
    	Referendum r = db.getReferendum();// getting from database
    	 if(r.getOpen() == 1)
    		 model.addAttribute("open", "Stop Referendum");
    	 else 
	 	model.addAttribute("open", "Start Referendum");

    	model.addAttribute("referendum", r);
        return "voterPage";
    } 
    
    @RequestMapping("/Logout")// logout request
    public String Logout(HttpServletRequest request,Model model) {
        	
    	HttpSession se=request.getSession();		
    	se.removeAttribute("voter");// removing session
        return "login";
    } 
    
    @RequestMapping("/editOptionPage")// request to edit options of the referendum
    public String editOptionPage(HttpServletRequest request,Model model,@RequestParam(value="OPT_ID", required=false, defaultValue="-1") String opID,@RequestParam(value="REF_ID", required=false, defaultValue="-1") String refID,@RequestParam(value="text", required=false, defaultValue="-1") String text) {
        	
    	Users u = (Users)request.getSession().getAttribute("voter");// get attributre from session
    	
    	if(!u.getType().equalsIgnoreCase(""))// check only election commision can edit the options
    	{
    		model.addAttribute("opt", "'"+text+"'");
        	model.addAttribute("OPT_ID", opID);
        	model.addAttribute("REF_ID", refID);
            return "editOption";
    	}
    	model.addAttribute("errorid", "Only Election Commission can edit the options");
    	return "error";
    } 
    
    @RequestMapping("/editOptions")
    public String editOption(Model model,@RequestParam(value="OPT_ID", required=false, defaultValue="-1") String opID,@RequestParam(value="REF_ID", required=false, defaultValue="-1") String refID,@RequestParam(value="text", required=false, defaultValue="-1") String text) {
    	
    		boolean condition = db.alterOption(Integer.parseInt(opID), Integer.parseInt(refID), text);
        	if(condition)
        	{
        		Referendum r = db.getReferendum();
        		 if(r.getOpen() == 1)
            		 model.addAttribute("open", "Stop Referendum");
            		 else
			model.addAttribute("open", "Start Referendum");

		    	model.addAttribute("referendum", r);
		        return "voterPage";
        	}  	
        	return "error";
    	
    } 
    

    @RequestMapping("/editTitlePage")// handles request to change title page
    public String editTitlePage(HttpServletRequest request,Model model,@RequestParam(value="REF_ID", required=false, defaultValue="-1") String refID,@RequestParam(value="text", required=false, defaultValue="-1") String text) {
        	
    	Users u = (Users)request.getSession().getAttribute("voter");
    	
    	if(!u.getType().equalsIgnoreCase(""))// only election commission can change title of the referendum
    	{
    		model.addAttribute("opt", "'"+text+"'");
        	model.addAttribute("REF_ID", refID);
            return "editText";
            
    	}
    	
    	model.addAttribute("errorid", "Only Election Commission can edit the Title");
    	return "error";
    } 
    
    @RequestMapping("/editTitle")
    public String editTitle(Model model,@RequestParam(value="REF_ID", required=false, defaultValue="-1") String refID,@RequestParam(value="text", required=false, defaultValue="-1") String text) {
        	
    	boolean condition = db.alterText(Integer.parseInt(refID), text);// change title of the referendum in the database
    	if(condition)
    	{
    		Referendum r = db.getReferendum();
    		 if(r.getOpen() == 1)
        		 model.addAttribute("open", "Stop Referendum");
        	 else
			 model.addAttribute("open", "Start Referendum");

        	model.addAttribute("referendum", r);
    		return "voterPage";
    	}
    	return "login";
    }
    
    
    
    @RequestMapping("/ResultsPage")// displays result page
    public String ResultsPage(HttpServletRequest request,Model model) {
        
    	Users u = (Users)request.getSession().getAttribute("voter");
    	
    	if(!u.getType().equalsIgnoreCase(""))
    	{
    		Referendum r = db.getReferendum();// get all referendums and pass it to the view
        	int count = db.getCount(r.getID());
        	model.addAttribute("count", count);
        	model.addAttribute("referendum", r);
            return "Result";
    	}
    	
    	model.addAttribute("errorid", "Only Election Commission can view the results");
    	return "error";
    	
    } 
    
    
    @RequestMapping("/Vote")// increments vote count in the database
    public String Vote(HttpServletRequest request,Model model,@RequestParam(value="COUNT", required=false, defaultValue="-1") String count,@RequestParam(value="REF_ID", required=false, defaultValue="-1") String refID,@RequestParam(value="OPT_ID", required=false, defaultValue="-1") String id) {
        
    	Users u = (Users)request.getSession().getAttribute("voter");
    	Boolean condition = db.doVote(Integer.parseInt(id),Integer.parseInt(refID),Integer.parseInt(count),u);// changing values of count in database
    	if(condition)
    	{
    		Referendum r = db.getReferendum();   
    		 if(r.getOpen() == 1)
        		 model.addAttribute("open", "Stop Referendum");
        	 else 
			model.addAttribute("open", "Start Referendum");

        	model.addAttribute("referendum", r);
        	return "voterPage";
    	}
    	model.addAttribute("errorid", "You have already voted!");
    	return "error";
    } 
}
