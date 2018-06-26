package ${package_name}.service;

import com.lanqi.common.utils.PageInfo;
import com.lanqi.common.utils.PageRequestParams;
<#--import ${package_name}.entity.${table_name};-->
import com.lanqi.common.entity.${table_name};

import java.util.List;

/**
* 描述：${table_annotation} 服务实现层接口
* @author ${author}
* @date ${date}
*/
public interface ${table_name}Service {

    /**
    * @des 添加${table_name}
    * @param ${table_name?uncap_first}
    */
    ${table_name} save${table_name}(${table_name} ${table_name?uncap_first});

    /**
    * @des 根据id删除
    * @param recordId
    */
    int deleteById(String recordId);

    /**
    * @des 根据id集合删除
    * @param idArr
    */
    int deleteByIdArr(Long[] idArr);

    /**
    * @des 修改${table_name}
    * @param ${table_name?uncap_first}
    */
    ${table_name} update${table_name}(${table_name} ${table_name?uncap_first});

    /**
    * @des 根据Id获取
    * @param recordId
    */
    ${table_name} findById(String recordId);

    /**
    * @des 根据id 集合查询信息
    * @param idArr
    */
    List<${table_name}> findByIdArr(Long[] idArr);

    /**
    * @des 分页查询
    * @param pageRequest
    */
    PageInfo findByParams(PageRequestParams<${table_name}> pageRequest);

    /**
    * @des 根据条件计数
    * @param ${table_name?uncap_first}
    */
    int count(${table_name} ${table_name?uncap_first});

}