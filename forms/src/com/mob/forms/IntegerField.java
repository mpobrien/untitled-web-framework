package com.mob.forms;

public class IntegerField extends AbstractFormField implements FormField<Integer>{
    private Integer value;

    public IntegerField(String name){
        super(name);
    }

    public IntegerField(String name, Integer defaultVal){
        super(name);
        this.value = defaultVal;
    }

    @Override
    public Integer getValue(){
        return this.value;
    }

    @Override
    public void setValue(Integer val){
        this.value = val;
    }
    
}
