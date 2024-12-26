CREATE TABLE public.transactions (
    id integer NOT NULL,
    amount numeric NOT NULL,
    registration_id varchar(255) NOT NULL,
    time_stamp timestamp NOT NULL,
    status integer NOT NULL,
    processing_time int NOT NULL
);

ALTER TABLE public.transactions OWNER TO pluralsight;

CREATE SEQUENCE public.transactions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
ALTER TABLE public.transactions_id_seq OWNER TO pluralsight;

ALTER SEQUENCE public.transactions_id_seq OWNED BY public.transactions.id;

ALTER TABLE ONLY public.transactions ALTER COLUMN id SET DEFAULT nextval('public.transactions_id_seq'::regclass);

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (id);
