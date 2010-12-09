package !!!PROJECT_NAME!!!.ctrlrs;
import com.mob.web.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.*;
import com.google.common.collect.*;

@At("^/$")
public class HomeController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

    @Override
    public WebResponse get(HttpServletRequest req, HttpServletResponse res){

		HashMap context = new HashMap();
		context.put("message", "hello world!");

		return responses.render("index.html", context);

		// Return a string:
		// return new StringWebResponse("Hello World!");
    }

    @Override
    public WebResponse post(HttpServletRequest req, HttpServletResponse res){
		return get(req, res);
	}

}
