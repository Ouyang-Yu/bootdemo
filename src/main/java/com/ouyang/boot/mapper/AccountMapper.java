package com.ouyang.boot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ouyang.boot.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * @DateTime 2023/5/6 20:58
 */

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    // @Select("select ${wrapper.SqlSelect} from account ${wrapper.getcustomSqlSegment} ")
    // List<Account> customSqlUseWrapper(Wrapper<Account> wrapper);

    // Constants.Q_WRAPPER_SQL_SELECT;
    // Constants.WRAPPER_SQLSEGMENT;

    @Select("select ${ew.SqlSelect} from account ${ew.getcustomSqlSegment} ")
    List<Account> customSqlUseWrapper(Page<Account> page, @Param(Constants.WRAPPER) Wrapper<Account> wrapper);
    // wrapper本来就是拼接str,不如用它拼好,拿来自定义sql,为什么不自己拼?因为查询条件if test拼起来麻烦
    // 注意如果多表join返回的是vo类,那么BaseMapper的泛型也应该是Vo类
}
