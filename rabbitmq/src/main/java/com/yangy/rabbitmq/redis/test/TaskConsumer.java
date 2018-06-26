package com.yangy.rabbitmq.redis.test;
  
import java.util.Random;  
  
import redis.clients.jedis.Jedis;  
  
/** 
 * 模拟消费者 
 * <p>Title: TaskConsumer</p> 
 * <p>Description: </p> 
 * <p>Company: </p> 
 * @author 夏 杰 
 * @date 2015年12月11日 下午4:44:23 
 * @vesion 1.0 
*/  
public class TaskConsumer implements Runnable {  
    Jedis jedis = new Jedis("127.0.0.1",6379);
  
    public void run() {  
        Random random = new Random();  
          
        while(true){  
              
            //从任务队列"task-queue"中获取一个任务，并将该任务放入暂存队列"tmp-queue"  
            String taskid = jedis.rpoplpush("task-queue", "tmp-queue");  
              
              
            // 处理任务----纯属业务逻辑，模拟一下：睡觉  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
              
              
            //模拟成功和失败的偶然现象  
            if(random.nextInt(13) % 7 == 0){// 模拟失败的情况,概率为2/13  
                //将本次处理失败的任务从暂存队列"tmp-queue"中，弹回任务队列"task-queue"  
                jedis.rpoplpush("tmp-queue", "task-queue");  
                System.out.println(taskid + "处理失败，被弹回任务队列");  
              
            } else {// 模拟成功的情况  
                jedis.rpoplpush("tmp-queue", "task-queue");
                // 将本次任务从暂存队列"tmp-queue"中清除  
                jedis.rpop("tmp-queue");  
                System.out.println(taskid+"处理成功，被清除");  
                  
            }     
        }  
                          
    }  
      
  
      
}  