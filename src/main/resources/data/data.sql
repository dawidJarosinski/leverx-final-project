-- admin@admin.com:admin
INSERT INTO users (first_name, last_name, password, email, created_at, role, enabled)
VALUES ('admin', 'admin', '$2a$12$TS5XPiVPCESnPjBBYgnd2.ZIQUekePXROUNzjLH1BKlARKH7KST8u',
        'admin@admin.com', '2025-01-01 01:00:00.000000', 0, true);

-- seller1@seller.com:seller
INSERT INTO users (first_name, last_name, password, email, created_at, role, enabled)
VALUES ('seller1', 'seller1', '$2a$12$VAtVGnNnL9x8k.pdj448uuca9V4PgxNoLG.8tvy05J3Tm3KJlWa2W',
        'seller@seller.com', '2025-01-01 01:00:00.000000', 1, true);

-- seller2@seller.com:seller
INSERT INTO users (first_name, last_name, password, email, created_at, role, enabled)
VALUES ('seller2', 'seller2', '$2a$12$VAtVGnNnL9x8k.pdj448uuca9V4PgxNoLG.8tvy05J3Tm3KJlWa2W',
        'seller@seller.com', '2025-01-01 01:00:00.000000', 1, true);

-- sellerProfile for seller
INSERT INTO seller_profiles (name, approved, user_id)
VALUES ('super seller profile', true, 2);

-- sellerProfile for seller
INSERT INTO seller_profiles (name, approved, user_id)
VALUES ('super seller profile', true, 3);

--comments

INSERT INTO comments (author_id, message, rating, created_at, approved, seller_profile_id)
VALUES ('a7fb78ff-4faa-41a7-a636-7dad3343203e', 'message1', 5, '2025-01-01 01:00:00.000000', true, 1);

INSERT INTO comments (author_id, message, rating, created_at, approved, seller_profile_id)
VALUES ('a7fb78ff-4faa-41a7-a636-7dad3343203e', 'message2', 3, '2025-01-01 01:00:00.000000', true, 1);

INSERT INTO comments (author_id, message, rating, created_at, approved, seller_profile_id)
VALUES ('a7fb78ff-4faa-41a7-a636-7dad3343203e', 'message3', 5, '2025-01-01 01:00:00.000000', true, 2);
