CREATE TABLE IF NOT EXISTS clans_clan
(
    id          INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    origin      TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS clans_player
(
    uuid       CHAR(36) NOT NULL,
    clan_id    INT      NOT NULL,
    permission INT      NOT NULL,
    PRIMARY KEY (uuid)
);

CREATE TABLE IF NOT EXISTS clans_history
(
    id        INT                                 NOT NULL AUTO_INCREMENT,
    uuid      CHAR(36)                            NOT NULL,
    clan_id   INT                                 NOT NULL,
    activity  ENUM('CREATE', 'UPDATE', 'DISBAND') NOT NULL,
    parameter VARCHAR(255)                        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS clans_invite
(
    id        INT       NOT NULL AUTO_INCREMENT,
    from_uuid CHAR(36)  NOT NULL,
    to_uuid   CHAR(36)  NOT NULL,
    clan_id   INT       NOT NULL,
    origin    TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS clans_wallet
(
    id      INT              NOT NULL AUTO_INCREMENT,
    clan_id INT              NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS clans_wallet_history
(
    id        INT                         NOT NULL AUTO_INCREMENT,
    wallet_id INT                         NOT NULL,
    activity  ENUM('deposit', 'withdraw') NOT NULL,
    amount    DOUBLE PRECISION            NOT NULL,
    PRIMARY KEY (id)
);