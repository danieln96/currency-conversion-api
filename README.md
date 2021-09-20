# SAMPLE REQUEST
```
curl "http://localhost:8080/api/currency/USD/" \
  -X POST \
  -d "{\n  \"currencyCode\":\"EUR\",\n  \"amount\":\"12.93\"\n}" \
  -H "Content-Type: application/json"
```