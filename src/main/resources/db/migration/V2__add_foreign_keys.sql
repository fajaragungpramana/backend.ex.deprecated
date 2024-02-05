ALTER TABLE wallets
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE transactions
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE transactions
ADD CONSTRAINT fk_category_id
FOREIGN KEY (category_id)
REFERENCES categories(id);

ALTER TABLE transactions
ADD CONSTRAINT fk_status_id
FOREIGN KEY (status_id)
REFERENCES statuses(id);

ALTER TABLE transactions
ADD CONSTRAINT fk_type_id
FOREIGN KEY (type_id)
REFERENCES types(id);

ALTER TABLE payments
ADD CONSTRAINT fk_transaction_id
FOREIGN KEY (transaction_id)
REFERENCES transactions(id);

ALTER TABLE payments
ADD CONSTRAINT fk_wallet_id
FOREIGN KEY (wallet_id)
REFERENCES wallets(id);