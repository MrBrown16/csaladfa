-- PERSON_ID  	BIRTHDATE  	BIRTHLOCATION  	DEATHDATE  	DEATHLOCATION  	NAME  	SEX  
INSERT INTO persons (birthDate,birthLocation,deathDate,deathLocation,name,sex) VALUES
('1988-01-01','Budapest',null,null,'Sanyi',false),
('2001-11-11','Debrecen',null,null,'Mira',true),
('1989-04-21','Pécs',null,null,'Saci',true),
('2011-05-10','Bécs',null,null,'Ákos',false),
('2011-05-10','Bécs',null,null,'Ákos1',false),
('2011-05-10','Bécs',null,null,'Ákos2',false);


-- CHILDREN_PERSON_ID  	PARENTS_PERSON_ID  
INSERT INTO PERSONS_PERSONS (children_person_id,parents_person_id) VALUES
(2,1),
(2,3),
(4,2),
(5,2),
(6,2);