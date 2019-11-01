package com.xiaobingby.auth.api;

import com.xiaobingby.common.core.api.R;
import com.xiaobingby.upms.api.feign.IUserFeign;
import com.xiaobingby.upms.dto.SysUserDetailsDto;
import com.xiaobingby.upms.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApis {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserFeign iUserFeign;

    @Test
    public void test1() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<Future<String>>();
        //创建10个任务并执行
        for (int i = 0; i < 50; i++){
            //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<String> future = executorService.submit(new TaskWithResult());
            //将任务执行结果存储到List中
            resultList.add(future);
        }


        //遍历任务的结果
        for (Future<String> fs : resultList){
            try{
                while(!fs.isDone());// Future返回如果没有完成，则一直循环等待，直到Future返回完成
                logger.info("{}", fs.get()); // 打印各个线程（任务）执行的结果
            }catch(Exception e){
                e.printStackTrace();
            } finally{
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务
                executorService.shutdown();
            }
        }
    }

    class TaskWithResult implements Callable<String> {

        /**
         * 任务的具体过程，一旦任务传给ExecutorService的submit方法，
         * 则该方法自动在一个线程上执行
         */
        public String call() throws Exception {
            while (true) {
                R<SysUserDetailsDto> result = iUserFeign.loadUserByUsername("XiaoBingBy");
                logger.info("{}", result);
            }
//            R<SysUserDetailsDto> result = iUserFeign.loadUserByUsername("XiaoBingBy");
//            logger.info("{}", result);
//            //该返回结果将被Future的get方法得到
//            return JSON.toJSONString(result);
        }
    }

}
