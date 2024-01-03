CREATE TABLE topic_reviews (
    id              SERIAL        PRIMARY KEY,
    grade           INT           NOT NULL,
    category        VARCHAR(20)   NOT NULL,
    review_id   BIGINT        NOT NULL,

    FOREIGN KEY (review_id)   REFERENCES reviews(id)
);
