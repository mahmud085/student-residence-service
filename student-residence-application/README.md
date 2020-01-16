# Student Residence

Student Residence is an application for managing residence for the students.

## Installation

Install docker in your local machine.

## Update URLs

Update the following file.

```
/client-app/src/assets/config/config.json
```

Edit the base URLs of the services and replace the IP address with the IP address of the docker installed in your local machine. Do not change the protocol or the port. The existing IP address is the default cocker host IP.

```
{
    "service": {
        "auth": {
            "baseUrl": "http://192.168.99.100:8080"
        },
        "contract": {
            "baseUrl": "http://192.168.99.100:8081"
        },
        "appointment": {
            "baseUrl": "http://192.168.99.100:8082"
        }
    }
}
```

## Build and run
Navigate to the root of the project and run the following command.

```
docker-compose up
```

Services and Client Application will be available at the following URLs.

```
Authentication Service - http://{docker-ip-address}:8080/api
Contract Service - http://{docker-ip-address}:8081/api
Appointment Service - http://docker-ip-address:8082/api
Client Application - http://{docker-ip-address}:4200
```