package com.mob.web;
import javax.servlet.http.*;
import java.io.*;

public interface WebResponse{

	public boolean writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
