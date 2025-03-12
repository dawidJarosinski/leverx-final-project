-- admin@admin.com:admin
INSERT INTO users (first_name, last_name, password, email, created_at, role, enabled)
VALUES ('admin', 'admin', '$2a$12$TS5XPiVPCESnPjBBYgnd2.ZIQUekePXROUNzjLH1BKlARKH7KST8u',
        'admin@admin.com', '2025-01-01 01:00:00.000000', 0, true);

-- seller@seller.com:seller
INSERT INTO users (first_name, last_name, password, email, created_at, role, enabled)
VALUES ('seller', 'seller', '$2a$12$VAtVGnNnL9x8k.pdj448uuca9V4PgxNoLG.8tvy05J3Tm3KJlWa2W',
        'seller@seller.com', '2025-01-01 01:00:00.000000', 1, true);
-- sellerProfile for seller
INSERT INTO seller_profiles (name, approved, user_id)
VALUES ('super seller profile', true, 2);