package com.macro.mall.component;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.macro.mall.service.PmsSkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;

@Component
@Slf4j
public class CanalListener {

    @Value("${canal-monitor-mysql.hostname}")
    String canalMonitorHost;

    @Value("${canal-monitor-mysql.port}")
    Integer canalMonitorPort;

    @Value("${canal-monitor-mysql.tableName}")
    String canalMonitorTableName;

    private final static int BATCH_SIZE = 10000;

    @Autowired
    private PmsSkuStockService skuStockService;

    /**
     * 启动服务
     */
    @Bean
    @Async("ttlThreadExecutor")
    public void startMonitorSQL() {
        while (true) {
            CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(canalMonitorHost, canalMonitorPort), "example", "", "");
            try {
                //打开连接
                connector.connect();
                log.info("数据库检测连接成功!" + canalMonitorTableName);
                //订阅数据库表,全部表q
                connector.subscribe(canalMonitorTableName);
                //回滚到未进行ack的地方，下次fetch的时候，可以从最后一个没有ack的地方开始拿
                connector.rollback();
                while (true) {
                    // 获取指定数量的数据
                    Message message = connector.getWithoutAck(BATCH_SIZE);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                    } else {
                        handleDATAChange(message.getEntries());
                    }
                    // 提交确认
                    connector.ack(batchId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("成功断开监测连接!尝试重连");
            } finally {
                connector.disconnect();
                //防止频繁访问数据库链接: 线程睡眠 10秒
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印canal server解析binlog获得的实体类信息
     */
    private void handleDATAChange(List<CanalEntry.Entry> entrys) {
        log.info("处理[{}]条记录中...",entrys.size());
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            //RowChange对象，包含了一行数据变化的所有特征
            CanalEntry.RowChange rowChange;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),e);
            }
            CanalEntry.EventType eventType = rowChange.getEventType();
            if (eventType == CanalEntry.EventType.UPDATE){
                log.info("Canal监测到更新:[{}] [{}]", entry.getHeader().getTableName(),eventType);
                // 获取之前之后数据集合
                CanalEntry.RowData rowDatas = rowChange.getRowDatas(0);
                skuStockService.listenStock(rowDatas);
            }
        }
    }
}


