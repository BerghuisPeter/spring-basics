POST http://localhost:8080/trades
Content-Type: application/json
#Authorization: Basic dXNlcjpwYXNzd29yZA==
Authorization: Basic dG9yby55YW1hZGFAZ21haWwuY29tOnRvcm8ueWFtYWRh

{
"quantity": 10,
"price": 3,
"currency": "JPY",
"buy_sell": "B",
"asset_type": "Bond",
"origin_country": "JPY"
}

###

POST http://localhost:8080/users
Content-Type: application/json

{
"userId": "toro.yamada@gmail.com",
"password": "toro.yamada"
}


###

GET http://localhost:8080/actuator/health
Content-Type: application/json


###

GET http://localhost:8080/trades?price=3
Content-Type: application/json
Authorization: Basic dG9yby55YW1hZGFAZ21haWwuY29tOnRvcm8ueWFtYWRh


###