package org.banyan.spring.boot.starter.hbase.boot;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.banyan.spring.boot.starter.hbase.api.HbaseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * desc： hbase auto configuration
 * date： 2016-11-16 11:11:27
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HbaseProperties.class)
@ConditionalOnClass(HbaseTemplate.class)
public class HbaseAutoConfiguration {

    private static final String HBASE_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ROOTDIR = "hbase.rootdir";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";


    @Autowired
    private HbaseProperties hbaseProperties;

    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate() {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set(HBASE_QUORUM, this.hbaseProperties.getQuorum());
        if (StringUtils.isNotBlank(this.hbaseProperties.getRootDir())){
            configuration.set(HBASE_ROOTDIR, this.hbaseProperties.getRootDir());
        }
        if (StringUtils.isNotBlank(this.hbaseProperties.getNodeParent())) {
            configuration.set(HBASE_ZNODE_PARENT, this.hbaseProperties.getNodeParent());
        }
        return new HbaseTemplate(configuration);
    }
}
