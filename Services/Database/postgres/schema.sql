CREATE TABLE IF NOT EXISTS "patients"
(
    uid serial PRIMARY KEY,
    full_name varchar (50) NOT NULL,
    date_of_birth date NOT NULL,
    email varchar (50) NOT NULL,
    password_hash char (64) NOT NULL
);
CREATE INDEX IF NOT EXISTS patient_id ON patients (uid);

CREATE TABLE IF NOT EXISTS "doctors"
(
    uid serial PRIMARY KEY,
    full_name varchar (50) NOT NULL,
    email varchar (50) NOT NULL,
    password_hash char (64) NOT NULL,
    verified varchar (50) NOT NULL,
    specialty varchar (50)
);
CREATE INDEX IF NOT EXISTS doctor_id ON doctors (uid);
CREATE INDEX IF NOT EXISTS specialty ON doctors (specialty);

CREATE TABLE IF NOT EXISTS "consultation_queue"
(
    consultation_id serial PRIMARY KEY,
    patient_uid integer references patients(uid) NOT NULL,
    assigned_doctor_uid integer references doctors(uid),
    is_assigned integer NOT NULL,
    specialty varchar (50) NOT NULL,
    added_on timestamp NOT NULL
);
CREATE INDEX IF NOT EXISTS queue_idx ON consultation_queue (added_on, is_assigned);

CREATE TABLE IF NOT EXISTS "messages"
(
    message_id serial PRIMARY KEY,
    from_patient integer NOT NULL,
    consultation_id integer references consultation_queue(consultation_id) NOT NULL,
    message_content varchar (500) NOT NULL
);
CREATE INDEX IF NOT EXISTS message_idx ON messages (consultation_id, message_id);

CREATE TABLE IF NOT EXISTS "resources"
(
    resource_id serial PRIMARY KEY,
    owner_uid integer references patients(uid),
    resource_name varchar (50) NOT NULL,
    resource_qty integer NOT NULL,
    latitude float NOT NULL,
    longitude float NOT NULL,
    price float NOT NULL,
    notes varchar (200),
    available integer NOT NULL
);
CREATE INDEX IF NOT EXISTS name ON resources (resource_name, available);

CREATE TABLE IF NOT EXISTS "prescriptions"
(
    prescription_id serial PRIMARY KEY,
    issuer_doctor_uid integer references doctors(uid),
    patient_uid integer references patients(uid),
    issue_date date NOT NULL,
    notes varchar (500)
);

CREATE TABLE IF NOT EXISTS "medicines"
(
    med_id serial PRIMARY KEY,
    prescription_id integer references prescriptions(prescription_id),
    medicine_name varchar (100) NOT NULL,
    num_days integer NOT NULL,
    notes varchar (500)
);