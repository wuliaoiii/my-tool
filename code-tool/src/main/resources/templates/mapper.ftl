package ${package_name}.dao;

<#--import ${package_name}.entity.${table_name};-->
import com.lanqi.common.entity.${table_name};
import com.lanqi.common.utils.PageInfo;
import com.lanqi.common.utils.PageRequestParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* 描述：${table_annotation}
* @author ${author}
* @date ${date}
*/
@Mapper
public interface ${table_name}Mapper {

    /**
    * @des 添加${table_name}
    * @param ${table_name?uncap_first}
    */
    int save${table_name}(${table_name} ${table_name?uncap_first});

    /**
    * @des 根据id删除
    * @param recordId
    */
    int deleteById(@Param("recordId")String recordId);

    /**
    * @des 根据id集合删除
    * @param idArr
    */
    int deleteByIdArr(@Param("idArr")Long[] idArr);

    /**
    * @des 修改${table_name}
    * @param ${table_name?uncap_first}
    */
    int update${table_name}(${table_name} ${table_name?uncap_first});

    /**
    * @des 根据Id获取
    * @param recordId
    */
    ${table_name} findById(String recordId);

    /**
    * @des 根据id 集合查询信息
    * @param idArr
    */
    List<${table_name}> findByIdArr(@Param("idArr")Long[] idArr);

    /**
    * @des 分页查询
    * @param pageRequest
    */
    List<${table_name}> findByParams(@Param("${table_name?uncap_first}")${table_name} ${table_name?uncap_first},@Param("pageRequest")PageRequestParams<${table_name}> pageRequest);


    /**
    * @des 根据条件计数
    * @param pageRequest
    */
    int count(${table_name} ${table_name?uncap_first});

}