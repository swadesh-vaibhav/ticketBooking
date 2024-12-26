CREATE TABLE public.transactions (
    id varchar(255) NOT NULL,
    amount numeric NOT NULL,
    registration_id varchar(255) NOT NULL,
    time_stamp timestamp NOT NULL,
    status integer NOT NULL,
    processing_time int NOT NULL
);

ALTER TABLE public.transactions OWNER TO pluralsight;

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (id);
