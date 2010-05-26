package com.mob.web;
import javax.servlet.http.*;
import java.util.*;
import com.google.inject.*;
import com.google.inject.servlet.*;

@RequestScoped
public class UserContext extends ContextProcessor {

	@Inject
	UserResolver userResolver;

    private SiteUser user = new AnonymousUser(); 

    @Override
	public void process(HttpServletRequest request, HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		if( cookies == null || cookies.length == 0 ){
			return;
		}else{
			for( Cookie c : cookies ){
				if( (c.getName() + "").equals("auth") ){
					String auth = c.getValue() + "";
					if( auth.length() > 0 ){
						UserCookie userCookie = new UserCookie( auth );
						this.user = userResolver.getUser(userCookie);
					}
				}
			}
			return;
		}
    }

    public SiteUser getSiteUser(){ return this.user; }

}
