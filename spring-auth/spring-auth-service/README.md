# Overview

## Tests

The `com.luxoft.springadvanced.auth.serviceaccounts.ServiceAccountTest` test relies on the fact that there is keycloak
up and running on the computer. In order to run keycloak in docker, please execute the following commands:

1. Start keycloak in docker
```bash
docker run -p 18080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.0.1 start-dev
```
2. Open [http://localhost:8080/](http://localhost:8080/)
3. Enter credentials: `admin/admin`
4. Create a new realm: `myrealm`
5. Create a new confidential client: `client`
6. Replace the `-DclientSecret`'s value with the credentials of the `client` client in the `ServiceAccountTest` Run Configuration: `-DclientSecret="XXXYYYZZZ"`
7. Run the `ServiceAccountTest` Run Configuration

## Oauth 2.0. Authorization Code Flow

1. Generate a URL:
```bash
echo 'http://localhost:18080/realms/myrealm/protocol/openid-connect/auth'\
'?client_id=client'\
'&redirect_uri=http%3A//localhost%3A3000/auth'\
'&response_type=code'\
'&grant_type=authorization_code'\
'&state=2'\
'&scope=openid'
```
2. Open the link in your web-browser
3. Enter credentials
4. You see a Not Found page, DON'T WORRY, it's ok
5. Copy the link of this NOT FOUND page from your web-browser's address bar 
6. Remove everything from the link address until you see `code=xxx...`
7. Copy what is left from the link and create a new variable in your shell:
```bash
code=xxx....
```
8. Create a new variable for your client
```bash
CLIENT_SECRET=yyy....
```
8. Execute the following curl:
```bash
curl -v -X POST 'http://localhost:18080/realms/myrealm/protocol/openid-connect/token' \
  -d 'client_id=client' \
  -d "client_secret=$CLIENT_SECRET" \
  -d 'grant_type=authorization_code' \
  -d 'redirect_uri=http%3A//localhost%3A3000/auth' \
  -d "code=${code}" | json_pp
```
9. Congratulations, you just issued your first token manually!
10. Now copy the `access_token` to your clipboard and decrypt it with [jwt.io](https://jwt.io)