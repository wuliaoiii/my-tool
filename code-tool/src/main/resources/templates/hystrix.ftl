package ${package_name}.api.hystrix;

import ${package_name}.api.${table_name}Api;
import ${package_name}.entity.${table_name};
<#--import ${package_name}.common.entity.${table_name};-->
import ${package_name}.common.enums.ErrorCode;
import ${package_name}.common.utils.PageRequestParams;
import ${package_name}.common.utils.Response;
import ${package_name}.common.utils.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 描述：${table_annotation} 熔断
 *
 * @author ${author}
 * @date ${date}
 */
@Component
public class ${table_name}Hystrix implements ${table_name}Api {

    @Override
    public Result save(${table_name} ${table_name?uncap_first}){
        return new Response<Object>().fail(ErrorCode.NETWORK_FAIL);
    }

    @Override
    public Result deleteById(String recordId){
        return new Response<Object>().fail(ErrorCode.NETWORK_FAIL);
    }

    @Override
    public Result deleteByIdArr(Long[] recordIdArr){
    return new Response<Object>().fail(ErrorCode.NETWORK_FAIL);
    }

    @Override
    public Result update${table_name}(${table_name} ${table_name?uncap_first}){
        return new Response<Object>().fail(ErrorCode.NETWORK_FAIL);
    }

    @Override
    public Result find${table_name}ById(String recordId){
        return new Response<Object>().fail(ErrorCode.NETWORK_FAIL);
    }

    @Override
    public Result find${table_name}ByIdList(Long[] recordIdArr){
        return new Response<Object>().fail(ErrorCode.NETWORK_FAIL);
    }

    @Override
    public Result find${table_name}ByParams(PageRequestParams<${table_name}> pageRequest){
        return new Response<Object>().fail(ErrorCode.NETWORK_FAIL);
    }

}
