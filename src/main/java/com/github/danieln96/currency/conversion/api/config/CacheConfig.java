package com.github.danieln96.currency.conversion.api.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final String CURRENCIES_CACHE_NAME = "currencies";

    @Bean
    public Config hazelcastConfig() {

        final MapConfig currenciesCacheMapConfig = new MapConfig()
                .setName(CURRENCIES_CACHE_NAME)
                .setEvictionConfig(new EvictionConfig()
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
                        .setSize(10))
                .setTimeToLiveSeconds(60)
                .setInMemoryFormat(InMemoryFormat.BINARY);

        return new Config()
                .addMapConfig(currenciesCacheMapConfig)
                .setNetworkConfig(new NetworkConfig()
                        .setJoin(new JoinConfig()
                                .setMulticastConfig(new MulticastConfig()
                                        .setEnabled(false))));
    }
}
