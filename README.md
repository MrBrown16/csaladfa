endpoints:
http://localhost:8080/
GET person/reference/:name
GET person/:id
GET person/create    (example object)
GET person/update    (example object)
PUT person/update/:id  
POST person/create
DELETE person/del/:id

Get /person/{id}

{"id":4,"name":"Ákos","sex":false,"birthDate":"2011-05-09T22:00:00.000+00:00","birthLocation":"Bécs","motherId":2,"motherName":"Mira","fatherId":null,"fatherName":null,"deathDate":null,"deathLocation":null,"childId":null,"childName":null}

Post /person

{"name":"Ákos",  REQUIRED
"sex":false, REQUIRED
"birthDate":"2011-05-09T22:00:00.000+00:00", REQUIRED
"birthLocation":"Bécs", REQUIRED
"motherId":2, 
"motherName":"Mira",
"fatherId":null,
"fatherName":null,
"deathDate":null,
"deathLocation":null,
"childId":null,
"childName":null}

get id=1 / name matches sanyi (LIKE %name%)
{"id":1,"name":"Sanyi","sex":false,"birthDate":"1988-01-01","birthLocation":"Budapest","motherId":null,"motherName":null,"fatherId":null,"fatherName":null,"deathDate":null,"deathLocation":null,"children":[{"id":2,"name":"Mira"},{"id":7,"name":"Miklós"},{"id":8,"name":"Miklós"}]}

post
{"name":"Miklós","sex":false,"birthDate":"1970-11-03","birthLocation":"Miskolc","motherId":66,"motherName":"Márta","fatherId":1,"fatherName":"Sanyi","deathDate":null,"deathLocation":null,"children":[{"id":2,"name":"Mira"}]}

put id = 7
{"id":7,"name":"Miklós","sex":false,"birthDate":"1970-11-03","birthLocation":"Miskolc","motherId":66,"motherName":"Márta","fatherId":1,"fatherName":"Sanyi","deathDate":null,"deathLocation":null,"children":[]}


Basic CRUD achieved in approximately 17 hours 🥲
