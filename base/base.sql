CREATE SCHEMA IF NOT EXISTS "public";

CREATE SEQUENCE categorie_id_categorie_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE produit_id_produit_seq START WITH 1 INCREMENT BY 1;

CREATE  TABLE categorie ( 
	designation          varchar(255)    ,
	id_categorie         integer DEFAULT nextval('categorie_id_categorie_seq'::regclass) NOT NULL  ,
	CONSTRAINT pk_categorie PRIMARY KEY ( id_categorie )
 );

CREATE  TABLE mot_concerner ( 
	mot                  varchar(255)    
 );

CREATE  TABLE produit ( 
	id_categorie_fk      integer    ,
	designation_produit  varchar(255)    ,
	prix                 double precision    ,
	qualite              double precision    ,
	id_produit           integer DEFAULT nextval('produit_id_produit_seq'::regclass) NOT NULL  ,
	CONSTRAINT pk_produit PRIMARY KEY ( id_produit ),
	CONSTRAINT fk_produit_categorie FOREIGN KEY ( id_categorie_fk ) REFERENCES categorie( id_categorie )   
 );

INSERT INTO categorie( designation, id_categorie ) VALUES ( 'Telephone', 1);
INSERT INTO categorie( designation, id_categorie ) VALUES ( 'Montre', 2);
INSERT INTO categorie( designation, id_categorie ) VALUES ( 'Ordinateur', 3);
INSERT INTO mot_concerner( mot ) VALUES ( 'plus');
INSERT INTO mot_concerner( mot ) VALUES ( 'moin');
INSERT INTO mot_concerner( mot ) VALUES ( 'chere');
INSERT INTO mot_concerner( mot ) VALUES ( 'meilleur');
INSERT INTO mot_concerner( mot ) VALUES ( 'pire');
INSERT INTO mot_concerner( mot ) VALUES ( 'prix');
INSERT INTO mot_concerner( mot ) VALUES ( 'qualite');
INSERT INTO mot_concerner( mot ) VALUES ( 'Ar');
INSERT INTO mot_concerner( mot ) VALUES ( 'Q');
INSERT INTO mot_concerner( mot ) VALUES ( 'rapport');
INSERT INTO mot_concerner( mot ) VALUES ( 'top');
INSERT INTO produit( id_categorie_fk, designation_produit, prix, qualite, id_produit ) VALUES ( 1, 'Honor 6C Pro', 1000.0, 5.5, 1);
INSERT INTO produit( id_categorie_fk, designation_produit, prix, qualite, id_produit ) VALUES ( 1, 'Samsung S6', 2000.0, 6.0, 2);
INSERT INTO produit( id_categorie_fk, designation_produit, prix, qualite, id_produit ) VALUES ( 1, 'Meizu', 1000.0, 4.0, 3);
INSERT INTO produit( id_categorie_fk, designation_produit, prix, qualite, id_produit ) VALUES ( 2, 'Casio 2', 1000.0, 8.0, 5);
INSERT INTO produit( id_categorie_fk, designation_produit, prix, qualite, id_produit ) VALUES ( 3, 'ASUS', 10000.0, 8.5, 7);
INSERT INTO produit( id_categorie_fk, designation_produit, prix, qualite, id_produit ) VALUES ( 3, 'MSI', 12000.0, 10.0, 8);
INSERT INTO produit( id_categorie_fk, designation_produit, prix, qualite, id_produit ) VALUES ( 3, 'HP', 9000.0, 7.5, 9);
INSERT INTO produit( id_categorie_fk, designation_produit, prix, qualite, id_produit ) VALUES ( 1, 'Motorola', 800.0, 3.0, 4);
INSERT INTO produit( id_categorie_fk, designation_produit, prix, qualite, id_produit ) VALUES ( 3, 'Mac Book Pro', 15000.0, 8.5, 6);
