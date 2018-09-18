package ${package_name}.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 描述：${table_annotation}模型
 * @author ${author}
 * @date ${date}
 */
public class ${table_name} implements Serializable {

<#if model_column?exists>
    <#list model_column as model>
    <#if (model.columnType = 'VARCHAR' || model.columnType = 'text')>
    /**
     *${model.columnComment!}
     */
    private String ${model.changeColumnName?uncap_first};

    </#if>
    <#if (model.columnType = 'BIGINT' && model.columnName = 'id')>
    /**
     *${model.columnComment!}
     */
    private Long ${model.changeColumnName?uncap_first};

    <#elseif (model.columnType = 'BIGINT' && model.columnName != 'id')>
    /**
     *${model.columnComment!}
     */
    private Long ${model.changeColumnName?uncap_first};

    </#if>
    <#if (model.columnType = 'INT')>
    /**
     *${model.columnComment!}
     */
    private Integer ${model.changeColumnName?uncap_first};

    </#if>
    <#if (model.columnType = 'DECIMAL')>
    /**
     *${model.columnComment!}
     */
    private BigDecimal ${model.changeColumnName?uncap_first};

    </#if>
    <#if model.columnType = 'TIMESTAMP'>
    /**
     *${model.columnComment!}
     */
    private Date ${model.changeColumnName?uncap_first};

    </#if>
    </#list>
</#if>

<#if model_column?exists>
    <#list model_column as model>
    <#if (model.columnType = 'VARCHAR' || model.columnType = 'text')>
    public String get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }
    public void set${model.changeColumnName}(String ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if (model.columnType = 'Long')>
    public Integer get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }
    public void set${model.changeColumnName}(Integer ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if (model.columnType = 'INT')>
    public Integer get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }
    public void set${model.changeColumnName}(Integer ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if (model.columnType = 'BIGINT')>
    public Long get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }
    public void set${model.changeColumnName}(Long ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if (model.columnType = 'DECIMAL')>
    public BigDecimal get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }
    public void set${model.changeColumnName}(BigDecimal ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>

    <#if model.columnType = 'TIMESTAMP' >
    public Date get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }
    public void set${model.changeColumnName}(Date ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    </#list>
</#if>

}