package com.mob.forms;
import com.mob.forms.widgets.*;
import java.util.*;
import com.google.common.collect.*;

public class EnumField<T extends Enum<T>> extends AbstractMultiFormField<T>{

    private final Class<T> enumClass;
    private T value;

    public EnumField(String name, Class<T> enumClass){
        super(name);
        this.enumClass = enumClass;
		//this.widget = WidgetGenerator.text(this);
        //TODO set the widget up
    }

    @Override
    public T getValue(){ return this.value; }

    @Override
    public void setValue(T val){ this.value = val; }

	@Override
	public void bind() throws BindValueException{//{{{
        if( this.raw == null ){
			setValue( null );
			if( this.required ){
				throw new BindValueException("This field is required.");
			}
        }else{
            this.setValue( lookupOption( this.raw ) );
            if( this.required && this.getValue() == null ){
                throw new BindValueException("Invalid index to enum choices");
            }
        }
	}//}}}

    @Override
    public Iterator<T> getOptions(){
        return Iterators.forArray( enumClass.getEnumConstants() );
    }

//     public Iterator<MultiOption> getHtmlOptions(){
//         return new AbstractIterator<MultiOption>{
//             private Iterator<T> optionsIterator = getOptions();
//             while( optionsIterator.hasNext() ){
//                 return 
// 
//                 return new MultiOption( getFormVal(option), 
//                                         WidgetGenerator.getNiceName(option.toString());
//                                         this.getValue() != null && this.getValue() == option);
//             }
//         }
//     }

//     public abstract Iterator<T> getOptions(String getHtml(){
// 		return new AbstractIterator<MultiOption>{
//             private final Iterator<T> enumIterator = Iterators.forArray( enumClass.getEnumConstants() );
//             private int counter = -1;
// 		    protected String computeNext() {
// 			    while (enumIterator.hasNext()) {
//                     counter++;option
//                     T iterVal = enumIterator.next(0);
//                     boolean selected = ( this.getValue() != null && this.getValue() == iterVal );
//                     return new MultiOption( Integer.toString(val.ordinal()),
//                                             val.toString(),
//                                             selected);
// 				}
// 				return endOfData();
// 			}
// 		};
//     }

    @Override
    public String getFormVal(T value){
        return Integer.toString( value.ordinal() );
    }

    @Override
    public String getFormName(T value){
        return WidgetGenerator.getNiceName(value.toString());
    }

    @Override
	public T lookupOption(String val){
        try{
            Integer index = new Integer(val);
            return this.enumClass.getEnumConstants()[index];
        }catch(Exception e){
            return null;
        }
    }

}
