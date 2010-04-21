package tests;
import com.mob.forms.*;
import java.util.*;
import com.google.common.collect.*;

public class TestForm extends AbstractForm{

    public TestForm(){
        field( new IntegerField("field1") );
        field( new StringField("field2") );
    }

	public void validate(){
		// field1 must be between 100 and 200
		// field2 must not be less than 5 chars
		IntegerField f1 = (IntegerField)fields.get("field1");
		StringField f2 = (StringField)fields.get("field2");
		if( f1.getValue() == null || f1.getValue() < 100 || f1.getValue() > 200 ){
			error(f1, "value must be between 100 and 200");
		}

		if( (f2.getValue() + "").length() >= 5 ){
			error(f2, "must be less than 5 chars");
		}
    }

	public static void main(String args[]){
		TestForm f = new TestForm();
		Map<String, String[]> testmap1 =
			ImmutableMap.of("field1",new String[]{"250"},
			                "field2", new String[]{"1234567"});
		f.bind(testmap1);
		f.validate();
		FormErrors fe = f.getErrors();
		if( fe.hasErrors() ){
			System.out.println("field1 errors: " + f.getField("field1").getErrors() );
			System.out.println("field2 errors: " + f.getField("field2").getErrors() );
		}
	}

}
