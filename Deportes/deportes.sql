DROP FUNCTION IF EXISTS verdatosdeportista(character varying);
DROP TYPE IF EXISTS tipo_deportistas;
DROP TABLE IF EXISTS deportista;
DROP TABLE IF EXISTS deporte;

CREATE TABLE public.deporte
(
    cod serial NOT NULL,
    nombre character varying(20),
    PRIMARY KEY (cod)
);

ALTER TABLE public.deporte
    OWNER to postgres;

ALTER TABLE public.deporte
    OWNER to postgres;

CREATE TABLE public.deportista
(
    cod serial NOT NULL,
    nombre character varying(20),
    codDeporte integer,
    PRIMARY KEY (cod),
	CONSTRAINT fk_deporte FOREIGN KEY(codDeporte) REFERENCES deporte(cod)
);

ALTER TABLE public.deportista
    OWNER to postgres;
	
INSERT INTO deporte 
(nombre)
VALUES
('Fútbol'), ('Baloncesto'), ('Arco'), ('Jabalina');

INSERT INTO deportista
(nombre,codDeporte) 
VALUES
('Manuel', null), ('Cristian', 1), ('Jose', 2), ('Julia',3), ('Adrian',3);

CREATE TYPE tipo_deportistas as (codDeportista integer, nomDeportista character varying, nomDeporte character varying);

CREATE OR REPLACE FUNCTION verdatosdeportista(nombreDeportista character varying) RETURNS SETOF tipo_deportistas
AS
$$
DECLARE
	reg tipo_deportistas%ROWTYPE;
BEGIN
	FOR reg IN SELECT deportista.cod, deportista.nombre, deporte.nombre
		FROM deporte, deportista
		WHERE deporte.cod = deportista.codDeporte 
		  AND UPPER(deportista.nombre) LIKE UPPER(nombreDeportista)
	LOOP
		RETURN NEXT reg;
	END LOOP;
RETURN;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION deporteNoAsignado() RETURNS SETOF character varying
AS
$$
DECLARE
	reg character varying; 
BEGIN
	FOR reg IN SELECT deporte.nombre
		FROM deporte
		WHERE not exists (select 1 from deportista where deportista.codDeporte = deporte.cod) 
	LOOP
		RETURN NEXT reg;
	END LOOP;
RETURN;
END;
$$
LANGUAGE plpgsql;
