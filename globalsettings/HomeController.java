package !!!PROJECT_NAME!!!.ctrlrs;
import com.mob.web.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.*;

@At("^/$")
public class HomeController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

    @Override
    public WebResponse get(HttpServletRequest req){

        return new StringWebResponse("Hello World!");

    }

}
