CREATE TYPE "class_type" AS ENUM (
  'lab',
  'exercises',
  'lecture'
);

CREATE TABLE "schedule_version" (
  "entity_id" SERIAL UNIQUE PRIMARY KEY NOT NULL,
  "url" varchar,
  "version_update_date" timestamp,
  "version_addition_date" timestamp
);

CREATE TABLE "schedule" (
  "entity_id" SERIAL UNIQUE PRIMARY KEY NOT NULL,
  "date" date,
  "daily_schedule_id" int
);

CREATE TABLE "daily_schedule" (
  "entity_id" SERIAL PRIMARY KEY NOT NULL,
  "is_active" boolean,
  "class_details_id" int,
  "group_id" int
);

CREATE TABLE "class_details" (
  "entity_id" SERIAL PRIMARY KEY NOT NULL,
  "class_code" int,
  "type" lecture_type,
  "lecturer_code" int,
  "start" time,
  "end" time
);

CREATE TABLE "class" (
  "code" int,
  "name" varchar,
  "short_name" varchar
);

CREATE TABLE "lecturer" (
  "code" int,
  "email" varchar,
  "name" varchar,
  "surname" varchar,
  "group" int
);

ALTER TABLE "daily_schedule" ADD FOREIGN KEY ("entity_id") REFERENCES "schedule" ("daily_schedule_id");

ALTER TABLE "class_details" ADD FOREIGN KEY ("entity_id") REFERENCES "daily_schedule" ("class_details_id");

ALTER TABLE "class" ADD FOREIGN KEY ("code") REFERENCES "class_details" ("class_code");

ALTER TABLE "lecturer" ADD FOREIGN KEY ("code") REFERENCES "class_details" ("lecturer_code");
