package config;

import com.aerospike.client.AerospikeClient;

public class AeroSpikeConfig {

    private AeroSpikeConfig() {

    }

    public static AerospikeClient getAeroSpikeClient() {
        AerospikeClient client = new AerospikeClient("172.28.128.3", 3000);
        return client;
    }
}
