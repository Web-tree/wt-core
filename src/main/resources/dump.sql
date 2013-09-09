CREATE TABLE activity
(
  "activityId" serial NOT NULL,
  "nodeId" integer NOT NULL,
  "humanId" integer DEFAULT 0,
  CONSTRAINT activity_pkey PRIMARY KEY ("activityId"),
  CONSTRAINT "activity_humanId_fkey" FOREIGN KEY ("humanId")
      REFERENCES human ("humanId") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "activity_nodeId_fkey" FOREIGN KEY ("nodeId")
      REFERENCES node ("nodeId") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "activity_nodeId_humanId_key" UNIQUE ("nodeId", "humanId")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE activity
  OWNER TO webtree;

CREATE TABLE category
(
  "categoryId" serial NOT NULL,
  title text,
  CONSTRAINT category_pkey PRIMARY KEY ("categoryId")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE category
  OWNER TO webtree;

CREATE TABLE human
(
  "humanId" integer NOT NULL DEFAULT nextval('human_id_seq'::regclass),
  name text,
  rate integer,
  hidden boolean DEFAULT false,
  CONSTRAINT human_id PRIMARY KEY ("humanId")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE human
  OWNER TO webtree;

CREATE TABLE node
(
  "nodeId" integer NOT NULL DEFAULT nextval('node_id_seq'::regclass),
  "ownerId" integer,
  title text,
  "categoryId" integer,
  rate integer NOT NULL DEFAULT 0,
  active boolean,
  creation timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT node_id PRIMARY KEY ("nodeId")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE node
  OWNER TO webtree;

CREATE TABLE page
(
  "pageId" integer NOT NULL DEFAULT nextval('page_id_seq'::regclass),
  header character(255),
  content text,
  CONSTRAINT page_id PRIMARY KEY ("pageId")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE page
  OWNER TO webtree;

CREATE TABLE feedback
(
  "feedbackId" integer NOT NULL DEFAULT nextval('feedback_id_seq'::regclass),
  theme text,
  email text,
  text text,
  CONSTRAINT feedback_pkey PRIMARY KEY ("feedbackId")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE feedback
  OWNER TO webtree;

