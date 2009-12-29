package com.mob.forms;
import java.lang.reflect.*;
import java.util.*;

public class TestForm extends Form{

	@Override
	protected void fields(){
		stringField( "field1", Rules.required() );
	}

}
