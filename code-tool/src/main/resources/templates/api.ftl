package ${package_name}.api;

import ${package_name}.api.hystrix.${table_name}Hystrix;
import ${package_name}.entity.${table_name};
<#--import com.lanqi.common.entity.${table_name};-->
import ${package_name}.common.utils.PageRequestParams;
import ${package_name}.common.utils.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：${table_annotation} Api
 *
 * @author ${author}
 * @date ${date}
 */
@FeignClient(value = "${table_name?uncap_first}", fallback = ${table_name}Hystrix.class)
public interface ${table_name}Api {
     /**
    * @des 创建${table_annotation}
    * @param ${table_name?uncap_first}
    */
    @PostMapping("/${table_name?uncap_first}/save")
    @ResponseBody
    Result save(@RequestBody ${table_name} ${table_name?uncap_first});

    /**
    * @des 根据id删除${table_annotation}
    * @param recordId ${table_annotation}id
    */
    @PostMapping(value = "/${table_name?uncap_first}/del")
    @ResponseBody
    Result deleteById(@RequestParam("recordId") String recordId);

    /**
    * @des 根据id集合删除${table_annotation}
    * @param recordIdArr ${table_annotation}id
    */
    @PostMapping(value = "/${table_name?uncap_first}/del/list/{recordIdArr}")
    @ResponseBody
    Result deleteByIdArr(@RequestParam("recordIdArr") Long[] recordIdArr);

    /**
    * @des 修改${table_annotation}
    * @param ${table_name?uncap_first}
    */
    @PostMapping("/${table_name?uncap_first}/update")
    @ResponseBody
    Result update${table_name}(@RequestBody ${table_name} ${table_name?uncap_first});

    /**
    * @des 根据id查询${table_annotation}
    * @param recordId
    */
    @PostMapping(value = "/${table_name?uncap_first}/find")
    @ResponseBody
    Result find${table_name}ById(@RequestParam("recordId") String recordId);

    /**
    * @des 根据id集合查询${table_annotation}
    * @param recordIdArr
    */
    @PostMapping(value = "/${table_name?uncap_first}/find/list")
    @ResponseBody
    Result find${table_name}ByIdList(@RequestParam("recordIdArr") Long[] recordIdArr);

    /**
    * @des 分页条件${table_annotation}
    * @param pageRequest
    */
    @PostMapping(value = "/${table_name?uncap_first}/find/list/params")
    @ResponseBody
    Result find${table_name}ByParams(@RequestBody PageRequestParams<${table_name}> pageRequest);
}