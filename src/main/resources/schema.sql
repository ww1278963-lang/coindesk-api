DROP TABLE IF EXISTS currency;
CREATE TABLE currency (
    code VARCHAR(3) NOT NULL,
    name_zh VARCHAR(50) NOT NULL,
    PRIMARY KEY (code)
);