package com.envyful.sts.forge.config;

public class STSQueries {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `envy_sts_cooldowns`(" +
            "id         INT         UNSIGNED        NOT NULL        AUTO_INCREMENT, " +
            "uuid       VARCHAR(64) NOT NULL, " +
            "last_use   BIGINT      NOT NULL, " +
            "UNIQUE(uuid), " +
            "PRIMARY KEY(id));";

    public static final String LOAD_DATA = "SELECT last_use FROM `envy_sts_cooldowns` WHERE uuid = ?;";

    public static final String UPDATE_DATA = "INSERT INTO `envy_sts_cooldowns`(uuid, last_use) VALUES (?, ?) " +
            "ON DUPLICATE KEY UPDATE last_use = VALUES(`last_use`);";

}
