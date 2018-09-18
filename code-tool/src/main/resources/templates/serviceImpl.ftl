package ${package_name}.service.impl;

import com.alibaba.fastjson.JSON;
import ${package_name}.common.enums.ErrorCode;
import ${package_name}.common.exception.MyException;
import ${package_name}.common.utils.PageInfo;
import ${package_name}.common.utils.PageRequestParams;
import ${package_name}.dao.${table_name}Mapper;
import ${package_name}.entity.${table_name};
<#--import ${package_name}.common.entity.${table_name};-->
import ${package_name}.service.${table_name}Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：${table_annotation} 服务实现层
 *
 * @author ${author}
 * @date ${date}
 */
@Service
public class ${table_name}ServiceImpl implements ${table_name}Service {

    @Resource
    private ${table_name}Mapper ${table_name?uncap_first}Mapper;

    private static final Logger log = LoggerFactory.getLogger(${table_name}Service.class);

    /**
     * @des 添加 ${table_name}
     * @param ${table_name?uncap_first}
     */
    @Override
    @Transactional
    public ${table_name} save${table_name}(${table_name} ${table_name?uncap_first}){
        log.info("添加${table_name?uncap_first} -> ${table_name?uncap_first}={}",JSON.toJSONString(${table_name?uncap_first}));
        ${table_name?uncap_first}.setCreateTime(System.currentTimeMillis());
        int saveResult = ${table_name?uncap_first}Mapper.save${table_name}(${table_name?uncap_first});
        if (saveResult < 1) {
            throw new MyException(ErrorCode.SAVE_FAIL);
        }
        return findById(String.valueOf(${table_name?uncap_first}.getId()));
    }

    /**
     * @des 修改 ${table_name}
     * @param ${table_name?uncap_first}
     */
    @Override
    @Transactional
    public ${table_name} update${table_name}(${table_name} ${table_name?uncap_first}){
        log.info("修改${table_name?uncap_first}-> ${table_name?uncap_first}={}",JSON.toJSONString(${table_name?uncap_first}));
        if(null == ${table_name?uncap_first}.getId()){
            throw new MyException(ErrorCode.UPDATE_FAIL);
        }
        int updateResult = ${table_name?uncap_first}Mapper.update${table_name}(${table_name?uncap_first});
        if (updateResult < 1) {
            throw new MyException(ErrorCode.UPDATE_FAIL);
        }
        return findById(String.valueOf(${table_name?uncap_first}.getId()));
    }

    /**
     * @des 根据主键删除信息
     * @param recordId
     */
    @Override
    @Transactional
    public int deleteById(String recordId){
        log.info("删除${table_name?uncap_first} -> recordId={}",recordId);
        if(StringUtils.isEmpty(recordId)){
            throw new MyException(ErrorCode.DELETE_FAIL);
        }
        int delResult = ${table_name?uncap_first}Mapper.deleteById(recordId);
        if (delResult < 1) {
            throw new MyException(ErrorCode.DELETE_FAIL);
        }
        return delResult;
    }

    /**
     * @des 根据主键集合删除信息
     * @param idArr
     */
    @Override
    @Transactional
    public int deleteByIdArr(Long[] idArr){
        log.info("批量删除${table_name?uncap_first} -> idArr={}",JSON.toJSONString(idArr));
        if(null == idArr || 0 == idArr.length){
            throw new MyException(ErrorCode.REQUEST_ERROR);
        }
        return ${table_name?uncap_first}Mapper.deleteByIdArr(idArr);
    }

    /**
     * @des 根据id 查询信息
     * @param recordId
     */
    @Override
    @Transactional
    public ${table_name} findById(String recordId){
        log.info("根据主键查询${table_name?uncap_first} -> recordId={}",recordId);
        return StringUtils.isEmpty(recordId ) ? null : ${table_name?uncap_first}Mapper.findById(recordId) ;
    }

    /**
     * @des 根据id 集合查询信息
     * @param idArr id集合
     */
    @Override
    @Transactional(readOnly = true)
    public List<${table_name}> findByIdArr(Long[] idArr){
        log.info("根据主键集合查询${table_name?uncap_first} -> idArr={}",JSON.toJSONString(idArr));
        return ${table_name?uncap_first}Mapper.findByIdArr(idArr);
    }

    /**
     * @des 根据条件分页查询信息
     * @param pageRequest
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo findByParams(PageRequestParams<${table_name}> pageRequest) {
        log.info("分页查询${table_name?uncap_first} -> pageRequest={}",JSON.toJSONString(pageRequest));
        ${table_name} ${table_name?uncap_first} = pageRequest.getParams();
        List<${table_name}> ${table_name?uncap_first}List = ${table_name?uncap_first}Mapper.findByParams(${table_name?uncap_first}, pageRequest);
        int count = count(${table_name?uncap_first});

        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalElements(count);
        pageInfo.setNumber(pageRequest.getPageIndex());
        pageInfo.setSize(pageRequest.getPageSize());
        pageInfo.setContent(${table_name?uncap_first}List);

        return pageInfo;
    }

    /**
     * @des 根据条件统计信息
     * @param ${table_name?uncap_first}
     */
    @Override
    @Transactional(readOnly = true)
    public int count(${table_name} ${table_name?uncap_first}) {
        log.info("根据条件计数 -> ${table_name?uncap_first}={}", JSON.toJSONString(${table_name?uncap_first}));
        return ${table_name?uncap_first}Mapper.count(${table_name?uncap_first});
    }

}