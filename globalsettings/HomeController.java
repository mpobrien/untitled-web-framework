package !!!PROJECT_NAME!!!.ctrlrs;
import com.mob.web.*;
import javax.servlet.http.HttpServletRequest;

@At("^/$")
public class HomeController extends Controller{

    @Override
    public WebResponse get(HttpServletRequest req){

        return new StringWebResponse("Hello World!");

    }

}
