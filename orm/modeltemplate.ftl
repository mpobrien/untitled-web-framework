package ${package};
import com.mob.orm.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import javax.sql.*;
import java.sql.*;
import java.util.List;

<#assign modeltype>
	<#if table.primaryKeys?size==0>
		Model
	<#elseif table.primaryKeys?size==1>
		SingleKeyedModel<#t>
	<#elseif (table.primaryKeys?size>1) >
		MultiKeyedModel<#t>
	</#if>
</#assign>

<#assign modelmanagertype = modeltype + "Manager">

public class ${table.classname} extends ${modeltype}{
    protected boolean isDirty = false;
    protected boolean isNew = true;

//Fields
<#list table.cols as col>
    protected ${col.javaTypeStr} ${col.javaName};
</#list>  

	public enum ${table.classname}Field implements DbField {
<#list table.cols as col>
	<#if col_index == table.cols?size-1>
        ${col.name}("${col.name}");
	<#else>
        ${col.name}("${col.name}"),
	</#if>
</#list>  

        private String column;
        ${table.classname}Field(String column){ this.column = column; }
        public String table(){ return "${table.tablename}"; } 
        public String column(){ return this.column; }
	}

<#if modeltype=="SingleKeyedModel">
    public static DbField pkField(){ return ${table.classname}Field.${table.primaryKeys[0].name}; }
	public ${table.primaryKeys[0].javaTypeStr} getPrimaryKey(){ return ${table.primaryKeys[0].javaName}; };
</#if>

<#if modeltype=="MultiKeyedModel">
    protected static final ImmutableList<DbField> primaryKeyFields = new ImmutableList.Builder<DbField>()
<#list table.primaryKeys as pk>
    <#if pk_index=table.primaryKeys?size-1>
        .add( ${table.classname}Field.${pk.name}).build();
    <#else>
        .add( ${table.classname}Field.${pk.name})
    </#if>
</#list>

    public static List<DbField> pkFields(){ return primaryKeyFields; }
</#if>

    public static final ImmutableList<DbField> FIELDS = new ImmutableList.Builder<DbField>().addAll( Lists.newArrayList( ${table.classname}Field.values() ) ).build();

    public static ${modelmanagertype}<${table.classname}> objects( ConnectionProvider connProvider ){ return new ${modelmanagertype}( ${table.classname}.class, connProvider ); }
    public static <T extends ${table.classname}> ${modelmanagertype}<T> objects( Class<T> returnClass, ConnectionProvider connProvider ){ return new ${modelmanagertype}<T>(returnClass, connProvider ); }

    public ImmutableList<DbField> fields(){ return FIELDS; }


    private void setDirty(){ this.isDirty = true; }
    private void unsetDirty(){ this.isDirty = false; }

//Getters//{{{
<#list table.cols as col>
    public ${col.javaTypeStr} get${col.javaNameCaps}(){ return this.${col.javaName}; }
</#list>  
//}}}

//Setters //{{{
<#list table.cols as col>
    public void set${col.javaNameCaps}( ${col.javaTypeStr} ${col.javaName} ){
        this.${col.javaName} = ${col.javaName}; 
    }
</#list>  
//}}}

	public ${table.classname}(){}

	public ${table.classname}( BasicResultSet rs, int offset, int size ){
		this.populateWithRS( rs, offset, size );
	}

	public void populateWithRS( BasicResultSet rs, int offset, int size ) {
<#list table.cols as col>
		this.${col.javaName} = rs.get${col.javaTypeStr}(offset + ${col_index} );
</#list>  
        this.isNew = false;
        this.isDirty = false;
    }

<#if modeltype == "SingleKeyedModel" || modeltype == "MultiKeyedModel">
    public void insert(ConnectionProvider cp){
        Object[] args = new Object[ ${table.cols?size-1} ];
        String sql = "INSERT INTO ${table.tablename} (<#rt>
    <#list table.nonAutoincCols as c>
        <#if c_index == table.nonAutoincCols?size-1>
        ${c.name})<#t>
        <#else>
        ${c.name},<#t>
        </#if>
    </#list> values (<#t>
    <#list table.nonAutoincCols as c>
        <#if c_index == table.nonAutoincCols?size-1>
        ?<#t>
        <#else>
        ?,<#t>
        </#if>
    </#list>)";<#lt>

    <#list table.nonAutoincCols as c><#t>
        <#if !c.isAutoIncrement>
            args[ ${c_index} ] = this.${c.javaName};
        </#if>
    </#list>
            this.objects(cp).update(sql,args);
            //TODO fetching the keys
            <#--this.${table.primaryKey.javaName} = this.objects(cp).updateGetKey(sql, args);-->
    }
</#if>

<#if modeltype=='SingleKeyedModel' || modeltype=='MultiKeyedModel'>
    public void update(ConnectionProvider cp){
        Object[] args = new Object[${table.cols?size + table.primaryKeys?size}];
        String sql = "UPDATE ${table.tablename} SET <#rt>
<#list table.cols as c>
	<#if c_index==table.cols?size-1>
		${c.name} = ?<#t>
	<#else>
		${c.name} = ?,<#t>
	</#if>
</#list>
 WHERE <#rt>
<#list table.primaryKeys as pk>
	<#if pk_index==table.primaryKeys?size-1>
		${pk.name} = ?)"; <#t>
	<#else>
		${pk.name} = ? AND <#t>
	</#if> 
</#list>

<#list table.cols as c>
    args[${c_index}] = this.${c.javaName};
</#list>
<#list table.primaryKeys as pk>
    args[${table.cols?size + pk_index}] = this.${pk.javaName};
</#list>
            this.objects(cp).update(sql, args);
    }
</#if>


<#if modeltype=='SingleKeyedModel' || modeltype=='MultiKeyedModel'>
    public void delete(ConnectionProvider cp){
        //TODO check each primary key
        if( this.isNew ){
            throw new IllegalStateException("Can't delete an instance of a model that has no primary key.");
        }else{
            String sql = "DELETE FROM ${table.tablename} WHERE <#rt>
<#list table.primaryKeys as pk>
	<#if pk_index==table.primaryKeys?size-1>
		${pk.name} = ?<#t>
	<#else>
		${pk.name} = ? AND <#t>
	</#if>
</#list>";
            this.objects(cp).update(sql,<#rt>
<#list table.primaryKeys as pk>
	<#if pk_index==table.primaryKeys?size-1>
	 this.${pk.javaName}   <#t>
	<#else>
	 this.${pk.javaName},<#t>
	</#if>
    <#t>
</#list>);

        }
    }
</#if>

    protected String showFields(){
        StringBuilder retVal = new StringBuilder("");
<#list table.cols as c>
	<#if c_index==table.cols?size-1>
        retVal.append("{").append("${c.javaName}=").append( this.${c.javaName} != null ? this.${c.javaName}.toString() : "null").append("},");
	<#else>
        retVal.append("{").append("${c.javaName}=").append( this.${c.javaName} != null ? this.${c.javaName}.toString() : "null").append("}]");
	</#if>
</#list>
        return retVal.toString();
    }

    public String toString(){
        StringBuilder retVal = new StringBuilder("[" + this.getClass().getName() + ": ");
        retVal.append( showFields() );
        return retVal.toString();
    }

}
