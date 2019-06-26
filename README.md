## database credentials
`database.url=jdbc:hsqldb:mem:voting`
`database.username=`
`database.password=`
 
### curl samples (application deployed in application context `app`).

### user can look restaurants and menus without authorization

#### get All restaurants
`curl -s http://localhost:8080/app/restaurants`

#### get restaurant 100002
`curl -s http://localhost:8080/app/restaurants/100002`

#### get current menus
`curl -s http://localhost:8080/app/menus/by-date`

#### get menus by date 2019-06-05
`curl -s http://localhost:8080/app/menus/by-date?date=2019-06-05`

#### get menus by restaurant 100002
`curl -s http://localhost:8080/app/menus/by-restaurant?restaurantId=100002`

### for logged user

#### get all votes for for user
`curl -s http://localhost:8080/app/votes --user user@yandex.ru:password`

#### create vote for restaurant 100002
`curl -s -X POST http://localhost:8080/app/votes?restaurantId=100002 --user user@yandex.ru:password`

#### update vote 100017 for restaurant 100003 (only if current time is before 11:00 a.m. and user is voted this day)
`curl -s -X PUT http://localhost:8080/app/votes/100018?restaurantId=100003 --user user@yandex.ru:password`

### for admin

#### delete restaurant 100002
`curl -s -X DELETE http://localhost:8080/app/restaurants/100003 --user admin@gmail.com:admin`

#### create restaurant
`curl -s -X POST -d '{"name":"Created restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/app/restaurants --user admin@gmail.com:admin`

#### update restaurant 100004
`curl -s -X PUT -d '{"name":"updated restaurant"}' -H 'Content-Type: application/json' http://localhost:8080/app/restaurants/100002 --user admin@gmail.com:admin`

#### get lunches
`curl -s http://localhost:8080/app/admin/lunches --user admin@gmail.com:admin`

#### get lunch 100005
`curl -s http://localhost:8080/app/admin/lunches/100005 --user admin@gmail.com:admin`

#### delete lunch 100008
`curl -s -X DELETE http://localhost:8080/app/admin/lunches/100008 --user admin@gmail.com:admin`

#### create lunch
`curl -s -X POST -d '{"name":"Created lunch", "price":10}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/app/admin/lunches --user admin@gmail.com:admin`

#### update lunch 100005
`curl -s -X PUT -d '{"name":"updated lunch", "price":10}' -H 'Content-Type: application/json' http://localhost:8080/app/admin/lunches/100005 --user admin@gmail.com:admin`

#### create menu for restaurant 100004
`curl -s -X POST -d '{"restaurant": {"id":100004},"lunches":[{"id":100006},{"id":100007}]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/app/menus --user admin@gmail.com:admin`

#### delete menu for 2019-06-05 for restaurant 100003
`curl -s -X DELETE 'http://localhost:8080/app/menus?restaurantId=100003&date=2019-06-05' --user admin@gmail.com:admin`