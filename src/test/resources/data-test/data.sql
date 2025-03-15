-- seller1@seller.com:seller
INSERT INTO users (id, first_name, last_name, password, email, created_at, role, enabled)
VALUES (1, 'seller1', 'seller1', '$2a$12$VAtVGnNnL9x8k.pdj448uuca9V4PgxNoLG.8tvy05J3Tm3KJlWa2W',
        'seller1@seller.com', '2025-01-01 01:00:00.000000', 1, true);

-- seller2@seller.com:seller
INSERT INTO users (id, first_name, last_name, password, email, created_at, role, enabled)
VALUES (2, 'seller2', 'seller2', '$2a$12$VAtVGnNnL9x8k.pdj448uuca9V4PgxNoLG.8tvy05J3Tm3KJlWa2W',
        'seller2@seller.com', '2025-01-01 01:00:00.000000', 1, true);

-- seller3@seller.com:seller
INSERT INTO users (id, first_name, last_name, password, email, created_at, role, enabled)
VALUES (3, 'seller3', 'seller3', '$2a$12$VAtVGnNnL9x8k.pdj448uuca9V4PgxNoLG.8tvy05J3Tm3KJlWa2W',
        'seller3@seller.com', '2025-01-01 01:00:00.000000', 1, true);
-- seller4@seller.com:seller
INSERT INTO users (id, first_name, last_name, password, email, created_at, role, enabled)
VALUES (4, 'seller4', 'seller4', '$2a$12$VAtVGnNnL9x8k.pdj448uuca9V4PgxNoLG.8tvy05J3Tm3KJlWa2W',
        'seller4@seller.com', '2025-01-01 01:00:00.000000', 1, true);

-- sellerProfile for seller
INSERT INTO seller_profiles (id, name, approved, user_id)
VALUES (1, 'super seller profile', true, 1);

-- sellerProfile for seller
INSERT INTO seller_profiles (id, name, approved, user_id)
VALUES (2, 'mega seller profile', true, 2);

-- sellerProfile for seller
INSERT INTO seller_profiles (id, name, approved, user_id)
VALUES (3, 'awesome seller profile', true, 3);

--comments

INSERT INTO comments (author_id, message, rating, created_at, approved, seller_profile_id)
VALUES ('a7fb78ff-4faa-41a7-a636-7dad3343203e', 'message1', 5, '2025-01-01 01:00:00.000000', true, 1);

INSERT INTO comments (author_id, message, rating, created_at, approved, seller_profile_id)
VALUES ('a7fb78ff-4faa-41a7-a636-7dad3343203e', 'message2', 3, '2025-01-01 01:00:00.000000', true, 1);

INSERT INTO comments (author_id, message, rating, created_at, approved, seller_profile_id)
VALUES ('a7fb78ff-4faa-41a7-a636-7dad3343203e', 'message3', 5, '2025-01-01 01:00:00.000000', true, 2);

INSERT INTO comments (author_id, message, rating, created_at, approved, seller_profile_id)
VALUES ('a7fb78ff-4faa-41a7-a636-7dad3343203e', 'message4', 3, '2025-01-01 01:00:00.000000', true, 3);


--reset id's generation
SELECT setval('seller_profiles_id_seq', (SELECT MAX(id) FROM seller_profiles));