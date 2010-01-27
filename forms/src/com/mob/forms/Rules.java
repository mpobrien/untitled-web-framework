package com.mob.forms;
import java.util.*;

public class Rules{

	public static final String PATTERN_ERROR = "pattern";
	public static final String REQUIRED_ERROR = "required";
	public static final String RANGE_ERROR = "range";

    public static ValidationRule required(){//{{{
		return new ValidationRule(){
			public boolean validate(Object value){
				return value != null;
			}
			public String key(){ return REQUIRED_ERROR; }
		};
	}//}}}

	public static ValidationRule requiredWhen( final Condition checkCon ){
		return new ValidationRule(){
			public boolean validate(Object value){
				if( checkCon.isSatisfied() ){
					return value != null;
				}else {
					return true;
				}
			}
			public String key(){ return REQUIRED_ERROR; }
		};
	}

    public static ValidationRule<String> matchesRegex(final String regex){//{{{
		return new ValidationRule<String>(){
			public boolean validate(String value){
				if( value != null ){
					return value.matches( regex );
				}else{
					return true;
				}
			}
			public String key(){ return PATTERN_ERROR; }
		};
	}//}}}

    public static ValidationRule<String> maxLen(final int maxLength){//{{{
		return new ValidationRule<String>(){
			public boolean validate(String value){
				if( value != null ){
					return value.length() <= maxLength;
				}else{
					return true;
				}
			}
			public String key(){ return RANGE_ERROR; }
		};
	}//}}}

    public static ValidationRule<String> minLen(final int minLength){//{{{
		return new ValidationRule<String>(){
			public boolean validate(String value){
				if( value != null ){
					return value.length() >= minLength;
				}else{
					return true;
				}
			}
			public String key(){ return RANGE_ERROR; }
		};
	}//}}}

    public static ValidationRule<Integer> minVal(final int minVal){//{{{
		return new ValidationRule<Integer>(){
			public boolean validate(Integer value){
				if( value != null ){
					return value >= minVal;
				}else{
					return true;
				}
			}
			public String key(){ return RANGE_ERROR; }
		};
	}//}}}

    public static ValidationRule<Integer> maxVal(final int maxVal){//{{{
		return new ValidationRule<Integer>(){
			public boolean validate(Integer value){
				if( value != null ){
					return value <= maxVal;
				}else{
					return true;
				}
			}
			public String key(){ return RANGE_ERROR; }
		};
	}//}}}

    public static ValidationRule<Double> minVal(final double minVal){//{{{
		return new ValidationRule<Double>(){
			public boolean validate(Double value){
				if( value != null ){
					return value >= minVal;
				}else{
					return true;
				}
			}
			public String key(){ return RANGE_ERROR; }
		};
	}//}}}

    public static ValidationRule<Double> maxVal(final double maxVal){//{{{
		return new ValidationRule<Double>(){
			public boolean validate(Double value){
				if( value != null ){
					return value <= maxVal;
				}else{
					return true;
				}
			}
			public String key(){ return RANGE_ERROR; }
		};
	}//}}}

    public static ValidationRule<Float> minVal(final float minVal){//{{{
		return new ValidationRule<Float>(){
			public boolean validate(Float value){
				if( value != null ){
					return value >= minVal;
				}else{
					return true;
				}
			}
			public String key(){ return RANGE_ERROR; }
		};
	}//}}}

    public static ValidationRule<Float> maxVal(final float maxVal){//{{{
		return new ValidationRule<Float>(){
			public boolean validate(Float value){
				if( value != null ){
					return value <= maxVal;
				}else{
					return true;
				}
			}
			public String key(){ return RANGE_ERROR; }
		};
	}//}}}

}
