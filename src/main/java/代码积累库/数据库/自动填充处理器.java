package 代码积累库.数据库;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class 自动填充处理器 {
    @Component
    public class AutoFillHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            metaObject.setValue("createTime", LocalDateTime.now());
            metaObject.setValue("createBy","ThreadLocal");
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            metaObject.setValue("updateTime",LocalDateTime.now());
        }
    }
}
