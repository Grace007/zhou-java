package com.zhou.test.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.List;

/**
 * Curator Framework提供了简化使用zookeeper更高级的API接口,更加便捷的开发zookeeper应用
 * @author eli
 * @date 2017/12/26 15:40
 */
public class CuratorFrameworkTest {

    @Test
    public void test1() {
        //重试规则
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //创建实例
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("curator")
                .build();
        client.start();
        try {
            if (client.checkExists().forPath("/curatorFramework/test") ==  null) {
                //创建一个节点，指定创建模式（节点类型），附带初始化内容，并且自动递归创建父节点
                client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/curatorFramework/test", "init".getBytes());
            }
            //强制删除一个节点，并且递归删除其所有的子节点
            //client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/curatorFramework/test");
            //获取节点数据并且获得stat  返回值是byte[ ];
            Stat stat = new Stat();
            System.out.println("client.getData().forPath(\"/curatorFramework/test\") = " + new String(client.getData().storingStatIn(stat).forPath("/curatorFramework/test")));
            System.out.println("stat = " + stat.toString());
            //更新节点数据
            client.setData().forPath("/curatorFramework/test","update".getBytes());
            //获取某个节点的所有子节点路径(路径不完整,而且只是子节点不会递归)
            List<String>  pathList = client.getChildren().forPath("/curatorFramework");
            System.out.println("pathList.size() = " + pathList.size());
            for(int i=0;i<pathList.size();i++){
                System.out.println("pathList.get(i) = " + pathList.get(i));
            }


            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * PathChildrenCache测试(Node Cache与Path Cache类似, 都有弊端)
     * Path Cache用来监控一个ZNode的子节点. 当一个子节点增加， 更新，删除时， Path Cache会改变它的状态， 会包含最新的子节点， 子节点的数据和状态，而状态的更变将通过PathChildrenCacheListener通知。
     * 弊端:注意：示例中的Thread.sleep(10)可以注释掉，但是注释后事件监听的触发次数会不全，这可能与PathCache的实现原理有关，不能太过频繁的触发事件！
     * @throws Exception
     */
    @Test
    public void PathChildrenCache() throws  Exception{
        String PATH="/example/pathCache";
        //重试规则
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //创建实例
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("curator")
                .build();
        client.start();
        //创建监听缓存, 第三个参数是缓存路径数据
        PathChildrenCache cache = new PathChildrenCache(client, PATH, true);
        cache.start();
        //创建监听器
        PathChildrenCacheListener cacheListener = (client1, event) -> {
            System.out.println("事件类型：" + event.getType());
            if (null != event.getData()) {
                System.out.println("节点数据：" + event.getData().getPath() + " = " + new String(event.getData().getData()));
            }
        };
        cache.getListenable().addListener(cacheListener);
        client.create().creatingParentsIfNeeded().forPath("/example/pathCache/test01", "01".getBytes());
        Thread.sleep(10);
        client.create().creatingParentsIfNeeded().forPath("/example/pathCache/test02", "02".getBytes());
        Thread.sleep(10);
        client.setData().forPath("/example/pathCache/test01", "01_V2".getBytes());
        Thread.sleep(10);
        for (ChildData data : cache.getCurrentData()) {
            System.out.println("getCurrentData:" + data.getPath() + " = " + new String(data.getData()));
        }
        client.delete().forPath("/example/pathCache/test01");
        Thread.sleep(10);
        client.delete().forPath("/example/pathCache/test02");
        Thread.sleep(1000 * 5);
        cache.close();
        client.close();
        System.out.println("OK!");
    }

    @Test
    public void TreeCache() throws Exception{
        String path="/example/treeCache";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(10000,3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(50000).connectionTimeoutMs(50000).retryPolicy(retryPolicy).namespace("curator").build();
        client.start();
        TreeCache cache=new TreeCache(client,path);
        TreeCacheListener treeCacheListener = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("事件类型 : " + treeCacheEvent.getType());
                if(treeCacheEvent.getData() != null){
                    System.out.println("节点数据 : " + treeCacheEvent.getData().getPath()+" =" + new String (treeCacheEvent.getData().getData()));
                }
            }
        };
        cache.start();
        cache.getListenable().addListener(treeCacheListener);
        client.create().creatingParentsIfNeeded().forPath("/example/treeCache/test01","01".getBytes());
        client.create().creatingParentsIfNeeded().forPath("/example/treeCache/test02","02".getBytes());

        client.setData().forPath("/example/treeCache/test01","01_v1".getBytes());

        client.delete().deletingChildrenIfNeeded().forPath("/example/treeCache/test01");
        client.delete().deletingChildrenIfNeeded().forPath("/example/treeCache/test02");
        //这里睡眠是因为cache执行的太快,导致事件都没执行完就关闭
        Thread.sleep(1000);

        cache.close();
        client.close();


    }

/*
    newClient静态工厂方法包含四个主要参数：
    connectionString	服务器列表，格式host1:port1,host2:port2,...
    sessionTimeoutMs	会话超时时间，单位毫秒，默认60000ms
    connectionTimeoutMs	连接创建超时时间，单位毫秒，默认60000ms
    retryPolicy	重试策略,内建有四种重试策略,也可以自行实现RetryPolicy接口

    //使用Fluent风格的Api创建会话,流式创建(带namespace)
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectionInfo)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("curator")
                .build();
    //构造函数构建
    CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",5000, 3000, retryPolicy);

    Zookeeper的节点创建模式：
    PERSISTENT：持久化
    PERSISTENT_SEQUENTIAL：持久化并且带序列号
    EPHEMERAL：临时
    EPHEMERAL_SEQUENTIAL：临时并且带序列号

    同步:
    zookeeper也是事务的概念
    client.inTransaction().check().forPath("path")
      .and()
      .create().withMode(CreateMode.EPHEMERAL).forPath("path","data".getBytes())
      .and()
      .setData().withVersion(10086).forPath("path","data2".getBytes())
      .and()
      .commit();
    异步:


    */

}
