## Working with docker without compose

- `docker network create mynetwork`
- `docker run --name myredis --network redisnet -p 6379:6379 -p 8001:8001 -d redis`
- `docker run --name mypostgres --network redisnet -p 5432:5432 -e POSTGRES_DB=course1 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123 -d postgres`
- `docker build -t my-spring-boot-app .`
- `docker run --name my-spring-boot-app --network redisnet -p 8080:8080 my-spring-boot-app`
