-- migration 1
ALTER TABLE contacts ADD COLUMN first_name TEXT;
ALTER TABLE contacts ADD COLUMN last_name TEXT;
ALTER TABLE contacts ADD COLUMN phone TEXT;
ALTER TABLE contacts ADD COLUMN email TEXT;
ALTER TABLE contacts
    ALTER COLUMN name DROP NOT NULL;