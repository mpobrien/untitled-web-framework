package com.mob.web;
import javax.servlet.http.*;
import java.util.*;
import com.google.inject.*;

public abstract class ContextProcessor{

	public abstract void process(HttpServletRequest request);

}
