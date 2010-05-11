package com.mob.web;
import javax.servlet.http.*;
import java.util.*;
import com.google.inject.*;
import com.google.inject.servlet.*;

@RequestScoped
public class UserContextProcessor extends ContextProcessor implements Provider<UserCookie>{

    @Override
	public void process(){
        System.out.println("haha " + this.toString() );
    }

	@Provides
	public UserCookie get(){
		return new UserCookie(this.toString());
	}


}
