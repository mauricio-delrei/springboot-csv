Instructions
POST http://localhost:8080/customer (Method to create a customer in the database)
Payload Example:
{
        "id": "",
        "customerRef": "PEX992",
        "customerName": "Charles",
        "addressLine1": "Ambleside Avenue",
        "addressLine2": "10C",
        "town": "STREATHAM",
        "county": "London",
        "country": "UK",
        "postcode": "SW16 6AD"
	
}
GET http://localhost:8080/customer (Method that returns a list of customers registered in the database)

GET http://localhost:8080/api/csv/download (Method that download the .csv file)

POST http://localhost:8080/api/csv/upload (Method that upload the .csv file)



