package ${package_name}.controller;

import com.lanqi.common.utils.PageInfo;
import com.lanqi.common.utils.PageRequestParams;
import com.lanqi.common.utils.Response;
import com.lanqi.common.utils.Result;
<#--import ${package_name}.entity.${table_name};-->
import com.lanqi.common.entity.${table_name};
import ${package_name}.service.${table_name}Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：${table_annotation}控制层
 * @author ${author}
 * @email ${author}
 * @date ${date}
 */
@Controller
@RequestMapping("/${table_name?uncap_first}")
public class ${table_name}Controller {

    @Resource
    private ${table_name}Service ${table_name?uncap_first}Service;

    /**
     * @param ${table_name?uncap_first}
     * @des 创建${table_annotation}
     */
    @PostMapping("/save")
    @ResponseBody
    public Result save(@RequestBody ${table_name} ${table_name?uncap_first}) {
        return new Response<${table_name}>().success(${table_name?uncap_first}Service.save${table_name}(${table_name?uncap_first}));
    }

    /**
     * @des 根据id删除${table_annotation}
     * @param recordId ${table_annotation}id
     */
    @PostMapping(value = "/del")
    @ResponseBody
    public Result deleteById(@RequestParam("recordId") String recordId) {
        return new Response<Integer>().success(${table_name?uncap_first}Service.deleteById(recordId));
    }

    /**
     * @des 根据id集合删除${table_annotation}
     * @param recordIdArr ${table_annotation}id
     */
    @PostMapping(value = "/del/list")
    @ResponseBody
    public Result deleteById(@RequestParam("recordIdArr") Long[] recordIdArr) {
        return new Response<Integer>().success(${table_name?uncap_first}Service.deleteByIdArr(recordIdArr));
    }

    /**
     * @des 修改${table_annotation}
     * @param ${table_name?uncap_first}
     */
    @PostMapping(value = "/update")
    @ResponseBody
    public Result update${table_name}(@RequestBody ${table_name} ${table_name?uncap_first}) {
        return new Response<${table_name}>().success(${table_name?uncap_first}Service.update${table_name}(${table_name?uncap_first}));
    }

    /**
     * @des 根据id查询${table_annotation}
     * @param recordId
     */
    @PostMapping(value = "/find")
    @ResponseBody
    public Result find${table_name}ById(@RequestParam("recordId") String recordId)  {
        return new Response<${table_name}>().success(${table_name?uncap_first}Service.findById(recordId));
    }

    /**
     * @des 根据id集合查询${table_annotation}
     * @param recordIdArr
     */
    @PostMapping(value = "/find/list")
    @ResponseBody
    public Result find${table_name}ByIdList(@RequestParam("recordIdArr") Long[] recordIdArr)  {
        return new Response<List<${table_name}>>().success(${table_name?uncap_first}Service.findByIdArr(recordIdArr));
    }

    /**
     * @des 分页条件${table_annotation}
     * @param pageRequest
     */
    @PostMapping(value = "/find/list/params")
    @ResponseBody
    public Result find${table_name}ByParams(@RequestBody PageRequestParams<${table_name}> pageRequest)  {
        return new Response<PageInfo>().success(${table_name?uncap_first}Service.findByParams(pageRequest));
    }
}