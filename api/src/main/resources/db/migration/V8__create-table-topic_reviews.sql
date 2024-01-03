CREATE TABLE topic_reviews (
    id              SERIAL        PRIMARY KEY,
    grade           INT           NOT NULL,
    category        VARCHAR(20)   NOT NULL,
    occupation_id   BIGINT        NOT NULL,

    FOREIGN KEY (occupation_id)   REFERENCES occupations(id)
);
